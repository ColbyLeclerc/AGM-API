package io.colby.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

//@Component
//@Entity
//@Table(name="soil_temp_sensor_link")
public class SoilTempSensorLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soil_moisture_sensor_id", nullable = false, updatable = false)
    @OneToMany(mappedBy = "soil_temp_sensor_id")
    @JsonProperty("soil-temp-sensor-id")
    private int soilTempSensorId;

    @Column(name = "plant_id")
    @JsonProperty("plant-id")
    private int plantId;

    @CreationTimestamp
    @Column(nullable = false, name = "insert_timestamp")
    @JsonProperty("created-date-time")
    private LocalDateTime insertTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    @JsonProperty("last-updated-date-time")
    private LocalDateTime updateTimestamp;

}
