package com.github.davetrencher.formatters;

import java.util.function.Supplier;

/**
 * Enum of formatters that can be used to output the data in different ways.
 */
public enum Formatters {

    /**
     * Outputs the data in Json format.
     */
    JSON(JsonFormatter::new);

    private Supplier<Formatter> formatter;

    /**
     * Takes a supplier so that we can use the enum as a factory.
     * @param formatter the formatter as a supplier object.
     */
    Formatters(Supplier<Formatter> formatter) {

        this.formatter = formatter;
    }

    /**
     * Returns a new instance of the formatter associated with this enum.
     * @return a formatter object.
     */
    public Formatter getInstance() {
        return formatter.get();
    }

    /**
     * Takes in a name corresponding to the
     * @param name the name of the enum that we will use to get an instance.
     * @return an instance of the formatter.
     */
    public static Formatter getInstance(String name) {

        return Formatters.valueOf(name).getInstance();
    }

}
