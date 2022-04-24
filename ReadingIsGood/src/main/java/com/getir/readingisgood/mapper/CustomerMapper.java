package com.getir.readingisgood.mapper;

import com.getir.readingisgood.dao.CustomerDAO;
import com.getir.readingisgood.document.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDAO toDAO(Customer customer);
}
