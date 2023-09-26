package com.mattmattica.clash.model.repositories;

import com.mattmattica.clash.model.entities.BuildingEnum;
import com.mattmattica.clash.model.entities.BuildingUpgrade;
import org.springframework.data.repository.ListCrudRepository;

public interface BuildingUpgradeRepository extends ListCrudRepository<BuildingUpgrade, Integer> {

    BuildingUpgrade findByBuildingEnumAndLevel(BuildingEnum buildingEnum, Integer level);
}
