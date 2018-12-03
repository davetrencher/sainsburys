package com.github.davetrencher.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class TotalTest {

    private Total subject;

    @Before
    public void setUp() throws IOException {
        List<LineItem> lineItems = givenListOfLineItems();
        subject = new Total(lineItems);
    }

    @Test
    public void getGross() {

        assertThat(subject.getGross(), is(new BigDecimal("6.50")));

    }

    @Test
    public void getVat() {

        assertThat(subject.getVat(), is(new BigDecimal("1.08")));
    }

    private List<LineItem> givenListOfLineItems() throws IOException {

        List<LineItem> lineItems = new ArrayList<>();
        lineItems.add(givenLineItemWithUnitPrice("lineItem1", new BigDecimal(1.75)));
        lineItems.add(givenLineItemWithUnitPrice("lineItem2", new BigDecimal(1.50)));
        lineItems.add(givenLineItemWithUnitPrice("lineItem3", new BigDecimal(2.25)));
        lineItems.add(givenLineItemWithUnitPrice("lineItem4", new BigDecimal(1.00)));

        return lineItems;
    }

    private LineItem givenLineItemWithUnitPrice(String title, BigDecimal unitPrice) throws IOException {

        return new LineItem.Builder(title).unitPrice(unitPrice).build();

    }

}