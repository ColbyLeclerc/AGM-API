package io.colby.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
//
//@Component
//@Entity
//@Table(name="soil_moisture_reading")
public class SoilMoistureReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soil_moisture_reading_id", nullable = false, updatable = false)
    @JsonProperty("soil-moisture-reading-id")
    private int soilMoistureReadingId;

    //Foreign key
    @ManyToOne
    @JoinColumn(name = "soil_moisture_sensor_id", nullable = false)
    @Column(name = "soilMoistureSensorId", nullable = false, updatable = false)
    @JsonProperty("soil-moisture-sensor-id")
    private int soilMoistureSensorId;

    @Column(name = "moisture_level", length = 5, precision = 2)
    @JsonProperty("moisture-level")
    private double moistureLevel;

    @Size(max = 25)
    @Column(name = "moisture_level_units")
    @JsonProperty("moisture-level-units")
    private String moistureLevelUnits;

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
