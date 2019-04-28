package io.colby.modules.routes.sensors.model.repository;

import io.colby.modules.routes.sensors.model.entity.Sensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorRepository extends CrudRepository<Sensor, Integer> {

    /**
     * Retrieves the Sensor record using the passed sensorId
     *
     * @param sensorId ID associated with the sensor
     * @return Sensor record
     */
    Optional<Sensor> findBySensorId(int sensorId);

}
