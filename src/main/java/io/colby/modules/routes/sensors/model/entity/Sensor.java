package io.colby.modules.routes.sensors.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import io.colby.modules.routes.sensors.entity.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@Component
@Entity
@Table(name = "sensor")
public class Sensor implements SensorResponse{

    private String message = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_id", nullable = false, updatable = false)
    @JsonProperty("sensor-id")
    private int sensorId;

    @Column(name = "auth_id")
    @JsonIgnore
    private int authId;

    @Column(name = "plant_id")
    @JsonProperty("plant-id")
    private int plantId;

    @Column(name = "enclosure_id")
    @JsonProperty("enclosure-id")
    private int enclosureId;

    @Column(name = "sensor_type", nullable = false)
    @JsonProperty("sensor-type")
    private String type;

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
        return SensorType.fromText(this.type);
    }

    public void setType(SensorType type) {
        this.type = type.toString();
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

    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Sensor.class.getSimpleName() + "[", "]")
                .add("\nsensorId=" + sensorId)
                .add("\nauthId=" + authId)
                .add("\nplantId=" + plantId)
                .add("\nenclosureId=" + enclosureId)
                .add("\ntype='" + type + "'")
                .add("\nisPlant=" + isPlant)
                .add("\nisEnclosure=" + isEnclosure)
                .add("\ninsertTimestamp=" + insertTimestamp)
                .add("\nupdateTimestamp=" + updateTimestamp)
                .toString();
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
