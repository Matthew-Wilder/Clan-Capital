package com.mattmattica.clash.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class BuildingEnum implements Comparable<BuildingEnum> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private BuildingCategory category;
    private String name;

    @ManyToMany(mappedBy = "allowedBuildings")
    private Set<DistrictEnum> allowedInDistricts = new HashSet<>();

    @OneToMany(mappedBy = "buildingEnum")
    private Set<DistrictBuildingLimit> districtBuildingLimits = new HashSet<>();

    @OneToMany(mappedBy = "buildingEnum")
    private Set<BuildingUpgrade> upgrades = new HashSet<>();

    @OneToMany(mappedBy = "buildingEnum")
    private Set<BuildingInstance> instances = new HashSet<>();

    public BuildingUpgrade getMaxUpgrade() {
        var upgrades = this.upgrades.stream().toList();
        BuildingUpgrade currentMax = upgrades.get(0);
        for (var upgrade : upgrades) {
            if (upgrade.getLevel() > currentMax.getLevel()) {
                currentMax = upgrade;
            }
        }
        return currentMax;
    }

    public List<BuildingInstance> getSortedInstances() {
        return this.getInstances().stream().sorted().toList();
    }

    public List<BuildingUpgrade> sortedUpgrades() {
        return this.upgrades.stream().sorted().toList();
    }

    public List<BuildingInstance> getSortedInstances(DistrictEnum district) {
        return this.getInstances().stream().filter(b -> b.getDistrictEnum().equals(district)).sorted().toList();
    }

    private static final Comparator<BuildingEnum> COMPARATOR = Comparator
            .comparing(BuildingEnum::getCategory)
            .thenComparing(BuildingEnum::getId);

    public DistrictBuildingLimit getLimitInDistrict(DistrictEnum target) {
        for (var limit : districtBuildingLimits) {
            if(limit.getDistrictEnum().getId().equals(target.getId())) {
                return limit;
            }
        }
        return null;
    }



    @Override
    public int compareTo(BuildingEnum o) {
        return COMPARATOR.compare(this, o);
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BuildingEnum)) return false;
        final BuildingEnum other = (BuildingEnum) o;
        this.getId().compareTo(other.getId());
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BuildingEnum;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $category = this.getCategory();
        result = result * PRIME + ($category == null ? 43 : $category.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $allowedInDistricts = this.getAllowedInDistricts();
        result = result * PRIME + ($allowedInDistricts == null ? 43 : $allowedInDistricts.hashCode());
        final Object $districtBuildingLimits = this.getDistrictBuildingLimits();
        result = result * PRIME + ($districtBuildingLimits == null ? 43 : $districtBuildingLimits.hashCode());
        final Object $upgrades = this.getUpgrades();
        result = result * PRIME + ($upgrades == null ? 43 : $upgrades.hashCode());
        final Object $instances = this.getInstances();
        result = result * PRIME + ($instances == null ? 43 : $instances.hashCode());
        return result;
    }
}
