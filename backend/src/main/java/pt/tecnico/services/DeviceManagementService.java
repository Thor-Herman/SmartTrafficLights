package pt.tecnico.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.tecnico.entities.TrafficLight;
import pt.tecnico.entities.TrafficLightState;
import pt.tecnico.repositories.TrafficLightRepository;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

@Service
public class DeviceManagementService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private TrafficLightRepository trafficLightRepository;

    @Autowired
    public DeviceManagementService(TrafficLightRepository trafficLightRepository) {
        this.trafficLightRepository = trafficLightRepository;
    }

    public TrafficLightState getTrafficLightState(int id) {
        Optional<TrafficLight> tf = trafficLightRepository.findById(id);
        return tf.map(TrafficLight::getState).orElse(null);
    }

    private void computeTrafficLightState() {
        //TODO: Algorithm to determine traffic light state according to computer vision data
    }

}