package io.colby.modules.auth.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.colby.modules.routes.enclosures.model.entity.Enclosure;
import io.colby.modules.routes.plants.model.entity.Plant;
import io.colby.modules.routes.readings.model.entity.SoilMoistureReading;
import io.colby.modules.routes.readings.model.entity.SoilTempReading;
import io.colby.modules.routes.readings.model.entity.TempHumidReading;
import io.colby.modules.routes.sensors.model.entity.Sensor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//TODO add email/name
@Component
@Entity
@Table(name = "auth")
public class Auth {

    @Id
    @Column(name = "auth_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authId;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Size(max = 75)
    @Column(name = "first_name")
    @JsonProperty("first-name")
    private String firstName;

    @Size(max = 75)
    @Column(name = "last_name")
    @JsonProperty("last-name")
    private String lastName;

    @Size(max = 255)
    @Column(name = "email_addr")
    @JsonProperty("email-addr")
    private String emailAddr;

    @Size(max = 255)
    @Column(name = "password_hash")
    @JsonProperty("password-hash")
    @JsonIgnore
    private String passwordHash;

    @Size(max = 25)
    @Column(name = "salt")
    @JsonIgnore
    private String salt;

    @CreationTimestamp
    @Column(nullable = false, name = "create_timestamp")
    @JsonProperty("create-timestamp")
    private LocalDateTime insertTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp", nullable = false)
    @JsonProperty("update-timestamp")
    private LocalDateTime updateTimestamp;

//    NOTE: JsonIgnore is used to separate out the auth record response from the associated relations

    @OneToMany()
    @JoinColumn(name = "auth_id", referencedColumnName = "auth_id", foreignKey = @ForeignKey(name = "Fk_auth_plants"))
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Plant> plants = new ArrayList<>();

    @OneToMany()
    @JoinColumn(name = "auth_id", referencedColumnName = "auth_id", foreignKey = @ForeignKey(name = "Fk_auth_enclosures"))
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Enclosure> enclosures = new ArrayList<>();

    @OneToMany()
    @JoinColumn(name = "auth_id", referencedColumnName = "auth_id", foreignKey = @ForeignKey(name = "Fk_auth_sensors"))
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Sensor> sensors = new ArrayList<>();

    @OneToMany()
    @JoinColumn(name = "auth_id", referencedColumnName = "auth_id", foreignKey = @ForeignKey(name = "Fk_auth_temp_humid_reading"))
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<TempHumidReading> tempHumidReadings = new ArrayList<>();

    @OneToMany()
    @JoinColumn(name = "auth_id", referencedColumnName = "auth_id", foreignKey = @ForeignKey(name = "Fk_auth_soil_temp_reading"))
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<SoilTempReading> soilTempReadings = new ArrayList<>();

    @OneToMany()
    @JoinColumn(name = "auth_id", referencedColumnName = "auth_id", foreignKey = @ForeignKey(name = "Fk_auth_soil_moisture_reading"))
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<SoilMoistureReading> soilMoistureReadings = new ArrayList<>();


    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    public UUID getToken() {
        return UUID.fromString(token);
    }

    public void setToken(UUID token) {
        this.token = token.toString();
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

    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }

    public List<Enclosure> getEnclosures() {
        return enclosures;
    }

    public void setEnclosures(List<Enclosure> enclosures) {
        this.enclosures = enclosures;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "authId=" + authId +
                ", token='" + token + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddr='" + emailAddr + '\'' +
                ", passwordHashLength='" + (passwordHash == null ? 0 : passwordHash.length()) + '\'' +
                ", saltLength='" + (salt == null ? 0 : salt.length()) + '\'' +
                ", insertTimestamp=" + insertTimestamp +
                ", updateTimestamp=" + updateTimestamp +
                ", plants=" + plants +
                ", enclosures=" + enclosures +
                ", sensors=" + sensors +
                '}';
    }

    public List<TempHumidReading> getTempHumidReadings() {
        return tempHumidReadings;
    }

    public void setTempHumidReadings(List<TempHumidReading> tempHumidReadings) {
        this.tempHumidReadings = tempHumidReadings;
    }

    public List<SoilTempReading> getSoilTempReadings() {
        return soilTempReadings;
    }

    public void setSoilTempReadings(List<SoilTempReading> soilTempReadings) {
        this.soilTempReadings = soilTempReadings;
    }

    public List<SoilMoistureReading> getSoilMoistureReadings() {
        return soilMoistureReadings;
    }

    public void setSoilMoistureReadings(List<SoilMoistureReading> soilMoistureReadings) {
        this.soilMoistureReadings = soilMoistureReadings;
    }
}
