package io.mhj.truckcrm.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TruckJourney {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_driver")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "id_route")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "id_truck")
    private Truck truck;

    private Boolean actif;

    private LocalDate date;

    private LocalDate arriving_date;

    @Enumerated(EnumType.STRING)
    private JourneyStatus status;

    private Boolean arrived;

    private Integer travelDistance;

    private Integer courseDistance;

    private Integer changed;


}
