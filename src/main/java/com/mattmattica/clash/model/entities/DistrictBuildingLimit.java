package com.mattmattica.clash.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DistrictBuildingLimit {
    @EmbeddedId
    private DistrictBuildingLimitId id;

    @ManyToOne
    @MapsId("districtEnumId")
    @JoinColumn(name = "district_enum_id")
    private DistrictEnum districtEnum;

    @ManyToOne
    @MapsId("buildingEnumId")
    @JoinColumn(name = "building_enum_id")
    private BuildingEnum buildingEnum;

    private int maximumAmount;
}
