package com.clickpay.errors.general;

public class EntityNotUpdateException extends Exception{

    public EntityNotUpdateException() {
    }

    public EntityNotUpdateException(String message) {
        super(message);
    }

    public EntityNotUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
