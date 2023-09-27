package com.mattmattica.clash.model.dto.stats;

import com.mattmattica.clash.util.MathUtils;

public interface Buildable extends Upgradable {
    int getNumberOfBuildingsMaxed();

    int getNumberOfBuildings();

    default double getPercentageOfBuildingsMaxed() {
        return MathUtils.divide(getNumberOfBuildingsMaxed(), getNumberOfBuildings()) * 100;
    }

    @Override
    default boolean isMaxed() {
        return getNumberOfBuildingsMaxed() == getNumberOfBuildings();
    }
}
