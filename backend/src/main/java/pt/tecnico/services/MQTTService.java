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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Service
public class MQTTService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private final String TOPIC = "TRAFFIC_LIGHT_DATA";
    private final String SERVER_URI = "tcp://iot.eclipse.org:1883";
    private final int CONNECTION_TIMEOUT = 0; //TODO: Change to 10 when actual connection is established
    private final int QOS = 0;

    private final DeviceRepository deviceRepository;
    private final TrafficLightRepository trafficLightRepository;
    private IMqttClient publisher;
    private IMqttClient subscriber;
    private byte[] payload;

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
    }

    private void send(MqttMessage msg) throws MqttException {
        if (!publisher.isConnected()) {
            return;
        }
        msg.setQos(QOS);
        msg.setRetained(true);
        publisher.publish(TOPIC, msg);
    }

    public void requestInitialTrafficLightData() throws MqttException {
        double temp = 80; //TODO: Read actual data from Pi
        byte[] payload = String.valueOf(temp).getBytes();
        send(new MqttMessage(payload));
    }

    public void requestTrafficLightState() throws MqttException {
        double temp = 80; //TODO: Read actual data from Pi
        byte[] payload = String.valueOf(temp).getBytes();
        send(new MqttMessage(payload));
    }

    public void sendNewTrafficLightState(TrafficLightState state) throws MqttException {
        byte[] payload = String.valueOf(state).getBytes();
        send(new MqttMessage(payload));
    }

    public void receive() throws MqttException {
        if (!subscriber.isConnected()) {
            return;
        }
        CountDownLatch receivedSignal = new CountDownLatch(10);
        subscriber.subscribe(TOPIC, (topic, msg) -> {
            setPayload(msg.getPayload());
            receivedSignal.countDown();
        });
    }

    private void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public List<TrafficLight> receiveInitialTrafficLightData() throws MqttException, InterruptedException {
        receive();
        List<TrafficLight> trafficLightList = new ArrayList<>();
        try (ByteArrayInputStream bis = new ByteArrayInputStream(payload);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            while(ois.available() != 0) {
                TrafficLight deserializedTrafficLight = (TrafficLight) ois.readObject();
                logger.info("Deserialized tf object with id: " + deserializedTrafficLight.getId() +
                        " and state: " + deserializedTrafficLight.getCurrentLightState());
                trafficLightList.add(deserializedTrafficLight);
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error: ", e);
        }
        return trafficLightList;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeDevice() {

        //TESTING
        TrafficLight trafficLight = new TrafficLight(TrafficLightState.GREEN);
        trafficLightRepository.save(trafficLight);
        //DONE TESTING
        /**
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
         */
    }
}