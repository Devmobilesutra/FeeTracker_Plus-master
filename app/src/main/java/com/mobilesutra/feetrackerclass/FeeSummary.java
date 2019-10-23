package com.mobilesutra.feetrackerclass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mobilesutra.feetrackerclass.ErrorHandler.ExceptionHandler;
import com.mobilesutra.feetrackerclass.activities.ActivityFeeManagement;
import com.mobilesutra.feetrackerclass.model.SubjectModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class FeeSummary extends Activity {
    TextView std, total, received, balance, edit_total, edit_received, edit_balance;
    TableLayout table1, table2;

    Context context = this;
    String std1, sub1, tot1, rec1, bal1, branch, batch;
    int classtot = 0, classrec = 0, stdtot, stdrec;
    ArrayList<LinkedHashMap<String, String>> arrayList = new ArrayList<LinkedHashMap<String, String>>();
    LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
    ArrayList<String> addData;
    ;
    ArrayList<FeeModel> rowItems1;
    List<ListModel> arrayDTO = null;
    RecyclerView recycler_view = null;
    RecyclerAdapter recyclerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        Log.d("SCREEN", width + "----" + height);
//        if (width < 800) {
//            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }

        getIntentData();
        initComponentData();
//        initComponentListeners();
        initbindComponentData();

    }

    public void getIntentData() {

    }

    public void initComponentData() {
        setContentView(R.layout.fee_summary);
        total = (TextView) findViewById(R.id.tot_total);
        received = (TextView) findViewById(R.id.tot_received);
        balance = (TextView) findViewById(R.id.tot_balance);
        edit_total = (TextView) findViewById(R.id.edit_total);
        edit_received = (TextView) findViewById(R.id.edit_received);
        edit_balance = (TextView) findViewById(R.id.edit_balance);
        table1 = (TableLayout) findViewById(R.id.TableLayout11);
        recycler_view = (RecyclerView) findViewById(R.id.list);
        arrayDTO = new ArrayList<ListModel>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FeeSummary.this);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setHasFixedSize(true);

    }

