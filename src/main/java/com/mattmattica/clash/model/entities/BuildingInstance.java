package com.mattmattica.clash.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;

@Getter
@Setter
@Entity
public class BuildingInstance implements Comparable<BuildingInstance> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private DistrictEnum districtEnum;

    @ManyToOne
    private BuildingEnum buildingEnum;

    @ManyToOne
    private BuildingUpgrade buildingUpgrade;

    private static final Comparator<BuildingInstance> COMPARATOR = Comparator
            .comparing(BuildingInstance::getBuildingUpgrade)
            .thenComparing(BuildingInstance::getId);

    @Override
    public int compareTo(BuildingInstance o) {
        return COMPARATOR.compare(this, o);
    }
}