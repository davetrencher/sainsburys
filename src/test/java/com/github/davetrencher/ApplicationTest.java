package com.github.davetrencher;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

import com.github.davetrencher.config.Config;
import com.github.davetrencher.scrapers.Scraper;
import com.github.davetrencher.scrapers.ScraperTest;
import com.github.davetrencher.services.LineItemService;
import com.github.davetrencher.util.TestHelper;
import com.github.davetrencher.util.TestLoggingAppender;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;


public class ApplicationTest {

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(9999);
    @Rule
    public WireMockClassRule instanceRule = wireMockRule;

    private TestLoggingAppender testLoggingAppender;

    @Before
    public void setupLogging() {
        testLoggingAppender = TestHelper.setUpLogAppender(Application.class);
    }

    @After
    public void tearDown() {
        testLoggingAppender.clearMessages();
    }

    @Test
    public void mainReturnsScrapeData() throws IOException {

        String url = TestHelper.givenWebsiteWithLineItems(LineItemService.class);
        String[] args = {url};

        Application.main(args);

        List<String> logs = testLoggingAppender.getMessages();

        String expectedResponse = TestHelper.getBodyFromFile(this.getClass(),"scrapeData-sample1.json");
        assertThat(logs.get(logs.size()-1), is(expectedResponse));


    }


}