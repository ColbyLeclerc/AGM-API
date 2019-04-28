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
@Table(name = "temp_humid_reading")
public class TempHumidReading implements Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "temp_humid_reading_id", nullable = false, updatable = false)
    @JsonProperty("temp-humid-reading-id")
    private int tempHumidReadingId;

    @Column(nullable = false, name = "sensor_id")
    @JsonProperty("sensor-id")
    private int sensorId;

    @Column(name = "auth_id")
    @JsonIgnore
    private int authId;

    @Column(name = "temp_level", length = 5, precision = 2)
    @JsonProperty("temp-level")
    private double tempLevel;

    @Size(max = 1)
    @Column(name = "temp_scale")
    @JsonProperty("temp-scale")
    private String tempScale;

    @Column(name = "humidity", length = 5, precision = 2)
    private double humidity;

    @Size(max = 20)
    @Column(name = "humidity_units")
    @JsonProperty("humidity-units")
    private String humidityUnits;

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
     * @return Temperature Humidity Reading ID
     */
    public int getTempHumidReadingId() {
        return tempHumidReadingId;
    }

    /**
     * Set the associated reading ID
     *
     * @param tempHumidReadingId Reading ID
     */
    public void setTempHumidReadingId(int tempHumidReadingId) {
        this.tempHumidReadingId = tempHumidReadingId;
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

    /**
     * Get the temp level of the reading
     *
     * @return temp level
     */
    public double getTempLevel() {
        return tempLevel;
    }

    /**
     * Sets the temp level
     *
     * @param tempLevel temp level
     */
    public void setTempLevel(double tempLevel) {
        this.tempLevel = tempLevel;
    }

    /**
     * Gets the scale used to measure the temp
     *
     * @return temp scale
     */
    public String getTempScale() {
        return tempScale;
    }

    /**
     * Sets the scale used to measure the temp
     *
     * @param tempScale temp scale
     */
    public void setTempScale(String tempScale) {
        this.tempScale = tempScale;
    }

    /**
     * Get the humidity level
     *
     * @return humidity level
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * Set the humidity level
     *
     * @param humidity humidity level
     */
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    /**
     * Get the measurement units for the humidity
     *
     * @return measurement units
     */
    public String getHumidityUnits() {
        return humidityUnits;
    }

    /**
     * Set the humidity units for measurement
     *
     * @param humidityUnits humidity units
     */
    public void setHumidityUnits(String humidityUnits) {
        this.humidityUnits = humidityUnits;
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
