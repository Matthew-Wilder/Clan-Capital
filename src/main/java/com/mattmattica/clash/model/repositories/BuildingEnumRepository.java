package com.mattmattica.clash.model.repositories;

import com.mattmattica.clash.model.entities.BuildingEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface BuildingEnumRepository extends ListCrudRepository<BuildingEnum, Integer> {
    default List<BuildingEnum> findAllSorted() {
        return this.findAll().stream().sorted().toList();
    }

    BuildingEnum findByName(String name);
}
