package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/21/2017.
 */

public class LoanLevel0_Response {
    private String VR_LoanType_Name;

    public LoanLevel0_Response(String VR_LoanType_Name) {
        this.VR_LoanType_Name = VR_LoanType_Name;
    }

    public String getVR_LoanType_Name() {
        return VR_LoanType_Name;
    }

    public void setVR_LoanType_Name(String VR_LoanType_Name) {
        this.VR_LoanType_Name = VR_LoanType_Name;
    }
}
