package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/21/2017.
 */

public class NearestBranchesByPin_Response {
    private String Latitude;
    private String Regional_Office1;
    private String Longitude;
    private String stype;
    private String name;


    public NearestBranchesByPin_Response(String latitude, String regional_Office1, String longitude, String stype, String name) {
        Latitude = latitude;
        Regional_Office1 = regional_Office1;
        Longitude = longitude;
        this.stype = stype;
        this.name = name;
    }


    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getRegional_Office1() {
        return Regional_Office1;
    }

    public void setRegional_Office1(String regional_Office1) {
        Regional_Office1 = regional_Office1;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
