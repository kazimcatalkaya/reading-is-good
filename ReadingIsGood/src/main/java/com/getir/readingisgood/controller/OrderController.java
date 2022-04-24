package com.getir.readingisgood.controller;

import com.getir.readingisgood.dao.OrderCreateDAO;
import com.getir.readingisgood.dao.OrderDAO;
import com.getir.readingisgood.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateDAO orderCreate, Principal principal) {
        orderService.createOrder(principal.getName(), orderCreate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_USER')")
    public ResponseEntity<OrderDAO> getOrderById(@PathVariable Long orderId, Principal principal) {
        OrderDAO order = orderService.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/date-interval")
    public ResponseEntity<List<OrderDAO>> getOrdersByDateInterval(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<OrderDAO> orders = orderService.getOrdersByDateInterval(startDate, endDate);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
