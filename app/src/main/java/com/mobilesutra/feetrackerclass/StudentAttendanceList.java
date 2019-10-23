package com.mobilesutra.feetrackerclass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesutra.feetrackerclass.ErrorHandler.ExceptionHandler;
import com.mobilesutra.feetrackerclass.activities.Add_attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by MobilesutraDesigner on 27/01/2016.
 */
public class StudentAttendanceList extends Activity {

    ListView list;
    Switch togglebtn;
    ArrayList<HashMap<String, String>> menu_list;
    ArrayList<ListModel> rowItems1 = new ArrayList<ListModel>();
    final ArrayList<String> ids = new ArrayList<String>();
    final ArrayList<String> names = new ArrayList<String>();
    ArrayList<HashMap<String, String>> imcompletefees;

    final ArrayList<String> ids_present = new ArrayList<String>();
    final ArrayList<String> names_present = new ArrayList<String>();

    CustomAdapter customAdapter;
    Button send,SendWothoutsms;
    String branch,std,batch,sub,date;
    String feeflag,sid,id;
    EditText search;
    Context context=this;
    int textlength=0;
    String toggle_flag="P";
    Typeface roboto_light,roboto_reguler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.student_attendance_list);




       roboto_light =  Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
         roboto_reguler =  Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");

        TextView stu_text = (TextView) findViewById(R.id.text1);

        TextView txt_user = (TextView) findViewById(R.id.txt_user);
        txt_user.setText(MyApplication.get_session("classname"));
        list = (ListView) findViewById(R.id.list);
        send = (Button) findViewById(R.id.Send);
        SendWothoutsms = (Button) findViewById(R.id.SendWothoutsms);

        search = (EditText) findViewById(R.id.search);
        togglebtn=(Switch)findViewById(R.id.selectPresent);
        togglebtn.setChecked(false);
        menu_list = new ArrayList<HashMap<String, String>>();

        MyApplication.set_session("Attendance", "Absent");

        Intent intent = getIntent();
        branch = intent.getStringExtra("br");
        std = intent.getStringExtra("st");
        batch = intent.getStringExtra("ba");
        sub = intent.getStringExtra("su");
        date = intent.getStringExtra("date");

        stu_text.setTypeface(roboto_reguler);
        txt_user.setTypeface(roboto_reguler);
        send.setTypeface(roboto_reguler);
//
        search.setTypeface(roboto_reguler);
        SendWothoutsms.setTypeface(roboto_reguler);


        bindComponentData();

