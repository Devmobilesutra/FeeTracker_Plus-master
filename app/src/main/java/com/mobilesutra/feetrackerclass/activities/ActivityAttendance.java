package com.mobilesutra.feetrackerclass.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.bumptech.glide.Glide;
import com.mobilesutra.feetrackerclass.FAB.FloatingActionButton;
import com.mobilesutra.feetrackerclass.ListModel;
import com.mobilesutra.feetrackerclass.MyApplication;
import com.mobilesutra.feetrackerclass.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ActivityAttendance extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener, ActivityCompat.OnRequestPermissionsResultCallback,
        BillingProcessor.IBillingHandler {

    Context context = this;
    LinkedHashMap<String, String> lhm_branch = new LinkedHashMap<String, String>();// Branch
    LinkedHashMap<String, String> lhm_std = new LinkedHashMap<String, String>();// Std
    LinkedHashMap<String, String> lhm_batch = new LinkedHashMap<String, String>();// Batch
    LinkedHashMap<String, String> lhm_sub = new LinkedHashMap<String, String>();// Subjects
    LinkedHashMap<String, String> lhm_sub2 = new LinkedHashMap<String, String>();// Subjects
    Spinner spnr_branch=null,spnr_stand=null,spnr_batch=null;
    MultiSelectionSpinner mspnr_subject = null;
    String selected_subj_value="",prv_stand_value="",prv_batch_value="",selected_branch_value = "", selected_stand_value = "", selected_batch_value = "", id = "", rowid = "", edit_batch, edit_stand,date4 = "",stud_id = "";
    Boolean  flag=false,flag_data=true;
    ArrayList<String> arrstand1 ;
    ArrayList<String> arrbatch ;
    String[] array2;
    String Subject_Id;
    List<String> SelectedSubj = new ArrayList<>();
    TextView txt_class_name,txt_activity_header,txt_date_picker;
    Typeface roboto_reguler,roboto_light;

    List<ListModel> arrayDTO = null;
    RecyclerView recycler_view_student = null;
    RecyclerAdapter recyclerAdapter = null;
    ArrayList<HashMap<String, String>> menu_list;
    ArrayList<ListModel> rowItems1 = new ArrayList<ListModel>();
    List<ListModel> rowItems;
    ArrayList<HashMap<String, String>> imcompletefees;
    final ArrayList<String> absent_ids = new ArrayList<String>();
    final ArrayList<String> absent_ids_parent = new ArrayList<String>();
    final ArrayList<String> stud_names = new ArrayList<String>();
    final ArrayList<String> ids = new ArrayList<String>();
    final ArrayList<String> names = new ArrayList<String>();
    String feeflag, sid;
    static String LOG_TAG = "ActivityAttendance";
    Button send, SendWothoutsms;
    int PERMISSION_REQUEST_SMS = 1;
    FloatingActionButton student_clearall_button;


    // Billing
    private Button btnBuy;
    BillingProcessor bp;
    private String APP_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtVlqUkDuCS2e3ADCQU4+BkaVfkE4EujmiZYo3HYFhrvoFaOk9fMCx7CvCGDhddF/3QFNUD6kdVq2aX2dvShN4gfnOuqj3GCAWewURaRQk5H9aKFW/K8/m2xls0Fl5guMbUjLlvGhBQKhj4WFHyds0ERWEskJFoXuI8leMLl+z1fZXfZAho0BSVi4t/Om8D4jpreypphvQ5NFjW5G3sbtUbbxGtQo/UrtxqPjgz7ogfmc1I0TxXIZFzLI9OtCbJlt45DPKvJpjBeGCjawOWvbiG7kadV3bhb1o0/1HcXV8b+yD26FAOYaDpN5tT6PEd1lBxz2x0nP8dXfWTXngj2lywIDAQAB";
    private String USER_KEY = MyApplication.get_session(MyApplication.SESSION_USERTEXT);
    private String PRODUCT_ID = "attendance";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        MyApplication.log(LOG_TAG, "onCreate()");

        context = this;
        initComponents();
        initSpinnerData();
        initComponentListeners();
        bindComponentData();

    }

    private void initComponents() {

        send = (Button) findViewById(R.id.Send);
      //  send.setVisibility(View.VISIBLE);
        SendWothoutsms = (Button) findViewById(R.id.SendWothoutsms);
        btnBuy= (Button)findViewById(R.id.btnBuy);

        txt_date_picker = (TextView) findViewById(R.id.txt_date_picker);
        spnr_branch = (Spinner) findViewById(R.id.spnr_branch);
        spnr_stand = (Spinner) findViewById(R.id.spnr_stand);
        spnr_batch = (Spinner) findViewById(R.id.spnr_batch);
        mspnr_subject = (MultiSelectionSpinner) findViewById(R.id.mspnr_subject);

        roboto_light =  Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
        roboto_reguler =  Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");
        txt_class_name = (TextView) findViewById(R.id.txt_class_name);
        txt_activity_header = (TextView) findViewById(R.id.txt_activity_header);
        txt_activity_header.setTypeface(roboto_reguler);
        txt_activity_header.setText("Attendance");
        txt_class_name.setText(MyApplication.get_session("classname"));
        send.setTypeface(roboto_reguler);

        SendWothoutsms.setTypeface(roboto_reguler);
        btnBuy.setTypeface(roboto_reguler);
        recycler_view_student = (RecyclerView) findViewById(R.id.recycler_view_student);
        arrayDTO = new ArrayList<ListModel>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityAttendance.this);
        recycler_view_student.setLayoutManager(linearLayoutManager);
        recycler_view_student.setHasFixedSize(true);
        student_clearall_button = (FloatingActionButton) findViewById(R.id.fab_clear_attendance);


        //Billing
        bp = BillingProcessor.newBillingProcessor(this, APP_KEY, USER_KEY, this); // doesn't bind
        bp.initialize();
        Log.d("onBillingError", "> initialize");


        for(String sku : bp.listOwnedSubscriptions())
        {
            Log.d("onBillingError", "Owned Subscription: " + sku);
            Log.d("onBillingError", "> Success - getSubscriptionTransactionDetails");
            if (bp.isSubscribed(PRODUCT_ID)) {
                btnBuy.setVisibility(View.GONE);
                send.setVisibility(View.VISIBLE);
                Log.d("onBillingError", "> Success - True");
            } else {
                btnBuy.setVisibility(View.VISIBLE);
                send.setVisibility(View.GONE);
                Log.d("onBillingError", "> Success - else");
            }
        }
    }

    private void initSpinnerData() {
        Date d = new Date();
        CharSequence s = DateFormat.format("dd" + "/" + "MM" + "/" + "yyyy", d.getTime());
        txt_date_picker.setText(s.toString());

        //add spinner _branch details
        lhm_branch = MyApplication.dbo.getBranchWithActiveFlag(MyApplication.get_session("classid"));
        if (lhm_branch.size() == 0) {

            ErrorDialog("Please go to profile page  and add class details");
        } else {
            log(lhm_branch + "");
            ArrayList<String> arrBranch = new ArrayList<String>();

            for (Object o : lhm_branch.keySet()) {
                arrBranch.add(o.toString());
                System.out.println("key:" + o.toString() + "___" + "value:" + lhm_branch.get(o).toString());
            }
            ArrayAdapter<String> adapter0 = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arrBranch);
            adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnr_branch.setAdapter(adapter0);


            spnr_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //select branch value
                    selected_branch_value = lhm_branch.get(spnr_branch.getSelectedItem().toString());
                    MyApplication.set_session(MyApplication.add_session_branch, selected_branch_value);
//                Toast.makeText(context, "selected_stand_value"+selected_branch_value,
//                        Toast.LENGTH_SHORT).show();
                    bindComponentData();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            int count1 = lhm_branch.size();
            if (count1 != 0) {
                selected_branch_value = lhm_branch.get(spnr_branch.getSelectedItem().toString());
                if (MyApplication.get_session(MyApplication.add_session_branch).equals(""))
                    MyApplication.set_session(MyApplication.add_session_branch, selected_branch_value);

            }

            if (flag_data) {

                String branchname = MyApplication.dbo.getBranchName(MyApplication.get_session(MyApplication.add_session_branch));
                int position = arrBranch.indexOf(branchname);
//                Toast.makeText(context,"value "+MyApplication.get_session(MyApplication.session_branch)+position,Toast.LENGTH_SHORT).show();
                spnr_branch.setSelection(position, true);
                selected_branch_value = lhm_branch.get(spnr_branch.getSelectedItem().toString());
                MyApplication.set_session(MyApplication.add_session_branch, selected_branch_value);

            }
            //add spinner standard  details
            lhm_std = MyApplication.dbo.getStandard(MyApplication.get_session("classid"), selected_branch_value);
            log(lhm_std + "");
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
                    MyApplication.set_session(MyApplication.add_session_stand, selected_stand_value);//**
//                Toast.makeText(context, "selected_stand_value"+selected_stand_value,
//                        Toast.LENGTH_SHORT).show();

                    ;
                    bindComponentData();


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });


            int count2 = lhm_std.size();
            if (count2 != 0) {
                selected_stand_value = lhm_std.get(spnr_stand.getSelectedItem().toString());
                if (MyApplication.get_session(MyApplication.add_session_stand).equals(""))
                    MyApplication.set_session(MyApplication.add_session_stand, selected_stand_value);//**
            }

            if (flag_data) {

                String standname = MyApplication.dbo.getStandardName(MyApplication.get_session(MyApplication.add_session_stand));
                int position = arrstand1.indexOf(standname);
//                Toast.makeText(context,"value "+MyApplication.get_session(MyApplication.session_branch)+position,Toast.LENGTH_SHORT).show();
                spnr_stand.setSelection(position, true);
                selected_stand_value = lhm_std.get(spnr_stand.getSelectedItem().toString());
                MyApplication.set_session(MyApplication.add_session_stand, selected_stand_value);

            }


            lhm_batch = MyApplication.dbo
                    .getBatch(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value);
            log(lhm_batch + "");
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
                    MyApplication.set_session(MyApplication.add_session_batch, selected_batch_value);//**
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
                if (MyApplication.get_session(MyApplication.add_session_batch).equals(""))
                    MyApplication.set_session(MyApplication.add_session_batch, selected_batch_value);//**

            }

            if (flag_data) {

                String batchname = MyApplication.dbo.getbatchhhName(MyApplication.get_session(MyApplication.add_session_batch));
                int position = arrbatch.indexOf(batchname);
//                Toast.makeText(context,"value "+MyApplication.get_session(MyApplication.session_branch)+position,Toast.LENGTH_SHORT).show();
                spnr_batch.setSelection(position, true);


                selected_batch_value = lhm_batch.get(spnr_batch.getSelectedItem().toString());
                MyApplication.set_session(MyApplication.add_session_batch, selected_batch_value);

            }


            lhm_sub = MyApplication.dbo.getSubjectWithInsertFee(MyApplication.get_session("classid"));
            // lhm_sub = MyApplication.dbo.getSubject(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value);
            MyApplication.log(LOG_TAG, "lhm_sub2" + lhm_sub);
            List<String> array1_subj = new ArrayList<>();
            for (Object o : lhm_sub.keySet()) {
                array1_subj.add(o.toString());
                System.out.println("subject-key:" + o.toString() + "___" + "value:" + lhm_sub.get(o).toString());
            }
            MyApplication.log(LOG_TAG, "array1" + array1_subj);
            int[] numbers = {0};
            array2 = array1_subj.toArray(new String[array1_subj.size()]);

            if (array2.length != 0)
                set(array2, numbers);

            if (lhm_sub.size() != 0) {


            }

        }

        student_clearall_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Delete Confirm");
                alertDialog.setMessage("Do you want to delete all past attendance records of all students? Please ensure that you have taken backup.");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        MyApplication.dbo.deleteAttendance();
                        bindComponentData();

                    }
                });


                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });
    }

    private void initComponentListeners() {


        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.subscribe(ActivityAttendance.this, PRODUCT_ID);
            }
        });



        SendWothoutsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int records = menu_list.size();
                int c = ids.size();
                Log.i("TAG", "ids_-->" + ids);
                Log.i("TAG", "ids_-->" + ids);

                if (selected_subj_value.length() == 0)
                    selected_subj_value = "";

