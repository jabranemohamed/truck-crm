package io.mhj.truckcrm.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    private String departureCity;

    private String targetCity;

    @OneToMany(mappedBy = "route", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<TruckJourney> truckJourney;

}
