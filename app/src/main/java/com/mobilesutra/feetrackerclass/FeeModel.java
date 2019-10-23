package com.mobilesutra.feetrackerclass;

import android.util.Log;

import com.mobilesutra.feetrackerclass.model.SubjectModel;

import java.util.ArrayList;

/**
 * Created by Acer on 23/03/2016.
 */

public class FeeModel {
    String studCount = "";
    String id = "", fee_branch = "", fee_batch = "", fee_stand = "", fee_total = "", fee_bal = "", fee_paid = "", fee_subj = "";
    ArrayList<SubjectModel> arrSubject = null;

    public FeeModel(String fee_branch,String fee_stand,String fee_batch,String studCount) {
        this.fee_branch = fee_branch;

        this.fee_stand = fee_stand;
        this.fee_batch = fee_batch;
        arrSubject = new ArrayList<>();
        this.studCount = studCount;
    }
    public FeeModel()
    {

    }

    public String getStudCount() {
        return studCount;
    }

    public void setStudCount(String studCount) {
        this.studCount = studCount;
    }

    public String getFee_branch() {
        return fee_branch;
    }

    public void setFee_branch(String fee_branch) {
        this.fee_branch = fee_branch;
    }

    public String getFee_batch() {
        return fee_batch;
    }

    public void setFee_batch(String fee_batch) {
        this.fee_batch = fee_batch;
    }

    public String getFee_stand() {
        return fee_stand;
    }

    public void setFee_stand(String fee_stand) {
        this.fee_stand = fee_stand;
    }

    public ArrayList<SubjectModel> getItems() {
        return arrSubject;
    }

    public Boolean  checkData(String studCount,String stand,String batch)
    {
        if(studCount.equalsIgnoreCase(this.studCount))
        {
            Log.d("checkData","return false");
            return false;

        }else
        {
            Log.d("checkData","return true");
            return true;
        }

    }

    public void addSubj(SubjectModel subjectModel) {
        arrSubject.add(subjectModel);
    }
}
