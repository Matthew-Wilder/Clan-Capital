package com.mattmattica.clash.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class BuildingEnum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "allowedBuildings")
    private Set<DistrictEnum> allowedInDistricts = new HashSet<>();

    @OneToMany(mappedBy = "buildingEnum")
    private Set<DistrictBuildingLimit> districtBuildingLimits = new HashSet<>();

    @OneToMany(mappedBy = "buildingEnum")
    private Set<BuildingUpgrade> upgrades = new HashSet<>();

    @OneToMany(mappedBy = "buildingEnum")
    private Set<BuildingInstance> instances = new HashSet<>();
}
