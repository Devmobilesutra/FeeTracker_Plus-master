package com.mobilesutra.feetrackerclass;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesutra.feetrackerclass.ErrorHandler.ExceptionHandler;
import com.mobilesutra.feetrackerclass.activities.ActivityDashboard;

//
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class InsertNotice extends Activity {
    TextView sdate;
    TextView title_branch;
    EditText et;
    Button b, StartDate;
    int check;
//    HttpClient mClient, mClient1;
    static String msgsend = "success";
//    HttpPost httppost, httppost1;
    String response111 = "", usermono;
//    ArrayList<NameValuePair> SendingData;
////    ResponseHandler<String> ResponseHandler1, ResponseHandler2;
////    UrlEncodedFormEntity EntityToSend, EntityToSend1;
//    BasicNameValuePair NameAndValue, NameAndValue1, NameAndValue2,
//            NameAndValue3, NameAndValue4, NameAndValue5, NameAndValue6,
//            NameAndValue7, NameAndValue8;
    StringBuilder sbforstartdate;
    static final int DATE_DIALOG_ID = 999;
    static final int DATE_DIALOG_ID1 = 1000;
    SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat f2 = new SimpleDateFormat("yyyy-M-dd");
    String edate1 = "",response12,response13;

    RadioButton sendviamobile, sendviaserver, donotsend, sendselective;
    int smsflag = 0;
    int textlength, msgcount = 1;
    TextView charcount;



    LinkedHashMap<String, String> lhm_branch = new LinkedHashMap<String, String>();// Branch
    LinkedHashMap<String, String> lhm_std = new LinkedHashMap<String, String>();// Std
    LinkedHashMap<String, String> lhm_batch = new LinkedHashMap<String, String>();// Batch
    LinkedHashMap<String, String> lhm_sub = new LinkedHashMap<String, String>();// Subjects

    LinkedHashMap<String, String> lhm_checkedSub = new LinkedHashMap<String, String>();// Subjects
    ArrayList<String> my_array, list, fee_array;
    Context context = this;
    RadioGroup.LayoutParams rprms, rprms1;
    LinearLayout rel_branchchild, rel_sub;
    RelativeLayout rel_std, rel_batch, rel_subject;
    RadioGroup rgp, rgp_std, rgp_batch;
    RadioButton radioButton, radioButtonstd, radioButtonbatch;
    String selection_branch, selection_std, selection_batch;
    int radioId = -1, radioId1 = -1, radioId2 = -1;
    ProgressDialog dialog1;
    String result = "";
    int checkedid = 0;
    String rowid;
    int result1 = 0;
    String subject_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.insert_notice);

        rel_branchchild = (LinearLayout) findViewById(R.id.rel_branchchild);
        rgp = (RadioGroup) findViewById(R.id.radiogroup);


        Typeface roboto_light =  Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
        Typeface roboto_reguler =  Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");



        TextView title = (TextView) findViewById(R.id.title);
        TextView title_batch = (TextView) findViewById(R.id.title_batch);
        TextView title_sub = (TextView) findViewById(R.id.title_sub);
        TextView title_std = (TextView) findViewById(R.id.title_std);
        TextView count = (TextView) findViewById(R.id.noticecharcount);
        EditText notice = (EditText) findViewById(R.id.notice);

        TextView txt_user = (TextView) findViewById(R.id.txt_user);
        txt_user.setText(MyApplication.get_session("classname"));

        rel_std = (RelativeLayout) findViewById(R.id.rel_std);
        rgp_std = (RadioGroup) findViewById(R.id.radiogroup_std);
        title_branch = (TextView) findViewById(R.id.title_branch);
        rel_batch = (RelativeLayout) findViewById(R.id.rel_batch);
        rgp_batch = (RadioGroup) findViewById(R.id.radiogroup_batch);
        b = (Button) findViewById(R.id.insert);
        rel_sub = (LinearLayout) findViewById(R.id.rel_sub);
        rel_subject = (RelativeLayout) findViewById(R.id.rel_subject);


        txt_user.setTypeface(roboto_reguler);
        title.setTypeface(roboto_reguler);
        title_batch.setTypeface(roboto_reguler);
        title_std.setTypeface(roboto_reguler);
        title_branch.setTypeface(roboto_reguler);
        b.setTypeface(roboto_reguler);
        count.setTypeface(roboto_light);
        notice.setTypeface(roboto_light);


        lhm_branch = ((MyApplication) getApplication()).dbo
                .getBranchWithActiveFlag(MyApplication.get_session("classid"));

        Log.i("tag", "lhm_branch" + lhm_branch);
        if (lhm_branch.size() == 0) {
            title_branch.setVisibility(View.GONE);
            ErrorDialog("Please go to profile page  and active branch");
        }else
        {

        my_array = new ArrayList<String>();
        list = new ArrayList<String>();
        fee_array = new ArrayList<String>();

        final Iterator<String> cursor = lhm_branch.keySet().iterator();
        while (cursor.hasNext()) {
            final String key = cursor.next();
            /* print the key */
            Log.i("tag", "key" + key);

            radioButton = new RadioButton(context);
            radioButton.setTextColor(getResources().getColor(R.color.white));
            radioButton.setText(key);
            // radioButton.setId("rbtn"+i);
            // rel_branchchild.addView(rgp);
            rprms = new RadioGroup.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.WRAP_CONTENT);
            rgp.addView(radioButton, rprms);

        }


        rgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // rgp_std.removeAllViews();
                // rgp_std.clearCheck();

                if (rgp.getCheckedRadioButtonId() != -1) {

                    int id = rgp.getCheckedRadioButtonId();
                    View radioButtonview = rgp.findViewById(id);
                    radioId = rgp.indexOfChild(radioButtonview);
                    Log.i("tag", "radioId" + radioId);

                    for (int i = 0; i < rgp.getChildCount(); i++) {
                        radioButton = (RadioButton) rgp.getChildAt(i);
                        // int id12 = radioButton.getId();
                        if (radioButton.getId() == checkedId) {
                            String text = radioButton.getText().toString();
                            // Log.i("tag", "selection" + text);

                            selection_branch = text;

                            // Log.i("tag", "selection branch" +
                            // selection_branch);
                        }
                    }

                    // radioButton = (RadioButton) rgp.getChildAt(checkedId);
                    // selection_branch = (String) radioButton.getText();
                    //
                    Log.i("tag", "selection" + selection_branch);

                    if (radioId == -1)
                        rel_std.setVisibility(View.GONE);
                    else {
                        rel_std.setVisibility(View.VISIBLE);

                        // list = new ArrayList<String>();

                        Log.i("tag",
                                "branch id is "
                                        + lhm_branch.get(selection_branch));

                        lhm_std.clear();

                        lhm_std = ((MyApplication) getApplication()).dbo
                                .getStandard(
                                        MyApplication.get_session("classid"),
                                        lhm_branch.get(selection_branch));

                        Log.i("tag", "lhm_std" + lhm_std);

                        rgp_std.removeAllViews();

                        final Iterator<String> cursor = lhm_std.keySet()
                                .iterator();
                        while (cursor.hasNext()) {
                            final String key = cursor.next();
                            /* print the key */
                            Log.i("tag", "key" + key);

                            radioButtonstd = new RadioButton(context);
                            radioButtonstd.setTextColor(getResources().getColor(R.color.white));
                            radioButtonstd.setText(key);
                            // radioButton.setId("rbtn"+i);
                            // rel_branchchild.addView(rgp);
                            rprms = new RadioGroup.LayoutParams(
                                    ActionBar.LayoutParams.WRAP_CONTENT,
                                    ActionBar.LayoutParams.WRAP_CONTENT);
                            rgp_std.addView(radioButtonstd, rprms);

                        }

                    }

                }
            }
        });

        rgp_std.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                if (rgp_std.getCheckedRadioButtonId() != -1) {

                    int id = rgp_std.getCheckedRadioButtonId();
                    View radioButtonview = rgp_std.findViewById(id);
                    radioId1 = rgp_std.indexOfChild(radioButtonview);
                    Log.i("tag", "radioId1" + radioId1);

                    for (int i = 0; i < rgp_std.getChildCount(); i++) {
                        radioButtonstd = (RadioButton) rgp_std.getChildAt(i);
                        // int id12 = radioButton.getId();
                        if (radioButtonstd.getId() == checkedId) {
                            String text = radioButtonstd.getText().toString();
                            // Log.i("tag", "selection" + text);

                            selection_std = text;

                            // Log.i("tag", "selection branch" +
                            // selection_branch);
                        }
                    }

                    // radioButton = (RadioButton) rgp.getChildAt(checkedId);
                    // selection_branch = (String) radioButton.getText();
                    //
                    Log.i("tag", "selection_std" + selection_std);

                    if (radioId1 == -1)
                        rel_batch.setVisibility(View.GONE);
                    else {
                        rel_batch.setVisibility(View.VISIBLE);

                        // list = new ArrayList<String>();

                        Log.i("tag",
                                "standard id is " + lhm_std.get(selection_std));

                        lhm_batch.clear();

                        lhm_batch = ((MyApplication) getApplication()).dbo
                                .getBatch(MyApplication.get_session("classid"),
                                        lhm_branch.get(selection_branch),
                                        lhm_std.get(selection_std));

                        Log.i("tag", "lhm_batch" + lhm_batch);

                        rgp_batch.removeAllViews();

                        final Iterator<String> cursor = lhm_batch.keySet()
                                .iterator();
                        while (cursor.hasNext()) {
                            final String key = cursor.next();
							/* print the key */
                            Log.i("tag", "key" + key);

                            radioButtonbatch = new RadioButton(context);
                            radioButtonbatch.setTextColor(getResources().getColor(R.color.white));
                            radioButtonbatch.setText(key);
                            // radioButton.setId("rbtn"+i);
                            // rel_branchchild.addView(rgp);
                            rprms = new RadioGroup.LayoutParams(
                                    ActionBar.LayoutParams.WRAP_CONTENT,
                                    ActionBar.LayoutParams.WRAP_CONTENT);
                            rgp_batch.addView(radioButtonbatch, rprms);

                        }

                    }

                }
            }
        });

        rgp_batch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                if (rgp_batch.getCheckedRadioButtonId() != -1) {

                    int id = rgp_batch.getCheckedRadioButtonId();
                    View radioButtonview = rgp_batch.findViewById(id);
                    radioId2 = rgp_batch.indexOfChild(radioButtonview);
                    Log.i("tag", "radioId2" + radioId2);

                    for (int i = 0; i < rgp_batch.getChildCount(); i++) {
                        radioButtonbatch = (RadioButton) rgp_batch
                                .getChildAt(i);
                        // int id12 = radioButton.getId();
                        if (radioButtonbatch.getId() == checkedId) {
                            String text = radioButtonbatch.getText().toString();
                            // Log.i("tag", "selection" + text);

                            selection_batch = text;

                            // Log.i("tag", "selection branch" +
                            // selection_branch);
                        }
                    }

                    // radioButton = (RadioButton) rgp.getChildAt(checkedId);
                    // selection_branch = (String) radioButton.getText();
                    //
                    Log.i("tag", "selection_batch" + selection_batch);

                    if (radioId2 == -1) {
                        rel_subject.setVisibility(View.GONE);
                        rel_sub.setVisibility(View.GONE);
                    } else {

                        rel_subject.setVisibility(View.VISIBLE);
                        rel_sub.setVisibility(View.VISIBLE);

                        // list = new ArrayList<String>();

                        Log.i("tag",
                                "standard id is "
                                        + lhm_batch.get(selection_batch));

                        lhm_sub.clear();

                        lhm_sub = ((MyApplication) getApplication()).dbo
                                .getSubject(MyApplication.get_session("classid"),
                                        lhm_branch.get(selection_branch),
                                        lhm_std.get(selection_std),
                                        lhm_batch.get(selection_batch));

                        Log.i("tag", "lhm_sub" + lhm_sub);

                        result = "";
                        checkedid = 0;
                        lhm_checkedSub.clear();
                        rel_sub.removeAllViews();


                        final Iterator<String> cursor = lhm_sub.keySet()
                                .iterator();
                        while (cursor.hasNext()) {
                            final String key = cursor.next();
							/* print the key */
                            Log.i("tag", "key" + key);

                            lhm_checkedSub.put(key, checkedid + "");

                            final CheckBox ch;
                            ch = new CheckBox(context);
                            ch.setTextColor(getResources().getColor(R.color.white));
                            ch.setText(key);

                            // radioButton.setId("rbtn"+i);
                            // rel_branchchild.addView(rgp);
                            rprms = new RadioGroup.LayoutParams(
                                    ActionBar.LayoutParams.WRAP_CONTENT,
                                    ActionBar.LayoutParams.WRAP_CONTENT);
                            rel_sub.addView(ch, rprms);


                            ch.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub

                                    if (ch.isChecked()) {

                                        Log.i("tag", "In If  ");

                                        checkedid = 1;
                                        String checkedSub = ch.getText().toString();
                                        Log.i("tag", "checkedSub:  " + checkedSub);

                                        subject_id = ((MyApplication) getApplication()).dbo.getSubjectId(checkedSub);
                                        Log.i("tag", "subject_id:  " + subject_id);

                                        result1 += checkedid * (int) Math.pow(10, Integer.parseInt(subject_id));
                                        Log.i("tag", "final result1:  " + result1);
                                         /*result1 += (int) Math.pow(10, Integer.parseInt(subject_id));
                                        Log.i("tag", "result1:  " + result1);*/
                                        // lhm_checkedSub.put(ch.getText().toString(),
                                        // checkedid+"");
                                    } else {

                                        Log.i("tag", "In else  ");
                                        checkedid = 0;
                                        String checkedSub = ch.getText().toString();
                                        Log.i("tag", "UncheckedSub:  " + checkedSub);
                                        subject_id = ((MyApplication) getApplication()).dbo.getSubjectId(checkedSub);
                                        Log.i("tag", "subject_id: unchecked  " + subject_id);
                                        result1 -= (int) Math.pow(10, Integer.parseInt(subject_id));
                                        Log.i("tag", "final result1:  " + result1);
                                    }

                                    Log.i("tag", "outer result1:  " + result1);
                                    lhm_checkedSub.put(ch.getText().toString(),
                                            checkedid + "");
                                    // Log.i("tag", "lhm_checkedSub"
                                    // + lhm_checkedSub);
                                }

                            });

                        }

                    }

                }
            }
        });


        // By Ganesh 10-09-2015

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // Spinner s1 = (Spinner)findViewById(R.id.spinner2);
        // batche=(Button)findViewById(R.id.spinner2);
        et = (EditText) findViewById(R.id.notice);


        charcount = (TextView) findViewById(R.id.noticecharcount);
