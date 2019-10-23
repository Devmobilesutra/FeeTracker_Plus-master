package com.mobilesutra.feetrackerclass;

import java.util.ArrayList;
import java.util.HashMap;

import com.mobilesutra.feetrackerclass.ErrorHandler.ExceptionHandler;
import com.mobilesutra.feetrackerclass.activities.Send_sms;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SelectiveNotice extends Activity {
    ListView lv;
    EditText edittext;
    int textlength=0,reload=0;
    int check;
    //ArrayList<HashMap<String, String>> data,PresentData;
    Button send;
    String usermo;
    ProgressDialog progressDialog;

    MyCustomAdapter dataAdapter = null;
//    ArrayList<NameValuePair> SendingData;

    //BasicNameValuePair NameAndValue,NameAndValue1,NameAndValue2,NameAndValue3,NameAndValue4,NameAndValue5,NameAndValue6,NameAndValue7,NameAndValue8,NameAndValue10,NameAndValue9;
    InputMethodManager imm;
    String data3;
    TextView t0;
    int n=0;
    String subj,notice,branch,std,batch;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.insert_notice2);

        send=(Button)findViewById(R.id.Send);
        edittext=(EditText)findViewById(R.id.search);
        t0=(TextView)findViewById(R.id.text1);

        Intent in=getIntent();
        //noticetitle=in.getExtras().getString("NoticeTitle");
        notice=in.getExtras().getString("Notice");
        branch=in.getExtras().getString("Branch");
        std=in.getExtras().getString("Standard");
        batch=in.getExtras().getString("Batch");
        subj=in.getExtras().getString("subj");


        //Integer flag=Integer.valueOf(sender.getExtras().getString("updatedFlag").trim().toString());
 //       mClient = new DefaultHttpClient();  //Object of DefaultHttpClient

//        httppost = new HttpPost("http://smartbridges.co.in/Android/SelectiveNotice1.php");


        RequestBody formBody = new FormBody.Builder()

                .build();

       String response12 = ((MyApplication) getApplication()).post_server_call("http://smartbridges.co.in/Android/SelectiveNotice1.php", formBody);

