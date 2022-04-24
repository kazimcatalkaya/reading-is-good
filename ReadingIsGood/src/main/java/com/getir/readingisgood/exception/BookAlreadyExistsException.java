package com.getir.readingisgood.exception;

import com.getir.readingisgood.controller.error.dao.GenericException;

public class BookAlreadyExistsException extends GenericException {
    public BookAlreadyExistsException() {
    }

    public BookAlreadyExistsException(Object ...message) {
        super(message);
    }
}
