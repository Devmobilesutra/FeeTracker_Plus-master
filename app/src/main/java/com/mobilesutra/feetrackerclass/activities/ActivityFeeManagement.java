package com.mobilesutra.feetrackerclass.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobilesutra.feetrackerclass.FAB.FloatingActionButton;
import com.mobilesutra.feetrackerclass.FeeSummary;
import com.mobilesutra.feetrackerclass.ListModel;
import com.mobilesutra.feetrackerclass.MyApplication;
import com.mobilesutra.feetrackerclass.R;
import com.mobilesutra.feetrackerclass.StudentFeeStructure;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ActivityFeeManagement extends AppCompatActivity {

    Spinner spnr_branch=null,spnr_stand=null,spnr_batch=null,spnr_subject=null;
  //  Spinner spnr_branch=null,spnr_stand=null,spnr_batch=null,spnr_subject=null;
    //    MultiSelectionSpinner multiSelectionSpinner;
    LinkedHashMap<String, String> lhm_branch = new LinkedHashMap<String, String>();// Branch
    LinkedHashMap<String, String> lhm_std = new LinkedHashMap<String, String>();// Std
    LinkedHashMap<String, String> lhm_batch = new LinkedHashMap<String, String>();// Batch
    LinkedHashMap<String, String> lhm_sub = new LinkedHashMap<String, String>();// Subjects
    String selected_branch_value="",selected_stand_value="",selected_batch_value="", selected_subj_value="",rowid="",edit_batch,edit_stand;
    ArrayList<String> arrstand1 ;
    ArrayList<String> arrbatch ;
    ArrayList<String> arraysubj ;
    String[] array2;
    List<String> SelectedSubj=new ArrayList<>();
    FloatingActionButton fab_fee_summary=null;
    ImageView menu_serch;

    ListView recycler_view=null;

    Typeface roboto_reguler,roboto_light;

    ArrayList<HashMap<String, String>> menu_list;
    ArrayList<ListModel> rowItems1 = new ArrayList<ListModel>();
   // CustomAdapter customAdapter;
    //EditText edittext;
    Context context=this;
    int size = 0;
    int index, top;
    int textlength = 0;
    Boolean flag=false;
    Boolean flag_data=true;
    TextView txt_class_name,txt_activity_header;
    EditText edit_student_search;
    ImageView img_search;
    String LOG_TAG = "ActivityFeeManagement";
    CustomAdapter customAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_management);

        initComponents();
        initComponentListeners();
        initSpinnerData();
        bindComponentData();

    }

    private void initSpinnerData() {


        //add spinner _branch details
        lhm_branch = MyApplication.dbo.getBranchWithActiveFlag(MyApplication.get_session("classid"));
        if (lhm_branch.size() == 0) {

            ErrorDialog(getResources().getString(R.string.empty_data_error_msg));
        } else {
            MyApplication.log(LOG_TAG,lhm_branch + "");
            ArrayList<String> arrBranch = new ArrayList<String>();

            for (Object o : lhm_branch.keySet()) {
                arrBranch.add(o.toString());
                System.out.println("key:" + o.toString() + "___" + "value:" + lhm_branch.get(o).toString());
            }
            ArrayAdapter<String> adapter0 = new MyApplication.MySpinnerAdapter(context,R.layout.spinner_properties, arrBranch);
            adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnr_branch.setAdapter(adapter0);







            spnr_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    selected_branch_value = lhm_branch.get(spnr_branch.getSelectedItem().toString());
                    MyApplication.set_session(MyApplication.Fee_session_branch, selected_branch_value);//**
                    bindComponentData();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            int count1 = lhm_branch.size();
            if (count1 != 0) {
                selected_branch_value = lhm_branch.get(spnr_branch.getSelectedItem().toString());
                if(MyApplication.get_session(MyApplication.Fee_session_branch).equals(""))
                    MyApplication.set_session(MyApplication.Fee_session_branch,selected_branch_value);

            }
            if(flag_data)
            {

                String  branchname=MyApplication.dbo.getBranchName(MyApplication.get_session(MyApplication.Fee_session_branch));
                int position = arrBranch.indexOf(branchname);
//                Toast.makeText(context,"value "+MyApplication.get_session(MyApplication.session_branch)+position,Toast.LENGTH_SHORT).show();
                spnr_branch.setSelection(position, true);
                selected_branch_value = lhm_branch.get(spnr_branch.getSelectedItem().toString());
                MyApplication.set_session(MyApplication.Fee_session_branch,selected_branch_value);

            }
            //add spinner standard  details
            lhm_std = MyApplication.dbo.getStandard(MyApplication.get_session("classid"), selected_branch_value);
            MyApplication.log(LOG_TAG,lhm_std + "");
            arrstand1 = new ArrayList<String>();
            for (Object o : lhm_std.keySet()) {
                arrstand1.add(o.toString());
                System.out.println("stand-key:" + o.toString() + "___" + "value:" + lhm_std.get(o).toString());
            }
            ArrayAdapter<String> adapter = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arrstand1);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnr_stand.setAdapter(adapter);


            spnr_stand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    selected_stand_value = lhm_std.get(spnr_stand.getSelectedItem().toString());
                    MyApplication.set_session("std", selected_stand_value);
                    MyApplication.set_session(MyApplication.Fee_session_stand,selected_stand_value);//**

                    bindComponentData();



                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });


            int count2 = lhm_std.size();
            if (count2 != 0) {
                selected_stand_value = lhm_std.get(spnr_stand.getSelectedItem().toString());
                if(MyApplication.get_session(MyApplication.Fee_session_stand).equals(""))
                    MyApplication.set_session(MyApplication.Fee_session_stand,selected_stand_value);//**
            }

            if(flag_data)
            {

                String  standname=MyApplication.dbo.getStandardName(MyApplication.get_session(MyApplication.Fee_session_stand));
                int position = arrstand1.indexOf(standname);
//                Toast.makeText(context,"value "+MyApplication.get_session(MyApplication.session_branch)+position,Toast.LENGTH_SHORT).show();
                spnr_stand.setSelection(position, true);
                selected_stand_value = lhm_std.get(spnr_stand.getSelectedItem().toString());
                MyApplication.set_session(MyApplication.Fee_session_stand,selected_stand_value);

            }






            lhm_batch = MyApplication.dbo
                    .getBatch(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value);
            MyApplication.log(LOG_TAG,lhm_batch + "");
            arrbatch = new ArrayList<String>();
            for (Object o : lhm_batch.keySet()) {
                arrbatch.add(o.toString());
                System.out.println("batch-key:" + o.toString() + "___" + "value:" + lhm_batch.get(o).toString());
            }
            ArrayAdapter<String> adapterb = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arrbatch);
            adapterb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnr_batch.setAdapter(adapterb);


            spnr_batch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selected_batch_value = lhm_batch.get(spnr_batch.getSelectedItem().toString());
                    MyApplication.set_session(MyApplication.Fee_session_batch,selected_batch_value);//**
