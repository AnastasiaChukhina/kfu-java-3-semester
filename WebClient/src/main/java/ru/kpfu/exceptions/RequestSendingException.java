package ru.kpfu.exceptions;

public class RequestSendingException extends RuntimeException{

    public RequestSendingException() {
    }

    public RequestSendingException(String message) {
        super(message);
    }

    public RequestSendingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestSendingException(Throwable cause) {
        super(cause);
    }
}
