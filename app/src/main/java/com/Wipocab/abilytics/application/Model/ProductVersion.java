package com.Wipocab.abilytics.application.Model;

/**
 * Created by gautam on 11/1/16.
 */

public class ProductVersion {
    public String getMain_product_id() {
        return main_product_id;
    }

    private String main_product_id;
    private String p_id;
    private String p_name;
    private String p_info;
    private String p_sold;
    private boolean isliked;
    private String product_name;
    private String id;
    private String Product_code;
    private String NumberperDiaofWire;
    private String NumberperDiaofWireinch;
    private String NumberAreaofCond;
    private String PriceperCoil;
    private String Length;
    private String Pkg;
    private String NominalDiaofCond_04;
    private String NominalDiaofCond_05;
    private String 	NoofPair;
    private String 	Pricepermetre;
    private String Description;
    private String Priceper100mtrs;
    private String Priceper300mtrs;
    private String Priceper305mtrs;
    private String NominalAreaCond;
    private String 	one_Core;
    private String two_Core;
    private String three_Core;
    private int noi=1;

    public int getNoi() {
        return noi;
    }

    public void setNoi(int noi) {
        this.noi = noi;
    }

    public String getCart_price() {
        return cart_price;
    }

    public void setCart_price(String cart_price) {
        this.cart_price = cart_price;
    }

    private String cart_price;

    public String getNumberperDiaofWireinch() {
        return NumberperDiaofWireinch;
    }

    public void setNumberperDiaofWireinch(String numberperDiaofWireinch) {
        NumberperDiaofWireinch = numberperDiaofWireinch;
    }

    public String getNominalDiaofCond_04() {
        return NominalDiaofCond_04;
    }

    public String getNominalDiaofCond_05() {
        return NominalDiaofCond_05;
    }

    public String getNoofPair() {
        return NoofPair;
    }

    public String getPricepermetre() {
        return Pricepermetre;
    }

    public String getDescription() {
        return Description;
    }

    public String getPriceper100mtrs() {
        return Priceper100mtrs;
    }

    public String getPriceper300mtrs() {
        return Priceper300mtrs;
    }

    public String getPriceper305mtrs() {
        return Priceper305mtrs;
    }

    public String getNominalAreaCond() {
        return NominalAreaCond;
    }

    public String getOne_Core() {
        return one_Core;
    }

    public String getTwo_Core() {
        return two_Core;
    }

    public String getThree_Core() {
        return three_Core;
    }

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
