package io.colby.modules.routes.readings.model.repository;

import io.colby.modules.routes.readings.model.entity.Reading;
import io.colby.modules.routes.readings.model.entity.TempHumidReading;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TempHumidReadingRepository extends CrudRepository<TempHumidReading, Integer> {


    /**
     * Retrieves TempHumidReading record using the passed
     * record id
     *
     * @param id temp humid reading id
     * @return SoilTempReading record
     */
    Optional<TempHumidReading> findByTempHumidReadingId(int id);


    /**
     * Returns all associated readings given the passed authId
     *
     * @param authId id associated with the Auth record
     * @return List of all associated readings
     */
    List<Reading> findAllByAuthId(int authId);

}
