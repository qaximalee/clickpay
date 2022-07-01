package com.clickpay.errors.general;

public class InternalServerException extends Exception{

    public InternalServerException() {
    }

    public InternalServerException(String message){super(message);}

    public InternalServerException(String message, Throwable cause){super(message, cause);}
}
