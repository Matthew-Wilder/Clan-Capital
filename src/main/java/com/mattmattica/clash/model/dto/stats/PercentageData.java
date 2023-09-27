package com.mattmattica.clash.model.dto.stats;

import com.mattmattica.clash.util.MathUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PercentageData {
    private int amount;
    private int maxAmount;
    public double getPercentage() {
        return MathUtils.divide(amount, maxAmount) * 100;
    }

    public String getCanonicalForm() {
        return String.format("%s / %s", MathUtils.format(amount), MathUtils.format(maxAmount));
    }

    public void add(PercentageData percentageData) {
        this.amount += percentageData.amount;
        this.maxAmount += percentageData.maxAmount;
    }
}
