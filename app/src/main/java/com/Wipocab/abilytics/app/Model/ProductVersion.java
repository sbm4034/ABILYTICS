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
