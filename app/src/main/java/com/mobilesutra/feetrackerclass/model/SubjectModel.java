package com.mobilesutra.feetrackerclass.model;

/**
 * Created by Acer on 24/03/2016.
 */
public class SubjectModel {
        String branch="",batch="",subjid="",stand="",flag="",Rid="",SubID="";
    String str_subject = "",str_total = "",str_fee_paid = "",str_balance = "";

    public SubjectModel(String str_subject, String str_total, String str_fee_paid, String str_balance) {
        this.str_subject = str_subject;
        this.str_total = str_total;
        this.str_fee_paid = str_fee_paid;
        this.str_balance = str_balance;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSubjid() {
        return subjid;
    }

    public void setSubjid(String subjid) {
        this.subjid = subjid;
    }

    public String getStand() {
        return stand;
    }

    public void setStand(String stand) {
        this.stand = stand;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRid() {
        return Rid;
    }

    public void setRid(String rid) {
        Rid = rid;
    }

    public SubjectModel(String branch, String rid, String flag, String stand, String subjid, String batch) {



        this.branch = branch;
        Rid = rid;
        this.flag = flag;
        this.stand = stand;
        this.subjid = subjid;
        this.SubID=subjid;
        this.batch = batch;
    }

    public String getStr_subject() {
        return str_subject;
    }

    public void setStr_subject(String str_subject) {
        this.str_subject = str_subject;
    }

    public String getStr_total() {
        return str_total;
    }

    public void setStr_total(String str_total) {
        this.str_total = str_total;
    }

    public String getStr_fee_paid() {
        return str_fee_paid;
    }

    public void setStr_fee_paid(String str_fee_paid) {
        this.str_fee_paid = str_fee_paid;
    }

    public String getSubID() {
        return SubID;
    }

    public void setSubID(String subID) {
        SubID = subID;
    }

    public String getStr_balance() {
        return str_balance;
    }

    public void setStr_balance(String str_balance) {
        this.str_balance = str_balance;
    }
}
