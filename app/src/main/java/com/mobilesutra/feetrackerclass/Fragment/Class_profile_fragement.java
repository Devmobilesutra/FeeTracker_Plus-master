package com.mobilesutra.feetrackerclass.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesutra.feetrackerclass.MyApplication;

import com.mobilesutra.feetrackerclass.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Class_profile_fragement.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Class_profile_fragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Class_profile_fragement extends Fragment {


    private EditText addLoc=null,addLevel=null,addBatch=null,addsubject=null;
    LinkedHashMap<String, String> lhm_branch = new LinkedHashMap<String, String>();//Branch
    private ImageView btn_loc=null,btn_level=null,btn_batch=null,btn_subj=null;
    Button define_class=null,btn_update=null;
    Context context=null;
    String flag="add",flag_branch="Y",flag_subj="Y",flag_batch="Y",flag_stand="Y";
    int flagText=0;
    CoordinatorLayout coordinatorLayout;
    LinearLayout branch=null,level=null,batch=null,sub=null;
    long branchid=0,subjid=0,batchid=0,Standid=0;
    Typeface roboto_light,roboto_reguler;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

  OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Class_profile_fragement.
     */
    // TODO: Rename and change types and number of parameters
    public static Class_profile_fragement newInstance(String param1, String param2) {
        Class_profile_fragement fragment = new Class_profile_fragement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Class_profile_fragement() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_class_profile_fragement2, container, false);

        context = container.getContext();

        roboto_light =  Typeface.createFromAsset(context.getAssets(),"fonts/robotocondensed-light.ttf" );
        roboto_reguler =  Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");


        addLoc=(EditText)view.findViewById(R.id.edit_branch);
        addLevel=(EditText)view.findViewById(R.id.edit_level);
        addBatch=(EditText)view.findViewById(R.id.edit_batch);
        addsubject=(EditText)view.findViewById(R.id.edit_subj);
        btn_loc=(ImageView)view.findViewById(R.id.btn_branch);
        btn_level=(ImageView)view.findViewById(R.id.btn_level);
        btn_batch=(ImageView)view.findViewById(R.id.btn_batch);
        btn_subj=(ImageView)view.findViewById(R.id.btn_subj);
        define_class=(Button)view.findViewById(R.id.define_class);
        btn_update=(Button)view.findViewById(R.id.btn_update);
        branch=(LinearLayout)view.findViewById(R.id.relsample);
        level=(LinearLayout)view.findViewById(R.id.sample_level);
        batch=(LinearLayout)view.findViewById(R.id.sample_batch);
        sub=(LinearLayout)view.findViewById(R.id.sample_subj);
//     coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout);

        TextView branch_text=(TextView)view.findViewById(R.id.branch_text);
        TextView level_text=(TextView)view.findViewById(R.id.level_text);
        TextView  batch_text=(TextView)view.findViewById(R.id.batch_text);
        TextView subj_text=(TextView)view.findViewById(R.id.subj_text);
        addLoc.setTypeface(roboto_reguler);
        addLevel.setTypeface(roboto_reguler);
        addBatch.setTypeface(roboto_reguler);
        addsubject.setTypeface(roboto_reguler);
        define_class.setTypeface(roboto_reguler);
        btn_update.setTypeface(roboto_reguler);
        batch_text.setTypeface(roboto_reguler);
        level_text.setTypeface(roboto_reguler);
        branch_text.setTypeface(roboto_reguler);
        subj_text.setTypeface(roboto_reguler);


        initComponentListeners();
        addBranchValue();
        addStandValue();
        addBatchValue();
        addSubValue();
        return  view;
    }
    public void initComponentListeners()
    {

        btn_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag.equals("add")) {

                    String Location = addLoc.getText().toString();
                    Log.d("Profile", "branch addede:" + Location);
                    if (Location.equals("")) {
                        if(flag_branch=="Y") {

                            addLoc.setEnabled(true);
                            addLoc.setClickable(true);
                            addLoc.setHint("");
                            addLoc.setBackgroundColor(getResources().getColor(R.color.white));
                            btn_loc.setImageResource(R.drawable.ic_done_white_24dp);
                            btn_loc.setBackgroundColor(getResources().getColor(R.color.box_color));
                            addLoc.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(addLoc, InputMethodManager.SHOW_IMPLICIT);
                            flag_branch="N";


                        }else
                        {

                            addLoc.setText("");
                            addLoc.setHint("add location");
                            addLoc.setEnabled(false);
                            addLoc.setClickable(false);

                            addLoc.setBackgroundColor(  getResources().getColor(R.color.white));
                            btn_loc.setBackgroundColor(getResources().getColor(R.color.box_color));
                            btn_loc.setImageResource(R.drawable.pluss);
                            flag_branch="Y";
                        }

                    } else {



                        branchid = MyApplication.dbo.insertBranch(MyApplication.get_session("classid"),Location);
                        Log.i("tag", "insert" + branchid);

                        lhm_branch.put(Location, branchid + "");

                        addLoc.setText("");
                        addLoc.setHint("add location");
                        addLoc.setEnabled(false);
                        addLoc.setClickable(false);
                        addLoc.setBackgroundColor(  getResources().getColor(R.color.white));
                        btn_loc.setBackgroundColor(getResources().getColor(R.color.box_color));
                        btn_loc.setImageResource(R.drawable.pluss);
                        addBranchValue();
                        flag_branch="Y";



                    }
                }else {

                    if(addLoc.getText().toString()!="") {
                        String update_id = MyApplication.dbo.update_branch(MyApplication.get_session("branchid"), addLoc.getText().toString());
                        addLoc.setText("");
                        addLoc.setHint("add location");
                        addLoc.setBackgroundColor(  getResources().getColor(R.color.white));
                        addLoc.setEnabled(false);
                        addLoc.setClickable(false);
                        btn_loc.setBackgroundColor(getResources().getColor(R.color.box_color));
                        btn_loc.setImageResource(R.drawable.pluss);
                        flag = "add";
                        addBranchValue();
                    }else
                    {
                        Toast.makeText(context,"delete",Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });
        btn_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag.equals("add")) {

                    String Level = addLevel.getText().toString();

                    if (Level.equals("")) {

                        if(flag_stand=="Y") {

                            addLevel.setEnabled(true);
                            addLevel.setClickable(true);
                            addLevel.setHint("");
                            addLevel.setBackgroundColor(getResources().getColor(R.color.white));
                            btn_level.setImageResource(R.drawable.ic_done_white_24dp);
                            btn_level.setBackgroundColor(getResources().getColor(R.color.box_color));
                            addLevel.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(addLevel, InputMethodManager.SHOW_IMPLICIT);
                            flag_stand = "N";
                        }else
                        {
                            addLevel.setText("");
                            addLevel.setHint("add Level");
                            addLevel.setEnabled(false);
                            addLevel.setClickable(false);
                            addLevel.setBackgroundColor(  getResources().getColor(R.color.white));
                            btn_level.setBackgroundColor(getResources().getColor(R.color.box_color));
                            btn_level.setImageResource(R.drawable.pluss);
                            flag_stand="Y";
                        }
                    } else {


                        Standid = MyApplication.dbo.insertStandard(MyApplication.get_session("classid"), Level);
                        Log.i("tag", "insert" + Standid);
                        addLevel.setText("");
                        addLevel.setHint("add Level");
                        addLevel.setEnabled(false);
                        addLevel.setClickable(false);
                        addLevel.setBackgroundColor(  getResources().getColor(R.color.white));
                        btn_level.setBackgroundColor(getResources().getColor(R.color.box_color));
                        btn_level.setImageResource(R.drawable.pluss);

                        addStandValue();


                    }


                } else {


                    String update_id = MyApplication.dbo.update_stand(MyApplication.get_session("standid"), addLevel.getText().toString());
                    addLevel.setText("");
                    addLevel.setHint("add level");
                    addLevel.setBackgroundColor(  getResources().getColor(R.color.white));
                    addLevel.setEnabled(false);
                    addLevel.setClickable(false);
                    btn_level.setBackgroundColor(getResources().getColor(R.color.box_color));
                    btn_level.setImageResource(R.drawable.pluss);
                    flag = "add";
                    addStandValue();

                }

            }
        });
        btn_subj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag.equals("add")) {

                    String subj = addsubject.getText().toString();

                    if (subj.equals("")) {

                        if(flag_subj=="Y") {
                            addsubject.setEnabled(true);
                            addsubject.setClickable(true);
                            addsubject.setHint("");
                            addsubject.setBackgroundColor(getResources().getColor(R.color.white));
                            btn_subj.setImageResource(R.drawable.ic_done_white_24dp);
                            btn_subj.setBackgroundColor(getResources().getColor(R.color.box_color));
                            addsubject.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(addsubject, InputMethodManager.SHOW_IMPLICIT);
                            flag_subj="N";
                        }else
                        {
                            addsubject.setText("");
                            addsubject.setHint("add subject");

                            addsubject.setEnabled(false);
                            addsubject.setClickable(false);
                            addsubject.setBackgroundColor(  getResources().getColor(R.color.white));
                            btn_subj.setBackgroundColor(getResources().getColor(R.color.box_color));
                            btn_subj.setImageResource(R.drawable.pluss);
                            flag_subj="Y";
                        }
                    } else {


                        subjid = MyApplication.dbo.insertSubject(MyApplication.get_session("classid"), subj);
                        Log.i("tag", "insert" + subjid);
                        addsubject.setText("");
                        addsubject.setHint("add subject");

                        addsubject.setEnabled(false);
                        addsubject.setClickable(false);
                        addsubject.setBackgroundColor(  getResources().getColor(R.color.white));
                        btn_subj.setBackgroundColor(getResources().getColor(R.color.box_color));
                        btn_subj.setImageResource(R.drawable.pluss);

                     addSubValue();


                    }


                } else {


                    String update_id = MyApplication.dbo.update_subj(MyApplication.get_session("subjid"), addsubject.getText().toString());
                    addsubject.setText("");
                    addsubject.setHint("add subject");
                    addsubject.setBackgroundColor(  getResources().getColor(R.color.white));
                    addsubject.setEnabled(false);
                    addsubject.setClickable(false);
                    btn_subj.setBackgroundColor(getResources().getColor(R.color.box_color));
                    btn_subj.setImageResource(R.drawable.pluss);

                    flag = "add";
                    addSubValue();

                }

            }
        });
        btn_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag.equals("add")) {

                    String batch = addBatch.getText().toString();

                    if (batch.equals("")) {
                        if(flag_batch=="Y") {
                            addBatch.setEnabled(true);

                            addBatch.setHint("");
                            addBatch.setBackgroundColor(getResources().getColor(R.color.white));
                            btn_batch.setImageResource(R.drawable.ic_done_white_24dp);
                            btn_batch.setBackgroundColor(getResources().getColor(R.color.box_color));
                            addBatch.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(addBatch, InputMethodManager.SHOW_IMPLICIT);
                            flag_batch="N";
                        }else
                        {
                            addBatch.setText("");
                            addBatch.setHint("add batch");

                            addBatch.setEnabled(false);
                            addBatch.setClickable(false);
                            addBatch.setBackgroundColor(  getResources().getColor(R.color.white));
                            btn_batch.setBackgroundColor(getResources().getColor(R.color.box_color));
                            btn_batch.setImageResource(R.drawable.pluss);
                            flag_batch="Y";
                        }
                    } else {


                        batchid = MyApplication.dbo.insertBatch(MyApplication.get_session("classid"), batch);
                        Log.i("tag", "insert" + batchid);
                        addBatch.setText("");
                        addBatch.setHint("add batch");

                        addBatch.setEnabled(false);
                        addBatch.setClickable(false);
                        addBatch.setBackgroundColor(  getResources().getColor(R.color.white));
                        btn_batch.setBackgroundColor(getResources().getColor(R.color.box_color));
                        btn_batch.setImageResource(R.drawable.pluss);

                        addBatchValue();


                    }


                } else {


                    String update_id = MyApplication.dbo.update_batch(MyApplication.get_session("batchid"), addBatch.getText().toString());
                    addBatch.setText("");
                    addBatch.setHint("add batch");
                    flag = "add";
                    addBatch.setBackgroundColor(  getResources().getColor(R.color.white));
                    addBatch.setEnabled(false);
                    addBatch.setClickable(false);
                    btn_batch.setBackgroundColor(getResources().getColor(R.color.box_color));
                    btn_batch.setImageResource(R.drawable.pluss);
                    addBatchValue();

                }

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createBatch();
                Toast.makeText(context, getResources().getString(R.string.success_activate_batches),
                        Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void addBranchValue() {
        branch.removeAllViews();
        ArrayList<HashMap<String, String>> l = new ArrayList<HashMap<String, String>>();
        l = MyApplication.dbo.getBranchList(MyApplication.get_session("classid"));
        Log.d("tag", "getbranch" + l);
        int a = l.size();
        for (int i = 0; i < a; i++) {
            flagText=1;
            addTextview(l.get(i).get("Bname"), l.get(i).get("Id"));


        }
    }
    public void addStandValue() {

            level.removeAllViews();
        ArrayList<HashMap<String, String>> l = new ArrayList<HashMap<String, String>>();
        l = MyApplication.dbo.getStand(MyApplication.get_session("classid"));
        Log.d("tag", "getbranch-" + l);
        int a = l.size();
        for (int i = 0; i < a; i++) {
            flagText=2;
            addTextview(l.get(i).get("Sname"), l.get(i).get("Id"));


        }
    }
    public void addBatchValue() {
        batch.removeAllViews();
        ArrayList<HashMap<String, String>> l = new ArrayList<HashMap<String, String>>();
        l = MyApplication.dbo.getbatch(MyApplication.get_session("classid"));
        Log.d("tag", "getbranch-" + l);
        int a = l.size();
        for (int i = 0; i < a; i++) {
            flagText=3;
            addTextview(l.get(i).get("Batname"),l.get(i).get("Id"));

        }
    }
    public void addSubValue() {
        sub.removeAllViews();
        ArrayList<HashMap<String,String>> l = new ArrayList<HashMap<String,String>>();
        l = MyApplication.dbo.getSub(MyApplication.get_session("classid"));
        Log.d("tag", "getSub-" + l);
        int a = l.size();
        for (int i = 0; i < a; i++) {
            flagText=4;
            addTextview(l.get(i).get("Subname"),l.get(i).get("Id"));

        }

    }

   public void createBatch()
    {

        ArrayList<HashMap<String, String>> branchl = new ArrayList<HashMap<String, String>>();
        branchl = MyApplication.dbo.getBranchList(MyApplication.get_session("classid"));
        Log.d("tag", "getbranch" + branchl);
        int branch_size=branchl.size();

        ArrayList<HashMap<String, String>> standl = new ArrayList<HashMap<String, String>>();
        standl = MyApplication.dbo.getStand(MyApplication.get_session("classid"));
        Log.d("tag", "getbranch-" + standl);
        int stand_size=standl.size();
        ArrayList<HashMap<String, String>> batchl = new ArrayList<HashMap<String, String>>();
        batchl = MyApplication.dbo.getbatch(MyApplication.get_session("classid"));
        Log.d("tag", "getbranch-" + batchl);
        int batch_size=batchl.size();
        ArrayList<HashMap<String,String>> subjl = new ArrayList<HashMap<String,String>>();
        subjl = MyApplication.dbo.getSub(MyApplication.get_session("classid"));
        Log.d("tag", "getSub-" + subjl);
        int subj_size=subjl.size();


        for(int a=0;a<branch_size;a++)
        {
            String branchid=branchl.get(a).get("Id");
            for(int b=0;b<stand_size;b++)
            {
                String standid=standl.get(b).get("Id");
                for(int c=0;c<batch_size;c++)
                {
                    String batchid=batchl.get(c).get("Id");
                    for(int d=0;d<subj_size;d++)
                    {
                             String subjid=subjl.get(d).get("Id");
                             MyApplication.dbo.insertProfileTable(
                                     MyApplication.get_session("classid"), "", branchid,
                                     standid, batchid, subjid, "Y");



                    }
                }
            }
        }
        mListener.onFragmentInteraction(2);

    }

    public void addTextview(String text,String id)
    {

        LayoutInflater inflater = (LayoutInflater) getActivity().getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v_child = inflater.inflate(R.layout.addlayout, null);


        final TextView addbranch= (TextView) v_child.findViewById(R.id.add_details);
        final TextView autoid= (TextView) v_child.findViewById(R.id.add_details);
        final TextView ID= (TextView) v_child.findViewById(R.id.add_rid);
        final ImageView edit= (ImageView) v_child.findViewById(R.id.edit_details);
        final ImageView delete= (ImageView) v_child.findViewById(R.id.delete_details);


        addbranch.setTypeface(roboto_reguler);

        switch (flagText) {

            case 1: {

                addbranch.setText(text);
                ID.setText(id);
                branch.addView(v_child);
                addbranch.setBackgroundColor(getResources().getColor(R.color.white));
                edit.setBackgroundColor(getResources().getColor(R.color.white));
                delete.setBackgroundColor(getResources().getColor(R.color.white));


                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setTitle("Delete Confirm");
                        alertDialog.setMessage("Do you want to delete ?");
                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                int a = MyApplication.dbo.deleteBranch(ID.getText().toString());
                                addBranchValue();
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
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MyApplication.set_session("branchid", ID.getText().toString());
                        addLoc.setText(addbranch.getText().toString());
                        flag = "edit";
                        addLoc.requestFocus();
                        addLoc.setBackgroundColor(getResources().getColor(R.color.white));
                        addLoc.setEnabled(true);
                        addLoc.setClickable(true);
                        btn_loc.setImageResource(R.drawable.ic_done_white_24dp);
                        btn_loc.setBackgroundColor(getResources().getColor(R.color.box_color));

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(addLoc, InputMethodManager.SHOW_IMPLICIT);

                    }
                });
                addbranch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addLoc.setText(addbranch.getText().toString());

                    }
                });




                break;
            }
            case 2: {
                addbranch.setText(text);
                ID.setText(id);
                level.addView(v_child);
                addbranch.setBackgroundColor(getResources().getColor(R.color.white));
                edit.setBackgroundColor(getResources().getColor(R.color.white));
                delete.setBackgroundColor(getResources().getColor(R.color.white));
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setTitle("Delete Confirm");
                        alertDialog.setMessage("Do you want to delete ?");
                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                int a = MyApplication.dbo.deleteStand(ID.getText().toString());
                                addStandValue();
                            }
                        });


                        // Setting Negative "NO" Button
                        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.show();


