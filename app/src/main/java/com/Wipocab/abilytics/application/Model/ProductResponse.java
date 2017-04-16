package com.Wipocab.abilytics.application.Model;

/**
 * Created by gautam on 11/1/16.
 */

public class ProductResponse extends ServerResponse{
    private ProductVersion[] products;
    private ProductVersion[][] prod;

    public ProductVersion[] getProducts() {
        return products;
    }
    public ProductVersion[][] getProd(){
        return prod;
}}
