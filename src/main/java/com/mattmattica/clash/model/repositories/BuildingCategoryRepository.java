package com.mattmattica.clash.model.repositories;

import com.mattmattica.clash.model.entities.BuildingCategory;
import org.springframework.data.repository.ListCrudRepository;

public interface BuildingCategoryRepository extends ListCrudRepository<BuildingCategory, Integer> {
}
