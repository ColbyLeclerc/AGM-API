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
public class Sensor {

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


    /**
     * Get the associated sensor ID
     *
     * @return The associated sensor ID
     */
    public int getSensorId() {
        return sensorId;
    }

    /**
     * Sets the associated sensor ID
     *
     * @param sensorId Sensor ID
     */
    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    /**
     * Get the SensorType enum (type of sensor)
     *
     * @return Sensor type enum
     */
    public SensorType getType() {
        return SensorType.fromText(this.type);
    }

    /**
     * Sets the SensorType enum of the sensor
     *
     * @param type Type of sensor
     */
    public void setType(SensorType type) {
        this.type = type.toString();
    }

    /**
     * Returns true if sensor belongs to a plant, false otherwise
     *
     * @return true if sensor belongs to a plant, false otherwise
     */
    public boolean isPlant() {
        return isPlant;
    }

    /**
     * Sets if sensor belongs to a plant
     *
     * @param plant is plant sensor
     */
    public void setPlant(boolean plant) {
        isPlant = plant;
    }

    /**
     * Returns true if sensor belongs to an enclosure, false otherwise
     *
     * @return true if sensor belongs to an enclosure, false otherwise
     */
    public boolean isEnclosure() {
        return isEnclosure;
    }

    /**
     * Sets if sensor belongs to an enclosure
     *
     * @param enclosure
     */
    public void setEnclosure(boolean enclosure) {
        isEnclosure = enclosure;
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
     * If sensor is associated with a plant, returns the associated plant id
     *
     * @return plant id
     */
    public int getPlantId() {
        return plantId;
    }

    /**
     * Sets the plant id associated with the sensor
     *
     * @param plantId plant id
     */
    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    /**
     * Returns the enclosure id the sensor is contained within (regardless of sensor type)
     *
     * @return enclosure id
     */
    public int getEnclosureId() {
        return enclosureId;
    }

    /**
     * Sets the enclosure id associated with the sensor
     *
     * @param enclosureId enclosure id
     */
    public void setEnclosureId(int enclosureId) {
        this.enclosureId = enclosureId;
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
}
