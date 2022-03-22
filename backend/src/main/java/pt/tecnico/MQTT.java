package pt.tecnico;

import org.eclipse.paho.client.mqttv3.*;
import pt.tecnico.entities.TrafficLightState;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MQTT {

    private IMqttClient publisher;
    private IMqttClient subscriber;
    private String TOPIC = "TRAFFIC_LIGHT_DATA";

    public void setup() throws MqttException {
        String publisherId = UUID.randomUUID().toString();
        publisher = new MqttClient("tcp://iot.eclipse.org:1883",publisherId);
    }

    public void connect() throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        publisher.connect(options);
    }

    private void send(MqttMessage msg) throws MqttException {
        if (!publisher.isConnected()) {
            return;
        }
        msg.setQos(0);
        msg.setRetained(true);
        publisher.publish(TOPIC, msg);
    }

    public void requestInitialTrafficLightData() throws MqttException {
        double temp = 80; //TODO: Read actual data from Pi
        byte[] payload = String.format("T:%04.2f", temp).getBytes();
        send(new MqttMessage(payload));
    }

    public void requestTrafficLightState() throws MqttException {
        double temp = 80; //TODO: Read actual data from Pi
        byte[] payload = String.format("T:%04.2f", temp).getBytes();
        send(new MqttMessage(payload));
    }

    public void sendNewTrafficLightState(TrafficLightState state) throws MqttException {
        byte[] payload = String.format("T:%04.2f", state).getBytes();
        send(new MqttMessage(payload));
    }

    public void receive() throws MqttException, InterruptedException {
        CountDownLatch receivedSignal = new CountDownLatch(10);
        subscriber.subscribe(TOPIC, (topic, msg) -> {
            byte[] payload = msg.getPayload();
            receivedSignal.countDown();
        });
        receivedSignal.await(1, TimeUnit.MINUTES);
    }

}
