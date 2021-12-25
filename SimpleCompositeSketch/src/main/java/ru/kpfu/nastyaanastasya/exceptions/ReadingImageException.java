package ru.kpfu.nastyaanastasya.exceptions;

public class ReadingImageException extends RuntimeException{

    public ReadingImageException() {
    }

    public ReadingImageException(String message) {
        super(message);
    }

    public ReadingImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadingImageException(Throwable cause) {
        super(cause);
    }
}
