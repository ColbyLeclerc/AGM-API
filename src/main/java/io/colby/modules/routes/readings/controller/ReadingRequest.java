package io.colby.modules.routes.readings.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ReadingRequest {

    @JsonProperty("sensor-id")
    private int sensorId;
    @JsonProperty("time-recorded")
    private LocalDateTime timeRecorded;

    //SoilMoistureReading
    @JsonProperty("moisture-level")
    private double moistureLevel;
    @JsonProperty("moisture-level-units")
    private String moistureLevelUnits;

    //SoilTempReading
    @JsonProperty("temp-level")
    private double tempLevel;
    @JsonProperty("temp-scale")
    private String tempScale;

    //tempHumidReading
    private double humidity;
    @JsonProperty("humidity-units")
    private String humidityUnits;

    /**
     * Get the associated sensor id
     *
     * @return sensor id int
     */
    public int getSensorId() {
        return sensorId;
    }

    /**
     * Set the associated sensor id
     *
     * @param sensorId sensor id int
     */
    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    /**
     * Get the date-time reading was recorded
     *
     * @return date-time recorded
     */
    public LocalDateTime getTimeRecorded() {
        return timeRecorded;
    }

    /**
     * Sets the date-time reading was recorded
     *
     * @param timeRecorded date-time recorded
     */
    public void setTimeRecorded(LocalDateTime timeRecorded) {
        this.timeRecorded = timeRecorded;
    }

    /**
     * Gets the moisture level if applicable
     *
     * @return moisture level if applicable, 0 otherwise
     */
    public double getMoistureLevel() {
        return moistureLevel;
    }

    /**
     * Sets the moisture level if applicable
     *
     * @param moistureLevel moistureLevel
     */
    public void setMoistureLevel(double moistureLevel) {
        this.moistureLevel = moistureLevel;
    }

    /**
     * Get the units used to measure moisture, if applicable
     *
     * @return moisture level units
     */
    public String getMoistureLevelUnits() {
        return moistureLevelUnits;
    }

    /**
     * Set the moisture level units
     *
     * @param moistureLevelUnits Moisture level unit
     */
    public void setMoistureLevelUnits(String moistureLevelUnits) {
        this.moistureLevelUnits = moistureLevelUnits;
    }

    /**
     * Get the temp level if applicable
     *
     * @return temp level if applicable, 0.0 otherwise
     */
    public double getTempLevel() {
        return tempLevel;
    }

    /**
     * Set the temp level
     *
     * @param tempLevel temp level
     */
    public void setTempLevel(double tempLevel) {
        this.tempLevel = tempLevel;
    }

    /**
     * Get the temp scale used
     *
     * @return temp scale
     */
    public String getTempScale() {
        return tempScale;
    }

    /**
     * Set the temp scale
     *
     * @param tempScale temp scale
     */
    public void setTempScale(String tempScale) {
        this.tempScale = tempScale;
    }

    /**
     * Get the humidity if applicable
     *
     * @return humidity if applicable, 0.0 otherwise
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
     * Get the units used to measure the humidity
     *
     * @return humidity units
     */
    public String getHumidityUnits() {
        return humidityUnits;
    }

    /**
     * Sets the units used to measure the humidity
     *
     * @param humidityUnits humidity units
     */
    public void setHumidityUnits(String humidityUnits) {
        this.humidityUnits = humidityUnits;
    }
}
