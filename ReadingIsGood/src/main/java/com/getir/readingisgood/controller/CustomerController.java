package com.getir.readingisgood.controller;

import com.getir.readingisgood.dao.CustomerCreateDAO;
import com.getir.readingisgood.dao.CustomerLoginDAO;
import com.getir.readingisgood.dao.OrderDAO;
import com.getir.readingisgood.service.CustomerAuthService;
import com.getir.readingisgood.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerAuthService customerAuthService;
    private final CustomerService customerService;

    @PostMapping("/register")
    public String registerCustomer(@RequestBody CustomerCreateDAO customerCreateDAO) {
        return customerAuthService.saveCustomer(customerCreateDAO);
    }

    @PostMapping("/login")
    public String loginCustomer(@RequestBody CustomerLoginDAO customerLoginDAO) {
        return customerAuthService.loginCustomer(customerLoginDAO);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDAO>> getOrders(Principal principal) {
        return new ResponseEntity<>(customerService.getOrdersByCustomer(principal), HttpStatus.OK);
    }
}
