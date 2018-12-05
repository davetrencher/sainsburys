package com.github.davetrencher.scrapers;

import static com.github.davetrencher.scrapers.LineItemLinkScraper.Selector.*;

import com.github.davetrencher.config.Config;

import java.io.IOException;

/**
 * This provides the CSS ref that will be used to scape the line item links.
 */
public class LineItemLinkScraper extends LinkScraper {

    /**
     * Holds the selectors used in this scraper.
     */
    public enum Selector {

        LINK("productPage.selector.link");

        private String cssSelector;

        Selector(String cssSelector) {
            this.cssSelector = cssSelector;
        }

        /**
         * Return the value of the cssSelector specified.
         * @return the value held in the properties file for this cssSelector.
         * @throws IOException thrown if we can't access the properties.
         */
        public String getCssSelector() throws IOException {
            return Config.getInstance().get(cssSelector);
        }
    }

    @Override
    public String getCssRef() throws IOException {
        return LINK.getCssSelector();
    }
}
