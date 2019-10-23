package com.mobilesutra.feetrackerclass.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesutra.feetrackerclass.ErrorHandler.ExceptionHandler;
import com.mobilesutra.feetrackerclass.FAB.FloatingActionButton;
import com.mobilesutra.feetrackerclass.ListModel;
import com.mobilesutra.feetrackerclass.MyApplication;
import com.mobilesutra.feetrackerclass.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Add_attendance extends ActionBarActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {
    Date myDate = null;
    Spinner spinner_branch = null, spinner_stand = null, spinner_batch = null, spinner_subj = null;
    Button submit = null;
    FloatingActionButton student_clearall_button;
    EditText studname = null, parent_ph = null, stud_ph = null, address = null;
    TextView birthdate = null, head_switch = null;
    Context context = this;
    MultiSelectionSpinner multiSelectionSpinner;
    int PERMISSION_REQUEST_SMS = 1;
    String selected_branch_value = "", selected_stand_value = "", selected_batch_value = "", selected_subj_value = "", date4 = "", edit_batch, edit_stand;
    Boolean allchecked = true;
    List<String> SelectedSubj = new ArrayList<>();
    List<String> array1;
    int result1 = 0;
    int checkedid = 0;
    String[] array2;
    ProgressDialog dialog1;
    Boolean flag1 = false;
    String flag = "True";
    LinkedHashMap<String, String> lhm_branch = new LinkedHashMap<String, String>();// Branch
    LinkedHashMap<String, String> lhm_std = new LinkedHashMap<String, String>();// Std
    LinkedHashMap<String, String> lhm_batch = new LinkedHashMap<String, String>();// Batch
    LinkedHashMap<String, String> lhm_sub = new LinkedHashMap<String, String>();// Subjects
    LinkedHashMap<String, String> lhm_sub2 = new LinkedHashMap<String, String>();// Subjects
    ArrayList<HashMap<String, String>> menu_list;
    ArrayList<String> arrstand1;
    ArrayList<String> arrbatch;
    LinkedHashMap<String, String> lhm_checkedSub = new LinkedHashMap<String, String>();// Subjects
    Typeface roboto_light, roboto_reguler;
    ListView list;
    String feeflag, sid;
    //    Switch togglebtn;
    TextView txt_user = null;
    ArrayList<ListModel> rowItems1 = new ArrayList<ListModel>();
    final ArrayList<String> ids = new ArrayList<String>();
    final ArrayList<String> absent_ids = new ArrayList<String>();
    final ArrayList<String> stud_names = new ArrayList<String>();

    final ArrayList<String> names = new ArrayList<String>();
    String toggle_flag = "P";
    Boolean flag_data = true;
    String Subject_Id;
    Button send, SendWothoutsms;

    List<ListModel> rowItems;

    LinearLayout l1 = null;
    String stud_id = "";
    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

    List<ListModel> arrayDTO = null;
    RecyclerView recycler_view = null;
    RecyclerAdapter recyclerAdapter = null;

    ArrayList<HashMap<String, String>> imcompletefees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_add_attendance);
        getIntentData();
        initComponentData();
        initSpinnerData();
        bindComponentData();
        initSpinnerListener();
    }


    public void getIntentData() {

    }

    public void initComponentData() {

        roboto_light = Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
        roboto_reguler = Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");
        birthdate = (TextView) findViewById(R.id.student_dateofbirth);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.switch_linear);
        linearLayout.setVisibility(View.VISIBLE);
        txt_user = (TextView) findViewById(R.id.txt_user);
        txt_user.setText(MyApplication.get_session("classname"));
        txt_user.setTypeface(roboto_reguler);
        TextView heading = (TextView) findViewById(R.id.txt_user_heading);
        heading.setText("Attendance");

        heading.setTypeface(roboto_reguler);
        head_switch = (TextView) findViewById(R.id.head_switch);
        head_switch.setVisibility(View.VISIBLE);
        student_clearall_button = (FloatingActionButton) findViewById(R.id.student_clearall_button);
        spinner_branch = (Spinner) findViewById(R.id.spinner_branch);
        spinner_stand = (Spinner) findViewById(R.id.spinner_stand);
        spinner_batch = (Spinner) findViewById(R.id.spinner_batch);
        spinner_subj = (Spinner) findViewById(R.id.spinner_subjjj);
        submit = (Button) findViewById(R.id.btn_submit);
        multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.spinner_subj);
        l1 = (LinearLayout) findViewById(R.id.subj_att);
        send = (Button) findViewById(R.id.Send);
        SendWothoutsms = (Button) findViewById(R.id.SendWothoutsms);
        list = (ListView) findViewById(R.id.list);

        send.setTypeface(roboto_reguler);
        SendWothoutsms.setTypeface(roboto_reguler);
        CheckBox checkBox = (CheckBox) findViewById(R.id.selectPresent);
        checkBox.setVisibility(View.VISIBLE);
