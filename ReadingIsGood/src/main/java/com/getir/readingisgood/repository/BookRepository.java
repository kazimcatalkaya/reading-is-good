package com.getir.readingisgood.repository;

import com.getir.readingisgood.document.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, Long> {
    boolean existsByNameAndAuthor(String name, String author);
}
