package com.mobilesutra.feetrackerclass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;


import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesutra.feetrackerclass.ErrorHandler.ExceptionHandler;
import com.mobilesutra.feetrackerclass.activities.ActivityDashboard;

import okhttp3.FormBody;
import okhttp3.RequestBody;


public class Login extends Activity {
	EditText txtUserName=null,txtPassword=null;
	TextView txt_user;
	String loginwithpounet, clid="", clname="";
	Button btnLogin=null,btnCancel=null,Register=null,btnForgotPass=null;
	Integer GetUserData;

	private ProgressDialog progressDialog;
	Integer CheckIfErrorPresent, IfErrorPresent;
	Database db;
	CheckBox check=null;
	String classid,city="",contactno="",valid_till="",emailid="";
	ProgressDialog dialog1;
	String Response = "",username="",password="";
	JSONObject json;
	Typeface roboto_light,roboto_reguler;
	Context context=this;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
		setContentView(R.layout.activity1);
//
		//export_database();


		db = new Database(getApplicationContext());
		Display d = ((WindowManager) getSystemService(this.WINDOW_SERVICE)).getDefaultDisplay();
		((MyApplication) getApplication()).DeviceHeight = d.getHeight();
		((MyApplication) getApplication()).DeviceWidth = d.getWidth();
		 txt_user = (TextView) findViewById(R.id.txt_user);




		roboto_light =  Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
		roboto_reguler =  Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");


		/*((MyApplication) getApplication()).set_session(
				"language", "mr");
		((MyApplication) getApplication())
				.changeLang(((MyApplication) getApplication())
						.get_session("language"));*/


