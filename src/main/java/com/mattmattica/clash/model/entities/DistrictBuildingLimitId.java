package com.mattmattica.clash.model.entities;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
@Embeddable
public class DistrictBuildingLimitId implements Serializable {
    private Integer districtEnumId;
    private Integer buildingEnumId;

    public DistrictBuildingLimitId() {
    }

    public DistrictBuildingLimitId(Integer districtEnumId, Integer buildingEnumId) {
        this.districtEnumId = districtEnumId;
        this.buildingEnumId = buildingEnumId;
    }
}
