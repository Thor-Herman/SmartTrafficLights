package pt.tecnico.entities;

import javax.persistence.*;

@Entity
@Table(name = "`Device`")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "`Device_ID`")
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Traffic_Light_1_ID", referencedColumnName = "Traffic_Light_ID")
    private TrafficLight tf1;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Traffic_Light_2_ID", referencedColumnName = "Traffic_Light_ID")
    private TrafficLight tf2;

    public TrafficLight getTf1() {
        return tf1;
    }

    public void setTf1(TrafficLight tf1) {
        this.tf1 = tf1;
    }

    public TrafficLight getTf2() {
        return tf2;
    }

    public void setTf2(TrafficLight tf2) {
        this.tf2 = tf2;
    }
}
