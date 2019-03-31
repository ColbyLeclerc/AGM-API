package io.colby.modules.routes.readings.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Component
@Entity
@Table(name="soil_temp_reading")
public class SoilTempReading implements Reading{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soil_temp_reading_id", nullable = false, updatable = false)
    @JsonProperty("soil-temperature-id")
    private int soilTempReadingId;

    //Foreign key
    @Column(name = "sensor_id", nullable = false, updatable = false)
    @JsonProperty("sensor-id")
    private int sensorId;

    @Column(name = "auth_id")
    @JsonIgnore
    private int authId;

    @Column(name = "temp_level", length = 5, precision = 2, nullable = false)
    @JsonProperty("temp-level")
    private double tempLevel;

    @Size(max = 1)
    @Column(name = "temp_scale", nullable = false)
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
    @Column(nullable = false, name = "update_timestamp")
    @JsonProperty("last-updated-date-time")
    private LocalDateTime updateTimestamp;

    public int getSoilTempReadingId() {
        return soilTempReadingId;
    }

    public void setSoilTempReadingId(int soilTempReadingId) {
        this.soilTempReadingId = soilTempReadingId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public double getTempLevel() {
        return tempLevel;
    }

    public void setTempLevel(double tempLevel) {
        this.tempLevel = tempLevel;
    }

    public String getTempScale() {
        return tempScale;
    }

    public void setTempScale(String tempScale) {
        this.tempScale = tempScale;
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

    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }
}
