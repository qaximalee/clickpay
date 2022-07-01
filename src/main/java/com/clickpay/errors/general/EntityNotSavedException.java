package com.clickpay.errors.general;

public class EntityNotSavedException extends Exception{
    public EntityNotSavedException() {
    }

    public EntityNotSavedException(String message) {
        super(message);
    }

    public EntityNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }
}
