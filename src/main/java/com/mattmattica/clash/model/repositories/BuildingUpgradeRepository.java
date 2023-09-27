package com.mattmattica.clash.model.repositories;

import com.mattmattica.clash.model.entities.BuildingEnum;
import com.mattmattica.clash.model.entities.BuildingUpgrade;
import com.mattmattica.clash.model.entities.DistrictEnum;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface BuildingUpgradeRepository extends ListCrudRepository<BuildingUpgrade, Integer> {

    BuildingUpgrade findByBuildingEnumAndLevel(BuildingEnum buildingEnum, Integer level);
}
