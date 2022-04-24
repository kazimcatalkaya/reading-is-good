package com.getir.readingisgood.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDAO {
    private Long id;
    private CustomerDAO customer;
    private BookDAO book;
    private Long quantity;
    private LocalDateTime orderTime;
}
