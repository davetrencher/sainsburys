package com.github.davetrencher.scrapers;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Classes that plan to parse links from pages will exted this class.
 */
public abstract class LinkScraper implements Scraper<List<String>> {

    /**
     * Scrape all the URLs from a page.
     * @param pageURLToScrape the URL of the page that we want to scrape.
     * @return a list of URLs that we find.
     */
    public List<String> scrape(String pageURLToScrape) throws IOException {

        Connection.Response response = Jsoup.connect(pageURLToScrape)
                                .followRedirects(true)
                                .timeout(Scraper.TIMEOUT_MILLISECONDS_DEFAULT)
                                .execute();

        List<String> lineItemLinks = new ArrayList<>();

        if (response.statusCode() == HttpURLConnection.HTTP_OK) {

            Document doc = response.parse();
            for (Element a : doc.select(getCssRef())) {
                lineItemLinks.add(a.attributes().get("href"));
            }
        }

        return lineItemLinks;

    }

    /**
     * This method should return the cssRef that will identify the links that will be scraped from the page.
     * @return the cssRef that will be used to identify links within the page.
     * @throws IOException thrown if we can't access the page.
     */
    public abstract String getCssRef() throws IOException;
}
