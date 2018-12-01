package com.github.davetrencher.scrapers;

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

    public LineItem scrape(String pageURLToScrape) throws IOException {

        Connection.Response response = Jsoup.connect(pageURLToScrape)
                .followRedirects(true)
                .timeout(TIMEOUT_MILLISECONDS_DEFAULT)
                .execute();

        if (response.statusCode() == HttpURLConnection.HTTP_OK) {
            Document doc = response.parse();

            Element title = doc.selectFirst("div.productTitleDescriptionContainer h1");
            Element kCalPer100g = doc.selectFirst("table.nutritionTable tbody tr:nth-child(2) td"); //check contains kcal
            Element unitPrice = doc.selectFirst("div.pricingAndTrolleyOptions p.pricePerUnit");
            Element description = doc.selectFirst(".productText p");

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
