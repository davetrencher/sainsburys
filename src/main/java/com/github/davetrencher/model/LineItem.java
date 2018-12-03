package com.github.davetrencher.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.davetrencher.config.Config;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * LineItem class holding the details of an individual line item.
 */
public class LineItem {

    private String title;
    private Integer kcalPer100g;
    private BigDecimal unitPrice;
    private String description;
    private BigDecimal vatRate;

    /**
     * Title of the line item e.g. "Sainsbury's Blueberries 200g"
     * @return the title of the line itme.
     */
    public String getTitle() {
        return title;
    }

    /**
     * The number of calories per 100 grams.
     *
     * @return the number of calories or null if not set.
     */
    public Integer getKcalPer100g() {
        return kcalPer100g;
    }

    /**
     * The unit price of an item.
     * This is a BigDecimal as it has better accuracy than using floats when handling currency.
     * @return the unit price e.g. 1.75
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * The description of the line item.
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the vatRate applicable for this product.
     * Each item may have a different VAT rate.
     * @return the vatRate.
     */
    @JsonIgnore
    public BigDecimal getVatRate() {
        return vatRate;
    }

    /**
     * Builder class for a builder pattern.
     *
     * Building up the line item using a builder pattern gives us a couple of benefits:
     *  - It enables us to add fields as we parse them.
     *  - LineItem is effectively immutable once it is built as we don't need setters.
     *  - We can also avoid a constructor with lots of parameters which runs the risk of transposition.
     *
     *  The constructor sets a standard VAT rate which is obtained from configuration.  This could be overridden in
     *  the future with lineItem specific VAT rates.
     */
    public static class Builder {

        private String title;
        private Integer kcalPer100g;
        private BigDecimal unitPrice;
        private String description;
        private BigDecimal vatRate;

        private Builder() throws IOException {
            vatRate = Config.getInstance().getStandardVatRate();
        }

        public Builder(String title) throws IOException {
            this();
            this.title = title;
        }

        public Builder kCalPer100g(Integer kcalPer100g) {
            this.kcalPer100g = kcalPer100g;
            return this;
        }

        public Builder unitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice.setScale(2, RoundingMode.HALF_UP);
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public LineItem build() {

            LineItem lineItem = new LineItem();
            lineItem.title = this.title;
            lineItem.kcalPer100g = this.kcalPer100g;
            lineItem.unitPrice = this.unitPrice;
            lineItem.description = this.description;
            lineItem.vatRate = this.vatRate;

            return lineItem;
        }

    }

}
