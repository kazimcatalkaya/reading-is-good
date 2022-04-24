package com.getir.readingisgood.service;

import com.getir.readingisgood.dao.BookCreateDAO;
import com.getir.readingisgood.dao.BookUpdateDAO;
import com.getir.readingisgood.document.Book;
import com.getir.readingisgood.helper.SequenceService;
import com.getir.readingisgood.mapper.BookMapper;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.repository.StockRepository;
import com.getir.readingisgood.service.Impl.BookServiceImpl;
import com.getir.readingisgood.service.enumaration.Sequence;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private StockRepository stockRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private SequenceService sequenceService;
    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private BookServiceImpl bookService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_createBook() {
        BookCreateDAO bookCreateDAO = new BookCreateDAO();
        bookCreateDAO.setPrice(10L);
        bookCreateDAO.setName("testName");
        bookCreateDAO.setAuthor("testAuthor");
        Book book = new Book();

        Mockito.when(bookRepository.existsByNameAndAuthor("testName","testAuthor")).thenReturn(false);
        Mockito.when(sequenceService.getNextSequence(Sequence.BOOK.getName())).thenReturn(1L);
        Mockito.when(sequenceService.getNextSequence(Sequence.STOCK.getName())).thenReturn(1L);
        Mockito.when(bookMapper.toEntity(bookCreateDAO)).thenReturn(book);
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        bookService.createBook(bookCreateDAO);

        Mockito.verify(bookRepository, Mockito.times(1)).save(book);
    }

    @Test
    public void test_updateBook() {
        Long givenBookId = 4L;
        BookUpdateDAO givenBookDAO = new BookUpdateDAO(4L, "givenName","givenAuthor",5L);
        Book book = new Book(4L, "givenName","givenAuthor",5L,null);

        Mockito.when(bookRepository.findById(givenBookId)).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.save(book)).thenReturn(null);

        bookService.updateBook(givenBookId, givenBookDAO);

        Mockito.verify(bookRepository, Mockito.times(1)).findById(givenBookId);
        Mockito.verify(bookRepository, Mockito.times(1)).save(book);
    }
}
