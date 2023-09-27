package com.mattmattica.clash.model.dto;

import com.mattmattica.clash.model.dto.stats.Buildable;
import com.mattmattica.clash.model.dto.stats.PercentageData;
import com.mattmattica.clash.util.MathUtils;
import lombok.Data;
import org.hibernate.boot.jaxb.mapping.ManagedType;

import java.util.ArrayList;
import java.util.List;

@Data
public class DistrictDTO implements Buildable {
    private int id;
    private String name;
    private List<BuildingInstanceDTO> buildingInstances = new ArrayList<>();
    private List<BuildingDTO> availableBuildings = new ArrayList<>();


    public List<BuildingInstanceDTO> filterBuildingInstances(BuildingDTO matchBuilding) {
        return this.buildingInstances.stream().filter(b -> b.getBuilding().equals(matchBuilding)).toList();
    }

    public int getNumberOfUpgradesCompleted(BuildingDTO matchBuilding) {
        return filterBuildingInstances(matchBuilding).stream().mapToInt(BuildingInstanceDTO::getNumberOfUpgradesCompleted).sum();
    }

    public int getNumberOfUpgrades(BuildingDTO matchBuilding) {
        return filterBuildingInstances(matchBuilding).stream().mapToInt(BuildingInstanceDTO::getNumberOfUpgrades).sum();
    }

    public PercentageData getPercentageOfUpgradesCompleted(BuildingDTO matchBuilding) {
        return new PercentageData(getNumberOfUpgradesCompleted(matchBuilding), getNumberOfUpgrades(matchBuilding));
    }

    public PercentageData getCostPercentageData(BuildingDTO matchBuilding) {
        PercentageData percentageData = new PercentageData();
        filterBuildingInstances(matchBuilding).forEach(e -> percentageData.add(e.getCostPercentageData()));
        return percentageData;
    }

    public PercentageData getCostPercentageData() {
        PercentageData percentageData = new PercentageData();
        buildingInstances.forEach(e -> percentageData.add(e.getCostPercentageData()));
        return percentageData;
    }

    public PercentageData getUpgradePercentageData() {
        PercentageData percentageData = new PercentageData();
        buildingInstances.forEach(e -> percentageData.add(e.getUpgradePercentageData()));
        return percentageData;
    }

    @Override
    public int getNumberOfUpgradesCompleted() {
        return buildingInstances.stream().mapToInt(BuildingInstanceDTO::getNumberOfUpgradesCompleted).sum();
    }

    @Override
    public int getNumberOfUpgrades() {
        return buildingInstances.stream().mapToInt(BuildingInstanceDTO::getNumberOfUpgrades).sum();
    }

    @Override
    public int getNumberOfBuildingsMaxed() {
        return buildingInstances.stream().filter(BuildingInstanceDTO::isMaxed).toList().size();
    }

    @Override
    public int getNumberOfBuildings() {
        return buildingInstances.size();
    }


}
