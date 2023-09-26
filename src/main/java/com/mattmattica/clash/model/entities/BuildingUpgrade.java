package com.mattmattica.clash.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;

@Getter
@Setter
@Entity
public class BuildingUpgrade implements Comparable<BuildingUpgrade> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "building_enum_id")
    private BuildingEnum buildingEnum;

    private int level;
    private int cost;

    private static final Comparator<BuildingUpgrade> COMPARATOR = Comparator
            .comparing(BuildingUpgrade::getId)
            .thenComparing(BuildingUpgrade::getLevel);

    @Override
    public int compareTo(BuildingUpgrade o) {
        return COMPARATOR.compare(this, o);
    }

    public String getLevelProgress() {
        int maxLevel = buildingEnum.getUpgrades().size();
        return "Level %d/%d".formatted(level, maxLevel);
    }
}
