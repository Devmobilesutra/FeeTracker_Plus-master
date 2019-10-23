package com.mobilesutra.feetrackerclass.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesutra.feetrackerclass.MyApplication;
import com.mobilesutra.feetrackerclass.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Restore.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_Restore#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Restore extends Fragment {


    Boolean configflag = false, studflag = false, feeflag = false, attendanceflag = false;
    Button backup = null, restore = null;
    Context context = null;
    Typeface roboto_light, roboto_reguler;
    ProgressDialog progressDialog = null;
    String Response = "";
    Button btn_submit;
    JSONArray jsonObjectMAIN = new JSONArray();
    CheckBox class_config_backup, fee_backup, student_backup, attandance_backup;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    int page = 1;
    JSONArray jsonArray = new JSONArray();
    String jObj = "";
    int paginationTablePos = 0;

    private OnFragmentInteractionListener mListener;
    String LOG_TAG = "Fragment_Restore";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Restore.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Restore newInstance(String param1, String param2) {
        Fragment_Restore fragment = new Fragment_Restore();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_Restore() {
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

        View view = inflater.inflate(R.layout.fragment_fragment__restore, container, false);


        roboto_light = Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
        roboto_reguler = Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");


        TextView textView = (TextView) view.findViewById(R.id.title_heading);
        class_config_backup = (CheckBox) view.findViewById(R.id.checkBox1);
        student_backup = (CheckBox) view.findViewById(R.id.checkBox2);
        fee_backup = (CheckBox) view.findViewById(R.id.checkBox3);
        attandance_backup = (CheckBox) view.findViewById(R.id.checkBox4);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        class_config_backup.setTypeface(roboto_reguler);
        fee_backup.setTypeface(roboto_reguler);
        student_backup.setTypeface(roboto_reguler);
        attandance_backup.setTypeface(roboto_reguler);
        btn_submit.setTypeface(roboto_reguler);
        textView.setTypeface(roboto_reguler);


        initComponentListeners();

        return view;
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


                    if (configflag) {
                        jsonArray.put("branchtable");
                        jsonArray.put("batchtable");
                        jsonArray.put("subjecttable");
                        jsonArray.put("standardtable");
                        jsonArray.put("profiletable");
                        jsonArray.put("insert_fee");
                        configflag = false;
                    }
                    if (studflag) {
                        jsonArray.put("insert_local_add_student");
                        jsonArray.put("student_subject");
                        studflag = false;
                    }
                    if (feeflag) {
                        jsonArray.put("studentfeetable");
                        feeflag = false;
                    }
                    if (attendanceflag) {
                        jsonArray.put("absentstudentregister");
                        attendanceflag = false;
                    }


                   MyApplication.log(LOG_TAG, "class_config_tablename" + jsonArray);


//                    int tableLength = jsonArray.length();

                    try {
//                        jObj = jsonArray.getJSONObject(paginationTablePos);
                        jObj = jsonArray.getString(paginationTablePos);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    MyApplication.log(LOG_TAG, "table name" + jObj.toString());

                    HttpBackup serverCall = new HttpBackup(jObj.toString());
                    page = 1;
                    serverCall.execute();

                } else {
                    Toast.makeText(context, "Please select one ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    class HttpBackup extends AsyncTask<Void, String, String> {

        String currentTable = "";

        public HttpBackup(String table) {

            this.currentTable = table;
        }

        protected void onPreExecute() {
            String cls = "", cls1 = "", cls2 = "", cls3 = "";
            if (configflag) {
                cls = "Class configuration";
            } else {
                cls = "";
            }
            if (configflag) {
                cls1 = "Student's records";
            } else {
                cls1 = "";
            }
            if (configflag) {
                cls2 = "Fee records";
            } else {
                cls2 = "";
            }
            if (configflag) {
                cls3 = "Attendance Attendance";
            } else {
                cls3 = "";
            }

            if(progressDialog == null ) {

                progressDialog = ProgressDialog.show(context, "",
                        "Restoring Data");
            }
            else {
                if(!progressDialog.isShowing())
                    progressDialog.show();
            }
        }

        protected String doInBackground(Void... params) {


           MyApplication.log(LOG_TAG, "HttpBackup: on do in background:table_name ---" + currentTable);
           MyApplication.log(LOG_TAG, "HttpBackup: on do in background:page no---" + page);
           MyApplication.log(LOG_TAG, "HttpBackup server: Start");
            String url = "http://mobilesutra.com/FeeTracker/Service/restore_table";
            RequestBody formBody = new FormBody.Builder()
                    .add("class_id", MyApplication.get_session("classid"))
                    .add("table", currentTable)
                    .add("page", page + "")
                    .build();
            Response = (MyApplication.post_server_call(url, formBody));
           MyApplication.log(LOG_TAG, "HttpBackup server: Resopnse");
            MyApplication.log(LOG_TAG, "Response" + Response);
            if (!Response.equals("-0")) {
               /* Toast.makeText(context, getResources().getString(R.string.Internet_problem), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            } else {*/
                JSONObject json = null;
                try {
                    json = new JSONObject(Response);
                    if (json.getString("response_status").equals("true")) {
                       MyApplication.log(LOG_TAG, "success");
                       MyApplication.log(LOG_TAG, "HttpBackup server: parsing start");


                        /*JSONArray jsonArrayData = json.getJSONArray("data");

                       MyApplication.log(LOG_TAG, "%%%" + jsonArrayData.length());
                        for (int k = 0; k < jsonArrayData.length(); k++) {*/
                        //JSONObject jsonObjectChild = json.getJSONObject("table");
                        String tablename = json.getString("table");
                       MyApplication.log(LOG_TAG, "HttpBackup: tablename---" + tablename);

                        switch (tablename) {

                            case "branchtable":

                                if(page == 1)
                                {
                                    MyApplication.dbo.deleteBranchTable();

                                }

                      //          progressDialog.setMessage("Restoring data of branches...");

                               MyApplication.log(LOG_TAG, "HttpBackup: In branchtable---");
                                JSONArray jsonArray2Records = json.getJSONArray("records");
                                MyApplication.dbo.bulk_Branch(jsonArray2Records);

                                /*JSONArray jsonArray2Records = json.getJSONArray("records");
                               MyApplication.log(LOG_TAG, "%%%" + jsonArray2Records.length());
                                for (int j = 0; j < jsonArray2Records.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String BranchName = jsonObject2RecordsChild.getString("BranchName");

                                    MyApplication.dbo.insertBranch(ClassID, BranchName);

                                   MyApplication.log(LOG_TAG, "%%%%--->" + Auto_Id + "-->" + id + "-->" + ClassID + "-->" + BranchName);
                                }*/

                                break;

                            case "batchtable":

                           //     progressDialog.setMessage("Restoring data of batches...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In branchtable---");
                                if(page == 1) {
                                    MyApplication.dbo.deleteBatchTable();
                                }

                                JSONArray jsonArray2Records1 = json.getJSONArray("records");
                                MyApplication.dbo.bulk_Batch(jsonArray2Records1);
                                /*Log.d("tag", "%%%" + jsonArray2Records1.length());
                                for (int j = 0; j < jsonArray2Records1.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records1.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String BatchName = jsonObject2RecordsChild.getString("BatchName");

                                    MyApplication.dbo.insertBatch(ClassID, BatchName);
                                   MyApplication.log(LOG_TAG, "%%%%--->" + Auto_Id + "-->" + id + "-->" + ClassID + "-->" + BatchName);
                                }*/

                                break;

                            case "standardtable":

                       //         progressDialog.setMessage("Restoring data of standards...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In standardtable---");
                                if(page == 1) {
                                    MyApplication.dbo.deleteStandardTable();
                                }

                                JSONArray jsonArray2Records2 = json.getJSONArray("records");
                                MyApplication.dbo.bulk_Standard(jsonArray2Records2);
                                /*Log.d("tag", "%%%" + jsonArray2Records2.length());
                                for (int j = 0; j < jsonArray2Records2.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records2.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String StandardName = jsonObject2RecordsChild.getString("StandardName");

                                    MyApplication.dbo.insertStandard(ClassID, StandardName);
                                   MyApplication.log(LOG_TAG, "%%%%--->" + Auto_Id + "-->" + id + "-->" + ClassID + "-->" + StandardName);
                                }*/


                                break;

                            case "subjecttable":

                       //         progressDialog.setMessage("Restoring data of subjects...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In subjecttable---");
                                if(page == 1) {
                                    MyApplication.dbo.deleteSubjectTable();
                                }

                                JSONArray jsonArray2Records3 = json.getJSONArray("records");
                                MyApplication.dbo.bulk_Subject(jsonArray2Records3);
                                /*Log.d("tag", "%%%" + jsonArray2Records3.length());
                                for (int j = 0; j < jsonArray2Records3.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records3.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String SubjectName = jsonObject2RecordsChild.getString("SubjectName");

                                    MyApplication.dbo.insertSubject(ClassID, SubjectName);
                                   MyApplication.log(LOG_TAG, "%%%%--->" + Auto_Id + "-->" + id + "-->" + ClassID + "-->" + SubjectName);
                                }*/

                                break;

                            case "profiletable":

                        //        progressDialog.setMessage("Restoring data of profile...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In profiletable---");
                                if(page == 1) {
                                    MyApplication.dbo.deleteProfileTable();
                                }
                                JSONArray jsonArray2Records4 = json.getJSONArray("records");
                                MyApplication.dbo.bulk_Profile(jsonArray2Records4);

                                /*Log.d("tag", "%%%" + jsonArray2Records4.length());
                                for (int j = 0; j < jsonArray2Records4.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records4.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String ClassName = jsonObject2RecordsChild.getString("ClassName");
                                    String BranchId = jsonObject2RecordsChild.getString("BranchId");
                                    String StandardId = jsonObject2RecordsChild.getString("StandardId");
                                    String BatchId = jsonObject2RecordsChild.getString("BatchId");
                                    String SubjectId = jsonObject2RecordsChild.getString("SubjectId");
                                    String flag_active = jsonObject2RecordsChild.getString("flag_active");

                                    MyApplication.dbo.insertProfileTable(ClassID, ClassName, BranchId, StandardId, BatchId, SubjectId, flag_active);


                                   MyApplication.log(LOG_TAG, "%%%%--->" + jsonObject2RecordsChild);
                                }*/

                                break;

                            case "insert_fee":

                       //         progressDialog.setMessage("Restoring data of fees...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In insert_fee---");
                                if(page == 1) {
                                    MyApplication.dbo.deleteInsertFeeTable();
                                }

                                JSONArray jsonArray2Records5 = json.getJSONArray("records");
                                MyApplication.dbo.bulk_InsertFee(jsonArray2Records5);
                                /*Log.d("tag", "%%%" + jsonArray2Records5.length());
                                for (int j = 0; j < jsonArray2Records5.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records5.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String FeeID = jsonObject2RecordsChild.getString("FeeID");
                                    String Fee = jsonObject2RecordsChild.getString("Fee");
                                    String Subjects = jsonObject2RecordsChild.getString("Subjects");
                                    String Branch = jsonObject2RecordsChild.getString("Branch");
                                    String Standard = jsonObject2RecordsChild.getString("Standard");
                                    String Batch = jsonObject2RecordsChild.getString("Batch");


                                    MyApplication.dbo.insertFee(ClassID, FeeID, Fee, Subjects, Branch, Standard, Batch);


                                }*/

                                break;

                            case "insert_local_add_student":

                      //          progressDialog.setMessage("Restoring data of students...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In insert_local_add_student---");
                                if(page == 1) {
                                    MyApplication.dbo.deleteStudentTable();
                                }

                               MyApplication.log(LOG_TAG, "jsonlocal_add_student_success");
                                JSONArray jsonArray2Records6 = json.getJSONArray("records");
                               MyApplication.log(LOG_TAG, "insert_local_add_student length :" + jsonArray2Records6.length());
                               MyApplication.log(LOG_TAG, "jsonlocal_add_student" + jsonArray2Records6);

                                MyApplication.dbo.bulk_AddStudent(jsonArray2Records6);

                                /*if (jsonArray2Records6.length() != 0)
                                    MyApplication.dbo.deleteStudents();*/

                                /*Log.d("rasika", "record count of insert_local_add_student : " + jsonArray2Records6.length());

                                for (int j = 0; j < jsonArray2Records6.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records6.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String Branch = jsonObject2RecordsChild.getString("Branch");
                                    String StudentName = jsonObject2RecordsChild.getString("StudentName");
                                    String Standard = jsonObject2RecordsChild.getString("Standard");
                                    String Batch = jsonObject2RecordsChild.getString("Batch");
                                    String DOB = jsonObject2RecordsChild.getString("DOA");
                                    String Address = jsonObject2RecordsChild.getString("Address");
                                    String Phone1 = jsonObject2RecordsChild.getString("Phone1");
                                    String Phone2 = jsonObject2RecordsChild.getString("Phone2");


                                    MyApplication.dbo.insert_local_student2(Auto_Id, id, ClassID, Branch, StudentName, Standard, Batch, DOB, Address, Phone1, Phone2);


                                }*/

                                break;

                            case "student_subject":

                      //          progressDialog.setMessage("Restoring data of student's subjects ...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In student_subject---");
                                if(page == 1) {
                                    MyApplication.dbo.deleteStudentSubjectTable();
                                }

                                JSONArray jsonArray2Records7 = json.getJSONArray("records");
                               MyApplication.log(LOG_TAG, "%%%" + jsonArray2Records7.length());
                               MyApplication.log(LOG_TAG, "student_subject" + jsonArray2Records7);

                                MyApplication.dbo.bulk_StudentSubject(jsonArray2Records7);
                              /*  if (jsonArray2Records7.length() != 0)
                                    MyApplication.dbo.deleteStudent_subject();*/

                               /* for (int j = 0; j < jsonArray2Records7.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records7.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String StudentID = jsonObject2RecordsChild.getString("StudentID");
                                    String SubjectID = jsonObject2RecordsChild.getString("SubjectID");
                                    String StudentIdWithAll = jsonObject2RecordsChild.getString("StudentIdWithAll");


                                    MyApplication.dbo.insertStudent_Subject(ClassID, StudentID, SubjectID, StudentIdWithAll);


                                }*/

                                break;

                            case "studentfeetable":

                       //         progressDialog.setMessage("Restoring data of student's fees...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In studentfeetable---");
                                if(page == 1) {
                                    MyApplication.dbo.deleteStudentFeeTable();
                                }

                                JSONArray jsonArray2Records8 = json.getJSONArray("records");
                               MyApplication.log(LOG_TAG, "%%%" + jsonArray2Records8.length());
                                MyApplication.dbo.bulk_StudentFeeTable(jsonArray2Records8);

                                /*for (int j = 0; j < jsonArray2Records8.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records8.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String StudentID = jsonObject2RecordsChild.getString("StudentID");
                                    String Standard = jsonObject2RecordsChild.getString("Standard");
                                    String FeesID = jsonObject2RecordsChild.getString("FeesID");
                                    String FeesPaid = jsonObject2RecordsChild.getString("FeesPaid");
                                    String Remark = jsonObject2RecordsChild.getString("Remark");
                                    String Balance = jsonObject2RecordsChild.getString("Balance");
                                    String Batch = jsonObject2RecordsChild.getString("Batch");
                                    String Date = jsonObject2RecordsChild.getString("Date");
                                    String Subjects = jsonObject2RecordsChild.getString("Subjects");
                                    String Branch = jsonObject2RecordsChild.getString("Branch");
                                    String PaymentStatus = jsonObject2RecordsChild.getString("PaymentStatus");


                                    MyApplication.dbo.updateRestore(ClassID, StudentID, Standard, FeesID, FeesPaid, Remark, Balance, Batch, Date, Subjects, Branch, PaymentStatus);


                                }*/

                                break;

                            case "absentstudentregister":

                    //            progressDialog.setMessage("Restoring data of absent students...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In absentstudentregister---");
                                if(page == 1) {
                                    MyApplication.dbo.deleteAbsentStudentTable();
                                }
                                JSONArray jsonArray2Records9 = json.getJSONArray("records");
                                MyApplication.dbo.bulk_AbsentStudentRegister(jsonArray2Records9);
                                /*Log.d("tag", "%%%" + jsonArray2Records9.length());
                                for (int j = 0; j < jsonArray2Records9.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records9.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String StudentID = jsonObject2RecordsChild.getString("StudentID");
                                    String StudentName = jsonObject2RecordsChild.getString("StudentName");
                                    String Date = jsonObject2RecordsChild.getString("Date");
                                    String Branch = jsonObject2RecordsChild.getString("Branch");
                                    String Standard = jsonObject2RecordsChild.getString("Standard");
                                    String Batch = jsonObject2RecordsChild.getString("Batch");
                                    String Subjects = jsonObject2RecordsChild.getString("Subjects");
                                    String IsPresent = jsonObject2RecordsChild.getString("IsPresent");
                                    String SubjectOption = jsonObject2RecordsChild.getString("SubjectOption");


                                    MyApplication.dbo.insertAbsentStudentRegister(MyApplication.get_session("classid"), StudentID, StudentName, Date, Branch, Standard, Batch, Subjects, IsPresent, SubjectOption);


                                }*/

                                break;

                        }
                    }
                } catch (JSONException e) {
                }
            }
            publishProgress("progress");
            return Response;
        }

        protected void onProgressUpdate(String... progress) {

           MyApplication.log(LOG_TAG, "onProgressUpdate:Response--" + Response);

//            while(page!=0) {

            if (Response.equals("-0")) {
                Toast.makeText(context, getResources().getString(R.string.Internet_problem), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            } else {
                JSONObject json = null;
                try {
                    json = new JSONObject(Response);
                    if (json.getString("response_status").equals("true")) {
                       MyApplication.log(LOG_TAG, "success");

                        /*JSONArray jsonArrayData = json.getJSONArray("data");

                       MyApplication.log(LOG_TAG, "%%%" + jsonArrayData.length());
                        for (int k = 0; k < jsonArrayData.length(); k++) {*/
                        //JSONObject jsonObjectChild = json.getJSONObject("table");
                        String tablename = json.getString("table");
                       MyApplication.log(LOG_TAG, "HttpBackup: tablename---" + tablename);

                        switch (tablename) {

                            case "branchtable":

                                progressDialog.setMessage("Restoring data of branches...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In branchtable---");

                       /*         JSONArray jsonArray2Records = json.getJSONArray("records");
                               MyApplication.log(LOG_TAG, "%%%" + jsonArray2Records.length());
                                for (int j = 0; j < jsonArray2Records.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String BranchName = jsonObject2RecordsChild.getString("BranchName");

                                    MyApplication.dbo.insertBranch(ClassID, BranchName);

                                   MyApplication.log(LOG_TAG, "%%%%--->" + Auto_Id + "-->" + id + "-->" + ClassID + "-->" + BranchName);
                                }
*/
                                break;

                            case "batchtable":

                                progressDialog.setMessage("Restoring data of batches...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In branchtable---");

                            /*    JSONArray jsonArray2Records1 = json.getJSONArray("records");
                               MyApplication.log(LOG_TAG, "%%%" + jsonArray2Records1.length());
                                for (int j = 0; j < jsonArray2Records1.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records1.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String BatchName = jsonObject2RecordsChild.getString("BatchName");

                                    MyApplication.dbo.insertBatch(ClassID, BatchName);
                                   MyApplication.log(LOG_TAG, "%%%%--->" + Auto_Id + "-->" + id + "-->" + ClassID + "-->" + BatchName);
                                }*/

                                break;

                            case "standardtable":

                                progressDialog.setMessage("Restoring data of standards...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In branchtable---");

                               /* JSONArray jsonArray2Records2 = json.getJSONArray("records");
                               MyApplication.log(LOG_TAG, "%%%" + jsonArray2Records2.length());
                                for (int j = 0; j < jsonArray2Records2.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records2.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String StandardName = jsonObject2RecordsChild.getString("StandardName");

                                    MyApplication.dbo.insertStandard(ClassID, StandardName);
                                   MyApplication.log(LOG_TAG, "%%%%--->" + Auto_Id + "-->" + id + "-->" + ClassID + "-->" + StandardName);
                                }*/


                                break;

                            case "subjecttable":

                                progressDialog.setMessage("Restoring data of subjects...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In branchtable---");


                               /* JSONArray jsonArray2Records3 = json.getJSONArray("records");
                               MyApplication.log(LOG_TAG, "%%%" + jsonArray2Records3.length());
                                for (int j = 0; j < jsonArray2Records3.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records3.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String SubjectName = jsonObject2RecordsChild.getString("SubjectName");

                                    MyApplication.dbo.insertSubject(ClassID, SubjectName);
                                   MyApplication.log(LOG_TAG, "%%%%--->" + Auto_Id + "-->" + id + "-->" + ClassID + "-->" + SubjectName);
                                }*/

                                break;

                            case "profiletable":

                                progressDialog.setMessage("Restoring data of profile...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In branchtable---");

                                /*JSONArray jsonArray2Records4 = json.getJSONArray("records");

                               MyApplication.log(LOG_TAG, "%%%" + jsonArray2Records4.length());
                                for (int j = 0; j < jsonArray2Records4.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records4.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String ClassName = jsonObject2RecordsChild.getString("ClassName");
                                    String BranchId = jsonObject2RecordsChild.getString("BranchId");
                                    String StandardId = jsonObject2RecordsChild.getString("StandardId");
                                    String BatchId = jsonObject2RecordsChild.getString("BatchId");
                                    String SubjectId = jsonObject2RecordsChild.getString("SubjectId");
                                    String flag_active = jsonObject2RecordsChild.getString("flag_active");

                                    MyApplication.dbo.insertProfileTable(ClassID, ClassName, BranchId, StandardId, BatchId, SubjectId, flag_active);


                                   MyApplication.log(LOG_TAG, "%%%%--->" + jsonObject2RecordsChild);
                                }*/

                                break;

                            case "insert_fee":

                                progressDialog.setMessage("Restoring data of fees...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In branchtable---");

                               /* JSONArray jsonArray2Records5 = json.getJSONArray("records");
                               MyApplication.log(LOG_TAG, "%%%" + jsonArray2Records5.length());
                                for (int j = 0; j < jsonArray2Records5.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records5.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String FeeID = jsonObject2RecordsChild.getString("FeeID");
                                    String Fee = jsonObject2RecordsChild.getString("Fee");
                                    String Subjects = jsonObject2RecordsChild.getString("Subjects");
                                    String Branch = jsonObject2RecordsChild.getString("Branch");
                                    String Standard = jsonObject2RecordsChild.getString("Standard");
                                    String Batch = jsonObject2RecordsChild.getString("Batch");


                                    MyApplication.dbo.insertFee(ClassID, FeeID, Fee, Subjects, Branch, Standard, Batch);


                                }*/

                                break;

                            case "insert_local_add_student":

                                progressDialog.setMessage("Restoring data of students...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In branchtable---");

                              /* MyApplication.log(LOG_TAG, "jsonlocal_add_student_success");
                                JSONArray jsonArray2Records6 = json.getJSONArray("records");
                               MyApplication.log(LOG_TAG, "insert_local_add_student length :" + jsonArray2Records6.length());
                               MyApplication.log(LOG_TAG, "jsonlocal_add_student" + jsonArray2Records6);

                                if (jsonArray2Records6.length() != 0)
                                    MyApplication.dbo.deleteStudents();

                                Log.d("rasika","record count of insert_local_add_student : "+ jsonArray2Records6.length());

                                for (int j = 0; j < jsonArray2Records6.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records6.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String Branch = jsonObject2RecordsChild.getString("Branch");
                                    String StudentName = jsonObject2RecordsChild.getString("StudentName");
                                    String Standard = jsonObject2RecordsChild.getString("Standard");
                                    String Batch = jsonObject2RecordsChild.getString("Batch");
                                    String DOB = jsonObject2RecordsChild.getString("DOA");
                                    String Address = jsonObject2RecordsChild.getString("Address");
                                    String Phone1 = jsonObject2RecordsChild.getString("Phone1");
                                    String Phone2 = jsonObject2RecordsChild.getString("Phone2");


                                    MyApplication.dbo.insert_local_student2(Auto_Id, id, ClassID, Branch, StudentName, Standard, Batch, DOB, Address, Phone1, Phone2);


                                }
*/
                                break;

                            case "student_subject":

                                progressDialog.setMessage("Restoring data of student's subjects ...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In branchtable---");

                              /*  JSONArray jsonArray2Records7 = json.getJSONArray("records");
                               MyApplication.log(LOG_TAG, "%%%" + jsonArray2Records7.length());
                               MyApplication.log(LOG_TAG, "student_subject" + jsonArray2Records7);
                                if (jsonArray2Records7.length() != 0)
                                    MyApplication.dbo.deleteStudent_subject();

                                for (int j = 0; j < jsonArray2Records7.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records7.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String StudentID = jsonObject2RecordsChild.getString("StudentID");
                                    String SubjectID = jsonObject2RecordsChild.getString("SubjectID");
                                    String StudentIdWithAll = jsonObject2RecordsChild.getString("StudentIdWithAll");


                                    MyApplication.dbo.insertStudent_Subject(ClassID, StudentID, SubjectID, StudentIdWithAll);


                                }
*/
                                break;

                            case "studentfeetable":

                                progressDialog.setMessage("Restoring data of student's fees...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In branchtable---");

                              /*  JSONArray jsonArray2Records8 = json.getJSONArray("records");
                               MyApplication.log(LOG_TAG, "%%%" + jsonArray2Records8.length());


                                for (int j = 0; j < jsonArray2Records8.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records8.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String StudentID = jsonObject2RecordsChild.getString("StudentID");
                                    String Standard = jsonObject2RecordsChild.getString("Standard");
                                    String FeesID = jsonObject2RecordsChild.getString("FeesID");
                                    String FeesPaid = jsonObject2RecordsChild.getString("FeesPaid");
                                    String Remark = jsonObject2RecordsChild.getString("Remark");
                                    String Balance = jsonObject2RecordsChild.getString("Balance");
                                    String Batch = jsonObject2RecordsChild.getString("Batch");
                                    String Date = jsonObject2RecordsChild.getString("Date");
                                    String Subjects = jsonObject2RecordsChild.getString("Subjects");
                                    String Branch = jsonObject2RecordsChild.getString("Branch");
                                    String PaymentStatus = jsonObject2RecordsChild.getString("PaymentStatus");


                                    MyApplication.dbo.updateRestore(ClassID, StudentID, Standard, FeesID, FeesPaid, Remark, Balance, Batch, Date, Subjects, Branch, PaymentStatus);


                                }
*/
                                break;

                            case "absentstudentregister":

                                progressDialog.setMessage("Restoring data of absent students...");
                               MyApplication.log(LOG_TAG, "HttpBackup: In branchtable---");

                               /* JSONArray jsonArray2Records9 = json.getJSONArray("records");
                               MyApplication.log(LOG_TAG, "%%%" + jsonArray2Records9.length());
                                for (int j = 0; j < jsonArray2Records9.length(); j++) {
                                    JSONObject jsonObject2RecordsChild = jsonArray2Records9.getJSONObject(j);
                                    String Auto_Id = jsonObject2RecordsChild.getString("Auto_Id");
                                    String id = jsonObject2RecordsChild.getString("id");
                                    String ClassID = jsonObject2RecordsChild.getString("ClassID");
                                    String StudentID = jsonObject2RecordsChild.getString("StudentID");
                                    String StudentName = jsonObject2RecordsChild.getString("StudentName");
                                    String Date = jsonObject2RecordsChild.getString("Date");
                                    String Branch = jsonObject2RecordsChild.getString("Branch");
                                    String Standard = jsonObject2RecordsChild.getString("Standard");
                                    String Batch = jsonObject2RecordsChild.getString("Batch");
                                    String Subjects = jsonObject2RecordsChild.getString("Subjects");
                                    String IsPresent = jsonObject2RecordsChild.getString("IsPresent");
                                    String SubjectOption = jsonObject2RecordsChild.getString("SubjectOption");


                                    MyApplication.dbo.insertAbsentStudentRegister(MyApplication.get_session("classid"), StudentID, StudentName, Date, Branch, Standard, Batch, Subjects, IsPresent, SubjectOption);


                                }*/

                                break;

                        }
                        String current_page = json.getString("current_page");
                       MyApplication.log(LOG_TAG, "HttpBackup: current_page---" + current_page);
                        String total_page = json.getString("total_page");
                       MyApplication.log(LOG_TAG, "HttpBackup: total_page---" + total_page);

                        if (current_page.equals(total_page)) {
                            page = 1;
                            paginationTablePos++;

                           MyApplication.log(LOG_TAG, "HttpBackup server: next table");
                           MyApplication.log(LOG_TAG, "In if of paginationTablePos<(jsonArray.length())");
                            if (paginationTablePos < (jsonArray.length())) {
                               MyApplication.log(LOG_TAG, "paginationTablePos---" + paginationTablePos);
                               MyApplication.log(LOG_TAG, "jsonArray.length()---" + jsonArray.length());
                               MyApplication.log(LOG_TAG, "In if of paginationTablePos<(jsonArray.length())");
                               MyApplication.log(LOG_TAG, "last loop " + paginationTablePos + " - " + jsonArray.length());
                                try {
                                    jObj = jsonArray.getString(paginationTablePos);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                               MyApplication.log(LOG_TAG, "HttpBackup: page count after increment ---" + page);
                                MyApplication.log(LOG_TAG, "class id = " + MyApplication.get_session("classid") + " table " + currentTable + " page = " + page);
                                HttpBackup serverCall = new HttpBackup(jObj.toString());
                                serverCall.execute();
                            } else {


                               MyApplication.log(LOG_TAG, "paginationTablePos---" + paginationTablePos);
                               MyApplication.log(LOG_TAG, "jsonArray.length()---" + jsonArray.length());
                               MyApplication.log(LOG_TAG, "In else of paginationTablePos<(jsonArray.length())");

                                Toast.makeText(context, getResources().getString(R.string.success_restore), Toast.LENGTH_LONG).show();
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                } else {

                                   MyApplication.log(LOG_TAG, "in else of progressdialog");
                                }

                            }

                        } else {

                           MyApplication.log(LOG_TAG, "HttpBackup server: next page");
                           MyApplication.log(LOG_TAG, "paginationTablePos---" + paginationTablePos);
                           MyApplication.log(LOG_TAG, "jsonArray.length()---" + jsonArray.length());
                           MyApplication.log(LOG_TAG, "In else");
                            page++;
                            try {
                                jObj = jsonArray.getString(paginationTablePos);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                           MyApplication.log(LOG_TAG, "HttpBackup: page count after increment ---" + page);
                            MyApplication.log(LOG_TAG, "class id = " + MyApplication.get_session("classid") + " table " + currentTable + " page = " + page);
                            HttpBackup serverCall = new HttpBackup(jObj.toString());
                            serverCall.execute();

                        }






                        //progressDialog.dismiss();
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.fail_restore), Toast.LENGTH_LONG).show();
                        //progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MyApplication.log(LOG_TAG, json + "");
            }
//            }
        }

        protected void onPostExecute(Long result) {

            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        }

    }// HTTP


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

}
