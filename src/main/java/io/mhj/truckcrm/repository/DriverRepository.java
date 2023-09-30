package io.mhj.truckcrm.repository;

import io.mhj.truckcrm.domain.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {
}
