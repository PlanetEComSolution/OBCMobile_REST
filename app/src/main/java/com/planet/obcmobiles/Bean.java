package com.planet.obcmobiles;

/**
 * Created by planet on 11/4/15.
 */
public class Bean {


    public Bean(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    String latitude, longitude;


}
