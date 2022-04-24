package com.getir.readingisgood.service.Impl;

import com.getir.readingisgood.dao.OrderCreateDAO;
import com.getir.readingisgood.dao.OrderDAO;
import com.getir.readingisgood.document.Book;
import com.getir.readingisgood.document.Customer;
import com.getir.readingisgood.document.Order;
import com.getir.readingisgood.document.Stock;
import com.getir.readingisgood.exception.BookNotFoundException;
import com.getir.readingisgood.exception.CustomerNotFoundException;
import com.getir.readingisgood.exception.OrderNotFoundException;
import com.getir.readingisgood.exception.ThereIsNotEnoughProductException;
import com.getir.readingisgood.helper.SequenceService;
import com.getir.readingisgood.mapper.OrderMapper;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.repository.StockRepository;
import com.getir.readingisgood.service.OrderService;
import com.getir.readingisgood.service.enumaration.Sequence;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;
    private final MongoTemplate mongoTemplate;
    private final SequenceService sequenceService;
    private final OrderMapper orderMapper;

    @Override
    public void createOrder(String email, OrderCreateDAO orderCreateDAO) {
        Book book = bookRepository.findById(orderCreateDAO.getBookId()).orElseThrow(() -> new BookNotFoundException(orderCreateDAO.getBookId()));
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new CustomerNotFoundException(email));
        Order order = new Order();
        order.setOrderTime(LocalDateTime.now());
        order.setBook(book);
        order.setCustomer(customer);
        order.setQuantity(orderCreateDAO.getQuantity());

        synchronized (this) {
            Stock stock = mongoTemplate.query(Stock.class)
                    .matching(where("book.id").is(book.getId()))
                    .stream().filter(bookEntity ->
                            bookEntity.getCount() > 0L && bookEntity.getCount() >= orderCreateDAO.getQuantity())
                    .findFirst()
                    .orElseThrow(ThereIsNotEnoughProductException::new);

            stock.setCount(stock.getCount() - orderCreateDAO.getQuantity());
            order.setId(sequenceService.getNextSequence(Sequence.ORDER.getName()));
            stockRepository.save(stock);
            orderRepository.save(order);
        }
    }

    @Override
    public OrderDAO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        return orderMapper.toGetDAO(order);
    }

    @Override
    public List<OrderDAO> getOrdersByDateInterval(LocalDate startDate, LocalDate endDate) {
        startDate = startDate == null ? LocalDate.EPOCH : startDate;
        endDate = endDate == null ? LocalDate.now().plusDays(1) : endDate;

        List<Order> orders = orderRepository.findAllByOrderTimeGreaterThanAndOrderTimeLessThan(startDate, endDate);
        return orderMapper.toGetDAOList(orders);
    }
}
