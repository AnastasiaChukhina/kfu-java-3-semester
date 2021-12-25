package ru.kpfu.nastyaanastasya.exceptions;

public class NoAccessToFileException extends RuntimeException{
    public NoAccessToFileException() {
    }

    public NoAccessToFileException(String message) {
        super(message);
    }

    public NoAccessToFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAccessToFileException(Throwable cause) {
        super(cause);
    }
}
