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

    private final TrafficLightRepository trafficLightRepository;

    @Autowired
    public DeviceManagementService(TrafficLightRepository trafficLightRepository) {
        this.trafficLightRepository = trafficLightRepository;
    }

    public TrafficLightState getTrafficLightState(int id) {
        logger.info("Fetching current tf light state...");
        Optional<TrafficLight> tf = trafficLightRepository.findById(id);
        return tf.map(TrafficLight::getCurrentLightState).orElse(null);
    }

    private void computeTrafficLightState() {
        //TODO: Determine TL state according to traffic density and waiting time by leveraging computer vision data (STLSDT)
        logger.info("Computing next traffic light state...");
        //compute traffic density (di) and waiting time (ti) of cars in WZ
        //while (number of cars left > 0)
        //  if ((ti2 the maximum delay found on the road with the red light < 7 seconds)
        //      || (di1 the density of the road with green light < di2 the density of the road with red light))
        //      letPass(di2)
    }

}