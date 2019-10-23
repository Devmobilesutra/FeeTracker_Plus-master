package com.mobilesutra.feetrackerclass.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.mobilesutra.feetrackerclass.Database;
import com.mobilesutra.feetrackerclass.ListModel;
import com.mobilesutra.feetrackerclass.MyApplication;
import com.mobilesutra.feetrackerclass.R;
import android.util.Base64;
import com.mobilesutra.feetrackerclass.model.StudentInfo;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.mobilesutra.feetrackerclass.activities.ActivityAttendance.LOG_TAG;

public class ActivityStudentManagementList extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    ArrayList<HashMap<String, String>> menu_list;
    ArrayList<ListModel> rowItems1 = new ArrayList<ListModel>();

    Typeface roboto_reguler,roboto_light;
    List<ListModel> arrayDTO = null;
    RecyclerView recycler_view = null;
    RecyclerAdapter recyclerAdapter = null;
    Boolean flag_check;

    LinkedHashMap<String, String> lhm_branch = new LinkedHashMap<String, String>();// Branch
    LinkedHashMap<String, String> lhm_std = new LinkedHashMap<String, String>();// Std
    LinkedHashMap<String, String> lhm_batch = new LinkedHashMap<String, String>();// Batch
    LinkedHashMap<String, String> lhm_sub = new LinkedHashMap<String, String>();// Subjects
    String selected_branch_value="",selected_stand_value="",selected_batch_value="",selected_subj_value="",prv_stand_value="",prv_batch_value="";
    ArrayList<String> arrstand1 ;
    ArrayList<String> arrbatch ;
    ArrayList<String> arraysubj ;
    List<String> SelectedSubj=new ArrayList<>();
    Boolean  flag=false,flag_data=true;
    Context context = this;

    TextView txt_class_name,txt_activity_header;
    LinearLayoutManager linearLayoutManager;
    ImageView img_search,img_add;
    EditText edit_student_search;
    Spinner spnr_branch=null,spnr_stand=null,spnr_batch=null,spnr_subject=null;
    int textlength=0;
    int permission_count = 1;
    private ArrayList<StudentInfo> studentInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_management_list);

        context = this;

        if(MyApplication.is_marshmellow()) {
            int count = getPermissionCount();
            if( count > 0) {
                check_app_persmission();
            }
        }

        initComponents();
        initComponentListeners();
        initSpinnerData();
        bindComponentData();
    }



    private void initComponents() {

        roboto_light =  Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
        roboto_reguler =  Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");
        txt_class_name = (TextView) findViewById(R.id.txt_class_name);
        txt_activity_header = (TextView) findViewById(R.id.txt_activity_header);
        edit_student_search = (EditText) findViewById(R.id.edit_student_search);
        txt_activity_header.setTypeface(roboto_reguler);
        txt_activity_header.setText("Student Management");
        txt_class_name.setText(MyApplication.get_session("classname"));
        menu_list = new ArrayList<HashMap<String, String>>();
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        arrayDTO = new ArrayList<ListModel>();
        linearLayoutManager = new LinearLayoutManager(ActivityStudentManagementList.this);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setHasFixedSize(true);
        img_search = (ImageView) findViewById(R.id.img_search);
        img_add = (ImageView) findViewById(R.id.img_add);
        spnr_branch = (Spinner) findViewById(R.id.spnr_branch);
        spnr_stand = (Spinner) findViewById(R.id.spnr_stand);
        spnr_batch = (Spinner) findViewById(R.id.spnr_batch);
        spnr_subject = (Spinner) findViewById(R.id.spnr_subject);

        img_search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!flag) {
                    edit_student_search.setVisibility(View.VISIBLE);
                    flag = true;
                } else {
                    edit_student_search.setVisibility(View.GONE);
                    flag = false;
                }


            }
        });
        img_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i = new Intent(ActivityStudentManagementList.this, addStudent.class);
                i.putExtra("autoid", "");
                i.putExtra("flag", "flag");
                startActivity(i);
                finish();


            }
        });

        edit_student_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {


                //NAMRATA changes
                String branch = spnr_branch.getSelectedItem().toString();
                String standrd = spnr_stand.getSelectedItem().toString();
                String batch = spnr_batch.getSelectedItem().toString();
                String subject = spnr_subject.getSelectedItem().toString();
                MyApplication.log(LOG_TAG,"spnr_branch->"+branch+"-"+standrd+"-"+batch+"-"+subject);



                MyApplication.log("","Ids of branch1="+MyApplication.dbo.getBranchID(branch));
                MyApplication.log("","Ids of standerd1="+MyApplication.dbo.getBatchID(batch));
                MyApplication.log("","Ids of batch1="+MyApplication.dbo.getStandID(standrd));
                MyApplication.log("","Ids of subject1="+MyApplication.dbo.getSubjectId(subject));



                textlength = edit_student_search.getText().length();
                ArrayList<ListModel> row = new ArrayList<ListModel>();
                ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

                data = ((MyApplication) getApplication()).dbo.GetStudentData1(MyApplication.dbo.getBranchID(branch)
                        ,MyApplication.dbo.getBatchID(batch)
                        ,MyApplication.dbo.getStandID(standrd)
                        ,MyApplication.dbo.getSubjectId(subject));


//                data = ((MyApplication) getApplication()).dbo.GetStudentData();

                for (int i = 0; i < data.size(); i++) {
                    String name = data.get(i).get("sname").toString();
                    String searchtext = edit_student_search.getText().toString();
                    if (textlength <= name.length()) {
                        if (name.toLowerCase().indexOf(searchtext.toLowerCase()) != -1) {


                            ListModel data1 = new ListModel(data.get(i).get("id"), data.get(i).get("auto_id"), data.get(i).get("sname"), data.get(i).get("phone1"), data.get(i).get("studphone"), data.get(i).get("std"), data.get(i).get("Batch"), data.get(i).get("Branch"), data.get(i).get("date"), "");


                            row.add(data1);
                        }
                    }
                }



                recyclerAdapter = new RecyclerAdapter(row);
                recycler_view.setAdapter(recyclerAdapter);

            }
        });
    }

    private void initComponentListeners() {

    }

    private void initSpinnerData() {
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

                    selected_branch_value = lhm_branch.get(spnr_branch.getSelectedItem().toString());
                    MyApplication.set_session(MyApplication.session_branch, selected_branch_value);//**
                    bindComponentData();
//                    flag_check = MyApplication.dbo.checkFeeCombination(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value,prv_stand_value);
////                        if(flag_check==true) {
////                            addstud.setEnabled(true);
////                            addstud.setImageResource(R.drawable.add_black);
////                        }
////                        else {
////                            addstud.setEnabled(false);
////                            addstud.setImageResource(R.drawable.add_gray);
////                        }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            int count1 = lhm_branch.size();
            if (count1 != 0) {
                selected_branch_value = lhm_branch.get(spnr_branch.getSelectedItem().toString());
                if (MyApplication.get_session(MyApplication.session_branch).equals(""))
                    MyApplication.set_session(MyApplication.session_branch, selected_branch_value);

            }
            if (flag_data) {

                String branchname = MyApplication.dbo.getBranchName(MyApplication.get_session(MyApplication.session_branch));
                int position = arrBranch.indexOf(branchname);
//                Toast.makeText(context,"value "+MyApplication.get_session(MyApplication.session_branch)+position,Toast.LENGTH_SHORT).show();
                spnr_branch.setSelection(position, true);
                selected_branch_value = lhm_branch.get(spnr_branch.getSelectedItem().toString());
                MyApplication.set_session(MyApplication.session_branch, selected_branch_value);

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
                    MyApplication.set_session(MyApplication.session_stand, selected_stand_value);//**

                    bindComponentData();


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });


            int count2 = lhm_std.size();
            if (count2 != 0) {
                selected_stand_value = lhm_std.get(spnr_stand.getSelectedItem().toString());
                if (MyApplication.get_session(MyApplication.session_stand).equals(""))
                    MyApplication.set_session(MyApplication.session_stand, selected_stand_value);//**
            }

            if (flag_data) {

                String standname = MyApplication.dbo.getStandardName(MyApplication.get_session(MyApplication.session_stand));
                int position = arrstand1.indexOf(standname);

                spnr_stand.setSelection(position, true);
                selected_stand_value = lhm_std.get(spnr_stand.getSelectedItem().toString());
                MyApplication.set_session(MyApplication.session_stand, selected_stand_value);

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
                    MyApplication.set_session(MyApplication.session_batch, selected_batch_value);//**

                    bindComponentData();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }

            });

            int count3 = lhm_batch.size();
            if (count3 != 0) {
                selected_batch_value = lhm_batch.get(spnr_batch.getSelectedItem().toString());
                if (MyApplication.get_session(MyApplication.session_batch).equals(""))
                    MyApplication.set_session(MyApplication.session_batch, selected_batch_value);//**

            }

            if (flag_data) {

                String batchname = MyApplication.dbo.getbatchhhName(MyApplication.get_session(MyApplication.session_batch));
                int position = arrbatch.indexOf(batchname);
                //      Toast.makeText(context,"batchname-> "+MyApplication.get_session(MyApplication.session_batch)+batchname,Toast.LENGTH_SHORT).show();
                spnr_batch.setSelection(position, true);
                selected_batch_value = lhm_batch.get(spnr_batch.getSelectedItem().toString());
                MyApplication.set_session(MyApplication.session_batch, selected_batch_value);

            }


            lhm_sub = MyApplication.dbo.getSubjectWithInsertFee(MyApplication.get_session("classid"));

            log(lhm_sub + "");
            arraysubj = new ArrayList<String>();
            for (Object o : lhm_sub.keySet()) {
                arraysubj.add(o.toString());
                System.out.println("batch-key:" + o.toString() + "___" + "value:" + lhm_sub.get(o).toString());
            }
            ArrayAdapter<String> adaptersub = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arraysubj);
            adaptersub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnr_subject.setAdapter(adaptersub);

            spnr_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selected_subj_value = lhm_sub.get(spnr_subject.getSelectedItem().toString());
                    MyApplication.set_session(MyApplication.session_subj, selected_subj_value);//**

                    bindComponentData();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }

            });


            int count4 = lhm_sub.size();
            if (count4 != 0) {
                selected_subj_value = lhm_sub.get(spnr_subject.getSelectedItem().toString());
                if (MyApplication.get_session(MyApplication.session_subj).equals(""))
                    MyApplication.set_session(MyApplication.session_subj, selected_subj_value);//**
                //  Toast.makeText(context,"value "+selected_subj_value,Toast.LENGTH_SHORT).show();
            }

            if (flag_data) {

                String subjname = ((MyApplication) getApplication()).dbo.getSubjjjnameWithMultilpe(MyApplication.get_session(MyApplication.session_subj));//get selected subj name
                int position = arraysubj.indexOf(subjname);

                spnr_subject.setSelection(position, true);

                if (lhm_sub.size() != 0)
                    selected_subj_value = lhm_sub.get(spnr_subject.getSelectedItem().toString());
//                Toast.makeText(context,"value "+selected_subj_value,Toast.LENGTH_SHORT).show();
                MyApplication.set_session(MyApplication.session_subj, selected_subj_value);

            }
        }
    }


    private void bindComponentData()
    {
            flag_check = MyApplication.dbo.checkFeeCombination(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value, selected_subj_value);
            if (flag_check == true) {
                img_add.setEnabled(true);
                img_add.setImageResource(R.drawable.ic_person_add_black_24dp);
            } else {
                img_add.setEnabled(false);
                img_add.setImageResource(R.drawable.ic_person_add_black_grey);
            }


            prv_stand_value = "";
            int num = SelectedSubj.size();
            String subjectid = "", id = "";
            for (int j = 0; j < num; j++) {
                subjectid = lhm_sub.get(SelectedSubj.get(j));
                if (id.equals("")) {
                    id = subjectid;
                } else {
                    id = id + "," + subjectid;
                }
                Log.i("Database", "subjectid" + id);
            }
            prv_stand_value = id;
//        MyApplication.set_session("session_subj",prv_stand_value);
            Log.i("Database", "prv_stand_value" + prv_stand_value);
//        flag_check = MyApplication.dbo.checkFeeCombination(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value,selected_subj_value);
//        if(flag_check==true)
//            addstud.setEnabled(true);
//        else
//            addstud.setEnabled(flag);


            menu_list = null;
            menu_list = new ArrayList<HashMap<String, String>>();
            menu_list = ((MyApplication) getApplication()).dbo.GetStudentDataNew(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value, selected_subj_value);
            Log.i("tag", "getstud" + menu_list);
            rowItems1 = new ArrayList<ListModel>();
            int records = menu_list.size();
            for (int i = 0; i < records; i++) {


                ListModel item = new ListModel(menu_list.get(i).get("id").toString(),
                        menu_list.get(i).get("auto_id").toString(),
                        menu_list.get(i).get("sname").toString(),
                        menu_list.get(i).get("studentprofile"),
                        menu_list.get(i).get("phone1").toString(),
                        menu_list.get(i).get("studphone").toString(),
                        menu_list.get(i).get("std").toString(),
                        menu_list.get(i).get("Batch").toString(),
                        menu_list.get(i).get("Branch").toString(),
                        menu_list.get(i).get("date").toString(),
                        "");

                rowItems1.add(item);
                Log.i("rowItems1:id", "" + rowItems1.get(i).getId());
                Log.i("rowItems1:name", "" + rowItems1.get(i).getName());
                Log.i("rowItems1:name", "" + rowItems1.get(i).getPhone1());
                Log.i("rowItems1:name", "" + rowItems1.get(i).getStudphone());
                Log.i("rowItems1:std", "" + rowItems1.get(i).getStandard());
                Log.i("rowItems1:batch", "" + rowItems1.get(i).getBatch());
                Log.i("rowItems1:branch", "" + rowItems1.get(i).getBranch());
            }


            recyclerAdapter = new RecyclerAdapter(rowItems1);
            recycler_view.setAdapter(recyclerAdapter);

           /* studentInfoList = Database.getStudentInfo(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value, selected_subj_value);
            recyclerAdapter = new RecyclerAdapter(rowItems1);
            recycler_view.setAdapter(recyclerAdapter);*/


            MyApplication.set_session("session_branch", selected_branch_value);
            MyApplication.set_session("session_stand", selected_stand_value);
            MyApplication.set_session("session_batch", selected_batch_value);
            MyApplication.set_session("session_subj", selected_subj_value);
        }


    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        List<ListModel> rowItems;
        String strappid = "";
        int temp = 0;

        public RecyclerAdapter(List<ListModel> rowItems) {
            this.rowItems = rowItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            RecyclerView.ViewHolder vh;
            final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mylist, viewGroup, false);
            vh = new MyViewHolder(view);

            return vh;
        }


        public void Remove(int pos)
        {
            rowItems.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(0,rowItems.size());
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final ListModel row_pos = rowItems.get(position);

            ((MyViewHolder) holder).sname.setText(row_pos.getName());
            String stdname = ((MyApplication) getApplication()).dbo.getStandardName(row_pos.getStandard());
            String brnchname = ((MyApplication) getApplication()).dbo.getBranchName(row_pos.getBranch());
            String batch = ((MyApplication) getApplication()).dbo.getbatchhhName(row_pos.getBatch());
            String autoid = ((MyApplication) getApplication()).dbo.getSId(row_pos.getId());
            String subjname = ((MyApplication) getApplication()).dbo.getSubjjjnameWithMultilpe(autoid);
            Log.i("tag", "value_subject" + autoid + "=" + subjname);
            ((MyViewHolder) holder).img_edit.setTag(position);
            ((MyViewHolder) holder).std.setText(stdname);
            ((MyViewHolder) holder).branch.setText(brnchname);
            ((MyViewHolder) holder).batch.setText(batch);
            ((MyViewHolder) holder).subj.setText(subjname);
            ((MyViewHolder) holder).img_call1.setContentDescription(row_pos.getPhone1());
            ((MyViewHolder) holder).img_call2.setContentDescription(row_pos.getStudphone());
            ((MyViewHolder) holder).img_delete.setTag(position);
            ((MyViewHolder) holder).date.setText(row_pos.getDate());
            ((MyViewHolder) holder).stud_id.setText(row_pos.getAuto_id());



           Log.i("tag", "getProfile is " + row_pos.getName() +" "+row_pos.getProfile());

            if (row_pos.getProfile() != null)
            {
               // Log.i("tag", "getProfile is not null" + row_pos.getName() +" "+row_pos.getProfile());
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

                    Bitmap bitmap = MyApplication.decodeBase64Profile(row_pos.getProfile());
                    ((MyViewHolder) holder).ivProfile.setImageBitmap(bitmap);
                }

            }

            ((MyViewHolder) holder).img_delete.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("")
                                    .setMessage("Are you sure you want to delete this student?")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Yes button clicked, do something

                                            ImageView v1 = (ImageView) v;
                                            ListModel item = rowItems.get((Integer) v1.getTag());
                                            //   Toast.makeText(context, "" + item.getId(), Toast.LENGTH_SHORT).show();

                                         ((MyApplication) getApplication()).dbo.DeleteStudentFeeTable(item.getId());
                                         ((MyApplication) getApplication()).dbo.deleteStudData(item.getId());

                                         Remove(position);

                                        }
                                    })
                                    .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }).show();
                        }
                    }
            );



            ((MyViewHolder) holder).img_edit.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            ImageView v1 = (ImageView) v;
                            ListModel item = rowItems.get((Integer) v1.getTag());

