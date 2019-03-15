package io.colby.modules.routes.enclosures.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.colby.modules.routes.plants.model.entity.Plant;
import io.colby.modules.routes.sensors.model.entity.Sensor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//TODO for NotBlank fields, setup proper error handling for better messages

@Component
@Entity
@Table(name="enclosure")
public class Enclosure {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enclosure_id", nullable = false, updatable = false)
    @JsonProperty("enclosure-id")
    private int enclosureId;

    @Column(name = "title")
    @Size(max = 255)
    @NotBlank
    private String title;

    @Column(name = "location")
    @Size(max = 255)
    private String location;

    @Column(length = 25, name = "dimension_units")
    @Size(max = 25)
    @JsonProperty("dimension-units")
    private String dimensionUnits;

    @CreationTimestamp
    @Column(nullable = false, name = "insert_timestamp")
    @JsonProperty("created-date-time")
    private LocalDateTime insertTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    @JsonProperty("last-updated-date-time")
    private LocalDateTime updateTimestamp;


    @Column(length = 5, precision = 2)
    private double length;
    @Column(length = 5, precision = 2)
    private double width;
    @Column(length = 5, precision = 2)
    private double height;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "enclosure_id", referencedColumnName = "enclosure_id")
    private List<Plant> plants = new ArrayList<>();

//    @OneToMany
//    @JoinColumn(name = "enclosure_id", referencedColumnName = "plant_id")
//    private List<Sensor> sensors;

    public Enclosure(String title,
                     String location,
                     String dimensionUnits,
                     double length, double width, double height) {
        this.title = title;
        this.location = location;
        this.dimensionUnits = dimensionUnits;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public Enclosure(int enclosureId){
        this.enclosureId = enclosureId;
    }

    public Enclosure(){

    }

    /**
     * Get all plants within the enclosure. Empty if no plants exist.
     * @return ArrayList with populated plants. Empty ArrayList if none exist
     */
    public List<Plant> getPlants() {
        return plants;
    }

    /**
     * Sets the list of plants within the enclosure.
     *
     * @param plants List of plants
     */
    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }

    /**
     * Set the readable title of the enclosure
     * @param title String enclosure title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the unique id of the enclosure
     * @return int unique id of the enclosure
     */
    public int getEnclosureId() {
        return enclosureId;
    }

    /**
     * Sets the unique id of the enclosure.
     *
     * @param enclosureId int unique id of the enclosure
     */
    public void setEnclosureId(int enclosureId) {
        this.enclosureId = enclosureId;
    }

    /**
     * The readable title of the enclosure
     * @return Title of the enclosure
     */
    public String getTitle() {
        return title;
    }

    /**
     * Location of the enclosure as a String.
     * @return Enclosure's location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the enclosure
     * @param location Enclosure's location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    //TODO change to ENUM
    /**
     * Measuring units used for the length, width, and height. Can be one of the following:
     * FEET, INCHES, YARDS, METERS, CENTIMETERS
     *
     * @return The measuring unit for length, width, and height
     */
    public String getDimensionUnits() {
        return dimensionUnits;
    }

    /**
     * Sets the measuring units used for the length, width, and height.
     * Can be one of the following: FEET, INCHES, YARDS, METERS, CENTIMETERS
     *
     * Represents the units of measurements for the Length, Width,
     * and Height
     *
     * @param dimensionUnits The measuring unit for length, width, and height
     */
    public void setDimensionUnits(String dimensionUnits) {
        this.dimensionUnits = dimensionUnits;
    }

    /**
     * The time the record was created
     * @return LocalDateTime record was created
     */
    public LocalDateTime getInsertTimestamp() {
        return insertTimestamp;
    }

    /**
     * Set the insert time for the enclosure
     * @param insertTimestamp LocalDateTime time enclosure was created
     */
    public void setInsertTimestamp(LocalDateTime insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    /**
     * The time the record was updated
     * @return LocalDateTime record was updated
     */
    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    /**
     * Set the last update time for the enclosure
     * @param updateTimestamp LocalDateTime last time enclosure was updated
     */
    public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    /**
     * Length of the enclosure.
     *
     * @return double enclosure length
     */
    public double getLength() {
        return length;
    }

    /**
     * Set the length of the enclosure
     * @param length double enclosure length
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Width of the enclosure.
     *
     * @return double enclosure width
     */
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Height of the enclosure.
     *
     * @return double enclosure height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Set the height of the enclosure
     * @param height double enclosure height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Enclosure{" +
                "enclosureId=" + enclosureId +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", dimensionUnits='" + dimensionUnits + '\'' +
                ", insertTimestamp=" + insertTimestamp +
                ", updateTimestamp=" + updateTimestamp +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

//    public List<Sensor> getSensors() {
//        return sensors;
//    }
//
//    public void setSensors(List<Sensor> sensors) {
//        this.sensors = sensors;
//    }
}


