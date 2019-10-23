package com.mobilesutra.feetrackerclass;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.mobilesutra.feetrackerclass.ErrorHandler.ExceptionHandler;
import com.mobilesutra.feetrackerclass.activities.ActivityFeeManagement;
import com.mobilesutra.feetrackerclass.activities.ActivitySMS;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by MobilesutraDesigner on 06/01/2016.
 */
public class StudentFeeStructure extends Activity implements BillingProcessor.IBillingHandler {

    TextView sname, std, sub, totalFee;
    TableLayout tableLayout;
    TextView remark, bal;
    Button addfee, editfee;
    int ttbal, fesspaid, bal2 = 0;
    String inst_date, actualdate, remark_note, studebatch, fees, instdate1, rem1;
    String tfee, tbal, c, resultinfo, edit_result, tempsubject, Balance;
    TextView firstCol;
    TextView secondCol;
    TextView thirdCol;
    TextView forthCol;
    TextView FifthCol;
    Context context = this;
    TableRow row1;
    String branch = "", batch = "", stand = "", subj = "";
    String edit_fees_amt = "", edit_date = "", edit_remark = "",
            row_id = "", edit_subject_id = "", edit_sub_name = "",
            edit_fees_id = "", payment_type = "";
    SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat f3 = new SimpleDateFormat("dd-MM-yyyy");
    String subname = "", cheque_number = "";
    Typeface roboto_light, roboto_reguler;
    String payment_status = "Cash";

    // Billing
    private LinearLayout llBuy,lin2;
    private Button btnBuy;
    BillingProcessor bp;
    private String APP_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtVlqUkDuCS2e3ADCQU4+BkaVfkE4EujmiZYo3HYFhrvoFaOk9fMCx7CvCGDhddF/3QFNUD6kdVq2aX2dvShN4gfnOuqj3GCAWewURaRQk5H9aKFW/K8/m2xls0Fl5guMbUjLlvGhBQKhj4WFHyds0ERWEskJFoXuI8leMLl+z1fZXfZAho0BSVi4t/Om8D4jpreypphvQ5NFjW5G3sbtUbbxGtQo/UrtxqPjgz7ogfmc1I0TxXIZFzLI9OtCbJlt45DPKvJpjBeGCjawOWvbiG7kadV3bhb1o0/1HcXV8b+yD26FAOYaDpN5tT6PEd1lBxz2x0nP8dXfWTXngj2lywIDAQAB";
    private String USER_KEY = MyApplication.get_session(MyApplication.SESSION_USERTEXT);
    private String PRODUCT_ID = "fees";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        getIntentData();
        initComponentData();
        initComponentListener();
    }


    public void getIntentData() {
        Intent extras = getIntent();
        branch = extras.getStringExtra("branch");
        batch = extras.getStringExtra("batch");
        stand = extras.getStringExtra("stand");
        subj = extras.getStringExtra("subj");

    }

    public void initComponentData() {
        roboto_light = Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
        roboto_reguler = Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");
        setContentView(R.layout.student_fee_structure);
        TextView txt_user = (TextView) findViewById(R.id.txt_user);
        txt_user.setText(MyApplication.get_session("classname"));
        txt_user.setTypeface(roboto_reguler);
        TextView heading = (TextView) findViewById(R.id.txt_user_heading);
        heading.setText("Fee Details");
        heading.setTypeface(roboto_reguler);
        sname = (TextView) findViewById(R.id.sname);
        std = (TextView) findViewById(R.id.standard);
        sub = (TextView) findViewById(R.id.subjects);
        totalFee = (TextView) findViewById(R.id.totalFee);

        tableLayout = (TableLayout) findViewById(R.id.TableLayout1);
        remark = (TextView) findViewById(R.id.remark_note);
        bal = (TextView) findViewById(R.id.bal);

        addfee = (Button) findViewById(R.id.addfee);
        editfee = (Button) findViewById(R.id.editfee);

        //Billing
        lin2 = (LinearLayout)findViewById(R.id.lin2);
        llBuy=(LinearLayout)findViewById(R.id.llBuy);
        btnBuy=(Button)findViewById(R.id.btnBuy);
        llBuy.setVisibility(View.VISIBLE);



        bp = BillingProcessor.newBillingProcessor(this, APP_KEY, USER_KEY, this); // doesn't bind
        bp.initialize();
        Log.d("onBillingError", "> initialize");
        for(String sku : bp.listOwnedSubscriptions())
        {
            Log.d("onBillingError", "Owned Subscription: " + sku);
            Log.d("onBillingError", "> Success - getSubscriptionTransactionDetails");
            if (bp.isSubscribed(PRODUCT_ID)) {
                llBuy.setVisibility(View.GONE);
                lin2.setVisibility(View.VISIBLE);
                Log.d("onBillingError", "> Success - True");
            } else {
                llBuy.setVisibility(View.VISIBLE);
                lin2.setVisibility(View.GONE);
                Log.d("onBillingError", "> Success - else");
            }
        }
    }

    public void initComponentListener() {

        sname.setText(MyApplication.get_session("name"));
        String stdname = ((MyApplication) getApplication()).dbo.getStandardName(MyApplication.get_session("std"));
        std.setText(stdname);

        subname = ((MyApplication) getApplication()).dbo.getSubName(MyApplication.get_session("sb"));
        sub.setText(subname);
        totalFee.setText(MyApplication.get_session("amt"));

        display();
        addfee.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (ttbal > 0) {
                    MyApplication.log("","BALANCE 1 = "+ttbal);
//                    addfee();
                    showdialog_notes();
                } else {
                    Toast.makeText(StudentFeeStructure.this,
                            MyApplication.get_session("name") + getResources().getString(R.string.valid_paid_all_fee),
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        editfee.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (((MyApplication) getApplication()).isNetworkAvailable()) {

                    if (edit_fees_amt != "") {
                        if (edit_fees_id.equals("0"))
                            Toast.makeText(
                                    StudentFeeStructure.this,
                                    "Unable to edit local data please all sync data",
                                    Toast.LENGTH_LONG).show();
                        else
                            showdialog_notesedit(edit_date, edit_fees_amt, edit_sub_name,
                                    edit_fees_id, edit_subject_id);
                    } else
                        Toast.makeText(StudentFeeStructure.this, getResources().getString(R.string.valid_select_row)
                                ,
                                Toast.LENGTH_LONG).show();
                }

            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.subscribe(StudentFeeStructure.this, PRODUCT_ID);
            }
        });

    }


