package io.colby.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

//@Component
//@Entity
//@Table(name="temp_humid_reading")
public class TempHumidReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "temp_humid_reading_id", nullable = false, updatable = false)
    @JsonProperty("temp-humid-reading-id")
    private int tempHumidReadingId;

    //Foreign key
    @Column(name = "temp_humid_sensor_id", nullable = false, updatable = false)
    @ManyToOne
    @JoinColumn(name = "temp_humid_sensor_id", nullable = false)
    @JsonProperty("temp-humid-sensor-id")
    private int tempHumidSensorId;

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
    private double humidityUnits;

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
