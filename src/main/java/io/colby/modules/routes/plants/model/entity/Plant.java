package io.colby.modules.routes.plants.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.colby.modules.routes.sensors.model.entity.Sensor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Entity
@Table(name="plant")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id", nullable = false, updatable = false)
    @JsonProperty("plant-id")
    private int plantId;

    @Column(name = "enclosure_id")
    private int enclosureId;

    @Column(name = "auth_id")
    @JsonIgnore
    private int authId;

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
    @Column(name = "update_timestamp", nullable = false)
    @JsonProperty("last-updated-date-time")
    private LocalDateTime updateTimestamp;

    @OneToMany
    @JoinColumn(name = "plant_id", referencedColumnName = "plant_id", foreignKey=@ForeignKey(name = "Fk_plant_sensors"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Sensor> sensors;

    public Plant(int plantId){
        this.plantId = plantId;
    }

    public Plant() {

    }

    /**
     * Retrieve the enclosure id of the Enclosure the plant resides within
     * @return int enclosureId
     */
    public int getEnclosureId() {
        return enclosureId;
    }

    /**
     * Set the enclosure id of the Enclosure the plant resides within
     * @param enclosureId int enclosureId
     */
    public void setEnclosureId(int enclosureId) {

        this.enclosureId = enclosureId;
    }

    /**
     * Retrieve the unique plant identifier
     * @return int plantId
     */
    public int getPlantId() {
        return plantId;
    }

    /**
     * Set the unique plant identifier
     * @param plantId int plantId
     */
    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    /**
     * Retrieve the readable name of the plant
     * @return String plant name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the readable name of the plant
     * @param name String plant name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the magnitude of the pot size
     * @return integer size of pot
     */
    public int getPotSize() {
        return potSize;
    }

    /**
     * Set the magnitude of the pot size
     * @param potSize int size of pot
     */
    public void setPotSize(int potSize) {
        this.potSize = potSize;
    }

    /**
     * Retrieve the units used to measure the pot size (see <code>getPotSize()</code>)
     * @return The units used to measure the pot size
     */
    public String getPotSizeUnits() {
        return potSizeUnits;
    }

    /**
     * Set the units used to measure the pot size (see <code>getPotSize()</code>)
     * @param potSizeUnits
     */
    public void setPotSizeUnits(String potSizeUnits) {
        this.potSizeUnits = potSizeUnits;
    }

    /**
     * Returns the yield of the crop
     * @return double crop yield
     */
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

    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "\nplantId=" + plantId +
                ", \nenclosureId=" + enclosureId +
                ", \nauthId=" + authId +
                ", \nname='" + name + '\'' +
                ", \npotSize=" + potSize +
                ", \npotSizeUnits='" + potSizeUnits + '\'' +
                ", \nyield=" + yield +
                ", \nyieldUnits='" + yieldUnits + '\'' +
                ", \ndateHarvested=" + dateHarvested +
                ", \ndatePlanted=" + datePlanted +
                ", \ninsertTimestamp=" + insertTimestamp +
                ", \nupdateTimestamp=" + updateTimestamp +
                ", \nsensors=" + sensors +
                '}';
    }
}
