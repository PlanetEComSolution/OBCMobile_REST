
package com.planet.obcmobiles.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product2_Response {

    @SerializedName("SubHeadingId")
    @Expose
    private String subHeadingId;
    @SerializedName("SubHeadingName")
    @Expose
    private String subHeadingName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Product2_Response() {
    }

    /**
     * 
     * @param subHeadingName
     * @param subHeadingId
     */
    public Product2_Response(String subHeadingId, String subHeadingName) {
        super();
        this.subHeadingId = subHeadingId;
        this.subHeadingName = subHeadingName;
    }

    public String getSubHeadingId() {
        return subHeadingId;
    }

    public void setSubHeadingId(String subHeadingId) {
        this.subHeadingId = subHeadingId;
    }

    public String getSubHeadingName() {
        return subHeadingName;
    }

    public void setSubHeadingName(String subHeadingName) {
        this.subHeadingName = subHeadingName;
    }

}
