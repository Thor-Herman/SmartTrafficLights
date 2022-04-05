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
    private int redLightDuration;

    @Column(name = "Green_Light_Duration")
    private int greenLightDuration;

    @Column(name = "Yellow_Light_Duration")
    private int yellowLightDuration;

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

    public int getRedLightDuration() {
        return redLightDuration;
    }

    public void setRedLightDuration(int redLightDuration) {
        this.redLightDuration = redLightDuration;
    }

    public int getGreenLightDuration() {
        return greenLightDuration;
    }

    public void setGreenLightDuration(int greenLightDuration) {
        this.greenLightDuration = greenLightDuration;
    }

    public int getYellowLightDuration() {
        return yellowLightDuration;
    }

    public void setYellowLightDuration(int yellowLightDuration) {
        this.yellowLightDuration = yellowLightDuration;
    }

    public TrafficLightState getCurrentLightState() {
        return currentLightState;
    }

    public void setCurrentLightState(TrafficLightState currentLightState) {
        this.currentLightState = currentLightState;
    }
}
