package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/30/2017.
 */

public class CityResponse {
    String City_id;
    String city_name;

    public CityResponse(String city_id, String city_name) {
        City_id = city_id;
        this.city_name = city_name;
    }

    public String getCity_id() {
        return City_id;
    }

    public void setCity_id(String city_id) {
        City_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}