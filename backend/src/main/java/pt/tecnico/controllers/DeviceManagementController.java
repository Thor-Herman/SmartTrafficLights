package pt.tecnico.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.tecnico.entities.TrafficLightState;

@RestController
public class DeviceManagementController {

    @CrossOrigin(origins = "*")
    @GetMapping(path="/readTrafficLightState")
    public ResponseEntity<TrafficLightState> readTrafficLightState(int id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
