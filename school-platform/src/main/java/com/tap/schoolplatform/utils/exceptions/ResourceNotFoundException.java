package com.tap.schoolplatform.utils.exceptions;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException() {
        super("Resource not found");
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
