package com.github.davetrencher.config;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConfigTest {

    private Config subject;

    @Before
    public void setUp() throws Exception {
        subject = Config.getInstance();
    }

    @Test
    public void configReturnsURL() throws Exception {

        assertThat(subject.get("url"), is("https://localhost:8889/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html"));

    }


}