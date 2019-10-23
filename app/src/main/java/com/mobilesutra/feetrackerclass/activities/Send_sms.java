package com.mobilesutra.feetrackerclass.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesutra.feetrackerclass.ErrorHandler.ExceptionHandler;
import com.mobilesutra.feetrackerclass.ListModel;
import com.mobilesutra.feetrackerclass.MyApplication;
import com.mobilesutra.feetrackerclass.R;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Send_sms extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener, ActivityCompat.OnRequestPermissionsResultCallback {

    Spinner spinner_branch = null, spinner_stand = null, spinner_batch = null;
    Button submit = null;
    EditText notice = null;
    TextView charcount = null;
    Context context = this;
    MultiSelectionSpinner multiSelectionSpinner = null;
    String selected_branch_value = "", selected_stand_value = "", selected_batch_value = "", id = "", rowid = "", edit_batch, edit_stand;
    int PERMISSION_REQUEST_SMS = 1;
    List<String> SelectedSubj = new ArrayList<>();

    String[] array2;


    LinkedHashMap<String, String> lhm_branch = new LinkedHashMap<String, String>();// Branch
    LinkedHashMap<String, String> lhm_std = new LinkedHashMap<String, String>();// Std
    LinkedHashMap<String, String> lhm_batch = new LinkedHashMap<String, String>();// Batch
    LinkedHashMap<String, String> lhm_sub = new LinkedHashMap<String, String>();// Subjects
    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    final ArrayList<String> ids = new ArrayList<String>();
    final ArrayList<String> parent_ids = new ArrayList<String>();

    ArrayList<String> arrstand1;
    ArrayList<String> arrbatch;

    RadioButton sendselective;
    int smsflag = 0;
    Typeface roboto_reguler, roboto_light;
    int textlength, msgcount = 1;

    ArrayList<ListModel> rowItems1 = new ArrayList<ListModel>();
    List<ListModel> arrayDTO = null;
    RecyclerView recycler_view = null;
    RecyclerAdapter recyclerAdapter = null;
    Boolean flag_data = true;
    TextView txt_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_send_sms);

        getIntentData();
        initComponentData();
        initSpinnerData();
        initComponentListeners();

    }

    public void getIntentData() {

    }

    public void initComponentData() {
        roboto_light = Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
        roboto_reguler = Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");

        TextView heading = (TextView) findViewById(R.id.txt_user_heading);
        heading.setText("Send SMS");
        heading.setTypeface(roboto_reguler);
        spinner_branch = (Spinner) findViewById(R.id.spinner_branch);
        spinner_stand = (Spinner) findViewById(R.id.spinner_stand);
        spinner_batch = (Spinner) findViewById(R.id.spinner_batch);
        multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.spinner_subj);
        charcount = (TextView) findViewById(R.id.noticecharcount);
        notice = (EditText) findViewById(R.id.notice);


        txt_user = (TextView) findViewById(R.id.txt_user);
        txt_user.setText(MyApplication.get_session("classname"));
        txt_user.setTypeface(roboto_reguler);
        submit = (Button) findViewById(R.id.insert);
        submit.setTypeface(roboto_reguler);
        notice.setTypeface(roboto_reguler);
        charcount.setTypeface(roboto_reguler);
        recycler_view = (RecyclerView) findViewById(R.id.list);
        arrayDTO = new ArrayList<ListModel>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Send_sms.this);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setHasFixedSize(true);
    }

    public void initComponentListeners() {
        MyApplication.selecteddate = new SimpleDateFormat(
                "yyyy-MM-dd").format(new Date());

        notice.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                notice.addTextChangedListener(new TextWatcher() {

                    public void afterTextChanged(Editable s) {

                    }

                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {

                    }

                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        textlength = notice.getText().length();
                        float len = textlength;
                        float ms = 0;
                        if (textlength > 160) {
                            ms = len / 160;

                            msgcount = (int) Math.ceil(ms);

                            // msgcount=(textlength/50);

                        } else {
                            msgcount = 1;
                        }
                        charcount.setText(getResources().getString(R.string.char_countt) + textlength
                                + getResources().getString(R.string.message_count) + msgcount);
                        msgcount = msgcount;

                    }
                });
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int len = ids.size();
                Log.d("TAG", "checked student count is: " + len);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("")
                        .setMessage("Do you want to send the text message to the " + len + " students of " + MyApplication.dbo.getBranchName(selected_branch_value) + " branch, " + MyApplication.dbo.getStandardName(selected_stand_value) + " standard, " + MyApplication.dbo.getbatchhhName(selected_batch_value) + " batch and  " + MyApplication.dbo.getSubjjjnameWithMultilpe(id) + " subjects? ")//getResources().getString(R.string.dialog_send_sms)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("confirmation", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Yes button clicked, do something

                                //  Toast.makeText(context,"ids"+ids+"", Toast.LENGTH_LONG).show();
