package com.github.davetrencher;

import com.github.davetrencher.config.Config;
import com.github.davetrencher.exception.FormattingException;
import com.github.davetrencher.formatters.Formatters;
import com.github.davetrencher.model.LineItem;
import com.github.davetrencher.model.result.LineItemResult;
import com.github.davetrencher.model.result.Result;
import com.github.davetrencher.services.LineItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * This is the initial entry point to the application.
 *
 * The Application calls a URL and then processes the data returning data as JSON.
 *
 */
public class Application {

    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args)  {

        String url = getUrl(args);

        if (url == null) {
            logger.error("No url found unable to obtain product data, please check configuration.");
            System.exit(0);
        }

        scrapeAndOutputData(url);

    }

    private static void scrapeAndOutputData(String url) {
        try {

            List<LineItem> lineItems = new LineItemService().getLineItems(url);
            Result result = new LineItemResult(lineItems);

            logger.info(Formatters.JSON.getInstance().format(result));

        } catch (IOException | FormattingException e) {
            logger.error("Unable to get line item data from: " +url, e);
        }
    }

    private static String getUrl(String[] args) {

        try {
            if (args != null && args.length == 1) {
                return args[0];
            }

            return Config.getInstance().get("url");

        } catch (IOException ioe) {
            logger.error("Unable to load Url from config", ioe);
        }

        return null;

    }
 }
