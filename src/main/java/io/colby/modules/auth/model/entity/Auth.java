package io.colby.modules.auth.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.colby.modules.routes.enclosures.model.entity.Enclosure;
import io.colby.modules.routes.plants.model.entity.Plant;
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

//TODO add email/name
@Component
@Entity
@Table(name="auth")
public class Auth {

    @Id
    @Column(name = "auth_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authId;

    @Column(name = "token")
    private String token;

    @Size(max = 75)
    @Column(name = "first_name")
    @JsonProperty("first_name")
    private String firstName;

    @Size(max = 75)
    @Column(name = "last_name")
    @JsonProperty("last_name")
    private String lastName;

    @Size(max = 255)
    @Column(name = "email_addr")
    @JsonProperty("email_addr")
    private String emailAddr;

    @Size(max = 255)
    @Column(name = "password_hash")
    @JsonIgnore
    private String passwordHash;

    @Size(max = 25)
    @Column(name = "salt")
    @JsonIgnore
    private String salt;

    @CreationTimestamp
    @Column(nullable = false, name = "insert_timestamp")
    private LocalDateTime insertTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp", nullable = false)
    private LocalDateTime updateTimestamp;

    @OneToMany()
    @JoinColumn(name = "auth_id", referencedColumnName = "auth_id", foreignKey=@ForeignKey(name = "Fk_auth_plants"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Plant> plants = new ArrayList<>();

    @OneToMany()
    @JoinColumn(name = "auth_id", referencedColumnName = "auth_id", foreignKey=@ForeignKey(name = "Fk_auth_enclosures"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Enclosure> enclosures = new ArrayList<>();

    @OneToMany()
    @JoinColumn(name = "auth_id", referencedColumnName = "auth_id", foreignKey=@ForeignKey(name = "Fk_auth_sensors"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Sensor> sensors = new ArrayList<>();

    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
}
