package com.getir.readingisgood.service.Impl;

import com.getir.readingisgood.dao.OrderDAO;
import com.getir.readingisgood.document.Customer;
import com.getir.readingisgood.document.Order;
import com.getir.readingisgood.exception.CustomerNotFoundException;
import com.getir.readingisgood.mapper.OrderMapper;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final MongoTemplate mongoTemplate;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDAO> getOrdersByCustomer(Principal principal) {
        Customer customer = customerRepository.findByEmail(principal.getName()).orElseThrow(CustomerNotFoundException::new);
        return mongoTemplate.query(Order.class)
                .matching(where("customer.id").is(customer.getId()))
                .stream()
                .map(orderMapper::toGetDAO)
                .collect(Collectors.toList());
    }
}
