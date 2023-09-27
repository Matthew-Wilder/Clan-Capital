package com.mattmattica.clash.controllers;

import com.mattmattica.clash.model.dto.ClanCapitalDTO;
import com.mattmattica.clash.model.dto.DistrictDTO;
import com.mattmattica.clash.model.dto.stats.PercentageData;
import com.mattmattica.clash.model.entities.BuildingInstance;
import com.mattmattica.clash.model.entities.BuildingUpgrade;
import com.mattmattica.clash.model.entities.DistrictEnum;
import com.mattmattica.clash.model.repositories.BuildingEnumRepository;
import com.mattmattica.clash.model.repositories.BuildingInstanceRepository;
import com.mattmattica.clash.model.repositories.BuildingUpgradeRepository;
import com.mattmattica.clash.model.repositories.DistrictEnumRepository;
import com.mattmattica.clash.model.services.ClanCapitalService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Controller
public class ProgressController {

    @Autowired
    private BuildingEnumRepository buildingRepo;
    @Autowired
    private DistrictEnumRepository districtRepo;
    @Autowired
    private BuildingUpgradeRepository upgradeRepo;
    @Autowired
    private BuildingInstanceRepository instanceRepository;
    @Autowired
    private ClanCapitalService clanCapitalService;

    @GetMapping("/progress")
    public String home(Model model) {
        ClanCapitalDTO clanCapital = clanCapitalService.getClanCapital();
        model.addAttribute("clanCapital", clanCapital);
        PercentageData pd = new PercentageData();
        clanCapital.getDistricts().stream().map(DistrictDTO::getCostPercentageData).forEach(pd::add);
        System.out.println(pd.getMaxAmount());
        return "progress";
    }


    public String initializeInstances() {
        var districts = districtRepo.findAllSorted();
        for (DistrictEnum district : districts) {
            var allowedBuildings = district.getSortedBuildings();
            for (var allowedBuilding : allowedBuildings) {
                var limit = allowedBuilding.getLimitInDistrict(district);
                int max = limit.getMaximumAmount();
                for (int i = 0; i < max; ++i) {
                    BuildingInstance instance = new BuildingInstance();
                    instance.setDistrictEnum(district);
                    instance.setBuildingEnum(allowedBuilding);
                    var upgrade = upgradeRepo.findByBuildingEnumAndLevel(allowedBuilding, 1);
                    instance.setBuildingUpgrade(upgrade);
                    instanceRepository.save(instance);
                }
            }
        }
        return "progress";
    }


}
