package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/30/2017.
 */

public class BranchResponse {
    String branch_id;
    String branch_name;

    public BranchResponse(String branch_id, String branch_name) {
        this.branch_id = branch_id;
        this.branch_name = branch_name;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }
}