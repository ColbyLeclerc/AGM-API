package io.colby.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Component
@Entity
@Table(name="plant")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id", nullable = false, updatable = false)
    @JsonProperty("plant-id")
    private int plantId;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Column(name = "pot_size")
    @JsonProperty("pot-size")
    private int potSize;

    @Size(max = 25)
    @Column(name = "pot_size_units")
    @JsonProperty("pot-size-units")
    private String potSizeUnits;

    @Column(name = "yield", length = 5, precision = 2)
    private double yield;

    @Size(max = 25)
    @Column(name = "yield_units")
    @JsonProperty("yield-units")
    private String yieldUnits;

    @Column(name = "date_harvested")
    @JsonProperty("date-harvested")
    private LocalDateTime dateHarvested;

    @Column(name = "date_planted")
    @JsonProperty("date-planted")
    private LocalDateTime datePlanted;

    @CreationTimestamp
    @Column(nullable = false, name = "insert_timestamp")
    @JsonProperty("created-date-time")
    private LocalDateTime insertTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    @JsonProperty("last-updated-date-time")
    private LocalDateTime updateTimestamp;


    @ManyToOne
    @JoinColumn(name = "enclosure_id")
    private Enclosure enclosure;

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPotSize() {
        return potSize;
    }

    public void setPotSize(int potSize) {
        this.potSize = potSize;
    }

    public String getPotSizeUnits() {
        return potSizeUnits;
    }

    public void setPotSizeUnits(String potSizeUnits) {
        this.potSizeUnits = potSizeUnits;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }

    public String getYieldUnits() {
        return yieldUnits;
    }

    public void setYieldUnits(String yieldUnits) {
        this.yieldUnits = yieldUnits;
    }

    public LocalDateTime getDateHarvested() {
        return dateHarvested;
    }

    public void setDateHarvested(LocalDateTime dateHarvested) {
        this.dateHarvested = dateHarvested;
    }

    public LocalDateTime getDatePlanted() {
        return datePlanted;
    }

    public void setDatePlanted(LocalDateTime datePlanted) {
        this.datePlanted = datePlanted;
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
