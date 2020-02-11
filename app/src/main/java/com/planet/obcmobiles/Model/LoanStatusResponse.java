package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/30/2017.
 */

public class LoanStatusResponse {
    String LoanStatus;

    public LoanStatusResponse(String loanStatus) {
        LoanStatus = loanStatus;
    }

    public String getLoanStatus() {
        return LoanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        LoanStatus = loanStatus;
    }
}