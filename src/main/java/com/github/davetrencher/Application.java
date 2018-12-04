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

        String fileName = getFileName(args);

        try {

            Config.setFileNameOverride(fileName);
            String productPageURL = Config.getInstance().get("url");
            List<LineItem> lineItems = new LineItemService().getLineItems(productPageURL);
            Result result = new LineItemResult(lineItems);

            logger.info(Formatters.JSON.getInstance().format(result));

        } catch (IOException ioe) {
            logger.error("Issue retrieving lineitem data", ioe);
        } catch (FormattingException fe) {
            logger.error("Issue outputting lineitem data", fe);
        }
    }

    private static String getFileName(String[] args) {

        if (args != null && args.length == 1) {
            return args[0];
        }

        return Config.FILE_NAME;

    }
 }
