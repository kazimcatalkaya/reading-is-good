package com.getir.readingisgood.service.enumaration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Sequence {
    BOOK("bookSeq"),
    STOCK("stockSeq"),
    ORDER("orderSeq"),
    CUSTOMER("customerSeq");

    private final String name;
}
