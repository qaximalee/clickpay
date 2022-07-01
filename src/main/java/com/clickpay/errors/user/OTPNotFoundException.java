package com.clickpay.errors.user;

public class OTPNotFoundException  extends RuntimeException{
    private static final long serialVersionUID = 5776681206288518234L;

    public OTPNotFoundException(String message) {
        super(message);
    }
}
