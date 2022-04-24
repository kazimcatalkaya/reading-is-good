package com.getir.readingisgood.service.Impl;

import com.getir.readingisgood.dao.BookCreateDAO;
import com.getir.readingisgood.dao.BookDAO;
import com.getir.readingisgood.dao.BookUpdateDAO;
import com.getir.readingisgood.document.Book;
import com.getir.readingisgood.document.Stock;
import com.getir.readingisgood.exception.BookAlreadyExistsException;
import com.getir.readingisgood.exception.BookImproperValueException;
import com.getir.readingisgood.exception.BookNotFoundException;
import com.getir.readingisgood.helper.SequenceService;
import com.getir.readingisgood.mapper.BookMapper;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.repository.StockRepository;
import com.getir.readingisgood.service.BookService;
import com.getir.readingisgood.service.enumaration.Sequence;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final StockRepository stockRepository;
    private final BookMapper bookMapper;
    private final SequenceService sequenceService;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<BookDAO> getAllBooks() {
        return mongoTemplate.query(Stock.class)
                .matching(where("count").gt(0L))
                .stream().map(Stock::getBook)
                .map(bookMapper::fromEntityToBookDAO)
                .collect(Collectors.toList());
    }

    @Override
    public void createBook(BookCreateDAO bookCreateDAO) throws BookAlreadyExistsException {
        if (bookCreateDAO.getPrice() < 0L ||
                StringUtils.isEmpty(bookCreateDAO.getAuthor().trim()) ||
                StringUtils.isEmpty(bookCreateDAO.getName().trim())) {
            throw new BookImproperValueException();
        }

        boolean isExistsBookByNameAndAuthor = bookRepository.existsByNameAndAuthor(bookCreateDAO.getName(), bookCreateDAO.getAuthor());

        if (isExistsBookByNameAndAuthor) {
            throw new BookAlreadyExistsException(bookCreateDAO.getName(), bookCreateDAO.getAuthor());
        }

        Book book = bookMapper.toEntity(bookCreateDAO);
        book.setId(sequenceService.getNextSequence(Sequence.BOOK.getName()));

        Book savedBook = bookRepository.save(book);
        Stock stock = new Stock(sequenceService.getNextSequence(Sequence.STOCK.getName()), savedBook, 100L);
        stockRepository.save(stock);
    }

    @Override
    public void updateBook(Long bookId, BookUpdateDAO bookDAO) {
        if (bookDAO.getPrice() < 0L || StringUtils.isEmpty(bookDAO.getAuthor().trim()) || StringUtils.isEmpty(bookDAO.getName().trim())) {
            throw new BookImproperValueException();
        }
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId.toString()));

        book.setName(bookDAO.getName());
        book.setAuthor(bookDAO.getAuthor());
        book.setPrice(bookDAO.getPrice());
        bookRepository.save(book);
    }
}
