package com.getir.readingisgood.mapper;

import com.getir.readingisgood.dao.BookCreateDAO;
import com.getir.readingisgood.dao.BookDAO;
import com.getir.readingisgood.dao.BookUpdateDAO;
import com.getir.readingisgood.document.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toEntity(BookCreateDAO dao);

    Book toEntityFromUpdateDto(BookUpdateDAO dao);

    BookDAO fromEntityToBookDAO(Book book);
}
