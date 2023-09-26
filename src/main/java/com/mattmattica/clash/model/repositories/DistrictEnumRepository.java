package com.mattmattica.clash.model.repositories;

import com.mattmattica.clash.model.entities.DistrictEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface DistrictEnumRepository extends ListCrudRepository<DistrictEnum, Integer> {

    default List<DistrictEnum> findAllSorted() {
        return this.findAll().stream().sorted().toList();
    }
}
