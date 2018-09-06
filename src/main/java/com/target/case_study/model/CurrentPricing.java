package com.target.case_study.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Collection of current product pricing and currency code information
 */
@Document(collection = "current_pricing")
public class CurrentPricing {

    @Id
    @JsonIgnore
    public ObjectId _id;

    public String productId;
    public Double value;
    public String currency_code;

    public CurrentPricing(){}

    public CurrentPricing(Double value, String currency_code) {
        this.value = value;
        this.currency_code = currency_code;
    }

    public ObjectId get_id() {
        return _id;
    }
    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    @JsonIgnore
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }
}