//    public void initComponentListeners() {
//
//
//        std1 = "";
//        sub1 = "";
//        tot1 = "0";
//        rec1 = "0";
//        bal1 = "0";
//        stdtot = 0;
//        stdrec = 0;
//
//
//        arrayList = ((MyApplication) getApplication()).dbo.DisplayBalance(MyApplication.get_session("classid"));
//        String previous_std = "", new_std = "";
//        Log.i("Database", "Previous std is_s " + arrayList);
//        Collections.sort(arrayList,
//                new Comparator<LinkedHashMap<String, String>>() {
//                    @Override
//                    public int compare(LinkedHashMap<String, String> obj1,
//                                       LinkedHashMap<String, String> obj2) {
//                        return (obj1.get("branch").compareTo(obj2.get("branch")));
//                    }
//                });
//        Log.i("Database", "Previous std is " + arrayList);
//        if (arrayList.size() > 0) {
//            previous_std = arrayList.get(0).get("branch");
//
//            Log.i("Database", "Previous std is_p " + previous_std);
//
//            for (int i = 0; i < arrayList.size(); i++) {
//
//                lhm = arrayList.get(i);
//                std1 = lhm.get("std");
//                sub1 = lhm.get("sub");
//                branch = lhm.get("branch");
//                batch = lhm.get("batch");
//                tot1 = lhm.get("total");
//                if (TextUtils.isEmpty(tot1)) {
//                    tot1 = "0";
//                }
//                rec1 = lhm.get("feepaid");
//                bal1 = lhm.get("bal");
//
//                if (previous_std.equals(branch)) {
//                    stdtot = Integer.parseInt(tot1) + stdtot;
//                    stdrec = Integer.parseInt(rec1) + stdrec;
//                    Log.i("Database", "Previous std is_if " + stdtot + "" + stdtot);
//                } else {
//                    Log.i("Database", "Previous std is_else " + stdtot);
//                    if (stdtot > 0) {
//
//                        int bal22 = stdtot - stdrec;
//
//                        //sub1 = "All_Sub";
//                        //tot1 = Integer.toString(stdtot);
//                        //rec1 = Integer.toString(stdrec);
//                        //bal1 = Integer.toString(bal22);
//
//
//                        TableLayout table1 = (TableLayout) findViewById(R.id.TableLayout11);
//                        TableRow row1 = new TableRow(this);
//
//                        row1.setBackgroundColor(getResources().getColor(R.color.yellow));
//
//                        TextView t12 = new TextView(this);
//                        t12.setText("");
//                        t12.setTextSize(18);
//                        t12.setTextColor(Color.WHITE);
//                        t12.setGravity(Gravity.CENTER);
//                        row1.addView(t12);
//
//                        t12 = new TextView(this);
//                        t12.setText("");
//                        t12.setTextSize(18);
//                        t12.setTextColor(Color.WHITE);
//                        t12.setGravity(Gravity.CENTER);
//                        row1.addView(t12);
//
//
//                        t12 = new TextView(this);
//                        String branchName = MyApplication.dbo.getBranchName(previous_std);
//                        t12.setText(branchName);
//                        t12.setTextSize(18);
//                        t12.setTextColor(Color.WHITE);
//                        t12.setGravity(Gravity.CENTER);
//
//                        new_std = std1;
//
//                        row1.addView(t12);
//
//                        t12 = new TextView(this);
//                        t12.setText("All Sub");
//                        t12.setTextSize(18);
//                        t12.setTextColor(Color.WHITE);
//                        t12.setGravity(Gravity.CENTER);
//                        row1.addView(t12);
//
//                        t12 = new TextView(this);
//                        t12.setText(stdtot + "");
//                        t12.setTextSize(18);
//                        t12.setTextColor(Color.WHITE);
//                        t12.setGravity(Gravity.RIGHT);
//                        row1.addView(t12);
//
//                        t12 = new TextView(this);
//                        t12.setText(stdrec + "");
//                        t12.setTextSize(18);
//                        t12.setTextColor(Color.WHITE);
//                        t12.setGravity(Gravity.RIGHT);
//                        row1.addView(t12);
//
//                        t12 = new TextView(this);
//                        t12.setText(bal22 + "");
//                        t12.setTextSize(18);
//                        t12.setTextColor(Color.WHITE);
//                        t12.setGravity(Gravity.RIGHT);
//                        row1.addView(t12);
//                        table1.addView(row1, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//
//
//                        classtot = stdtot + classtot;
//                        classrec = stdrec + classrec;
//
//                        stdtot = 0;
//                        stdrec = 0;
//                        bal22 = 0;
//
//                    }
//                }
//
//
//                TableLayout table = (TableLayout) findViewById(R.id.TableLayout11);
//                TableRow row = new TableRow(this);
//
//                row.setBackgroundColor(getResources().getColor(R.color.selected_gray));
//
//                TextView t1 = new TextView(this);
//
//                String branchName = MyApplication.dbo.getBranchName(branch);
//                t1.setText(branchName);
//                t1.setTextColor(Color.BLUE);
//                t1.setGravity(Gravity.CENTER);
//
//                row.addView(t1);
//
//                t1 = new TextView(this);
//                String std = MyApplication.dbo.getStandardName(std1);
//                t1.setText(std);
//                t1.setTextColor(Color.BLUE);
//                t1.setGravity(Gravity.CENTER);
//
//                row.addView(t1);
//
//                t1 = new TextView(this);
//                String batch3 = MyApplication.dbo.getbatchhhName(batch);
//                t1.setText(batch3);
//                t1.setTextColor(Color.BLUE);
//                t1.setGravity(Gravity.CENTER);
//
//                row.addView(t1);
//
//                t1 = new TextView(this);
//                String subjname = ((MyApplication) getApplication()).dbo.getSubjjjnameWithMultilpe(sub1);
//                t1.setText(subjname);
//                t1.setTextColor(Color.BLUE);
//                t1.setGravity(Gravity.CENTER);
//                row.addView(t1);
//
//                t1 = new TextView(this);
//                t1.setText(tot1);
//                t1.setTextColor(Color.RED);
//                t1.setGravity(Gravity.RIGHT);
//                row.addView(t1);
//
//                t1 = new TextView(this);
//                t1.setText(rec1);
//                t1.setTextColor(Color.RED);
//                t1.setGravity(Gravity.RIGHT);
//                row.addView(t1);
//
//                t1 = new TextView(this);
//                t1.setText(bal1);
//                t1.setTextColor(Color.RED);
//                t1.setGravity(Gravity.RIGHT);
//                row.addView(t1);
//                table.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//
//                if (new_std.equals(std1)) {
//                    if (!previous_std.equals(std1)) {
//                        previous_std = new_std;
//
//                        stdtot = Integer.parseInt(tot1) + stdtot;
//                        stdrec = Integer.parseInt(rec1) + stdrec;
//                    }
//                }
//
//            }
//
//            if (stdtot > 0) {
//
//                int bal22 = stdtot - stdrec;
//
//                //sub1 = "All_Sub";
//                //tot1 = Integer.toString(stdtot);
//                //rec1 = Integer.toString(stdrec);
//                //bal1 = Integer.toString(bal22);
//
//
//                TableLayout table1 = (TableLayout) findViewById(R.id.TableLayout11);
//                TableRow row1 = new TableRow(this);
//
//                row1.setBackgroundColor(getResources().getColor(R.color.yellow));
//
//
//                TextView t12 = new TextView(this);
//                t12.setText("");
//                t12.setTextSize(18);
//                t12.setTextColor(Color.WHITE);
//                t12.setGravity(Gravity.CENTER);
//
//                previous_std = std1;
//
//                row1.addView(t12);
//
//                t12 = new TextView(this);
//                t12.setText("");
//                t12.setTextSize(18);
//                t12.setTextColor(Color.WHITE);
//                t12.setGravity(Gravity.CENTER);
//                row1.addView(t12);
//
//
//                t12 = new TextView(this);
//                String branchName = MyApplication.dbo.getBranchName(previous_std);
//                t12.setText(branchName);
//                t12.setTextSize(18);
//                t12.setTextColor(Color.WHITE);
//                t12.setGravity(Gravity.CENTER);
//
//                previous_std = std1;
//
//                row1.addView(t12);
//
//                t12 = new TextView(this);
//                t12.setText("All Sub");
//                t12.setTextSize(18);
//                t12.setTextColor(Color.WHITE);
//                t12.setGravity(Gravity.CENTER);
//                row1.addView(t12);
//
//                t12 = new TextView(this);
//                t12.setText(stdtot + "");
//                t12.setTextSize(18);
//                t12.setTextColor(Color.WHITE);
//                t12.setGravity(Gravity.RIGHT);
//                row1.addView(t12);
//
//                t12 = new TextView(this);
//                t12.setText(stdrec + "");
//                t12.setTextSize(18);
//                t12.setTextColor(Color.WHITE);
//                t12.setGravity(Gravity.RIGHT);
//                row1.addView(t12);
//
//                t12 = new TextView(this);
//                t12.setText(bal22 + "");
//                t12.setTextSize(18);
//                t12.setTextColor(Color.WHITE);
//                t12.setGravity(Gravity.RIGHT);
//                row1.addView(t12);
//                table1.addView(row1, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//
//
//                classtot = stdtot + classtot;
//                classrec = stdrec + classrec;
//
//
//            }
//
//        }
//
//        int bal22 = classtot - classrec;
//        std1 = "All_Branch";
//        sub1 = "All_Sub";
//        tot1 = Integer.toString(classtot);
//        rec1 = Integer.toString(classrec);
//        bal1 = Integer.toString(bal22);
//        TableLayout table = (TableLayout) findViewById(R.id.TableLayout11);
//        TableRow row = new TableRow(this);
//
//        row.setBackgroundColor(Color.RED);
//
//        TextView t1 = new TextView(this);
//        t1.setText("");
//        t1.setTextColor(Color.WHITE);
//        t1.setGravity(Gravity.CENTER);
//
//        row.addView(t1);
//
//        t1 = new TextView(this);
//        t1.setText("");
//        t1.setTextColor(Color.WHITE);
//        t1.setGravity(Gravity.CENTER);
//
//        row.addView(t1);
//
//
//        t1 = new TextView(this);
//        t1.setText(std1);
//        t1.setTextColor(Color.WHITE);
//        t1.setGravity(Gravity.CENTER);
//
//        row.addView(t1);
//
//        t1 = new TextView(this);
//        t1.setText(sub1);
//        t1.setTextColor(Color.WHITE);
//        t1.setGravity(Gravity.CENTER);
//        row.addView(t1);
//
//        t1 = new TextView(this);
//        t1.setText(tot1);
//        t1.setTextColor(Color.WHITE);
//        t1.setGravity(Gravity.RIGHT);
//        row.addView(t1);
//
//
//        t1 = new TextView(this);
//        t1.setText(rec1);
//        t1.setTextColor(Color.WHITE);
//        t1.setGravity(Gravity.RIGHT);
//        row.addView(t1);
//
//        t1 = new TextView(this);
//        t1.setText(bal1);
//        t1.setTextColor(Color.WHITE);
//        t1.setGravity(Gravity.RIGHT);
//
//        row.addView(t1);
//        table.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//
//
//        edit_total.setText(tot1);
//        edit_balance.setText(bal1);
//        edit_received.setText(rec1);
//
//    }

    public void initbindComponentData() {
        addData = new ArrayList<String>();

        arrayList = ((MyApplication) getApplication()).dbo.DisplayBalanceFeeNew(MyApplication.get_session("classid"));
        Log.d("FeeSummary", "arrayList" + arrayList);
        int records = arrayList.size();
        rowItems1 = new ArrayList<FeeModel>();
        FeeModel fnew = new FeeModel();
        int rowCount = 0;
        boolean flag = false;
        int j = 0;


        int totFee = 0, totRecieved = 0, totBalance = 0, totClasses = records,  totStudents= 0;


        for (int i = 0; i < records; i++) {
            flag = true;
            j = 0;

            String f_branch = arrayList.get(i).get("branch");
            String f_batch = arrayList.get(i).get("batch");
            String f_std = arrayList.get(i).get("std");
            String f_sub = arrayList.get(i).get("sub");
            String f_total = arrayList.get(i).get("total");
            String f_bal = arrayList.get(i).get("bal");
            String f_feepaid = arrayList.get(i).get("feepaid");
            String studCount = arrayList.get(i).get("studCount");
            SubjectModel subjectModel = new SubjectModel(f_sub, f_total, f_feepaid, f_bal);

            rowCount = rowItems1.size();
            for (j = 0; j < rowCount; j++) {
                flag = rowItems1.get(j).checkData(f_branch, f_std, f_batch);
                if (!flag)
                    break;
            }

            if (flag) {
                totFee = totFee+ Integer.valueOf(f_total.trim());
                totRecieved = totRecieved+ Integer.valueOf(f_feepaid.trim());
                totBalance = totBalance+ Integer.valueOf(f_bal.trim());
                totStudents = totStudents+ Integer.valueOf(studCount.trim());

                Log.d("FeeSummary", "totFee is " + totFee);

                Log.d("FeeSummary", "size******#" + f_branch + "***" + f_std + "***" + f_batch);
                FeeModel feeModel = new FeeModel(f_branch, f_std, f_batch, studCount);
                feeModel.addSubj(subjectModel);
                rowItems1.add(feeModel);
            } else {
                rowItems1.get(j).addSubj(subjectModel);
            }
        }
        if(records > 0){
            LinearLayout relLayoutTotal = (LinearLayout) findViewById(R.id.relLayoutTotal);
            relLayoutTotal.setVisibility(View.VISIBLE);

            TextView txtFee, txtBalance, txtReceived,  txtAllStudent;
            txtFee = (TextView) findViewById(R.id.txtFee);
            txtBalance = (TextView) findViewById(R.id.txtBalance);
            txtReceived = (TextView) findViewById(R.id.txtReceived);

            txtAllStudent =  (TextView) findViewById(R.id.txtAllStudent);


            Log.d("FeeSummary ", "totFee: " + totFee + ", totBalance: " + totBalance + ", totRecieved: " + totRecieved);
            Log.d("FeeSummary ", "totClasses: " + totClasses + ", totStudents: " + totStudents);
            txtFee.setText(totFee+"");
            txtBalance.setText(totBalance+"");
            txtReceived.setText(totRecieved+"");

            txtAllStudent.setText("Total Students: "+totStudents);

        }

        recyclerAdapter = new RecyclerAdapter(rowItems1);
        recycler_view.setAdapter(recyclerAdapter);
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        List<FeeModel> rowItems1;
        String strappid = "";
        int temp = 0;

        public RecyclerAdapter(List<FeeModel> rowItems) {
            this.rowItems1 = rowItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            RecyclerView.ViewHolder vh;
            final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewsummary, viewGroup, false);
            vh = new MyViewHolder(view);

            return vh;
        }


        /*public void Remove(int pos) {
            rowItems.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(0, rowItems.size());
        }*/

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


            Log.i("FeeSummary", "RecyclerView position :  " + position);
            final FeeModel row_pos = rowItems1.get(position);


            String stdname = ((MyApplication) getApplication()).dbo.getStandardName(row_pos.getFee_stand());
            String brnchname = ((MyApplication) getApplication()).dbo.getBranchName(row_pos.getFee_branch());
            String batch = ((MyApplication) getApplication()).dbo.getbatchhhName(row_pos.getFee_batch());
            String studCount = row_pos.getStudCount();

            Log.i("FeeSummary", "Fee Summary stdname :  " + stdname);
            Log.i("FeeSummary", "Fee Summary brnchname :  " + brnchname);
            Log.i("FeeSummary", "Fee Summary batch :  " + batch);
            Log.i("FeeSummary", "Fee Summary studCount :  " + studCount);


            int main_total = 0, main_bal = 0, main_reciv = 0;

            ((MyViewHolder) holder).std.setText(stdname);
            ((MyViewHolder) holder).branch.setText(brnchname);
            ((MyViewHolder) holder).batch.setText(batch);
            Log.i("Fee_Summary", "Stud_Count" + studCount);
            ((MyViewHolder) holder).stud_count.setText("Total Students: " + studCount);
            Log.d("FeeSummary", "size**#" + row_pos.getFee_branch() + "***" + row_pos.getFee_stand() + "***" + row_pos.getFee_batch());
            ArrayList<SubjectModel> subjectData = row_pos.getItems();
            int count = subjectData.size();
            Log.i("Fee_Summary", "subjectData_Count" + count);

            for (int j = 0; j < count; j++) {

                String Str_subject = subjectData.get(j).getStr_subject();
                String Str_Str_total = subjectData.get(j).getStr_total();
                String Str_fee_paid = subjectData.get(j).getStr_fee_paid();
                String getStr_balancet = subjectData.get(j).getStr_balance();

                main_total = main_total + Integer.parseInt(Str_Str_total);
                main_reciv = main_reciv + Integer.parseInt(Str_fee_paid);
                main_bal = main_bal + Integer.parseInt(getStr_balancet);


                Log.i("Fee_Summary", "Stud_tot" + Str_Str_total);
                Log.i("Fee_Summary", "Stud_paid" + Str_fee_paid);
                Log.i("Fee_Summary", "Stud_bal" + getStr_balancet);

                TableRow row = new TableRow(context);

                row.setBackgroundColor(getResources().getColor(R.color.selected_gray));

                TextView t1 = new TextView(context);
                String subjname = ((MyApplication) getApplication()).dbo.getSubjjjnameWithMultilpe(Str_subject);
                t1.setText(subjname);
                t1.setTextColor(getResources().getColor(R.color.black_color75));
                t1.setGravity(Gravity.CENTER);
                row.addView(t1);

                t1 = new TextView(context);
                t1.setText(Str_Str_total);
                t1.setTextColor(getResources().getColor(R.color.black_color75));
                t1.setGravity(Gravity.RIGHT);
                row.addView(t1);

                t1 = new TextView(context);
                t1.setText(Str_fee_paid);
                t1.setTextColor(getResources().getColor(R.color.black_color75));
                t1.setGravity(Gravity.RIGHT);
                row.addView(t1);

                t1 = new TextView(context);
                t1.setText(getStr_balancet);
                t1.setTextColor(getResources().getColor(R.color.black_color75));
                t1.setGravity(Gravity.RIGHT);
                row.addView(t1);
                table2.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            }


            TableRow row = new TableRow(context);
            row.setBackgroundColor(getResources().getColor(R.color.selected_gray));
            TextView t1 = new TextView(context);
            t1.setText("Total");
            t1.setTextColor(Color.RED);
            t1.setGravity(Gravity.CENTER);
            row.addView(t1);

            t1 = new TextView(context);
            t1.setText(main_total + "");
            t1.setTextColor(Color.RED);
            t1.setGravity(Gravity.RIGHT);
            row.addView(t1);

            t1 = new TextView(context);
            t1.setText(main_reciv + "");
            t1.setTextColor(Color.RED);
            t1.setGravity(Gravity.RIGHT);
            row.addView(t1);

            t1 = new TextView(context);
            t1.setText(main_bal + "");
            t1.setTextColor(Color.RED);
            t1.setGravity(Gravity.RIGHT);
            row.addView(t1);
            table2.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));


            Log.d("tag", "total@@" + main_total);


        }

        @Override
        public int getItemCount() {

            Log.d("FeeSummary", "rowItems.size()" + rowItems1.size());
            //return rowItems == null ? 0 : rowItems.size();
            return rowItems1.size();

        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView sname, std, branch, batch, subj, stud_count;
            CardView cardview;
            TableLayout table1;
            ImageView img_delete, img_edit;

            public MyViewHolder(View v) {
                super(v);
                cardview = (CardView) v.findViewById(R.id.cardview);

                std = (TextView) v.findViewById(R.id.text_stand);
                branch = (TextView) v.findViewById(R.id.text_branch);
                batch = (TextView) v.findViewById(R.id.text_batch);
                subj = (TextView) v.findViewById(R.id.text_subj);
                stud_count = (TextView) v.findViewById(R.id.text_count);
                table2 = (TableLayout) v.findViewById(R.id.TableLayout11);

            }


        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Intent intent = new Intent(FeeSummary.this, StudentListInFeeMgmt.class);
        Intent intent = new Intent(FeeSummary.this, ActivityFeeManagement.class);
        startActivity(intent);
        finish();

    }
}