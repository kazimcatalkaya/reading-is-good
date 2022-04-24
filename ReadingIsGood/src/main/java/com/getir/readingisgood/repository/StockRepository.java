package com.getir.readingisgood.repository;

import com.getir.readingisgood.document.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface StockRepository extends MongoRepository<Stock, Long> {
    @Query("{'book.name':  ?0}")
    List<Stock> findAllByName(String name);
}
