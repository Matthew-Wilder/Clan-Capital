package com.mattmattica.clash.model.dto;


import com.mattmattica.clash.model.dto.stats.PercentageData;
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

    public PercentageData getUpgradePercentageData() {
//         TODO optimize
        return new PercentageData(getNumberOfUpgradesCompleted(), getNumberOfUpgrades());
    }

    public boolean hasUpgrade(UpgradeDTO upgrade) {
        return upgrade.getLevel() <= this.upgrade.getLevel();
    }

    public PercentageData getCostPercentageData() {
        int amountSpent = 0;
        int totalAmount = 0;
        for (UpgradeDTO upgrade : building.getUpgrades()) {
            totalAmount += upgrade.getCost();
            if (hasUpgrade(upgrade)) {
                amountSpent += upgrade.getCost();
            }
        }
        return new PercentageData(amountSpent, totalAmount);
    }
}
