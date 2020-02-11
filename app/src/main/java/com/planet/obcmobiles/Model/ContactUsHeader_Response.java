package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/21/2017.
 */

public class ContactUsHeader_Response {
    private String VR_CategoryId;
    private String VR_Category;

    public ContactUsHeader_Response(String VR_CategoryId, String VR_Category) {
        this.VR_CategoryId = VR_CategoryId;
        this.VR_Category = VR_Category;
    }

    public String getVR_CategoryId() {
        return VR_CategoryId;
    }

    public void setVR_CategoryId(String VR_CategoryId) {
        this.VR_CategoryId = VR_CategoryId;
    }

    public String getVR_Category() {
        return VR_Category;
    }

    public void setVR_Category(String VR_Category) {
        this.VR_Category = VR_Category;
    }
}
