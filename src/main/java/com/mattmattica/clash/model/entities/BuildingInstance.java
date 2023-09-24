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
    private DistrictEnum districtEnum;

    @ManyToOne
    private BuildingEnum buildingEnum;

    @ManyToOne
    private BuildingUpgrade buildingUpgrade;
}