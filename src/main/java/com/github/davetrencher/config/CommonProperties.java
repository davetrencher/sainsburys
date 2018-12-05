package com.github.davetrencher.config;

import java.io.IOException;

/**
 * Set of common properties and the ability to obtain the value from a properties file.
 */
public enum CommonProperties {

    URL("url"),
    VAT_RATE("vatRate");

    private String property;

    CommonProperties(String property) {
        this.property = property;
    }

    /**
     * Return the value of the property specified.
     * @return the value held in the properties file for this property
     * @throws IOException thrown if we can't access the properties.
     */
    public String getProperty() throws IOException {
        return Config.getInstance().get(property);
    }
}
