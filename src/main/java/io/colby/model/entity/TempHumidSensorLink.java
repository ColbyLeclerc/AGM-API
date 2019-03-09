package io.colby.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

//@Component
//@Entity
//@Table(name="temp_humid_sensor_link")
public class TempHumidSensorLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soil_moisture_sensor_id", nullable = false, updatable = false)
    @OneToMany(mappedBy = "temp_humid_sensor_id")
    @JsonProperty("temp-humid-sensor-id")
    private int tempHumidSensorId;

    @Column(name = "plant_id")
    @JsonProperty("plant-id")
    private int plantId;

    @Column(name = "enclosure_id")
    @JsonProperty("enclosure-id")
    private int enclosureId;

    @Size(max = 255)
    @Column(name = "sensor_location")
    @JsonProperty("sensor-location")
    private String sensorLocation;

    @CreationTimestamp
    @Column(nullable = false, name = "insert_timestamp")
    @JsonProperty("created-date-time")
    private LocalDateTime insertTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    @JsonProperty("last-updated-date-time")
    private LocalDateTime updateTimestamp;

}
