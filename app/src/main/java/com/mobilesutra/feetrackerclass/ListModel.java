package com.mobilesutra.feetrackerclass;

public class ListModel {

    private String id = "";
    private String name = "";
    private String profile;
    private String phone1 = "";
    private String studphone = "";
    private String standard = "";
    private String auto_id = "";
    private String subjects = "";
    private String fee = "";
    private String classid = "";
    private String feeid = "";
    private String branch = "";
    private String std = "";
    private String batch = "";
    private String subids = "";
    private String PresentFlag="";
    private String AbsentFlag2="";
    private String AbsentFlag="";
    private boolean smsFlag= false;

    public ListModel(String id, String auto_id, String name, String profile, String phone1, String studphone, String standard, String batch, String branch, String date, String s) {
        this.id = id;
        this.auto_id = auto_id;
        this.name = name;
        this.profile = profile;
        this.phone1 = phone1;
        this.studphone = studphone;
        this.standard = standard;
        this.batch = batch;
        this.branch = branch;
        this.date = date;
        this.PresentFlag = PresentFlag;

    }

   /* public ListModel(String id, Object auto_id, String name, byte[] profile, String phone1, String studphone, String std, String batch, String branch, String date, String PresentFlag) {
        this.id = id;
        this.name = name;
        this.profile = profile;
        this.phone1 = phone1;
        this.studphone = studphone;
        this.std = std;
        this.batch = batch;
        this.branch = branch;
        this.date = date;
        this.PresentFlag = PresentFlag;
    }*/

    /*public ListModel(String id, Object auto_id, String name,byte[] profile, String phone1, String studphone, String std, Object batch, Object branch, Object date, String PresentFlag) {
        this.id = id;
        this.name = name;
        this.profile = profile;
        this.phone1 = phone1;
        this.studphone = studphone;
        this.std = std;
        this.PresentFlag = PresentFlag;

    }*/


    // for get profile also 24-04-18


    public boolean isSmsFlag() {
        return smsFlag;
    }

    public void setSmsFlag(boolean smsFlag) {
        this.smsFlag = smsFlag;
    }

    String empty_flag="";
String date="";
    String isselected = null;
    Boolean selected = false;
    String selected2="";
    String feeflag = null;





//    public ListModel(String id, String name, String phone1, String studphone,String PresentFlag,String AbsentFlag,String standard,String batch,String branch,String subjects) {
//        // TODO Auto-generated constructor stub
//        this.id = id;
//        this.name = name;
//        this.phone1 = phone1;
//        this.studphone = studphone;
//        this.standard = standard;
//
//        this.PresentFlag = PresentFlag;
//        this.AbsentFlag = AbsentFlag;
//    }
    //atendance

    public  ListModel()
    {

    }
    public ListModel(String id,String auto_id,String name,String profile, String phone1, String studphone, String PresentFlag,String empty_flag) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.name = name;
        this.profile= profile;
        this.auto_id = auto_id;
        this.phone1 = phone1;
        this.studphone = studphone;
        this.PresentFlag = PresentFlag;

    }




    //studmang,fee//send sms
    public ListModel(String id,String auto_id, String name, String parentphone, String studphone,
                     String standard,String batch,String branch,String date,String empty_flag) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.name = name;

        this.auto_id = auto_id;
        this.phone1 = parentphone;
        this.studphone = studphone;
        this.standard = standard;


        this.batch = batch;
        this.branch = branch;
        this.date=date;
    }


    public ListModel(String id, String name, String isselected,
                     Boolean selected, String feeflag) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.name = name;
        this.isselected = isselected;
        this.selected = selected;
        this.feeflag = feeflag;


    }


    public ListModel(String id,String classid, String branch, String std, String batch,String subjects) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.classid = classid;
        this.branch = branch;
        this.std = std;
        this.batch = batch;
        this.subjects = subjects;

    }


    public ListModel(String id, String subjects, String fee, String classid, String feeid, String branch, String std, String batch,String subids) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.subjects = subjects;
        this.fee = fee;
        this.classid = classid;
        this.feeid = feeid;
        this.branch = branch;
        this.std = std;
        this.batch = batch;
        this.subids = subids;


    }



    /**
     * ******** Set Methods *****************
     */



    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public void setStudphone(String studphone) {
        this.studphone = studphone;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public void setFeeflag(String feeflag) {
        this.feeflag = feeflag;
    }

    public void setIsselected(String isselected) {
        this.isselected = isselected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public void setSubids(String subids) {
        this.subids = subids;
    }

    /**
     * ******** Get Methods ***************
     */
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getStudphone() {
        return studphone;
    }

    public String getStandard() {
        return standard;
    }

    public String getSubjects() {
        return subjects;
    }

    public String getFee() {
        return fee;
    }

    public String getClassid() {
        return classid;
    }

    public String getFeeid() {
        return feeid;
    }

    public String getBranch() {
        return branch;
    }

    public String getStd() {
        return std;
    }

    public String getBatch() {
        return batch;
    }

    public Boolean getSelected() {
        return selected;
    }

    public String getFeeflag() {
        return feeflag;
    }

    public String getIsselected() {
        return isselected;
    }

    public String getSubids() {
        return subids;
    }

    public String getSelected2() {
        return selected2;
    }

    public void setSelected2(String selected2) {
        this.selected2 = selected2;
    }

    public String getPresentFlag() {
        return PresentFlag;
    }

    public void setPresentFlag(String presentFlag) {
        PresentFlag = presentFlag;
    }

    public String getAbsentFlag() {
        return AbsentFlag;
    }

    public void setAbsentFlag(String absentFlag) {
        AbsentFlag = absentFlag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(String auto_id) {
        this.auto_id = auto_id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}