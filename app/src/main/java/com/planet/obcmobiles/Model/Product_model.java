package com.planet.obcmobiles.Model;

import java.util.List;

/**
 * Created by Admin on 11/21/2017.
 */

public class Product_model {
    private String product_services;
    private String product_services_id;
    private String header_data;
    private List child_data;


    public String getProduct_services() {
        return product_services;
    }

    public void setProduct_services(String product_services) {
        this.product_services = product_services;
    }

    public String getProduct_services_id() {
        return product_services_id;
    }

    public void setProduct_services_id(String product_services_id) {
        this.product_services_id = product_services_id;
    }

    public String getHeader_data() {
        return header_data;
    }

    public void setHeader_data(String header_data) {
        this.header_data = header_data;
    }


    public List getChild_data() {
        return child_data;
    }

    public void setChild_data(List child_data) {
        this.child_data = child_data;
    }
}