//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (MyApplication.get_session("Attendance").equals("Absent")) {
//
//                    ((MyApplication) getApplication()).dbo.insertStudentAttendance(MyApplication.get_session("classid"), branch, std, batch, sub, ids, names, date); //MyApplication.get_session("br"), MyApplication.get_session("st"), MyApplication.get_session("ba"), MyApplication.get_session("su")
//                    /*SmsManager smsManager = SmsManager.getDefault();
//                    String message = "Hello";
//                    ArrayList<String> parts = smsManager.divideMessage(message);
//                    smsManager.sendMultipartTextMessage(Phoneno, null, parts,
//                            null, null);*/
//
//                } else {
//
//                    ids_present.clear();
//                    names_present.clear();
//                    for (int i = 0; i < menu_list.size(); i++) {
//                        String id = menu_list.get(i).get("id");
//                        String name = menu_list.get(i).get("sname");
//                        if (!ids.contains(id)) {
//                            ids_present.add(id);
//                            names_present.add(menu_list.get(i).get("sname"));
//                        }
//
//
//                    }
//
//
//                    Log.i("TAG", "ids_present" + ids_present);
//                    Log.i("TAG", "names_present" + names_present);
//
//                }
//                Log.i("TAG", "names_present" + ids);
//                ((MyApplication) getApplication()).dbo.insertStudentAttendance(MyApplication.get_session("classid"), branch, std, batch, sub, ids_present, names_present, date);// MyApplication.get_session("br"), MyApplication.get_session("st"), MyApplication.get_session("ba"), MyApplication.get_session("su")
//            }
//
//        });
        togglebtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (togglebtn.isChecked()) {
                    toggle_flag = "A";
                    togglebtn.setTextOn("P");
                    Toast.makeText(context,"A-"+toggle_flag,Toast.LENGTH_SHORT).show();
                } else {
                    toggle_flag = "P";

                    togglebtn.setTextOff("A");
                    Toast.makeText(context,"P-"+toggle_flag,Toast.LENGTH_SHORT).show();
                }

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int records = menu_list.size();
                int c = ids.size();
                Log.i("TAG", "ids_-->" + c);
                String toggle_flag_2;
                if (toggle_flag.equals("P")) {
                    toggle_flag_2 = "A";
                } else {
                    toggle_flag_2 = "p";
                }

                for (int i = 0; i < records; i++) {

                    String isvalue = menu_list.get(i).get("id");
                    String name = menu_list.get(i).get("sname");

                    for (int j = 0; j < c; j++) {
                        id = ids.get(j);

                        if (id == isvalue) {
//                          ((MyApplication) getApplication()).dbo.insertStudentAttendance(MyApplication.get_session("classid"), branch, std, batch, sub, ids, names, date); //MyApplication.get_session("br"), MyApplication.get_session("st"), MyApplication.get_session("ba"), MyApplication.get_session("su")
                            String rowId = MyApplication.dbo.insertAbsentStudentRegister(MyApplication.get_session("classid"), isvalue, name, date, branch, std, batch, sub, toggle_flag_2,"");
                            Log.i("TAG", "ids_-->insert" + rowId);
                        }
                    }

                    String rowId = MyApplication.dbo.insertAbsentStudentRegister(MyApplication.get_session("classid"), isvalue, name, date, branch, std, batch, sub, toggle_flag,"");
                    Log.i("TAG", "ids_-->insert" + rowId);
//                    ((MyApplication) getApplication()).dbo.insertStudentAttendance(MyApplication.get_session("classid"), branch, std, batch, sub, ids, names, date); //MyApplication.get_session("br"), MyApplication.get_session("st"), MyApplication.get_session("ba"), MyApplication.get_session("su")


//
                    Toast.makeText(context,"successful send sms",Toast.LENGTH_SHORT).show();

                }

            }
        });



