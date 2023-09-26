package com.mattmattica.clash.model.dto;

import com.mattmattica.clash.model.dto.stats.Buildable;
import com.mattmattica.clash.util.MathUtils;
import lombok.Data;

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