//	    if(((MyApplication)getApplication()).update==0)
//        {
//			//StartProgressDailog();
//	    	
//			new LongOperation().execute();
//		}
	   
		/*if(flag==0)
		{
			//StartProgressDailog();
			new LongOperation().execute();
			//Toast.makeText(this, "Data:", Toast.LENGTH_LONG).show();
		}
		 else
	      if(flag==2)
	      {
	            	  // Toast.makeText(Login.this, "Connection Is Slow...",Toast.LENGTH_LONG).show();
	            	   AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        	  	   builder
	        	  	   .setTitle("Internet Speed Is Slow...!")
	        	  	   .setMessage("Do You Want To Exit?")
	        	  	   .setIcon(android.R.drawable.ic_dialog_alert)
	        	  	   .setPositiveButton("Try Again", new DialogInterface.OnClickListener() 
	        	  	   {
	        	  	    public void onClick(DialogInterface dialog, int which)
	        	  	    {		
	        	  	    	
	        	  	    	//StartProgressDailog();
	        	  	    	//Yes button clicked, do something 	
	        	  	    	new LongOperation().execute();
	        	        }
	        	  		})
	        	       	.setNegativeButton("Exit", new DialogInterface.OnClickListener() 
	        		  	   {
	        		  	    public void onClick(DialogInterface dialog, int which)
	        		  	    {			      	
	        		  	    	
	        		  	    	
	        		  	    	Toast.makeText(InsertNotice2.this, "Exit button pressed", 
	        		                             Toast.LENGTH_SHORT).show();
	        		  	    	finish();
	        		  	    	System.exit(0);
	        		        }
	        		  		})						//Do nothing on no
	        	       	.show();
	               }   
	      else
	      {
	    	  display();
	      }*/
        display();
        send.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
 				
 			 /* for(int i=0;i<((SchoolApplication)getApplication()).data.size();i++)
		    	 {
		    		
		    		//Toast.makeText(InsertNotice2.this, "Flag"+((SchoolApplication)getApplication()).data.get(i).get("flag"), Toast.LENGTH_LONG).show();	
		    		
		    	 }*/
                confirmDialog();
            }
        });




        edittext.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start,int count,int after)
            {

            }

            public void onTextChanged(CharSequence s, int start, int before,int count)
            {
				/*data=((MyApplication)getApplication()).db.getStudentInfo1(((MyApplication)getApplication()).selectedbranch,((MyApplication)getApplication()).ClassID,((MyApplication)getApplication()).selectedstd,((MyApplication)getApplication()).selectedsubjects,((MyApplication)getApplication()).selectedbatch);	   
				textlength = edittext.getText().length();
				ArrayList<HashMap<String, String>> data1=new ArrayList<HashMap<String,String>>();
				
				for (int i = 0; i < data.size(); i++) 
				{
					String name =data.get(i).get("StudentID").toString();
					String searchtext = edittext.getText().toString();
					if (textlength <=name.length())
					{
						if (name.toLowerCase().indexOf(searchtext.toLowerCase())!= -1)
						{
							    HashMap<String, String> map = new HashMap<String, String>();
				        	    map.put("StudentName",data.get(i).get("StudentName").toString());
								map.put("StudentID", data.get(i).get("StudentID").toString());
								map.put("Flag",data.get(i).get("Flag").toString());
								map.put("FeeFlag",data.get(i).get("FeeFlag").toString());
								// adding HashList to ArrayList
								data1.add(map);
							
						}
					}
				}
				displayListView(data1);	
				*/


            }
        });

    }

    private void displayListView(ArrayList<HashMap<String, String>> data)
    {

        ArrayList<ListModel> studlist = new ArrayList<ListModel>();
        int records=data.size();
        ListModel student1;
        if(records==0)
        {
            Toast.makeText(SelectiveNotice.this,"No Data ....",Toast.LENGTH_LONG).show();

        }
        else
        {
            String str,NoticeData,NoticeData1;
            for(int i=0;i<records;i++)
            {
                str="";
                Boolean f=Boolean.valueOf("0");
                //  NoticeData = data.get(i).get("firstname").toString();
                // NoticeData1 = data.get(i).get("lastname").toString();

                // str=str.concat(NoticeData).concat(" ").concat(NoticeData1);
                student1=new ListModel(data.get(i).get("rollno"),data.get(i).get("sname"),"0",data.get(i).get("phno"),"0","","","","");//f.toString()
                Log.d("student  LISTADAPTER", student1.toString());
                studlist.add(student1);

            }
        }
        dataAdapter = new MyCustomAdapter(this,R.layout.student_list_row, studlist);
        ListView listView = (ListView) findViewById(R.id.ListView01);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                ListModel country = (ListModel) parent.getItemAtPosition(position);
                //  Toast.makeText(getApplicationContext(),
                //  "Clicked on Row: " + country.getName(),
                // Toast.LENGTH_LONG).show();
            }
        });

    }





    private class MyCustomAdapter extends ArrayAdapter<ListModel>
    {

        private ArrayList<ListModel> studentList;

        public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<ListModel> studentList)
        {
            super(context, textViewResourceId, studentList);
            this.studentList = new ArrayList<ListModel>();
            this.studentList.addAll(studentList);

        }


        private class ViewHolder
        {
            TextView studid;
            TextView name;
            TextView ispresent;
            CheckBox check;
            TextView feeflag;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            ViewHolder holder = new ViewHolder();
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.student_list_row, null);

                holder.studid = (TextView) convertView.findViewById(R.id.StudID1);
                holder.name = (TextView) convertView.findViewById(R.id.name1);
                //  holder.lastname = (TextView) convertView.findViewById(R.id.lastname);
                holder.ispresent = (TextView) convertView.findViewById(R.id.ispresent);
                holder.check = (CheckBox) convertView.findViewById(R.id.checkBox1);

                // holder.feeflag=(TextView) convertView.findViewById(R.id.FeesPaid);
                convertView.setTag(holder);

                holder.check.setOnClickListener( new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        CheckBox cb = (CheckBox) v ;

                        ListModel country = (ListModel) cb.getTag();
                        //Toast.makeText(getApplicationContext(),"Clicked on Checkbox: " + country.studid +" is " + cb.isChecked(),Toast.LENGTH_LONG).show();
               //         country.setSelected(cb.isChecked());
                        if(cb.isChecked()==true)
                        {
                            for(int i=0;i<((MyApplication)getApplication()).data.size();i++)
                            {
                                /*if(((MyApplication)getApplication()).data.get(i).get("sname").equals(country.name))
                                {*/
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put("flag", "1");
                                    map.put("name", ((MyApplication)getApplication()).data.get(i).get("sname"));
                                    map.put("rollno", ((MyApplication)getApplication()).data.get(i).get("rollno"));
                                    map.put("phno", ((MyApplication)getApplication()).data.get(i).get("phno"));
                                    ((MyApplication)getApplication()).data.set(i, map);


                             //   }
                            }
                            //country.isselected="1";
                            //((MyApplication)getApplication()).db.PresentStudent(((MyApplication)getApplication()).selectedbranch,country.name,((MyApplication)getApplication()).ClassID,((MyApplication)getApplication()).selectedstd,((MyApplication)getApplication()).selectedsubjects,((MyApplication)getApplication()).selectedbatch);
                        }
                        else
                        {
                            for(int i=0;i<((MyApplication)getApplication()).data.size();i++)
                            {
                               /* if(((MyApplication)getApplication()).data.get(i).get("sname").equals(country.name))
                                {*/
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put("flag", "0");
                                    map.put("sname", ((MyApplication)getApplication()).data.get(i).get("sname"));
                                    map.put("rollno", ((MyApplication)getApplication()).data.get(i).get("rollno"));
                                    map.put("phno", ((MyApplication)getApplication()).data.get(i).get("phno"));
                                    ((MyApplication)getApplication()).data.set(i, map);


                              //  }
                            }
                            // country.isselected="0";
                            // ((MyApplication)getApplication()).db.AbsentStudent(((MyApplication)getApplication()).selectedbranch,country.name,((MyApplication)getApplication()).ClassID,((MyApplication)getApplication()).selectedstd,((MyApplication)getApplication()).selectedsubjects,((MyApplication)getApplication()).selectedbatch);
                        }

                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();

            }

            ListModel student = studentList.get(position);

            holder.studid.setText(student.getId());
            holder.name.setText(student.getName());
