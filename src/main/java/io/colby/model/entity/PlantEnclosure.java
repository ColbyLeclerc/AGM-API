package io.colby.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

//@Component
//@Entity
//@Table(name="plant_enclosure")
public class PlantEnclosure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("plant-enclosure-id")
    @Column(name = "plant_enclosure_id", nullable = false, updatable = false)
    private int plantEnclosureId;

    @Column(name = "enclosure_id")
    @JsonProperty("enclosure-id")
    private int enclosureId;

    @Column(name = "plant_id")
    @JsonProperty("plant-id")
    private int plantId;

    @CreationTimestamp
    @Column(nullable = false, name = "insert_timestamp")
    @JsonProperty("created-date-time")
    private LocalDateTime insertTimestamp;

    @UpdateTimestamp
    @JsonProperty("last-updated-date-time")
    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;


    public int getPlantEnclosureId() {
        return plantEnclosureId;
    }

    public void setPlantEnclosureId(int plantEnclosureId) {
        this.plantEnclosureId = plantEnclosureId;
    }

    public int getEnclosureId() {
        return enclosureId;
    }

    public void setEnclosureId(int enclosureId) {
        this.enclosureId = enclosureId;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
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
