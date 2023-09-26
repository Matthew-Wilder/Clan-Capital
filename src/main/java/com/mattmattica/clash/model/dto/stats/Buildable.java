package com.mattmattica.clash.model.dto.stats;

import com.mattmattica.clash.util.MathUtils;

public interface Buildable extends Upgradable {
    int getNumberOfBuildingsMaxed();

    int getNumberOfBuildings();

    default double getPercentageOfBuildingsMaxed() {
        return MathUtils.divide(getNumberOfBuildingsMaxed(), getNumberOfBuildings());
    }

    @Override
    default boolean isMaxed() {
        return getNumberOfBuildingsMaxed() == getNumberOfBuildings();
    }
}
