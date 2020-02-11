package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/21/2017.
 */

public class NearestBranches_Response {
    private String type_id;
    private String type_name;
    private String address;
    private String Latitude;
    private String Longitude;
    private String branchdistance;
    private String stype;




    public NearestBranches_Response(String type_id, String type_name, String address, String latitude, String longitude, String branchdistance, String stype) {
        this.type_id = type_id;
        this.type_name = type_name;
        this.address = address;
        Latitude = latitude;
        Longitude = longitude;
        this.branchdistance = branchdistance;
        this.stype = stype;
    }


    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getBranchdistance() {
        return branchdistance;
    }

    public void setBranchdistance(String branchdistance) {
        this.branchdistance = branchdistance;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }
}
