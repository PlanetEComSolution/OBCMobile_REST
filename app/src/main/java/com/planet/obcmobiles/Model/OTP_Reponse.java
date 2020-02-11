
package com.planet.obcmobiles.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTP_Reponse {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("mobile")
    @Expose
    private Object mobile;

    /**
     * No args constructor for use in serialization
     * 
     */
    public OTP_Reponse() {
    }

    /**
     * 
     * @param msg
     * @param mobile
     */
    public OTP_Reponse(String msg, Object mobile) {
        super();
        this.msg = msg;
        this.mobile = mobile;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

}
