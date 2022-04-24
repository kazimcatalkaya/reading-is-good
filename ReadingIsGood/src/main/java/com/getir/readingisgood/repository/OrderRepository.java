package com.getir.readingisgood.repository;

import com.getir.readingisgood.document.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, Long> {
    @Query(value = "{'orderTime' : {$gte : ?0, $lte : ?1}}")
    List<Order> findAllByOrderTimeGreaterThanAndOrderTimeLessThan(LocalDate startDate, LocalDate endDate);

    List<Order> findAllByCustomerId(Long customerId);
}
