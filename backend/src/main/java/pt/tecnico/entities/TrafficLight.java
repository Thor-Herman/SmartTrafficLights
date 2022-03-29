package pt.tecnico.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "`Traffic_Light`")
public class TrafficLight {

    @Id
    @Column(name = "Traffic_Light_ID")
    private String id;

    @Column(name = "Timestamp", nullable = false)
    private Date timestamp;

    @Column(name = "Red_Light_Duration", nullable = false)
    private TrafficLightState redLightDuration;

    @Column(name = "Green_Light_Duration", nullable = false)
    private TrafficLightState greenLightDuration;

    @Column(name = "Yellow_Light_Duration", nullable = false)
    private TrafficLightState yellowLightDuration;

    @Transient
    private TrafficLightState currentLightState;

    public TrafficLight() {
    }

    public TrafficLight(TrafficLightState currentLightState, String lat, String lng) {
        this.currentLightState = currentLightState;
        id = lat + lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
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