//                            Toast.makeText(context, "value id" + v.getTag(),Toast.LENGTH_SHORT).show();  ;
                            Intent intent=new Intent(context,addStudent.class);
                            intent.putExtra("autoid",item.getId());
                            intent.putExtra("local_id",item.getAuto_id());
                            intent.putExtra("flag", "edit");
                            startActivity(intent);
                            finish();

                            Remove(position);
                        }
                    }
            );

            ((MyViewHolder) holder).img_call1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(MyApplication.is_marshmellow()) {
                        int count = getPermissionCount();
                        if( count > 0) {
                            check_app_persmission();
                        }
                        else {
                            if (!row_pos.getPhone1().equals("")) {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + row_pos.getPhone1()));
                                startActivity(callIntent);
                            } else {
                                Toast.makeText(ActivityStudentManagementList.this, "No Parent Phone Number",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    else {
                        if (!row_pos.getPhone1().equals("")) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + row_pos.getPhone1()));
                            startActivity(callIntent);
                        } else {
                            Toast.makeText(ActivityStudentManagementList.this, "No Parent Phone Number",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

            ((MyViewHolder) holder).img_call2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(MyApplication.is_marshmellow()) {
                        int count = getPermissionCount();
                        if( count > 0) {
                            check_app_persmission();
                        }
                        else {
                            if (!row_pos.getStudphone().equals("")) {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + row_pos.getStudphone()));
                                startActivity(callIntent);
                            } else {
                                Toast.makeText(ActivityStudentManagementList.this, "No Student Phone Number",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    else {
                        if (!row_pos.getStudphone().equals("")) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + row_pos.getStudphone()));
                            startActivity(callIntent);
                        } else {
                            Toast.makeText(ActivityStudentManagementList.this, "No Student Phone Number",
                                    Toast.LENGTH_LONG).show();
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

            public TextView sname, std,branch,batch,subj,autoid,date,stud_id;
            CardView cardview;
            Button img_call1, img_call2;
            ImageView img_delete,img_edit;
            ImageView ivProfile;

            public MyViewHolder(View v) {
                super(v);
                cardview = (CardView) v.findViewById(R.id.cardview);
                stud_id = (TextView) v.findViewById(R.id.edit_stud_auto_id);
                sname = (TextView) v.findViewById(R.id.edit_stud_name);
                ivProfile = (ImageView)v.findViewById(R.id.ivProfile);
                date = (TextView) v.findViewById(R.id.text_date);
                std = (TextView) v.findViewById(R.id.edit_Stand_name);
                branch = (TextView) v.findViewById(R.id.edit_branch_name);
                batch = (TextView) v.findViewById(R.id.edit_batch_name);
                subj = (TextView) v.findViewById(R.id.edit_Sub_name);
                autoid = (TextView) v.findViewById(R.id.auto_id);
                img_delete = (ImageView) v.findViewById(R.id.img_delete);
                img_edit = (ImageView) v.findViewById(R.id.img_edit);
                img_call1 = (Button) v.findViewById(R.id.img_phone1);
                img_call2 = (Button) v.findViewById(R.id.stud_phone);
                stud_id.setTypeface(roboto_reguler);
                sname.setTypeface(roboto_reguler);
                std.setTypeface(roboto_reguler);
                branch.setTypeface(roboto_reguler);
                batch.setTypeface(roboto_reguler);
                subj.setTypeface(roboto_reguler);
                date.setTypeface(roboto_reguler);
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
            Log.i("addstudent", str.substring(0, 4000));
            log(str.substring(4000));
        } else
            Log.i("addstudent", str);
    }
    public static void log(String LOG_TAG,String str) {
        if (str.length() > 4000) {
            Log.i(LOG_TAG, str.substring(0, 4000));
            log(str.substring(4000));
        } else
            Log.i(LOG_TAG, str);
    }

    private void check_app_persmission() {
        permission_count = 1;
        int permission_granted = PackageManager.PERMISSION_GRANTED;
        int call_permission = checkSelfPermission(Manifest.permission.CALL_PHONE);
        if(call_permission == permission_granted)
            permission_count -= 1;
        if(permission_count > 0)
        {
            String permissionArray[] = new String[permission_count];

            for(int i = 0; i < permission_count; i++) {
                if (call_permission != permission_granted) {
                    if(!Arrays.asList(permissionArray).contains(Manifest.permission.CALL_PHONE)) {
                        permissionArray[i] = Manifest.permission.CALL_PHONE;
                    }
                }
            }
            ActivityCompat.requestPermissions(ActivityStudentManagementList.this, permissionArray, permission_count);
        }
    }

    public int getPermissionCount()
    {
        int count = 1;
        int permission_granted = PackageManager.PERMISSION_GRANTED;
        int call_permission = checkSelfPermission(Manifest.permission.CALL_PHONE);
        if (call_permission == permission_granted)
            count -= 1;
        return count;
    }


    @Override
    public void onBackPressed() {

        Intent i = new Intent(ActivityStudentManagementList.this,ActivityDashboard.class);
        startActivity(i);
        finish();
    }
}
