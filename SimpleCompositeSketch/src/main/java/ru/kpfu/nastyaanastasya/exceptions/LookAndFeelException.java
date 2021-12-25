package ru.kpfu.nastyaanastasya.exceptions;

public class LookAndFeelException extends RuntimeException{

    public LookAndFeelException() {
    }

    public LookAndFeelException(String message) {
        super(message);
    }

    public LookAndFeelException(String message, Throwable cause) {
        super(message, cause);
    }

    public LookAndFeelException(Throwable cause) {
        super(cause);
    }
}
