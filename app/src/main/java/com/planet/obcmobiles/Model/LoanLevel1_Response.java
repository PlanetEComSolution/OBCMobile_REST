package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/21/2017.
 */

public class LoanLevel1_Response {
    private String period;
    private String MinPeriod;
    private String MaxPeriod;
    private String VR_LoanType_Name;

    public LoanLevel1_Response(String period, String minPeriod, String maxPeriod, String VR_LoanType_Name) {
        this.period = period;
        MinPeriod = minPeriod;
        MaxPeriod = maxPeriod;
        this.VR_LoanType_Name = VR_LoanType_Name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getMinPeriod() {
        return MinPeriod;
    }

    public void setMinPeriod(String minPeriod) {
        MinPeriod = minPeriod;
    }

    public String getMaxPeriod() {
        return MaxPeriod;
    }

    public void setMaxPeriod(String maxPeriod) {
        MaxPeriod = maxPeriod;
    }

    public String getVR_LoanType_Name() {
        return VR_LoanType_Name;
    }

    public void setVR_LoanType_Name(String VR_LoanType_Name) {
        this.VR_LoanType_Name = VR_LoanType_Name;
    }
}
