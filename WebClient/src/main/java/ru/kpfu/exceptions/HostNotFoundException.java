package ru.kpfu.exceptions;

public class HostNotFoundException extends RuntimeException{

    public HostNotFoundException() {
    }

    public HostNotFoundException(String message) {
        super(message);
    }

    public HostNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public HostNotFoundException(Throwable cause) {
        super(cause);
    }
}
