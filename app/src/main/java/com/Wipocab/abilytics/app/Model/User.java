package com.Wipocab.abilytics.app.Model;

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
}
