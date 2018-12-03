package com.github.davetrencher.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * This returns the total amounts based upon the list of line items passed in.
 */
public class Total {

    /**
     * Set the scale to 3 so we don't end up with rounding errors.
     */
    private static final int CURRENCY_SCALE = 3;

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    private BigDecimal gross;

    private BigDecimal vat;

    /**
     * Default constructor this should not be called to instantiate the class as we need to get the values from the
     * list of line items.  This is why it is marked as private.
     */
    private Total() {

    }

    /**
     * This should be used as the constructor to instantiate this object.
     * @param lineItems the line items that we wish to obtain the values from.
     */
    public Total(List<LineItem> lineItems) {

        this.gross = lineItems.stream()
                .map(LineItem::getUnitPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add)
                .setScale(2,RoundingMode.HALF_UP);

        this.vat = gross.subtract(lineItems.stream()
                .map(lineItem -> lineItem.getUnitPrice()
                                        .setScale(CURRENCY_SCALE,RoundingMode.HALF_UP)
                                        .divide(getVatDivisor(lineItem),RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO,BigDecimal::add)
                .setScale(2,RoundingMode.HALF_UP));

    }

    /**
     * Return the Gross Amount (total value including vat).
     * The method is public so that the Json serialization can access the field.
     * @return the gross amount.
     */
    public BigDecimal getGross() {
        return gross;
    }

    /**
     * Return the vat amount on the list of line items.
     * The method is public so that the Json serialization can access the field.
     * @return the amount of vat payable.
     */
    public BigDecimal getVat() {
        return vat;
    }

    private BigDecimal getVatDivisor(LineItem lineItem) {

        return new BigDecimal(1).setScale(CURRENCY_SCALE,RoundingMode.HALF_UP)
                .add(lineItem.getVatRate().setScale(CURRENCY_SCALE,RoundingMode.HALF_UP).divide(ONE_HUNDRED, RoundingMode.HALF_UP));
    }


}
