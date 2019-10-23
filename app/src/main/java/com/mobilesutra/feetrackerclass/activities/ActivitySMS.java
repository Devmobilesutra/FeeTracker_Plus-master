package com.mobilesutra.feetrackerclass.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.bumptech.glide.Glide;
import com.mobilesutra.feetrackerclass.ListModel;
import com.mobilesutra.feetrackerclass.MyApplication;
import com.mobilesutra.feetrackerclass.R;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ActivitySMS extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener, ActivityCompat.OnRequestPermissionsResultCallback,
        BillingProcessor.IBillingHandler {

    Spinner spnr_branch = null, spnr_stand = null, spnr_batch = null;
    TextView txt_class_name, txt_activity_header, charcount;
    Context context = this;
    Typeface roboto_reguler, roboto_light;
    LinkedHashMap<String, String> lhm_branch = new LinkedHashMap<String, String>();// Branch
    LinkedHashMap<String, String> lhm_std = new LinkedHashMap<String, String>();// Std
    LinkedHashMap<String, String> lhm_batch = new LinkedHashMap<String, String>();// Batch
    LinkedHashMap<String, String> lhm_sub = new LinkedHashMap<String, String>();// Subjects
    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    final ArrayList<String> ids = new ArrayList<String>();
    final ArrayList<String> parent_ids = new ArrayList<String>();
    ArrayList<ListModel> rowItems1 = new ArrayList<ListModel>();
    Boolean flag = false, flag_data = true;
    ArrayList<String> arrstand1;
    ArrayList<String> arrbatch;
    ArrayList<String> arraysubj;
    MultiSelectionSpinner mspnr_subject = null;
    String[] array2;
    String selected_subj_value = "", prv_stand_value = "", prv_batch_value = "", selected_branch_value = "", selected_stand_value = "", selected_batch_value = "", id = "", rowid = "", edit_batch, edit_stand;
    List<String> SelectedSubj = new ArrayList<>();
    int PERMISSION_REQUEST_SMS = 1;
    RecyclerView recycler_view = null;
    RecyclerAdapter recyclerAdapter = null;
    EditText notice = null;
    int textlength, msgcount = 1;
    private Button btnBuy;
    private Button btnFreeSend;

    BillingProcessor bp;
    private String APP_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtVlqUkDuCS2e3ADCQU4+BkaVfkE4EujmiZYo3HYFhrvoFaOk9fMCx7CvCGDhddF/3QFNUD6kdVq2aX2dvShN4gfnOuqj3GCAWewURaRQk5H9aKFW/K8/m2xls0Fl5guMbUjLlvGhBQKhj4WFHyds0ERWEskJFoXuI8leMLl+z1fZXfZAho0BSVi4t/Om8D4jpreypphvQ5NFjW5G3sbtUbbxGtQo/UrtxqPjgz7ogfmc1I0TxXIZFzLI9OtCbJlt45DPKvJpjBeGCjawOWvbiG7kadV3bhb1o0/1HcXV8b+yD26FAOYaDpN5tT6PEd1lBxz2x0nP8dXfWTXngj2lywIDAQAB";
    private String USER_KEY = MyApplication.get_session(MyApplication.SESSION_USERTEXT);
    private String PRODUCT_ID = "sms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        context = this;
        initComponents();


        initSpinnerData();
        initComponentListeners();
        bindComponentData();

    }

    private void initComponents() {
        // bp = new BillingProcessor(context, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtVlqUkDuCS2e3ADCQU4+BkaVfkE4EujmiZYo3HYFhrvoFaOk9fMCx7CvCGDhddF/3QFNUD6kdVq2aX2dvShN4gfnOuqj3GCAWewURaRQk5H9aKFW/K8/m2xls0Fl5guMbUjLlvGhBQKhj4WFHyds0ERWEskJFoXuI8leMLl+z1fZXfZAho0BSVi4t/Om8D4jpreypphvQ5NFjW5G3sbtUbbxGtQo/UrtxqPjgz7ogfmc1I0TxXIZFzLI9OtCbJlt45DPKvJpjBeGCjawOWvbiG7kadV3bhb1o0/1HcXV8b+yD26FAOYaDpN5tT6PEd1lBxz2x0nP8dXfWTXngj2lywIDAQAB", this);


        bp = BillingProcessor.newBillingProcessor(this, APP_KEY, USER_KEY, this); // doesn't bind
        bp.initialize();
        Log.d("onBillingError", "> initialize");

        roboto_light = Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
        roboto_reguler = Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");
        txt_class_name = (TextView) findViewById(R.id.txt_class_name);
        txt_activity_header = (TextView) findViewById(R.id.txt_activity_header);
        txt_activity_header.setTypeface(roboto_reguler);
        txt_activity_header.setText("SEND SMS TO PARENTS OR STUDENTS");
        txt_class_name.setText(MyApplication.get_session("classname"));
        charcount = (TextView) findViewById(R.id.noticecharcount);
        notice = (EditText) findViewById(R.id.notice);
        btnBuy = (Button) findViewById(R.id.btnBuy);

        btnBuy.setTypeface(roboto_reguler);
        btnBuy.setVisibility(View.VISIBLE);
        btnFreeSend = (Button) findViewById(R.id.btnFreeSend);
      //  btnFreeSend.setVisibility(View.VISIBLE);
        btnFreeSend.setTypeface(roboto_reguler);
        notice.setTypeface(roboto_reguler);
        charcount.setTypeface(roboto_reguler);
        spnr_branch = (Spinner) findViewById(R.id.spnr_branch);
        spnr_stand = (Spinner) findViewById(R.id.spnr_stand);
        spnr_batch = (Spinner) findViewById(R.id.spnr_batch);
        mspnr_subject = (MultiSelectionSpinner) findViewById(R.id.mspnr_subject);

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view_student);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivitySMS.this);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setHasFixedSize(true);




        for(String sku : bp.listOwnedSubscriptions())
        {
            Log.d("onBillingError", "Owned Subscription: " + sku);
            Log.d("onBillingError", "> Success - getSubscriptionTransactionDetails");
            if (bp.isSubscribed(PRODUCT_ID)) {
                btnBuy.setVisibility(View.GONE);
                btnFreeSend.setVisibility(View.VISIBLE);
                Log.d("onBillingError", "> Success - True");
            } else {
                btnBuy.setVisibility(View.VISIBLE);
                btnFreeSend.setVisibility(View.GONE);
                Log.d("onBillingError", "> Success - else");
            }
        }

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
                    new HTTPDATA().execute();
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
                    new HTTPDATA().execute();


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
                    new HTTPDATA().execute();

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
            // lhm_sub = MyApplication.dbo.getSubject(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value);
            Log.d("tag", "lhm_sub2" + lhm_sub);
            List<String> array1_subj = new ArrayList<>();
            for (Object o : lhm_sub.keySet()) {
                array1_subj.add(o.toString());
                System.out.println("subject-key:" + o.toString() + "___" + "value:" + lhm_sub.get(o).toString());
            }
            Log.d("tag", "array1" + array1_subj);
            int[] numbers = {0};
            array2 = array1_subj.toArray(new String[array1_subj.size()]);

            if (array2.length != 0)
                set(array2, numbers);

            if (lhm_sub.size() != 0) {


            }

        }


    }

    public void set(String[] array2, int[] pos) {
        mspnr_subject.setItems(array2);
        mspnr_subject.setSelection(pos);
        mspnr_subject.setListener(this);
        mspnr_subject.set_selection();

    }


    private void initComponentListeners() {
        MyApplication.selecteddate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        notice.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                notice.addTextChangedListener(new TextWatcher() {

                    public void afterTextChanged(Editable s) {

                    }

                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {

                    }

                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        textlength = notice.getText().length();
                        float len = textlength;
                        float ms = 0;
                        if (textlength > 160) {
                            ms = len / 160;

                            msgcount = (int) Math.ceil(ms);

                            // msgcount=(textlength/50);

                        } else {
                            msgcount = 1;
                        }
                        charcount.setText(getResources().getString(R.string.char_countt) + textlength + " "
                                + getResources().getString(R.string.message_count) + msgcount);
                        msgcount = msgcount;

                    }
                });
            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    bp.subscribe(ActivitySMS.this, PRODUCT_ID);
            }
        });


        btnFreeSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // by Bhavesh
                int len = ids.size();
                Log.d("TAG", "checked student count is: " + len);

                if (notice.getText().toString().equalsIgnoreCase("")) {

                    Toast.makeText(context, getResources().getString(R.string.valid_notice), Toast.LENGTH_LONG).show();

                } else if (len == 0) {

                    Toast.makeText(context, getResources().getString(R.string.valid_select_stud), Toast.LENGTH_LONG).show();

                } else {

                    showdialog_SMS(len);
                }
            }
        });
    }


    private void bindComponentData() {

    }

    @Override
        public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        btnBuy.setVisibility(View.GONE);
        btnFreeSend.setVisibility(View.VISIBLE);

        Log.d("onBillingError", "> Success - onProductPurchased");
    }

    @Override
    public void onPurchaseHistoryRestored() {

        Log.d("onBillingError", "onPurchaseHistoryRestored ");

        /*for(String sku : bp.listOwnedProducts())
        {
            Log.d("onBillingError", "Owned Managed Product: " + sku);
        }*/

        for(String sku : bp.listOwnedSubscriptions())
        {
            Log.d("onBillingError", "Owned Subscription: " + sku);
            Log.d("onBillingError", "> Success - getSubscriptionTransactionDetails");
            if (bp.isSubscribed(PRODUCT_ID)) {
                btnBuy.setVisibility(View.GONE);
                btnFreeSend.setVisibility(View.VISIBLE);
                Log.d("onBillingError", "> Success - True");
            } else {
                btnBuy.setVisibility(View.VISIBLE);
                btnFreeSend.setVisibility(View.GONE);
                Log.d("onBillingError", "> Success - else");
            }
        }


      /*  Log.d("onBillingError", "> Success - onPurchaseHistoryRestored");
        if (bp.getSubscriptionTransactionDetails(PRODUCT_ID).purchaseInfo.purchaseData.autoRenewing == true)
        {*/
           /* Log.d("onBillingError", "> Success - getSubscriptionTransactionDetails");
                if (bp.isSubscribed("sms")) {
                    btnBuy.setVisibility(View.GONE);
                    btnFreeSend.setVisibility(View.VISIBLE);
                    Log.d("onBillingError", "> Success - True");
                } else {
                    btnBuy.setVisibility(View.VISIBLE);
                    btnFreeSend.setVisibility(View.GONE);
                    Log.d("onBillingError", "> Success - else");
                }*/

       /* } else {
            btnBuy.setVisibility(View.VISIBLE);
            btnFreeSend.setVisibility(View.GONE);
            Log.d("onBillingError", "> Success - getSubscriptionTransactionDetails else");
        }*/



        /*Log.d("onBillingError", "> Success - onPurchaseHistoryRestored");
        if (bp.isSubscribed("sms")) {
            btnBuy.setVisibility(View.GONE);
            btnFreeSend.setVisibility(View.VISIBLE);
            Log.d("onBillingError", "> Success - True");
        } else {
            btnBuy.setVisibility(View.VISIBLE);
            btnFreeSend.setVisibility(View.GONE);
            Log.d("onBillingError", "> Success - else");
        }*/
    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

        /*
    * Called when some error occurred. See Constants class for more details
    *
    * Note - this includes handling the case where the user canceled the buy dialog:
    * errorCode = Constants.BILLING_RESPONSE_RESULT_USER_CANCELED
    */

        // Toast.makeText(context, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
        switch (errorCode) {
            case 0:
                Log.d("onBillingError", "> Success - BILLING_RESPONSE_RESULT_OK");
                break;
            case 1:
                Log.d("onBillingError", "> User pressed back or canceled a dialog - BILLING_RESPONSE_RESULT_USER_CANCELED");
                break;
            case 3:
                Log.d("onBillingError", "> Billing API version is not supported for the type requested - BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE");
                break;
            case 4:
                Log.d("onBillingError", "> Requested product is not available for purchase - BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE");
                break;
            case 5:
                Log.d("onBillingError", "> Invalid arguments provided to the API. This error can also indicate that the application was not correctly signed or properly set up for In-app Billing in Google Play, or does not have the necessary permissions in its manifest - BILLING_RESPONSE_RESULT_DEVELOPER_ERROR");
                break;
            case 6:
                Log.d("onBillingError", "> Fatal error during the API action - BILLING_RESPONSE_RESULT_ERROR");
                break;
            case 7:
                Log.d("onBillingError", "> Failure to purchase since item is already owned - BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED");
                break;
            case 8:
                Log.d("onBillingError", "> Failure to consume since item is not owned - BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED");
                break;


        }

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

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<ListModel> rowItems;
        public RecyclerAdapter(List<ListModel> rowItems) {
            this.rowItems = rowItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            RecyclerView.ViewHolder vh;
            final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_list_row, viewGroup, false);
            vh = new MyViewHolder(view);

            return vh;
        }


        public void Remove(int pos) {
            rowItems.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(0, rowItems.size());
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final ListModel row_pos = rowItems.get(position);
            ((MyViewHolder) holder).studid.setText(row_pos.getAuto_id());
            ((MyViewHolder) holder).name.setText(row_pos.getName());
            ((MyViewHolder) holder).check.setTag(position);
            log("row_pos.getId() Student: ", row_pos.getStudphone());
            log("row_pos.getId() Parent: ", row_pos.getPhone1());

            if (row_pos.isSmsFlag()) {
                ((MyViewHolder) holder).check.setChecked(true);
            } else {
                ((MyViewHolder) holder).check.setChecked(false);
            }

            if (row_pos.getProfile() != null)
            {
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
                    Log.i("tag", "getProfile is " + row_pos.getName() +" "+row_pos.getProfile());
                    Bitmap bitmap = MyApplication.decodeBase64Profile(row_pos.getProfile());
                    ((MyViewHolder) holder).ivProfile.setImageBitmap(bitmap);
                }
            }



            ((MyViewHolder) holder).check.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    int pos = (int) ((CheckBox) v).getTag();
                    ListModel row_pos = rowItems.get(pos);

                    if (cb.isChecked()) {


                        row_pos.setSmsFlag(true);
                        notifyItemChanged(pos);
                        String id = row_pos.getStudphone().toString();
                        String id1 = row_pos.getPhone1().toString();

                        log("check_id_if", id);

                        MyApplication.log("SendSMS", "stud phone : " + id);
                        MyApplication.log("SendSMS", "parent phone : " + id1);


                        ids.add(id);
                        parent_ids.add(id1);

                    } else {

                        row_pos.setSmsFlag(false);
                        notifyItemChanged(pos);
                        String id = row_pos.getStudphone().toString();
                        String id1 = row_pos.getPhone1().toString();
                        log("uncheck_id_if", id);

                        MyApplication.log("SendSMS", "stud phone : " + id);
                        MyApplication.log("SendSMS", "parent phone : " + id1);
                        if (ids.contains(id)) {
                            ids.remove(ids.indexOf(id));
                        }

                        if (parent_ids.contains(id1)) {

                            parent_ids.remove(parent_ids.indexOf(id1));
                        }
                    }
                    log("check_id_if" + ids);

                }
            });

        }

        @Override
        public int getItemCount() {
            return rowItems == null ? 0 : rowItems.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView studid;
            TextView name;
            TextView ispresent;
            CheckBox check;
            ImageView ivProfile;


            public MyViewHolder(View v) {
                super(v);

                studid = (TextView) v.findViewById(R.id.StudID1);
                name = (TextView) v.findViewById(R.id.name1);

                ispresent = (TextView) v.findViewById(R.id.ispresent);
                check = (CheckBox) v.findViewById(R.id.checkBox1);
                ivProfile = (ImageView)v.findViewById(R.id.ivProfile);
                studid.setTypeface(roboto_reguler);
                name.setTypeface(roboto_reguler);

            }


        }


    }

    @Override
    public void onBackPressed() {
        //Intent i = new Intent(Send_sms.this, ActivityDashboard.class);
        Intent i = new Intent(ActivitySMS.this, ActivityDashboard.class);
        startActivity(i);
        finish();
    }

    public void ErrorDialog(String text) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage(text)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(context, Activity_profileNew.class);
                        i.putExtra("dialog_tab", "1");
                        startActivity(i);
                        finish();
                        ;

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

    public static void log(String LOG_TAG, String str) {
        if (str.length() > 4000) {
            Log.i(LOG_TAG, str.substring(0, 4000));
            log(str.substring(4000));
        } else
            Log.i(LOG_TAG, str);
    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        id = "";
        SelectedSubj = strings;
        int count = SelectedSubj.size();

        for (int j = 0; j < count; j++) {

            String checkedsubj = lhm_sub.get(SelectedSubj.get(j));

            if (id.equals("")) {
                id = checkedsubj;

            } else {
                id = id + "," + checkedsubj;

            }


        }

        log("chek_subjectid", id);
//        bindComponentData();

        new HTTPDATA().execute();
//        Toast.makeText(context, strings.toString(), Toast.LENGTH_LONG).show();
    }


    private void send_sms(ArrayList<String> ids, ArrayList<String> parent_ids, String smsFlag) {
        int len3 = ids.size();

        if (smsFlag.equalsIgnoreCase("p")) {
            for (int k = 0; k < len3; k++) {
                try {

                /*if (msgcount == 1) {

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+91" + ids.get(k), null, notice.getText().toString(), null, null);
                } else {*/
                    if (((MyApplication) getApplication()).isNetworkAvailable()) {

                        AsyncSendSMS asyncSendSMS = new AsyncSendSMS(parent_ids.get(k), notice.getText().toString());
                        asyncSendSMS.execute();
                    }

                    /*SmsManager smsManager = SmsManager.getDefault();
                    ArrayList<String> parts = smsManager.divideMessage(notice.getText().toString());
                    smsManager.sendMultipartTextMessage("+91" + parent_ids.get(k), null, parts, null, null);*/


                    //  }
                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.fail_send_sms), Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
        } else if (smsFlag.equalsIgnoreCase("s")) {


            for (int k = 0; k < len3; k++) {
                try {

                    if (((MyApplication) getApplication()).isNetworkAvailable()) {

                        AsyncSendSMS asyncSendSMS = new AsyncSendSMS(ids.get(k), notice.getText().toString());
                        asyncSendSMS.execute();
                    }

                   /* SmsManager smsManager = SmsManager.getDefault();
                    ArrayList<String> parts = smsManager.divideMessage(notice.getText().toString());
                    smsManager.sendMultipartTextMessage("+91" + ids.get(k), null, parts, null, null);*/

                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.fail_send_sms), Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }


        } else if (smsFlag.equalsIgnoreCase("ps")) {

            for (int k = 0; k < len3; k++) {
                try {


                    if (((MyApplication) getApplication()).isNetworkAvailable()) {

                        AsyncSendSMS asyncSendSMS = new AsyncSendSMS(parent_ids.get(k), notice.getText().toString());
                        asyncSendSMS.execute();

                        AsyncSendSMS asyncSendSMS1 = new AsyncSendSMS(ids.get(k), notice.getText().toString());
                        asyncSendSMS1.execute();
                    }

                    /*SmsManager smsManager = SmsManager.getDefault();
                    ArrayList<String> parts = smsManager.divideMessage(notice.getText().toString());
                    smsManager.sendMultipartTextMessage("+91" + ids.get(k), null, parts, null, null);

                    SmsManager smsManager1 = SmsManager.getDefault();
                    ArrayList<String> parts1 = smsManager1.divideMessage(notice.getText().toString());
                    smsManager1.sendMultipartTextMessage("+91" + parent_ids.get(k), null, parts1, null, null);*/

                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.fail_send_sms), Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == PERMISSION_REQUEST_SMS) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                Snackbar.make(txt_class_name, "SMS permission was granted.",
                        Snackbar.LENGTH_SHORT)
                        .show();
//                if(ids.size()!=0)
//                    send_sms(ids);
//                else
//                    Toast.makeText(context, "Please select student", Toast.LENGTH_LONG).show();
            } else {
                // Permission request was denied.
                Snackbar.make(txt_class_name, "SMS permission request was denied.",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
        // END_INCLUDE(onRequestPermissionsResult)
    }

    private void requestSMSPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            Snackbar.make(txt_class_name, "FeeTracker app need permission to send SMS.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(ActivitySMS.this,
                            new String[]{Manifest.permission.SEND_SMS},
                            PERMISSION_REQUEST_SMS);
                }
            }).show();

        } else {
            Snackbar.make(txt_class_name, "Permission is not available. Requesting sms permission.", Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_REQUEST_SMS);
        }
    }


    public void SendTextMessageDialog(String text) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("")
                .setMessage(text)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(ActivitySMS.this, ActivityDashboard.class);
                        startActivity(i);
                        finish();
                        ;

                    }
                })

                .show();
    }


    class HTTPDATA extends AsyncTask<Void, String, String> {


        protected void onPreExecute() {

//            dialog1 = ProgressDialog.show(Send_sms.this, "Submitting  Form....",
//                    "Please Wait", true, false);
        }

        protected String doInBackground(Void... params) {


            data = MyApplication.dbo.getstudentlist2(MyApplication.get_session("classid"), selected_branch_value, selected_stand_value, selected_batch_value, id);
            rowItems1 = new ArrayList<ListModel>();

            int records = data.size();
            for (int i = 0; i < records; i++) {

                ListModel item = new ListModel(data.get(i).get("rollno"),
                        data.get(i).get("auto_id"),
                        data.get(i).get("sname"),
                        data.get(i).get("StudentProfile"),
                        data.get(i).get("parent_phno"),
                        data.get(i).get("stud_phno"),
                        "0", "",
                        "",
                        "",
                        "");//f.toString()

                rowItems1.add(item);

            }

            publishProgress("progress");
            return "";
        }

        protected void onProgressUpdate(String... progress) {

            recyclerAdapter = new RecyclerAdapter(rowItems1);
            recycler_view.setAdapter(recyclerAdapter);

        }

        protected void onPostExecute(Long result) {

        }
    }// HTTPRequest


    public void showdialog_SMS(int len) {

        final Dialog dialog1 = new Dialog(context);

        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_send_sms);
        final TextView msg = (TextView) dialog1.findViewById(R.id.msg);
        msg.setText("Do you want to send the text message to the " + len + " students of " + MyApplication.dbo.getBranchName(selected_branch_value) + " branch, " + MyApplication.dbo.getStandardName(selected_stand_value) + " standard, " + MyApplication.dbo.getbatchhhName(selected_batch_value) + " batch and  " + MyApplication.dbo.getSubjjjnameWithMultilpe(id) + " subjects? ");

        final Button btn_ok = (Button) dialog1.findViewById(R.id.btnOk);
        final Button btn_Cancel = (Button) dialog1.findViewById(R.id.btnCancel);
        final CheckBox ch_parent = (CheckBox) dialog1.findViewById(R.id.ch_parent);
        final CheckBox ch_student = (CheckBox) dialog1.findViewById(R.id.ch_student);

        ch_parent.setChecked(true);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sms_flag = "";
                if (ch_parent.isChecked() && ch_student.isChecked()) {

                    sms_flag = "ps";
                } else if (ch_parent.isChecked()) {

                    sms_flag = "p";

                } else if (ch_student.isChecked()) {

                    sms_flag = "s";

                }

                if (sms_flag.isEmpty())
                {
                    Toast.makeText(context, "Please select parent or student", Toast.LENGTH_SHORT).show();
                }
                else
                {




                   // if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //This is marshmellow and above version code
                      //  if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                            //application has permission
                            if (notice.getText().toString().equals("")) {
                                Toast.makeText(context, getResources().getString(R.string.valid_notice), Toast.LENGTH_LONG).show();
                            } else {
                                if (ids.size() != 0) {

                                    Log.d("TAG", "sms_flag is: " + sms_flag);

                                    send_sms(ids, parent_ids, sms_flag);

                                    int len = ids.size();
                                    Log.d("TAG", "checked student count is: " + len);
                                    SendTextMessageDialog(getResources().getString(R.string.success_send_sms_to) + " " + len + " " + getResources().getString(R.string.students));
                                    //Toast.makeText(context, getResources().getString(R.string.success_send_sms_to) + " " + len + " " + getResources().getString(R.string.students), Toast.LENGTH_LONG).show();


                                } else
                                    Toast.makeText(context, getResources().getString(R.string.valid_select_stud), Toast.LENGTH_LONG).show();

                            }
                      //  }

                        /*else {
                            //application has not permission.Request for permission
                            requestSMSPermission();
                        }*/
                    /*} else {
                        //This is below marshmellow version code
                        if (notice.getText().toString().equals("")) {
                            Toast.makeText(context, getResources().getString(R.string.valid_notice), Toast.LENGTH_LONG).show();
                        } else {
                            if (ids.size() != 0) {

                                send_sms(ids, parent_ids, sms_flag);

                                int len = ids.size();
                                Log.d("TAG", "checked student count is: " + len);
                                //Toast.makeText(context, getResources().getString(R.string.success_send_sms_to) + " " + len + " " + getResources().getString(R.string.students), Toast.LENGTH_LONG).show();
                                SendTextMessageDialog(getResources().getString(R.string.success_send_sms_to) + " " + len + " " + getResources().getString(R.string.students));

                            } else

                                Toast.makeText(context, getResources().getString(R.string.valid_select_stud), Toast.LENGTH_LONG).show();

                        }*/
                    }
                    dialog1.dismiss();
                }




           // }
        });
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        dialog1.show();
        dialog1.setCancelable(true);
        dialog1.setCanceledOnTouchOutside(false);

    }


    class AsyncSendSMS extends AsyncTask<String, String, String> {

        String Response = "";
        Dialog dialog1 = null;
        String mobileno = "", sms_text = "";

        int getstate = 0;

        public AsyncSendSMS(String mobileno, String sms_text) {

            this.mobileno = mobileno;
            this.sms_text = sms_text;

        }

        protected void onPreExecute() {
            Response = "";
            /*dialog1 = ProgressDialog.show(ActivitySMS.this, "Sending SMS",
                    "", true, false);*/
        }


        protected String doInBackground(String... params) {


            MyApplication.log("SMS", "mobileno is: " + mobileno);
            MyApplication.log("SMS", "sms text is: " + sms_text);
            Response = MyApplication.post_send_sms(this.mobileno, this.sms_text);
            // publishProgress("");
            return "";

        }

        protected void onProgressUpdate(String... progress) {


            // dialog1.dismiss();

        }
    }


}
