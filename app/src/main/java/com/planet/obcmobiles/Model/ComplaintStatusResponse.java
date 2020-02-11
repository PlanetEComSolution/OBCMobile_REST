package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/30/2017.
 */

public class ComplaintStatusResponse {
    String ComplaintStatus;

    public ComplaintStatusResponse(String complaintStatus) {
        ComplaintStatus = complaintStatus;
    }

    public String getComplaintStatus() {
        return ComplaintStatus;
    }

    public void setComplaintStatus(String complaintStatus) {
        ComplaintStatus = complaintStatus;
    }
}