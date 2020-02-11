package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/21/2017.
 */

public class ContactUs_Response {
    private String VR_CategoryId;
    private String VR_Category;
    private String VR_ContactCaption;
    private String VR_ContactNo;

    public ContactUs_Response(String VR_CategoryId, String VR_Category, String VR_ContactCaption, String VR_ContactNo) {
        this.VR_CategoryId = VR_CategoryId;
        this.VR_Category = VR_Category;
        this.VR_ContactCaption = VR_ContactCaption;
        this.VR_ContactNo = VR_ContactNo;
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

    public String getVR_ContactCaption() {
        return VR_ContactCaption;
    }

    public void setVR_ContactCaption(String VR_ContactCaption) {
        this.VR_ContactCaption = VR_ContactCaption;
    }

    public String getVR_ContactNo() {
        return VR_ContactNo;
    }

    public void setVR_ContactNo(String VR_ContactNo) {
        this.VR_ContactNo = VR_ContactNo;
    }
}
