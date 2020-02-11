package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/21/2017.
 */

public class LoanLevel2_Response {
    private String amount;
    private String VR_RepaymentPeriod_Name;
    private String MinPeriod;
    private String MaxPeriod;
    private String VR_LoanType_Name;


    public LoanLevel2_Response(String amount, String VR_RepaymentPeriod_Name, String minPeriod, String maxPeriod, String VR_LoanType_Name) {
        this.amount = amount;
        this.VR_RepaymentPeriod_Name = VR_RepaymentPeriod_Name;
        MinPeriod = minPeriod;
        MaxPeriod = maxPeriod;
        this.VR_LoanType_Name = VR_LoanType_Name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getVR_RepaymentPeriod_Name() {
        return VR_RepaymentPeriod_Name;
    }

    public void setVR_RepaymentPeriod_Name(String VR_RepaymentPeriod_Name) {
        this.VR_RepaymentPeriod_Name = VR_RepaymentPeriod_Name;
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
