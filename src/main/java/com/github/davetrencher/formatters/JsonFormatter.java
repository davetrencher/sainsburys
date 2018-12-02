package com.github.davetrencher.formatters;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.davetrencher.exception.FormattingException;
import com.github.davetrencher.model.LineItem;

import java.util.List;

/**
 * Formats a list of line items in to Json format.
 *
 * The values for each item is output and then followed by a total gross amount and total VAT amount.
 */
public class JsonFormatter implements Formatter<List<LineItem>> {

    @Override
    public String format(List<LineItem> data) throws FormattingException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_NULL);
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException jpe) {
            throw new FormattingException(jpe);
        }

    }
}
