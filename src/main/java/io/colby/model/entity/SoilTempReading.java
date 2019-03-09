package io.colby.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

//@Component
//@Entity
//@Table(name="soil_temp_reading")
public class SoilTempReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soil_temp_reading_id", nullable = false, updatable = false)
    @JsonProperty("soil-temperature-id")
    private int soilTempReadingId;

    //Foreign key
    @Column(name = "soil_moisture_sensor_id", nullable = false, updatable = false)
    @ManyToOne
    @JoinColumn(name = "soil_temp_sensor_id", nullable = false)
    @JsonProperty("soil-temp-sensor-id")
    private int soilTempSensorId;

    @Column(name = "temp_level", length = 5, precision = 2)
    @JsonProperty("temp-level")
    private double tempLevel;

    @Size(max = 1)
    @Column(name = "temp_scale")
    @JsonProperty("temp-scale")
    private String tempScale;

    @Column(name = "time_recorded")
    @JsonProperty("time-recorded")
    private LocalDateTime timeRecorded;

    @CreationTimestamp
    @Column(nullable = false, name = "insert_timestamp")
    @JsonProperty("created-date-time")
    private LocalDateTime insertTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    @JsonProperty("last-updated-date-time")
    private LocalDateTime updateTimestamp;

}
