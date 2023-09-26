package com.mattmattica.clash.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BuildingCategory implements Comparable<BuildingCategory> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "category")
    private Set<BuildingEnum> buildings = new HashSet<>();


    private static final Comparator<BuildingCategory> COMPARATOR = Comparator
            .comparing(BuildingCategory::getId);

    @Override
    public int compareTo(BuildingCategory o) {
        return COMPARATOR.compare(this, o);
    }
}
