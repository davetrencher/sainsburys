package com.github.davetrencher.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

/**
 * Class to provide access to the configuration.
 *
 * It should only be possible to load this configuration and not be able to amend it
 * within the program other than reloading it. (Possible Improvement).
 */
public class Config {

    private static final Logger logger = LogManager.getLogger(Config.class);

    /**
     * CommonProperties filename.
     */
    public static final String FILE_NAME = "/config/config.properties";

    /**
     * Allow config filename override.
     */
    private static String fileNameOverride;

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

    private void initialise() throws IOException {

        String fileName = StringUtils.isEmpty(fileNameOverride) ? FILE_NAME : fileNameOverride;
        logger.info("Loading configuration from file: {}", fileName);
        props.load(this.getClass().getResourceAsStream(fileName));

    }

    /**
     * Allow overriding of the config files name.
     * @param fileNameOverride fileName should be relative or root relative on the classpath.
     */
    public static void setFileNameOverride(String fileNameOverride) {
        config = null;
        Config.fileNameOverride = fileNameOverride;
    }

    /**
     * Constructor made final as we should only ever instantiate through the getInstance() method.
     * @throws IOException thrown if there is an error loading the config file.
     */
    private Config() throws IOException {
        initialise();
    }

    /**
     * Return the property corresponding to the name passed in.
     * @param propertyName the name of property that we are after.
     * @return the property value or null.
     */
    public String get(String propertyName) {
        return (String)props.get(propertyName);
    }

    /**
     * The standard VAT rate that will be applied to line items. This can be overriden per lineItem.
     * This should be pulled from config.
     * @return the standard VAT rate.
     * @throws IOException thrown if we can't obtain the Standard VAT rate from configuration
     */
    public BigDecimal getStandardVatRate() throws IOException {
        return new BigDecimal(CommonProperties.VAT_RATE.getProperty());
    }

}
