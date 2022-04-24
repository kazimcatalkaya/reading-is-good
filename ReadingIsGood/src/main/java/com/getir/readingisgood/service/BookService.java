package com.getir.readingisgood.service;

import com.getir.readingisgood.dao.BookCreateDAO;
import com.getir.readingisgood.dao.BookDAO;
import com.getir.readingisgood.dao.BookUpdateDAO;

import java.util.List;

public interface BookService {
    void createBook(BookCreateDAO bookCreateDAO);

    void updateBook(Long bookId, BookUpdateDAO bookDAO);

    List<BookDAO> getAllBooks();
}
