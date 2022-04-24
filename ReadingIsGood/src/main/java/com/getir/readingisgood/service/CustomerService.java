package com.getir.readingisgood.service;

import com.getir.readingisgood.dao.OrderDAO;

import java.security.Principal;
import java.util.List;

public interface CustomerService {
    List<OrderDAO> getOrdersByCustomer(Principal principal);
}
