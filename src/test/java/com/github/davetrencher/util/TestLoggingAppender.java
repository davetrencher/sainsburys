package com.github.davetrencher.util;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Plugin(name="TestLoggingAppender", category="Core", elementType="appender", printObject=true)
public class TestLoggingAppender extends AbstractAppender {

    private final List<LogEvent> logs = new ArrayList<>();

    private TestLoggingAppender(String name, Filter filter,
                                  Layout layout, final boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }

    @Override
    public void append(LogEvent event) {
        logs.add(event);
    }

    public void clearMessages ()
    {
        logs.clear();
    }

    public String getMessage (int index){
        if (logs.isEmpty()) {
            return null;
        }
        return logs.get(index).getMessage().getFormattedMessage();
    }

    public List<String> getMessages() {
        if (logs.isEmpty()) {
            return null;
        }

        return logs.stream().map(logEvent -> logEvent.getMessage().getFormattedMessage()).collect(Collectors.toList());
    }

    // Your custom appender needs to declare a factory method
    // annotated with `@PluginFactory`. Log4j will parse the configuration
    // and call this factory method to construct an appender instance with
    // the configured attributes.
    @PluginFactory
    static TestLoggingAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout layout,
            @PluginElement("Filter") final Filter filter,
            @PluginAttribute("otherAttribute") String otherAttribute) {
        if (name == null) {
            LOGGER.error("No name provided for TestLog4j2Appender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new TestLoggingAppender(name, filter, layout, true);
    }

}
