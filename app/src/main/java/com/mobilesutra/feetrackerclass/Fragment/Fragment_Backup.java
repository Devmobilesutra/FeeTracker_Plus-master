package com.mobilesutra.feetrackerclass.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.mobilesutra.feetrackerclass.MyApplication;
import com.mobilesutra.feetrackerclass.R;
import com.mobilesutra.feetrackerclass.activities.ActivitySMS;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Backup.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_Backup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Backup extends Fragment implements BillingProcessor.IBillingHandler  {
    Boolean configflag = false, studflag = false, feeflag = false, attendanceflag = false;
    Button backup = null, restore = null;
    Context context = null;
    Typeface roboto_light, roboto_reguler;
    ProgressDialog progressDialog1 = null;

    String Response = "";
    Button btn_submit;

    // Billing
    private Button btn_Buy;
    BillingProcessor bp;
    private String APP_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtVlqUkDuCS2e3ADCQU4+BkaVfkE4EujmiZYo3HYFhrvoFaOk9fMCx7CvCGDhddF/3QFNUD6kdVq2aX2dvShN4gfnOuqj3GCAWewURaRQk5H9aKFW/K8/m2xls0Fl5guMbUjLlvGhBQKhj4WFHyds0ERWEskJFoXuI8leMLl+z1fZXfZAho0BSVi4t/Om8D4jpreypphvQ5NFjW5G3sbtUbbxGtQo/UrtxqPjgz7ogfmc1I0TxXIZFzLI9OtCbJlt45DPKvJpjBeGCjawOWvbiG7kadV3bhb1o0/1HcXV8b+yD26FAOYaDpN5tT6PEd1lBxz2x0nP8dXfWTXngj2lywIDAQAB";
    private String USER_KEY = MyApplication.get_session(MyApplication.SESSION_USERTEXT);
    private String PRODUCT_ID = "backup";



    JSONArray jsonObjectMAIN = new JSONArray();
    CheckBox class_config_backup, fee_backup, student_backup, attandance_backup;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Backup.
     */
    // TODO: Rename and change types and number of parameters



    public static Fragment_Backup newInstance(String param1, String param2)
    {
        Fragment_Backup fragment = new Fragment_Backup();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_Backup() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment__backup, container, false);


        roboto_light = Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
        roboto_reguler = Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");


        TextView textView = (TextView) view.findViewById(R.id.title_heading);
        class_config_backup = (CheckBox) view.findViewById(R.id.checkBox1);
        student_backup = (CheckBox) view.findViewById(R.id.checkBox2);
        fee_backup = (CheckBox) view.findViewById(R.id.checkBox3);
        attandance_backup = (CheckBox) view.findViewById(R.id.checkBox4);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        btn_Buy = (Button)view.findViewById(R.id.btn_Buy);
        btn_Buy.setVisibility(View.VISIBLE);
        class_config_backup.setTypeface(roboto_reguler);
        fee_backup.setTypeface(roboto_reguler);
        student_backup.setTypeface(roboto_reguler);
        attandance_backup.setTypeface(roboto_reguler);
        btn_submit.setTypeface(roboto_reguler);
        btn_Buy.setTypeface(roboto_reguler);

        textView.setTypeface(roboto_reguler);


        // commented by bhavesh
        // Billing
        bp = BillingProcessor.newBillingProcessor(getActivity(), APP_KEY, USER_KEY, this); // doesn't bind
        bp.initialize();
        Log.d("onBillingError", "> initialize");

        for(String sku : bp.listOwnedSubscriptions())
        {
            Log.d("onBillingError", "Owned Subscription: in Oncreate" + sku);
            Log.d("onBillingError", "> Success - in Oncreate getSubscriptionTransactionDetails");
            if (bp.isSubscribed(PRODUCT_ID)) {
                btn_Buy.setVisibility(View.GONE);
                btn_submit.setVisibility(View.VISIBLE);
                Log.d("onBillingError", "> Success in Oncreate - True");
            } else {
                btn_Buy.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.GONE);
                Log.d("onBillingError", "> Success in Oncreate - else");
            }
        }


        btn_Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.subscribe(getActivity(), PRODUCT_ID);
            }
        });



        initComponentListeners();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        mListener = null;
        if (bp != null) {
            bp.release();
        }
        super.onDetach();
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        btn_Buy.setVisibility(View.GONE);
        btn_submit.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPurchaseHistoryRestored() {
        for(String sku : bp.listOwnedSubscriptions())
        {
            Log.d("onBillingError", "Owned Restored Subscription: " + sku);
            Log.d("onBillingError", "> Success - Restored getSubscriptionTransactionDetails");
            if (bp.isSubscribed(PRODUCT_ID)) {
                btn_Buy.setVisibility(View.GONE);
                btn_submit.setVisibility(View.VISIBLE);
                Log.d("onBillingError", "> Success Restored - True");
            } else {
                btn_Buy.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.GONE);
                Log.d("onBillingError", "> Success Restored - else");
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    public void initComponentListeners() {


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (class_config_backup.isChecked()) {
//                        new HttpBackup().execute();
                    configflag = true;

                }
                if (student_backup.isChecked()) {
//                        new HttpStudent().execute();
                    studflag = true;

                }
                if (fee_backup.isChecked()) {
//                        new HttpFee().execute();
                    feeflag = true;

                }
                if (attandance_backup.isChecked()) {
//                        new HttpAttendace().execute();
                    attendanceflag = true;
                }
                if (class_config_backup.isChecked() || student_backup.isChecked() || fee_backup.isChecked() || attandance_backup.isChecked()) {

                    new HttpBackup().execute();
                } else {
                    Toast.makeText(context, "Please select one ", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }



    public JSONArray readdata(ArrayList<String> data) throws JSONException {

        String[] tablesnames = new String[data.size()];
        tablesnames = data.toArray(tablesnames);
        JSONArray jsonArrayData = new JSONArray();
        JSONObject jsonObjectMain = new JSONObject();
        JSONObject jsonObjectTable = new JSONObject();
        String[] tableNames = MyApplication.dbo.getTableNames();

//        String [] tablesnames= {"BranchTable", "BatchTable", "SubjectTable", "StandardTable", "ProfileTable", "Registration"};


        int tableLength = tablesnames.length;
        Log.i("Database_table", "names" + Arrays.toString(tablesnames));

        for (int i = 0; i < tableLength; i++) {
            jsonObjectTable = MyApplication.dbo.getDataFromBackup(tablesnames[i]);
            Log.i("Database_table", "tablesnames " + tablesnames[i]);
            Log.i("Database_table", "jsonObjectTable.length()--> " + jsonObjectTable.length());
            if (jsonObjectTable.length() != 0) {
                jsonArrayData.put(jsonObjectTable);

                //Log.i("readdata", "jsonObjectTable******************************************************");
               // Log.i("readdata", "jsonObjectTable---> table: "+tablesnames);
                //Log.i("readdata", "jsonObjectTable.length()--> " + jsonObjectTable.toString());
               // Log.i("readdata", "jsonObjectTable******************************************************");
            }
        }
        Log.i("FragBackup \n Database_table", "json_Array" + jsonArrayData + "\n.");
        return jsonArrayData;

    }

    class HttpBackup extends AsyncTask<Void, String, String> {
        protected void onPreExecute() {
            String cls = "", cls1 = "", cls2 = "", cls3 = "";
            if (configflag) {
                cls = "Class configuration";
            } else {
                cls = "";
            }
            if (studflag) {
                cls1 = "Student's records";
            } else {
                cls1 = "";
            }
            if (feeflag) {
                cls2 = "Fee records";
            } else {
                cls2 = "";
            }
            if (attendanceflag) {
                cls3 = "Attendance Attendance";
            } else {
                cls3 = "";
            }
            progressDialog1 = ProgressDialog.show(context, "",
                    "Taking backup of " + cls + ", " + cls1 + ", " + cls2 + ", " + cls3);
            progressDialog1.setCancelable(false);
        }

        protected String doInBackground(Void... params) {

            JSONArray jsonObjectmaster2 = new JSONArray();
//            String [] tablesnames= {"branchtable", "batchtable", "subjecttable", "standardtable", "profiletable","insert_fee"};

            ArrayList<String> jsonArray = new ArrayList<String>();
            if (configflag) {
                jsonArray.add("branchtable");
                jsonArray.add("batchtable");
                jsonArray.add("subjecttable");
                jsonArray.add("standardtable");
                jsonArray.add("profiletable");
                jsonArray.add("insert_fee");
                configflag = false;
            }
            if (studflag) {
                jsonArray.add("insert_local_add_student");
                jsonArray.add("student_subject");
                studflag = false;
            }
            if (feeflag) {
                jsonArray.add("studentfeetable");
                feeflag = false;
            }
            if (attendanceflag) {
                jsonArray.add("absentstudentregister");
                attendanceflag = false;
            }

            try {
                jsonObjectmaster2 = readdata(jsonArray);
                // Log.d("tag10", "jsonObjectmaster2--" + jsonObjectmaster2);
                // Log.d("tag10", "jsonObjectmaster2.length--" + jsonObjectmaster2.length());

                Log.i("START ", "******************************************************************");
                Log.e("START ", "before calling  jsonArray----> " + jsonArray.toString());
                Log.i("START ", "******************************************************************");
                Log.e("START ", "before calling  jsonObjectmaster2----> " + jsonObjectmaster2.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jsonObjectmaster2.length() == 0) {


                Response = "-2";
                publishProgress("progress");
                return Response;
            } else {

                String url = "http://mobilesutra.com/FeeTracker/Service/backup";
                RequestBody formBody = new FormBody.Builder()
                        .add("class_id", MyApplication.get_session("classid"))
                        .add("database", String.valueOf(jsonObjectmaster2))
                        .build();
                Response = (MyApplication.post_server_call(url, formBody));
                //publishProgress("progress");
                return Response;
            }
        }

        protected void onPostExecute(String progress) {
            Log.d("onProgressUpdate()", " Response --" + Response);
            Log.d("onProgressUpdate()", " Response.length()--" + Response.length());
            if (Response.equals("-2")) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("");
                alertDialog.setMessage(getResources().getString(R.string.blnck_data));
                alertDialog.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                alertDialog.show();
                progressDialog1.dismiss();
            } else {
                if (Response.equals("-0")) {
                    progressDialog1.dismiss();
                    Toast.makeText(context, getResources().getString(R.string.Internet_problem), Toast.LENGTH_LONG).show();

                } else {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(Response);
                        if (json.getString("response_status").equals("true")) {
                            Toast.makeText(context, getResources().getString(R.string.success_backup), Toast.LENGTH_LONG).show();
                            progressDialog1.dismiss();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.fail_backup), Toast.LENGTH_LONG).show();
                            progressDialog1.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("Msg", "JSONException is" + e.getMessage());
                    }
                    Log.e("Msg", json + "");
                }
            }
        }

        protected void onPostExecute(Long result) {

        }

    }// HTTP
//    class HttpStudent extends AsyncTask<Void, String,String>
//    {
//        protected void onPreExecute()
//        {
//            progressDialog2 = ProgressDialog.show(context, "",
//                    "wait.....");
//            progressDialog2.setCancelable(false);
//        }
//        protected String doInBackground(Void... params)
//        {
//            JSONArray jsonObjectmaster3=new JSONArray();
//            String [] tablesnames= {"insert_local_add_student","student_subject"};
//            try {
//                jsonObjectmaster3=readdata(tablesnames);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//            String url="http://mobilesutra.com/FeeTracker/Service/backup";
//            RequestBody formBody = new FormEncodingBuilder()
//                    .add("class_id",MyApplication.get_session("classid"))
//                    .add("database",String.valueOf(jsonObjectmaster3))
//                    .build();
//            Response=(MyApplication.post_server_call(url,formBody));
//            Log.i("response",""+Response);
//            publishProgress("progress");
//            return Response;
//        }
//        protected void onProgressUpdate(String... progress)
//        {
//
//            Log.d("tag10", "json--" + Response.length());
//            if (Response.equals("-0")) {
//                Toast.makeText(context,"Please try.. ",Toast.LENGTH_LONG).show();
//                progressDialog2.dismiss();
//            } else {
//                JSONObject json = null;
//                try {
//                    json = new JSONObject(Response);
//                    if(json.getString("response_status").equals("true"))
//                    {
//                        Toast.makeText(context, getResources().getString(R.string.success_backup), Toast.LENGTH_LONG).show();
//                        progressDialog2.dismiss();
//                    }else
//                    {
//                        Toast.makeText(context, getResources().getString(R.string.fail_backup), Toast.LENGTH_LONG).show();
//                        progressDialog2.dismiss();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.e("Msg", json + "");
//            }
//        }
//
//        protected void onPostExecute(Long result) {
//
//        }
//
//    }// HTTP
//    class HttpFee extends AsyncTask<Void, String,String>
//    {
//        protected void onPreExecute()
//        {
//            progressDialog3 = ProgressDialog.show(context, "",
//                    "wait.....");
//            progressDialog3.setCancelable(false);
//        }
//        protected String doInBackground(Void... params)
//        {
//
//
//            JSONArray jsonObjectmaster4=new JSONArray();
//            String [] tablesnames= {"studentfeetable"};
//            try {
//                jsonObjectmaster4=readdata(tablesnames);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            String url="http://mobilesutra.com/FeeTracker/Service/backup";
//            RequestBody formBody = new FormEncodingBuilder()
//                    .add("class_id",MyApplication.get_session("classid"))
//                    .add("database",String.valueOf(jsonObjectmaster4))
//                    .build();
//            Response=(MyApplication.post_server_call(url,formBody));
//            Log.i("response",""+Response);
//            publishProgress("progress");
//            return Response;
//        }
//        protected void onProgressUpdate(String... progress)
//        {
//
//            Log.d("tag10", "json--" + Response.length());
//            if (Response.equals("-0")) {
//                Toast.makeText(context,"Please try.. ",Toast.LENGTH_LONG).show();
//                progressDialog3.dismiss();
//            } else {
//                JSONObject json = null;
//                try {
//                    json = new JSONObject(Response);
//                    if(json.getString("response_status").equals("true"))
//                    {
//                        Toast.makeText(context, getResources().getString(R.string.success_backup), Toast.LENGTH_LONG).show();
//                        progressDialog3.dismiss();
//                    }else
//                    {
//                        Toast.makeText(context, getResources().getString(R.string.fail_backup), Toast.LENGTH_LONG).show();
//                        progressDialog3.dismiss();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.e("Msg", json + "");
//            }
//        }
//
//        protected void onPostExecute(Long result) {
//
//        }
//
//    }// HTTP
//    class HttpAttendace extends AsyncTask<Void, String,String>
//    {
//        protected void onPreExecute()
//        {
//            progressDialog4 = ProgressDialog.show(context, "",
//                    "wait.....");
//            progressDialog4.setCancelable(false);
//        }
//        protected String doInBackground(Void... params)
//        {
//
//            JSONArray jsonObjectmaster5=new JSONArray();
//            String [] tablesnames= {"absentstudentregister"};
//            try {
//                jsonObjectmaster5=readdata(tablesnames);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            String url="http://mobilesutra.com/FeeTracker/Service/backup";
//            RequestBody formBody = new FormEncodingBuilder()
//                    .add("class_id",MyApplication.get_session("classid"))
//                    .add("database",String.valueOf(jsonObjectmaster5))
//                    .build();
//            Response=(MyApplication.post_server_call(url,formBody));
//            Log.i("response",""+Response);
//            publishProgress("progress");
//            return Response;
//        }
//        protected void onProgressUpdate(String... progress)
//        {
//
//            Log.d("tag10", "json--" + Response.length());
//            if (Response.equals("-0")) {
//                Toast.makeText(context,"Please try.. ",Toast.LENGTH_LONG).show();
//                progressDialog4.dismiss();
//            } else {
//                JSONObject json = null;
//                try {
//                    json = new JSONObject(Response);
//                    if(json.getString("response_status").equals("true"))
//                    {
//                        Toast.makeText(context, getResources().getString(R.string.success_backup), Toast.LENGTH_LONG).show();
//                        progressDialog4.dismiss();
//                    }else
//                    {
//                        Toast.makeText(context, getResources().getString(R.string.fail_backup), Toast.LENGTH_LONG).show();
//                        progressDialog4.dismiss();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.e("Msg", json + "");
//            }
//        }
//
//        protected void onPostExecute(Long result) {
//
//        }
//
//    }// HTTP

}
