package com.github.davetrencher.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

public class LineItemTest {

    private static final String TITLE = "Sainsbury's Blueberries 200g";
    private static final Integer KCAL_PER_100G = 44;
    private static final BigDecimal UNIT_PRICE = new BigDecimal(1.75);
    private static final String DESCRIPTION = "by Sainsbury's blueberries";

    private LineItem subject;

    @Test
    public void lineItemBuilderFullyPopulatesLineItem() throws IOException {

        LineItem lineItem = new LineItem.Builder(TITLE)
                                        .kCalPer100g(KCAL_PER_100G)
                                        .unitPrice(UNIT_PRICE)
                                        .description(DESCRIPTION)
                                        .build();

        assertThat(lineItem.getTitle(), is(TITLE));
        assertThat(lineItem.getKcalPer100g(), is(KCAL_PER_100G));
        assertThat(lineItem.getUnitPrice(), is(UNIT_PRICE));
        assertThat(lineItem.getDescription(), is(DESCRIPTION));
    }


    @Test
    public void lineItemBuilderLeavesKCALNullIfNotSet() throws IOException  {

        LineItem lineItem = new LineItem.Builder(TITLE)
                .unitPrice(UNIT_PRICE)
                .description(DESCRIPTION)
                .build();

        assertThat(lineItem.getTitle(), is(TITLE));
        assertThat(lineItem.getKcalPer100g(), is(nullValue()));
        assertThat(lineItem.getUnitPrice(), is(UNIT_PRICE));
        assertThat(lineItem.getDescription(), is(DESCRIPTION));
    }


}