package pt.tecnico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.tecnico.entities.TrafficLightState;
import pt.tecnico.services.DeviceManagementService;

@RestController
public class DeviceManagementController {

    private final DeviceManagementService deviceManagementService;

    @Autowired
    public DeviceManagementController(DeviceManagementService deviceManagementService) {
        this.deviceManagementService = deviceManagementService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path="/readTrafficLightState")
    public ResponseEntity<TrafficLightState> readTrafficLightState(int tf_id) {
        TrafficLightState state = deviceManagementService.getTrafficLightState(tf_id);
        if(state != null) {
            return new ResponseEntity<>(state, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