//                showdialog_notes(ids);


                                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    //This is marshmellow and above version code
                                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                                        //application has permission
                                        if (notice.getText().toString().equals("")) {
                                            Toast.makeText(context, getResources().getString(R.string.valid_notice), Toast.LENGTH_LONG).show();
                                        } else {
                                            if (ids.size() != 0) {
                                                send_sms(ids);

                                                int len = ids.size();
                                                Log.d("TAG", "checked student count is: " + len);
                                                SendTextMessageDialog(getResources().getString(R.string.success_send_sms_to) + " " + len + " " + getResources().getString(R.string.students));
                                                //Toast.makeText(context, getResources().getString(R.string.success_send_sms_to) + " " + len + " " + getResources().getString(R.string.students), Toast.LENGTH_LONG).show();
                                            } else
                                                Toast.makeText(context, getResources().getString(R.string.valid_select_stud), Toast.LENGTH_LONG).show();

                                        }
                                    } else {
                                        //application has not permission.Request for permission
                                        requestSMSPermission();
                                    }
                                } else {
                                    //This is below marshmellow version code
                                    if (notice.getText().toString().equals("")) {
                                        Toast.makeText(context, getResources().getString(R.string.valid_notice), Toast.LENGTH_LONG).show();
                                    } else {
                                        if (ids.size() != 0) {
                                            send_sms(ids);

                                            int len = ids.size();
                                            Log.d("TAG", "checked student count is: " + len);
                                            //Toast.makeText(context, getResources().getString(R.string.success_send_sms_to) + " " + len + " " + getResources().getString(R.string.students), Toast.LENGTH_LONG).show();
                                            SendTextMessageDialog(getResources().getString(R.string.success_send_sms_to) + " " + len + " " + getResources().getString(R.string.students));

                                        } else

                                            Toast.makeText(context, getResources().getString(R.string.valid_select_stud), Toast.LENGTH_LONG).show();

                                    }
                                }


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
//        public void bindComponentData()
//        {
//
//         data=MyApplication.dbo.getstudentlist2(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value, id);
//         rowItems1 = new ArrayList<ListModel>();
//
//         int records=data.size();
//         for(int i=0;i<records;i++)
//         {
//
//              ListModel item=new ListModel(data.get(i).get("rollno"),data.get(i).get("auto_id"),data.get(i).get("sname"),"0",data.get(i).get("phno"),"0","","","","");//f.toString()
//
//        rowItems1.add(item);
//
//      }
//
//    recyclerAdapter = new RecyclerAdapter(rowItems1);
//    recycler_view.setAdapter(recyclerAdapter);
//}


    public void OnViaSelectiveClick(View v) {
        sendselective = (RadioButton) v;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SMS Sending Option!")
                .setMessage("You have chosen send selective SMS option..")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // Yes button clicked, do something
                    }
                }) // Do nothing on no
                .show();
        smsflag = 3;
    }


    public void initSpinnerData() {


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
            spinner_branch.setAdapter(adapter0);


            spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //select branch value
                    selected_branch_value = lhm_branch.get(spinner_branch.getSelectedItem().toString());
                    MyApplication.set_session(MyApplication.send_session_branch, selected_branch_value);
//                Toast.makeText(context, "selected_stand_value"+selected_branch_value,
//                        Toast.LENGTH_SHORT).show();
//                    bindComponentData();
                    new HTTPDATA().execute();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            int count1 = lhm_branch.size();
            if (count1 != 0) {
                selected_branch_value = lhm_branch.get(spinner_branch.getSelectedItem().toString());
                if (MyApplication.get_session(MyApplication.send_session_branch).equals(""))
                    MyApplication.set_session(MyApplication.send_session_branch, selected_branch_value);

            }


            if (flag_data) {

                String branchname = MyApplication.dbo.getBranchName(MyApplication.get_session(MyApplication.send_session_branch));
                int position = arrBranch.indexOf(branchname);
//                Toast.makeText(context,"value "+MyApplication.get_session(MyApplication.session_branch)+position,Toast.LENGTH_SHORT).show();
                spinner_branch.setSelection(position, true);
                selected_branch_value = lhm_branch.get(spinner_branch.getSelectedItem().toString());
                MyApplication.set_session(MyApplication.send_session_branch, selected_branch_value);

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
                    MyApplication.set_session(MyApplication.send_session_stand, selected_stand_value);//**
//                Toast.makeText(context, "selected_stand_value"+selected_stand_value,
//                        Toast.LENGTH_SHORT).show();

                    ;
//                    bindComponentData();
                    new HTTPDATA().execute();


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });


            int count2 = lhm_std.size();
            if (count2 != 0) {
                selected_stand_value = lhm_std.get(spinner_stand.getSelectedItem().toString());
                if (MyApplication.get_session(MyApplication.send_session_stand).equals(""))
                    MyApplication.set_session(MyApplication.send_session_stand, selected_stand_value);//**
            }

            if (flag_data) {

                String standname = MyApplication.dbo.getStandardName(MyApplication.get_session(MyApplication.send_session_stand));
                int position = arrstand1.indexOf(standname);
//                Toast.makeText(context,"value "+MyApplication.get_session(MyApplication.session_branch)+position,Toast.LENGTH_SHORT).show();
                spinner_stand.setSelection(position, true);
                selected_stand_value = lhm_std.get(spinner_stand.getSelectedItem().toString());
                MyApplication.set_session(MyApplication.send_session_stand, selected_stand_value);

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
                    MyApplication.set_session(MyApplication.send_session_batch, selected_batch_value);//**
//                Toast.makeText(context,"value_batch"+selected_batch_value,Toast.LENGTH_SHORT).show();;
//                    bindComponentData();
                    new HTTPDATA().execute();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }

            });

            int count3 = lhm_batch.size();
            if (count3 != 0) {
                selected_batch_value = lhm_batch.get(spinner_batch.getSelectedItem().toString());
                if (MyApplication.get_session(MyApplication.send_session_batch).equals(""))
                    MyApplication.set_session(MyApplication.send_session_batch, selected_batch_value);//**

            }

            if (flag_data) {

                String batchname = MyApplication.dbo.getbatchhhName(MyApplication.get_session(MyApplication.send_session_batch));
                int position = arrbatch.indexOf(batchname);
//                Toast.makeText(context,"value "+MyApplication.get_session(MyApplication.session_branch)+position,Toast.LENGTH_SHORT).show();
                spinner_batch.setSelection(position, true);
                selected_batch_value = lhm_batch.get(spinner_batch.getSelectedItem().toString());
                MyApplication.set_session(MyApplication.send_session_batch, selected_batch_value);

            }
            lhm_sub = MyApplication.dbo.getSubjectWithInsertFee(MyApplication.get_session("classid"));
            // lhm_sub = MyApplication.dbo.getSubject(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value);
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


    public void set(String[] array2, int[] pos) {
        multiSelectionSpinner.setItems(array2);
        multiSelectionSpinner.setSelection(pos);
        multiSelectionSpinner.setListener(this);
        multiSelectionSpinner.set_selection();

    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        List<ListModel> rowItems;


        public RecyclerAdapter(List<ListModel> rowItems) {
            this.rowItems = rowItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            RecyclerView.ViewHolder vh;
            final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_list_row, viewGroup, false);
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
            ((MyViewHolder) holder).studid.setText(row_pos.getAuto_id());
            ((MyViewHolder) holder).name.setText(row_pos.getName());
            ((MyViewHolder) holder).check.setTag(position);
            log("row_pos.getId()", row_pos.getStudphone());

            if (row_pos.isSmsFlag()) {
                ((MyViewHolder) holder).check.setChecked(true);
            } else {
                ((MyViewHolder) holder).check.setChecked(false);
            }


            ((MyViewHolder) holder).check.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    int pos = (int) ((CheckBox) v).getTag();
                    ListModel row_pos = rowItems.get(pos);

                    if (cb.isChecked()) {


                        row_pos.setSmsFlag(true);
                        notifyItemChanged(pos);


                        String id = row_pos.getStudphone().toString();
                        String id1 = row_pos.getPhone1().toString();


                        MyApplication.log("SendSMS","stud phone : "+ id);
                        MyApplication.log("SendSMS","parent phone : "+ id1);
                        log("check_id_if", id);


                        ids.add(id);
                        parent_ids.add(id1);

                    } else {

                        row_pos.setSmsFlag(false);
                        notifyItemChanged(pos);
                        String id = (row_pos.getStudphone().toString());
                        String id1 = row_pos.getPhone1().toString();
                        log("uncheck_id_if", id);

                        MyApplication.log("SendSMS","stud phone : "+ id);
                        MyApplication.log("SendSMS", "parent phone : " + id1);

                        if (ids.contains(id)) {
                            ids.remove(ids.indexOf(id));
                        }

                        if(parent_ids.contains(id1)){

                            parent_ids.remove(parent_ids.indexOf(id1));
                        }
                    }
                    log("check_id_if" + ids);

                }
            });

        }

        @Override
        public int getItemCount() {
            return rowItems == null ? 0 : rowItems.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView studid;
            TextView name;
            TextView ispresent;
            CheckBox check;


            public MyViewHolder(View v) {
                super(v);

                studid = (TextView) v.findViewById(R.id.StudID1);
                name = (TextView) v.findViewById(R.id.name1);

                ispresent = (TextView) v.findViewById(R.id.ispresent);
                check = (CheckBox) v.findViewById(R.id.checkBox1);

                studid.setTypeface(roboto_reguler);
                name.setTypeface(roboto_reguler);

            }


        }


    }

    public static void log(String str) {
        if (str.length() > 4000) {
            Log.i("addstudent", str.substring(0, 4000));
            log(str.substring(4000));
        } else
            Log.i("addstudent", str);
    }

    public static void log(String LOG_TAG, String str) {
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
        id = "";
        SelectedSubj = strings;
        int count = SelectedSubj.size();

        for (int j = 0; j < count; j++) {

            String checkedsubj = lhm_sub.get(SelectedSubj.get(j));

            if (id.equals("")) {
                id = checkedsubj;

            } else {
                id = id + "," + checkedsubj;

            }


        }

        log("chek_subjectid", id);
//        bindComponentData();

        new HTTPDATA().execute();
//        Toast.makeText(context, strings.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(Send_sms.this, ActivityDashboard.class);
        startActivity(i);
        finish();
    }


    private void send_sms(ArrayList<String> ids) {
        int len3 = ids.size();
        for (int k = 0; k < len3; k++) {
            try {

                /*if (msgcount == 1) {

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+91" + ids.get(k), null, notice.getText().toString(), null, null);
                } else {*/

                    SmsManager smsManager = SmsManager.getDefault();
                    ArrayList<String> parts = smsManager.divideMessage(notice.getText().toString());
                    smsManager.sendMultipartTextMessage("+91" + ids.get(k), null, parts, null, null);

              //  }
            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.fail_send_sms), Toast.LENGTH_LONG).show();
                e.printStackTrace();

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == PERMISSION_REQUEST_SMS) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                Snackbar.make(txt_user, "SMS permission was granted.",
                        Snackbar.LENGTH_SHORT)
                        .show();
