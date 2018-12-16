package com.github.davetrencher;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.davetrencher.config.CommonProperties;
import com.github.davetrencher.aws.lambda.Request;
import com.github.davetrencher.model.LineItem;
import com.github.davetrencher.model.result.LineItemResult;
import com.github.davetrencher.services.LineItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Handler class so that we can run the application as an AWS Lambda.
 *
 * This takes a Request object as it's a GET that doesn't require parameters.
 *
 */
public class GetProductsLambdaHandler implements RequestHandler<Request, LineItemResult> {

    private static final Logger logger = LogManager.getLogger(GetProductsLambdaHandler.class);

    @Override
    public LineItemResult handleRequest(Request request, Context context)  {

        try {

            String productPageURL = CommonProperties.URL.getProperty();
            logger.info("Using url: " +productPageURL);
            List<LineItem> lineItems = new LineItemService().getLineItems(productPageURL);
            return new LineItemResult(lineItems);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
