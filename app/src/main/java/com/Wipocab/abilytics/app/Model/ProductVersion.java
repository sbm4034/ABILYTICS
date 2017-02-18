package com.Wipocab.abilytics.app.Model;

/**
 * Created by gautam on 11/1/16.
 */

public class ProductVersion {
    private String p_id;
    private String p_name;
    private String p_info;
    private String p_sold;
    private boolean isliked;
    private String product_name;
    private String id;
    private String Product_code;
    private String NumberperDiaofWire;
    private String NumberAreaofCond;
    private String PriceperCoil;
    private String Length;
    private String Pkg;

    public String getLength() {
        return Length;
    }

    public void setLength(String length) {
        Length = length;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }



    public String getPkg() {
        return Pkg;
    }

    public void setPkg(String pkg) {
        Pkg = pkg;
    }


    public String getNumberperDiaofWire() {
        return NumberperDiaofWire;
    }

    public void setNumberperDiaofWire(String numberperDiaofWire) {
        NumberperDiaofWire = numberperDiaofWire;
    }

    public String getNumberAreaofCond() {
        return NumberAreaofCond;
    }

    public void setNumberAreaofCond(String numberAreaofCond) {
        NumberAreaofCond = numberAreaofCond;
    }

    public String getPriceperCoil() {
        return PriceperCoil;
    }

    public void setPriceperCoil(String priceperCoil) {
        PriceperCoil = priceperCoil;
    }

    public String getProduct_code() {
        return Product_code;
    }

    public void setProduct_code(String product_code) {
        Product_code = product_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String subproducts_id;

    public String getSubproducts_id() {
        return subproducts_id;
    }

    public void setSubproducts_id(String subproducts_id) {
        this.subproducts_id = subproducts_id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getP_info() {
        return p_info;
    }

    public void setP_info(String p_info) {
        this.p_info = p_info;
    }

    public String getP_sold() {
        return p_sold;
    }

    public void setP_sold(String p_sold) {
        this.p_sold = p_sold;
    }

    public boolean isliked() {
        return isliked;
    }

    public void setIsliked(boolean isliked) {
        this.isliked = isliked;
    }
}
