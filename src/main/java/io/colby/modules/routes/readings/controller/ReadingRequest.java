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

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public LocalDateTime getTimeRecorded() {
        return timeRecorded;
    }

    public void setTimeRecorded(LocalDateTime timeRecorded) {
        this.timeRecorded = timeRecorded;
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

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getHumidityUnits() {
        return humidityUnits;
    }

    public void setHumidityUnits(String humidityUnits) {
        this.humidityUnits = humidityUnits;
    }
}
