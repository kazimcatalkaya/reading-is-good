package com.getir.readingisgood.controller;

import com.getir.readingisgood.dao.BookCreateDAO;
import com.getir.readingisgood.dao.BookDAO;
import com.getir.readingisgood.dao.BookUpdateDAO;
import com.getir.readingisgood.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> createBook(@RequestBody BookCreateDAO bookDAO, Principal principal) {
        bookService.createBook(bookDAO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> updateBook(@PathVariable Long bookId, @RequestBody BookUpdateDAO bookDAO, Principal principal) {
        bookService.updateBook(bookId, bookDAO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BookDAO>> getAllBooks() {
        List<BookDAO> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
