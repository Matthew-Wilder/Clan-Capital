package com.mattmattica.clash.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BuildingDTO {
    private int id;
    private String name;
    private List<UpgradeDTO> upgrades = new ArrayList<>();
    private CategoryDTO category;

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BuildingDTO)) return false;
        final BuildingDTO other = (BuildingDTO) o;
        if (!other.canEqual((Object) this)) return false;
        return (this.getId() == other.getId());
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BuildingDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getId();
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $upgrades = this.getUpgrades();
        result = result * PRIME + ($upgrades == null ? 43 : $upgrades.hashCode());
        final Object $category = this.getCategory();
        result = result * PRIME + ($category == null ? 43 : $category.hashCode());
        return result;
    }
}