//        sdate = (TextView) findViewById(R.id.sdate);
        //       StartDate = (Button) findViewById(R.id.StartDate);
       /* mClient = new DefaultHttpClient(); // Object of DefaultHttpClient
        mClient1 = new DefaultHttpClient();

        httppost = new HttpPost(
                "http://smartbridges.co.in/Android/InsertNoticeFromAndroid.php");
        httppost1 = new HttpPost(
                "http://smartbridges.co.in/Android/Teacher_SendNoticeSms.php");

        ResponseHandler1 = new BasicResponseHandler();
        ResponseHandler2 = new BasicResponseHandler();*/

        RequestBody formBody = new FormBody.Builder()

                .build();
//  commit tejas
//        response12 = ((MyApplication) getApplication()).post_server_call("http://smartbridges.co.in/Android/InsertNoticeFromAndroid.php", formBody);
//
//        response13 = ((MyApplication) getApplication()).post_server_call("http://smartbridges.co.in/Android/Teacher_SendNoticeSms.php", formBody);

        Log.i("tag", "Response:" + response111);

        // Toast.makeText(InsertNotice.this,""
        // +((MyApplication)getApplication()).selectedbranch,
        // Toast.LENGTH_SHORT).show();
        // selectedbatches = new ArrayList<String>();
        // List<String> list2=new ArrayList<String>();
        // //list2=((MyApplication)getApplication()).dbo.getBatchList1(((MyApplication)getApplication()).ClassID,((MyApplication)getApplication()).selectedstd);
        // list2.add("All");
        // list2.add("Morning");
        // list2.add("Afternoon");
        // list2.add("Evening");
        // batches=list2.toArray(new String[list2.size()]);

        // ArrayAdapter<String> adapter2 = new
        // ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,list2);
        // adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((MyApplication) getApplication()).selecteddate = new SimpleDateFormat(
                "yyyy-MM-dd").format(new Date());

        et.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                et.addTextChangedListener(new TextWatcher() {

                    public void afterTextChanged(Editable s) {

                    }

                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {

                    }

                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        textlength = et.getText().length() + 35;
                        float len = textlength;
                        float ms = 0;
                        if (textlength > 160) {
                            ms = len / 160;

                            msgcount = (int) Math.ceil(ms);

                            // msgcount=(textlength/50);

                        } else {
                            msgcount = 1;
                        }
                        charcount.setText("Char Count :" + textlength
                                + "  Message Count :" + msgcount);
                        ((MyApplication) getApplication()).Messagecount = msgcount;
                    }
                });
            }
        });
        // batche.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v)
        // {
        // switch(v.getId()) {
        // case R.id.spinner2:
        // showSelectColoursDialog();
        // break;
        //
        // default:
        // break;
        // }// TODO Auto-generated method stub
        //
        // }
        // });
        //
        // sdate.setVisibility(View.INVISIBLE);
        // sbforstartdate=((MyApplication)getApplication()).setEndCurrentDateOnView();

        // StartDate.setOnClickListener(new OnClickListener()
        // {
        //
        //
        //
        // public void onClick(View arg0)
        // {
        //
        // showDialog(DATE_DIALOG_ID);
        // }
        //
        // });

        b.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (et.getText().toString().equals("")) {
                    Toast.makeText(InsertNotice.this, "Enter Notice...",
                            Toast.LENGTH_SHORT).show();
                } else if (smsflag == 3) {

                    Intent in = new Intent(InsertNotice.this,
                            SelectiveNotice.class);
                    // in.putExtra("NoticeTitle",noticetitle.getText().toString());
                    in.putExtra("Notice", et.getText().toString());
                    in.putExtra("Branch", lhm_branch.get(selection_branch));
                    in.putExtra("Standard", lhm_std.get(selection_std));
                    in.putExtra("Batch", lhm_batch.get(selection_batch));


                    startActivity(in);
                    finish();
                } else {

                    // confirmDialog();
                    new HTTPSendSms().execute();
                }
            }
        });
    }

    }

    // protected Dialog onCreateDialog(int id)
    // {
    //
    //
    // switch (id) {
    // case DATE_DIALOG_ID:
    // // set date picker as current date
    //
    // return new DatePickerDialog(this, datePickerListener,
    // ((MyApplication)getApplication()).year,
    // ((MyApplication)getApplication()).month,((MyApplication)getApplication()).day);
    //
    // }
    // return null;
    // }
    // private DatePickerDialog.OnDateSetListener datePickerListener = new
    // DatePickerDialog.OnDateSetListener()
    // {
    //
    // //when dialog box is closed, below method will be called.
    // public void onDateSet(DatePicker view, int selectedYear,
    // int selectedMonth, int selectedDay)
    // {
    // ((MyApplication)getApplication()).year = selectedYear;
    // ((MyApplication)getApplication()).month = selectedMonth;
    // ((MyApplication)getApplication()).day = selectedDay;
    //
    //
    // // set selected date into textview
    // //sdate.setText(new
    // StringBuilder().append(((MyApplication)getApplication()).year)
    // //
    // .append("-").append(((MyApplication)getApplication()).month+1).append("-").append(((MyApplication)getApplication()).day)
    // // .append(" "));
    //
    // //StartDate.setText(new
    // StringBuilder().append(((MyApplication)getApplication()).year)
    // //
    // .append("-").append(((MyApplication)getApplication()).month+1).append("-").append(((MyApplication)getApplication()).day)
    // // .append(" "));
    //
    // try
    // {
    // edate1=f1.format(f2.parse(sdate.getText().toString()));
    // ((MyApplication)getApplication()).selecteddate=edate1;
    // //Toast.makeText(FirstPage.this,""+((MyApplication)getApplication()).selecteddate,
    // Toast.LENGTH_LONG).show();
    //
    // }
    // catch (ParseException e)
    // {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    //
    // //((MyApplication)getApplication()).startdate=sdate.getText().toString();
    //
    // // set selected date into datepicker also
    // //dpResult.init(year, month, day, null);
    // }
    // };

    public void OnViaMobileClick(View v) {
        sendviamobile = (RadioButton) v;
        // Toast.makeText(InsertNotice.this,sendviamobile.getText() +
        // " was chosen.",
        // Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SMS Sending Option!")
                .setMessage(
                        "You have chosen send SMS via Mobile option..\nNotify that your mobile balance is used to send SMS.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // Yes button clicked, do something
                    }
                }) // Do nothing on no
                .show();
        smsflag = 1;
    }

    public void OnViaServerClick(View v) {
        sendviaserver = (RadioButton) v;
        // Toast.makeText(InsertNotice.this,sendviaserver.getText() +
        // " was chosen.", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SMS Sending Option!")
                .setMessage(
                        "You have chosen send SMS via Server option..\nNotify that you have using server to send SMS.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // Yes button clicked, do something
                    }
                }) // Do nothing on no
                .show();
        smsflag = 2;
    }

    public void OnDonotSendClick(View v) {
        donotsend = (RadioButton) v;
        // Toast.makeText(InsertNotice.this,donotsend.getText() +
        // " was chosen.", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SMS Sending Option!")
                .setMessage("You have chosen do not send SMS option..")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // Yes button clicked, do something
                    }
                }) // Do nothing on no
                .show();
        smsflag = 0;
    }

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

    public void confirmDialog() {

        if (check != 1) {
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            final String data4 = et.getText().toString();

            builder1.setTitle("Check  Message\n");
            builder1.setMessage("Date:"
                    + ((MyApplication) getApplication()).selecteddate
                    + "\nStd:" + ((MyApplication) getApplication()).selectedstd
                    + "\nMessage Count :" + msgcount
                    + "\n\nEnter 10 Digit Mo No");
            builder1.setView(input);
            builder1.setPositiveButton("Send",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String value = input.getText().toString().trim();
                            Toast.makeText(getApplicationContext(),
                                    value + "  msg " + data4.toString(),
                                    Toast.LENGTH_SHORT).show();

                            if (value.length() == 10) {


                               /* HttpClient httpclient = new DefaultHttpClient();
                                HttpPost httppost0 = new HttpPost(
                                        "http://smartbridges.co.in/Android/Ckecksms.php");


                                    usermono = value.trim();
                                    ArrayList personalnameValuepairs = new ArrayList(
                                            7);
                                    BasicNameValuePair mobile = new BasicNameValuePair(
                                            "Mo", value.trim());
                                    BasicNameValuePair massege1 = new BasicNameValuePair(
                                            "msg", data4.toString().trim());
                                    BasicNameValuePair date = new BasicNameValuePair(
                                            "Date",
                                            ((MyApplication) getApplication()).selecteddate);
                                    BasicNameValuePair clid = new BasicNameValuePair(
                                            "ClassID",
                                            ((MyApplication) getApplication()).ClassID);
                                    BasicNameValuePair std = new BasicNameValuePair(
                                            "Standard",
                                            ((MyApplication) getApplication()).selectedstd);
                                    BasicNameValuePair branch = new BasicNameValuePair(
                                            "Branch",
                                            ((MyApplication) getApplication()).selectedbranch);
                                    BasicNameValuePair batch = new BasicNameValuePair(
                                            "Batch",
                                            ((MyApplication) getApplication()).multipleselectedbatches
                                                    .toString());

                                    Log.i("mobilesutra.feetracker",
                                            "Mobile  no send to server is "
                                                    + mobile);
                                    Log.i("mobilesutra.feetracker",
                                            "message is " + massege1);
                                    Log.i("mobilesutra.feetracker", "Date is "
                                            + date);
                                    Log.i("mobilesutra.feetracker",
                                            "Class ID is " + clid);
                                    Log.i("mobilesutra.feetracker",
                                            "Standard is " + std);
                                    Log.i("mobilesutra.feetracker",
                                            "Branch is " + branch);
                                    Log.i("mobilesutra.feetracker", "Batch is "
                                            + batch);

                                    personalnameValuepairs.add(mobile);
                                    personalnameValuepairs.add(massege1);
                                    personalnameValuepairs.add(date);
                                    personalnameValuepairs.add(clid);
                                    personalnameValuepairs.add(std);
                                    personalnameValuepairs.add(branch);
                                    personalnameValuepairs.add(batch);

                                    Log.i("mobilesutra.feetracker",
                                            "value in personalnameValuepairs is "
                                                    + personalnameValuepairs);

                                    UrlEncodedFormEntity dataentitytosend = new UrlEncodedFormEntity(
                                            personalnameValuepairs);

                                    Log.i("mobilesutra.feetracker",
                                            "Data to send on server is "
                                                    + dataentitytosend);

                                    httppost0.setEntity(dataentitytosend);

                                    Log.i("mobilesutra.feetracker",
                                            "Value in httppost is " + httppost0);

                                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                                    response111 = httpclient.execute(httppost0,
                                            responseHandler);*/


                                RequestBody formBody = new FormBody.Builder()
                                        .add("Mo", value.trim())
                                        .add("msg", data4.toString().trim())
                                        .add("Date", ((MyApplication) getApplication()).selecteddate)
                                        .add("ClassID", MyApplication.get_session("classid"))
                                        .add("Standard", selection_std)
                                        .add("Branch", selection_branch)
                                        .add("Batch", selection_batch)


                                        .build();

                                response111 = ((MyApplication) getApplication()).post_server_call("http://smartbridges.co.in/Android/Ckecksms.php", formBody);
                                    Log.i("tag", "Response:" + response111);


                                if (response111.toString().trim()
                                        .equals("Send")) {
                                    Toast.makeText(getApplicationContext(),
                                            "Message Sent Successfully",
                                            Toast.LENGTH_SHORT).show();
                                    check = 1;
                                }


                        }

                        else

                        {
                            Toast.makeText(getApplicationContext(),
                                    "Enter Valid 10 Digit Mobile Number",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
        }).setNegativeButton("No", null) // Do nothing on no
                    .show();
        }

        if (check == 1) {
            String str = "Date:"
                    + ((MyApplication) getApplication()).selecteddate
                    + "\nStd:" + ((MyApplication) getApplication()).selectedstd
                    + "\nMessage Count :" + msgcount;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Inserting Notice On Server..!")
                    .setMessage(str + "\nDo You Confirm ?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    new LongOperation().execute();
                                    if (msgsend == "success") {
                                        Toast.makeText(InsertNotice.this,
                                                "Data Sent Successfully!!",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(
                                                InsertNotice.this,
                                                "Data Sending Failed!!  "
                                                        + msgsend,
                                                Toast.LENGTH_LONG).show();
                                    }

                                    // //Yes button clicked, do something
                                    //
                                    //
                                    //
                                    // String data3=et.getText().toString();
                                    // SendingData =new
                                    // ArrayList<NameValuePair>();
                                    // for(int
                                    // i=0;i<FirstPage.selectedsubjects.size();i++)
                                    // {
                                    // String
                                    // subid=((MyApplication)getApplication()).dbo.getSubjectID(((MyApplication)getApplication()).ClassID,FirstPage.selectedsubjects.get(i));
                                    // SendingData.add( new
                                    // BasicNameValuePair("SubjectID[]",subid));
                                    // }
                                    // NameAndValue = new
                                    // BasicNameValuePair("Data",data3);
                                    // NameAndValue1 = new
                                    // BasicNameValuePair("Date",((MyApplication)getApplication()).selecteddate);
                                    // NameAndValue2 = new
                                    // BasicNameValuePair("ClassID",((MyApplication)getApplication()).ClassID);
                                    // NameAndValue3 = new
                                    // BasicNameValuePair("Standard",((MyApplication)getApplication()).selectedstd);
                                    // //NameAndValue4 = new
                                    // BasicNameValuePair("SubjectID",subid);
                                    // NameAndValue5 = new
                                    // BasicNameValuePair("Branch",((MyApplication)getApplication()).selectedbranch);
                                    // NameAndValue6 = new
                                    // BasicNameValuePair("Batch", batchh);
                                    // NameAndValue7 = new
                                    // BasicNameValuePair("CurDate",((MyApplication)getApplication()).CurDate);
                                    // NameAndValue8 = new
                                    // BasicNameValuePair("usmo",usermono);
                                    //
                                    // SendingData.add(NameAndValue);
                                    // SendingData.add(NameAndValue1);
                                    // SendingData.add(NameAndValue2);
                                    // SendingData.add(NameAndValue3);
                                    // //SendingData.add(NameAndValue4);
                                    // SendingData.add(NameAndValue5);
                                    // SendingData.add(NameAndValue6);
                                    // SendingData.add(NameAndValue7);
                                    // SendingData.add(NameAndValue8);
                                    // String Response="";
                                    //
                                    //
                                    // // try
                                    // // {
                                    // // EntityToSend=new
                                    // UrlEncodedFormEntity(SendingData);
                                    // // httppost1.setEntity(EntityToSend);
                                    // // Response =
                                    // mClient.execute(httppost1,ResponseHandler1);
                                    // // if(Response.equals("success"))
                                    // // {
                                    // //
                                    // // }
                                    // // else
                                    // // {
                                    // // Toast.makeText(InsertNotice.this,
                                    // "Data Sending Failed!!",Toast.LENGTH_SHORT).show();
                                    // // }
                                    // //
                                    // // }
                                    // // catch (ClientProtocolException e)
                                    // // {
                                    // // // TODO Auto-generated catch block
                                    // // e.printStackTrace();
                                    // // }
                                    // // catch (IOException e)
                                    // // {
                                    // // // TODO Auto-generated catch block
                                    // // e.printStackTrace();
                                    // // }
                                    //
                                    // if(smsflag==1)
                                    // {
                                    // int c=sendLongSMS();
                                    // if(c==11)
                                    // {
                                    // AlertDialog.Builder builder = new
                                    // AlertDialog.Builder(InsertNotice.this);
                                    // builder
                                    // .setTitle("Internet Speed Is Slow...!")
                                    // .setMessage("Do You Want To Exit?")
                                    // .setIcon(android.R.drawable.ic_dialog_alert)
                                    // .setPositiveButton("Try Again", new
                                    // DialogInterface.OnClickListener()
                                    // {
                                    // public void onClick(DialogInterface
                                    // dialog, int which)
                                    // {
                                    // sendLongSMS();
                                    // //Yes button clicked, do something
                                    // }
                                    // })
                                    // .setNegativeButton("Exit", new
                                    // DialogInterface.OnClickListener()
                                    // {
                                    // public void onClick(DialogInterface
                                    // dialog, int which)
                                    // {
                                    //
                                    //
                                    // Toast.makeText(InsertNotice.this,
                                    // "Exit button pressed",
                                    // Toast.LENGTH_SHORT).show();
                                    // finish();
                                    // System.exit(0);
                                    // }
                                    // }) //Do nothing on no
                                    // .show();
                                    //
                                    // }
                                    // }
                                    // else
                                    // if(smsflag==2)
                                    // {
                                    // String Response1="";
                                    // try
                                    // {
                                    // EntityToSend1=new
                                    // UrlEncodedFormEntity(SendingData);
                                    // httppost1.setEntity(EntityToSend1);
                                    // Response1 =
                                    // mClient1.execute(httppost1,ResponseHandler2);
                                    // }
                                    // catch (UnsupportedEncodingException e) {
                                    // // TODO Auto-generated catch block
                                    // e.printStackTrace();
                                    // }catch (ClientProtocolException e) {
                                    // // TODO Auto-generated catch block
                                    // e.printStackTrace();
                                    // } catch (IOException e) {
                                    // // TODO Auto-generated catch block
                                    // e.printStackTrace();
                                    // }
                                    // if(Response1.equals("success"))
                                    // {
                                    //
                                    // }
                                    // else
                                    // {
                                    //
                                    // }
                                    // }
                                    // else
                                    // if(smsflag==3)
                                    // {
                                    //
                                    // }
                                    // else
                                    // {
                                    //
                                    // }
                                    // Toast.makeText(InsertNotice.this,
                                    // "Data Sent Successfully!!",Toast.LENGTH_SHORT).show();
                                    // Intent in=new
                                    // Intent(InsertNotice.this,InsertNotice.class);
                                    // startActivity(in);
                                    // finish();
                                }

                            }).setNegativeButton("No", null) // Do nothing on no
                    .show();

        }
    }

    // public void showSelectColoursDialog()
    // {
    // boolean[] checkedSubjects = new boolean[batches.length];
    // int count = batches.length;
    //
    // for(int i = 0; i < count; i++)
    // checkedSubjects[i] = selectedbatches.contains(batches[i]);
    //
    // DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new
    // DialogInterface.OnMultiChoiceClickListener()
    // {
    // @Override
    // public void onClick(DialogInterface dialog, int which, boolean isChecked)
    // {
    // if(isChecked)
    // selectedbatches.add(batches[which]);
    // else
    // selectedbatches.remove(batches[which]);
    //
    // onChangeSelectedSubjects();
    // }
    // };
    //
    // AlertDialog.Builder builder = new AlertDialog.Builder(this);
    // builder.setTitle("Select Subject");
    // builder.setMultiChoiceItems(batches, checkedSubjects,
    // coloursDialogListener);
    // builder.setNegativeButton("Set", null);
    // AlertDialog dialog = builder.create();
    // dialog.show();
    //
    // }

    // protected void onChangeSelectedSubjects()
    // {
    // StringBuilder stringBuilder = new StringBuilder();
    //
    // for(CharSequence subject : selectedbatches)
    // stringBuilder.append(subject + ",");
    // batchli=stringBuilder.toString();
    // batche.setText(stringBuilder.toString());
    // }

    // public void onBackPressed()
    // {
    // Intent i1=new Intent(InsertNotice.this,FirstPage.class);
    // startActivity(i1);
    // finish();
    // }
    //




