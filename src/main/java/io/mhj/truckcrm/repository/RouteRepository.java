package io.mhj.truckcrm.repository;

import io.mhj.truckcrm.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RouteRepository extends JpaRepository<Route, UUID> {
}
