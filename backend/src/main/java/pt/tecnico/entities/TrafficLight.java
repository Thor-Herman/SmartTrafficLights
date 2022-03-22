package pt.tecnico.entities;

public class TrafficLight {

    private TrafficLightState state;

    private String id;

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
