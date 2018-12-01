package com.github.davetrencher.scrapers;

import com.github.davetrencher.config.Config;

import java.io.IOException;

public class LineItemLinkScraper extends LinkScraper {

    @Override
    public String getCssRef() throws IOException {
        return Config.getInstance().get("cssRef");
    }
}
