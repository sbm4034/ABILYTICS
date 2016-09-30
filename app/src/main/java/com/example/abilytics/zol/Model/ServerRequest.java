package com.example.abilytics.zol.Model;

/**
 * Created by gautam on 9/6/16.
 */
public class ServerRequest {
    private String operation;
    private User user;

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
