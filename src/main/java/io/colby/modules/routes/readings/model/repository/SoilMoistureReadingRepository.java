package io.colby.modules.routes.readings.model.repository;

import io.colby.modules.routes.readings.model.entity.Reading;
import io.colby.modules.routes.readings.model.entity.SoilMoistureReading;
import io.colby.modules.routes.readings.model.entity.SoilTempReading;
import io.colby.modules.routes.readings.model.entity.TempHumidReading;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoilMoistureReadingRepository extends CrudRepository<SoilMoistureReading, Integer> {

    Optional<SoilMoistureReading> findBySoilMoistureReadingId(int id);


}
