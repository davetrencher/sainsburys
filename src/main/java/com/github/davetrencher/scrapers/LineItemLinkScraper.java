package com.github.davetrencher.scrapers;

import com.github.davetrencher.config.Config;

import java.io.IOException;

/**
 * This provides the CSS ref that will be used to scape the line item links.
 */
public class LineItemLinkScraper extends LinkScraper {

    @Override
    public String getCssRef() throws IOException {
        return Config.getInstance().get("cssRef");
    }
}
