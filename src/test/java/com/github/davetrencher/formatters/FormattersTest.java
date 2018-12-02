package com.github.davetrencher.formatters;

import static com.github.davetrencher.formatters.Formatters.JSON;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;


public class FormattersTest {

    @Test
    public void getInstance() {

        assertThat(JSON.getInstance(), is(instanceOf(JsonFormatter.class)));


    }

    @Test
    public void getInstanceReturnsEnumByName() {

        assertThat(Formatters.getInstance(JSON.name()),is(instanceOf(JsonFormatter.class)));

    }

    @Test(expected = IllegalArgumentException.class)
    public void getInstanceThrowsExceptionIfNameNotFound() {{

    }
        assertThat(Formatters.getInstance("foo"),is(instanceOf(JsonFormatter.class)));
    }
}