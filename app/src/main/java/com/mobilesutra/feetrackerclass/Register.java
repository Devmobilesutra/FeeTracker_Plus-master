package com.mobilesutra.feetrackerclass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;*/
/*import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;*/
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesutra.feetrackerclass.ErrorHandler.ExceptionHandler;

import okhttp3.FormBody;
import okhttp3.RequestBody;


public class Register extends Activity {
    EditText edt_clsname = null, edt_contactno = null, edt_email = null,
            edt_city = null, edt_description = null;
    Button btn_submit = null;
    ProgressDialog dialog1 = null;
    String classname;
    String Response = "";
    Context context = this;
    JSONObject json;
    String uname, pass, validfrom, validtill, classid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.register);

//		export_database();
        getIntentData();
        initComponentData();
        initComponentListeners();


    }

    public void getIntentData() {

    }

    public void initComponentData() {
        Typeface roboto_reguler = Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");
        Typeface roboto_light = Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
        TextView txt_user = (TextView) findViewById(R.id.txt_user);
//		txt_user.setText(MyApplication.get_session("classname"));
        txt_user.setTypeface(roboto_reguler);
        TextView heading = (TextView) findViewById(R.id.txt_user_heading);
        //heading.setText(getResources().getString(R.string.RegisterForTrial));
        heading.setText("ENTER INSTITUTE DETAILS");
        heading.setTypeface(roboto_reguler);

        edt_clsname = (EditText) findViewById(R.id.ed_name);
        edt_contactno = (EditText) findViewById(R.id.ed_mob_no);
        edt_email = (EditText) findViewById(R.id.ed_emailid);
        // edt_state = (EditText) findViewById(R.id.ed_state);
//		edt_address = (EditText) findViewById(R.id.ed_address);
        // edt_pincode = (EditText) findViewById(R.id.ed_pin);
        edt_city = (EditText) findViewById(R.id.ed_city);
        edt_description = (EditText) findViewById(R.id.ed_description);

        btn_submit = (Button) findViewById(R.id.btn_submit);

    }

    public void initComponentListeners() {


        btn_submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // if(studentid.getText().toString().equals(""))
                // {
                // Toast.makeText(Addstudent.this,"Enter Student ID...",
                // Toast.LENGTH_SHORT).show();
                // }
                // else

                if (edt_clsname.getText().toString().equals("")) {
                    Toast.makeText(Register.this, getResources().getString(R.string.valid_classname),
                            Toast.LENGTH_SHORT).show();
                } else if (!(edt_contactno.getText().toString().equals(""))
                        && edt_contactno.getText().toString().length() != 10) {

                    Toast.makeText(Register.this,
                            getResources().getString(R.string.valid_phone),
                            Toast.LENGTH_SHORT).show();

                } else if (edt_email.getText().toString().equals("")) {
                    Toast.makeText(Register.this, getResources().getString(R.string.valid_email),
                            Toast.LENGTH_SHORT).show();
                }

//				else if (edt_address.getText().toString().equals("")) {
//					Toast.makeText(Register.this, "Enter Address...",
//							Toast.LENGTH_SHORT).show();
//				}

                else if (((MyApplication) getApplication())
                        .isNetworkAvailable() == false) {
                    // Toast.makeText(Addstudent.this,"No internet Connection Student Saved in to Local Database",
                    // Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            Register.this);
                    builder.setMessage(
                            getResources().getString(R.string.Internet_problem))
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            Intent openMainActivity1 = new Intent(
                                                    Register.this,
                                                    Register.class);
                                            startActivity(openMainActivity1);
                                            finish();

                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                } else {
//					btn_submit.setBackgroundColor(Color.RED);
//					showprocessDialog();
                    Toast.makeText(Register.this, "Please Wait",
                            Toast.LENGTH_LONG).show();
                    // Addstudent();

                    new HTTPAdd(1).execute(); // end

                }

            }
        });

    }

