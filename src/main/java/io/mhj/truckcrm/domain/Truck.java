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
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    private String matricule;

    @Enumerated(EnumType.STRING)
    private TruckType type;

    private String modelYear;

    private String tire;

    private String cost;

    private String image;

    private String serie;

    @OneToMany(mappedBy = "truck", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<TruckJourney> truckJourney;

}
