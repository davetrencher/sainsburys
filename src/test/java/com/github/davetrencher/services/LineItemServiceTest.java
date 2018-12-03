package com.github.davetrencher.services;

import static com.github.davetrencher.util.TestHelper.getBodyFromFile;
import static com.github.davetrencher.util.TestHelper.givenWireMockReturnsData;
import static com.github.davetrencher.util.TestHelper.givenWireMockURL;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.github.davetrencher.model.LineItem;
import com.github.davetrencher.util.TestHelper;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;

public class LineItemServiceTest {

    private static final String URL_HOME_PAGE = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
    private static final String URL_STRAWBERRIES_RELATIVE = "../../../../../../shop/gb/groceries/berries-cherries-currants/sainsburys-british-strawberries-400g.html";
    private static final String URL_STRAWBERRIES_ABSOLUTE = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-british-strawberries-400g.html";



    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(9999);
    @Rule
    public WireMockClassRule instanceRule = wireMockRule;

    private LineItemService subject;

    @Before
    public void setUp() {
        this.subject = new LineItemService();
    }

    @Test
    public void getLineItems() throws IOException {

        String productPage = TestHelper.givenWebsiteWithLineItems(this.getClass());

        List<LineItem> lineItems = subject.getLineItems(productPage);

        assertThat(lineItems.size(), is(1));

    }

    @Test
    public void convertRelativeURLToAbsoluteConvertsLinks() throws MalformedURLException {

        String absoluteUrl = subject.convertRelativeURLToAbsolute(URL_HOME_PAGE, URL_STRAWBERRIES_RELATIVE);

        assertThat(absoluteUrl, is(URL_STRAWBERRIES_ABSOLUTE));

    }


}