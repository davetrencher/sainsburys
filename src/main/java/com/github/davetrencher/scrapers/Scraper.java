package com.github.davetrencher.scrapers;

import java.io.IOException;

/**
 * Provides a common scrape method that all scrapers should implement.
 * @param <T> the type of object that a scraper will return.
 */
public interface Scraper<T> {

    int TIMEOUT_MILLISECONDS_DEFAULT = 10 * 1000;

    /**
     * This will scrape the page and return data based on the scraping behaviour of the implementing object.
     * @param pageURLToScrape the pageURL that we will scrape for data.
     * @return the data that we wish to find on the page.
     * @throws IOException thrown if we can't access the data.
     */
    T scrape(String pageURLToScrape) throws IOException;
}
