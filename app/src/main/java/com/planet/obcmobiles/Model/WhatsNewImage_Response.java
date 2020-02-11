package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/21/2017.
 */

public class WhatsNewImage_Response {
    private String Caption;
    private String URL;

    public WhatsNewImage_Response(String caption, String URL) {
        Caption = caption;
        this.URL = URL;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
