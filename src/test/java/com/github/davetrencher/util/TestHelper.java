package com.github.davetrencher.util;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import com.github.davetrencher.config.Config;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TestHelper {

    private static final String TEXT_HTML = "text/html";

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



}
