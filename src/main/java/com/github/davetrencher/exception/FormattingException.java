package com.github.davetrencher.exception;

public class FormattingException extends Exception {

    public FormattingException() {
    }

    public FormattingException(String message) {
        super(message);
    }

    public FormattingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormattingException(Throwable cause) {
        super(cause);
    }

    public FormattingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
