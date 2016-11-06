package com.Wipocab.abilytics.app.Model;

/**
 * Created by gautam on 9/6/16.
 */
public class ServerRequest {
    private String operation;
    private User user;
    private String query;


    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public void setquery(String query){
        this.query=query;
    }
}