//            holder.ispresent.setText(student.isselected);
//            holder.check.setChecked(student.isSelected());
            holder.check.setTag(student);
            //holder.feeflag.setText(student.getfeeflag());
		/*   if( holder.feeflag.getText().toString().equals("1"))
		   {
			   holder.studid.setTextColor(Color.WHITE);
			   holder.name.setTextColor(Color.WHITE);
			   convertView.setBackgroundColor(Color.parseColor("#FF0000"));
		   }
		   else
		   {
			   holder.studid.setTextColor(Color.WHITE);
			   holder.name.setTextColor(Color.WHITE);
			   convertView.setBackgroundColor(Color.parseColor("#009E12"));
			  
		   }
		   */
            Log.d("GET VIEW ", " ddddd"+convertView.toString());
            return convertView;

        }

    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity_main, menu);

        return true;
    }

    //	 public void onBackPressed()
//	  {
////		 Intent in2=new Intent(SelectiveNotice.this,MainActivity.class);
//// 		 startActivity(in2);
//// 		 finish();
//	  }
//	 
    private class LongOperation  extends AsyncTask<String, Void, Void>
    {

        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(SelectiveNotice.this);

        // TextView uiUpdate = (TextView) findViewById(R.id.output);

        protected void onPreExecute() {
            // NOTE: You can call UI Element here.


            //  uiUpdate.setText("Output : ");
            Dialog.setMessage("Fetching Data.......");
            Dialog.show();
            Dialog.setCanceledOnTouchOutside(false);
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls)
        {

            //n=((MyApplication)getApplication()).getStudentData();
            // n=((MyApplication)getApplication()).getStudentData();
            //n=((MyApplication)getApplication()).getFeeDefaulterStudents();
            return null;
        }

        protected void onPostExecute(Void unused)
        {
            Dialog.dismiss();
            ((MyApplication)getApplication()).update++;
            display();
            if (Error != null) {

                //  uiUpdate.setText("Output : "+Error);

            } else {

                // uiUpdate.setText("Output : "+Content);

            }
        }

    }

    public void display()
    {
        if(n==11)
        {
            // Toast.makeText(Login.this, "Connection Is Slow...",Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle("Internet Speed Is Slow...!")
                    .setMessage("Do You Want To Exit?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Try Again", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {

                            //StartProgressDailog();
                            //Yes button clicked, do something
                            new LongOperation().execute();
                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {


                            Toast.makeText(SelectiveNotice.this, "Exit button pressed",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            System.exit(0);
                        }
                    })						//Do nothing on no
                    .show();
        }
        //  data=new ArrayList<HashMap<String,String>>();


       /* String subb;
        if(FirstPage.selectedsubjects.size()==1)
        {
            String br=FirstPage.Subjectli.toString().replace("[","");
            String tr=br.replace("]","");
            subb=tr.replace(",","");
            t0.setText("Std :-"+((MyApplication)getApplication()).selectedstd+"     Sub :-  "+subb);
            Log.d("selected subject", subb);
        }
        else
        {
            String br=FirstPage.Subjectli.toString().replace("[","");
            String tr=br.replace("]","");
            tr=tr.substring(0,tr.length()-1);
            t0.setText("Std :-"+((MyApplication)getApplication()).selectedstd+"     Sub :-  "+tr);
            subb=tr.replace(",","','");
            Log.d("selected subject,batch", subb+"   "+((MyApplication)getApplication()).multipleselectedbatches);

        }
        t0.setTextColor(Color.RED);*/

        ((MyApplication) getApplication()).data=MyApplication.dbo.getstudentlist2(MyApplication.get_session("classid"),branch,std,batch,subj);

//        ((MyApplication) getApplication()).data=((MyApplication)getApplication()).dbo.getstudentlist1(MyApplication.get_session("classid"),branch,std,batch);
        int records=((MyApplication)getApplication()).data.size();
        displayListView(((MyApplication)getApplication()).data);
        Log.d("RECARD SIZE", " Size  "+records);
        if(records==0 && reload==0)
        {
            new LongOperation().execute();
            reload++;
        }
        //Toast.makeText(SelectiveNotice.this, "Size  "+records,Toast.LENGTH_SHORT).show();

    }


    /*public void StartProgressDailog()
    {
         progressDialog = ProgressDialog.show(MainActivity.this, "", "Fetching Data.......");

           new Thread()
           {

           public void run()
           {
               int n=0;
           try
           {
               n=((MyApplication)getApplication()).getStudentData();
               n=((MyApplication)getApplication()).getFeeDefaulterStudents();
           }
           catch (Exception e)
           {

           Log.e("tag", e.getMessage());

           }
           // dismiss the progress dialog
           progressDialog.dismiss();
           if(n==11)
           {
           Intent in1=new Intent(MainActivity.this,MainActivity.class);
              in1.putExtra("updatedFlag","2");
              startActivity(in1);
              finish();
           }
           else
           {

               Intent in1=new Intent(MainActivity.this,MainActivity.class);
                  in1.putExtra("updatedFlag","1");
                  startActivity(in1);
                  finish();
           }
         }

     }.start();
  }
    */
    public void confirmDialog()
    {

        if(check !=1){
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

            builder1.setTitle("Check  Massege");
            builder1.setMessage("Date:"+ ((MyApplication)getApplication()).selecteddate+"\nStd:"+((MyApplication)getApplication()).selectedstd+"\nMessage Count :"+((MyApplication)getApplication()).Messagecount+"\n\nEnter 10 Digit Mo No");
            builder1.setView(input);
            builder1.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String value = input.getText().toString().trim();
                            Toast.makeText(getApplicationContext(), value + "  msg " + notice.toString(), Toast.LENGTH_SHORT).show();

                            if (value.length() == 10) {

                                usermo = value.trim();
                        /*HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://smartbridges.co.in/Android/Ckecksms.php");

                        try {

                            List personalnameValuepairs = new ArrayList(7);
                            BasicNameValuePair mobile = new BasicNameValuePair("Mo", value.trim());
                            BasicNameValuePair massege1 = new BasicNameValuePair("msg", notice.toString().trim());
                            BasicNameValuePair date = new BasicNameValuePair("Date", ((MyApplication) getApplication()).selecteddate);
                            BasicNameValuePair clid = new BasicNameValuePair("ClassID", ((MyApplication) getApplication()).ClassID);
                            BasicNameValuePair std = new BasicNameValuePair("Standard", ((MyApplication) getApplication()).selectedstd);
                            BasicNameValuePair branch = new BasicNameValuePair("Branch", ((MyApplication) getApplication()).selectedbranch);
                            BasicNameValuePair batch = new BasicNameValuePair("Batch", ((MyApplication) getApplication()).selectedbatch);

                            personalnameValuepairs.add(mobile);
                            personalnameValuepairs.add(massege1);
                            personalnameValuepairs.add(date);
                            personalnameValuepairs.add(clid);
                            personalnameValuepairs.add(std);
                            personalnameValuepairs.add(branch);
                            personalnameValuepairs.add(batch);
                            UrlEncodedFormEntity dataentitytosend = new UrlEncodedFormEntity(personalnameValuepairs);
                            httppost.setEntity(dataentitytosend);
                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            String response111 = httpclient.execute(httppost, responseHandler);*/


/*

                        } catch (UnsupportedEncodingException e1) {

                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } catch (ClientProtocolException e) {

                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
*/


                                RequestBody formBody = new FormBody.Builder()
                                        .add("Mo", value.trim())
                                        .add("msg", notice.toString().trim())
                                        .add("Date", ((MyApplication) getApplication()).selecteddate)
                                        .add("ClassID", MyApplication.get_session("classid"))
                                        .add("Standard", std)
                                        .add("Branch", branch)
                                        .add("Batch", batch)


                                        .build();

                                String response111 = ((MyApplication) getApplication()).post_server_call("http://smartbridges.co.in/Android/Ckecksms.php", formBody);
                                Log.i("tag", "Response:" + response111);


                                if (response111.toString().trim().equals("Send")) {
                                    Toast.makeText(getApplicationContext(), "Massege Sent Successfully", Toast.LENGTH_SHORT).show();
                                    check = 1;
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Enter Valid 10 Digit Mobile Number", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

            )
                        .

                setNegativeButton("Cancel", null)                        //Do nothing on no

                .

                show();
            }
            if(check==1){
            String sub="",absentdata="";
            String subid;

            String str="Date:"+ ((MyApplication)getApplication()).CurDate+"\nStd:"+((MyApplication)getApplication()).selectedstd;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle("Sending Data..!")
                    .setMessage(str+"\nDo You Confirm ?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Yes button clicked, do something
                            String ds = "";
                            for (int i = 0; i < ((MyApplication) getApplication()).data.size(); i++) {
                                if (((MyApplication) getApplication()).data.get(i).get("flag").equals("1")) {
                                    if (ds.equals("")) {
                                        ds = ((MyApplication) getApplication()).data.get(i).get("rollno");
                                    } else {
                                        ds = ds.concat(",").concat(((MyApplication) getApplication()).data.get(i).get("rollno"));
                                    }
                                }

                            }
                            Toast.makeText(SelectiveNotice.this, "data" + ds, Toast.LENGTH_LONG).show();

                            //  data3=((MyApplication)getApplication()).db.getAbsentStudentData(((MyApplication)getApplication()).selectedbranch,((MyApplication)getApplication()).ClassID,((MyApplication)getApplication()).selectedstd,subid,((MyApplication)getApplication()).selectedbatch);
                            //   Toast.makeText(MainActivity.this, "Data"+data3,Toast.LENGTH_SHORT).show();
                           /* NameAndValue = new BasicNameValuePair("StudentList", ds);
                            NameAndValue1 = new BasicNameValuePair("Date", ((MyApplication) getApplication()).CurDate);
                            NameAndValue2 = new BasicNameValuePair("SchoolID", ((MyApplication) getApplication()).ClassID);
                            NameAndValue3 = new BasicNameValuePair("Standard", ((MyApplication) getApplication()).selectedstd);
                            NameAndValue4 = new BasicNameValuePair("ValidDate", ((MyApplication) getApplication()).CurDate);
                            NameAndValue5 = new BasicNameValuePair("Batch", ((MyApplication) getApplication()).selectedbatch);
                            NameAndValue6 = new BasicNameValuePair("Branch", ((MyApplication) getApplication()).selectedbranch);
                            NameAndValue7 = new BasicNameValuePair("SubjectID", ((MyApplication) getApplication()).selectedsubjectid);
                            NameAndValue8 = new BasicNameValuePair("NoticeTitle", "");
                            NameAndValue9 = new BasicNameValuePair("Notice", notice);
                            NameAndValue10 = new BasicNameValuePair("usmo", usermo);

                            SendingData = new ArrayList<NameValuePair>();
                            SendingData.add(NameAndValue);
                            SendingData.add(NameAndValue1);
                            SendingData.add(NameAndValue2);
                            SendingData.add(NameAndValue3);
                            SendingData.add(NameAndValue4);
                            SendingData.add(NameAndValue5);
                            SendingData.add(NameAndValue6);
                            SendingData.add(NameAndValue7);
                            SendingData.add(NameAndValue8);
                            SendingData.add(NameAndValue9);
                            SendingData.add(NameAndValue10);*/
                            String Response = "";

                            RequestBody formBody = new FormBody.Builder()
                                    .add("StudentList", ds)
                                    .add("Date", ((MyApplication) getApplication()).CurDate)
                                    .add("SchoolID", MyApplication.get_session("classid"))
                                    .add("Standard", std)
                                    .add("ValidDate", ((MyApplication) getApplication()).CurDate)
                                    .add("Batch", batch)
                                    .add("Branch", branch)
                                    .add("SubjectID", "")
                                    .add("NoticeTitle", "")
                                    .add("Notice", notice)
                                    .add("usmo", usermo)


                                    .build();

                            Response = ((MyApplication) getApplication()).post_server_call("http://smartbridges.co.in/Android/Ckecksms.php", formBody);
                            Log.i("tag", "Response:" + Response);


                            if (Response.equals("1")) {
                                Toast.makeText(SelectiveNotice.this, "Data Sent Successfully!!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SelectiveNotice.this, "Data Sending Failed!!", Toast.LENGTH_SHORT).show();
                            }


                            Intent in = new Intent(SelectiveNotice.this, InsertNotice.class);
                            startActivity(in);
                            finish();
                        }


                    })
                    .setNegativeButton("No", null)						//Do nothing on no
                    .show();

        }
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent i = new Intent(SelectiveNotice.this, Send_sms.class);
        startActivity(i);
        finish();

    }
}
