package io.colby.modules.auth.service;

import io.colby.modules.auth.model.entity.Auth;
import io.colby.modules.routes.sensors.entity.SensorType;

import java.util.Optional;

//@Service
public interface AuthServiceI {


    /**
     * Get Auth record from token
     *
     * @param token Authorization string from header
     * @return Auth record
     */
    Optional<Auth> getFromToken(String token);

    /**
     * Checks if Auth record tied to user has access to enclosure
     *
     * @param auth        Auth record associated with user
     * @param enclosureId enclosureId to check
     * @return true if user has access, false otherwise
     */
    boolean userHasAccessToEnclosure(Auth auth, int enclosureId);

    /**
     * Checks if Auth record tied to user has access to plant
     *
     * @param auth    Auth record associated with user
     * @param plantId plantId to check
     * @return true if user has access, false otherwise
     */
    boolean userHasAccessToPlant(Auth auth, int plantId);

    /**
     * Checks if Auth record tied to user has access to sensor
     *
     * @param auth     Auth record associated with user
     * @param sensorId sensorId to check
     * @return true if user has access, false otherwise
     */
    boolean userHasAccessToSensor(Auth auth, int sensorId);

    /**
     * Checks if Auth record tied to user has access to reading
     *
     * @param auth      Auth record associated with user
     * @param readingId readingId to check
     * @return true if user has access, false otherwise
     */
    boolean userHasAccessToReading(Auth auth, SensorType sensorType, int readingId);
}
