package pt.tecnico.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.tecnico.entities.TrafficLightState;

@RestController
public class DeviceMetricsController {

    @CrossOrigin(origins = "*")
    @GetMapping(path="/readRedTime")
    public ResponseEntity<TrafficLightState> readRedTime(int id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path="/readYellowTime")
    public ResponseEntity<TrafficLightState> readYellowTime(int id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path="/readGreenTime")
    public ResponseEntity<TrafficLightState> readGreenTime(int id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