//

                    }
                });
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MyApplication.set_session("standid", ID.getText().toString());
                        addLevel.setText(addbranch.getText().toString());
                        flag = "edit";
                        addLevel.requestFocus();
                        addLevel.setBackgroundColor(getResources().getColor(R.color.white));
                        addLevel.setEnabled(true);
                        addLevel.setClickable(true);
                        btn_level.setImageResource(R.drawable.ic_done_white_24dp);
                        btn_level.setBackgroundColor(getResources().getColor(R.color.box_color));

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(addLevel, InputMethodManager.SHOW_IMPLICIT);

                    }
                });


                break;
            }
            case 3: {
                addbranch.setText(text);
                ID.setText(id);
                batch.addView(v_child);
                addbranch.setBackgroundColor(getResources().getColor(R.color.white));
                edit.setBackgroundColor(getResources().getColor(R.color.white));
                delete.setBackgroundColor(getResources().getColor(R.color.white));

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setTitle("Delete Confirm");
                        alertDialog.setMessage("Do you want to delete ?");
                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                int a = MyApplication.dbo.deletebatch(ID.getText().toString());
                          addBatchValue();
                            }
                        });


                        // Setting Negative "NO" Button
                        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.show();


//batch.removeView(v_child);
                    }
                });
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MyApplication.set_session("batchid", ID.getText().toString());
                        addBatch.setText(addbranch.getText().toString());
                        flag = "edit";

                        addBatch.setBackgroundColor(getResources().getColor(R.color.white));
                        addBatch.setEnabled(true);
                        addBatch.setClickable(true);
                        btn_batch.setImageResource(R.drawable.ic_done_white_24dp);
                        btn_batch.setBackgroundColor(getResources().getColor(R.color.box_color));
                        addBatch.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(addBatch, InputMethodManager.SHOW_IMPLICIT);

                    }
                });

                break;
            }
            case 4: {
                addbranch.setText(text);
                ID.setText(id);
                sub.addView(v_child);
                addbranch.setBackgroundColor(getResources().getColor(R.color.white));
                edit.setBackgroundColor(getResources().getColor(R.color.white));
                delete.setBackgroundColor(getResources().getColor(R.color.white));


                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setTitle("Delete Confirm");
                        alertDialog.setMessage("Do you want to delete ?");
                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                int a = MyApplication.dbo.deletesubj(ID.getText().toString());
                               addSubValue();
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
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MyApplication.set_session("subjid", ID.getText().toString());
                        addsubject.setText(addbranch.getText().toString());
                        flag = "edit";
                        addsubject.requestFocus();

                        addsubject.setBackgroundColor(getResources().getColor(R.color.white));
                        addsubject.setEnabled(true);
                        addsubject.setClickable(true);
                        btn_subj.setImageResource(R.drawable.ic_done_white_24dp);
                        btn_subj.setBackgroundColor(getResources().getColor(R.color.box_color));
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(addsubject, InputMethodManager.SHOW_IMPLICIT);

                    }
                });
            }

            break;

        }



    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Bundle args = getArguments();

       try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onStart() {
        Log.d("tag2", "onStart");

        super.onStart();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onStop() {
        Log.d("tag2", "onStop");

        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onPause() {
        Log.d("tag2", "onPause");

        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onResume() {
        Log.d("tag2", "onResume");

        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onDestroyView() {
        Log.d("tag2", "onDestroyView");

        super.onDestroyView();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onDestroy() {
        Log.d("tag2", "onDestroy");

        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onDetach() {
        Log.d("tag2", "onDetach");

        super.onDetach();    //To change body of overridden methods use File | Settings | File Templates.
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
        public void onFragmentInteraction(int uri);





    }


}
