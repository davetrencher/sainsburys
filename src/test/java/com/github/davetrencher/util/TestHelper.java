package com.github.davetrencher.util;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import com.github.davetrencher.config.Config;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TestHelper {

    private static final String TEXT_HTML = "text/html";

    private static final String BERRIES_CHERRIES_CURRANTS_SINGLE_ITEM = "berries-cherries-currants-one-item.html";
    private static final String BRITISH_STRAWBERRIES = "sainsburys-british-strawberries-400g.html";
    private static final String URL_GROCERIES_SECTION = "serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/";
    private static final String URL_LINE_ITEM_SECTION = "serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/";

    private TestHelper() {

    }

    public static void givenWireMockReturnsData(String url, int status, String body) throws IOException {

        stubFor(get(urlEqualTo(new URL(url).getPath()))
                .willReturn(aResponse()
                        .withHeader("Content-Type", TEXT_HTML)
                        .withStatus(status)
                        .withBody(body)));

    }

    public static String getBodyFromFile(Class clazz, String fileName) throws IOException {

        File file = new File(clazz.getResource(fileName).getFile());
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);

    }

    public static String givenWireMockURL(String page) throws IOException {
        return Config.getInstance().get("url") +page;
    }

    public static String givenWebsiteWithLineItems(Class clazz) throws IOException {

        String productPage = givenWireMockURL(URL_GROCERIES_SECTION +BERRIES_CHERRIES_CURRANTS_SINGLE_ITEM);
        String productBody = getBodyFromFile(clazz, BERRIES_CHERRIES_CURRANTS_SINGLE_ITEM);
        givenWireMockReturnsData(productPage, HttpURLConnection.HTTP_OK, productBody);

        String lineItemPage = givenWireMockURL(URL_LINE_ITEM_SECTION +BRITISH_STRAWBERRIES);
        String lineItemBody = getBodyFromFile(clazz, BRITISH_STRAWBERRIES);
        givenWireMockReturnsData(lineItemPage, HttpURLConnection.HTTP_OK, lineItemBody);

        return productPage;

    }

    public static TestLoggingAppender setUpLogAppender(Class clazz) {

        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();
        TestLoggingAppender appender = TestLoggingAppender.createAppender("TestAppender", null, null, null);
        appender.start();
        config.addAppender(appender);

        Logger logger = (Logger) LogManager.getLogger(clazz);
        logger.addAppender(appender);

        ctx.updateLoggers();

        return appender;
    }


}