//                if (toggle_flag.equals("P")) {
//                    toggle_flag_2 = "A";
//                } else {
//                    toggle_flag_2 = "p";
//                }

//                for (int i = 0; i < records; i++) {
//
//                    String isvalue = menu_list.get(i).get("id");
                String name = "";

                for (int j = 0; j < c; j++) {
                    stud_id = ids.get(j);
                    String toggle_flag_2 = "P";
                    name = MyApplication.dbo.getStudentName(stud_id);
                    Log.d("Absent_ids","absent_ids"+stud_id);
                    Log.d("stud_names","stud_names"+name);
//                          ((MyApplication) getApplication()).dbo.insertStudentAttendance(MyApplication.get_session("classid"), branch, std, batch, sub, ids, names, date); //MyApplication.get_session("br"), MyApplication.get_session("st"), MyApplication.get_session("ba"), MyApplication.get_session("su")
                    String rowId = MyApplication.dbo.insertAbsentStudentRegister(MyApplication.get_session("classid"), stud_id, name, date4, selected_branch_value, selected_stand_value, selected_batch_value, Subject_Id, toggle_flag_2, selected_subj_value);

                }
                Toast.makeText(context,"Attendance is saved successfully.It can be seen on your web panel after taking backup", Toast.LENGTH_LONG).show();
