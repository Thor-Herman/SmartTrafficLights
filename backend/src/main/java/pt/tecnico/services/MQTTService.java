package pt.tecnico.services;

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import pt.tecnico.entities.Device;
import pt.tecnico.entities.TrafficLight;
import pt.tecnico.entities.TrafficLightState;
import pt.tecnico.repositories.DeviceRepository;
import pt.tecnico.repositories.TrafficLightRepository;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class MQTTService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private final String TODO_TOPIC = "TODO";
    private final String TRAFFIC_LIGHT_1_DATA_TOPIC = "TRAFFIC_LIGHT_1_DATA";
    private final String TRAFFIC_LIGHT_2_DATA_TOPIC = "TRAFFIC_LIGHT_2_DATA";
    private final String SERVER_URI = "tcp://localhost:1883";
    private final int CONNECTION_TIMEOUT = 10;
    private final int QOS = 0;

    private final DeviceRepository deviceRepository;
    private final TrafficLightRepository trafficLightRepository;
    private IMqttClient publisher;
    private IMqttClient subscriber;
    private String payload;

    @Autowired
    public MQTTService(DeviceRepository deviceRepository, TrafficLightRepository trafficLightRepository) {
        this.deviceRepository = deviceRepository;
        this.trafficLightRepository = trafficLightRepository;
        setup();
    }

    public void setup() {
        String publisherId = UUID.randomUUID().toString();
        String subscriberId = UUID.randomUUID().toString();
        try {
            publisher = new MqttClient(SERVER_URI, publisherId);
            subscriber = new MqttClient(SERVER_URI, subscriberId);
        } catch (org.eclipse.paho.client.mqttv3.MqttException e) {
            logger.error("Error: ", e);
        }
    }

    public void connect() throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(CONNECTION_TIMEOUT);
        publisher.connect(options);
        subscriber.connect(options);
    }

    private void send(String topic, MqttMessage msg) throws MqttException {
        if (!publisher.isConnected()) {
            logger.error("Publisher is not connected.");
            return;
        }
        msg.setQos(QOS);
        msg.setRetained(true);
        publisher.publish(topic, msg);
    }


    public void requestInitialTrafficLightData() throws MqttException {
        double temp = 80; //TODO: Read actual data from Pi
        byte[] payload = String.valueOf(temp).getBytes();
        send(TRAFFIC_LIGHT_1_DATA_TOPIC, new MqttMessage(payload));
        send(TRAFFIC_LIGHT_2_DATA_TOPIC, new MqttMessage(payload));
    }

    public void requestTrafficData() throws MqttException {
        double temp = 80; //TODO: Read actual data from Pi
        byte[] payload = String.valueOf(temp).getBytes();
        send(TODO_TOPIC, new MqttMessage(payload));
    }

    public void sendNewTrafficLightState(TrafficLightState state) throws MqttException {
        byte[] payload = String.valueOf(state).getBytes();
        send(TODO_TOPIC, new MqttMessage(payload));
    }

    private void setPayload(String payload) {
        this.payload = payload;
    }

    public List<TrafficLight> receiveInitialTrafficLightData() throws MqttException, InterruptedException {
        if (!subscriber.isConnected()) {
            logger.error("Subscriber is not connected.");
            return null;
        }
        CountDownLatch receivedSignal = new CountDownLatch(10);

        List<TrafficLight> trafficLightList = new ArrayList<>();

        subscriber.subscribe(TRAFFIC_LIGHT_1_DATA_TOPIC, (topic, msg) -> {
            if (msg != null) {
                logger.info(msg.toString());
                setPayload(msg.toString());
                TrafficLight deserializedTrafficLight =
                        new TrafficLight(TrafficLightState.mapTrafficLightState(Integer.parseInt(msg.toString())));
                logger.info("Deserialized tf object with id: " + deserializedTrafficLight.getId() +
                        " and state: " + deserializedTrafficLight.getCurrentLightState());
                trafficLightList.add(deserializedTrafficLight);
            } else {
                logger.error("IS NULL...");
            }
            receivedSignal.countDown();
        });
        return trafficLightList;
    }

    private void receiveTrafficData() {
        //TODO: Receive cars in road
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeDevice() {

        //TESTING
        /*
        TrafficLight trafficLight = new TrafficLight(TrafficLightState.GREEN);
        trafficLight.setGreenLightDuration(0);
        trafficLight.setRedLightDuration(0);
        trafficLight.setYellowLightDuration(0);
        trafficLightRepository.save(trafficLight);
        int i=0;
        while(true) {
            TrafficLightState newState = TrafficLightState.mapTrafficLightState(i % 3);
            Optional<TrafficLight> tl = trafficLightRepository.findById(1);
            if(tl.isPresent()) {
                tl.get().setCurrentLightState(newState);
                trafficLightRepository.save(tl.get());
            }
            i++;
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        */
        //DONE TESTING

        List<TrafficLight> trafficLightList = new ArrayList<>();
        try {
            connect();
            requestInitialTrafficLightData();
            trafficLightList = receiveInitialTrafficLightData();
        } catch (MqttException | InterruptedException e) {
            logger.error("Error: ", e);
        }
        if(!trafficLightList.isEmpty()) {
            TrafficLight tf1 = trafficLightList.get(0);
            TrafficLight tf2 = trafficLightList.get(1);
            Device device = new Device(tf1, tf2);
            try {
                trafficLightRepository.save(tf1);
                trafficLightRepository.save(tf2);
                deviceRepository.save(device);
            } catch (DataAccessException e) {
                logger.error("Error: ", e);
            }
        }
    }
}