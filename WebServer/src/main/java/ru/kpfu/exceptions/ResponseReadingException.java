package ru.kpfu.exceptions;

public class ResponseReadingException extends RuntimeException{

    public ResponseReadingException() {
    }

    public ResponseReadingException(String message) {
        super(message);
    }

    public ResponseReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseReadingException(Throwable cause) {
        super(cause);
    }
}
