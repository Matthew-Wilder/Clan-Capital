package com.mattmattica.clash.controllers;

import com.mattmattica.clash.model.entities.BuildingEnum;
import com.mattmattica.clash.model.entities.BuildingInstance;
import com.mattmattica.clash.model.entities.BuildingUpgrade;
import com.mattmattica.clash.model.entities.DistrictEnum;
import com.mattmattica.clash.model.repositories.BuildingEnumRepository;
import com.mattmattica.clash.model.repositories.BuildingInstanceRepository;
import com.mattmattica.clash.model.repositories.BuildingUpgradeRepository;
import com.mattmattica.clash.model.repositories.DistrictEnumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
public class BuildingUpgradeController {

    @Autowired private BuildingInstanceRepository buildingInstanceRepository;
    @Autowired private BuildingUpgradeRepository upgradeRepository;
    @Autowired private BuildingEnumRepository buildingEnumRepository;
    @Autowired private DistrictEnumRepository districtEnumRepository;

    @RequestMapping(method=RequestMethod.PUT, path="/upgrade-building/{buildingId}/{upgradeId}")
    public ResponseEntity<?> setBuildingLevel(
            @PathVariable Integer buildingId,
            @PathVariable Integer upgradeId) {
        BuildingInstance buildingInstance = buildingInstanceRepository.findById(buildingId).get();
        BuildingUpgrade upgrade = upgradeRepository.findById(upgradeId).get();
        buildingInstance.setBuildingUpgrade(upgrade);
        buildingInstanceRepository.save(buildingInstance);
        return ResponseEntity.ok("Upgraded");
    }

    @GetMapping("/max-district")
    public ResponseEntity<?> maxDistrict(@RequestParam("districtId") Integer districtId) {
        DistrictEnum districtEnum = districtEnumRepository.findById(districtId).get();
        List<BuildingInstance> instances = buildingInstanceRepository.findAllByDistrictEnum(districtEnum);
        for (BuildingInstance instance : instances) {
            BuildingUpgrade maxUpgrade = instance.getBuildingEnum().getMaxUpgrade();
            instance.setBuildingUpgrade(maxUpgrade);
        }
        buildingInstanceRepository.saveAll(instances);
        return ResponseEntity.ok("");
    }


    @GetMapping("/upgrade-walls")
    public ResponseEntity<?> maxDistrict(@RequestParam("districtId") Integer districtId, @RequestParam("level") Integer level) {
        DistrictEnum districtEnum = districtEnumRepository.findById(districtId).get();
        BuildingEnum wallsEnum = buildingEnumRepository.findByName("Walls");
        BuildingUpgrade wallUpgrade = wallsEnum.getUpgrades().stream().filter(bu -> bu.getLevel() == level).toList().get(0);

        List<BuildingInstance> walls = buildingInstanceRepository.findAllByDistrictEnumAndBuildingEnum(districtEnum, wallsEnum);
        for (BuildingInstance wall : walls) {
            wall.setBuildingUpgrade(wallUpgrade);
        }
        buildingInstanceRepository.saveAll(walls);
        return ResponseEntity.ok("");
    }

}
