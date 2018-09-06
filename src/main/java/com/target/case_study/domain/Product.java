package com.target.case_study.domain;

import org.springframework.data.annotation.Id;

public class Product {

    @Id
    private Integer id;

    private String name;

    private CurrentPricing current_price;

    public void setName(String name) {
        this.name = name;
    }

    public CurrentPricing getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(CurrentPricing current_price) {
        this.current_price = current_price;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName() {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setProductInfo(Integer id, String name, CurrentPricing current_price) {
        this.id = id;
        this.name = name;
        this.current_price = current_price;
    }

    public Product(Integer id, String name, CurrentPricing current_price) {
        this.id = id;
        this.name = name;
        this.current_price = current_price;
    }
}