//    btn_select_present.setOnClickListener(new View.OnClickListener()
//    {
//        @Override
//        public void onClick (View view){
//
//        MyApplication.set_session("Attendance", "Present");
//
//        Log.i("TAG", "ID arraylist:" + ids);
//        Log.i("TAG", "On Present buttn:ids_present" + ids_present);
//            Log.i("TAG", "On Present buttn:names_present" + names_present);
//    }
//    }
//
//    );

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                textlength = search.getText().length();
                ArrayList<ListModel> row = new ArrayList<ListModel>();
                ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
                data = ((MyApplication) getApplication()).dbo.getStudentListForAttendance(MyApplication.get_session("classid"), branch, std, batch, sub,"");

                for (int i = 0; i < data.size(); i++) {
                    String name = data.get(i).get("sname").toString();
                    String searchtext = search.getText().toString();
                    if (textlength <= name.length()) {
                        if (name.toLowerCase().indexOf(searchtext.toLowerCase()) != -1) {


                            ListModel item = new ListModel(menu_list.get(i).get("id"), menu_list.get(i).get("sname"), menu_list.get(i).get("phoneno"), menu_list.get(i).get(""), menu_list.get(i).get(""), "", "","","");


                            row.add(item);
                        }
                    }
                }


               /* String[] from1 = {"studname", "phone1", "phone2", "standard"};
                // Ids of views in listview_layout
                int[] to1 = {R.id.name, R.id.StudID, R.id.username, R.id.password, R.id.ispresent};*/
                CustomAdapter sd1 = new CustomAdapter(getBaseContext(), row);
                list.setAdapter(sd1);

            }
        });


}


    public void bindComponentData() {

        menu_list = null;
        menu_list = new ArrayList<HashMap<String, String>>();

        menu_list = ((MyApplication) getApplication()).dbo.getStudentListForAttendance(MyApplication.get_session("classid"),branch,std,batch,sub,"" );//MyApplication.get_session("br"), MyApplication.get_session("st"), MyApplication.get_session("ba"), MyApplication.get_session("su")

        rowItems1 = new ArrayList<ListModel>();
        Log.i("menu_list", "attendance-list" + menu_list);

        int records = menu_list.size();

        for (int i = 0; i < records; i++) {


            ListModel item = new ListModel(menu_list.get(i).get("id"), menu_list.get(i).get("sname"), menu_list.get(i).get("phoneno"), menu_list.get(i).get(""), menu_list.get(i).get(""),"","","","");

            rowItems1.add(item);

            Log.i("rowItems1:id", "" + rowItems1.get(i).getId());
            Log.i("rowItems1:name", "" + rowItems1.get(i).getName());
            Log.i("rowItems1:phoneno", "" + rowItems1.get(i).getPhone1());

        }


        customAdapter = new CustomAdapter(this, rowItems1);
        list.setAdapter(customAdapter);

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

    @Override
    public void onClick(View view) {

    }

    /**
     * ****** Create a holder Class to contain inflated xml file elements ********
     */
    public class ViewHolder {

        public TextView sname, roll, ispresent, feeflag;
        CheckBox check;
        ImageView circle;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        final ViewHolder holder;


        convertView = null;
        if (convertView == null) {
            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.attendance_list, null);
            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();

            holder.sname = (TextView) vi.findViewById(R.id.sname);
            holder.roll = (TextView) vi.findViewById(R.id.id);
            holder.ispresent = (TextView) vi.findViewById(R.id.ispresent);
            holder.check = (CheckBox) vi.findViewById(R.id.checkBox1);
            holder.feeflag = (TextView) vi.findViewById(R.id.feeflag);
            holder.circle = (ImageView) vi.findViewById(R.id.circle);

            holder.sname.setTypeface(roboto_light);
            holder.roll.setTypeface(roboto_light);
//            holder.ispresent.setTypeface(roboto_light);
            holder.feeflag.setTypeface(roboto_light);
            /************ Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        final ListModel row_pos = rowItems.get(position);
        holder.sname.setText(row_pos.getName());
        holder.roll.setText(row_pos.getId());

        imcompletefees = new ArrayList<HashMap<String, String>>();
        imcompletefees = ((MyApplication) getApplication()).dbo.getBalance(row_pos.getId());
        Log.i("TAG", "id with balance 0 ---->" + imcompletefees);

        for(int i = 0 ; i<imcompletefees.size();i++)
        {
            sid = imcompletefees.get(i).get("sid");

            Log.i("TAG", "feeflag ---->" + feeflag);
            Log.i("TAG", "sid ---->" + sid);

            if(row_pos.getId().contains(sid))
            {
              //  holder.sname.setTextColor(Color.parseColor("#8b1919"));
                holder.circle.setBackgroundResource(R.drawable.ovalshape);
            }
        }

          //  holder.sname.setTextColor(Color.parseColor("#8b1919"));


            holder.check.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;

                    if (cb.isChecked()) {

                        Log.i("TAG", "In If");
                        Log.i("TAG", "In If:cb.ischecked()");

                        String id = row_pos.getId().toString();
                        Log.i("TAG", "In If:id:" + id);

                        ids.add(row_pos.getId().toString());
                        names.add(row_pos.getName());

                    } else {

                        Log.i("TAG", "In Else");
                        Log.i("TAG", "In Else:cb.ischecked()");
                        String id = row_pos.getId().toString();
                        Log.i("TAG", "In Else:id:" + id);
                        if (ids.contains(id)) {
                            ids.remove(ids.indexOf(id));
                        }
                }

                }
            });


            vi.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

            }
        });


        return vi;

    }

}


    @Override
    public void onBackPressed() {

        Intent i = new Intent(StudentAttendanceList.this, Add_attendance.class);
        startActivity(i);
        finish();
    }
}
