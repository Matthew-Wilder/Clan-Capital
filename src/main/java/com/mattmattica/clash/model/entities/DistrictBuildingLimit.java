package com.mattmattica.clash.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DistrictBuildingLimit {
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name="districtEnumId", column = @Column(name="district_enum_id")),
            @AttributeOverride(name="buildingEnumId", column = @Column(name="building_enum_id"))
    })
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
