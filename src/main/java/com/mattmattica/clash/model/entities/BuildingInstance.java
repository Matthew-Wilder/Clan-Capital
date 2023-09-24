package com.mattmattica.clash.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BuildingInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "district_enum_id")
    private DistrictEnum districtEnum;

    @ManyToOne
    @JoinColumn(name = "building_enum_id")
    private BuildingEnum buildingEnum;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "building_enum_id", referencedColumnName = "building_enum_id"),
            @JoinColumn(name = "level", referencedColumnName = "level")
    })
    private BuildingUpgrade buildingUpgrade;  // Represents the linked building_upgrade
}
