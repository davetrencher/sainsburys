package com.github.davetrencher;

import com.github.davetrencher.config.Config;
import com.github.davetrencher.model.LineItem;
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

        String url = null;
        try {
            url = Config.getInstance().get("url");
            String cssRef = Config.getInstance().get("cssRef");

            List<LineItem> lineItems = new LineItemService().getLineItems(url);


        } catch (IOException ioe) {
            logger.error("Unable to get line item data from: " +url, ioe);
        }

        //output prices

    }
}
