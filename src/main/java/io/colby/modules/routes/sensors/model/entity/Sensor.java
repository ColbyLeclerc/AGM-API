package io.colby.modules.routes.sensors.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.colby.modules.routes.sensors.entity.SensorType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

@Component
@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_id", nullable = false, updatable = false)
    @JsonProperty("sensor-id")
    private int sensorId;

    @Column(name = "plant_id", nullable = true)
    @JsonProperty("plant-id")
    private int plantId;

    @Column(name = "enclosure_id", nullable = true)
    @JsonProperty("enclosure-id")
    private int enclosureId;

    @Column(name = "type", nullable = false)
    private SensorType type;

    @Column(name = "is_plant", nullable = false)
    @JsonProperty("is-plant")
    private boolean isPlant;

    @Column(name = "is_enclosure", nullable = false)
    @JsonProperty("is-enclosure")
    private boolean isEnclosure;

    @CreationTimestamp
    @Column(nullable = false, name = "insert_timestamp")
    @JsonProperty("created-date-time")
    private LocalDateTime insertTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp", nullable = false)
    @JsonProperty("last-updated-date-time")
    private LocalDateTime updateTimestamp;



    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {
        this.type = type;
    }

    public boolean isPlant() {
        return isPlant;
    }

    public void setPlant(boolean plant) {
        isPlant = plant;
    }

    public boolean isEnclosure() {
        return isEnclosure;
    }

    public void setEnclosure(boolean enclosure) {
        isEnclosure = enclosure;
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

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public int getEnclosureId() {
        return enclosureId;
    }

    public void setEnclosureId(int enclosureId) {
        this.enclosureId = enclosureId;
    }
}
