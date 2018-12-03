package com.github.davetrencher.exception;

/**
 * Exception thrown when it's not been possible to format the object for output.
 *
 * This exception will probably just wrap the underlying exception but provides a single Exception for us to handle.
 */
public class FormattingException extends Exception {

    public FormattingException(Throwable cause) {
        super(cause);
    }

}
