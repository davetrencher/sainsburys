package com.github.davetrencher;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.github.davetrencher.config.Config;
import com.github.davetrencher.services.LineItemService;
import com.github.davetrencher.util.TestHelper;
import com.github.davetrencher.util.TestLoggingAppender;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;


public class ApplicationTest {

    private static final String TEST_FILE_NAME = "/config/config-application-test.properties";

    @ClassRule
    public static final WireMockClassRule wireMockRule = new WireMockClassRule(9999);
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
        Config.setFileNameOverride(null);
    }

    @Test
    public void mainReturnsScrapeData() throws IOException {

        Config.setFileNameOverride(Config.FILE_NAME);

        TestHelper.givenWebsiteWithLineItems(LineItemService.class);
        String[] args = {TEST_FILE_NAME};

        Application.main(args);

        List<String> logs = testLoggingAppender.getMessages();

        String expectedResponse = TestHelper.getBodyFromFile(this.getClass(),"scrapeData-sample1.json");
        String actualResponse = logs.get(logs.size()-1);
        assertThat(sanitiseLB(actualResponse), is(expectedResponse));

    }

    private String sanitiseLB(String data) {
        return StringUtils.replace(data, "\r\n", "\n");
    }


}