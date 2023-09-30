package io.mhj.truckcrm.repository;

import io.mhj.truckcrm.domain.JourneyStatus;
import io.mhj.truckcrm.domain.StatusCount;
import io.mhj.truckcrm.domain.TruckJourney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TruckJourneyRepository extends JpaRepository<TruckJourney, UUID> {

    public List<TruckJourney> findAllByActifIsTrue();



    @Query("SELECT new io.mhj.truckcrm.domain.StatusCount(c.status, COUNT(c.status)) "
            + "FROM TruckJourney AS c where c.actif = true GROUP BY c.status ORDER BY c.status DESC")
    List<StatusCount> countTotalJourneyByStatus();

}
