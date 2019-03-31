package io.colby.modules.routes.sensors.model.repository;

import io.colby.modules.routes.sensors.model.entity.Sensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorRepository extends CrudRepository<Sensor, Integer> {

    Optional<Sensor> findBySensorId(int sensorId);

}
