package com.github.davetrencher.scrapers;

import static com.github.davetrencher.util.TestHelper.getBodyFromFile;
import static com.github.davetrencher.util.TestHelper.givenWireMockReturnsData;
import static com.github.davetrencher.util.TestHelper.givenWireMockURL;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import com.github.davetrencher.config.Config;
import com.github.davetrencher.model.LineItem;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.List;


public class LineItemScraperTest implements ScraperTest {

    private static final String BRITISH_STRAWBERRIES = "sainsburys-british-strawberries-400g.html";
    private static final String BRITISH_MIXED_BERRY_NO_KCAL = "sainsburys-mixed-berry-twin-pack-200g-7696255-p-44_no_kcal.html";
    private static final String BLACK_CURRANTS = "Sainsburys_Blackcurrants_150g_Sainsburys.html";

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(9999);
    @Rule
    public WireMockClassRule instanceRule = wireMockRule;

    private LineItemScraper subject;

    private String wireMockUrl;

    @Before
    public void Setup() throws IOException {

        subject = new LineItemScraper();
    }

    @Test
    public void scrapeReturnsLineItemWithKCal() throws IOException {

        wireMockUrl = givenWireMockURL(BRITISH_STRAWBERRIES);

        String body = getBodyFromFile(this.getClass(), BRITISH_STRAWBERRIES);

        givenWireMockReturnsData(wireMockUrl, HttpURLConnection.HTTP_OK, body);

        LineItem lineItem = subject.scrape(wireMockUrl);

        assertThat(lineItem.getTitle(), is("Sainsbury's Strawberries 400g"));
        assertThat(lineItem.getKcalPer100g(), is(33));
        assertThat(lineItem.getUnitPrice(), is(new BigDecimal(1.75)));
        assertThat(lineItem.getDescription(), is("by Sainsbury's strawberries"));

    }

    @Test
    public void scrapeReturnsLineItemWithoutKCal() throws IOException {

        wireMockUrl = givenWireMockURL(BLACK_CURRANTS);

        String body = getBodyFromFile(this.getClass(), BLACK_CURRANTS);

        givenWireMockReturnsData(wireMockUrl, HttpURLConnection.HTTP_OK, body);

        LineItem lineItem = subject.scrape(wireMockUrl);

        assertThat(lineItem.getTitle(), is("Sainsbury's Blackcurrants 150g"));
        assertThat(lineItem.getKcalPer100g(), is(nullValue()));
        assertThat(lineItem.getUnitPrice(), is(new BigDecimal(1.75)));
        assertThat(lineItem.getDescription(), is("Union Flag"));


    }

    @Test
    public void scrapeBlackCurrentsReturnsLineItemWithDescription() throws IOException {

        wireMockUrl = givenWireMockURL(BRITISH_MIXED_BERRY_NO_KCAL);

        String body = getBodyFromFile(this.getClass(), BRITISH_MIXED_BERRY_NO_KCAL);

        givenWireMockReturnsData(wireMockUrl, HttpURLConnection.HTTP_OK, body);

        LineItem lineItem = subject.scrape(wireMockUrl);

        assertThat(lineItem.getTitle(), is("Sainsbury's Mixed Berry Twin Pack 200g"));
        assertThat(lineItem.getKcalPer100g(), is(nullValue()));
        assertThat(lineItem.getUnitPrice(), is(new BigDecimal(2.75)));
        assertThat(lineItem.getDescription(), is("Mixed Berries"));


    }

    @Test(expected = IOException.class)
    public void scrapeThrowsExceptionIfServerError() throws Exception {

        givenWireMockReturnsData(wireMockUrl, HttpURLConnection.HTTP_INTERNAL_ERROR, SERVER_ERROR);

        subject.scrape(wireMockUrl);

    }
}