//        togglebtn=(Switch)findViewById(R.id.selectPresent);
//        togglebtn.setVisibility(View.VISIBLE);
//        togglebtn.setChecked(false);

        recycler_view = (RecyclerView) findViewById(R.id.listview);
        arrayDTO = new ArrayList<ListModel>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Add_attendance.this);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setHasFixedSize(true);
    }

    public void initSpinnerData() {


        Date d = new Date();
        CharSequence s = DateFormat.format("dd" + "/" + "MM" + "/" + "yyyy", d.getTime());
        birthdate.setText(s.toString());


        //add spinner _branch details
        lhm_branch = MyApplication.dbo.getBranchWithActiveFlag(MyApplication.get_session("classid"));
        if (lhm_branch.size() == 0) {

            ErrorDialog(getResources().getString(R.string.empty_data_error_msg));
        } else {
            log(lhm_branch + "");
            ArrayList<String> arrBranch = new ArrayList<String>();

            for (Object o : lhm_branch.keySet()) {
                arrBranch.add(o.toString());
                System.out.println("key:" + o.toString() + "___" + "value:" + lhm_branch.get(o).toString());
            }
            ArrayAdapter<String> adapter0 = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arrBranch);
            adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_branch.setAdapter(adapter0);


            spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //select branch value
                    selected_branch_value = lhm_branch.get(spinner_branch.getSelectedItem().toString());
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
                selected_branch_value = lhm_branch.get(spinner_branch.getSelectedItem().toString());
                if (MyApplication.get_session(MyApplication.add_session_branch).equals(""))
                    MyApplication.set_session(MyApplication.add_session_branch, selected_branch_value);

            }


            if (flag_data) {

                String branchname = MyApplication.dbo.getBranchName(MyApplication.get_session(MyApplication.add_session_branch));
                int position = arrBranch.indexOf(branchname);
//                Toast.makeText(context,"value "+MyApplication.get_session(MyApplication.session_branch)+position,Toast.LENGTH_SHORT).show();
                spinner_branch.setSelection(position, true);
                selected_branch_value = lhm_branch.get(spinner_branch.getSelectedItem().toString());
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
            spinner_stand.setAdapter(adapter);


            spinner_stand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    selected_stand_value = lhm_std.get(spinner_stand.getSelectedItem().toString());
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
                selected_stand_value = lhm_std.get(spinner_stand.getSelectedItem().toString());
                if (MyApplication.get_session(MyApplication.add_session_stand).equals(""))
                    MyApplication.set_session(MyApplication.add_session_stand, selected_stand_value);//**
            }

            if (flag_data) {

                String standname = MyApplication.dbo.getStandardName(MyApplication.get_session(MyApplication.add_session_stand));
                int position = arrstand1.indexOf(standname);
//                Toast.makeText(context,"value "+MyApplication.get_session(MyApplication.session_branch)+position,Toast.LENGTH_SHORT).show();
                spinner_stand.setSelection(position, true);
                selected_stand_value = lhm_std.get(spinner_stand.getSelectedItem().toString());
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
            spinner_batch.setAdapter(adapterb);


            spinner_batch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selected_batch_value = lhm_batch.get(spinner_batch.getSelectedItem().toString());
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
                selected_batch_value = lhm_batch.get(spinner_batch.getSelectedItem().toString());
                if (MyApplication.get_session(MyApplication.add_session_batch).equals(""))
                    MyApplication.set_session(MyApplication.add_session_batch, selected_batch_value);//**

            }

            if (flag_data) {

                String batchname = MyApplication.dbo.getbatchhhName(MyApplication.get_session(MyApplication.add_session_batch));
                int position = arrbatch.indexOf(batchname);
//                Toast.makeText(context,"value "+MyApplication.get_session(MyApplication.session_branch)+position,Toast.LENGTH_SHORT).show();
                spinner_batch.setSelection(position, true);


                selected_batch_value = lhm_batch.get(spinner_batch.getSelectedItem().toString());
                MyApplication.set_session(MyApplication.add_session_batch, selected_batch_value);

            }

            //  lhm_sub = MyApplication.dbo.getSubject(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value);
            lhm_sub = MyApplication.dbo.getSubjectWithInsertFee(MyApplication.get_session("classid"));

            Log.d("tag", "lhm_sub2" + lhm_sub);
            List<String> array1_subj = new ArrayList<>();
            for (Object o : lhm_sub.keySet()) {
                array1_subj.add(o.toString());
                System.out.println("subject-key:" + o.toString() + "___" + "value:" + lhm_sub.get(o).toString());
            }
            Log.d("tag", "array1" + array1_subj);
            int[] numbers = {0};
            array2 = array1_subj.toArray(new String[array1_subj.size()]);

            if (array2.length != 0)
                set(array2, numbers);

            if (lhm_sub.size() != 0) {


            }


        }
    }

    public void initSpinnerListener() {


//        togglebtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (togglebtn.isChecked()) {
//                    toggle_flag = "A";
//                    head_switch.setText("Absent");
//                    togglebtn.setTextOn("P");
//                    Toast.makeText(context, "A-" + toggle_flag, Toast.LENGTH_SHORT).show();
//                } else {
//                    toggle_flag = "P";
//                    head_switch.setText("Present");
//                    togglebtn.setTextOff("A");
//                    Toast.makeText(context, "P-" + toggle_flag, Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

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
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // TODO Auto-generated method stub
                // To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(Add_attendance.this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker,
                                                  int selectedyear, int selectedmonth,
                                                  int selectedday) {
                                // TODO Auto-generated method stub
                                /* Your code to get date and time */
                                selectedmonth = selectedmonth + 1;
                                birthdate.setText("" + selectedday + "/"
                                        + selectedmonth + "/" + selectedyear);
                                bindComponentData();
                            }
                        }, mYear, mMonth, mDay);


                mDatePicker.setTitle("Select Date");
                mDatePicker.show();


            }
        });
        String check = birthdate.getText().toString();
        date4 = ((MyApplication) getApplication()).changedateformat(check);
        Log.d("tag", "tejas-" + date4);
