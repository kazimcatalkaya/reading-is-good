package com.getir.readingisgood.service;

import com.getir.readingisgood.dao.StatisticDAO;

import java.util.List;

public interface StatisticService {
    List<StatisticDAO> getStatistics(String email);
}
