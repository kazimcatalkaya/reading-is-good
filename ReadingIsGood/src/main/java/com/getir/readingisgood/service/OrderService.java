package com.getir.readingisgood.service;

import com.getir.readingisgood.dao.OrderCreateDAO;
import com.getir.readingisgood.dao.OrderDAO;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    void createOrder(String email, OrderCreateDAO orderCreateDAO);

    OrderDAO getOrderById(Long id);

    List<OrderDAO> getOrdersByDateInterval(LocalDate startDate, LocalDate endDate);
}
