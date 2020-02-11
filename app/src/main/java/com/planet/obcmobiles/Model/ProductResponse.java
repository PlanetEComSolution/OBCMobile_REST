package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/30/2017.
 */

public class ProductResponse {
    String SubHeadingId;
    String SubHeadingName;
    String fileName;
    String pagelink;
    String Description;


    public ProductResponse(String subHeadingId, String subHeadingName, String fileName, String pagelink, String description) {
        this.SubHeadingId = subHeadingId;
        this.SubHeadingName = subHeadingName;
        this.fileName = fileName;
        this.pagelink = pagelink;
        this.Description = description;
    }


    public String getSubHeadingId() {
        return SubHeadingId;
    }

    public void setSubHeadingId(String subHeadingId) {
        SubHeadingId = subHeadingId;
    }

    public String getSubHeadingName() {
        return SubHeadingName;
    }

    public void setSubHeadingName(String subHeadingName) {
        SubHeadingName = subHeadingName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPagelink() {
        return pagelink;
    }

    public void setPagelink(String pagelink) {
        this.pagelink = pagelink;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}