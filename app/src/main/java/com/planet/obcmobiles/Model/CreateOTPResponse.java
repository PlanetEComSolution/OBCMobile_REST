
package com.planet.obcmobiles.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateOTPResponse {

    @SerializedName("errormessage")
    @Expose
    private String errormessage;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("OTP")
    @Expose
    private String oTP;
    @SerializedName("data")
    @Expose
    private Object data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CreateOTPResponse() {
    }

    /**
     * 
     * @param errormessage
     * @param oTP
     * @param status
     * @param data
     */
    public CreateOTPResponse(String errormessage, String status, String oTP, Object data) {
        super();
        this.errormessage = errormessage;
        this.status = status;
        this.oTP = oTP;
        this.data = data;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOTP() {
        return oTP;
    }

    public void setOTP(String oTP) {
        this.oTP = oTP;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
