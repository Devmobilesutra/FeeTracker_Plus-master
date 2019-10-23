package com.mobilesutra.feetrackerclass.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

import com.mobilesutra.feetrackerclass.ErrorHandler.ExceptionHandler;
import com.mobilesutra.feetrackerclass.MyApplication;
import com.mobilesutra.feetrackerclass.R;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ActivityDashboard extends AppCompatActivity {

    ImageView img_profile = null,img_stud_mgmt=null,img_fee_mgmt=null,img_send_sms=null,img_attendance=null,img_faq=null;
    CardView profile=null,stud_mgt=null,fee_mgt=null,send_sms=null,attendance=null,get_help=null;
    Typeface roboto_light,roboto_reguler;
    Context context=this;
    LinkedHashMap<String,String> lhm_branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_dashboard);
        TextView txt_user = (TextView) findViewById(R.id.txt_user);

        txt_user.setText(MyApplication.get_session("classname"));
        roboto_light =  Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
        roboto_reguler =  Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");
        txt_user.setTypeface(roboto_reguler);
        infopage();
        initComponents();
        initComponentListeners();
        lhm_branch = MyApplication.dbo.getBranchWithActiveFlag(MyApplication.get_session("classid"));

        /*HashMap config = new HashMap();
        Core.init(Support.getInstance());
        Core.install(getApplication(),
                "f8b9b6654074021735e4eda1eb0c3234",
                "mobilesutra.helpshift.com",
                "mobilesutra_platform_20161122062227772-ebfce3f4bb5e945",
                config);*/
    }

    private void initComponents() {

        img_profile = (ImageView) findViewById(R.id.img_profile);
        img_stud_mgmt = (ImageView) findViewById(R.id.img_student_management);
        img_fee_mgmt = (ImageView) findViewById(R.id.img_fee_management);
        img_send_sms = (ImageView) findViewById(R.id.img_send_sms);
        img_attendance = (ImageView) findViewById(R.id.img_attendance);
        img_faq = (ImageView) findViewById(R.id.faq);
        img_faq.setVisibility(View.VISIBLE);
        lhm_branch = new LinkedHashMap<>();
//       TextView text_p = (TextView) findViewById(R.id.txt_management);
//        TextView text_profile = (TextView) findViewById(R.id.txt_management);
//        TextView text_stud_mng = (TextView) findViewById(R.id.txt_student_management);
//        TextView text_fee = (TextView) findViewById(R.id.txt_management22);
//        TextView text_send_sms = (TextView) findViewById(R.id.txt_send_sms);
//        TextView text_attenance = (TextView) findViewById(R.id.txt_management32);
//        TextView text_help = (TextView) findViewById(R.id.txt_get_help);
//        /*text_profile.setTypeface(roboto_reguler);
//        text_stud_mng.setTypeface(roboto_reguler);
//        text_send_sms.setTypeface(roboto_reguler);
//        text_attenance.setTypeface(roboto_reguler);
//        text_help.setTypeface(roboto_reguler);
//        text_fee.setTypeface(roboto_reguler);*/


        profile = (CardView) findViewById(R.id.card_profile);
        stud_mgt = (CardView) findViewById(R.id.card_student_management);
        fee_mgt = (CardView) findViewById(R.id.card_fee_management);
        send_sms = (CardView) findViewById(R.id.card_send_sms);
        attendance = (CardView) findViewById(R.id.card_attendance);
        get_help = (CardView) findViewById(R.id.card_get_help);

    }


    private void initComponentListeners() {


        img_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent openMainActivity1 = new Intent(ActivityDashboard.this, faq.class);
                startActivity(openMainActivity1);
                finish();

              //  Support.showFAQs(ActivityDashboard.this);


            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ImageView img = (ImageView) v;

                Intent openMainActivity1 = new Intent(
                        ActivityDashboard.this, Activity_profileNew.class);

                startActivity(openMainActivity1);
                finish();

//                Toast.makeText(ActivityDashboard.this,"Width->"+img.getWidth()+"\n"+"Height->"+img.getHeight(), Toast.LENGTH_SHORT).show();
            }
        });

        stud_mgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ImageView img = (ImageView) v;

              /*  Intent openMainActivity1 = new Intent(
                        ActivityDashboard.this, StudManagement.class);*/
                if (lhm_branch.size() == 0) {

                    ErrorDialog("Please go to profile page and add class details");
                }
                else {
                    Intent openMainActivity1 = new Intent(
                            ActivityDashboard.this, ActivityStudentManagementList.class);
                    startActivity(openMainActivity1);
                    finish();
                }


            }
        });
        send_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lhm_branch.size() == 0) {

                    ErrorDialog("Please go to profile page  and add class details");
                }
                else {
                    /*Intent openMainActivity1 = new Intent(
                            ActivityDashboard.this, Send_sms.class);*/


                    /*Intent openMainActivity1 = null;
                    try {
                        openMainActivity1 = new Intent(
                                ActivityDashboard.this,  (Class<? extends Activity>)Class.forName("com.mobilesutra.feetrackerclass.activities.Send_sms"));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }*/
                    Intent openMainActivity1 = new Intent(
                            ActivityDashboard.this, ActivitySMS.class);
                    startActivity(openMainActivity1);
                    finish();
                }