//                    String rowId = MyApplication.dbo.insertAbsentStudentRegister(MyApplication.get_session("classid"), stud_id, name, birthdate.getText().toString(), selected_branch_value, selected_stand_value, selected_batch_value, Subject_Id, toggle_flag);
//                    Log.i("TAG", "ids_-->insert" + rowId);
////                    ((MyApplication) getApplication()).dbo.insertStudentAttendance(MyApplication.get_session("classid"), branch, std, batch, sub, ids, names, date); //MyApplication.get_session("br"), MyApplication.get_session("st"), MyApplication.get_session("ba"), MyApplication.get_session("su")


//


//                }

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                absent_ids.clear();
                absent_ids_parent.clear();
                stud_names.clear();

                int leni = rowItems.size();
                for(int i=0;i<leni;i++)
                {
                    String value=rowItems.get(i).getPresentFlag();
                    String value2=rowItems.get(i).getStudphone();
                    String value3=rowItems.get(i).getPhone1();


                    String sname=rowItems.get(i).getName();
                    Log.i("###", "add-" + value);
                    if(value.equals("N"))
                    {
                        Log.i("###", "add-" + value2);
                        absent_ids.add(value2);
                        absent_ids_parent.add(value3);
                        stud_names.add(sname);
                    }

                }

                Log.d("Absent_ids","absent_ids"+absent_ids);
                Log.d("Absent_ids","absent_ids_parent"+absent_ids_parent);
                Log.d("stud_names", "stud_names" + stud_names);

                if (leni == 0) {

                    Toast.makeText(context,"There are no absent students", Toast.LENGTH_LONG).show();

                } else {

                    showdialog_SMS(leni);
                }



                /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("")
                        .setMessage(getResources().getString(R.string.dialog_send_sms))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("confirmation", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {




                                String name = "";
                                ;
                                int records = menu_list.size();
                                int c = ids.size();
                                Log.i("TAG", "ids_-->" + ids);

                                if (selected_subj_value.length() == 0)
                                    selected_subj_value = "";


                                for (int j = 0; j < c; j++) {
                                    stud_id = ids.get(j);
                                    name = MyApplication.dbo.getStudentName(stud_id);

                                    String toggle_flag_2 = "P";

                                    log("************", stud_id + " " + name);


                                    String rowId = MyApplication.dbo.insertAbsentStudentRegister(MyApplication.get_session("classid"), stud_id, name, date4, selected_branch_value, selected_stand_value, selected_batch_value, Subject_Id, toggle_flag_2, selected_subj_value);

                                }
                                log("absent student list-", absent_ids + "");
//                                send_sms(absent_ids);
//                                Toast.makeText(context,getResources().getString(R.string.send_attendance) , Toast.LENGTH_LONG).show();


                                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    //This is marshmellow and above version code
                                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                                        //application has permission
                                        Log.d("if ", "absent_student" + absent_ids);

                                        send_sms(absent_ids,absent_ids_parent,stud_names);
                                        Toast.makeText(context, getResources().getString(R.string.success_send_sms), Toast.LENGTH_LONG).show();
//                                        absent_ids.clear();

                                    } else {
                                        //application has not permission.Request for permission
                                        requestSMSPermission();
                                    }
                                } else {
                                    //This is below marshmellow version code

                                    Log.d("else ", "absent_student" + absent_ids);
                                    send_sms(absent_ids,absent_ids_parent,stud_names);
                                    Toast.makeText(context, getResources().getString(R.string.success_send_sms), Toast.LENGTH_LONG).show();

//                                            absent_ids.clear();

                                }



                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();*/


            }
        });

        txt_date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // TODO Auto-generated method stub
                // To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(ActivityAttendance.this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker,
                                                  int selectedyear, int selectedmonth,
                                                  int selectedday) {
                                // TODO Auto-generated method stub
                                /* Your code to get date and time */
                                selectedmonth = selectedmonth + 1;
                                txt_date_picker.setText("" + selectedday + "/"
                                        + selectedmonth + "/" + selectedyear);
                                bindComponentData();
                            }
                        }, mYear, mMonth, mDay);


                mDatePicker.setTitle("Select Date");
                mDatePicker.show();


            }
        });
        String check = txt_date_picker.getText().toString();
        date4 = ((MyApplication) getApplication()).changedateformat(check);
        Log.d("tag", "tejas-" + date4);
        Log.d("tag", "tejas-" + date4);
    }





    private void bindComponentData() {
        menu_list = null;
        menu_list = new ArrayList<HashMap<String, String>>();


        if (lhm_sub.size() != 0) {
            String[] array = Subject_Id.split(",");
            Log.i("menu_list", "##" + array.length);
            if (array.length == 1) {

                //l1.setVisibility(View.GONE);

            } else {
               // l1.setVisibility(View.VISIBLE);


                lhm_sub2 = MyApplication.dbo.getSubject(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value);

                log(lhm_sub2 + "");
                ArrayList<String> arrBranch2 = new ArrayList<String>();

                for (Object o : lhm_sub2.keySet()) {
                    arrBranch2.add(o.toString());
                    System.out.println("key:" + o.toString() + "___" + "value:" + lhm_sub2.get(o).toString());
                }
                ArrayAdapter<String> adapter0 = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arrBranch2);
                adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //spinner_subj.setAdapter(adapter0);

            }


          /*  spinner_subj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selected_subj_value = lhm_sub2.get(spinner_subj.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/
        }


//        menu_list = ((MyApplication) getApplication()).dbo.getStudentListForAttendance(MyApplication.get_session("classid"),selected_branch_value,selected_stand_value,selected_batch_value,id);//MyApplication.get_session("br"), MyApplication.get_session("st"), MyApplication.get_session("ba"), MyApplication.get_session("su")
//
//        rowItems1 = new ArrayList<ListModel>();
//        Log.i("menu_list", "attendance-list" + menu_list);
//
//        int records = menu_list.size();
//
//        for (int i = 0; i < records; i++) {
//
//
//            ListModel item = new ListModel(menu_list.get(i).get("id"), menu_list.get(i).get("sname"), menu_list.get(i).get("phoneno"), menu_list.get(i).get(""), menu_list.get(i).get(""),"","");
//
//            rowItems1.add(item);
//
//            Log.i("rowItems1:id", "" + rowItems1.get(i).getId());
//            Log.i("rowItems1:name", "" + rowItems1.get(i).getName());
//            Log.i("rowItems1:phoneno", "" + rowItems1.get(i).getPhone1());
//
//        }
//
//
//        customAdapter = new CustomAdapter(this, rowItems1);
//        list.setAdapter(customAdapter);


        String check = txt_date_picker.getText().toString();
        date4 = ((MyApplication) getApplication()).changedateformat(check);
        MyApplication.log(LOG_TAG, "date" + date4);

        menu_list = ((MyApplication) getApplication()).dbo.getStudentListForAttendance(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value, Subject_Id, date4);//MyApplication.get_session("br"), MyApplication.get_session("st"), MyApplication.get_session("ba"), MyApplication.get_session("su")
        rowItems1 = new ArrayList<ListModel>();
//        ((MyApplication) getApplication()).dbo.getPresentList()
        int records = menu_list.size();
        for (int i = 0; i < records; i++) {

            ListModel item = new ListModel(
                    menu_list.get(i).get("id"),
                    menu_list.get(i).get("auto_id"),
                    menu_list.get(i).get("sname"),
                    menu_list.get(i).get("studentprofile"),
                    menu_list.get(i).get("parent_phno"),
                    menu_list.get(i).get("stud_phno"),
                    menu_list.get(i).get("flags"),
                    "");
            rowItems1.add(item);
            ids.clear();
            absent_ids.clear();
            if(menu_list.get(i).get("flags").equalsIgnoreCase("Y"))
                ids.add(menu_list.get(i).get("id"));
            else
                absent_ids.add(menu_list.get(i).get("id"));
        }

        recyclerAdapter = new RecyclerAdapter(rowItems1);
        recycler_view_student.setAdapter(recyclerAdapter);

    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        btnBuy.setVisibility(View.GONE);
        send.setVisibility(View.VISIBLE);

        Log.d("onBillingError", "> Success - onProductPurchased");
    }

    @Override
    public void onPurchaseHistoryRestored() {
        for(String sku : bp.listOwnedSubscriptions())
        {
            Log.d("onBillingError", "Owned Subscription: " + sku);
            Log.d("onBillingError", "> Success - getSubscriptionTransactionDetails");
            if (bp.isSubscribed(PRODUCT_ID)) {
                btnBuy.setVisibility(View.GONE);
                send.setVisibility(View.VISIBLE);
                Log.d("onBillingError", "> Success - True");
            } else {
                btnBuy.setVisibility(View.VISIBLE);
                send.setVisibility(View.GONE);
                Log.d("onBillingError", "> Success - else");
            }
        }

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public RecyclerAdapter(List<ListModel> rowItems2) {
            MyApplication.log(LOG_TAG,"In RecyclerAdapter  rowItems--> "+rowItems);
            //MyApplication.log(LOG_TAG,"rowItemsSize->"+rowItems.size());
            rowItems = rowItems2;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            RecyclerView.ViewHolder vh;
            final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attendance_list, viewGroup, false);
            vh = new MyViewHolder(view);

            return vh;
        }


        public void Remove(int pos) {
            rowItems.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(0, rowItems.size());
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            ((MyViewHolder) holder).check.setTag(position);
            int pos = (int) ((MyViewHolder) holder).check.getTag();
            final ListModel row_pos = rowItems.get(pos);

            ((MyViewHolder) holder).sname.setText(row_pos.getName());
            ((MyViewHolder) holder).roll.setText(row_pos.getAuto_id());

            if (row_pos.getProfile() != null)
            {
                if (row_pos.getProfile().contains("[B@") || row_pos.getProfile().equals(""))
                {
                    Bitmap bitmap = MyApplication.decodeBase64Profile(row_pos.getProfile());
                    Glide.with(context)
                            .load(bitmap)
                            .error(R.drawable.ic_person_grey600_24dp)
                            .placeholder(R.drawable.ic_person_grey600_24dp)
                            .into(((MyViewHolder) holder).ivProfile);
                }
                else if (row_pos.getProfile() != null)
                {
                    Log.i("tag", "getProfile is " + row_pos.getName() +" "+row_pos.getProfile());
                    Bitmap bitmap = MyApplication.decodeBase64Profile(row_pos.getProfile());
                    ((MyViewHolder) holder).ivProfile.setImageBitmap(bitmap);
                }
            }




            String flagp = row_pos.getPresentFlag();
//            MyApplication.log(LOG_TAG, "id with balance " + row_pos.getId() + " ---->" + row_pos.getPhone1());


//            int leni= row_pos.getId().length();
            int leni=rowItems.size();
            MyApplication.log(LOG_TAG, "------len "+leni);

//            MyApplication.log(LOG_TAG, "------len "+absent_ids);
            MyApplication.log(LOG_TAG, "------absent_idslen " + absent_ids.size());
            if (row_pos.getPresentFlag().equals("Y")) {
                ((MyViewHolder) holder).check.setChecked(true);

            } else {
                ((MyViewHolder) holder).check.setChecked(false);
            }

            log("absent student list-", absent_ids + "");

           // ((MyViewHolder) holder).check.setTag(position);

            imcompletefees = new ArrayList<HashMap<String, String>>();
            imcompletefees = ((MyApplication) getApplication()).dbo.getBalance(row_pos.getId());
            MyApplication.log(LOG_TAG, " ID:->"+ row_pos.getId()+" ,  NAME: "+row_pos.getName()+" , HASHMAP "+ imcompletefees.toString());

            if ( imcompletefees.size() > 0 ) {
                MyApplication.log(LOG_TAG, " ID:->"+ row_pos.getId()+" , NAME: "+row_pos.getName()+" , SETTING GREEN COLOR");
                //  holder.sname.setTextColor(Color.parseColor("#8b1919"));
                ((MyViewHolder) holder).circle.setBackgroundResource(R.drawable.ovalshape);
            }
            else{
                MyApplication.log(LOG_TAG, " ID:->"+ row_pos.getId()+" , NAME: "+row_pos.getName()+" , SETTING RED COLOR");
                ((MyViewHolder) holder).circle.setBackgroundResource(R.drawable.ovalred);
            }


            //  holder.sname.setTextColor(Color.parseColor("#8b1919"));


            ((MyViewHolder) holder).check.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                  //  CheckBox cb = (CheckBox) v;
                    final int pos = (int)  ((MyViewHolder) holder).check.getTag();
                    final ListModel row_pos = rowItems.get(pos);


                    imcompletefees = new ArrayList<HashMap<String, String>>();
                    imcompletefees = ((MyApplication) getApplication()).dbo.getBalance(row_pos.getId());
                    MyApplication.log(LOG_TAG, "CHECK==> ID:->"+ row_pos.getId()+" ,  NAME: "+row_pos.getName()+" , HASHMAP "+ imcompletefees.toString());

                    if ( ((MyViewHolder) holder).check.isChecked()) {

                        row_pos.setPresentFlag("Y");

                        String id = row_pos.getId().toString();

                        ids.add(row_pos.getId().toString());

                        names.add(row_pos.getName());
                        if (absent_ids.contains(row_pos.getId())) {
                            MyApplication.log(LOG_TAG, "remove-" + row_pos.getId());
                            absent_ids.remove(absent_ids.indexOf(row_pos.getId()));
                        }

                        if ( imcompletefees.size() > 0 ) {
                            MyApplication.log(LOG_TAG, "CHECK==> ID:->"+ row_pos.getId()+" , NAME: "+row_pos.getName()+" , SETTING GREEN COLOR");
                            //  holder.sname.setTextColor(Color.parseColor("#8b1919"));
                            ((MyViewHolder) holder).circle.setBackgroundResource(R.drawable.ovalshape);
                        }
                        else{
                            MyApplication.log(LOG_TAG, "CHECK==> ID:->"+ row_pos.getId()+" , NAME: "+row_pos.getName()+" , SETTING RED COLOR");
                            ((MyViewHolder) holder).circle.setBackgroundResource(R.drawable.ovalred);
                        }
                        recyclerAdapter.notifyItemChanged(pos);

                    } else {
                        String id = row_pos.getId().toString();

                        row_pos.setPresentFlag("N");

                        if (ids.contains(id)) {
                            ids.remove(ids.indexOf(id));
                        }

                        if ( imcompletefees.size() > 0 ) {
                                MyApplication.log(LOG_TAG, "UNCHECK==> ID:->"+ row_pos.getId()+" , NAME: "+row_pos.getName()+" , SETTING GREEN COLOR");
                                //  holder.sname.setTextColor(Color.parseColor("#8b1919"));
                                ((MyViewHolder) holder).circle.setBackgroundResource(R.drawable.ovalshape);
                        }
                        else{
                                MyApplication.log(LOG_TAG, "UNCHECK==> ID:->"+ row_pos.getId()+" , NAME: "+row_pos.getName()+" , SETTING RED COLOR");
                                ((MyViewHolder) holder).circle.setBackgroundResource(R.drawable.ovalred);
                        }
                        recyclerAdapter.notifyItemChanged(pos);
                    }

                }
            });


        }

        @Override
        public int getItemCount() {
            return rowItems == null ? 0 : rowItems.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView sname, roll, ispresent, feeflag;
            CheckBox check;
            ImageView circle;
            ImageView ivProfile;

            public MyViewHolder(View v) {
                super(v);


                sname = (TextView) v.findViewById(R.id.sname);
                roll = (TextView) v.findViewById(R.id.id);
                ispresent = (TextView) v.findViewById(R.id.ispresent);
                check = (CheckBox) v.findViewById(R.id.checkBox1);
                feeflag = (TextView) v.findViewById(R.id.feeflag);
                circle = (ImageView) v.findViewById(R.id.circle);
                ivProfile = (ImageView)v.findViewById(R.id.ivProfile);
                sname.setTypeface(roboto_reguler);
                roll.setTypeface(roboto_reguler);
//            holder.ispresent.setTypeface(roboto_light);
                feeflag.setTypeface(roboto_reguler);
                /************ Set holder with LayoutInflater ************/


            }


        }


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

    public static void log(String str) {
        if (str.length() > 4000) {
            Log.i(LOG_TAG, str.substring(0, 4000));
            log(str.substring(4000));
        } else
            Log.i(LOG_TAG, str);
    }
    public static void log(String LOG_TAG,String str) {
        if (str.length() > 4000) {
            Log.i(LOG_TAG, str.substring(0, 4000));
            log(str.substring(4000));
        } else
            Log.i(LOG_TAG, str);
    }
    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {

        Subject_Id = "";
        SelectedSubj = strings;
        log("chek_subjectid", SelectedSubj+"");
        int count = SelectedSubj.size();

        for (int j = 0; j < count; j++) {

            String checkedsubj = lhm_sub.get(SelectedSubj.get(j));

            if (Subject_Id.equals("")) {
                Subject_Id = checkedsubj;

            } else {
                Subject_Id = Subject_Id + "," + checkedsubj;

            }


        }

        log("chek_subjectid", Subject_Id);
        bindComponentData();
//        Toast.makeText(context, strings.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(ActivityAttendance.this, ActivityDashboard.class);
        startActivity(i);
        finish();
    }

    public void set(String[] array2, int[] pos) {
        mspnr_subject.setItems(array2);
        mspnr_subject.setSelection(pos);
        mspnr_subject.setListener(this);
        mspnr_subject.set_selection();

    }

    private void send_sms(ArrayList<String> absent_ids, ArrayList<String> absent_ids_parent,
                          ArrayList<String> stud_names, String smsFlag) {
        int len3 = absent_ids.size();
        Log.d("if ", "absent_student" + len3);


        if (smsFlag.equalsIgnoreCase("p")) {
            for (int k = 0; k < len3; k++) {
                try {
                    Log.d("mobile no-", "absent_student_parent--" + absent_ids_parent.get(k));
                    Log.d("selected_subj-", "selected_subj--" + selected_subj_value );

                    String subject_name = MyApplication.dbo.getSubjjjnameWithMultilpe(Subject_Id);
                    Log.d("selected_subj-", "subject_name--" + subject_name);

                    if(((MyApplication) getApplication()).isNetworkAvailable()){
                        //AsyncSendSMS asyncSendSMS = new AsyncSendSMS(absent_ids_parent.get(k),getResources().getString(R.string.dear) + " " + stud_names.get(k) + getResources().getString(R.string.you_are_absent) + " " + subject_name  + " " + getResources().getString(R.string.session));
                        AsyncSendSMS asyncSendSMS = new AsyncSendSMS(absent_ids_parent.get(k),
                                "Dear parents,\n "+ stud_names.get(k)+" was absent on "+date4 +" for the "+ subject_name+" session.");
                        asyncSendSMS.execute();
                    }
                    /*SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+91" + absent_ids_parent.get(k), null, getResources().getString(R.string.dear) + stud_names.get(k) + getResources().getString(R.string.you_are_absent) + subject_name + getResources().getString(R.string.session), null, null);*/
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.fail_send_sms), Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            Toast.makeText(context, getResources().getString(R.string.success_send_sms) + " to " + len3 + " parents", Toast.LENGTH_LONG).show();

        } else if (smsFlag.equalsIgnoreCase("s")) {


            for (int k = 0; k < len3; k++) {
                try {
                  //  Log.d("mobile no-", "absent_student--" + absent_ids.get(k));
                  //  Log.d("selected_subj-", "selected_subj--" + selected_subj_value );

                    String subject_name = MyApplication.dbo.getSubjjjnameWithMultilpe(Subject_Id);
                    Log.d("selected_subj-", "subject_name--" + subject_name);

                    if(((MyApplication) getApplication()).isNetworkAvailable()){

                        AsyncSendSMS asyncSendSMS = new AsyncSendSMS(absent_ids.get(k),
                              "Dear " + stud_names.get(k) +",\n "+ "You were absent on " +date4+" for the "+ subject_name + " session.");
                        asyncSendSMS.execute();
                    }

                    /*SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+91" + absent_ids.get(k), null, getResources().getString(R.string.dear) + stud_names.get(k) + getResources().getString(R.string.you_are_absent) + subject_name + getResources().getString(R.string.session), null, null);*/
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.fail_send_sms), Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
            Toast.makeText(context, getResources().getString(R.string.success_send_sms) + " to " + len3 + " students", Toast.LENGTH_LONG).show();


        } else if (smsFlag.equalsIgnoreCase("ps")) {

            for (int k = 0; k < len3; k++) {
                try {

                    Log.d("mobile no-", "absent_student_parent--" + absent_ids_parent.get(k));
                    Log.d("selected_subj-", "selected_subj--" + selected_subj_value );

                    String subject_name = MyApplication.dbo.getSubjjjnameWithMultilpe(Subject_Id);
                    Log.d("selected_subj-", "subject_name--" + subject_name);

                    if(((MyApplication) getApplication()).isNetworkAvailable()){

                       // AsyncSendSMS asyncSendSMS = new AsyncSendSMS(absent_ids_parent.get(k),getResources().getString(R.string.dear) + " " + stud_names.get(k) + getResources().getString(R.string.you_are_absent) + " " + subject_name + " " + getResources().getString(R.string.session));
                       // asyncSendSMS.execute();
                        AsyncSendSMS asyncSendSMS = new AsyncSendSMS(absent_ids_parent.get(k),
                                "Dear parents,\n "+ stud_names.get(k)+" was absent on "+date4 +" for the "+ subject_name+" session.");
                        asyncSendSMS.execute();

                    }
                    /*SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+91" + absent_ids_parent.get(k), null, getResources().getString(R.string.dear) + stud_names.get(k) + getResources().getString(R.string.you_are_absent) + subject_name + getResources().getString(R.string.session), null, null);*/

                    Log.d("mobile no-", "absent_student--" + absent_ids.get(k));
                    Log.d("selected_subj-", "selected_subj--" + selected_subj_value);

                    String subject_name1 = MyApplication.dbo.getSubjjjnameWithMultilpe(Subject_Id);
                    Log.d("selected_subj-", "subject_name--" + subject_name1);

                    if(((MyApplication) getApplication()).isNetworkAvailable()){
                      //  AsyncSendSMS asyncSendSMS = new AsyncSendSMS(absent_ids.get(k),getResources().getString(R.string.dear) + " " + stud_names.get(k) + getResources().getString(R.string.you_are_absent) + " " + subject_name + " " + getResources().getString(R.string.session));
                     //   asyncSendSMS.execute();
                        AsyncSendSMS asyncSendSMS = new AsyncSendSMS(absent_ids.get(k),
                                "Dear " + stud_names.get(k) +",\n "+ "You were absent on " +date4+" for the "+ subject_name + " session.");
                        asyncSendSMS.execute();
                    }
                    /*SmsManager smsManager1 = SmsManager.getDefault();
                    smsManager1.sendTextMessage("+91" + absent_ids.get(k), null, getResources().getString(R.string.dear) + stud_names.get(k) + getResources().getString(R.string.you_are_absent) + subject_name + getResources().getString(R.string.session), null, null);*/

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.fail_send_sms), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
            Toast.makeText(context, getResources().getString(R.string.success_send_sms) + " to " + len3 + " students and parents", Toast.LENGTH_LONG).show();
        }







    }

    private void requestSMSPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            Snackbar.make(txt_activity_header, "FeeTracker app need permission to send SMS.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(ActivityAttendance.this,
                            new String[]{Manifest.permission.SEND_SMS},
                            PERMISSION_REQUEST_SMS);
                }
            }).show();

        } else {
            Snackbar.make(txt_activity_header, "Permission is not available. Requesting sms permission.", Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_REQUEST_SMS);
        }
    }


    public void showdialog_SMS(final int len) {

        final Dialog dialog1 = new Dialog(context);

        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_send_sms);
        final TextView msg = (TextView) dialog1.findViewById(R.id.msg);
        //msg.setText("Do you want to send the text message to the " + len + " students? ");
        msg.setText("Send text message to absent students.Take Backup to view Attendance Records in Web based Admin Panel.");

        final Button btn_ok = (Button) dialog1.findViewById(R.id.btnOk);
        final Button btn_Cancel = (Button) dialog1.findViewById(R.id.btnCancel);
        final CheckBox ch_parent = (CheckBox) dialog1.findViewById(R.id.ch_parent);
        final CheckBox ch_student = (CheckBox) dialog1.findViewById(R.id.ch_student);
        //NAMRATA CHANGE
