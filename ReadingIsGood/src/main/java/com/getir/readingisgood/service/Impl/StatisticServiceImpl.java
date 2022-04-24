package com.getir.readingisgood.service.Impl;

import com.getir.readingisgood.dao.StatisticDAO;
import com.getir.readingisgood.document.Customer;
import com.getir.readingisgood.document.Order;
import com.getir.readingisgood.exception.CustomerNotFoundException;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Override
    public List<StatisticDAO> getStatistics(String email) {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(CustomerNotFoundException::new);
        List<Order> orders = orderRepository.findAllByCustomerId(customer.getId());

        Map<Month, List<Order>> ordersByMount = orders.stream()
                .collect(Collectors.groupingBy(order -> order.getOrderTime().getMonth()));

        return ordersByMount.keySet()
                .stream()
                .map(key -> {
                    StatisticDAO statisticDAO = new StatisticDAO();
                    long totalOrderCount = ordersByMount.get(key).size();

                    Long totalBookCount = ordersByMount.get(key).stream()
                            .map(Order::getQuantity)
                            .reduce(0L, Long::sum);

                    Long totalPurchasedAmount = ordersByMount.get(key).stream()
                            .map(order -> order.getQuantity() * order.getBook().getPrice())
                            .reduce(0L, Long::sum);

                    statisticDAO.setMount(key.name());
                    statisticDAO.setBookCount(totalBookCount);
                    statisticDAO.setOrderCount(totalOrderCount);
                    statisticDAO.setPurchasedAmount(totalPurchasedAmount);
                    return statisticDAO;
                }).collect(Collectors.toList());
    }
}
