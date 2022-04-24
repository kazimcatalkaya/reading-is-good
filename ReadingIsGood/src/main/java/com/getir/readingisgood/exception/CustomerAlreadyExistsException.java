package com.getir.readingisgood.exception;

import com.getir.readingisgood.controller.error.dao.GenericException;

public class CustomerAlreadyExistsException extends GenericException {
    public CustomerAlreadyExistsException() {
    }

    public CustomerAlreadyExistsException(Object ...message) {
        super(message);
    }
}