//                Toast.makeText(context,"value_batch"+selected_batch_value,Toast.LENGTH_SHORT).show();;
                    bindComponentData();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }

            });

            int count3 = lhm_batch.size();
            if (count3 != 0) {
                selected_batch_value = lhm_batch.get(spnr_batch.getSelectedItem().toString());
                if(MyApplication.get_session(MyApplication.Fee_session_batch).equals(""))
                    MyApplication.set_session(MyApplication.Fee_session_batch,selected_batch_value);//**

            }

            if(flag_data)
            {

                String  batchname=MyApplication.dbo.getbatchhhName(MyApplication.get_session(MyApplication.Fee_session_batch));
                int position = arrbatch.indexOf(batchname);
//                Toast.makeText(context,"value "+MyApplication.get_session(MyApplication.session_branch)+position,Toast.LENGTH_SHORT).show();
                spnr_batch.setSelection(position, true);
                selected_batch_value = lhm_batch.get(spnr_batch.getSelectedItem().toString());
                MyApplication.set_session(MyApplication.Fee_session_batch,selected_batch_value);

            }




            lhm_sub = MyApplication.dbo.getSubjectWithInsertFee(MyApplication.get_session("classid"));

            MyApplication.log(LOG_TAG,lhm_sub + "");
            arraysubj = new ArrayList<String>();
            for (Object o : lhm_sub.keySet()) {
                arraysubj.add(o.toString());
                System.out.println("batch-key:" + o.toString() + "___" + "value:" + lhm_sub.get(o).toString());
            }
            ArrayAdapter<String> adaptersub = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arraysubj);
            adaptersub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnr_subject.setAdapter(adaptersub);

            spnr_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selected_subj_value = lhm_sub.get(spnr_subject.getSelectedItem().toString());
                    MyApplication.set_session(MyApplication.Fee_session_subj, selected_subj_value);//**