//        public void addfee() {
//
//        fesspaid = 0;
//        LayoutInflater factory = LayoutInflater.from(this);
//
//        // text_entry is an Layout XML file containing two text field to display
//        // in alert dialog
//        final View textEntryView = factory.inflate(R.layout.addfee_dialog, null);
//
//        final TextView instdate = (TextView) textEntryView
//                .findViewById(R.id.paid_date);
//        final EditText paid = (EditText) textEntryView
//                .findViewById(R.id.ammount);
//        final EditText note = (EditText) textEntryView
//                .findViewById(R.id.remark);
////            textEntryView.setBackgroundColor(getResources().getColor(R.color.));
//
//
//        paid.setInputType(InputType.TYPE_CLASS_NUMBER);
//
//        instdate.setHint("Select Installment Date");
//        paid.setHint("Enter Fees Amount");
//        note.setHint("Enter Remark");
//
//        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
//
//        instdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//
//                // TODO Auto-generated method stub
//                // To show current date in the datepicker
//                Calendar mcurrentDate = Calendar.getInstance();
//                int mYear = mcurrentDate.get(Calendar.YEAR);
//                int mMonth = mcurrentDate.get(Calendar.MONTH);
//                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog mDatePicker;
//                mDatePicker = new DatePickerDialog(StudentFeeStructure.this,
//                        new DatePickerDialog.OnDateSetListener() {
//                            public void onDateSet(DatePicker datepicker,
//                                                  int selectedyear, int selectedmonth,
//                                                  int selectedday) {
//                                // TODO Auto-generated method stub
//								/* Your code to get date and time */
//                                selectedmonth = selectedmonth + 1;
//                                if (selectedday < 10) {
//                                    instdate.setText("0" + selectedyear + "-"
//                                            + selectedmonth + "-" + selectedday);
//                                }
//                                if (selectedmonth < 10) {
//                                    instdate.setText("" + selectedyear + "-0"
//                                            + selectedmonth + "-" + selectedday);
//                                }
//                                if (selectedmonth < 10 && selectedday < 10) {
//                                    instdate.setText("0" + selectedyear + "-0"
//                                            + selectedmonth + "-" + selectedday);
//                                }
//                                if (selectedmonth > 9 && selectedday > 9) {
//                                    instdate.setText("" + selectedyear + "-"
//                                            + selectedmonth + "-" + selectedday);
//                                }
//
//                                // instdate.setText("" + selectedday + "-" +
//                                // selectedmonth + "-" + selectedyear);
//                            }
//                        }, mYear, mMonth, mDay);
//                mDatePicker.setTitle("Select Date");
//                mDatePicker.show();
//            }
//        });
//        alert.setTitle("Add Fee:")
//                .setView(textEntryView)
//                .setPositiveButton("Collect Fee",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,
//                                                int whichButton) {
//                                if (paid.getText().toString().equals("")) {
//                                    Toast.makeText(StudentFeeStructure.this,
//                                            "Enter Installment Ammount",
//                                            Toast.LENGTH_SHORT).show();
//
//                                }
//                                // else if(note.getText().toString().equals(""))
//                                // {
//                                // Toast.makeText(StudentFee.this,
//                                // "Enter Remark",Toast.LENGTH_SHORT).show();
//                                //
//                                // }
//                                //
//                                else if (instdate.getText().toString()
//                                        .equals("")) {
//                                    Toast.makeText(StudentFeeStructure.this,
//                                            "Select Installment Date",
//                                            Toast.LENGTH_SHORT).show();
//
//                                } else if (Integer.parseInt(paid.getText()
//                                        .toString()) > ttbal) {
//                                    Toast.makeText(
//                                            StudentFeeStructure.this,
//                                            "Installment Ammount Greater Than Balance Fees",
//                                            Toast.LENGTH_SHORT).show();
//
//                                    /*inst_date = instdate.getText().toString();
//                                    fesspaid = Integer.parseInt(paid.getText()
//                                            .toString());
//                                    remark_note = note.getText().toString();
//                                    fees = paid.getText().toString();
//
//                                    insert_local_dtabase();*/
//                                /*} else if (((MyApplication) getApplication())
//                                        .isNetworkAvailable() == false) {
//                                    // Toast.makeText(StudentFee.this,
//                                    // "No Internet Data Saved into Local Database",Toast.LENGTH_SHORT).show();
//                                    inst_date = instdate.getText().toString();
//                                    fesspaid = Integer.parseInt(paid.getText()
//                                            .toString());
//                                    remark_note = note.getText().toString();
//                                    fees = paid.getText().toString();
//                                    insert_local_dtabase();
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(
//                                            StudentFeeStructure.this);
//                                    builder.setMessage(
//                                            "No Internet Data Saved into Local Database")
//                                            .setCancelable(false)
//                                            .setPositiveButton(
//                                                    "OK",
//                                                    new DialogInterface.OnClickListener() {
//                                                        public void onClick(
//                                                                DialogInterface dialog,
//                                                                int id) {
//                                                            // do things
//                                                        }
//                                                    });
//                                    AlertDialog alert = builder.create();
//                                    alert.show();
//                                    // finish();*/
//                                } else {
//
//                                    inst_date = instdate.getText().toString();
//                                    fesspaid = Integer.parseInt(paid.getText()
//                                            .toString());
//                                    remark_note = note.getText().toString();
//                                    fees = paid.getText().toString();
//                              //      new LongOperation3().execute();
//                                    insert_local_dtabase();
//                                }
//
//                            }
//                        }).setNegativeButton("Cancel", null);
//        alert.show();
//
//    }


    public void showdialog_notes() {

        final Dialog dialog1 = new Dialog(context);


        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.addfee_dialog);
        final TextView date = (TextView) dialog1.findViewById(R.id.paid_date);
        final EditText paid = (EditText) dialog1.findViewById(R.id.ammount);
        final EditText cheque_no = (EditText) dialog1.findViewById(R.id.cash_no);
        final EditText note = (EditText) dialog1.findViewById(R.id.remark);
        final Button collectfee = (Button) dialog1.findViewById(R.id.collectfee);
        collectfee.setText("Add Fee");
        final Button Cancel = (Button) dialog1.findViewById(R.id.btnCancel);
        final RadioGroup payment_group = (RadioGroup) dialog1.findViewById(R.id.payment);
        final RadioButton cash_radio = (RadioButton) dialog1.findViewById(R.id.cash);
        final RadioButton cheque_radio = (RadioButton) dialog1.findViewById(R.id.chaque);
        final RadioButton online_radio = (RadioButton) dialog1.findViewById(R.id.online);
        cash_radio.setChecked(true);
        Date d = new Date();
        CharSequence s = DateFormat.format("yyyy" + "-" + "MM" + "-" + "dd", d.getTime());
        date.setText(s.toString());
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


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // TODO Auto-generated method stub
                // To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(StudentFeeStructure.this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker,
                                                  int selectedyear, int selectedmonth,
                                                  int selectedday) {
                                DecimalFormat df = new DecimalFormat("00");
                                String monthStr = df.format(selectedmonth + 1);
                                String dayStr = df.format(selectedday);

                                date.setText(new StringBuilder().append(selectedyear).append("-")
                                        .append(monthStr).append("-").append(dayStr));

                                /*// TODO Auto-generated method stub
                                *//* Your code to get date and time *//*
                                selectedmonth = selectedmonth + 1;
                                if (selectedday < 10) {
                                    date.setText("0" + selectedyear + "-"
                                            + selectedmonth + "-" + selectedday);
                                }
                                if (selectedmonth < 10) {
                                    date.setText("" + selectedyear + "-0"
                                            + selectedmonth + "-" + selectedday);
                                }
                                if (selectedmonth < 10 && selectedday < 10) {
                                    date.setText("0" + selectedyear + "-0"
                                            + selectedmonth + "-" + selectedday);
                                }
                                if (selectedmonth > 9 && selectedday > 9) {
                                    date.setText("" + selectedyear + "-"
                                            + selectedmonth + "-" + selectedday);
                                }*/

                                // instdate.setText("" + selectedday + "-" +
                                // selectedmonth + "-" + selectedyear);
                            }
                        }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });


        collectfee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cheque_number = cheque_no.getText().toString();

                if (paid.getText().toString().equals("")) {
                    Toast.makeText(StudentFeeStructure.this,
                            getResources().getString(R.string.valid_select_Instal),
                            Toast.LENGTH_SHORT).show();

                } else if (date.getText().toString().equals("")) {
                    Toast.makeText(StudentFeeStructure.this, getResources().getString(R.string.valid_select_Instal_date), Toast.LENGTH_SHORT).show();

                } else if (payment_status.equals("cheque") && cheque_number.equals("")) {
                    Toast.makeText(StudentFeeStructure.this, getResources().getString(R.string.valid_cheque_no), Toast.LENGTH_SHORT).show();

                } else if (note.getText().equals("")) {
                    Toast.makeText(StudentFeeStructure.this, getResources().getString(R.string.valid_cheque_no), Toast.LENGTH_SHORT).show();

                } else if (Integer.parseInt(paid.getText().toString()) > ttbal) {
                    MyApplication.log("","BALANCE 2 = "+ttbal);

                    Toast.makeText(
                            StudentFeeStructure.this,
                            getResources().getString(R.string.valid_greater_bal),
                            Toast.LENGTH_SHORT).show();

                } else {

                    inst_date = date.getText().toString();
                    fesspaid = Integer.parseInt(paid.getText()
                            .toString());
                    remark_note = note.getText().toString();
                    fees = paid.getText().toString();

                    insert_local_dtabase();
                    dialog1.dismiss();
                }

            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });


        dialog1.show();
        dialog1.setCancelable(true);
        dialog1.setCanceledOnTouchOutside(false);


    }


    public void showdialog_notesedit(String txt_date, String txt_fees_amt, String txt_sub_name, String txt_Fee_id, String txt_Sub_id) {

        final Dialog dialog1 = new Dialog(context);


        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.addfee_dialog);
        final TextView date = (TextView) dialog1.findViewById(R.id.paid_date);
        final EditText paid = (EditText) dialog1.findViewById(R.id.ammount);
        final EditText cheque_no = (EditText) dialog1.findViewById(R.id.cash_no);
        final EditText note = (EditText) dialog1.findViewById(R.id.remark);
        final Button collectfee = (Button) dialog1.findViewById(R.id.collectfee);
        collectfee.setText("Update Fee");
        final Button Cancel = (Button) dialog1.findViewById(R.id.btnCancel);
        final RadioGroup payment_group = (RadioGroup) dialog1.findViewById(R.id.payment);
        final RadioButton cash_radio = (RadioButton) dialog1.findViewById(R.id.cash);
        final RadioButton cheque_radio = (RadioButton) dialog1.findViewById(R.id.chaque);
        final RadioButton online_radio = (RadioButton) dialog1.findViewById(R.id.online);

        paid.setText(txt_fees_amt);
        date.setText(txt_date);
        note.setText(remark.getText().toString().trim());
        if (payment_type.equalsIgnoreCase("Cash")){
            cash_radio.setChecked(true);
            cheque_radio.setChecked(false);
            online_radio.setChecked(false);
        } else if (payment_type.equalsIgnoreCase("Cheque")){
            cash_radio.setChecked(false);
            cheque_radio.setChecked(true);
            online_radio.setChecked(false);
        } else if (payment_type.equalsIgnoreCase("Online")){
            cash_radio.setChecked(false);
            cheque_radio.setChecked(false);
            online_radio.setChecked(true);
        }

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

        if (paid.getText().toString().equals("")) {
            Toast.makeText(StudentFeeStructure.this,
                    getResources().getString(R.string.valid_select_Instal),
                    Toast.LENGTH_SHORT).show();
        } else if (date.getText().toString().equals("")) {
            Toast.makeText(StudentFeeStructure.this, getResources().getString(R.string.valid_select_Instal_date), Toast.LENGTH_SHORT).show();

        } else if (Integer.parseInt(paid.getText()
                .toString()) > ttbal) {
            MyApplication.log("","BALANCE 3 = "+ttbal);

            Toast.makeText(StudentFeeStructure.this, getResources().getString(R.string.valid_greater_bal), Toast.LENGTH_SHORT).show();

        } else if (((MyApplication) getApplication())
                .isNetworkAvailable() == false) {
            // Toast.makeText(StudentFee.this,
            // "No Internet Connection",Toast.LENGTH_SHORT).show();
            inst_date = date.getText().toString();
            fesspaid = Integer.parseInt(paid.getText().toString());
            MyApplication.log("SAMRATH","remark_note = "+remark_note);

            remark_note = note.getText().toString();
            fees = paid.getText().toString();
            // insert_local_dtabase();
            // finish();
        } else {

        }


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                MyApplication.log("","date =  "+date);
                // TODO Auto-generated method stub
                // To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(StudentFeeStructure.this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker,
                                                  int selectedyear, int selectedmonth,
                                                  int selectedday) {
                                DecimalFormat df = new DecimalFormat("00");
                                String monthStr = df.format(selectedmonth + 1);
                                String dayStr = df.format(selectedday);

                                date.setText(new StringBuilder().append(selectedyear).append("-")
                                        .append(monthStr).append("-").append(dayStr));

                                // instdate.setText("" + selectedday + "-" +
                                // selectedmonth + "-" + selectedyear);
                            }
                        }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });


        collectfee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.log("SAMARTH","update fee clicked");
                Boolean wantToCloseDialog = false;
                // Do stuff, possibly set wantToCloseDialog to true
                // then...
                if (paid.getText().toString().equals("")) {
                    Toast.makeText(StudentFeeStructure.this,
                            getResources().getString(R.string.valid_select_Instal),
                            Toast.LENGTH_SHORT).show();
                    wantToCloseDialog = false;
                } else if (date.getText().toString().equals("")) {
                    Toast.makeText(StudentFeeStructure.this,
                            getResources().getString(R.string.valid_select_Instal_date),
                            Toast.LENGTH_SHORT).show();
                    wantToCloseDialog = false;
                } else {
                    inst_date = date.getText().toString();
                    fesspaid = Integer.parseInt(paid.getText()
                            .toString());

                    //Namrata changes
                    String remark_note1 = note.getText().toString();
                    MyApplication.log("SAMARTH","remark_note in update fee = "+remark_note1.substring(remark_note1.lastIndexOf("-") + 1));
                    remark_note = remark_note1.substring(remark_note1.lastIndexOf("-") + 1);


                    fees = paid.getText().toString();
                    int response = (((MyApplication) getApplication()).dbo).insertEditedFee(
                            inst_date,
                            edit_fees_id,
                            fees,
                            remark_note,
                            MyApplication.get_session("studid"),
                            MyApplication.get_session("std"),
                            MyApplication.get_session("classid"));

                    display();
                    if (response == 1) {
                        Toast.makeText(StudentFeeStructure.this,
                                getResources().getString(R.string.valid_select_Instal),
                                Toast.LENGTH_SHORT).show();
                        wantToCloseDialog = false;
                    } else if (response == 2) {
                        Toast.makeText(StudentFeeStructure.this,
                                getResources().getString(R.string.valid_paid_amount_grt),
                                Toast.LENGTH_SHORT).show();
                        wantToCloseDialog = false;
                    } else {

                        wantToCloseDialog = true;
                    }

                    // Toast.makeText(StudentFee.this,
                    // "ClassID:"+((MyApplication)getApplication()).ClassID+"Standard:"+((MyApplication)getApplication()).selectedstd+"StudentID: "+((MyApplication)getApplication()).StudentID+"\nDate:"+inst_date+"\nFeesPaid:"+fees+"\nremote_note:"+remark_note,Toast.LENGTH_SHORT).show();

                }
                if (wantToCloseDialog) {
                    if (((MyApplication) getApplication())
                            .isNetworkAvailable()) {
                        // inst_date=instdate.getText().toString();
                        // fesspaid=Integer.parseInt(paid.getText().toString());
                        // remark_note=note.getText().toString();
                        // editfee(String txt_date,String
                        // txt_fees_amt,String txt_sub_name,String
                        // txt_Fee_id,String txt_Sub_id)
                        edit_fees_amt = paid.getText().toString();
                        instdate1 = date.getText().toString();
                        rem1 = note.getText().toString();
                        display();
                        //                new LongOperation2().execute();
                        dialog1.dismiss();
                    }
                }


            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });


        dialog1.show();
        dialog1.setCancelable(true);
        dialog1.setCanceledOnTouchOutside(false);


    }

    public void insert_local_dtabase() {
        /*((MyApplication) getApplication()).dbo.insert_local_fee(
                ((MyApplication) getApplication()).ClassID,
                ((MyApplication) getApplication()).StudentID,
                ((MyApplication) getApplication()).selectedstd,
                ((MyApplication) getApplication()).selectedsubject, fees,
                inst_date, remark_note);*/
        bal2 = bal2 - fesspaid;
        MyApplication.log("","BALANCE 4 = "+bal2);

        String Balance = "" + bal2;
        MyApplication.log("","BALANCE 5 = "+Balance);
//        String FeesID = "0";

        MyApplication.log("","LOCAL DATABASE fees = "+fees);
        MyApplication.log("","LOCAL DATABASE bal2 = "+bal2);
        MyApplication.log("","LOCAL DATABASE inst_date = "+inst_date);
        MyApplication.log("","LOCAL DATABASE payment_status = "+payment_status);



        MyApplication.log("","BALANCE 6 = "+bal2);

        ((MyApplication) getApplication()).dbo.InsertStudentfeetable1(MyApplication.get_session("classid"),
                MyApplication.get_session("studid"),
                MyApplication.get_session("std"),
                "", fees, bal2 + "", inst_date, remark_note, batch, branch, subj, payment_status);
        /*int count = tableLayout.getChildCount();
        for (int i = 1; i < count; i++) {
            tableLayout.removeViewAt(1);
        }*/

        display();
    }


    public void display() {


        int count = tableLayout.getChildCount();
        MyApplication.log("","tableLayout count = "+count);
        for (int i = 1; i < count; i++) {
            tableLayout.removeViewAt(1);

        }
        String ssiid = ((MyApplication) getApplication()).SubjectID;
        tfee = MyApplication.get_session("amt");
        tbal = ((MyApplication) getApplication()).dbo.gettotalbal(MyApplication.get_session("std"), MyApplication.get_session("studid"));
        MyApplication.log("","BALANCE 7 = "+tbal);

        if (tfee.toString() != "") {

            bal2 = Integer.parseInt(tfee.toString());
            MyApplication.log("","BALANCE 8 = "+bal2);

        }
        Log.i("StudentFeeStructure", "student_id :" + MyApplication.get_session("studid"));
        c = ((MyApplication) getApplication()).dbo.getfeereportcount(MyApplication.get_session("studid"), MyApplication.get_session("std"));
        count = Integer.valueOf(c);
        Log.i("count", "resultinfo" + count);
        resultinfo = ((MyApplication) getApplication()).dbo.getstudentfeerecord(MyApplication.get_session("studid"), MyApplication.get_session("std"));
        Log.i("resultinfo", "resultinfo" + resultinfo);
        edit_result = resultinfo;
        for (int len = 0; len < count; len++) {
            ((MyApplication) getApplication()).separated17 = resultinfo
                    .split("\\;");
            ((MyApplication) getApplication()).separated16 = ((MyApplication) getApplication()).separated17[len]
                    .split("\\?");
            MyApplication.log("","im in 1");
            Log.i("count", "resultinfo" + ((MyApplication) getApplication()).separated16.length);
            ((MyApplication) getApplication()).CreateNewRow();
            ((MyApplication) getApplication()).row.setId(len);
            // row.setLayoutParams(new
            // LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));

            FifthCol = ((MyApplication) getApplication())
                    .CreateNewTextView(FifthCol);
            FifthCol.setText(((MyApplication) getApplication()).separated16[4].trim().toString());
            MyApplication.log("","Testing FifthCol = "+((MyApplication) getApplication()).separated16[4].trim().toString());
            FifthCol.setTextColor(Color.GRAY);
            FifthCol.setTextSize(16);
            FifthCol.setTextAppearance(this, R.style.TableTextView);
            FifthCol.setVisibility(View.GONE);
//            // thirdCol.setLayoutParams(new LayoutParams(
//            // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//            ((MyApplication) getApplication()).row.addView(FifthCol);*/ // adding
            // coloumn
            // to
            // row

            Log.i("StudentFeeStructure", "ID" + ((MyApplication) getApplication()).separated16[4].trim().toString());

            thirdCol = ((MyApplication) getApplication()).CreateNewTextView(thirdCol);
            thirdCol.setText(((MyApplication) getApplication()).separated16[3].trim().toString());
            MyApplication.log("","Testing thirdCol = "+((MyApplication) getApplication()).separated16[3].trim().toString());

            thirdCol.setTextColor(Color.GRAY);
            thirdCol.setTextSize(16);
            thirdCol.setTextAppearance(this, R.style.TableTextView);
//              thirdCol.setLayoutParams(new LayoutParams(
//             LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            ((MyApplication) getApplication()).row.addView(thirdCol); //adding
            // coloumn to row
            //
            // forthCol=((MyApplication)getApplication()).CreateNewTextView(forthCol);
            // try {
            // forthCol.setText(f3.format(f1.parse(((MyApplication)getApplication()).separated16[3].trim().toString())));
            // } catch (ParseException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }
            // forthCol.setTextColor(Color.GRAY);
            // //forthCol.setTextSize(16);
            // forthCol.setTextAppearance(this, R.style.TableTextView);
            // // thirdCol.setLayoutParams(new LayoutParams(
            // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            // ((MyApplication)getApplication()).row.addView(forthCol); //adding
            // coloumn to row
            //

            firstCol = ((MyApplication) getApplication())
                    .CreateNewTextView(firstCol);
            firstCol.setText(((MyApplication) getApplication()).separated16[0]
                    .trim().toString());
            MyApplication.log("","Testing firstCol = "+((MyApplication) getApplication()).separated16[0]
                    .trim().toString());

            firstCol.setTextSize(16);
            firstCol.setTextColor(Color.GRAY);
            firstCol.setTextAppearance(this, R.style.TableTextView);
            // firstCol.setLayoutParams(new
            // LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            ((MyApplication) getApplication()).row.addView(firstCol); // adding
            // coloumn
            // to
            // row

            secondCol = ((MyApplication) getApplication())
                    .CreateNewTextView(secondCol);
            secondCol.setText(((MyApplication) getApplication()).separated16[1]
                    .trim().toString());
            MyApplication.log("","Testing secondCol = "+((MyApplication) getApplication()).separated16[1]
                    .trim().toString());
            secondCol.setTextColor(Color.GRAY);
            // secondCol.setTextSize(16);
            secondCol.setTextAppearance(this, R.style.TableTextView);
            // secondCol.setLayoutParams(new
            // LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            ((MyApplication) getApplication()).row.addView(secondCol); // adding
            // coloumn
            // to
            // row

            forthCol = ((MyApplication) getApplication())
                    .CreateNewTextView(forthCol);

            String subname1 = ((MyApplication) getApplication()).dbo.getSubName1(MyApplication.get_session("sb"));
//            forthCol.setText(subname1);//scodeubject

            forthCol.setText(((MyApplication) getApplication()).separated16[6]
                    .trim().toString());
            MyApplication.log("","Testing forthCol = "+((MyApplication) getApplication()).separated16[6]
                    .trim().toString());
            /*tempsubject = ((MyApplication) getApplication()).separated16[5]
                    .trim().toString();*/
            forthCol.setTextColor(Color.GRAY);
            // forthCol.setTextSize(16);
            forthCol.setTextAppearance(this, R.style.TableTextView);

            ((MyApplication) getApplication()).row.addView(forthCol); // adding


            Balance = ((MyApplication) getApplication()).separated16[1].trim()
                    .toString();
            MyApplication.log("","BALANCE 9 = "+Balance);

            remark_note = (((MyApplication) getApplication()).separated16[2]
                    .trim().toString());
            bal2 = Integer.parseInt(Balance.toString());
            MyApplication.log("","BALANCE 10 = "+bal2);

            tableLayout.addView(((MyApplication) getApplication()).row);
            ((MyApplication) getApplication()).row
                    .setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            for (int j = 1; j < tableLayout.getChildCount(); j++) {
                                if (j == (v.getId() + 1)) {
                                    row1 = (TableRow) tableLayout.getChildAt(v
                                            .getId() + 1);
                                    MyApplication.log("","tableLayout row1 = "+row1);

                                    row1.setBackgroundColor(Color.RED);
                                    ((MyApplication) getApplication()).separated17 = edit_result
                                            .split("\\;");
                                    ((MyApplication) getApplication()).separated16 = ((MyApplication) getApplication()).separated17[v
                                            .getId()].split("\\?");
                                    MyApplication.log("","im in 2");

                                    edit_date = ((MyApplication) getApplication()).separated16[3]
                                            .trim().toString();
                                    edit_fees_amt = ((MyApplication) getApplication()).separated16[0]
                                            .trim().toString();
                                    /*edit_sub_name = ((MyApplication) getApplication()).separated16[5]
                                            .trim().toString();*/
                                    edit_fees_id = ((MyApplication) getApplication()).separated16[4]
                                            .trim().toString();
                                    remark.setText("Remark :-"
                                            + ((MyApplication) getApplication()).separated16[2]
                                            .trim().toString());

                                    bal.setText("Balance Fee :-"
                                            + ((MyApplication) getApplication()).separated16[1]
                                            .trim().toString());


                                    payment_type = ((MyApplication) getApplication()).separated16[6].trim().toString();

                                    MyApplication.log("","tableLayout values edit_result= "+edit_result);
                                    MyApplication.log("","tableLayout values edit_fees_amt= "+edit_fees_amt);
                                    MyApplication.log("","tableLayout values edit_fees_id= "+edit_fees_id);
                                    MyApplication.log("","tableLayout values payment_type= "+payment_type);
                                    MyApplication.log("","tableLayout values bal= "+bal.getText().toString());
                                    MyApplication.log("","tableLayout values bal2= "+bal2);


                                    /*edit_subject_id = (((MyApplication) getApplication()).dbo)
                                            .getSubjectID(
                                                    ((MyApplication) getApplication()).ClassID,
                                                    edit_sub_name);*/
                                    // Toast.makeText(StudentFee.this,
                                    // "remote_note : "+((MyApplication)getApplication()).separated16[2].trim().toString()
                                    // +"\nDate : "+edit_date+
                                    // "\nFeesPaid : "+edit_fees_amt +
                                    // "\nBalance : "+((MyApplication)getApplication()).separated16[1].trim().toString()
                                    // +"\nSubject : " + edit_sub_name
                                    // +"\nSubID : "+edit_subject_id+
                                    // "\nFeesID : "+edit_fees_id,Toast.LENGTH_SHORT).show();
                                } else {
                                    row1 = (TableRow) tableLayout.getChildAt(j);
                                    MyApplication.log("","tableLayout row12 = "+row1);

                                    row1.setBackgroundColor(Color.BLACK);
                                }
                            }
                        }
                    });
        }

        ttbal = Integer.parseInt(tfee.toString())
                - Integer.parseInt(tbal.toString());
        MyApplication.log("","BALANCE 11 = "+ttbal);

        bal.setText("Balance Fee:-" + ttbal);
        MyApplication.log("","BALANCE 12 = "+bal);

        MyApplication.log("SAMARTH","ttbal = "+ttbal);
        MyApplication.log("SAMARTH","bal = "+bal);

        remark.setText(getResources().getString(R.string.remark_fee) + remark_note);


    }


