package io.colby.modules.auth.service;

import io.colby.modules.auth.model.entity.Auth;
import io.colby.modules.auth.model.repository.AuthRepository;
import io.colby.modules.routes.enclosures.model.entity.Enclosure;
import io.colby.modules.routes.enclosures.repository.EnclosureRepository;
import io.colby.modules.routes.plants.model.entity.Plant;
import io.colby.modules.routes.readings.model.entity.SoilMoistureReading;
import io.colby.modules.routes.readings.model.entity.SoilTempReading;
import io.colby.modules.routes.readings.model.entity.TempHumidReading;
import io.colby.modules.routes.readings.model.repository.SoilMoistureReadingRepository;
import io.colby.modules.routes.readings.model.repository.SoilTempReadingRepository;
import io.colby.modules.routes.readings.model.repository.TempHumidReadingRepository;
import io.colby.modules.routes.sensors.entity.SensorType;
import io.colby.modules.routes.sensors.model.entity.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements AuthServiceI {


    @Autowired
    AuthRepository authRepository;

    @Autowired
    EnclosureRepository enclosureRepository;

    @Autowired
    SoilMoistureReadingRepository soilMoistureRepository;

    @Autowired
    SoilTempReadingRepository soilTempRepository;

    @Autowired
    TempHumidReadingRepository tempHumidRepository;

    /**
     * Get Auth record from token
     *
     * @param token Authorization string from header
     * @return Auth record
     */
    @Override
    public Optional<Auth> getFromToken(String token) {

        return authRepository.findAuthByToken(token);
    }

    /**
     * Checks if Auth record tied to user has access to enclosure
     *
     * @param auth        Auth record associated with user
     * @param enclosureId enclosureId to check
     * @return true if user has access, false otherwise
     */
    @Override
    public boolean userHasAccessToEnclosure(Auth auth, int enclosureId) {

        Optional<Enclosure> enclosure = auth.getEnclosures().stream()
                .filter(x -> x.getEnclosureId() == enclosureId).findFirst();

        return enclosure.isPresent();
    }

    /**
     * Checks if Auth record tied to user has access to plant
     *
     * @param auth    Auth record associated with user
     * @param plantId plantId to check
     * @return true if user has access, false otherwise
     */
    @Override
    public boolean userHasAccessToPlant(Auth auth, int plantId) {

        Optional<Plant> plant = auth.getPlants().stream()
                .filter(x -> x.getPlantId() == plantId).findFirst();

        return plant.isPresent();
    }

    /**
     * Checks if Auth record tied to user has access to sensor
     *
     * @param auth     Auth record associated with user
     * @param sensorId sensorId to check
     * @return true if user has access, false otherwise
     */
    @Override
    public boolean userHasAccessToSensor(Auth auth, int sensorId) {

        Optional<Sensor> sensor = auth.getSensors().stream()
                .filter(x -> x.getSensorId() == sensorId).findFirst();

        return sensor.isPresent();
    }

    /**
     * Checks if Auth record tied to user has access to reading
     *
     * @param auth      Auth record associated with user
     * @param readingId readingId to check
     * @return true if user has access, false otherwise
     */
    @Override
    public boolean userHasAccessToReading(Auth auth, SensorType sensorType, int readingId) {

        //For each case, find the first result that equals the passed readingId
        switch (sensorType) {
            case TEMPERATURE_HUMIDITY:
                Optional<TempHumidReading> tempHumidReading = auth.getTempHumidReadings().stream()
                        .filter(x -> x.getTempHumidReadingId() == readingId).findFirst();
                return tempHumidReading.isPresent();
            case SOIL_MOISTURE:
                Optional<SoilMoistureReading> soilMoistureReading = auth.getSoilMoistureReadings().stream()
                        .filter(x -> x.getSoilMoistureReadingId() == readingId).findFirst();
                return soilMoistureReading.isPresent();
            case SOIL_TEMPERATURE:
                Optional<SoilTempReading> soilTempReading = auth.getSoilTempReadings().stream()
                        .filter(x -> x.getSoilTempReadingId() == readingId).findFirst();
                return soilTempReading.isPresent();
            default:
                return false;
        }

    }

}
