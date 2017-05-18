package com.Wipocab.abilytics.application.Model;

public class User {

        private String name;
        private String email;
        private String unique_id;
        private String password;
        private String old_password;
        private String new_password;
        private String money;
        private String promo_code;
        private String value;
        private String otp;
        private String dob;
        private String place,ptssend,nosend;
        private String product;
        private String category;
        private int pid;
        private int noi;
    private String p_id;
    private String id;
    private String product_code;
    private String Length;
    private String price;
    private String query;
    private String noi_for_one_core;
    private String noi_for_two_core;
    private String noi_for_three_core;
    private  String noi_for_04;
    private String noi_for_05;

    public String getNoi_for_04() {
        return noi_for_04;
    }

    public void setNoi_for_04(String noi_for_04) {
        this.noi_for_04 = noi_for_04;
    }

    public String getNoi_for_05() {
        return noi_for_05;
    }

    public void setNoi_for_05(String noi_for_05) {
        this.noi_for_05 = noi_for_05;
    }

    public String getNoi_for_one_core() {
        return noi_for_one_core;
    }

    public void setNoi_for_one_core(String noi_for_one_core) {
        this.noi_for_one_core = noi_for_one_core;
    }

    public String getNoi_for_two_core() {
        return noi_for_two_core;
    }

    public void setNoi_for_two_core(String noi_for_two_core) {
        this.noi_for_two_core = noi_for_two_core;
    }

    public String getNoi_for_three_core() {
        return noi_for_three_core;
    }

    public void setNoi_for_three_core(String noi_for_three_core) {
        this.noi_for_three_core = noi_for_three_core;
    }



    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLength() {
        return Length;
    }

    public void setLength(String length) {
        Length = length;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPtssend(String ptssend) {
        this.ptssend = ptssend;
    }

    public String getPtssend() {
        return ptssend;
    }

    public void setNosend(String nosend) {
        this.nosend = nosend;
    }

    public String getName() {
            return name;
        }

        public void setOtp(String otp){this.otp=otp;}
    public String getDob() {
        return dob;
    }
    public String getPlace() {
        return place;
    }

    public void setDob(String dob){this.dob=dob;}
    public void setPlace(String place){this.place=place;}

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getEmail() {
            return email;
        }

        public String getUnique_id() {
            return unique_id;
        }

    public String getPromo_money() {
        return value;
    }


    public void setName(String name) {
            this.name = name;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setOld_password(String old_password) {
            this.old_password = old_password;
        }

        public void setNew_password(String new_password) {
            this.new_password = new_password;
        }


        public void setUpdated_money(String value) {
        this.value = value;
          }

        public void setcode(String promo_code) {
        this.promo_code = promo_code;
    }
    public void setproduct(String product){this.product=product;}

    public int getPid() {
        return pid;
    }


    public void setNoi(int noi){this.noi=noi;
    }


    public void setPid(int pid) {
        this.pid = pid;
    }

}
