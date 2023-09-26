package com.mattmattica.clash.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class DistrictEnum implements Comparable<DistrictEnum> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "district_building_limit",
            joinColumns = @JoinColumn(name = "district_enum_id"),
            inverseJoinColumns = @JoinColumn(name = "building_enum_id")
    )
    private Set<BuildingEnum> allowedBuildings = new HashSet<>();

    public List<BuildingEnum> getSortedBuildings() {
        return this.getAllowedBuildings().stream().sorted().toList();
    }


    private static final Comparator<DistrictEnum> COMPARATOR = Comparator
            .comparing(DistrictEnum::getId);

    @Override
    public int compareTo(DistrictEnum o) {
        return COMPARATOR.compare(this, o);
    }

}
