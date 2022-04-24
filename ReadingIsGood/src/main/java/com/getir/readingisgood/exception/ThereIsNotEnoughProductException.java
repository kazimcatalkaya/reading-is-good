package com.getir.readingisgood.exception;

import com.getir.readingisgood.controller.error.dao.GenericException;

public class ThereIsNotEnoughProductException extends GenericException {
    public ThereIsNotEnoughProductException() {
    }

    public ThereIsNotEnoughProductException(Object ...message) {
        super(message);
    }
}
