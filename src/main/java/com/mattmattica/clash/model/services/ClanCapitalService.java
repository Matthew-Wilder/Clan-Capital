package com.mattmattica.clash.model.services;

import com.mattmattica.clash.model.dto.*;
import com.mattmattica.clash.model.entities.*;
import com.mattmattica.clash.model.repositories.BuildingCategoryRepository;
import com.mattmattica.clash.model.repositories.BuildingEnumRepository;
import com.mattmattica.clash.model.repositories.BuildingUpgradeRepository;
import com.mattmattica.clash.model.repositories.DistrictEnumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClanCapitalService {
    @Autowired private DistrictEnumRepository districtRepository;
    @Autowired private BuildingEnumRepository buildingEnumRepository;
    @Autowired private BuildingCategoryRepository categoryRepository;
    @Autowired private BuildingUpgradeRepository upgradeRepository;




    public ClanCapitalDTO getClanCapital() {
        ClanCapitalDTO clanCapital = new ClanCapitalDTO();

        final Map<Integer, UpgradeDTO> upgradeDTOMap = getUpgradeDTOMap();
        final Map<Integer, BuildingDTO> buildingDTOMap = getBuildingDTOMap(upgradeDTOMap);

        List<DistrictEnum> districtEnums = districtRepository.findAllSorted();
        for (var districtEnum : districtEnums) {
            DistrictDTO districtDTO = new DistrictDTO();
            districtDTO.setId(districtEnum.getId());
            districtDTO.setName(districtEnum.getName());

            districtDTO.setAvailableBuildings(
                    districtEnum
                            .getSortedBuildings()
                            .stream()
                            .map(building -> buildingDTOMap.get(building.getId()))
                            .toList()
            );

            List<BuildingInstanceDTO> buildingInstanceDTOs = new ArrayList<>();
            for (BuildingEnum buildingEnum : districtEnum.getSortedBuildings()) {
                List<BuildingInstance> instances = buildingEnum.getSortedInstances(districtEnum);
                for (BuildingInstance instance : instances) {
                    BuildingInstanceDTO instanceDTO = new BuildingInstanceDTO();
                    instanceDTO.setId(instance.getId());
                    instanceDTO.setBuilding(buildingDTOMap.get(buildingEnum.getId()));
                    instanceDTO.setDistrict(districtDTO);
                    instanceDTO.setUpgrade(upgradeDTOMap.get(instance.getBuildingUpgrade().getId()));
                    buildingInstanceDTOs.add(instanceDTO);
                }
            }
            districtDTO.setBuildingInstances(buildingInstanceDTOs);
            clanCapital.getDistricts().add(districtDTO);
        }
        return clanCapital;
    }



    private Map<Integer, CategoryDTO> getCategoryDTOMap() {
        Map<Integer, CategoryDTO> output = new HashMap<>();
        for (BuildingCategory buildingCategory : categoryRepository.findAll()) {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(buildingCategory.getId());
            dto.setName(buildingCategory.getName());
            output.put(dto.getId(), dto);
        }
        return output;
    }

    private Map<Integer, UpgradeDTO> getUpgradeDTOMap() {
        Map<Integer, UpgradeDTO> output = new HashMap<>();
        for (BuildingUpgrade upgrade : upgradeRepository.findAll()) {
            UpgradeDTO dto = new UpgradeDTO();
            dto.setId(upgrade.getId());
            dto.setLevel(upgrade.getLevel());
            dto.setCost(upgrade.getCost());
            output.put(dto.getId(), dto);
        }
        return output;
    }

    private Map<Integer, BuildingDTO> getBuildingDTOMap(Map<Integer, UpgradeDTO> upgradeDTOMap) {
        final Map<Integer, CategoryDTO> categoryDTOMap = getCategoryDTOMap();

        Map<Integer, BuildingDTO> output = new HashMap<>();
        for (BuildingEnum buildingEnum : buildingEnumRepository.findAll()) {
            BuildingDTO dto = new BuildingDTO();
            dto.setId(buildingEnum.getId());
            dto.setName(buildingEnum.getName());
            dto.setCategory(categoryDTOMap.get(buildingEnum.getCategory().getId()));
            dto.setUpgrades(buildingEnum.sortedUpgrades().stream().map(u -> upgradeDTOMap.get(u.getId())).collect(Collectors.toList()));
            output.put(dto.getId(), dto);
        }
        return output;
    }

}
