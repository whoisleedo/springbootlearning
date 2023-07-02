package com.practice.demo.exception;

public class AccountUnavailableException extends Exception{
    public AccountUnavailableException(String message) {
        super(message);
    }

    public AccountUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
