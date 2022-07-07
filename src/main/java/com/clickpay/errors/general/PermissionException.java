package com.clickpay.errors.general;

public class PermissionException extends Exception{

    public PermissionException(){}

    public PermissionException(String message) {
        super(message);
    }

    public PermissionException(String message, Throwable cause){super(message, cause);}
}
