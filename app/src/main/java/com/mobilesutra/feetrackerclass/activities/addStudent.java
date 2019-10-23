package com.mobilesutra.feetrackerclass.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobilesutra.feetrackerclass.Database;
import com.mobilesutra.feetrackerclass.ErrorHandler.ExceptionHandler;
import com.mobilesutra.feetrackerclass.MyApplication;
import com.mobilesutra.feetrackerclass.R;
import com.mobilesutra.feetrackerclass.StudManagement;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class addStudent extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {

    Spinner spinner_branch = null, spinner_stand = null, spinner_batch = null, spinner_subj = null;
    Button submit = null;
    EditText studname = null, parent_ph = null, stud_ph = null, address = null, studid = null;
    Context context = this;
    TextView birthdate = null;
    //    MultiSelectionSpinner multiSelectionSpinner;
    ArrayList<String> my_array, list, fee_array;
    String selected_branch_value = "", selected_stand_value = "", selected_batch_value = "", edit_branch = "", edit_batch, edit_stand, selected_subj_value = "", StudentD = "";
    Boolean allchecked = true;
    List<String> SelectedSubj = new ArrayList<>();
    List<String> array1;
    int result1 = 0;
    int checkedid = 0;
    long rowid;
    String[] array2;
    ProgressDialog dialog1;
    Boolean flag1 = false;
    String flag = "True";
    LinkedHashMap<String, String> lhm_branch = new LinkedHashMap<String, String>();// Branch
    LinkedHashMap<String, String> lhm_std = new LinkedHashMap<String, String>();// Std
    LinkedHashMap<String, String> lhm_batch = new LinkedHashMap<String, String>();// Batch
    LinkedHashMap<String, String> lhm_sub = new LinkedHashMap<String, String>();// Subjects
    ArrayList<HashMap<String, String>> menu_list;
    ArrayList<String> arrstand1;
    ArrayList<String> arrbatch;
    ArrayList<String> arraysubj;
    LinkedHashMap<String, String> lhm_checkedSub = new LinkedHashMap<String, String>();// Subjects
    String payment_status = "Cash";
    EditText initial_payment, cheque_no;
    RadioGroup payment_group;
    RadioButton cash_radio, cheque_radio, online_radio;
    String local_id = "";
    ImageView menuList = null, menu_add = null;

    // Declare
    static final int PICK_CONTACT = 1;

    private ImageView ivProfile;
    private static final String IMAGE_DIRECTORY = "/FeeTracker";
    private int GALLERY = 2, CAMERA = 3;
    private  String path;
    private byte[] inputData;
    private Database mDatabase;
    private RelativeLayout mLayout;
    private String ImageString = "";
    public final static int PERMISSION_REQUEST_CONTACT = 1800;
    private ImageView ivAddContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_add_student);
        mDatabase = new Database(context);
        getIntentData();
        initComponentData();
        initComponentListeners();
    }


    public void getIntentData() {

    }

    public void initComponentData() {
        Typeface roboto_reguler = Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");
        Typeface roboto_light = Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
        TextView txt_user = (TextView) findViewById(R.id.txt_user);
        txt_user.setText(MyApplication.get_session("classname"));
        txt_user.setTypeface(roboto_reguler);
        TextView heading = (TextView) findViewById(R.id.txt_user_heading);
        heading.setText("Add Student");
        heading.setTypeface(roboto_reguler);

        menuList = (ImageView) findViewById(R.id.menuList);
        menuList.setVisibility(View.GONE);

        menu_add = (ImageView) findViewById(R.id.menu_add);
        menu_add.setVisibility(View.GONE);

        studname = (EditText) findViewById(R.id.student_name);
        studid = (EditText) findViewById(R.id.student_id);
        parent_ph = (EditText) findViewById(R.id.phoneno2);
        stud_ph = (EditText) findViewById(R.id.student_phoneno);
        birthdate = (TextView) findViewById(R.id.student_dateofbirth);
        spinner_branch = (Spinner) findViewById(R.id.spinner_branch);
        spinner_stand = (Spinner) findViewById(R.id.spinner_stand);
        spinner_batch = (Spinner) findViewById(R.id.spinner_batch);
        submit = (Button) findViewById(R.id.btn_submit);
        // multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.spinner_subj);
        spinner_subj = (Spinner) findViewById(R.id.spinner_subj);
        submit.setTypeface(roboto_reguler);

        initial_payment = (EditText) findViewById(R.id.initial_payment);
        payment_group = (RadioGroup) findViewById(R.id.payment);
        cash_radio = (RadioButton) findViewById(R.id.cash);
        cheque_radio = (RadioButton) findViewById(R.id.chaque);
        online_radio = (RadioButton) findViewById(R.id.online);
        cheque_no = (EditText) findViewById(R.id.cash_no);
        cash_radio.setChecked(true);

        ivProfile = (ImageView) findViewById(R.id.ivProfile);

        mLayout = (RelativeLayout)findViewById(R.id.mLayout);

        ivAddContact =(ImageView)findViewById(R.id.ivAddContact);
    }

    public void initComponentListeners() {


        Intent extras = getIntent();
        final String autoid = extras.getStringExtra("autoid");
        final String local_id = extras.getStringExtra("local_id");
        flag = extras.getStringExtra("flag");
        //  Toast.makeText(context,"value-->"+flag,Toast.LENGTH_SHORT).show();



        ivAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    askForContactPermission();
                }
                else
                {
                    getContact();
                    /*Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, PICK_CONTACT);*/
                }
            }
        });


        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(addStudent.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(addStudent.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(addStudent.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        MyApplication.requestMarshMallowPermission(addStudent.this, mLayout);
                        return;
                    }
                    takePhotoFromCamera();
                    //showPictureDialog();
                }
                else {
                    takePhotoFromCamera();
                   // showPictureDialog();
                }

            }
        });


        menu_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    askForContactPermission();
                }
                else
                {
                    getContact();
                    /*Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, PICK_CONTACT);*/
                }

            }
        });
        payment_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == cash_radio.getId()) {
                    payment_status = "Cash";
                    cheque_no.setVisibility(View.GONE);

                } else if (checkedId == cheque_radio.getId()) {
                    cheque_no.setVisibility(View.VISIBLE);
                    payment_status = "Cheque";
                } else {
                    cheque_no.setVisibility(View.GONE);
                    payment_status = "Online";
                }

            }
        });

        if (flag.equals("edit")) {
            Cursor res = mDatabase.GetStudentDataById(autoid);
            log(res + " "+res);

            if (res.getCount() > 0) {
                if (res.moveToFirst()) {
                    do {

                        edit_branch = res.getString(res.getColumnIndexOrThrow("Branch"));
                        edit_stand = res.getString(res.getColumnIndexOrThrow("Standard"));
                        edit_batch = res.getString(res.getColumnIndexOrThrow("Batch"));

                        studname.setText(res.getString(res.getColumnIndexOrThrow("StudentName")));
                        studname.setSelection(studname.getText().length());

                        String studentProfile = res.getString(res.getColumnIndex("StudentProfile"));
                     // Bitmap bitmapss = MyApplication.decodeBase64Profile(studentProfile);
                      // ivProfile.setImageBitmap(bitmapss);
                        if (studentProfile != null)
                        {
                            if (studentProfile.startsWith("[B@") || studentProfile.equals(""))
                            {
                           /* Bitmap bitmapp = MyApplication.decodeBase64Profile(studentProfile);
                            Glide.with(context)
                                    .load(bitmapp)
                                    .error(R.drawable.user_placeholder)
                                    .placeholder(R.drawable.user_placeholder)
                                    .into(ivProfile);*/
                            }
                            else if (studentProfile != null)
                            {
                                Bitmap bitmap = MyApplication.decodeBase64Profile(studentProfile);
                                ivProfile.setImageBitmap(bitmap);
                            }
                        }

                       // parent_ph.setText(res.getString(res.getColumnIndexOrThrow("Phone1")));
                       // parent_ph.setSelection(parent_ph.getText().length());

                       // stud_ph.setText(res.getString(res.getColumnIndexOrThrow("Phone2")));
                       // stud_ph.setSelection(stud_ph.getText().length());

                        birthdate.setText(res.getString(res.getColumnIndexOrThrow("DOA")));
                        studid.setText(local_id);
                        studid.setSelection(studid.getText().length());


                    } while (res.moveToNext());
                }
            }

            /*menu_list = new ArrayList<HashMap<String, String>>();

            menu_list = ((MyApplication) getApplication()).dbo.GetStudentDataById(autoid);
            log(menu_list + "");

            for (int j = 0; j < menu_list.size(); j++) {
                edit_branch = menu_list.get(j).get("Branch");
                edit_stand = menu_list.get(j).get("std");
                edit_batch = menu_list.get(j).get("Batch");
               // ivProfile.setImageBitmap(menu_list.get(j).get("sprofile"));
               // byte[] outImage = menu_list.get(j).get("sprofile");
                studname.setText(menu_list.get(j).get("sname").toString());
                studname.setSelection(studname.getText().length());

                parent_ph.setText(menu_list.get(j).get("phone1").toString());
                parent_ph.setSelection(parent_ph.getText().length());

                stud_ph.setText(menu_list.get(j).get("studphone").toString());
                stud_ph.setSelection(stud_ph.getText().length());

                birthdate.setText(menu_list.get(j).get("date").toString());
                studid.setText(local_id);
                studid.setSelection(studid.getText().length());

            }*/
        } else if (flag.equals("flag")) {

            flag = "flag";

        } else {


        }

        studname.addTextChangedListener(new MyTextWatcher(studname));
       // parent_ph.addTextChangedListener(new MyTextWatcher(parent_ph));
       // stud_ph.addTextChangedListener(new MyTextWatcher(stud_ph));
        // birthdate.addTextChangedListener(new MyTextWatcher(birthdate));

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
                mDatePicker = new DatePickerDialog(addStudent.this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker,
                                                  int selectedyear, int selectedmonth,
                                                  int selectedday) {
                                // TODO Auto-generated method stub
                                /* Your code to get date and time */
                                selectedmonth = selectedmonth + 1;
                                birthdate.setText("" + selectedday + "/"
                                        + selectedmonth + "/" + selectedyear);
                            }
                        }, mYear, mMonth, mDay);

                mDatePicker.setTitle("Select Date");
                mDatePicker.show();


            }
        });

        my_array = new ArrayList<String>();
        list = new ArrayList<String>();
        fee_array = new ArrayList<String>();

        //add spinner _branch details
        lhm_branch = ((MyApplication) getApplication()).dbo
                .getBranchWithActiveFlag(MyApplication.get_session("classid"));
        log(lhm_branch + "");
        ArrayList<String> arrBranch = new ArrayList<String>();

        for (Object o : lhm_branch.keySet()) {
            arrBranch.add(o.toString());
            System.out.println("key:" + o.toString() + "___" + "value:" + lhm_branch.get(o).toString());
        }


        ArrayAdapter<String> adapter0 = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arrBranch);

        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_branch.setAdapter(adapter0);

        //editable selected branch
        if (flag.equals("edit")) {

            String branchname = MyApplication.dbo.getBranchName(edit_branch);
            int position = arrBranch.indexOf(branchname);

            spinner_branch.setSelection(position, true);
            selected_branch_value = lhm_branch.get(spinner_branch.getSelectedItem().toString());


        } else if (flag.equals("flag")) {
            String branchname = MyApplication.dbo.getBranchName(MyApplication.get_session("session_branch"));
            int position = arrBranch.indexOf(branchname);

            spinner_branch.setSelection(position, true);
            selected_branch_value = lhm_branch.get(spinner_branch.getSelectedItem().toString());

        } else {


        }


        spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //select branch value
                selected_branch_value = lhm_branch.get(spinner_branch.getSelectedItem().toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //editable selected stand
        if (flag.equals("edit")) {


            lhm_std = ((MyApplication) getApplication()).dbo.getStandard(MyApplication.get_session("classid"), edit_branch);
            log(lhm_std + "");
            arrstand1 = new ArrayList<String>();
            for (Object o : lhm_std.keySet()) {

                arrstand1.add(o.toString());
                System.out.println("stand-key:" + o.toString() + "___" + "value:" + lhm_std.get(o).toString());
            }
            ArrayAdapter<String> adapter = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arrstand1);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_stand.setAdapter(adapter);

            String standname = MyApplication.dbo.getStandardName(edit_stand);
            int position = arrstand1.indexOf(standname);
//            Toast.makeText(context,"edit stand"+position,Toast.LENGTH_SHORT).show();
            spinner_stand.setSelection(position, true);


            selected_stand_value = lhm_std.get(spinner_stand.getSelectedItem().toString());


        } else if (flag.equals("flag")) {
            lhm_std = ((MyApplication) getApplication()).dbo.getStandard(MyApplication.get_session("classid"), selected_branch_value);
            log(lhm_std + "");
            arrstand1 = new ArrayList<String>();
            for (Object o : lhm_std.keySet()) {

                arrstand1.add(o.toString());
                System.out.println("stand-key:" + o.toString() + "___" + "value:" + lhm_std.get(o).toString());
            }
            ArrayAdapter<String> adapter = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arrstand1);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_stand.setAdapter(adapter);

            String standname = MyApplication.dbo.getStandardName(MyApplication.get_session("session_stand"));
            int position = arrstand1.indexOf(standname);
