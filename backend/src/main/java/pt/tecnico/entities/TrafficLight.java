package pt.tecnico.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "`Traffic_Light`")
public class TrafficLight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Traffic_Light_ID")
    private int id;

    @Column(name = "Timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "Red_Light_Duration")
    private TrafficLightState redLightDuration;

    @Column(name = "Green_Light_Duration")
    private TrafficLightState greenLightDuration;

    @Column(name = "Yellow_Light_Duration")
    private TrafficLightState yellowLightDuration;

    @Column(name = "Current_Light_State")
    private TrafficLightState currentLightState;

    public TrafficLight() {
    }

    public TrafficLight(TrafficLightState currentLightState) {
        this.currentLightState = currentLightState;
        timestamp = Instant.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public TrafficLightState getRedLightDuration() {
        return redLightDuration;
    }

    public void setRedLightDuration(TrafficLightState redLightDuration) {
        this.redLightDuration = redLightDuration;
    }

    public TrafficLightState getGreenLightDuration() {
        return greenLightDuration;
    }

    public void setGreenLightDuration(TrafficLightState greenLightDuration) {
        this.greenLightDuration = greenLightDuration;
    }

    public TrafficLightState getYellowLightDuration() {
        return yellowLightDuration;
    }

    public void setYellowLightDuration(TrafficLightState yellowLightDuration) {
        this.yellowLightDuration = yellowLightDuration;
    }

    public TrafficLightState getCurrentLightState() {
        return currentLightState;
    }

    public void setCurrentLightState(TrafficLightState currentLightState) {
        this.currentLightState = currentLightState;
    }
}
