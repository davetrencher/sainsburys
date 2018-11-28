package com.github.davetrencher.config;

import java.io.IOException;
import java.util.Properties;

/**
 * Class to provide access to the configuration.
 *
 * It should only be possible to load this configuration and not be able to amend it
 * within the program other than reloading it. (Possible Improvement).
 */
public class Config {

    private static final String FILE_NAME = "./config.properties";

    /**
     * Singleton instance of the config.  We only want to have one copy of this configuration.
     */
    private static Config config;

    /**
     * Holds the properties, This should never be returned as it's mutable.
     */
    private final Properties props = new Properties();

    /**
     * Method to get hold of the Config object.
     * @return  a Config object that has been populated with the configuration held in the properties file.
     * @throws IOException thrown if we can't load the file.
     */
    public static Config getInstance() throws IOException {
        if (config == null) {
            config = new Config();
        }

        return config;
    }

    /**
     * Constructor made final as we should only ever instantiate through the getInstance() method.
     * @throws IOException thrown if there is an error loading the config file.
     */
    private Config() throws IOException {
        initialise();
    }

    public String get(String propertyName) {
        return (String)props.get(propertyName);
    }

    private void initialise() throws IOException {

        props.load(this.getClass().getResourceAsStream(FILE_NAME));

    }

}