//            Toast.makeText(context,"edit stand"+position,Toast.LENGTH_SHORT).show();
            spinner_stand.setSelection(position, true);


            selected_stand_value = lhm_std.get(spinner_stand.getSelectedItem().toString());

        } else {


        }


        spinner_stand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                selected_stand_value = lhm_std.get(spinner_stand.getSelectedItem().toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        if (flag.equals("edit")) {


            lhm_batch = ((MyApplication) getApplication()).dbo.getBatch(MyApplication.get_session("classid"), edit_branch, edit_stand);
            log(lhm_batch + "");


            arrbatch = new ArrayList<String>();
            for (Object o : lhm_batch.keySet()) {

                arrbatch.add(o.toString());
                System.out.println("batch-key:" + o.toString() + "___" + "value:" + lhm_batch.get(o).toString());
            }
            ArrayAdapter<String> adapter = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arrbatch);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_batch.setAdapter(adapter);

            String batchname = MyApplication.dbo.getbatchhhName(edit_batch);
            int position3 = arrbatch.indexOf(batchname);
            spinner_batch.setSelection(position3, true);

            selected_batch_value = lhm_batch.get(spinner_batch.getSelectedItem().toString());


        } else if (flag.equals("flag")) {

            lhm_batch = ((MyApplication) getApplication()).dbo.getBatch(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value);
            log(lhm_batch + "");


            arrbatch = new ArrayList<String>();
            for (Object o : lhm_batch.keySet()) {

                arrbatch.add(o.toString());
                System.out.println("batch-key:" + o.toString() + "___" + "value:" + lhm_batch.get(o).toString());
            }
            ArrayAdapter<String> adapter = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arrbatch);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_batch.setAdapter(adapter);

            String batchname = MyApplication.dbo.getbatchhhName(MyApplication.get_session("session_batch"));
            int position3 = arrbatch.indexOf(batchname);
            spinner_batch.setSelection(position3, true);

            selected_batch_value = lhm_batch.get(spinner_batch.getSelectedItem().toString());
        } else {


        }


        spinner_batch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_batch_value = lhm_batch.get(spinner_batch.getSelectedItem().toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        if (flag.equals("edit")) {
            lhm_sub = MyApplication.dbo.getSubjectWithInsertFee(MyApplication.get_session("classid"));

            log(lhm_sub + "");
            arraysubj = new ArrayList<String>();
            for (Object o : lhm_sub.keySet()) {
                arraysubj.add(o.toString());
                System.out.println("batch-key:" + o.toString() + "___" + "value:" + lhm_sub.get(o).toString());
            }
            ArrayAdapter<String> adaptersub = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arraysubj);
            adaptersub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_subj.setAdapter(adaptersub);

            String select_sub = ((MyApplication) getApplication()).dbo.getSId(autoid);//get selected subj id
            Log.e("count-TBL_DATA", "getSubjjjnameWithMultilpename" + select_sub);
            String subjname = ((MyApplication) getApplication()).dbo.getSubjjjnameWithMultilpe(select_sub);//get selected subj name

            int position = arraysubj.indexOf(subjname);

            spinner_subj.setSelection(position, true);
            selected_subj_value = lhm_sub.get(spinner_subj.getSelectedItem().toString());


        } else if (flag.equals("flag")) {

            lhm_sub = MyApplication.dbo.getSubjectWithInsertFee(MyApplication.get_session("classid"));

            log(lhm_sub + "");
            arraysubj = new ArrayList<String>();
            for (Object o : lhm_sub.keySet()) {
                arraysubj.add(o.toString());
                System.out.println("batch-key:" + o.toString() + "___" + "value:" + lhm_sub.get(o).toString());
            }
            ArrayAdapter<String> adaptersub = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arraysubj);
            adaptersub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_subj.setAdapter(adaptersub);


            String subjname = ((MyApplication) getApplication()).dbo.getSubjjjnameWithMultilpe(MyApplication.get_session(MyApplication.session_subj));//get selected subj name
            int position = arraysubj.indexOf(subjname);

            spinner_subj.setSelection(position, true);
            selected_subj_value = lhm_sub.get(spinner_subj.getSelectedItem().toString());

        } else {
        }

        spinner_subj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_subj_value = lhm_sub.get(spinner_subj.getSelectedItem().toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


//
//        if(flag.equals("edit"))
//        {
////            lhm_sub = ((MyApplication) getApplication()).dbo.getSubject(MyApplication.get_session("classid"), edit_branch, edit_stand, edit_batch);
//            lhm_sub = MyApplication.dbo.getSubjectWithInsertFee(MyApplication.get_session("classid"));
//            array1 = new ArrayList<>();
//
//
//            for (Object o : lhm_sub.keySet()) {
//
//                array1.add(o.toString());
//
//                System.out.println("subject-key:" + o.toString() + "___" + "value:" + lhm_sub.get(o).toString());
//            }
//            Log.d("tag", "array1" + array1);
//            int[] numbers = new int[0];
//
//                String select_sub = ((MyApplication) getApplication()).dbo.getSId(autoid);//get selected subj id
//                Log.e("count-TBL_DATA", "getSubjjjnameWithMultilpename" + select_sub);
//                String subjname = ((MyApplication) getApplication()).dbo.getSubjjjnameWithMultilpe(select_sub);//get selected subj name
//
//                String[] numberStrs = subjname.split(",");
//                numbers= new int[numberStrs.length];
//                for (int i = 0; i < numberStrs.length; i++) {
//                    int position3 = array1.indexOf(numberStrs[i]);
//                    numbers[i] = position3;
//                    Log.d("tag", "numbers" + position3);
//                }
//                Log.d("tag", "numbers" + numbers+"");
//
//            array2 =array1.toArray(new String[array1.size()]);
//            set(array2, numbers);
//
//
//        }else
//        {
////            lhm_sub = ((MyApplication) getApplication()).dbo.getSubject(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value);
//            lhm_sub = MyApplication.dbo.getSubjectWithInsertFee(MyApplication.get_session("classid"));
//            array1 = new ArrayList<>();
//
//
//            for (Object o : lhm_sub.keySet()) {
//
//                array1.add(o.toString());
//
//                System.out.println("subject-key:" + o.toString() + "___" + "value:" + lhm_sub.get(o).toString());
//            }
//            Log.d("tag", "array1" + array1);
//            int[] numbers = new int[0];
//
//
//            String subjname = ((MyApplication) getApplication()).dbo.getSubjjjnameWithMultilpe(MyApplication.get_session("session_subj"));//get selected subj name
//            Log.d("tag", "subjname" + subjname);
//            String[] numberStrs = {subjname};
//            numbers= new int[numberStrs.length];
////            for (int i = 0; i < numberStrs.length; i++) {
//                int position3 = array1.indexOf(subjname);
//                numbers[0] = position3;
////                Log.d("tag", "numbers" + position3);
//           // }
//            Log.d("tag", "numbers" + numbers+"");
//
//            array2 =array1.toArray(new String[array1.size()]);
//            set(array2, numbers);
//
//        }
//
//        if(lhm_sub.size()!=0)
//        {
//
//
//        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(addStudent.this, "Enter Student Name...",
//                        Toast.LENGTH_SHORT).show();
                String checkedsubj = "";
                Boolean flag5 = false;
                Log.d("tag", "2222" + StudentD);

                if (studid.getText().toString().equals("")) {
                    Toast.makeText(addStudent.this, getResources().getString(R.string.enter_roll_number),
                            Toast.LENGTH_SHORT).show();
                }
                else if (studname.getText().toString().equals("")) {
                    Toast.makeText(addStudent.this, getResources().getString(R.string.valid_studname), Toast.LENGTH_SHORT).show();

                }
                else if (stud_ph.getText().toString().equals("")) {
                    Toast.makeText(addStudent.this, getResources().getString(R.string.valid_phone), Toast.LENGTH_SHORT).show();
                }
//                } else if (!(stud_ph.getText().toString().equals(""))
//                        && stud_ph.getText().toString().length() != 10) {
//                    Toast.makeText(addStudent.this, getResources().getString(R.string.valid_phone),
//                            Toast.LENGTH_SHORT).show();
                /*} else if (parent_ph.getText().toString().equals("") || parent_ph.getText().toString().length() != 10) {
                    Toast.makeText(addStudent.this,
                            getResources().getString(R.string.valid_phone),
                            Toast.LENGTH_SHORT).show();
                }*/
                else if (initial_payment.getText().toString().equals(""))
                {
                    Toast.makeText(addStudent.this,
                            getResources().getString(R.string.enter_initial_payment),
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    if (birthdate.getText().toString().equals("")) {
                        birthdate.setText("");
                    }
                    if (flag.equals("edit")) {
//                            Toast.makeText(addStudent.this, "edit block",
//                                    Toast.LENGTH_SHORT).show();
                        checkedid = 0;
                        result1 = 0;
                        lhm_checkedSub.clear();

                        int count = SelectedSubj.size();
                        checkedid = 1;
                        if (selected_subj_value.length() != 0) {

                            String[] array = selected_subj_value.split(",");
                            log("checkedsubj", array.length + "");
                            for (int i = 0; i < array.length; i++) {
                                result1 += checkedid * (int) Math.pow(10, Integer.parseInt(array[i]));
                            }
                            log("result1", result1 + "");
                        }


//                            for (int k = 0; k < count; k++)
//                            {
//                                checkedsubj = lhm_sub.get(SelectedSubj.get(k));
//                                lhm_checkedSub.put(checkedsubj,String.valueOf(checkedid));
//                                log("checkedsubj", checkedsubj);
//                                String [] array =checkedsubj.split(",");
//                                log("checkedsubj", array.length+"");
//                                for(int i=0;i<array.length;i++) {
//                                    result1 += checkedid * (int) Math.pow(10, Integer.parseInt(array[i]));
//                                }
//                                log("result1", result1+"");
//
//                            }

                        fee_array = ((MyApplication) getApplication()).dbo.getFeeid(MyApplication.get_session("classid"));
                        log("fee_array", fee_array + "");

//                            for (int i = 0; i < fee_array.size(); i++) {

                        Boolean status = MyApplication.dbo.checkStudAddStatus(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value, String.valueOf(result1));
//                                 Toast.makeText(context,"status"+status,Toast.LENGTH_SHORT).show();;
                        if (status.equals(true)) {
                            showprocessDialog();
//                                    Toast.makeText(addStudent.this, "Please Wait", Toast.LENGTH_LONG).show();
                            rowid = 0;

                            /*if (!((MyApplication) getApplication()).dbo.isRollNoSetForSubject(
                                    MyApplication.get_session("classid"),  studid.getText().toString().trim(),
                                    selected_branch_value,  selected_stand_value, selected_batch_value) || studid.getText().toString().trim().equals(local_id) )
                            {*/
                            rowid = ((MyApplication) getApplication()).dbo.update_local_student(autoid, studid.getText().toString().trim(),
                                    MyApplication.get_session("classid"), selected_branch_value,
                                    studname.getText().toString().trim(),ImageString, selected_stand_value,
                                    selected_batch_value, birthdate.getText().toString().trim(),
                                    "", parent_ph.getText().toString().trim(),
                                    stud_ph.getText().toString().trim());
                            ((MyApplication) getApplication()).log("AddStudent ", " UPDATE ROWID : " + rowid);

                            if (rowid > 0) {
                                dialog1.dismiss();
//                                    Toast.makeText(context,"updateed value "+rowid,Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(addStudent.this, StudManagement.class);
                                startActivity(intent);
                                finish();

                                final Iterator<String> cursor1 = lhm_checkedSub.keySet().iterator();
                                while (cursor1.hasNext()) {
                                    final String key = cursor1.next();
                                    log("key", key);
                                    log("lhm_checkedSub", lhm_checkedSub + "");
                                }
                                ((MyApplication) getApplication()).dbo.DeleteStudent_Subject(autoid);

                                StringBuilder s = new StringBuilder();
                                String id = "";
                                for (Object o : lhm_checkedSub.keySet()) {
                                    if (lhm_checkedSub.get(o).toString().equals("1")) {
                                        if (id.equals("")) {
                                            id = o.toString();
                                            Log.i("Database", "id" + o.toString());
                                        } else {
                                            id = id + "," + o.toString();
                                            Log.i("Database", "id" + id);
                                        }

                                        log("insert_subject", rowid + "" + toString());
                                    }
                                }
                                Log.i("Database", "id***#" + id);


                                String[] array = selected_subj_value.split(",");
                                for (int j = 0; j < array.length; j++) {
                                    ((MyApplication) getApplication()).dbo.updateStudent_Subject(MyApplication.get_session("classid"), autoid, array[j], selected_subj_value);
                                    flag = "True";
                                    flag5 = true;
                                }

                                Toast.makeText(addStudent.this, getResources().getString(R.string.success_update_student), Toast.LENGTH_SHORT).show();

                                // MyApplication.dbo.deleteStudentDefaultFee()
///tejas changes

                                String totfee = MyApplication.dbo.getTotalFee(selected_subj_value, MyApplication.get_session("classid"), selected_stand_value, selected_branch_value, selected_batch_value);
//                                    bal2 = bal2 - fesspaid;
//                                    String Balance = "" + bal2;
//
//                                    ((MyApplication) getApplication()).dbo.InsertStudentfeetable1(MyApplication.get_session("classid"),MyApplication.get_session("studid"), MyApplication.get_session("std"), "", fees, bal2 + "", inst_date, remark_note, batch, branch, subj, payment_status);
//        /*
//                                    Log.i("add_student","totfee :"+totfee);
//                                    Log.i("add_student","studid :"+autoid);
//                                    Log.i("add_student","selected_stand_value :"+selected_stand_value);
//                                    Log.i("add_student","result1 :"+result1);
//                                    Log.i("add_student","selected_batch_value :"+selected_batch_value);
//                                    Log.i("add_student","selected_branch_value :"+selected_branch_value);
//                                    Log.i("add_student","selected_subj_value :"+selected_subj_value);
//
//                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                                    Date date = new Date();
//                                    String CurDate = dateFormat.format(date);
//
//                                    MyApplication.dbo.insert_student_fee_default(MyApplication.get_session("classid"), autoid, selected_stand_value, "" + result1, "0", totfee, CurDate , "", selected_batch_value, selected_branch_value, selected_subj_value, " ");
                                if (!flag5) {
                                    Toast.makeText(addStudent.this, getResources().getString(R.string.fail_fee_combination), Toast.LENGTH_SHORT).show();
                                }
                            }
                           /*} else {
                                dialog1.dismiss();
                                submit.setText(getResources().getString(R.string.submit));
                                Toast.makeText(addStudent.this, getResources().getString(R.string.enter_unique_roll_number), Toast.LENGTH_SHORT).show();
                                // studid.setError(getResources().getString(R.string.enter_unique_roll_number));
                            }*/
                        }
                    } else {
//                            Toast.makeText(addStudent.this, "other block",
//                                    Toast.LENGTH_SHORT).show();
                        checkedid = 0;
                        result1 = 0;
                        lhm_checkedSub.clear();

                        int count = SelectedSubj.size();

                        String[] arraydummy = new String[SelectedSubj.size()];
                        SelectedSubj.toArray(arraydummy); // fill the array
                        log("checkedsubj", arraydummy.toString());

                        checkedid = 1;

                        if (selected_subj_value.length() != 0) {

                            String[] array = selected_subj_value.split(",");
                            log("checkedsubj", array.length + "");
                            for (int i = 0; i < array.length; i++) {
                                result1 += checkedid * (int) Math.pow(10, Integer.parseInt(array[i]));
                            }
                            log("result1", result1 + "");
                        }

//                            for (int k = 0; k < count; k++)
//                            {
//                                checkedsubj = lhm_sub.get(SelectedSubj.get(k));
//                                lhm_checkedSub.put(checkedsubj, String.valueOf(checkedid));
//                                String [] array =checkedsubj.split(",");
//                                log("checkedsubj", array.length+"");
//                                for(int i=0;i<array.length;i++) {
//                                    result1 += checkedid * (int) Math.pow(10, Integer.parseInt(array[i]));
//                                }
//                                log("result1", result1+"");
//
//                            }

                        fee_array = ((MyApplication) getApplication()).dbo.getFeeid(MyApplication.get_session("classid"));
                        log("fee_array", fee_array + "");
                        Boolean status = MyApplication.dbo.checkStudAddStatus(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value, String.valueOf(result1));
//                            Toast.makeText(context,"status"+status,Toast.LENGTH_SHORT).show();;
                        if (status.equals(true)) {

//                            for (int i = 0; i < fee_array.size(); i++) {

//                                log("for_loop_fee_array", fee_array.get(i)+"");
//                                if (String.valueOf(result1).equals(fee_array.get(i)))
//                                {

                            showprocessDialog();
                            rowid = 0;
//                                    Toast.makeText(addStudent.this, "Please Wait", Toast.LENGTH_LONG).show();
                            /*if (!((MyApplication) getApplication()).dbo.isRollNoSetForSubject(
                                    MyApplication.get_session("classid"),
                                    studid.getText().toString().trim(),
                                    selected_branch_value,  selected_stand_value, selected_batch_value ))
                            {*/
                            rowid = ((MyApplication) getApplication()).dbo.insert_local_student(
                                    MyApplication.get_session("classid"),
                                    studid.getText().toString().trim(),
                                    selected_branch_value,
                                    studname.getText().toString().trim(),
                                    ImageString,
                                    selected_stand_value,
                                    selected_batch_value,
                                    birthdate.getText().toString().trim(),
                                    "", parent_ph.getText().toString().trim(),
                                    stud_ph.getText().toString().trim());

                            ((MyApplication) getApplication()).log("AddStudent", "INSERT ROWID: " + rowid);
                            if (rowid > 0) {
                                dialog1.dismiss();

                                Intent intent = new Intent(addStudent.this, StudManagement.class);
                                startActivity(intent);
                                finish();

                                final Iterator<String> cursor1 = lhm_checkedSub.keySet().iterator();
                                while (cursor1.hasNext()) {
                                    final String key = cursor1.next();
                                    log("key", key);
                                    log("lhm_checkedSub", lhm_checkedSub + "");
                                }

                                String id = "";
                                StringBuilder s = new StringBuilder();
                                for (Object o : lhm_checkedSub.keySet()) {
                                    if (lhm_checkedSub.get(o).toString().equals("1")) {

                                        if (id.equals("")) {
                                            id = o.toString();
                                            Log.i("Database", "id" + o.toString());
                                        } else {
                                            id = id + "," + o.toString();
                                            Log.i("Database", "id" + id);
                                        }

                                        String subid = ((MyApplication) getApplication()).dbo.getSubjectId(o.toString());

                                        log("insert_subject", rowid + "" + toString());


                                    }
                                }

                                String[] array = selected_subj_value.split(",");
                                Log.i("Database", "id*******" + Arrays.toString(array));
                                for (int j = 0; j < array.length; j++) {


                                    ((MyApplication) getApplication()).dbo.insertStudent_Subject(MyApplication.get_session("classid"), rowid + "", array[j], selected_subj_value);

                                    flag5 = true;

                                    Toast.makeText(addStudent.this, getResources().getString(R.string.success_add_student), Toast.LENGTH_SHORT).show();
                                }

                                int totfee = Integer.parseInt(MyApplication.dbo.getTotalFee(selected_subj_value, MyApplication.get_session("classid"), selected_stand_value, selected_branch_value, selected_batch_value));
                                String initial_amount = initial_payment.getText().toString();
                                if (initial_payment.getText().toString().equals("")) {
                                    initial_amount = "0";
                                }
                                int paid_fees = Integer.parseInt(initial_amount);
                                totfee = totfee - paid_fees;
                                String Balance = "" + totfee;

                                Log.i("add_student", "totfee :" + totfee);
                                Log.i("add_student", "initial_amount :" + initial_amount);
                                Log.i("add_student", "paid_fees :" + paid_fees);
                                Log.i("add_student", "selected_stand_value :" + selected_stand_value);
                                Log.i("add_student", "result1 :" + result1);
                                Log.i("add_student", "selected_batch_value :" + selected_batch_value);
                                Log.i("add_student", "selected_branch_value :" + selected_branch_value);
                                Log.i("add_student", "selected_subj_value :" + selected_subj_value);

                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = new Date();
                                String CurDate = dateFormat.format(date);

                                ((MyApplication) getApplication()).dbo.InsertStudentfeetable1(MyApplication.get_session("classid"), rowid + "",
                                        selected_stand_value, "", paid_fees + "", totfee + "", CurDate, "initial payment", selected_batch_value,
                                        selected_branch_value, selected_subj_value, payment_status);
                                // MyApplication.dbo.insert_student_fee_default(MyApplication.get_session("classid"), rowid, selected_stand_value, "" + result1, "0", totfee, CurDate , "", selected_batch_value, selected_branch_value, selected_subj_value, " ");

                                if (!flag5) {
                                    Toast.makeText(addStudent.this, getResources().getString(R.string.fail_fee_combination), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                dialog1.dismiss();
                                submit.setText(getResources().getString(R.string.submit));
                                Toast.makeText(addStudent.this, getResources().getString(R.string.enter_unique_roll_number), Toast.LENGTH_SHORT).show();
                                // studid.setError(getResources().getString(R.string.enter_unique_roll_number));
                            }
                            /*} else {
                                dialog1.dismiss();
                                submit.setText(getResources().getString(R.string.submit));
                                Toast.makeText(addStudent.this, getResources().getString(R.string.enter_unique_roll_number), Toast.LENGTH_SHORT).show();
                            }*/

                        } else {

//                            Toast.makeText(addStudent.this, "else_result_others", Toast.LENGTH_SHORT).show();
//
                        }

                        //  }
                    }
                }

            }
        });
    }

    private void getContact() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    private void askForContactPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(addStudent.this,
                    Manifest.permission.READ_CONTACTS)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Contacts access needed");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setMessage("please confirm Contacts access");//TODO put real question
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}
                                , PERMISSION_REQUEST_CONTACT);
                    }
                });
                builder.show();
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(addStudent.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        PERMISSION_REQUEST_CONTACT);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else{
            getContact();
        }
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }



    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
        /*if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(addStudent.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(addStudent.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(addStudent.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                MyApplication.requestMarshMallowPermission(addStudent.this, mLayout);
                return;
            }
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        }
        else
        {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        }*/

    }


    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {


        SelectedSubj = strings;

//        Toast.makeText(this, strings.toString()+SelectedSubj, Toast.LENGTH_LONG).show();

    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.student_name:
                    //	validateName();
                    break;
             /*   case R.id.phoneno2:
                    //	validateEmail();
                    break;*/
                case R.id.student_phoneno:
                    //	validatePassword();
                    break;
//                case R.id.student_address:
//                    //	validatePassword();
//                    break;
            }
        }
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        /*Intent i = new Intent(context, StudManagement.class);*/
        Intent i = new Intent(context, ActivityStudentManagementList.class);
        startActivity(i);
        finish();

    }

    public void showprocessDialog() {

        submit.setText("Please Wait.....");
        dialog1 = ProgressDialog.show(context, "Submitting  Form....",
                "Please Wait", true, false);
        dialog1.show();

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


    //code
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                if (null != contentURI) {

                   ivProfile.setImageURI(contentURI);
                    try {
                     //   InputStream iStream = getContentResolver().openInputStream(contentURI);
                       // inputData = MyApplication.getBytes(iStream);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        ImageString = MyApplication.ConvertString(bitmap);
                      //  ivProfile.setImageBitmap(bitmap);
                        Log.i("RecordCount", "inputData is Gallery " + ImageString);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i("RecordCount", "inputData IOException is" + e.getMessage());
                    }


                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ivProfile.setImageBitmap(thumbnail);
            ImageString = MyApplication.ConvertString(thumbnail);
            Log.i("RecordCount", "ImageString CAMERA is " + ImageString);

           /* ByteArrayOutputStream stream = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);
            inputData = stream.toByteArray();
            Log.i("RecordCount", "inputData is CAMERA" + inputData);*/
            /*if (data != null) {
                Uri contentURI = data.getData();
                if (null != contentURI) {

                    ivProfile.setImageURI(contentURI);
                    try {
                        InputStream iStream = getContentResolver().openInputStream(contentURI);
                        inputData = MyApplication.getBytes(iStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }*/
           // saveImage(thumbnail);
           // Toast.makeText(addStudent.this, "Image Saved!", Toast.LENGTH_SHORT).show();


        } else if (requestCode == PICK_CONTACT) {
            if (resultCode == Activity.RESULT_OK) {

                Uri contactData = data.getData();
                Cursor c = managedQuery(contactData, null, null, null, null);
                if (c.moveToFirst()) {
                    String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                    String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                    if (hasPhone.equalsIgnoreCase("1")) {
                        Cursor phones = getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                null, null);
                        phones.moveToFirst();
                        String cNumber = phones.getString(phones.getColumnIndex("data1"));
                        System.out.println("number is:" + cNumber);

                        stud_ph.setText(cNumber.trim());
                    }
                    String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    System.out.println("name is:" + name.trim());
                    studname.setText(name);

                }
            }

        }
    }

    /*public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }*/

}