//                Toast.makeText(ActivityDashboard.this,"Width->"+img.getWidth()+"\n"+"Height->"+img.getHeight(), Toast.LENGTH_SHORT).show();
            }
        });

        fee_mgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ImageView img = (ImageView) v;
                if (lhm_branch.size() == 0) {
                    ErrorDialog("Please go to profile page  and add class details");
                }
                else {
                /*Intent openMainActivity1 = new Intent(
                        ActivityDashboard.this, StudentListInFeeMgmt.class);*/
                    Intent openMainActivity1 = new Intent(
                            ActivityDashboard.this, ActivityFeeManagement.class);
                    startActivity(openMainActivity1);
                    finish();
                }
//                Toast.makeText(ActivityDashboard.this,"Width->"+img.getWidth()+"\n"+"Height->"+img.getHeight(), Toast.LENGTH_SHORT).show();
            }
        });
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ImageView img = (ImageView) v;
                if (lhm_branch.size() == 0) {

                    ErrorDialog("Please go to profile page and add class details");
                }
                else {
                    /*Intent openMainActivity1 = new Intent(
                            ActivityDashboard.this, Add_attendance.class);*/
                    Intent openMainActivity1 = new Intent(
                            ActivityDashboard.this, ActivityAttendance.class);
                    startActivity(openMainActivity1);
                    finish();
                }
//                Toast.makeText(ActivityDashboard.this,"Width->"+img.getWidth()+"\n"+"Height->"+img.getHeight(), Toast.LENGTH_SHORT).show();
            }
        });
        get_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ImageView img = (ImageView) v;
                Intent openMainActivity1 = new Intent(
                        ActivityDashboard.this, Backup_New.class);

                startActivity(openMainActivity1);
                finish();
//
//                Toast.makeText(ActivityDashboard.this, "get help", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Close Application..!")
                .setMessage("Do You Want To Exit?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // Yes button clicked, do something
                                Toast.makeText(ActivityDashboard.this, "Exit button pressed", Toast.LENGTH_SHORT).show();
                                System.runFinalization();
                                System.exit(0);
                                finish();
                            }
                        }).setNegativeButton("No", null) // Do nothing on no
                .show();
    }

    public void infopage()
    {
        if(MyApplication.get_session("check")=="")
        {
            MyApplication.set_session("check","check");
            Intent openMainActivity2 = new Intent(
                    ActivityDashboard.this, ClickActivity.class);
            startActivity(openMainActivity2);
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
}
