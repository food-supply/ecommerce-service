package com.se.ecommerce_service.exception;

public class BusinessException extends RuntimeException{
    private final String errorKey;

    public BusinessException(String errorKey, String message) {
        super(message);
        this.errorKey = errorKey;
    }

    public String getErrorKey() {
        return errorKey;
    }
}
