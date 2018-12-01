package com.github.davetrencher.scrapers;

import static com.github.davetrencher.util.TestHelper.getBodyFromFile;
import static com.github.davetrencher.util.TestHelper.givenWireMockReturnsData;
import static com.github.davetrencher.util.TestHelper.givenWireMockURL;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import com.github.davetrencher.config.Config;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LineItemLinkScraperTest implements ScraperTest {

    private static final String DATA_BERRIES_CHERRIES_CURRENTS = "berries-cherries-currants6039.html";

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(9999);
    @Rule
    public WireMockClassRule instanceRule = wireMockRule;

    private LinkScraper subject;

    private String wireMockUrl;

    @Before
    public void setUp() throws Exception {

        subject = new LineItemLinkScraper();
    }

    @Test
    public void scrapeRetrievesLinksFromPage() throws Exception {

        wireMockUrl = givenWireMockURL(DATA_BERRIES_CHERRIES_CURRENTS);

        String body = getBodyFromFile(this.getClass(), DATA_BERRIES_CHERRIES_CURRENTS);

        givenWireMockReturnsData(wireMockUrl, HttpURLConnection.HTTP_OK, body);

        List<String> links = subject.scrape(wireMockUrl);

        assertThat(links, hasSize(17));

    }

    @Test(expected = IOException.class)
    public void scrapeThrowsExceptionIfServerError() throws Exception {

        givenWireMockReturnsData(wireMockUrl, HttpURLConnection.HTTP_INTERNAL_ERROR, SERVER_ERROR);

        subject.scrape(wireMockUrl);

    }
    
}