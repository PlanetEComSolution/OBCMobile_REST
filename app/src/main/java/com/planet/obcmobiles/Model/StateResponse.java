package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/30/2017.
 */

public class StateResponse {
    String state_id;
    String state_name;


    public StateResponse(String state_id, String state_name) {
        this.state_id = state_id;
        this.state_name = state_name;
    }


    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }
}