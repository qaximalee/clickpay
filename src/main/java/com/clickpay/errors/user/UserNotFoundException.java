package com.clickpay.errors.user;


public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5776681206288518465L;

    public UserNotFoundException(String message) {
        super(message);
    }
}
