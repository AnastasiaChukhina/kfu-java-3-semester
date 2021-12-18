package ru.kpfu.exceptions;

public class InvalidMethodException extends RuntimeException{

    public InvalidMethodException() {
    }

    public InvalidMethodException(String message) {
        super(message);
    }

    public InvalidMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMethodException(Throwable cause) {
        super(cause);
    }
}
