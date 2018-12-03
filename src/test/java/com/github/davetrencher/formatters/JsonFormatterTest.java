package com.github.davetrencher.formatters;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;

import com.github.davetrencher.exception.FormattingException;
import com.github.davetrencher.model.LineItem;
import com.github.davetrencher.model.Total;
import com.github.davetrencher.model.result.LineItemResult;
import com.github.davetrencher.model.result.Result;
import com.github.davetrencher.util.TestHelper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JsonFormatterTest {

    private Formatter subject;

    @Before
    public void setup() {
        subject = new JsonFormatter();
    }

    @Test
    public void formatReturnsItemsAsJson() throws Exception {

        LineItemResult result = (LineItemResult)givenResult();

        String response = subject.format(result);

        String expectedResponse = TestHelper.getBodyFromFile(this.getClass(), "JsonFormatterTest-Sample1.json");

        assertThat(response, equalToIgnoringWhiteSpace(expectedResponse));

    }

    private Result givenResult() throws IOException {

        List<LineItem> lineItems = new ArrayList<>();

        lineItems.add(createLineItem("item1", new BigDecimal(1.75), 33));
        lineItems.add(createLineItem("item2", new BigDecimal(2.75), null));
        lineItems.add(createLineItem("item3", new BigDecimal(1.00), 44));
        lineItems.add(createLineItem("item4", new BigDecimal(0.75), 55));

        return new LineItemResult(lineItems);

    }

    private LineItem createLineItem(String title, BigDecimal unitPrice, Integer kCalPer100g) throws IOException {

        return new LineItem.Builder(title)
                                        .unitPrice(unitPrice)
                                        .description(title +" Description")
                                        .kCalPer100g(kCalPer100g)
                                        .build();
    }


}