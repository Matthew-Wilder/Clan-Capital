package com.mattmattica.clash.model.dto.stats;

import com.mattmattica.clash.util.MathUtils;

public interface Upgradable {

    int getNumberOfUpgradesCompleted();

    int getNumberOfUpgrades();

    default double getPercentageOfUpgradesCompleted() {
        return MathUtils.divide(getNumberOfUpgradesCompleted(), getNumberOfUpgrades()) * 100;
    }

    default boolean isMaxed() {
        return getNumberOfUpgradesCompleted() == getNumberOfUpgrades();
    }
}
