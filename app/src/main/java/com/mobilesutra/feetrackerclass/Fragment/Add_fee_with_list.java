package com.mobilesutra.feetrackerclass.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesutra.feetrackerclass.FAB.FloatingActionButton;
import com.mobilesutra.feetrackerclass.ListModel;
import com.mobilesutra.feetrackerclass.MyApplication;
import com.mobilesutra.feetrackerclass.R;
import com.mobilesutra.feetrackerclass.activities.Activity_profileNew;
import com.mobilesutra.feetrackerclass.activities.MultiSelectionSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Add_fee_with_list.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Add_fee_with_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add_fee_with_list extends Fragment implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {


    FloatingActionButton fab_filter, fab_add;

    ListView list_info;
    List<String> menuItems = new ArrayList();
    ArrayList<HashMap<String, String>> menu_list;
    ArrayList<ListModel> rowItems1 = new ArrayList<ListModel>();
    CustomAdapter customAdapter;
    EditText edittext;
    int size = 0;
    int index, top;
    int textlength = 0;
    Context context;
    Typeface roboto_light, roboto_reguler;

    boolean flagdialog = false;


    TextView Subject_Fee_Text;
    Spinner spinner_branch = null, spinner_stand = null, spinner_batch = null, spinner_subj = null;
    Button submit = null;
    EditText fee_amount = null;

    MultiSelectionSpinner multiSelectionSpinner;
    ArrayList<String> my_array, list, fee_array;
    String selected_branch_value = "", selected_stand_value = "", selected_batch_value = "", edit_branch = "", rowid = "", edit_batch, edit_stand, autoid = "", checkedsubj = "", edit_fee = "", edit_subj = "", selected_subjects_name = "";
    Boolean allchecked = true;
    List<String> SelectedSubj = new ArrayList<>();

    int feeid = 0;
    int checkedid = 0;
    String[] array2;
    ProgressDialog dialog1;
    Boolean flag1 = false;
    String flag = "True";
    LinkedHashMap<String, String> lhm_branch = new LinkedHashMap<String, String>();// Branch
    LinkedHashMap<String, String> lhm_std = new LinkedHashMap<String, String>();// Std
    LinkedHashMap<String, String> lhm_batch = new LinkedHashMap<String, String>();// Batch
    LinkedHashMap<String, String> lhm_sub = new LinkedHashMap<String, String>();// Subjects
    ArrayList<HashMap<String, String>> menu_list1;
    ArrayList<String> arrstand1;
    ArrayList<String> arrbatch;

    LinkedHashMap<String, String> lhm_checkedSub = new LinkedHashMap<String, String>();// Subjects
    static String str = "", key_str = "key_str";
    Boolean flag_data = true;
    String branchid = "", batchid = "", stdid = "";


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
     * @return A new instance of fragment Add_fee_with_list.
     */
    // TODO: Rename and change types and number of parameters
    public static Add_fee_with_list newInstance(String param1, String param2) {
        Add_fee_with_list fragment = new Add_fee_with_list();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putCharSequence(key_str, param1);
        str = param1;
        return fragment;
    }

    public Add_fee_with_list() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (bundle != null) {
            str = bundle.getCharSequence(key_str).toString();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_fee_with_list, container, false);

        context = container.getContext();

        Bundle bundle = getArguments();
        if (bundle != null) {
            str = bundle.getCharSequence(key_str).toString();
        }
        roboto_light = Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
        roboto_reguler = Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");


        edittext = (EditText) view.findViewById(R.id.search);

        list_info = (ListView) view.findViewById(R.id.list);


        Subject_Fee_Text = (TextView) view.findViewById(R.id.enter_subject_fee_text);
        fee_amount = (EditText) view.findViewById(R.id.enter_fee);
        spinner_branch = (Spinner) view.findViewById(R.id.spinner_branch);
        spinner_stand = (Spinner) view.findViewById(R.id.spinner_stand);
        spinner_batch = (Spinner) view.findViewById(R.id.spinner_batch);
        submit = (Button) view.findViewById(R.id.btn_submit);
        multiSelectionSpinner = (MultiSelectionSpinner) view.findViewById(R.id.spinner_subj);

        submit.setTypeface(roboto_reguler);

        initSpinnerData();
        initComponentListeners();
        bindComponentData();


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


    public void initSpinnerData() {


        spinner_batch.setEnabled(true);
        spinner_stand.setEnabled(true);
        spinner_branch.setEnabled(true);
        multiSelectionSpinner.setEnabled(true);


        //add spinner _branch details
        lhm_branch = MyApplication.dbo.getBranchWithActiveFlag(MyApplication.get_session("classid"));

        if (lhm_branch.size() == 0) {

            if (flagdialog)
                ErrorDialog("Please go to class profile tab  and add class details");

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


            if (flag.equals("edit")) {
                Log.d("tag", "edit branch");
                fee_amount.setText(edit_fee);
                spinner_branch.setEnabled(false);

                String branchname = MyApplication.dbo.getBranchName(edit_branch);
                int position = arrBranch.indexOf(branchname);

                spinner_branch.setSelection(position, true);
                selected_branch_value = lhm_branch.get(spinner_branch.getSelectedItem().toString());


            }


            int count1 = lhm_branch.size();
            if (count1 != 0) {
                selected_branch_value = lhm_branch.get(spinner_branch.getSelectedItem().toString());
                if (MyApplication.get_session(MyApplication.fee_session_branch).equals(""))
                    MyApplication.set_session(MyApplication.fee_session_branch, selected_branch_value);

            }
            if (flag_data) {

                String branchname = MyApplication.dbo.getBranchName(MyApplication.get_session(MyApplication.fee_session_branch));
                int position = arrBranch.indexOf(branchname);

                spinner_branch.setSelection(position, true);

                if (lhm_branch.size() != 0)
                    selected_branch_value = lhm_branch.get(spinner_branch.getSelectedItem().toString());
                MyApplication.set_session(MyApplication.fee_session_branch, selected_branch_value);

            }


            spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //select branch value
                    selected_branch_value = lhm_branch.get(spinner_branch.getSelectedItem().toString());
                    MyApplication.set_session(MyApplication.fee_session_branch, selected_branch_value);


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            //editable selected stand
            if (flag.equals("edit")) {
                Log.d("tag", "edit stand");
                spinner_stand.setEnabled(false);
                lhm_std = MyApplication.dbo.getStandard(MyApplication.get_session("classid"), selected_branch_value);
                log(lhm_std + "");

                for (Object o : lhm_std.keySet()) {

                    arrstand1.add(o.toString());
                    System.out.println("stand-key:" + o.toString() + "___" + "value:" + lhm_std.get(o).toString());
                }
                ArrayAdapter<String> adapter3 = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arrstand1);
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_stand.setAdapter(adapter3);

                String standname = MyApplication.dbo.getStandardName(edit_stand);
                int position = arrstand1.indexOf(standname);
//                Toast.makeText(context, "edit stand" + position, Toast.LENGTH_SHORT).show();
                spinner_stand.setSelection(position, true);


                selected_stand_value = lhm_std.get(spinner_stand.getSelectedItem().toString());


            } else {
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

                int count2 = lhm_std.size();
                if (count2 != 0) {
                    selected_stand_value = lhm_std.get(spinner_stand.getSelectedItem().toString());
                    if (MyApplication.get_session(MyApplication.fee_session_stand).equals(""))
                        MyApplication.set_session(MyApplication.fee_session_stand, selected_stand_value);//**
                }

                if (flag_data) {

                    String standname = MyApplication.dbo.getStandardName(MyApplication.get_session(MyApplication.fee_session_stand));
                    int position = arrstand1.indexOf(standname);

                    spinner_stand.setSelection(position, true);

                    int count3 = lhm_std.size();
                    if (count3 != 0)
                        selected_stand_value = lhm_std.get(spinner_stand.getSelectedItem().toString());
                    MyApplication.set_session(MyApplication.fee_session_stand, selected_stand_value);

                }
            }
            ;


            spinner_stand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    selected_stand_value = lhm_std.get(spinner_stand.getSelectedItem().toString());
                    MyApplication.set_session(MyApplication.fee_session_stand, selected_stand_value);//**

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });


            if (flag.equals("edit")) {
                Log.d("tag", "edit batch");
                spinner_batch.setEnabled(false);
                lhm_batch = MyApplication.dbo.getBatch(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value);
                log(lhm_batch + "");


                for (Object o : lhm_batch.keySet()) {

                    arrbatch.add(o.toString());
                    System.out.println("batch-key:" + o.toString() + "___" + "value:" + lhm_batch.get(o).toString());
                }
                ArrayAdapter<String> adapter4 = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arrbatch);
                adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_batch.setAdapter(adapter4);

                String batchname = MyApplication.dbo.getStandardName(edit_batch);
                int position3 = arrbatch.indexOf(batchname);
                spinner_batch.setSelection(position3, true);

                selected_batch_value = lhm_batch.get(spinner_batch.getSelectedItem().toString());


            } else {

                lhm_batch = MyApplication.dbo
                        .getBatch(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value);
                log(lhm_batch + "");

                arrbatch = new ArrayList<String>();
                for (Object o : lhm_batch.keySet()) {

                    arrbatch.add(o.toString());
                    System.out.println("batch-key:" + o.toString() + "___" + "value:" + lhm_batch.get(o).toString());
                }
                ArrayAdapter<String> adapter2 = new MyApplication.MySpinnerAdapter(context, R.layout.spinner_properties, arrbatch);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_batch.setAdapter(adapter2);

                int count3 = lhm_batch.size();
                if (count3 != 0) {
                    selected_batch_value = lhm_batch.get(spinner_batch.getSelectedItem().toString());
                    if (MyApplication.get_session(selected_batch_value).equals(""))
                        MyApplication.set_session(MyApplication.fee_session_batch, selected_batch_value);//**

                }

                if (flag_data) {

                    String batchname = MyApplication.dbo.getbatchhhName(MyApplication.get_session(MyApplication.fee_session_batch));
                    int position = arrbatch.indexOf(batchname);
                    spinner_batch.setSelection(position, true);

                    int count5 = lhm_batch.size();
                    if (count5 != 0) {
                        selected_batch_value = lhm_batch.get(spinner_batch.getSelectedItem().toString());
                        MyApplication.set_session(MyApplication.fee_session_batch, selected_batch_value);
                    }

                }
            }


            spinner_batch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selected_batch_value = lhm_batch.get(spinner_batch.getSelectedItem().toString());
                    MyApplication.set_session(MyApplication.fee_session_batch, selected_batch_value);


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });


            lhm_sub = MyApplication.dbo.getSubject(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value);
            Log.d("tag", "lhm_sub2" + lhm_sub);

            List<String> array1_subj = new ArrayList<>();


            for (Object o : lhm_sub.keySet()) {

                array1_subj.add(o.toString());

                System.out.println("subject-key:" + o.toString() + "___" + "value:" + lhm_sub.get(o).toString());
            }
            Log.d("tag", "array1" + array1_subj);

            int[] numbers = new int[1];
            if (flag.equals("edit")) {

                multiSelectionSpinner.setEnabled(false);


                Log.d("tag", "edit_subj" + edit_subj);
                String[] numberStrs = edit_subj.split(",");
                numbers = new int[numberStrs.length];
                for (int i = 0; i < numberStrs.length; i++) {
                    int position3 = array1_subj.indexOf(numberStrs[i]);
                    numbers[i] = position3;
                }
                Log.d("tag", "numbers" + numbers.length);

            } else {

                numbers[0] = 0;

            }


            array2 = array1_subj.toArray(new String[array1_subj.size()]);
            Log.d("tag", "$$$$" + Arrays.toString(array2) + "--" + array2.length);
            if (array2.length != 0)
                set(array2, numbers);


            if (lhm_sub.size() != 0) {


            }


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)

                {

                    checkedid = 0;
                    lhm_checkedSub.clear();
                    feeid = 0;
                    int count = SelectedSubj.size();
                    Log.d("tag", "subj_count" + count);
                    checkedid = 1;
                    for (int k = 0; k < count; k++) {
                        checkedsubj = lhm_sub.get(SelectedSubj.get(k));
                        Log.d("tag", "subj_In_LOPP" + checkedsubj);
                        String subjname = SelectedSubj.get(k);
                        lhm_checkedSub.put(subjname, String.valueOf(checkedid));

                        feeid += checkedid * (int) Math.pow(10, Integer.parseInt(checkedsubj));
                        Log.d("tag", "subj_In_LOPP" + feeid);


                    }

                    Log.d("tag", "lhm_checkedSublhm_checkedSub" + lhm_checkedSub);
                    if (fee_amount.getText().toString().equals("")) {
                        Toast.makeText(context, getResources().getString(R.string.valid_add_fee),
                                Toast.LENGTH_SHORT).show();
                    } else if (lhm_branch.size() == 0 || lhm_batch.size() == 0 || selected_stand_value.length() == 0) {


                        Toast.makeText(context, getResources().getString(R.string.valid_selected_subject),
                                Toast.LENGTH_SHORT).show();

                    } else {


                        showprocessDialog();


                        final Iterator<String> cursor1 = lhm_checkedSub.keySet()
                                .iterator();
                        while (cursor1.hasNext()) {
                            final String key = cursor1.next();
                        /* print the key */
                            Log.i("tag", "key" + key);
                            Log.i("tag", "lhm_checkedSub" + lhm_checkedSub);
                        }
                        Log.d("tag", "subj_lhm_checkedSub" + lhm_checkedSub);
                        selected_subjects_name = MyApplication.dbo.getSubjectId1(lhm_checkedSub);

                        Log.d("tag", "subj_lhm_subj_name" + selected_subjects_name);
                        Boolean checkdata = MyApplication.dbo.checkActiveBatch(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value, selected_subjects_name);
//                        Toast.makeText(context, "checkdata" + checkdata, Toast.LENGTH_SHORT).show();

                        if (checkdata) {
                            String FeeId = String.valueOf(feeid);
                            if (flag.equals("edit")) {
                                String rowid = MyApplication.dbo.updateFee(autoid, MyApplication.get_session("classid"), FeeId, fee_amount.getText().toString(), selected_subjects_name, selected_branch_value, selected_stand_value, selected_batch_value);
                                Toast.makeText(context, getResources().getString(R.string.success_update_fee) + rowid, Toast.LENGTH_SHORT).show();
                                selected_subjects_name = "";
                            } else {
                                rowid = MyApplication.dbo.insertFee(MyApplication.get_session("classid"), FeeId, fee_amount.getText().toString(), selected_subjects_name, selected_branch_value, selected_stand_value, selected_batch_value);
                                Toast.makeText(context, getResources().getString(R.string.success_add_fee), Toast.LENGTH_SHORT).show();
                                selected_subjects_name = "";
                            }


                            dialog1.dismiss();
                            flag = "True";

                            refreshData();
                        } else {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                            alertDialog.setTitle("Confirm");
                            alertDialog.setMessage(getResources().getString(R.string.dialog_active));
                            final String finalSelected_subjects_name = selected_subjects_name;
                            alertDialog.setPositiveButton("Active", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    MyApplication.dbo.updateFlagActiveBatch(MyApplication.get_session("classid"), selected_branch_value, selected_batch_value, selected_stand_value, selected_subjects_name);


                                    String FeeId = String.valueOf(feeid);
                                    if (flag.equals("Cancel")) {
                                        String rowid = MyApplication.dbo.updateFee(autoid, MyApplication.get_session("classid"), FeeId, fee_amount.getText().toString(), selected_subjects_name, selected_branch_value, selected_stand_value, selected_batch_value);
                                        Toast.makeText(context, getResources().getString(R.string.success_update_fee) + rowid, Toast.LENGTH_SHORT).show();
                                        selected_subjects_name = "";
                                    } else {
                                        rowid = MyApplication.dbo.insertFee(MyApplication.get_session("classid"), FeeId, fee_amount.getText().toString(), selected_subjects_name, selected_branch_value, selected_stand_value, selected_batch_value);
                                        Toast.makeText(context, getResources().getString(R.string.success_add_fee), Toast.LENGTH_SHORT).show();
                                        selected_subjects_name = "";
                                    }


                                    dialog1.dismiss();
                                    flag = "True";

                                    refreshData();
                                }
                            });


                            // Setting Negative "NO" Button
                            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog1.dismiss();

                                    lhm_checkedSub.clear();
                                    refreshData();

                                }
                            });
                            alertDialog.show();
                        }


                    }


                }


            });
        }

    }


    public void initComponentListeners() {


    }

    public void bindComponentData() {

        menu_list = null;
        menu_list = new ArrayList<HashMap<String, String>>();

        menu_list = MyApplication.dbo.GetFeeData(MyApplication.get_session("classid"));

        rowItems1 = new ArrayList<ListModel>();
        Log.i("menu_list", "menu_list_fee" + menu_list);

        int records = menu_list.size();

        for (int i = 0; i < records; i++) {


            ListModel item = new ListModel(menu_list.get(i).get("id"), menu_list.get(i).get("subjects"), menu_list.get(i).get("fee_amount"), menu_list.get(i).get("classid"), menu_list.get(i).get("feeid"), menu_list.get(i).get("branch"), menu_list.get(i).get("standard"), menu_list.get(i).get("batch"), menu_list.get(i).get("subids"));

            rowItems1.add(item);

            Log.i("rowItems1:id", "" + rowItems1.get(i).getId());
            Log.i("rowItems1:name", "" + rowItems1.get(i).getSubjects());
            Log.i("rowItems1:name", "" + rowItems1.get(i).getFee());

        }


        customAdapter = new CustomAdapter(context, rowItems1);
        list_info.setAdapter(customAdapter);

        list_info.setSelectionFromTop(index, top);

    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {

        SelectedSubj = strings;
        setSubjectName();
//

    }


    public class CustomAdapter extends BaseAdapter implements View.OnClickListener {

        Context context;
        List<ListModel> rowItems;
        int selectcount = 0;
        String strappid = "";
        int temp = 0;
        private LayoutInflater inflater = null;

        public CustomAdapter(Context context, List<ListModel> rowItems) {
            this.context = context;
            this.rowItems = rowItems;
            inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public int getCount() {
            return rowItems.size();
        }

        public Object getItem(int position) {
            return rowItems.get(position);
        }

        public long getItemId(int position) {
            return rowItems.indexOf(getItem(position));
        }

        /**
         * ****** Create a holder Class to contain inflated xml file elements ********
         */
        public class ViewHolder {

            public TextView subjects, total_fee, branch, stand, batch, text_total_fees;
            ImageView img_delete, img_fee_edit;


            //		WebView reason;
            //	ImageView sync;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi = convertView;
            final ViewHolder holder;


            convertView = null;
            if (convertView == null) {
                /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
                vi = inflater.inflate(R.layout.feelistitem, null);
                /****** View Holder Object to contain tabitem.xml file elements ******/

                holder = new ViewHolder();
                holder.branch = (TextView) vi.findViewById(R.id.branch);
                holder.stand = (TextView) vi.findViewById(R.id.stand);
                holder.text_total_fees = (TextView) vi.findViewById(R.id.text_total_fees);
                holder.batch = (TextView) vi.findViewById(R.id.batch);
                holder.subjects = (TextView) vi.findViewById(R.id.subjects);
                holder.total_fee = (TextView) vi.findViewById(R.id.total_fee);
                holder.img_delete = (ImageView) vi.findViewById(R.id.img_delete);
                holder.img_fee_edit = (ImageView) vi.findViewById(R.id.img_fee_edit);

                holder.subjects.setTypeface(roboto_reguler);
                holder.total_fee.setTypeface(roboto_reguler);
                holder.text_total_fees.setTypeface(roboto_reguler);
                holder.branch.setTypeface(roboto_reguler);
                holder.stand.setTypeface(roboto_reguler);
                holder.batch.setTypeface(roboto_reguler);


                /************ Set holder with LayoutInflater ************/
                vi.setTag(holder);
            } else
                holder = (ViewHolder) vi.getTag();

            final ListModel row_pos = rowItems.get(position);

            String sub = row_pos.getSubjects().toString();

            Log.i("tag", "sub" + sub);
            //     sub = sub.substring(0,sub.length()-1);
            Log.i("tag", "substring" + sub);

            String stdname = MyApplication.dbo.getStandardName(row_pos.getStd().toString());
            String brnchname = MyApplication.dbo.getBranchName(row_pos.getBranch().toString());
            String batch = MyApplication.dbo.getbatchhhName(row_pos.getBatch().toString());
            holder.subjects.setText(sub);
            holder.branch.setText(brnchname);
            holder.stand.setText(stdname);
            holder.batch.setText(batch);

            holder.total_fee.setText(row_pos.getFee());


            holder.img_delete.setTag(position);

            holder.img_delete.setContentDescription(row_pos.getId());
            holder.img_fee_edit.setContentDescription(row_pos.getId());

            final int tempPosition = position;
            holder.img_delete.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ImageView cb = (ImageView) v;
                            int pos = (int) ((ImageView) v).getTag();
                            final ListModel row_pos = rowItems.get(pos);

                            branchid = row_pos.getBranch();
                            batchid = row_pos.getBatch();
                            stdid = row_pos.getStd();
                            Log.d("tag", "branchid--" + branchid + "batchid--" + batchid + "stdid--" + stdid);

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                            alertDialog.setTitle("Delete Confirm");
                            alertDialog.setMessage("Do you want to delete records of this batch? Please note that, all student records, fee records, attendance records will be deleted for " + MyApplication.dbo.getBranchName(branchid) + " branch, " + MyApplication.dbo.getbatchhhName(batchid) + " batch and " + MyApplication.dbo.getStandardName(stdid) + " standard");
                            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                        /* size = menu_list.size();
                            if (size > 0) {
                                size = size - 1;

                                menu_list.remove(size);*/


                                    index = list_info.getFirstVisiblePosition();
                                    View v1 = list_info.getChildAt(0);
                                    top = (v1 == null) ? 0 : (v1.getTop() - list_info.getPaddingTop());

                                    Log.i("", "index" + index);
                                    Log.i("", "top" + top);


                                    strappid = (String) holder.img_delete.getContentDescription();
                                    Log.i("tag", "strppid" + strappid);

                                    MyApplication.dbo.deleteFeeData(strappid);
                                    bindComponentData();

                                    String ids = MyApplication.dbo.deleteFromStudentDataTable(branchid, batchid, stdid);
                                    MyApplication.dbo.deleteFromStudentSubjectTable(ids);
                                    MyApplication.dbo.deleteFromInsertFeeTable(branchid, batchid, stdid);
                                    MyApplication.dbo.deleteFromStudentFeeTable(branchid, batchid, stdid);
                                    MyApplication.dbo.deleteFromAbsentStudentTable(branchid, batchid, stdid);

                                    //  list_info.setSelection(size - 1);
                                    Toast.makeText(context, "DeleteSize:" + size, Toast.LENGTH_SHORT).show();
                            /*} else {
                                Toast.makeText(getApplicationContext(), "No elements to delete", Toast.LENGTH_SHORT).show();
                            }*/

                                }
                            });


                            // Setting Negative "NO" Button
                            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alertDialog.show();


                        }
                    }
            );


            holder.img_fee_edit.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            autoid = holder.img_fee_edit.getContentDescription().toString();
                            edit_fee = holder.total_fee.getText().toString();
                            edit_subj = holder.subjects.getText().toString();
                            edit_branch = holder.branch.getText().toString();
                            edit_batch = holder.batch.getText().toString();
                            edit_stand = holder.stand.getText().toString();


                            flag = "edit";
                            initSpinnerData();


                        }
                    }
            );


            //     final int tempPosition = position;
            vi.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub


                }
            });


            return vi;

        }

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub

        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public void refreshData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            str = bundle.getCharSequence(key_str).toString();
        }
        flag = "True";
        flagdialog = true;
        initSpinnerData();
        bindComponentData();
        fee_amount.setText("");
//        Log.d("tag2", "refreshData called->" + str);
//        Toast.makeText(context,"RefreshData->"+str,Toast.LENGTH_SHORT).show();

    }

    public void set(String[] array2, int[] pos) {
        multiSelectionSpinner.setItems(array2);
        multiSelectionSpinner.setSelection(pos);
        multiSelectionSpinner.setListener(this);
        multiSelectionSpinner.set_selection();


    }

    public void showprocessDialog() {


        dialog1 = ProgressDialog.show(context, "Submitting  Form....",
                "Please Wait", true, false);
        dialog1.show();

    }


    public void setSubjectName() {

        String subjname2 = "";

        for (int k = 0; k < SelectedSubj.size(); k++) {

            if (subjname2.equals(""))
                subjname2 = SelectedSubj.get(k);
            else
                subjname2 = subjname2 + "," + SelectedSubj.get(k);
        }
        Subject_Fee_Text.setText("Define fees for " + subjname2 + " subjects.");
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

    public void ErrorDialog(String text) {
        flagdialog = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setMessage(text)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(context, Activity_profileNew.class);
                        i.putExtra("dialog_tab","1");
                        startActivity(i);
                        getActivity().finish();
                        ;

                    }
                })

                .show();
    }


}
