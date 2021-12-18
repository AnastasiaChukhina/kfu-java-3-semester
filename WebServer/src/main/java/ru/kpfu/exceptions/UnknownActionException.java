package ru.kpfu.exceptions;

public class UnknownActionException extends RuntimeException{

    public UnknownActionException() {
        super();
    }

    public UnknownActionException(String message) {
        super(message);
    }

    public UnknownActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownActionException(Throwable cause) {
        super(cause);
    }
}
