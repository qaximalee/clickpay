package com.clickpay.errors.user;

public class UserNotActiveException extends RuntimeException{
    private static final long serialVersionUID = 5776681206288518465L;

    public UserNotActiveException(String message) {
        super(message);
    }
}
