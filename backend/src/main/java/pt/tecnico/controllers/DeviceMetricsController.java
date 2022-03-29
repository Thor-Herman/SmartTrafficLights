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
    @GetMapping(path="/readRedDuration")
    public ResponseEntity<Integer> readRedDuration(String id) {
        int duration = deviceMetricsService.computeRedDuration(id);
        return new ResponseEntity<>(duration, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path="/readYellowDuration")
    public ResponseEntity<Integer> readYellowDuration(String id) {
        int duration = deviceMetricsService.computeYellowDuration(id);
        return new ResponseEntity<>(duration, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path="/readGreenDuration")
    public ResponseEntity<Integer> readGreenDuration(String id) {
        int duration = deviceMetricsService.computeGreenDuration(id);
        return new ResponseEntity<>(duration, HttpStatus.OK);
    }
}
