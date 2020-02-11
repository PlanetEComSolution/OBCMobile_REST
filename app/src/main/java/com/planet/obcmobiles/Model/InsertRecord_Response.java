package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/21/2017.
 */

public class InsertRecord_Response {
    private String result;
    private String data;
    private String status;
    private String errormessage;

    public InsertRecord_Response(String result, String data, String status, String errormessage) {
        this.result = result;
        this.data = data;
        this.status = status;
        this.errormessage = errormessage;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }
}
