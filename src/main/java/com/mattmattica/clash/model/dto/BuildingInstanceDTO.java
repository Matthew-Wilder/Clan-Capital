package com.mattmattica.clash.model.dto;


import com.mattmattica.clash.model.dto.stats.Upgradable;
import lombok.Data;

@Data
public class BuildingInstanceDTO implements Upgradable {
    private int id;
    private BuildingDTO building;
    private DistrictDTO district;
    private UpgradeDTO upgrade;

    @Override
    public int getNumberOfUpgradesCompleted() {
        return upgrade.getLevel();
    }

    @Override
    public int getNumberOfUpgrades() {
        return building.getUpgrades().size();
    }

    public String toString() {
        return "BuildingInstanceDTO(id=" + this.getId() + ", building=" + this.getBuilding().getName() + ", district=" + this.getDistrict().getName() + ", upgrade=" + this.getUpgrade() + ")";
    }

    public String getCanonicalLevel() {
        return String.format("Level %s/%s", upgrade.getLevel(), getNumberOfUpgrades());
    }
}