//	public void showprocessDialog() {
//
//		btn_submit.setText("Please Wait.....");
//		dialog1 = ProgressDialog.show(Register.this, "Submitting  Form....",
//				"Please Wait", true, false);
//		dialog1.show();
//
//	}

    public void add_local() {
        ((MyApplication) getApplication()).dbo.insert_registration_details(
                classid, edt_clsname.getText().toString().trim(), edt_contactno
                        .getText().toString().trim(), edt_email.getText()
                        .toString().trim(), "", edt_city.getText().toString().trim(),
                edt_description.getText().toString().trim(), uname, pass,
                validfrom, validtill);


    }

    class HTTPAdd extends AsyncTask<Void, String, String> {


        String Response = "";
        int status_code = 0;
        int getstate = 0;

        public HTTPAdd(int getstate) {
            this.getstate = getstate;

        }

        protected void onPreExecute() {
            Response = "";
            dialog1 = ProgressDialog.show(Register.this, "Submitting  Form....",
                    "Please Wait", true, false);
        }

        protected String doInBackground(Void... params) {


            Date localDate = null;
            String date1 = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());
            try {
                localDate = sdf.parse(date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            date1 = sdf.format(localDate);
            Log.i("tag", "date:" + date1);

            RequestBody formBody = new FormBody.Builder()
                    .add("ClassName", edt_clsname.getText().toString().trim())
                    .add("ContactNo", edt_contactno.getText().toString().trim())
                    .add("EmailID", edt_email.getText().toString().trim())
                    .add("Address", "")
                    .add("City", edt_city.getText().toString().trim())
                    .add("Description", edt_description.getText().toString().trim())
                    .add("Valid_From", date1)
                    .build();
//http://192.168.0.242/ms-projects/mobilesutra.com/FeeTracker_New/Service/register
            Response = ((MyApplication) getApplication()).post_server_call("http://mobilesutra.com/FeeTracker/Service/register", formBody);
            Log.i("tag", "Response:" + Response);


            publishProgress("progress");
            return "";
        }

        protected void onProgressUpdate(String... progress) {

            dialog1.dismiss();

            try {

                if (Response.length() > 0) {


                    if (!Response.equals("-0")) {

                        try {

                            json = new JSONObject(Response);
                            if (json.getString("response_status").equals("true")) {


                                classid = json.getString("class_id");

                                Log.i("tag", "classid is" + classid);

                                add_local();
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                                alertDialog.setTitle("");
                                alertDialog.setMessage(getResources().getString(R.string.success_register));
                                alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent openMainActivity1 = new Intent(
                                                Register.this, Login.class);
                                        openMainActivity1.putExtra("classid", classid);


                                        startActivity(openMainActivity1);
                                        finish();


                                    }
                                });


                                alertDialog.show();


                            } else {
                                dialog1.dismiss();
                                String msg1 = json.getString("response_message");
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Register.this);
                                alertDialog.setTitle("");
                                alertDialog.setMessage("" + msg1);
                                alertDialog.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {


                                    }
                                });


                                alertDialog.show();
                            }
                        } catch (JSONException e) {

                            System.out.println("" + e);
                        }
                    }
                } else {

                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.fail_register), Toast.LENGTH_SHORT)
                            .show();
                }


            } catch (Exception e) {

                Log.i("mobilesutra.feetracker", "In JSONException "
                        + e.getMessage().toString());
                Toast.makeText(Register.this,
                        "ERROE" + e.getMessage().toString(), Toast.LENGTH_SHORT)
                        .show();
                Log.d("7", "7");
            }

        }

        protected void onPostExecute(Long result) {

//			 new HTTPMail(1).execute();
        }
    }// HTTPRequest

