package pt.tecnico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.tecnico.services.DeviceMetricsService;

@RestController
public class DeviceMetricsController {

    private final DeviceMetricsService deviceMetricsService;

    @Autowired
    public DeviceMetricsController(DeviceMetricsService deviceMetricsService) {
        this.deviceMetricsService = deviceMetricsService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path="/readTrafficLightMetrics")
    public ResponseEntity<int[]> readRedDuration(String id) {
        int redDuration = deviceMetricsService.computeRedDuration(id);
        int yellowDuration = deviceMetricsService.computeYellowDuration(id);
        int greenDuration = deviceMetricsService.computeGreenDuration(id);
        int[] durations = {redDuration, yellowDuration, greenDuration};
        return new ResponseEntity<>(durations, HttpStatus.OK);
    }
}