//        try {
//            myDate = dateformat.parse(check);
//            Log.d("tag", "tejas-" + myDate);
//            date4 = dateformat.format(myDate);
//            Log.d("tag", "tejas-" + date4);
//        } catch (Exception e) {
//            Log.d("tag", "tejas-" + e.getMessage());
//        }
        Log.d("tag", "tejas-" + date4);


        SendWothoutsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ;
                int records = menu_list.size();
                int c = ids.size();
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

//                          ((MyApplication) getApplication()).dbo.insertStudentAttendance(MyApplication.get_session("classid"), branch, std, batch, sub, ids, names, date); //MyApplication.get_session("br"), MyApplication.get_session("st"), MyApplication.get_session("ba"), MyApplication.get_session("su")
                    String rowId = MyApplication.dbo.insertAbsentStudentRegister(MyApplication.get_session("classid"), stud_id, name, date4, selected_branch_value, selected_stand_value, selected_batch_value, Subject_Id, toggle_flag_2, selected_subj_value);

                }
                Toast.makeText(context, getResources().getString(R.string.send_attendance_without_sms), Toast.LENGTH_LONG).show();
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
                stud_names.clear();

                int leni=rowItems.size();
                for(int i=0;i<leni;i++)
                {
                    String value=rowItems.get(i).getPresentFlag();
                    String value2=rowItems.get(i).getPhone1();
                    String sname=rowItems.get(i).getName();
                    Log.i("###", "add-" + value);
                    if(value.equals("N"))
                    {
                        Log.i("###", "add-" + value2);
                        absent_ids.add(value2);
                        stud_names.add(sname);
                    }

                }

                Log.d("Absent_ids","absent_ids"+absent_ids);
                Log.d("stud_names","stud_names"+stud_names);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                                send_sms(absent_ids,stud_names);
                                Toast.makeText(context, getResources().getString(R.string.success_send_sms), Toast.LENGTH_LONG).show();


                                //         Toast.makeText(context,getResources().getString(R.string.send_attendance) , Toast.LENGTH_LONG).show();


                            /*    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    //This is marshmellow and above version code
                                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                                        //application has permission
                                        Log.d("if ", "absent_student" + absent_ids);

                                        send_sms(absent_ids,stud_names);
                                        Toast.makeText(context, getResources().getString(R.string.success_send_sms), Toast.LENGTH_LONG).show();
//                                        absent_ids.clear();

                                    } else {
                                        //application has not permission.Request for permission
                                        requestSMSPermission();
                                    }
                                } else {
                                    //This is below marshmellow version code

                                    Log.d("else ", "absent_student" + absent_ids);
                                    send_sms(absent_ids,stud_names);
                                    Toast.makeText(context, getResources().getString(R.string.success_send_sms), Toast.LENGTH_LONG).show();

//                                            absent_ids.clear();

                                }


*/
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();


            }
        });

    }

    public void bindComponentData() {

        menu_list = null;
        menu_list = new ArrayList<HashMap<String, String>>();


        if (lhm_sub.size() != 0) {
            String[] array = Subject_Id.split(",");
            Log.i("menu_list", "##" + array.length);
            if (array.length == 1) {

                l1.setVisibility(View.GONE);

            } else {
                l1.setVisibility(View.VISIBLE);


                lhm_sub2 = MyApplication.dbo.getSubject(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value);

                log(lhm_sub2 + "");
                ArrayList<String> arrBranch2 = new ArrayList<String>();

                for (Object o : lhm_sub2.keySet()) {
                    arrBranch2.add(o.toString());
                    System.out.println("key:" + o.toString() + "___" + "value:" + lhm_sub2.get(o).toString());
                }
                ArrayAdapter<String> adapter0 = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arrBranch2);
                adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_subj.setAdapter(adapter0);

            }


            spinner_subj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selected_subj_value = lhm_sub2.get(spinner_subj.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
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


        String check = birthdate.getText().toString();
        date4 = ((MyApplication) getApplication()).changedateformat(check);
        Log.d("tag", "datee" + date4);

        menu_list = ((MyApplication) getApplication()).dbo.getStudentListForAttendance(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value, Subject_Id, date4);//MyApplication.get_session("br"), MyApplication.get_session("st"), MyApplication.get_session("ba"), MyApplication.get_session("su")
        rowItems1 = new ArrayList<ListModel>();
//        ((MyApplication) getApplication()).dbo.getPresentList()
        int records = menu_list.size();
        for (int i = 0; i < records; i++) {

            ListModel item = new ListModel(menu_list.get(i).get("id"),
                    menu_list.get(i).get("auto_id"),
                    menu_list.get(i).get("sname"),
                    menu_list.get(i).get("studentprofile"),
                    menu_list.get(i).get("phoneno"),
                    menu_list.get(i).get(""),
                    menu_list.get(i).get("flags"), "");

            rowItems1.add(item);

        }

        recyclerAdapter = new RecyclerAdapter(rowItems1);
        recycler_view.setAdapter(recyclerAdapter);

    }


    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {





        public RecyclerAdapter(List<ListModel> rowItems2) {
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

            final ListModel row_pos = rowItems.get(position);


            ((MyViewHolder) holder).sname.setText(row_pos.getName());
            ((MyViewHolder) holder).roll.setText(row_pos.getAuto_id());




            String flagp = row_pos.getPresentFlag();
//            Log.i("TAG", "id with balance " + row_pos.getId() + " ---->" + row_pos.getPhone1());


//            int leni= row_pos.getId().length();
            int leni=rowItems.size();
            Log.i("TAG", "------len "+leni);

//            Log.i("TAG", "------len "+absent_ids);
            Log.i("TAG", "------absent_idslen "+absent_ids.size());
            if (row_pos.getPresentFlag().equals("Y")) {
                ((MyViewHolder) holder).check.setChecked(true);

            } else {
                ((MyViewHolder) holder).check.setChecked(false);
    }

            log("absent student list-", absent_ids + "");

            ((MyViewHolder) holder).check.setTag(position);

            imcompletefees = new ArrayList<HashMap<String, String>>();
            imcompletefees = ((MyApplication) getApplication()).dbo.getBalance(row_pos.getId());
            Log.i("TAG", "id with balance 0 ---->" + imcompletefees);

            for (int i = 0; i < imcompletefees.size(); i++) {
                sid = imcompletefees.get(i).get("sid");

                Log.i("TAG", "feeflag ---->" + feeflag);
                Log.i("TAG", "sid ---->" + sid);

                if (row_pos.getId().contains(sid)) {
                    //  holder.sname.setTextColor(Color.parseColor("#8b1919"));
                    ((MyViewHolder) holder).circle.setBackgroundResource(R.drawable.ovalshape);
                }
            }

            //  holder.sname.setTextColor(Color.parseColor("#8b1919"));


            ((MyViewHolder) holder).check.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    int pos = (int) ((CheckBox) v).getTag();
                    ListModel row_pos = rowItems.get(pos);
                    if (cb.isChecked()) {

                        row_pos.setPresentFlag("Y");
                        notifyItemChanged(pos);

                        String id = row_pos.getId().toString();

                        ids.add(row_pos.getId().toString());

                        names.add(row_pos.getName());
                        if (absent_ids.contains(row_pos.getId())) {
                            Log.i("###", "remove-" + row_pos.getId());
                            absent_ids.remove(absent_ids.indexOf(row_pos.getId()));
                        }


                    } else {

                        Log.i("TAG", "In Else");
                        Log.i("TAG", "In Else:cb.ischecked()");
                        String id = row_pos.getId().toString();
                        Log.i("TAG", "In Else:id:" + id);

                        row_pos.setPresentFlag("N");
                        notifyItemChanged(pos);
                        if (ids.contains(id)) {
                            ids.remove(ids.indexOf(id));
                        }
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


            public MyViewHolder(View v) {
                super(v);


                sname = (TextView) v.findViewById(R.id.sname);
                roll = (TextView) v.findViewById(R.id.id);
                ispresent = (TextView) v.findViewById(R.id.ispresent);
                check = (CheckBox) v.findViewById(R.id.checkBox1);
                feeflag = (TextView) v.findViewById(R.id.feeflag);
                circle = (ImageView) v.findViewById(R.id.circle);

                sname.setTypeface(roboto_reguler);
                roll.setTypeface(roboto_reguler);
//            holder.ispresent.setTypeface(roboto_light);
                feeflag.setTypeface(roboto_reguler);
                /************ Set holder with LayoutInflater ************/


            }


        }


    }


    public void set(String[] array2, int[] pos) {
        multiSelectionSpinner.setItems(array2);
        multiSelectionSpinner.setSelection(pos);
        multiSelectionSpinner.setListener(this);
        multiSelectionSpinner.set_selection();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_attendance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    public static void log(String str) {
        if (str.length() > 4000) {
            Log.i("add_attendance", str.substring(0, 4000));
            log(str.substring(4000));
        } else
            Log.i("add_attendance", str);
    }

    public static void log(String LOG_TAG, String str) {
        if (str.length() > 4000) {
            Log.i(LOG_TAG, str.substring(0, 4000));
            log(str.substring(4000));
        } else
            Log.i(LOG_TAG, str);
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(context, ActivityDashboard.class);
        startActivity(i);
        finish();
    }

    public void ErrorDialog(String text) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage(text)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(context, Activity_profileNew.class);
                        i.putExtra("dialog_tab","1");
                        startActivity(i);
                        finish();
                        ;

                    }
                })

                .show();
    }

    private void send_sms(ArrayList<String> absent_ids,ArrayList<String> stud_names) {
        int len3 = absent_ids.size();
        Log.d("if ", "absent_student" + len3);
        for (int k = 0; k < len3; k++) {
            try {
                Log.d("mobile no-", "absent_student--" + absent_ids.get(k));
                Log.d("selected_subj-", "selected_subj--" + selected_subj_value );

                String subject_name = MyApplication.dbo.getSubjjjnameWithMultilpe(Subject_Id);
                Log.d("selected_subj-", "subject_name--" + subject_name);
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("+91" + absent_ids.get(k), null, getResources().getString(R.string.dear) + stud_names.get(k) + getResources().getString(R.string.you_are_absent) + subject_name + getResources().getString(R.string.session), null, null);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.fail_send_sms), Toast.LENGTH_LONG).show();
                e.printStackTrace();

            }
        }


    }

    private void requestSMSPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            Snackbar.make(txt_user, "FeeTracker app need permission to send SMS.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(Add_attendance.this,
                            new String[]{Manifest.permission.SEND_SMS},
                            PERMISSION_REQUEST_SMS);
                }
            }).show();

        } else {
            Snackbar.make(txt_user, "Permission is not available. Requesting sms permission.", Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_REQUEST_SMS);
        }
    }

}