//        ch_parent.setChecked(false);
//        ch_student.setChecked(false);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sms_flag = "";

                if (ch_parent.isChecked() && ch_student.isChecked()) {

                    sms_flag = "ps";
                }
                else if (ch_parent.isChecked()) {

                    sms_flag = "p";

                } else if (ch_student.isChecked()) {

                    sms_flag = "s";

                }

                String name = "";
                ;
                int records = menu_list.size();
                int c = ids.size();
                Log.i("TAG", "ids_-->" + ids);

                if (selected_subj_value.length() == 0)
                    selected_subj_value = "";


                for (int j = 0; j < c; j++) {
                    stud_id = ids.get(j);
                    name = MyApplication.dbo.getStudentName(stud_id);

                    String toggle_flag_2 = "P";

                    log("************", stud_id + " " + name);


                    String rowId = MyApplication.dbo.insertAbsentStudentRegister(MyApplication.get_session("classid"), stud_id, name, date4, selected_branch_value, selected_stand_value, selected_batch_value, Subject_Id, toggle_flag_2, selected_subj_value);

                }
                log("absent student list-", absent_ids + "");

//                                send_sms(absent_ids);
//                                Toast.makeText(context,getResources().getString(R.string.send_attendance) , Toast.LENGTH_LONG).show();


             //   if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //This is marshmellow and above version code
                 //   if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        //application has permission
                        Log.d("if ", "absent_student" + absent_ids);

                        send_sms(absent_ids, absent_ids_parent, stud_names, sms_flag);