//               Toast.makeText(context,"value "+selected_subj_value,Toast.LENGTH_SHORT).show();
                    bindComponentData();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }

            });


            int count4 = lhm_sub.size();
            if (count4 != 0) {
                selected_subj_value = lhm_sub.get(spnr_subject.getSelectedItem().toString());
                if(MyApplication.get_session(MyApplication.Fee_session_subj).equals(""))
                    MyApplication.set_session(MyApplication.Fee_session_subj, selected_subj_value);//**
//                    Toast.makeText(context,"value "+selected_subj_value,Toast.LENGTH_SHORT).show();
            }

            if(flag_data)
            {

                String subjname = ((MyApplication) getApplication()).dbo.getSubjjjnameWithMultilpe(MyApplication.get_session(MyApplication.Fee_session_subj));//get selected subj name
                int position = arraysubj.indexOf(subjname);

                spnr_subject.setSelection(position, true);

                if(lhm_sub.size()!=0)
                    selected_subj_value = lhm_sub.get(spnr_subject.getSelectedItem().toString());
//                Toast.makeText(context,"value "+selected_subj_value,Toast.LENGTH_SHORT).show();
                MyApplication.set_session(MyApplication.Fee_session_subj, selected_subj_value);

            }


        }

    }
    private void initComponents() {
        roboto_reguler =  Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");
        roboto_light =  Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
        txt_class_name = (TextView) findViewById(R.id.txt_class_name);
        txt_activity_header = (TextView) findViewById(R.id.txt_activity_header);
        edit_student_search = (EditText) findViewById(R.id.edit_student_search);
        txt_activity_header.setText("Fee Management");
        txt_class_name.setText(MyApplication.get_session("classname"));
        img_search = (ImageView) findViewById(R.id.img_search);
        fab_fee_summary = (FloatingActionButton) findViewById(R.id.fab_fee_summary);
        spnr_branch = (Spinner) findViewById(R.id.spnr_branch);
        spnr_stand = (Spinner) findViewById(R.id.spnr_stand);
        spnr_batch = (Spinner) findViewById(R.id.spnr_batch);
        spnr_subject = (Spinner) findViewById(R.id.spnr_subject);

        recycler_view = (ListView) findViewById(R.id.recycler_view);


    }

    private void initComponentListeners() {
        fab_fee_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              //  Toast.makeText(context, "Hiiii", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ActivityFeeManagement.this, FeeSummary.class);
                startActivity(i);
                finish();

            }
        });
        img_search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MyApplication.log("","into feemanagemtnt serack");
                if (!flag) {
                    edit_student_search.setVisibility(View.VISIBLE);
                    flag = true;
                } else {
                    edit_student_search.setVisibility(View.GONE);
                    flag = false;
                }


            }
        });
        edit_student_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //NAMRATA changes
                String branch = spnr_branch.getSelectedItem().toString();
                String standrd = spnr_stand.getSelectedItem().toString();
                String batch = spnr_batch.getSelectedItem().toString();
                String subject = spnr_subject.getSelectedItem().toString();
                MyApplication.log(LOG_TAG,"spnr_branch->"+branch+"-"+standrd+"-"+batch+"-"+subject);



                MyApplication.log("","Ids of branch="+MyApplication.dbo.getBranchID(branch));
                MyApplication.log("","Ids of standerd="+MyApplication.dbo.getBatchID(batch));
                MyApplication.log("","Ids of batch="+MyApplication.dbo.getStandID(standrd));
                MyApplication.log("","Ids of subject="+MyApplication.dbo.getSubjectId(subject));



                textlength = edit_student_search.getText().length();
                ArrayList<ListModel> row = new ArrayList<ListModel>();
                ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
                data = ((MyApplication) getApplication()).dbo.GetStudentData1(MyApplication.dbo.getBranchID(branch)
                                                                            ,MyApplication.dbo.getBatchID(batch)
                                                                            ,MyApplication.dbo.getStandID(standrd)
                                                                            ,MyApplication.dbo.getSubjectId(subject));
