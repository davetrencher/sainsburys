package com.github.davetrencher.scrapers;

import static com.github.davetrencher.scrapers.LineItemScraper.Selector.*;

import com.github.davetrencher.config.Config;
import com.github.davetrencher.model.LineItem;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;


/**
 * This will scrape the given page and return a line item object populated with the information from the page.
 *
 */
public class LineItemScraper implements Scraper<LineItem> {

    private static final String KCAL_UPPER_CASE = "kcal";

    /**
     * Holds the selectors used in this scraper.
     */
    public enum Selector {

        TITLE("lineItemPage.selector.title"),
        KCAL_PER_100G("lineItemPage.selector.kCalPer100g"),
        UNIT_PRICE("lineItemPage.selector.unitPrice"),
        DESCRIPTION("lineItemPage.selector.description");

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

    public LineItem scrape(String pageURLToScrape) throws IOException {

        Connection.Response response = Jsoup.connect(pageURLToScrape)
                .followRedirects(true)
                .timeout(TIMEOUT_MILLISECONDS_DEFAULT)
                .execute();

        if (response.statusCode() == HttpURLConnection.HTTP_OK) {
            Document doc = response.parse();

            Element title = doc.selectFirst(TITLE.getCssSelector());
            Element kCalPer100g = doc.selectFirst(KCAL_PER_100G.getCssSelector());
            Element unitPrice = doc.selectFirst(UNIT_PRICE.getCssSelector());
            Element description = doc.selectFirst(DESCRIPTION.getCssSelector());

            return new LineItem.Builder(toString(title))
                                            .kCalPer100g(kCalPer100gToInteger(kCalPer100g))
                                            .unitPrice(toBigDecimal(unitPrice))
                                            .description(toString(description))
                                            .build();

        }

        return null;

    }

    private Integer kCalPer100gToInteger(Element kCalPer100g) {


        if (kCalPer100g == null || StringUtils.isEmpty(kCalPer100g.text())) {
            return null;
        }

        String numberPart = StringUtils.substringBefore(kCalPer100g.text().toLowerCase(), KCAL_UPPER_CASE);

        if (NumberUtils.isDigits(numberPart)) {
            return new Integer(numberPart);
        }

        return null;
    }

    private String toString(Element element) {

        if (element != null) {
            return element.text();
        }

        return null;

    }

    private BigDecimal toBigDecimal(Element element) {

        if (element != null) {
            String unitPrice = RegExUtils.replaceAll(element.text(),"[^\\d.]", "");
            return new BigDecimal(unitPrice);
        }

        return null;

    }

}
