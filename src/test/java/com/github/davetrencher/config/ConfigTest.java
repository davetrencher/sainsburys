package com.github.davetrencher.config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class ConfigTest {

    private static final String EXPECTED_CONFIG_URL = "http://localhost:9999/";

    private Config subject;

    @Before
    public void setUp() throws Exception {
        subject = Config.getInstance();
    }

    @Test
    public void configReturnsURL() {

        assertThat(subject.get("url"), is(EXPECTED_CONFIG_URL));

    }

    @Test
    public void getStandardVatRate() {

        assertThat(subject.getStandardVatRate(), is(new BigDecimal("20.0")));

    }
}