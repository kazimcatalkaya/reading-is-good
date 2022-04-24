package com.getir.readingisgood.controller;

import com.getir.readingisgood.dao.StatisticDAO;
import com.getir.readingisgood.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/statistics")
@AllArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_USER')")
    public ResponseEntity<List<StatisticDAO>> getStatistics(Principal principal) {
        List<StatisticDAO> statistics = statisticService.getStatistics(principal.getName());
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }
}
