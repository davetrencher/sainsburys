package com.github.davetrencher.formatters;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.davetrencher.exception.FormattingException;
import com.github.davetrencher.model.result.Result;

/**
 * Formats a list of line items in to Json format.
 *
 * The values for each item is output and then followed by a total gross amount and total VAT amount.
 */
public class JsonFormatter implements Formatter {

    @Override
    public String format(Result data) throws FormattingException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_NULL);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException jpe) {
            throw new FormattingException(jpe);
        }

    }
}
