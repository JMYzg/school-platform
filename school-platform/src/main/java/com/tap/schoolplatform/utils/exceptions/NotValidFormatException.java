package com.tap.schoolplatform.utils.exceptions;

public class NotValidFormatException extends Exception {
    public NotValidFormatException() {
        super("Not a valid format");
    }
    public NotValidFormatException(String message) {
        super(message);
    }
    public NotValidFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
