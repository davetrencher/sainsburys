package com.github.davetrencher.formatters;

import com.github.davetrencher.exception.FormattingException;

/**
 * Base Interface for classes that are used to format a list of line items.
 *
 * Each implementing formatter is responsible for formatting the list items.
 */
public interface Formatter<T> {

    /**
     * Takes a list of line items and returns them as a formatted String.
     * @param data a list of line items.
     * @return a String representation of the lines items.
     * @exception FormattingException if there is an issue formatting the data.
     */
    String format(T data) throws FormattingException;

}
