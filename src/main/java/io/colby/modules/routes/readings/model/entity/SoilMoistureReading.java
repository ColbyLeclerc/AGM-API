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
@Table(name = "soil_moisture_reading")
public class SoilMoistureReading implements Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soil_moisture_reading_id", nullable = false, updatable = false)
    @JsonProperty("soil-moisture-reading-id")
    private int soilMoistureReadingId;

    @Column(name = "sensor_id", nullable = false)
    @JsonProperty("sensor-id")
    private int sensorId;

//    @Column(name = "soilMoistureSensorId", nullable = false, updatable = false)
//    @JsonProperty("soil-moisture-sensor-id")
//    private int soilMoistureSensorId;

    @Column(name = "moisture_level", length = 5, precision = 2, nullable = false)
    @JsonProperty("moisture-level")
    private double moistureLevel;

    @Column(name = "auth_id")
    @JsonIgnore
    private int authId;

    @Size(max = 25)
    @Column(name = "moisture_level_units", nullable = false)
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

    /**
     * Get the associated reading ID
     *
     * @return Soil Moisture Reading ID
     */
    public int getSoilMoistureReadingId() {
        return soilMoistureReadingId;
    }

    /**
     * Set the associated reading ID
     *
     * @param soilMoistureReadingId Reading ID
     */
    public void setSoilMoistureReadingId(int soilMoistureReadingId) {
        this.soilMoistureReadingId = soilMoistureReadingId;
    }

    /**
     * Get the ID of the sensor that created the reading
     *
     * @return Associated sensor ID
     */
    public int getSensorId() {
        return sensorId;
    }

    /**
     * Sets the associated sensor ID
     *
     * @param sensorId sensorId associated
     */
    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }
//
//    public int getSoilMoistureSensorId() {
//        return soilMoistureSensorId;
//    }
//
//    public void setSoilMoistureSensorId(int soilMoistureSensorId) {
//        this.soilMoistureSensorId = soilMoistureSensorId;
//    }

    /**
     * Get the moisture level of the reading
     *
     * @return moisture level
     */
    public double getMoistureLevel() {
        return moistureLevel;
    }

    /**
     * Sets the moisture level
     *
     * @param moistureLevel moisture level
     */
    public void setMoistureLevel(double moistureLevel) {
        this.moistureLevel = moistureLevel;
    }

    /**
     * Get the units used to measure the moisture
     *
     * @return measurement units for moisture level
     */
    public String getMoistureLevelUnits() {
        return moistureLevelUnits;
    }

    /**
     * Sets the units used to measure the moisture
     *
     * @param moistureLevelUnits measurement units for moisture level
     */
    public void setMoistureLevelUnits(String moistureLevelUnits) {
        this.moistureLevelUnits = moistureLevelUnits;
    }

    /**
     * Get the time the sensor recorded the reading
     *
     * @return time recorded
     */
    public LocalDateTime getTimeRecorded() {
        return timeRecorded;
    }

    /**
     * Sets the time the sensor recorded the reading
     *
     * @param timeRecorded time recorded
     */
    public void setTimeRecorded(LocalDateTime timeRecorded) {
        this.timeRecorded = timeRecorded;
    }

    /**
     * Time record was inserted into the database
     *
     * @return date-time record
     */
    public LocalDateTime getInsertTimestamp() {
        return insertTimestamp;
    }

    /**
     * Sets time record was inserted into the database (ORM usage)
     *
     * @param insertTimestamp date-time record
     */
    public void setInsertTimestamp(LocalDateTime insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    /**
     * Get time record was last updated
     *
     * @return date-time record
     */
    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    /**
     * Sets time record was last updated (ORM usage)
     *
     * @param updateTimestamp date-time record
     */
    public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    /**
     * Get associated auth id
     *
     * @return ID of associated Auth record
     */
    public int getAuthId() {
        return authId;
    }

    /**
     * Sets associated auth id
     *
     * @param authId ID of associated Auth record
     */
    public void setAuthId(int authId) {
        this.authId = authId;
    }
}
