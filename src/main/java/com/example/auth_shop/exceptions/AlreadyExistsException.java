package com.example.auth_shop.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String msg) {
    super(msg);
    }
}
