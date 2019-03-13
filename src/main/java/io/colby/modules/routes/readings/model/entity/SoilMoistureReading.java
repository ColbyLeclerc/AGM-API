package io.colby.modules.routes.readings.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Component
@Entity
@Table(name="soil_moisture_reading")
public class SoilMoistureReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soil_moisture_reading_id", nullable = false, updatable = false)
    @JsonProperty("soil-moisture-reading-id")
    private int soilMoistureReadingId;

    @Column(name = "sensor_id", nullable = false)
    @JsonProperty("sensor-id")
    private int sensorId;

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
    @Column(nullable = false, name = "update_timestamp")
    @JsonProperty("last-updated-date-time")
    private LocalDateTime updateTimestamp;

    public int getSoilMoistureReadingId() {
        return soilMoistureReadingId;
    }

    public void setSoilMoistureReadingId(int soilMoistureReadingId) {
        this.soilMoistureReadingId = soilMoistureReadingId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public int getSoilMoistureSensorId() {
        return soilMoistureSensorId;
    }

    public void setSoilMoistureSensorId(int soilMoistureSensorId) {
        this.soilMoistureSensorId = soilMoistureSensorId;
    }

    public double getMoistureLevel() {
        return moistureLevel;
    }

    public void setMoistureLevel(double moistureLevel) {
        this.moistureLevel = moistureLevel;
    }

    public String getMoistureLevelUnits() {
        return moistureLevelUnits;
    }

    public void setMoistureLevelUnits(String moistureLevelUnits) {
        this.moistureLevelUnits = moistureLevelUnits;
    }

    public LocalDateTime getTimeRecorded() {
        return timeRecorded;
    }

    public void setTimeRecorded(LocalDateTime timeRecorded) {
        this.timeRecorded = timeRecorded;
    }

    public LocalDateTime getInsertTimestamp() {
        return insertTimestamp;
    }

    public void setInsertTimestamp(LocalDateTime insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}
