package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/30/2017.
 */

public class UUIDInsertResponse {
    String success;

    public UUIDInsertResponse(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}