package pt.tecnico.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class DeviceMetricsService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    public int computeRedDuration(String trafficLightID) {
        //TODO
        logger.info("Fetching last red light duration...");
        //fetch timestamp of last change from yellow to red (dyr) and from red to green (drg)
        //compute last red duration (drg - dyr)
        return -1;
    }

    public int computeYellowDuration(String trafficLightID) {
        //TODO
        logger.info("Fetching last yellow light duration...");
        //fetch timestamp of last change from green to yellow (dgy) and from yellow to red (dyr)
        //compute last yellow duration (dyr - dgy)
        return -1;
    }

    public int computeGreenDuration(String trafficLightID  ) {
        //TODO
        logger.info("Fetching last green light duration...");
        //fetch timestamp of last change from red to green (drg) and from green to yellow (dgy)
        //compute last green duration (dgy - drg)
        return -1;
    }
}