//	class HTTPMail extends AsyncTask<Void, String, String> {
//
//		String Response = "";
//		InputStream inputStream = null;
//		int status_code = 0;
//		int getstate = 0;
//
//		public HTTPMail(int getstate) {
//			this.getstate=getstate;
//
//		}
//
//		protected void onPreExecute() {
//			Response = "";
//			dialog1 = ProgressDialog.show(Register.this,
//					"Sending Data....", "Sending Data to Server..", true,
//					false);
//		}
//
//		protected String doInBackground(Void... params) {
//
//	/*		HttpClient mClient;
//			HttpPost httppost;
//			ArrayList<NameValuePair> SendingData;
//			ResponseHandler<String> ResponseHandler1;
//			UrlEncodedFormEntity EntityToSend;
//			Date localDate = null;
//			String date1 = "";
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			String date = sdf.format(new Date());
//			try {
//				localDate = sdf.parse(date);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			date1 = sdf.format(localDate);
//			Log.i("tag", "date:" + date1);
//
//			// BasicNameValuePair NameAndValue1,NameAndValue2;
//			Log.e("tag", "1");
//			Response = "";
//			mClient = new DefaultHttpClient(); // Object
//												// of
//												// DefaultHttpClient
//			Log.e("tag", "2");
//
//			httppost = new HttpPost(
//					"http://smartbridges.co.in/Android/sendmailexample.php");
//
//			Log.e("tag", "3");
//			ResponseHandler1 = new BasicResponseHandler();
//			Log.e("tag", "4");
//
//			BasicNameValuePair classname1 = new BasicNameValuePair("classname",
//					edt_clsname.getText().toString().trim());
//
//			BasicNameValuePair contact1 = new BasicNameValuePair("contactno",
//					edt_contactno.getText().toString().trim());
//
//			BasicNameValuePair email1 = new BasicNameValuePair("emailid",
//					edt_email.getText().toString().trim());
//
//			BasicNameValuePair address1 = new BasicNameValuePair("address",
//					edt_address.getText().toString().trim());
//
//			BasicNameValuePair currentdate = new BasicNameValuePair(
//					"current_date", date1);
//
//			SendingData = new ArrayList<NameValuePair>();
//			SendingData.add(classname1);
//			SendingData.add(contact1);
//			SendingData.add(email1);
//			SendingData.add(address1);
//			SendingData.add(currentdate);
//			System.out.println(SendingData);
//			Log.e("tag", "5");
//			try {
//				EntityToSend = new UrlEncodedFormEntity(SendingData);
//				Log.e("tag", "6");
//				httppost.setEntity(EntityToSend);
//				Log.e("tag", "7");
//				HttpResponse httpResponse = mClient.execute(httppost);
//				inputStream = httpResponse.getEntity().getContent();
//
//				if (inputStream != null) {
//					Response = convertInputStreamToString(inputStream);
//				} else {
//					Response = "";
//				}
//
//			} catch (ClientProtocolException e) {
//				Log.e("tag", "8");
//			} catch (IOException e) {
//				Log.e("tag", "9");
//			}*/
//
//			Date localDate = null;
//			String date1 = "";
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			String date = sdf.format(new Date());
//			try {
//				localDate = sdf.parse(date);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			date1 = sdf.format(localDate);
//			Log.i("tag", "date:" + date1);
//
//			RequestBody formBody = new FormEncodingBuilder()
//					.add("classname",edt_clsname.getText().toString().trim())
//					.add("contactno", edt_contactno.getText().toString().trim())
//					.add("emailid", edt_email.getText().toString().trim())
//					.add("address", "")
//					.add("current_date", date1)
//					.build();
//
//			Response= ((MyApplication)getApplication()).post_server_call("http://smartbridges.co.in/Android/sendmailexample.php", formBody);
//			Log.i("tag", "Response:" + Response);
//
//
//
//			publishProgress("progress");
//			return "";
//		}
//
//		protected void onProgressUpdate(String... progress) {
//			try {
//
//				Intent openMainActivity1 = new Intent(Register.this,
//						Login.class);
//				startActivity(openMainActivity1);
//				finish();
//
//			} catch (Exception e) {
//
//				Log.i("mobilesutra.feetracker", "In JSONException "
//						+ e.getMessage().toString());
//				Toast.makeText(Register.this,
//						"ERROE" + e.getMessage().toString(), Toast.LENGTH_SHORT)
//						.show();
//				Log.d("7", "7");
//			}
//
//		}
//
//		protected void onPostExecute(Long result) {
//
//		}
//	}// HTTPRequest
//
//	private String convertInputStreamToString(InputStream inputStream) {
//		BufferedReader bufferedReader = new BufferedReader(
//				new InputStreamReader(inputStream));
//		String line = "";
//		String result = "";
//		try {
//			while ((line = bufferedReader.readLine()) != null)
//				result += line;
//
//			inputStream.close();
//		} catch (ConnectTimeoutException e) {
//			// Here Connection TimeOut excepion
//			Log.i("Server", "server slow");
//		} catch (IllegalStateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			Log.i("Server", "exception 1");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			Log.i("Server", "exception 2");
//		}
//
//		return result;
//	}

    public void export_database() {
        File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/ExportDatabase");
        File dir_app = new File(Environment.getExternalStorageDirectory().getPath() + "/ExportDatabase/" + this.getString(R.string.app_name));
        try {
            if (!dir.exists()) {
                if (dir.mkdir()) {
                    System.out.println("Directory created");
                } else {
                    System.out.println("Directory is not created");
                }
            }

            if (!dir_app.exists()) {
                if (dir_app.mkdir()) {
                    System.out.println("Directory created");
                } else {
                    System.out.println("Directory is not created");
                }
            }
        } catch (Exception e) {
            System.out.println("irectoryErrors" + e.getLocalizedMessage());
        }
        String[] tableNames = ((MyApplication) getApplication()).dbo.getTableNames();
        int tableLength = tableNames.length;
        for (int i = 0; i < tableLength; i++) {
            String file = Environment.getExternalStorageDirectory().getPath() + "/ExportDatabase/" + this.getString(R.string.app_name) + "/" + tableNames[i] + ".csv";
            try {
                File myFile = new File(file);
                FileOutputStream fOut = null;
                OutputStreamWriter myOutWriter = null;
                String[] columnNames = ((MyApplication) getApplication()).dbo.getTableColumnNames(tableNames[i]);
                if (myFile.exists() == true)
                    myFile.delete();

                myFile.createNewFile();
                fOut = new FileOutputStream(myFile, true);
                myOutWriter = new OutputStreamWriter(fOut);
                int columnCount = columnNames.length;
                String headerNames = "";
                for (int j = 0; j < columnCount; j++)
                    headerNames += columnNames[j] + ",";

                myOutWriter.write(headerNames + "\n");

                String data = ((MyApplication) getApplication()).dbo.getDataFromTable(tableNames[i]);
                myOutWriter.write(data + "\n");

                myOutWriter.flush();
                myOutWriter.close();
                fOut.close();
                myFile = null;

            } catch (IOException e) {
                System.out.println("FileErrors" + e.getLocalizedMessage());
            }
            MediaScannerConnection.scanFile(Register.this, new String[]{file}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("MyFileStorage", "Scanned " + path);
                        }


                    });//for

        }


    }

    // @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent i = new Intent(Register.this, Login.class);
        startActivity(i);
        finish();
    }

}