		chech_for_upgrade();
		getIntentData();
		initComponentData();
		initComponentListeners();





	}

	public void getIntentData() {

	}

	public void initComponentData() {
		check = (CheckBox) findViewById(R.id.checkBox1);
		txtUserName = (EditText) this.findViewById(R.id.txtUname);
		//txtUserName.setText("neeruphadke@gmail.com");
		txtPassword = (EditText) this.findViewById(R.id.txtPwd);
		//txtPassword.setText("devraj");
		btnLogin = (Button) this.findViewById(R.id.btnLogin);
		btnCancel = (Button) this.findViewById(R.id.btnCancel);

		btnForgotPass = (Button) this.findViewById(R.id.btnForgotPass);
		Register = (Button) this.findViewById(R.id.btnRegister);
		TextView textname = (TextView) this.findViewById(R.id.text_name);
		TextView textpass = (TextView) this.findViewById(R.id.text_pass);

		textname.setTypeface(roboto_reguler);
		textpass.setTypeface(roboto_reguler);
		txtUserName.setTypeface(roboto_reguler);
		txtPassword.setTypeface(roboto_reguler);



		if((MyApplication.get_session(MyApplication.SESSION_USERTEXT)!= null)) {
			MyApplication.log("","You are printing notnull values");

			txtUserName.setText(MyApplication.get_session(MyApplication.SESSION_USERTEXT));
			txtPassword.setText(MyApplication.get_session(MyApplication.SESSION_PASSTEXT));
			txtUserName.setSelection(txtUserName.getText().length());
			txtPassword.setSelection(txtPassword.getText().length());
		}
		else
			MyApplication.log("","You are printing null values");
		btnLogin.setTypeface(roboto_reguler);
		btnCancel.setTypeface(roboto_reguler);
		Register.setTypeface(roboto_reguler);
		txt_user.setTypeface(roboto_reguler);
		btnForgotPass.setTypeface(roboto_reguler);

	}
	public void initComponentListeners() {



		btnForgotPass.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				username=txtUserName.getText().toString().trim();
				if(txtUserName.getText().toString().equals(""))
				{
					Toast.makeText(Login.this, getResources().getString(R.string.valid_username_forgot), Toast.LENGTH_SHORT).show();

				}else
				{
					HttpForgotPass pass=new HttpForgotPass();
					pass.execute();
				}

			}
		});


		check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {


				if (buttonView.isChecked()) {

					txtPassword
							.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());
					txtPassword.setSelection(txtPassword.getText().length());

				} else {
					txtPassword
							.setTransformationMethod(PasswordTransformationMethod
									.getInstance());
					int pos = txtPassword.getText().length();
					txtPassword.setSelection(pos);

				}
			}
		});




		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
				System.exit(0);
			}
		});


		btnLogin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {


				username=txtUserName.getText().toString().trim();
				password=txtPassword.getText().toString().trim();

				MyApplication.set_session(MyApplication.SESSION_USERTEXT,username);
				MyApplication.set_session(MyApplication.SESSION_PASSTEXT,password);
				Log.i("","Here user name and pas = "+username+" and "+password);

				boolean b=false;
				String uname = txtUserName.getText().toString();
				String pass = txtPassword.getText().toString();
				b = ((MyApplication)getApplication()).dbo.get_registration_details(uname, pass);
				Log.i("tag", "b="+b);

				if(b == true)
				{



					Toast.makeText(Login.this, getResources().getString(R.string.success_login),
							Toast.LENGTH_SHORT).show();
					Intent i = new Intent(Login.this,ActivityDashboard.class);
					startActivity(i);
					finish();
				}
				else
				{

					if ((txtUserName.getText().toString().trim().equals("")) || (txtPassword.getText().toString().trim().equals(""))) {
						Toast.makeText(Login.this, getResources().getString(R.string.valid_username_pass),
								Toast.LENGTH_SHORT).show();
					}else {
					if (((MyApplication) getApplication()).isNetworkAvailable() == false) {
						Toast.makeText(Login.this, getResources().getString(R.string.Internet_problem),
								Toast.LENGTH_SHORT).show();

					} else {

						new HTTPLogin(1).execute();
					}
				}
				}
//
			}
		});

		Register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Login.this,Register.class);
				startActivity(i);
				finish();
			}
		});




	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Close Application..!")
				.setMessage("Do You Want To Exit?")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton("Exit",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Yes button clicked, do something
								Toast.makeText(Login.this,
										"Exit button pressed",
										Toast.LENGTH_SHORT).show();
								finish();
								System.exit(0);
							}
						}).setNegativeButton("No", null) // Do nothing on no
				.show();
	}

	class HttpForgotPass extends AsyncTask<Void, String,String>
	{
		protected void onPreExecute()
		{
			progressDialog = ProgressDialog.show(Login.this, "",
					"wait.......");
			progressDialog.setCancelable(false);

		}
		protected String doInBackground(Void... params)
		{



			String url="http://mobilesutra.com/FeeTracker/Service/forgot_password";
			RequestBody formBody = new FormBody.Builder()
					.add("username",username)
					.build();
			Log.i("response","--------------------------"+formBody);
			Response=(MyApplication.post_server_call(url,formBody));
			//  Log.e("Socket", "formBodyt" + phototostring.trim());
			Log.i("response",""+Response);

			publishProgress("progress");
			return Response;






		}
		protected void onProgressUpdate(String... progress) {



			Log.d("tag10", "json--" + Response.length());
			if (Response.length() > 0) {
				try {


					JSONObject json = new JSONObject(Response);
					Log.e("Msg", json + "");

					if (json.getString("response_status").equals("true")) {

						progressDialog.dismiss();

						String msg1=json.getString("response_message");
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);
						alertDialog.setTitle("");
						alertDialog.setMessage(""+msg1);
						alertDialog.setPositiveButton("continue", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {



							}
						});


						alertDialog.show();
//						Toast.makeText(getApplicationContext(),
//								"Your user name and password is sent to your email address. Contact sbsupport@mobilesutra.com for any further help.", Toast.LENGTH_SHORT)
//								.show();


					} else {
//						Toast.makeText(getApplicationContext(), "Internet Connection problem",
//								Toast.LENGTH_SHORT).show();
						progressDialog.dismiss();
						progressDialog.dismiss();
						String msg1=json.getString("response_message");
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);
						alertDialog.setTitle("");
						alertDialog.setMessage(""+msg1);
						alertDialog.setPositiveButton("continue", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {



							}
						});


						alertDialog.show();
					}


				} catch (JSONException e) {
					Log.e("Msg", e.getMessage() + "");
				}
			} else {
				progressDialog.dismiss();
			}


		}

		protected void onPostExecute(Long result) {

		}

	}// HTTP

	@Override
	public void onResume() {
		super.onResume();
		Log.e("Msg", "resume");
	//	chech_for_upgrade();
	}

	class HTTPLogin extends AsyncTask<Void, String, String> {

		String Response = "";

		int status_code = 0;
		int getstate = 0;

		public HTTPLogin(int getstate) {
			this.getstate=getstate;

		}

		protected void onPreExecute() {
			Response = "";
			dialog1 = ProgressDialog.show(Login.this,
					"", "Validating user credentials...", true,
					false);
		}

		protected String doInBackground(Void... params) {


			RequestBody formBody = new FormBody.Builder()
					.add("username", username)
					.add("password", password)

					.build();

			Response= ((MyApplication)getApplication()).post_server_call1("http://mobilesutra.com/FeeTracker/Service/authentication", formBody);
			Log.i("tag", "Response:" + Response);

			publishProgress("progress");
			return "";
		}

		protected void onProgressUpdate(String... progress) {



			try {
					int resp=Response.length();


							Log.i("tag", "Response:" + Response);


					if(!Response.equals("-0")) {
						try {
							Log.i("tag", "try");
							JSONObject json = new JSONObject(Response);
							Log.i("tag", "Response:" + json);
							boolean flag_resp=json.getBoolean("response_status");
							if (flag_resp) {
								Log.i("tag", "if");
								dialog1.dismiss();

								String class_details = json.getString("class_details");
								Log.i("tag", "response_status:" + class_details);
								JSONObject jClass = new JSONObject(class_details);

								contactno=jClass.getString("ContactNo");
								clname=jClass.getString("ClassName");
								city=jClass.getString("City");
								emailid=jClass.getString("EmailID");
								valid_till=jClass.getString("Valid_Till");
								classid=jClass.getString("Auto_Id");
								Log.d("tag","records"+contactno);



								MyApplication.set_session("password", password);
								Log.d("Login_Activity","password"+password);



								MyApplication.set_session("contactno",contactno);
								MyApplication.set_session("emailid",emailid);
								MyApplication.set_session("city",city);
								MyApplication.set_session("valid_till",valid_till);
								MyApplication.set_session("classname",clname);
								MyApplication.set_session("classid",classid);


//							Log.i("tag", "uname is" + uname);
//							Log.i("tag", "pass is" + pass);
//							Log.i("tag", "validfrom is" + validfrom);
//							Log.i("tag", "validtill is" + validtill);
//							Log.i("tag", "classid is" + classid);
//
//							add_local();


								Toast.makeText(Login.this,getResources().getString(R.string.success_login),
										Toast.LENGTH_SHORT).show();
								Intent openMainActivity1 = new Intent(
										Login.this, ActivityDashboard.class);
								startActivity(openMainActivity1);
								finish();
							}
							else {

								dialog1.dismiss();
								Log.i("tag", "else");

								String msg1=json.getString("response_message");
								Log.i("tag", "teju:" + msg1);
								AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);
								alertDialog.setTitle("");
								alertDialog.setMessage("" + msg1);
								alertDialog.setPositiveButton("continue", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {


									}
								});


								alertDialog.show();

//								String response_message=json.getString("response_message");
//								Toast.makeText(getApplicationContext(), ""+response_message,
//										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {

							System.out.println("" + e);
						}
					}
					else {
						dialog1.dismiss();
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.Internet_problem),
								Toast.LENGTH_SHORT).show();
					}



			} catch (Exception e) {

				Log.i("mobilesutra.feetracker", "In JSONException "
						+ e.getMessage().toString());
//				Toast.makeText(Login.this,
//						"ERROE" + e.getMessage().toString(), Toast.LENGTH_SHORT)
//						.show();
				Log.d("7", "7");
			}

		}

		protected void onPostExecute(Long result) {


		}
	}// HTTPRequest

	private String convertInputStreamToString(InputStream inputStream) {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		try {
			while ((line = bufferedReader.readLine()) != null)
				result += line;

			inputStream.close();
		} catch (ConnectTimeoutException e) {
			// Here Connection TimeOut excepion
			Log.i("Server", "server slow");
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("Server", "exception 1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("Server", "exception 2");
		}

		return result;
	}

	public void StartProgressDailog() {
		progressDialog = ProgressDialog.show(Login.this, "",
				"Fetching Data.......");

		new Thread() {

			public void run() {

				try {

					if (((MyApplication) getApplication()).isNetworkAvailable() == true) {
						String UserName = txtUserName.getText().toString()
								.trim().replace(" ", "%20");
						String Password = txtPassword.getText().toString()
								.trim().replace(" ", "%20");
						((MyApplication) getApplication()).AdminUserName = UserName;
						((MyApplication) getApplication()).AdminPassword = Password;

						/*GetUserData = ((MyApplication) getApplication())
								.getClassData();*/
					} else {
						if (loginwithpounet == "Yes") {
							((MyApplication) getApplication()).ClassID = clid;
							((MyApplication) getApplication()).ClassName = clname;
							GetUserData = 1;
						} else {
							GetUserData = 6;
						}
					}

				} catch (Exception e) {

					Log.e("tag", e.getMessage());

				}

				// dismiss the progress dialog

				progressDialog.dismiss();
				if (GetUserData == 11) {
//					 Toast.makeText(Login.this,
//					 "UserName OR Password Is Incorrect...",Toast.LENGTH_LONG).show();
					AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
					builder.setMessage("UserName OR Password Is Incorrect...")
					       .setCancelable(false)
					       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   Intent openMainActivity2 = new Intent(Login.this,
											Login.class);
									openMainActivity2.putExtra("iferrorpresent", "2");
									startActivity(openMainActivity2);
									finish();
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();

				}
				if (GetUserData == 1 || GetUserData == 3) {
					((MyApplication) getApplication()).senddata = "Yes";
//					Intent openMainActivity1 = new Intent(Login.this,FirstPage.class);
//					openMainActivity1.putExtra("display", "0");
//					startActivity(openMainActivity1);
//					finish();

				}
				if (GetUserData == 2 || GetUserData == 4 || GetUserData == 5) {
					// Toast.makeText(Login.this,
					// "UserName OR Password Is Incorrect...",Toast.LENGTH_LONG).show();
					Intent openMainActivity2 = new Intent(Login.this,
							Login.class);
					openMainActivity2.putExtra("iferrorpresent", "1");
					startActivity(openMainActivity2);
					finish();

				}
				if (GetUserData == 6) {
					// Toast.makeText(Login.this,
					// "Inter net no avalable",Toast.LENGTH_LONG).show();
					Intent openMainActivity2 = new Intent(Login.this,
							Login.class);
					openMainActivity2.putExtra("iferrorpresent", "6");
					startActivity(openMainActivity2);
					finish();

				}

			}

		}.start();

	}


	public void export_database()
	{
		File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/ExportDatabase");
		File dir_app = new File(Environment.getExternalStorageDirectory().getPath() + "/ExportDatabase/"+this.getString(R.string.app_name));
		try{
			if(!dir.exists()) {
				if(dir.mkdir()) {
					System.out.println("Directory created");
				} else {
					System.out.println("Directory is not created");
				}
			}

			if(!dir_app.exists()) {
				if(dir_app.mkdir()) {
					System.out.println("Directory created");
				} else {
					System.out.println("Directory is not created");
				}
			}
		}catch(Exception e){
			System.out.println("DirectoryErrors"+e.getLocalizedMessage());
		}
		String[] tableNames = ((MyApplication)getApplication()).dbo.getTableNames();
		int tableLength = tableNames.length;
		for(int i = 0; i < tableLength; i++)
		{
			String file = Environment.getExternalStorageDirectory().getPath() + "/ExportDatabase/"+this.getString(R.string.app_name)+"/"+tableNames[i]+".csv";
			try {
				File myFile = new File(file);
				FileOutputStream fOut = null;
				OutputStreamWriter myOutWriter = null;
				String[] columnNames = ((MyApplication)getApplication()).dbo.getTableColumnNames(tableNames[i]);
				if (myFile.exists() == true)
					myFile.delete();

				myFile.createNewFile();
				fOut = new FileOutputStream(myFile, true);
				myOutWriter = new OutputStreamWriter(fOut);
				int columnCount = columnNames.length;
				String headerNames = "";
				for(int j = 0;j < columnCount;j++)
					headerNames +=columnNames[j]+",";

				myOutWriter.write(headerNames+"\n");

				String data = ((MyApplication)getApplication()).dbo.getDataFromTable(tableNames[i]);
				myOutWriter.write(data+"\n");

				myOutWriter.flush();
				myOutWriter.close();
				fOut.close();
				myFile = null;

			} catch (IOException e) {
				System.out.println("FileErrors"+e.getLocalizedMessage());
			}
			MediaScannerConnection.scanFile(Login.this, new String[]{file}, null,
					new MediaScannerConnection.OnScanCompletedListener() {
						@Override
						public void onScanCompleted(String path, Uri uri) {
							Log.i("MyFileStorage", "Scanned " + path);
						}


					});//for

		}


	}
    // Comment by bhavesh
	void chech_for_upgrade() {
		if (MyApplication.get_Longsession(MyApplication.SESSION_VERSION_DATE_CHECK) == 0) {
			new AsyncVersion().execute();
			Log.e("Msg", "resumeif");
		} else

		{
			long session_time = MyApplication.get_Longsession(MyApplication.SESSION_VERSION_DATE_CHECK);
			Log.e("Msg", "resumeelse"+session_time);
			Date date = new Date();
			long date_time = date.getTime();
			long diff = date_time - session_time;
			MyApplication.log("TimeSession->" + session_time);
			MyApplication.log("TimeCurrent->" + date_time);
			MyApplication.log("TimeDiff->" + diff);
			if (diff > 21600000)//6 hours in milliseconds
			{
				new AsyncVersion().execute();
			}
		}
	}


	// Comment by bhavesh
	public class AsyncVersion extends AsyncTask<String, Void, String> {

		protected void onPreExecute() {
			MyApplication.log("AsyncVersion-onPreExecute");
			dialog1 = ProgressDialog.show(Login.this,
					"", "Validating user credentials...", true,
					false);
		}

		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			try {

				response = MyApplication.get_play_store_version();
				return response;
			} catch (Exception e) {
				return "-2";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != "-0") {

				int server_version = Integer.parseInt(result);
				int app_version = 0;
				PackageManager manager = context.getPackageManager();
				PackageInfo info = null;
				try {
					info = manager.getPackageInfo(context.getPackageName(), 0);
					app_version = info.versionCode;


				} catch (PackageManager.NameNotFoundException e) {
					MyApplication.log("VersionE->" + e);
				}
				MyApplication.log("AppVersion->" + app_version);
				MyApplication.log("ServerVersion->" + server_version);
				if (MyApplication.get_Longsession(MyApplication.SESSION_VERSION_DATE_CHECK) == 0) {
					MyApplication.log("AsyncOnPostExecuteIf");
					if (server_version > app_version) {
						//current application is not updated
						show_upgrade_dialog();
						dialog1.dismiss();
					} else {
						//current application is updated
						Date date = new Date();
						long date_time = date.getTime();
					//	Toast.makeText(context, "New Version", Toast.LENGTH_SHORT).show();
						MyApplication.set_session(MyApplication.SESSION_VERSION_DATE_CHECK, date_time + "");
						dialog1.dismiss();
					}

				} else {
					MyApplication.log("AsyncOnPostExecuteElse");
					//Toast.makeText(context, "In else", Toast.LENGTH_SHORT).show();
					if (server_version > app_version) {
						//current application is not updated
						show_upgrade_dialog();
						dialog1.dismiss();
					} else {
						//current application is updated
						Date date = new Date();
						long date_time = date.getTime();
						//Toast.makeText(Login.this, "New Version", Toast.LENGTH_SHORT).show();
						MyApplication.set_session(MyApplication.SESSION_VERSION_DATE_CHECK, date_time + "");
						dialog1.dismiss();
					}
				}
			}
			else
			{
				dialog1.dismiss();
			}
		}
	}

	// comment by bhavesh
	void show_upgrade_dialog() {
		final Dialog dialog1 = new Dialog(Login.this);
		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog1.setContentView(R.layout.layout_upgrade);

		dialog1.show();
		dialog1.setCancelable(false);
		dialog1.setCanceledOnTouchOutside(false);

		final Button btn_upgrade = (Button) dialog1.findViewById(R.id.btn_upgrade);
		btn_upgrade.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.mobilesutra.feetrackerclass");
				Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
				// To count with Play market backstack, After pressing back button,
				// to taken back to our application, we need to add following flags to intent.
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
				try {
					MyApplication.log("1");
					startActivity(goToMarket);
					MyApplication.log("2");
				} catch (ActivityNotFoundException e) {
					MyApplication.log("3");
					startActivity(new Intent(Intent.ACTION_VIEW,
							Uri.parse("https://play.google.com/store/apps/details?id=com.mobilesutra.feetrackerclass")));
				}
				dialog1.dismiss();
			}
		});
	}


//	public void readData() throws JSONException {
//
//		JSONArray jsonArrayData=new JSONArray();
//		JSONObject jsonObjectMain=new JSONObject();
//		JSONObject jsonObjectTable=new JSONObject();
//		String[] tableNames = ((MyApplication)getApplication()).dbo.getTableNames();
//		int tableLength = tableNames.length;
//		Log.i("Database_table", String.valueOf(tableLength));
//		for(int i = 0; i < tableLength; i++)
//		{
//
//
//			jsonObjectTable= ((MyApplication)getApplication()).dbo.getDataFromBackup(tableNames[i]);
//			jsonArrayData.put(jsonObjectTable);
//
//		}
//
//		jsonObjectMain.put("Data",jsonArrayData);
//		Log.i("Database_table", "Data" + jsonObjectMain);
//	}


}