/*    public int sendLongSMS() {
        // String phoneNumber = "9665277443";
		*//*
		 * String message = "Hello World! Now we are going to demonstrate " +
		 * "how to send a message with more than 160 characters from your Android application."
		 * ;
		 *//*
        String message = et.getText().toString();
        SmsManager smsManager = SmsManager.getDefault();

       *//* final String URL = "http://smartbridges.co.in/Android/GetStudentPhoneNo.php?Branch="
                + selection_branch.replace(
                " ", "%20")
                + "&ClassID="
                + MyApplication.get_session("classid")
                + "&Standard="
                + selection_std;*//*

        final String KEY = "Student"; // parent node
        final String StudentName1 = "StudentName";
        final String PhoneNo1 = "PhoneNo";
        String StudentName2 = "", PhoneNo2 = "";
        XMLParser parser = new XMLParser();

        String xml = MyApplication.get_server_call("http://smartbridges.co.in/Android/GetStudentPhoneNo.php?Branch="
                + selection_branch.replace(
                " ", "%20")
                + "&ClassID="
                + MyApplication.get_session("classid")
                + "&Standard="
                + selection_std);

        Toast.makeText(InsertNotice.this, xml.toString(), Toast.LENGTH_SHORT)
                .show();
        if (xml.equals("n")) {
            return 11;
        }
        Document doc = parser.getDomElement(xml); // getting DOM element

        NodeList nl = doc.getElementsByTagName(KEY);

        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
            // HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            // String str=parser.getValue(e,KEY_ID);

            // adding each child node to HashMap key => value
            StudentName2 = parser.getValue(e, StudentName1);
            PhoneNo2 = parser.getValue(e, PhoneNo1);
            Toast.makeText(InsertNotice.this, PhoneNo2.toString(),
                    Toast.LENGTH_SHORT).show();
            if (PhoneNo2.equals("")) {

            } else {
                ArrayList<String> parts = smsManager.divideMessage(message);
                smsManager.sendMultipartTextMessage(PhoneNo2, null, parts,
                        null, null);
            }
        }
        return 1;
    }*/


    public String sendLongSMS() {
        // String phoneNumber = "9665277443";
		/*
		 * String message = "Hello World! Now we are going to demonstrate " +
		 * "how to send a message with more than 160 characters from your Android application."
		 * ;
		 */
       //String message = et.getText().toString();
      //  SmsManager smsManager = SmsManager.getDefault();

       /* final String URL = "http://smartbridges.co.in/Android/GetStudentPhoneNo.php?Branch="
                + selection_branch.replace(
                " ", "%20")
                + "&ClassID="
                + MyApplication.get_session("classid")
                + "&Standard="
                + selection_std;*/


        RequestBody formBody = new FormBody.Builder()

                .add("class_id", MyApplication.get_session("classid"))
                .add("standard", selection_std)
                .add("branch", selection_branch)
                .add("batch", selection_batch)

                .build();


        String Response1 = ((MyApplication) getApplication()).post_server_call("http://smartbridges.co.in/Android/test.php", formBody);
        Log.i("tag", "Response:test" + Response1);



        return Response1;
    }

    private class LongOperation extends AsyncTask<String, Void, Void> {

        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(InsertNotice.this);

        protected void onPreExecute() {
            Dialog.setMessage("Sending Messages.......");
            Dialog.show();
            Dialog.setCanceledOnTouchOutside(false);
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            // Yes button clicked, do something

            String data3 = et.getText().toString();
            /*SendingData = new ArrayList<NameValuePair>();
            for (int i = 0; i < FirstPage.selectedsubjects.size(); i++) {
                String subid = ((MyApplication) getApplication()).dbo
                        .getSubjectID(
                                ((MyApplication) getApplication()).ClassID,
                                FirstPage.selectedsubjects.get(i));
                SendingData.add(new BasicNameValuePair("SubjectID[]", subid));
            }
            NameAndValue = new BasicNameValuePair("Data", data3);
            NameAndValue1 = new BasicNameValuePair("Date",
                    ((MyApplication) getApplication()).selecteddate);
            NameAndValue2 = new BasicNameValuePair("ClassID",
                    ((MyApplication) getApplication()).ClassID);
            NameAndValue3 = new BasicNameValuePair("Standard",
                    ((MyApplication) getApplication()).selectedstd);
            // NameAndValue4 = new BasicNameValuePair("SubjectID",subid);
            NameAndValue5 = new BasicNameValuePair("Branch",
                    ((MyApplication) getApplication()).selectedbranch);
            NameAndValue6 = new BasicNameValuePair("Batch",
                    ((MyApplication) getApplication()).multipleselectedbatches);
            NameAndValue7 = new BasicNameValuePair("CurDate",
                    ((MyApplication) getApplication()).CurDate);
            NameAndValue8 = new BasicNameValuePair("usmo", usermono);

            SendingData.add(NameAndValue);
            SendingData.add(NameAndValue1);
            SendingData.add(NameAndValue2);
            SendingData.add(NameAndValue3);
            // SendingData.add(NameAndValue4);
            SendingData.add(NameAndValue5);
            SendingData.add(NameAndValue6);
            SendingData.add(NameAndValue7);
            SendingData.add(NameAndValue8);*/

            RequestBody formBody = new FormBody.Builder()
                    .add("Data", data3)
                    .add("Date", ((MyApplication) getApplication()).selecteddate)
                    .add("ClassID", MyApplication.get_session("classid"))
                    .add("Standard", selection_std)
                    .add("Branch", selection_branch)
                    .add("Batch", selection_batch)
                    .add("CurDate", ((MyApplication) getApplication()).CurDate)
                    .add("usmo", usermono)

                    .build();


            String Response1 = ((MyApplication) getApplication()).post_server_call("", formBody);
            Log.i("tag", "Response:" + Response1);

            String Response = "";

            if (smsflag == 1) {
                String c = sendLongSMS();
                /*if (c == 11) {
                    msgsend = "Internet Speed is Slow";
                }*/
            } else if (smsflag == 2) {
                Response1 = "";

                   /* EntityToSend1 = new UrlEncodedFormEntity(SendingData);
                    httppost1.setEntity(EntityToSend1);
                    Response1 = mClient1.execute(httppost1, ResponseHandler2);*/

                if (Response1.equals("success")) {
                    msgsend = "success";
                } else {
                    msgsend = "fail" + Response1;
                }
            } else if (smsflag == 3) {

            } else {

            }
            // Toast.makeText(InsertNotice.this,
            // "Data Sent Successfully!!",Toast.LENGTH_SHORT).show();
            // Intent in=new Intent(InsertNotice.this,InsertNotice.class);
            // startActivity(in);
            // finish();

            return null;
        }

        protected void onPostExecute(Void unused) {
            Dialog.dismiss();
            Intent openMainActivity1 = new Intent(InsertNotice.this,
                    InsertNotice.class);
            startActivity(openMainActivity1);
            finish();

        }

    }

    // By Ganesh 10-09-2015

    class HTTPSendSms extends AsyncTask<Void, String, String> {
        // String Response = "";

        protected void onPreExecute() {
            response111 = "";

            if (check != 1) {
                final EditText input = new EditText(InsertNotice.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(
                        InsertNotice.this);
                final String data4 = et.getText().toString();

                builder1.setTitle("Check  Message\n");
                builder1.setMessage("Date:"
                        + ((MyApplication) getApplication()).selecteddate
                        + "\nStd:"
                        + ((MyApplication) getApplication()).selectedstd
                        + "\nMessage Count :" + msgcount
                        + "\n\nEnter 10 Digit Mo No");
                builder1.setView(input);
                builder1.setPositiveButton("Send",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                String value = input.getText().toString()
                                        .trim();
                                Toast.makeText(getApplicationContext(),
                                        value + "  msg " + data4.toString(),
                                        Toast.LENGTH_SHORT).show();

                                if (value.length() == 10) {

                                    usermono = value.trim();
                                    /*HttpClient httpclient = new DefaultHttpClient();
                                    HttpPost httppost0 = new HttpPost(
                                            "http://smartbridges.co.in/Android/Ckecksms.php");

                                    try {


                                        BasicNameValuePair mobile = new BasicNameValuePair(
                                                "Mo", value.trim());
                                        BasicNameValuePair massege1 = new BasicNameValuePair(
                                                "msg", data4.toString().trim());
                                        BasicNameValuePair date = new BasicNameValuePair(
                                                "Date",
                                                ((MyApplication) getApplication()).selecteddate);
                                        BasicNameValuePair clid = new BasicNameValuePair(
                                                "ClassID",
                                                ((MyApplication) getApplication()).ClassID);
                                        BasicNameValuePair std = new BasicNameValuePair(
                                                "Standard",
                                                ((MyApplication) getApplication()).selectedstd);
                                        BasicNameValuePair branch = new BasicNameValuePair(
                                                "Branch",
                                                ((MyApplication) getApplication()).selectedbranch);
                                        BasicNameValuePair batch = new BasicNameValuePair(
                                                "Batch",
                                                ((MyApplication) getApplication()).multipleselectedbatches
                                                        .toString());

                                        Log.i("mobilesutra.feetracker",
                                                "Mobile  no send to server is "
                                                        + mobile);
                                        Log.i("mobilesutra.feetracker",
                                                "message is " + massege1);
                                        Log.i("mobilesutra.feetracker",
                                                "Date is " + date);
                                        Log.i("mobilesutra.feetracker",
                                                "Class ID is " + clid);
                                        Log.i("mobilesutra.feetracker",
                                                "Standard is " + std);
                                        Log.i("mobilesutra.feetracker",
                                                "Branch is " + branch);
                                        Log.i("mobilesutra.feetracker",
                                                "Batch is " + batch);

                                        ArrayList<NameValuePair> personalnameValuepairs = new ArrayList<NameValuePair>();

                                        personalnameValuepairs.add(mobile);
                                        personalnameValuepairs.add(massege1);
                                        personalnameValuepairs.add(date);
                                        personalnameValuepairs.add(clid);
                                        personalnameValuepairs.add(std);
                                        personalnameValuepairs.add(branch);
                                        personalnameValuepairs.add(batch);
                                        UrlEncodedFormEntity dataentitytosend = new UrlEncodedFormEntity(
                                                personalnameValuepairs);

                                        Log.i("mobilesutra.feetracker",
                                                "Value assign in UrlEncodedFormEntity "
                                                        + dataentitytosend);

                                        httppost0.setEntity(dataentitytosend);
                                        ResponseHandler<String> responseHandler = new BasicResponseHandler();

                                        // Log.i("mobilesutra.feetracker",
                                        // "Value assign in UrlEncodedFormEntity "
                                        // + responseHandler);
                                        response111 = httpclient.execute(
                                                httppost0, responseHandler);

                                        Log.i("mobilesutra.feetracker",
                                                "Response given after sending notice is "
                                                        + response111);*/

                                    RequestBody formBody = new FormBody.Builder()
                                            .add("Mo", value.trim())
                                            .add("msg", data4.toString().trim())
                                            .add("Date", ((MyApplication) getApplication()).selecteddate)
                                            .add("ClassID", MyApplication.get_session("classid"))
                                            .add("Standard", selection_std)
                                            .add("Branch", selection_branch)
                                            .add("Batch", selection_batch)


                                            .build();

                                    response111 = ((MyApplication) getApplication()).post_server_call("http://smartbridges.co.in/Android/Ckecksms.php", formBody);
                                    Log.i("tag", "Response:" + response111);


                                    if (response111.toString().trim()
                                            .equals("Send")) {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "Message Sent Successfully",
                                                Toast.LENGTH_SHORT).show();
                                        check = 1;
                                    }

                            }

                            else

                            {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Enter Valid 10 Digit Mobile Number",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
            }).setNegativeButton("No", null) // Do
                        .show(); // nothing on no

            }

            if (check == 1) {
                String str = "Date:"
                        + ((MyApplication) getApplication()).selecteddate
                        + "\nStd:"
                        + ((MyApplication) getApplication()).selectedstd
                        + "\nMessage Count :" + msgcount;
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        InsertNotice.this);
                builder.setTitle("Inserting Notice On Server..!")
                        .setMessage(str + "\nDo You Confirm ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        new LongOperation().execute();
                                        if (msgsend == "success") {
                                            Toast.makeText(InsertNotice.this,
                                                    "Data Sent Successfully!!",
                                                    Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(
                                                    InsertNotice.this,
                                                    "Data Sending Failed!!  "
                                                            + msgsend,
                                                    Toast.LENGTH_LONG).show();
                                        }

                                    }

                                }).setNegativeButton("No", null) // Do nothing
                        // on no
                        .show();

            }

        }

        protected String doInBackground(Void... params) {

            publishProgress("progress");
            return "";
        }

        protected void onProgressUpdate(String... progress) {
            try {
                if (response111.toString().trim().equals("Send")) {
                    Toast.makeText(getApplicationContext(),
                            "Message Sent Successfully", Toast.LENGTH_SHORT)
                            .show();
                    check = 1;
                }

            } catch (Exception e) {
                // TODO Auto-generated catch
                // block

                Toast.makeText(InsertNotice.this,
                        "ERROE" + e.getMessage().toString(), Toast.LENGTH_SHORT)
                        .show();

                Log.i("mobilesutra.feetracker", "In Exception "
                        + e.getMessage().toString());
                // e.printStackTrace();
            }

        }

        protected void onPostExecute(Long result) {

        }
    }// HTTPRequest

    // done 10-09-2015
    @Override
    public void onBackPressed() {

        Intent i = new Intent(InsertNotice.this,ActivityDashboard.class);
        startActivity(i);
        finish();
    }
    public void ErrorDialog(String text)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Error...")
                .setMessage(text)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i=new Intent(context, ActivityDashboard.class);
                        startActivity(i);
                        finish();;

                    }
                })

                .show();
    }
}
