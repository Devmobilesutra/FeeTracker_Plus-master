package com.mobilesutra.feetrackerclass.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesutra.feetrackerclass.MyApplication;
import com.mobilesutra.feetrackerclass.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.RequestBody;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Personal_profile_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Personal_profile_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Personal_profile_fragment extends Fragment {


    private EditText change_pass = null, email_id = null, mob_no = null;
    private TextView account_exp_date = null, text_classname = null, exp_date = null;
    private Button btn_update_profile = null;
    private ImageView change_passwordImg = null;
    private String classname = "", vtill = "", vform = "", classID = "", email_add = "", pass = "", mob = "";
    private Typeface roboto_light, roboto_reguler;
    LinkedHashMap<String, String> lhm = null;
    Context context = null;
    ProgressDialog progressDialog;
    String Response = "";
    Boolean change_pass_flag = true;
    int len;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters


    private OnFragmentInteractionListener mListener;


    // TODO: Rename and change types and number of parameters
    public static Personal_profile_fragment newInstance(String param1, String param2) {
        Personal_profile_fragment fragment = new Personal_profile_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Personal_profile_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        final View view = inflater.inflate(R.layout.fragment_personal_profile_fragment, container, false);
        text_classname = (TextView) view.findViewById(R.id.full_name);
        change_pass = (EditText) view.findViewById(R.id.change_password);
        change_pass.setText(MyApplication.get_session("password"));
        MyApplication.log("SAMARTH","change_pass in session = "+MyApplication.get_session("password"));
        email_id = (EditText) view.findViewById(R.id.email_id);
        mob_no = (EditText) view.findViewById(R.id.mobile_no);
        account_exp_date = (TextView) view.findViewById(R.id.account_exp_date);
        btn_update_profile = (Button) view.findViewById(R.id.update_button);
        exp_date = (TextView) view.findViewById(R.id.account_exp);
        change_passwordImg = (ImageView) view.findViewById(R.id.change_password_img);


        roboto_light = Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
        roboto_reguler = Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");


        getIntentData();
        initComponentData();
        initComponentListeners();
        return view;
    }

    public void getIntentData() {

    }

    public void initComponentData() {


        text_classname.setTypeface(roboto_reguler);
        change_pass.setTypeface(roboto_reguler);
        email_id.setTypeface(roboto_reguler);
        mob_no.setTypeface(roboto_reguler);
        exp_date.setTypeface(roboto_reguler);
        account_exp_date.setTypeface(roboto_reguler);
        btn_update_profile.setTypeface(roboto_reguler);
    }

    public void initComponentListeners() {
        classID = MyApplication.get_session("classid");
        lhm = MyApplication.dbo.getProfileDetails(classID);

        MyApplication.set_session("op", "add");

        classname = lhm.get("name");
        vform = lhm.get("valid_from");
        vtill = lhm.get("valid_till");


        text_classname.setText(MyApplication.get_session("classname"));
        account_exp_date.setText(MyApplication.get_session("valid_till"));
        mob_no.setText(MyApplication.get_session("contactno"));
        email_id.setText(MyApplication.get_session("emailid"));
        email_id.setEnabled(false);
        mob_no.setEnabled(false);
        text_classname.setEnabled(false);

        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = change_pass.getText().toString().trim();
                email_add = email_id.getText().toString();
                mob = mob_no.getText().toString();
                if (pass.isEmpty()) {

                    Toast.makeText(context, getResources().getString(R.string.valid_change_pass), Toast.LENGTH_SHORT).show();

                } else {
                    MyApplication.log("SAMARTH","DATA TO BE SEND \n");
                    MyApplication.log("SAMARTH","classID="+classID);
                    MyApplication.log("SAMARTH","pass ="+pass);
                    MyApplication.log("SAMARTH","mob="+mob);

                    //NAMRATA changes
                    MyApplication.set_session("password",pass);
                    MyApplication.set_session(MyApplication.SESSION_PASSTEXT,pass);

                    long id = MyApplication.dbo.update_profile(classID, pass, email_add, mob);
                    Log.d("tag", "id = " + id);
                    HttpForgotPass pass = new HttpForgotPass();
                    pass.execute();

                }
            }
        });

        String v = change_pass.getText().toString();
        len = v.length();
        Log.d("tag", "length" + v + "--" + len);
        if (len != 0)
            change_pass.setSelection(len);

        change_passwordImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApplication.log("SAMARTH","change_pass.getText()= "+change_pass.getText());
                String PASS = change_pass.getText().toString();

                if (change_pass_flag) {

                    Log.d("tag", "length1 = " + len);

                    change_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    change_pass_flag = false;
                    int pos = change_pass.getText().length();
                    change_pass.setSelection(pos);
                    MyApplication.log("SAMARTH","change_pass.getText()1 = "+change_pass.getText());

                } else {
                    Log.d("tag", "length2 = " + len);
                    try {
                        MyApplication.log("SAMARTH","leght in 2 = "+change_pass.getText().length());

//                        change_pass.setSelection(len);
                        change_pass.setSelection(change_pass.getText().length());
                        change_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        change_pass_flag = true;
                        int pos = change_pass.getText().length();
                        change_pass.setSelection(pos);
                        MyApplication.log("SAMARTH","change_pass.getText()2 = "+change_pass.getText());

                    }
                    catch (Exception e)
                    {
                        MyApplication.log("SAMARTH","Exception in catch = "+e.getMessage());
                    }
                }

                change_pass.setText(PASS);

            }
        });


    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onStart() {
        Log.d("tag1", "onStart");

        super.onStart();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onStop() {
        Log.d("tag1", "onStop");

        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onPause() {
        Log.d("tag1", "onPause");

        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onResume() {
        Log.d("tag1", "onResume");

        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onDestroyView() {
        Log.d("tag1", "onDestroyView");

        super.onDestroyView();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onDestroy() {
        Log.d("tag1", "onDestroy");

        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onDetach() {
        Log.d("tag1", "onDetach");

        super.onDetach();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    class HttpForgotPass extends AsyncTask<Void, String, String> {
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "",
                    "wait.......");
            progressDialog.setCancelable(false);

        }

        protected String doInBackground(Void... params) {

            // http://192.168.0.242/ms-projects/mobilesutra.com/FeeTracker_New/Service

            String url = "http://mobilesutra.com/FeeTracker/Service/update_password";
            RequestBody formBody = new FormBody.Builder()
                    .add("username", email_id.getText().toString().trim())
                    .add("password", change_pass.getText().toString().trim())
                    .build();
            Log.i("response", "--------------------------" + formBody);
            Response = (MyApplication.post_server_call(url, formBody));

            Log.i("response", "" + Response);

            publishProgress("progress");
            return Response;


        }

        protected void onProgressUpdate(String... progress) {


            Log.d("tag10", "json--" + Response.length());
            if (Response.length() > 0) {
                try {


                    JSONObject json = new JSONObject(Response);
                    Log.e("Msg", json + "");

                    if (json.getString("response_status").equals("true")) {

                        progressDialog.dismiss();
                        String msg1 = json.getString("response_message");
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setTitle("");
                        alertDialog.setMessage("" + msg1);
                        alertDialog.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        });


                        alertDialog.show();
//						Toast.makeText(getApplicationContext(),
//								"Your user name and password is sent to your email address. Contact sbsupport@mobilesutra.com for any further help.", Toast.LENGTH_SHORT)
//								.show();


                    } else {
                        progressDialog.dismiss();
                        String msg = json.getString("response_message");
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setTitle("");
                        alertDialog.setMessage("" + msg);
                        alertDialog.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        });


                        alertDialog.show();


                    }


                } catch (JSONException e) {
                    Log.e("Msg", e.getMessage() + "");
                }
            } else {
                progressDialog.dismiss();
            }


        }

        protected void onPostExecute(Long result) {

        }

    }// HTTP

}
