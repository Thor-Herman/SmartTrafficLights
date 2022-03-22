package pt.tecnico.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pt.tecnico.MQTT;

import java.lang.invoke.MethodHandles;

@Service
public class DeviceManagementService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private MQTT mqtt;

    @Autowired
    public DeviceManagementService(MQTT mqtt) {
        this.mqtt = mqtt;
        try {
            mqtt.setup();
        } catch (org.eclipse.paho.client.mqttv3.MqttException e) {
            logger.error("Error: ", e);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeDevice() {
        try {
            mqtt.connect();
            mqtt.requestInitialTrafficLightData();
        } catch (org.eclipse.paho.client.mqttv3.MqttException e) {
            logger.error("Error: ", e);
        }
    }

}
