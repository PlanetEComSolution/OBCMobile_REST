package com.planet.obcmobiles.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 11/21/2017.
 */

public class Deposit_Response {

    @SerializedName("duration")
    @Expose
    private String VR_Duration;

    @SerializedName("rate")
    @Expose
    private String VR_RateofInterest;

    @SerializedName("MinDays")
    @Expose
    private String MinDays;

    @SerializedName("MaxDays")
    @Expose
    private String MaxDays;


    public Deposit_Response(String VR_Duration, String VR_RateofInterest, String minDays, String maxDays) {
        this.VR_Duration = VR_Duration;
        this.VR_RateofInterest = VR_RateofInterest;
        MinDays = minDays;
        MaxDays = maxDays;
    }

    public String getVR_Duration() {
        return VR_Duration;
    }

    public void setVR_Duration(String VR_Duration) {
        this.VR_Duration = VR_Duration;
    }

    public String getVR_RateofInterest() {
        return VR_RateofInterest;
    }

    public void setVR_RateofInterest(String VR_RateofInterest) {
        this.VR_RateofInterest = VR_RateofInterest;
    }

    public String getMinDays() {
        return MinDays;
    }

    public void setMinDays(String minDays) {
        MinDays = minDays;
    }

    public String getMaxDays() {
        return MaxDays;
    }

    public void setMaxDays(String maxDays) {
        MaxDays = maxDays;
    }
}
