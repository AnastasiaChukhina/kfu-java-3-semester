package ru.kpfu.exceptions;

public class UnknownMethodException extends RuntimeException{

    public UnknownMethodException() {
    }

    public UnknownMethodException(String message) {
        super(message);
    }

    public UnknownMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownMethodException(Throwable cause) {
        super(cause);
    }
}
