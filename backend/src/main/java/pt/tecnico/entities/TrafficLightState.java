package pt.tecnico.entities;

public enum TrafficLightState {
    RED,
    YELLOW,
    GREEN;

    public static TrafficLightState mapTrafficLightState(int num) {
        switch (num) {
            case 0:
                return RED;
            case 1:
                return YELLOW;
            case 2:
                return GREEN;
            default:
                return null;
        }
    }
}