//    public void editfee(String txt_date, String txt_fees_amt,
//                        String txt_sub_name, String txt_Fee_id, String txt_Sub_id) {
//
//        fesspaid = 0;
//        LayoutInflater factory = LayoutInflater.from(this);
//
//        // text_entry is an Layout XML file containing two text field to display
//        // in alert dialog
//        final View textEntryView = factory.inflate(R.layout.addfee_dialog, null);
//
//        final TextView instdate = (TextView) textEntryView
//                .findViewById(R.id.paid_date);
//        final EditText paid = (EditText) textEntryView
//                .findViewById(R.id.ammount);
//        final EditText note = (EditText) textEntryView
//                .findViewById(R.id.remark);
//        instdate.setText(txt_date);
//        paid.setText(txt_fees_amt);
//        paid.setInputType(InputType.TYPE_CLASS_NUMBER);
//
//        instdate.setHint("Select Installment Date");
//        paid.setHint("Enter Fees Amount");
//        note.setHint("Enter Remark");
//
//        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
//
//        instdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//
//                // TODO Auto-generated method stub
//                // To show current date in the datepicker
//                Calendar mcurrentDate = Calendar.getInstance();
//                int mYear = mcurrentDate.get(Calendar.YEAR);
//                int mMonth = mcurrentDate.get(Calendar.MONTH);
//                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog mDatePicker;
//                mDatePicker = new DatePickerDialog(StudentFeeStructure.this,
//                        new DatePickerDialog.OnDateSetListener() {
//                            public void onDateSet(DatePicker datepicker,
//                                                  int selectedyear, int selectedmonth,
//                                                  int selectedday) {
//                                // TODO Auto-generated method stub
//								/* Your code to get date and time */
//                                selectedmonth = selectedmonth + 1;
//                                if (selectedday < 10) {
//                                    instdate.setText("0" + selectedyear + "-"
//                                            + selectedmonth + "-" + selectedday);
//                                }
//                                if (selectedmonth < 10) {
//                                    instdate.setText("" + selectedyear + "-0"
//                                            + selectedmonth + "-" + selectedday);
//                                }
//                                if (selectedmonth < 10 && selectedday < 10) {
//                                    instdate.setText("0" + selectedyear + "-0"
//                                            + selectedmonth + "-" + selectedday);
//                                }
//                                if (selectedmonth > 9 && selectedday > 9) {
//                                    instdate.setText("" + selectedyear + "-"
//                                            + selectedmonth + "-" + selectedday);
//                                }
//
//                                // instdate.setText("" + selectedday + "-" +
//                                // selectedmonth + "-" + selectedyear);
//                            }
//                        }, mYear, mMonth, mDay);
//                mDatePicker.setTitle("Select Date");
//                mDatePicker.show();
//            }
//        });
//        alert.setTitle("Edit Fee:")
//                .setView(textEntryView)
//                .setPositiveButton("Update Fee",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,
//                                                int whichButton) {
//                                if (paid.getText().toString().equals("")) {
//                                    Toast.makeText(StudentFeeStructure.this,
//                                            "Enter Installment Ammount",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                                // else if(note.getText().toString().equals(""))
//                                // {
//                                // Toast.makeText(StudentFee.this,
//                                // "Enter Remark",Toast.LENGTH_SHORT).show();
//                                //
//                                // }
//                                //
//                                else if (instdate.getText().toString()
//                                        .equals("")) {
//                                    Toast.makeText(StudentFeeStructure.this,
//                                            "Select Installment Date",
//                                            Toast.LENGTH_SHORT).show();
//
//                                } else if (Integer.parseInt(paid.getText()
//                                        .toString()) > ttbal) {
//                                    Toast.makeText(
//                                            StudentFeeStructure.this,
//                                            "Installment Ammount Greater Than Balance Fees",
//                                            Toast.LENGTH_SHORT).show();
//
//                                } else if (((MyApplication) getApplication())
//                                        .isNetworkAvailable() == false) {
//                                    // Toast.makeText(StudentFee.this,
//                                    // "No Internet Connection",Toast.LENGTH_SHORT).show();
//                                    inst_date = instdate.getText().toString();
//                                    fesspaid = Integer.parseInt(paid.getText()
//                                            .toString());
//                                    remark_note = note.getText().toString();
//                                    fees = paid.getText().toString();
//                                    // insert_local_dtabase();
//                                    // finish();
//                                } else {
//
//                                }
//
//                            }// onclick
//                        }).setNegativeButton("Cancel", null);
//        final AlertDialog dialog = alert.create();
//        dialog.show();
//        // Overriding the handler immediately after show is probably a better
//        // approach than OnShowListener as described below
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Boolean wantToCloseDialog = false;
//                        // Do stuff, possibly set wantToCloseDialog to true
//                        // then...
//                        if (paid.getText().toString().equals("")) {
//                            Toast.makeText(StudentFeeStructure.this,
//                                    "Enter Installment Ammount",
//                                    Toast.LENGTH_SHORT).show();
//                            wantToCloseDialog = false;
//                        } else if (instdate.getText().toString().equals("")) {
//                            Toast.makeText(StudentFeeStructure.this,
//                                    "Select Installment Date",
//                                    Toast.LENGTH_SHORT).show();
//                            wantToCloseDialog = false;
//                        } else {
//                            inst_date = instdate.getText().toString();
//                            fesspaid = Integer.parseInt(paid.getText()
//                                    .toString());
//                            remark_note = note.getText().toString();
//                            fees = paid.getText().toString();
//                            int response = (((MyApplication) getApplication()).dbo)
//                                    .insertEditedFee(
//                                            inst_date,
//                                            edit_fees_id,
//                                            fees,
//                                            remark_note,
//                                            MyApplication.get_session("studid"),
//                                            MyApplication.get_session("std"),
//                                            MyApplication.get_session("classid"));
//
//                            display();
//                            if (response == 1) {
//                                Toast.makeText(StudentFeeStructure.this,
//                                        "Please Enter Valid Amount",
//                                        Toast.LENGTH_SHORT).show();
//                                wantToCloseDialog = false;
//                            } else if (response == 2) {
//                                Toast.makeText(StudentFeeStructure.this,
//                                        "Paid Amount is greater than balance",
//                                        Toast.LENGTH_SHORT).show();
//                                wantToCloseDialog = false;
//                            } else {
//
//                                wantToCloseDialog = true;
//                            }
//
//                            // Toast.makeText(StudentFee.this,
//                            // "ClassID:"+((MyApplication)getApplication()).ClassID+"Standard:"+((MyApplication)getApplication()).selectedstd+"StudentID: "+((MyApplication)getApplication()).StudentID+"\nDate:"+inst_date+"\nFeesPaid:"+fees+"\nremote_note:"+remark_note,Toast.LENGTH_SHORT).show();
//
//                        }
//                        if (wantToCloseDialog) {
//                            if (((MyApplication) getApplication())
//                                    .isNetworkAvailable()) {
//                                // inst_date=instdate.getText().toString();
//                                // fesspaid=Integer.parseInt(paid.getText().toString());
//                                // remark_note=note.getText().toString();
//                                // editfee(String txt_date,String
//                                // txt_fees_amt,String txt_sub_name,String
//                                // txt_Fee_id,String txt_Sub_id)
//                                edit_fees_amt = paid.getText().toString();
//                                instdate1 = instdate.getText().toString();
//                                rem1 = note.getText().toString();
//                                display();
//                                //                new LongOperation2().execute();
//                                dialog.dismiss();
//                            }
//                        }
//                        // else dialog stays open. Make sure you have an obvious
//                        // way to close the dialog especially if you set
//                        // cancellable to false.
//                    }
//                });
//        dialog.setCanceledOnTouchOutside(false);
//
//    }


    @Override
    public void onBackPressed() {

        //Intent i = new Intent(StudentFeeStructure.this,StudentListInFeeMgmt.class);
        Intent i = new Intent(StudentFeeStructure.this, ActivityFeeManagement.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        llBuy.setVisibility(View.GONE);
        lin2.setVisibility(View.VISIBLE);

        Log.d("onBillingError", "> Success - onProductPurchased");
    }

    @Override
    public void onPurchaseHistoryRestored() {
        for(String sku : bp.listOwnedSubscriptions())
        {
            Log.d("onBillingError", "Owned Subscription: " + sku);
            Log.d("onBillingError", "> Success - getSubscriptionTransactionDetails");
            if (bp.isSubscribed(PRODUCT_ID)) {
                llBuy.setVisibility(View.GONE);
                lin2.setVisibility(View.VISIBLE);
                Log.d("onBillingError", "> Success - True");
            } else {
                llBuy.setVisibility(View.VISIBLE);
                lin2.setVisibility(View.GONE);
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
}