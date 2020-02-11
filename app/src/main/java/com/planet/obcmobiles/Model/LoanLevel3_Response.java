package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/21/2017.
 */

public class LoanLevel3_Response {
    private String rate;
    private String VR_RepaymentPeriod_Name;
    private String VR_LoanType_Name;
    private String VR_LoanAmount_Name;

    public LoanLevel3_Response(String rate, String VR_RepaymentPeriod_Name, String VR_LoanType_Name, String VR_LoanAmount_Name) {
        this.rate = rate;
        this.VR_RepaymentPeriod_Name = VR_RepaymentPeriod_Name;
        this.VR_LoanType_Name = VR_LoanType_Name;
        this.VR_LoanAmount_Name = VR_LoanAmount_Name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getVR_RepaymentPeriod_Name() {
        return VR_RepaymentPeriod_Name;
    }

    public void setVR_RepaymentPeriod_Name(String VR_RepaymentPeriod_Name) {
        this.VR_RepaymentPeriod_Name = VR_RepaymentPeriod_Name;
    }

    public String getVR_LoanType_Name() {
        return VR_LoanType_Name;
    }

    public void setVR_LoanType_Name(String VR_LoanType_Name) {
        this.VR_LoanType_Name = VR_LoanType_Name;
    }

    public String getVR_LoanAmount_Name() {
        return VR_LoanAmount_Name;
    }

    public void setVR_LoanAmount_Name(String VR_LoanAmount_Name) {
        this.VR_LoanAmount_Name = VR_LoanAmount_Name;
    }
}
