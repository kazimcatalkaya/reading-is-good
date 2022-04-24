package com.getir.readingisgood.enumaration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StockStatus {
    ACTIVE("active"),
    SOLD_OUT("sold out");

    private final String status;
}
