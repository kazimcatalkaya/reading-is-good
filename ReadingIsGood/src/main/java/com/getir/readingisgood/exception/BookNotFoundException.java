package com.getir.readingisgood.exception;

import com.getir.readingisgood.controller.error.dao.GenericException;

public class BookNotFoundException extends GenericException {
    public BookNotFoundException(Object ...parameters) {
        super(parameters);
    }
}
