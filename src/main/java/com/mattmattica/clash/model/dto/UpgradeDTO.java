package com.mattmattica.clash.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UpgradeDTO {
    int id;
    int level;
    int cost;
}
