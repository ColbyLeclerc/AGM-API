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
    private Integer enclosureId;

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

    @OneToMany
    @JoinColumn(name = "enclosure_id", referencedColumnName = "enclosure_id")
    private List<Plant> plants;

    @OneToMany
    @JoinColumn(name = "enclosure_id", referencedColumnName = "plant_id")
    private List<Sensor> sensors;

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

    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEnclosureId() {
        return enclosureId;
    }

    public void setEnclosureId(Integer enclosureId) {
        this.enclosureId = enclosureId;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDimensionUnits() {
        return dimensionUnits;
    }

    public void setDimensionUnits(String dimensionUnits) {
        this.dimensionUnits = dimensionUnits;
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

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

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

}


