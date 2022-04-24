package com.getir.readingisgood.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateDAO {
    private Long id;
    private String name;
    private String author;
    private Long price;
}
