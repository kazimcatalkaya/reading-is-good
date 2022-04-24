package com.getir.readingisgood.exception;

import com.getir.readingisgood.controller.error.dao.GenericException;

public class CustomerNotFoundException extends GenericException {
    public CustomerNotFoundException(Object ...parameters) {
        super(parameters);
    }
}
