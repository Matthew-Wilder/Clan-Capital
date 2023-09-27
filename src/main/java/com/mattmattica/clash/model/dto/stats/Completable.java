package com.mattmattica.clash.model.dto.stats;

import com.mattmattica.clash.util.MathUtils;

public interface Completable extends Buildable {

    int getNumberOfDistrictsCompleted();

    int getNumberOfDistricts();

    default double getPercentageOfDistrictsCompleted() {
        return MathUtils.divide(getNumberOfDistrictsCompleted(), getNumberOfDistricts()) * 100;
    }

    @Override
    default boolean isMaxed() {
        return getNumberOfUpgradesCompleted() == getNumberOfDistricts();
    }
}
