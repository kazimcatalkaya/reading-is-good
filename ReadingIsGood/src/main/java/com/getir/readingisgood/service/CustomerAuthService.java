package com.getir.readingisgood.service;

import com.getir.readingisgood.dao.CustomerCreateDAO;
import com.getir.readingisgood.dao.CustomerLoginDAO;

public interface CustomerAuthService {
    String saveCustomer(CustomerCreateDAO customer);

    String loginCustomer(CustomerLoginDAO login);
}
