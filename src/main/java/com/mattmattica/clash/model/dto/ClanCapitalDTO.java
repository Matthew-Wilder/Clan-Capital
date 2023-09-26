package com.mattmattica.clash.model.dto;

import com.mattmattica.clash.model.dto.stats.Completable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClanCapitalDTO implements Completable {
    private List<DistrictDTO> districts = new ArrayList<>();

    @Override
    public int getNumberOfUpgradesCompleted() {
        return districts.stream().mapToInt(DistrictDTO::getNumberOfUpgradesCompleted).sum();
    }

    @Override
    public int getNumberOfUpgrades() {
        return districts.stream().mapToInt(DistrictDTO::getNumberOfUpgrades).sum();
    }

    @Override
    public int getNumberOfBuildingsMaxed() {
        return districts.stream().mapToInt(DistrictDTO::getNumberOfBuildingsMaxed).sum();
    }

    @Override
    public int getNumberOfBuildings() {
        return districts.stream().mapToInt(DistrictDTO::getNumberOfBuildings).sum();
    }

    @Override
    public int getNumberOfDistrictsCompleted() {
        return districts.stream().filter(DistrictDTO::isMaxed).toList().size();
    }

    @Override
    public int getNumberOfDistricts() {
        return districts.size();
    }


    // Districts maxed x/8
    // Buildings Maxed y/1980
    // Upgrades Completd z / 50000?;

}