//                data = ((MyApplication) getApplication()).dbo.GetStudentData();

                MyApplication.log(LOG_TAG,"SearchTextSize->"+data);
                MyApplication.log(LOG_TAG,"SearchTextData->"+data.size());


                for (int i = 0; i < data.size(); i++) {
                    String name = data.get(i).get("sname").toString();
                    MyApplication.log(LOG_TAG,name.length()+"_name->"+name);
                    String searchtext = edit_student_search.getText().toString();
                    MyApplication.log(LOG_TAG,textlength+"_searchtext->"+searchtext);
                    MyApplication.log(LOG_TAG,"if->"+(textlength <= name.length()));
                    if (textlength <= name.length()) {
                        MyApplication.log(LOG_TAG,"ifcond->"+(name.toLowerCase().indexOf(searchtext.toLowerCase())));
                        if (name.toLowerCase().indexOf(searchtext.toLowerCase()) != -1) {
                            MyApplication.log(LOG_TAG,"i->"+i);
                            MyApplication.log(LOG_TAG,"record->"+data.get(i));
//                          ListModel data1 = new ListModel(data.get(i).get("id"),data.get(i).get("auto_id"), data.get(i).get("sname"), data.get(i).get("phone1"), data.get(i).get("studphone"), data.get(i).get("std"), menu_list.get(i).get("Batch"), menu_list.get(i).get("Branch"),menu_list.get(i).get("date"),"");
                            ListModel data1 = new ListModel(data.get(i).get("id"),data.get(i).get("auto_id"), data.get(i).get("sname"), data.get(i).get("phone1"), data.get(i).get("studphone"), data.get(i).get("std"), data.get(i).get("Batch"), data.get(i).get("Branch"),data.get(i).get("date"),"");
                            row.add(data1);
                        }
                    }
                }

                CustomAdapter sd1 = new CustomAdapter(getBaseContext(), row);
                recycler_view.setAdapter(sd1);

            }
        });
    }



    private void bindComponentData() {
        int num=SelectedSubj.size();
        String subjectid="",id="";
        for(int j=0;j<num;j++)
        {
            subjectid=lhm_sub.get(SelectedSubj.get(j));
            if(id.equals(""))
            {
                id = subjectid;
            }else
            {
                id = id + "," + subjectid;
            }
            MyApplication.log(LOG_TAG,"Databasec" + id);
        }

        menu_list = null;
        menu_list = new ArrayList<HashMap<String, String>>();

//        menu_list = ((MyApplication) getApplication()).dbo.GetStudentData(); old code
        menu_list = ((MyApplication) getApplication()).dbo.GetStudentDataNew(MyApplication.get_session("classid"),selected_branch_value,selected_stand_value,selected_batch_value,selected_subj_value);
        MyApplication.log(LOG_TAG, menu_list + "");
        rowItems1 = new ArrayList<ListModel>();
        MyApplication.log(LOG_TAG,"menu_list" + menu_list);

        int records = menu_list.size();

        for (int i = 0; i < records; i++) {


            ListModel item = new ListModel(menu_list.get(i).get("id"),
                    menu_list.get(i).get("auto_id"),
                    menu_list.get(i).get("sname"),
                    menu_list.get(i).get("studentprofile"),
                    menu_list.get(i).get("phone1"),
                    menu_list.get(i).get("studphone"),
                    menu_list.get(i).get("std"),
                    menu_list.get(i).get("Batch"),
                    menu_list.get(i).get("Branch"),
                    menu_list.get(i).get("date"),"");

            rowItems1.add(item);

            MyApplication.log(LOG_TAG,"rowItems1:id" + rowItems1.get(i).getId());
            MyApplication.log(LOG_TAG,"rowItems1:name" + rowItems1.get(i).getName());
            MyApplication.log(LOG_TAG,"rowItems1:name" + rowItems1.get(i).getPhone1());
            MyApplication.log(LOG_TAG,"rowItems1:name" + rowItems1.get(i).getStudphone());
            MyApplication.log(LOG_TAG,"rowItems1:std" + rowItems1.get(i).getStandard());
            MyApplication.log(LOG_TAG,"rowItems1:profile " + rowItems1.get(i).getName() +" "+rowItems1.get(i).getProfile());
            MyApplication.log(LOG_TAG,"rowItems1:branch" + rowItems1.get(i).getBranch());
        }


        customAdapter = new CustomAdapter(this, rowItems1);
        recycler_view.setAdapter(customAdapter);

        recycler_view.setSelectionFromTop(index, top);
    }

    public class CustomAdapter extends BaseAdapter implements View.OnClickListener {

        Context context;
        List<ListModel> rowItems;
        int selectcount = 0;
        String strappid = "";
        String sid;
        int temp = 0;
        private LayoutInflater inflater = null;
        ArrayList<HashMap<String, String>> fee_list;

        public CustomAdapter(Context context, List<ListModel> rowItems) {
            this.context = context;
            this.rowItems = rowItems;
            inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public int getCount() {
            return rowItems.size();
        }

        public Object getItem(int position) {
            return rowItems.get(position);
        }

        public long getItemId(int position) {
            return rowItems.indexOf(getItem(position));
        }

        /**
         * ****** Create a holder Class to contain inflated xml file elements ********
         */
        public class ViewHolder {

            public TextView sname, std,totfee,total,branch,stand,batch,subj,date,stud_id;
            ImageView img_delete, img_call1, img_call2;
            ImageView ivProfile;

            //		WebView reason;
            //	ImageView sync;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi = convertView;
            final ViewHolder holder;

            convertView = null;
            if (convertView == null) {
                /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
                vi = inflater.inflate(R.layout.studentlistitem_in_feemgmt, null);
                /****** View Holder Object to contain tabitem.xml file elements ******/

                holder = new ViewHolder();
                holder.stud_id = (TextView) vi.findViewById(R.id.auto_id);
                holder.branch = (TextView) vi.findViewById(R.id.branch);
                holder.stand = (TextView) vi.findViewById(R.id.stand);
                holder.date = (TextView) vi.findViewById(R.id.stud_date);
                holder.batch = (TextView) vi.findViewById(R.id.batch);
                holder.subj = (TextView) vi.findViewById(R.id.subjects);
                holder.sname = (TextView) vi.findViewById(R.id.sname);
                holder.ivProfile=(ImageView) vi.findViewById(R.id.ivProfile);
                holder.totfee = (TextView) vi.findViewById(R.id.totfee);
                holder.total = (TextView) vi.findViewById(R.id.total);


                /************ Set holder with LayoutInflater ************/
                vi.setTag(holder);
            } else
                holder = (ViewHolder) vi.getTag();

            final ListModel row_pos = rowItems.get(position);

            holder.sname.setText(row_pos.getName());




            sid = ((MyApplication) getApplication()).dbo.getSId(row_pos.getId());
            String tot = ((MyApplication) getApplication()).dbo.GetFee(sid, row_pos.getStandard(), row_pos.getBatch(), row_pos.getBranch(), MyApplication.get_session("classid"));
           // String feeAmt = ((MyApplication) getApplication()).dbo.GetFeesPaid(row_pos.getId());

            String feeAmt = ((MyApplication) getApplication()).dbo.gettotalbal_new(MyApplication.get_session("std"), row_pos.getId());
            MyApplication.log("JARVIS", "VALUE of : " + feeAmt);
           // holder.totfee.setText(tbal+"");

            Log.i("StudentListInFee", "tot" + tot);
            Log.i("StudentListInFee","feeAmt"+feeAmt);
            String stdname = ((MyApplication) getApplication()).dbo.getStandardName(row_pos.getStandard().toString());
            final String brnchname = ((MyApplication) getApplication()).dbo.getBranchName(row_pos.getBranch().toString());
            String batch = ((MyApplication) getApplication()).dbo.getbatchhhName(row_pos.getBatch().toString());


            String subjname = ((MyApplication) getApplication()).dbo.getSubjjjnameWithMultilpe(sid);

            Log.d("tag", "subjname" + subjname);

            holder.branch.setText(brnchname);
            holder.stand.setText(stdname);
            holder.batch.setText(batch);
            holder.subj.setText(subjname);
            holder.date.setText(row_pos.getDate());
            holder.stud_id.setText(row_pos.getAuto_id());
            holder.sname.setTypeface(roboto_reguler);
            holder.branch.setTypeface(roboto_reguler);
            holder.stand.setTypeface(roboto_reguler);
            holder.batch.setTypeface(roboto_reguler);
            holder.subj.setTypeface(roboto_reguler);
            holder.date.setTypeface(roboto_reguler);
            holder.stud_id.setTypeface(roboto_reguler);
            // int bal = Integer.parseInt(tot)- Integer.parseInt(feeAmt);
            holder.totfee.setText(feeAmt+"");
            holder.total.setText(tot);

            if (row_pos.getProfile() != null)
            {
                if (row_pos.getProfile().contains("[B@") || row_pos.getProfile().equals(""))
                {
                    Bitmap bitmap = MyApplication.decodeBase64Profile(row_pos.getProfile());
                    Glide.with(context)
                            .load(bitmap)
                            .error(R.drawable.ic_person_grey600_24dp)
                            .placeholder(R.drawable.ic_person_grey600_24dp)
                            .into(holder.ivProfile);
                    //.into((holder).ivProfile);
                }
                else if (row_pos.getProfile() != null)
                {
                    Log.i("tag", "getProfile is " + row_pos.getName() +" "+row_pos.getProfile());
                    Bitmap bitmap = MyApplication.decodeBase64Profile(row_pos.getProfile());
                    holder.ivProfile.setImageBitmap(bitmap);
                    // (holder).ivProfile.setImageBitmap(bitmap);
                }
            }




            vi.setContentDescription(row_pos.getId());
            vi.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    sid = ((MyApplication) getApplication()).dbo.getSId(row_pos.getId());
                    //sid = sid.substring(0, sid.length() - 1);
                    Log.i("sid", "sid" + sid);


                    /*fee_list = new ArrayList<HashMap<String, String>>();

                    fee_list = ((MyApplication) getApplication()).dbo.GetFeeData();
                    Log.i("fee_list", "fee_list" + fee_list);
                    for (int j = 0; j < fee_list.size(); j++) {
                        Log.i("fee", "fee_list.get(j).get(\"subjects\")" + fee_list.get(j).get("subjects"));
                        String d = fee_list.get(j).get("subjects");
                        Log.i("d", "d" + d);
                        Log.i("d", "sid" + sid);

                        if (sid.equals(d)) {
                            Log.i("fee", "if condition" + sid.toString().equals(fee_list.get(j).get("subjects")));

                            String fee = fee_list.get(j).get("fee_amount");
                            Log.i("fee", "fee" + fee);
                        }
                    }*/

                    String feeAmt = ((MyApplication) getApplication()).dbo.GetFee(sid,row_pos.getStandard(),row_pos.getBatch(),row_pos.getBranch(),MyApplication.get_session("classid"));
                    if(feeAmt.length()>0){
                        MyApplication.set_session("amt", feeAmt);
                    }


                    MyApplication.set_session("studid", row_pos.getId());
                    MyApplication.set_session("name", row_pos.getName());
                    MyApplication.set_session("std", row_pos.getStandard());
                    MyApplication.set_session("sb", sid);

                    Intent i = new Intent(ActivityFeeManagement.this, StudentFeeStructure.class);
                    i.putExtra("branch", MyApplication.dbo.getBranchID(holder.branch.getText().toString()));
                    i.putExtra("batch", MyApplication.dbo.getBatchID(holder.batch.getText().toString()));
                    i.putExtra("stand", MyApplication.dbo.getStandID(holder.stand.getText().toString()));
                    i.putExtra("subj", MyApplication.dbo.getSId(row_pos.getId()));
                    startActivity(i);
                    finish();



                }
            });


            return vi;

        }

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub

        }
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(ActivityFeeManagement.this, ActivityDashboard.class);
        startActivity(i);
        finish();
    }

    public void ErrorDialog(String text)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage(text)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(context, Activity_profileNew.class);
                        i.putExtra("dialog_tab","1");
                        startActivity(i);
                        finish();;

                    }
                })

                .show();
    }
}
