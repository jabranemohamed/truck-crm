package io.mhj.truckcrm.repository;

import io.mhj.truckcrm.domain.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TruckRepository extends JpaRepository<Truck, UUID> {
}
