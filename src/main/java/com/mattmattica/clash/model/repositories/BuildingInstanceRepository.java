package com.mattmattica.clash.model.repositories;

import com.mattmattica.clash.model.entities.BuildingEnum;
import com.mattmattica.clash.model.entities.BuildingInstance;
import com.mattmattica.clash.model.entities.DistrictEnum;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface BuildingInstanceRepository extends ListCrudRepository<BuildingInstance, Integer> {
    List<BuildingInstance> findAllByDistrictEnum(DistrictEnum districtEnum);
    List<BuildingInstance> findAllByDistrictEnumAndBuildingEnum(DistrictEnum districtEnum, BuildingEnum buildingEnum);
}