//                if(ids.size()!=0)
//                    send_sms(ids);
//                else
//                    Toast.makeText(context, "Please select student", Toast.LENGTH_LONG).show();
            } else {
                // Permission request was denied.
                Snackbar.make(txt_user, "SMS permission request was denied.",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
        // END_INCLUDE(onRequestPermissionsResult)
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
                    ActivityCompat.requestPermissions(Send_sms.this,
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


    public void SendTextMessageDialog(String text) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("")
                .setMessage(text)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(Send_sms.this, ActivityDashboard.class);
                        startActivity(i);
                        finish();
                        ;

                    }
                })

                .show();
    }


    public void ErrorDialog(String text) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder

                .setMessage(text)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(context, Activity_profileNew.class);
                        i.putExtra("dialog_tab", "1");
                        startActivity(i);
                        finish();
                        ;

                    }
                })

                .show();
    }


    class HTTPDATA extends AsyncTask<Void, String, String> {


        protected void onPreExecute() {

//            dialog1 = ProgressDialog.show(Send_sms.this, "Submitting  Form....",
//                    "Please Wait", true, false);
        }

        protected String doInBackground(Void... params) {


            data = MyApplication.dbo.getstudentlist2(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value, id);
            rowItems1 = new ArrayList<ListModel>();

            int records = data.size();
            for (int i = 0; i < records; i++) {

                ListModel item = new ListModel(data.get(i).get("rollno"), data.get(i).get("auto_id"), data.get(i).get("sname"), "0", data.get(i).get("phno"), "0", "", "", "", "");//f.toString()

                rowItems1.add(item);

            }

            publishProgress("progress");
            return "";
        }

        protected void onProgressUpdate(String... progress) {

            recyclerAdapter = new RecyclerAdapter(rowItems1);
            recycler_view.setAdapter(recyclerAdapter);

        }

        protected void onPostExecute(Long result) {

        }
    }// HTTPRequest


}