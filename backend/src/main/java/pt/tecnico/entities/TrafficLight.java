package pt.tecnico.entities;

import javax.persistence.*;

@Entity
@Table(name = "`Traffic_Light`")
public class TrafficLight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Traffic_Light_ID")
    private String id;

    private TrafficLightState state;

    public TrafficLight() {
    }

    public TrafficLight(TrafficLightState state) {
        this.state = state;
    }

    public TrafficLight(String lat, String lng) {
        id = lat + lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TrafficLightState getState() {
        return state;
    }

    public void setState(TrafficLightState state) {
        this.state = state;
    }
}
