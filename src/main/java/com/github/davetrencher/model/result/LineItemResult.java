package com.github.davetrencher.model.result;

import com.github.davetrencher.model.LineItem;
import com.github.davetrencher.model.Total;

import java.util.Collections;
import java.util.List;

/**
 * Response object that will hold both the LineItems and also the Total fields.
 *
 */
public class LineItemResult implements Result {

    private List<LineItem> results;

    private Total total;

    /**
     * Constructor marked as private as this class relies upon having line items.
     */
    private LineItemResult() {

    }

    /**
     * This is the main constructor.  It uses the list of line items to generate both the results and total field.
     *
     * We set them both in this constructor so that we know that the total is in sync with the results on the object.
     * @param lineItems the individual line items from which we can also derive the total fields.
     */
    public LineItemResult(List<LineItem> lineItems) {
        this.results = Collections.unmodifiableList(lineItems);
        this.total = new Total(results);
    }

    /**
     * The line items that we originally passed in.
     * @return the list of line items.
     */
    public List<LineItem> getResults() {
        return results;
    }

    /**
     * The total object with total value fields e.g. gross and vat that are derived from the result.
     * @return the total object.
     */
    public Total getTotal() {
        return total;
    }

}
