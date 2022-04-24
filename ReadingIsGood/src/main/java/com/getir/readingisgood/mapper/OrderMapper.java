package com.getir.readingisgood.mapper;

import com.getir.readingisgood.dao.OrderDAO;
import com.getir.readingisgood.document.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDAO toGetDAO(Order order);

    List<OrderDAO> toGetDAOList(List<Order> order);
}
