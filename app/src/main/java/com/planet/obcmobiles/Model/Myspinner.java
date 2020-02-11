package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/30/2017.
 */

public class Myspinner {
    String spinnerText;
    String id;


    public Myspinner(String spinnerText, String id) {
        this.spinnerText = spinnerText;
        this.id = id;

    }

    public String getSpinnerText() {
        return spinnerText;
    }

    public String getid() {
        return id;
    }


    public String toString() {
        return spinnerText;
    }


}