//                                        absent_ids.clear();

                   /* } else {
                        //application has not permission.Request for permission
                        requestSMSPermission();
                    }*/
               /* } else {
                    //This is below marshmellow version code

                    Log.d("else ", "absent_student" + absent_ids);
                    send_sms(absent_ids, absent_ids_parent, stud_names, sms_flag);
                   // Toast.makeText(context, getResources().getString(R.string.success_send_sms), Toast.LENGTH_LONG).show();

//                                            absent_ids.clear();

                }*/

                dialog1.dismiss();


            }
        });
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        dialog1.show();
        dialog1.setCancelable(true);
        dialog1.setCanceledOnTouchOutside(false);

    }


    class AsyncSendSMS extends AsyncTask<String, String, String> {

        String Response = "";
        Dialog dialog1 = null;
        String mobileno = "",sms_text = "";

        int getstate = 0;

        public AsyncSendSMS(String mobileno, String sms_text) {

            this.mobileno = mobileno;
            this.sms_text = sms_text;

        }

        protected void onPreExecute() {
            Response = "";
            /*dialog1 = ProgressDialog.show(ActivitySMS.this, "Sending SMS",
                    "", true, false);*/
        }


        protected String doInBackground(String... params) {


            MyApplication.log("SMS","mobileno is: "+mobileno);
            MyApplication.log("SMS","sms text is: "+sms_text);
            Response = MyApplication.post_send_sms(this.mobileno,this.sms_text);
            // publishProgress("");
            return "";

        }

        protected void onProgressUpdate(String... progress) {


            // dialog1.dismiss();

        }
    }

}
