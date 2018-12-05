package com.github.davetrencher.services;

import com.github.davetrencher.model.LineItem;
import com.github.davetrencher.scrapers.LineItemLinkScraper;
import com.github.davetrencher.scrapers.LineItemScraper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class obtain a list of line items from a source.
 */
public class LineItemService {

    /**
     * Return all the line items on a given URL using the css selector.
     * @param url the url that we want to check.
     * @return a list of line items.
     * @throws IOException thrown if we can't access the relevant web pages.
     */
    public List<LineItem> getLineItems(String url) throws IOException {

        List<String> links = new LineItemLinkScraper().scrape(url);
        final LineItemScraper lineItemScraper = new LineItemScraper();

        List<LineItem> list = new ArrayList<>();
        for (String relativeLink : links) {
            String absoluteLink = convertRelativeURLToAbsolute(url, relativeLink);
            LineItem scrape = lineItemScraper.scrape(absoluteLink);
            list.add(scrape);
        }

        return list;

    }

    String convertRelativeURLToAbsolute(String relativeUrl, String relativeURL) throws MalformedURLException {

        URL baseUrl = new URL(relativeUrl);
        return new URL(baseUrl, relativeURL).toString();

    }
}
