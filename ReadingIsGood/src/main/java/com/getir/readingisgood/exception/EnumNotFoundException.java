package com.getir.readingisgood.exception;

import com.getir.readingisgood.controller.error.dao.GenericException;

public class EnumNotFoundException extends GenericException {
    public EnumNotFoundException() {
    }

    public EnumNotFoundException(Object[] parameters) {
        super(parameters);
    }
}
