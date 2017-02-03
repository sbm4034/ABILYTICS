package com.Wipocab.abilytics.app.Model;

import java.util.Set;

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
