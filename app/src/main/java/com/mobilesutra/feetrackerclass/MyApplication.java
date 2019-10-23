package com.mobilesutra.feetrackerclass;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.mobilesutra.feetrackerclass.config.AESAlgorithm;
import com.mobilesutra.feetrackerclass.config.Base64;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PowerManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyApplication extends Application {

    public static Database dbo;
    TableRow row, row1;
    String Network;
    int Messagecount;
    String finalDate = "", expired;
    String UserName, Password, AdminUserName, AdminPassword;
    String EventDate, ShortEvent, LongEvent, StudentTotalSubject,
            multipleselectedbatches;
    String StudentID, StudentName, Standard, ClassID, ClassName, SubjectID,
            SubjectName, senddata;
    public static final String SESSION_VERSION_DATE_CHECK = "session_version_date_check";
    String Response1 = "";
    Integer year, month, day;
    String key, getresultdata, startdate, enddate;
    StringBuilder sbforenddate, sbforstartdate;
    public static String str3, selectedsubject, Date, selecteddate;
    String[] separated1, separated2, separated3, separated4, separated5,
            separated6, separated7, separated8, separated9;
    String[] separated10, separated11, separated12, separated13, separated14,
            separated15, separated16, separated17, separated18, separated19,
            separated20;
    int DeviceHeight, DeviceWidth;
    String CurDate;
    String GetUserData;
    int resultupdate = 0, feeupdate = 0, examupdate = 0, noticeupdate = 0,
            feesubjectupdate = 0, attendanceupdate = 0, update = 0;
    String selectedstd, selectedsubjectid, selectedbatch = "", selectedbranch;
    static final String SERVER_URL = "http://smartbridges.co.in/Android/register.php";
    public static final String version = "http://mobilesutra.com/FeeTracker/version_smart.php";
    public static final String url_send_sms = "http://mobilesutra.com/FeeTracker/Service/send_sms_to_student";

    private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();

    // Google project id
    static final String SENDER_ID = "12394462408";

    public static SharedPreferences sharedPref;
    public static SharedPreferences.Editor editor;
    Context context = this;
    String PREFS_NAME = "jdviudsfb4r4327_Sdfj";
    public static String SessionKey = "j5aD9uweHEAncbhd";// Must have 16
    // character session
    // key
    public static AESAlgorithm aes;

    public static String COL_1 = "col_1", COL_2 = "col_2";


    public static final String session_branch = "session_branch", session_stand = "session_stand", session_batch = "session_batch", session_subj = "session_subj";


    public static final String Fee_session_branch = "Fee_session_branch", Fee_session_stand = "Fee_session_stand", Fee_session_batch = "Fee_session_batch", Fee_session_subj = "Fee_session_subj";
    public static final String send_session_branch = "send_session_branch", send_session_stand = "send_session_stand", send_session_batch = "send_session_batch", send_session_subj = "send_session_subj";

    public static final String add_session_branch = "add_session_branch", add_session_stand = "add_session_stand", add_session_batch = "add_session_batch", add_session_subj = "add_session_subj";

    public static final String fee_session_branch = "fee_session_branch", fee_session_stand = "fee_session_stand", fee_session_batch = "fee_session_batch", fee_session_subj = "fee_session_subj";

    public static final String GCM_SENDER_ID = "810503395913";
    public static final String PREFS_PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    public static final String SESSION_USERTEXT = "sessionusertext";
    public static final String SESSION_PASSTEXT = "sessionpasstext";


    GoogleCloudMessaging gcm;
    public String regid = "";

    private Locale myLocale;

    /**
     * Tag used on log messages.
     */
    static final String TAG = "FeeTracker",LOG_TAG = "FeeTracker";

    static final String DISPLAY_MESSAGE_ACTION = "com.example.athavaleclasses.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";
    ArrayList<HashMap<String, String>> data;
    public static String Key_product_info = "produxt_info", SESSION_CURRENT_TAB = "current_tab";

    /**
     * Notifies UI to display a message.
     * <p/>
     * This method is defined in the common helper because it's used both by the
     * UI and the background service.
     **/


	/*@param context
    *            application's context.
			* @param message
	*            message to be displayed.*/


    public static String[] PERMISSION_CAMERA_READ_WRITE={Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public final static int PERMISSION_REQUEST_CODE_CAMERA_READ_WRITE = 1500;


    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        aes = new AESAlgorithm();

        dbo = new Database(context);
        data = new ArrayList<HashMap<String, String>>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        CurDate = dateFormat.format(date);


       /* HashMap config = new HashMap();
        Core.init(Support.getInstance());
        Core.install(this,
                "f8b9b6654074021735e4eda1eb0c3234",
                "mobilesutra.helpshift.com",
                "mobilesutra_platform_20161122062227772-ebfce3f4bb5e945",
                config);*/


        /*
		 * data=getUserData();
		 * //Toast.makeText(MainActivity.this,data.toString(),
		 * Toast.LENGTH_LONG).show(); //String StudentID
		 * =data.get(0).get("StudentID").toString(); //String StudentName
		 * =data.get(0).get("StudentFirstName").toString();
		 * //Toast.makeText(MainActivity
		 * .this,StudentID.toString()+" "+StudentName.toString(),
		 * Toast.LENGTH_LONG).show(); int records=data.size();
		 * //Toast.makeText(MainActivity.this,""+records,
		 * Toast.LENGTH_LONG).show(); StudentID
		 * =data.get(0).get("StudentID").toString(); StudentName
		 * =data.get(0).get("StudentFirstName").toString(); Standard
		 * =data.get(0).get("Standard").toString(); ClassName
		 * =data.get(0).get("ClassName").toString(); for(int i=0;i<records;i++)
		 * { SubjectID =data.get(i).get("SubjectID").toString();
		 * //Toast.makeText(MainActivity.this,SubjectID.toString(),
		 * Toast.LENGTH_LONG).show(); dbo.insertstudentinfotable(StudentName,
		 * ClassName, StudentID, Standard, SubjectID); }
		 */

    }

    // Comment by bhavesh
    public static String get_play_store_version() {

        String url = MyApplication.version;
        RequestBody formBody = new FormBody.Builder()
                .add("id", "1")
                .build();

        String Response1 = (MyApplication.post_server_call(url, formBody));

        Log.i("response", "--------------------------" + Response1);
        return Response1;


    }

    public static void log(String str) {
        if (str.length() > 4000) {
            Log.i(LOG_TAG, str.substring(0, 4000));
            //log(TAG, str.substring(4000));
        } else
            Log.i(LOG_TAG, str);
    }
    public static void log(String TAG,String str) {
        if (str.length() > 4000) {
            Log.i(LOG_TAG, TAG+"-"+str.substring(0, 4000));
            log(TAG, str.substring(4000));
        } else
            Log.i(LOG_TAG,TAG +"-"+ str);
    }

    public static long get_Longsession(String key) {
        String temp_key = aes.Encrypt(key);
        if (sharedPref.contains(temp_key)) {
            String str = aes.Decrypt(sharedPref.getString(temp_key, ""));
            return Long.parseLong(str);
        } else
            return 0;
    }

    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

    public static void set_session(String key, String value) {
        Log.i("SetSession", "Key=" + key + "__value=" + value);
        String temp_key = aes.Encrypt(key);
        String temp_value = aes.Encrypt(value);
        MyApplication.editor.putString(temp_key, temp_value);
        MyApplication.editor.commit();

    }

    public static String get_session(String key) {
        String temp_key = aes.Encrypt(key);
        if (sharedPref.contains(temp_key)) {
            return aes.Decrypt(sharedPref.getString(temp_key, ""));
        } else
            return "";
    }

    public static int get_Intsession(String key) {
        String temp_key = aes.Encrypt(key);
        if (sharedPref.contains(temp_key)) {
            String str = aes.Decrypt(sharedPref.getString(temp_key, ""));
            return Integer.parseInt(str);
        } else
            return 0;
    }
	
	/*
	 * GCM FUNCTIONS
	 */

    public void getRegistrationGCMID() {
        if (checkPlayServices()) {
            // Retrieve registration id from local storage
            regid = getRegistrationId(context);

            if (TextUtils.isEmpty(regid)) {
                Log.i("Empty", regid);
                registerInBackground();

            } else {
                Log.i("Not empty", regid);

            }
            Log.i("Store in database", regid);
        } else {
            Log.i("1", "No valid Google Play Services APK found.");
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                // GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                // PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("2", "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PREFS_PROPERTY_REG_ID, "");
        if (registrationId == null || registrationId.equals("")) {
            Log.i("3", "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
                Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i("4", "App version changed.");
            return "";
        }
        return registrationId;
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private SharedPreferences getGcmPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences,
        // but how you store the regID in your app is up to you.
        return getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(GCM_SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;
                    // You should send the registration ID to your server over
                    // HTTP, so it can use GCM/HTTP or CCS to send messages to
                    // your app.
                    // sendRegistrationIdToBackend();
                    // postData(regid);
                    // For this demo: we use upstream GCM messages to send the
                    // registration ID to the 3rd party server

                    // Persist the regID - no need to register again.
                    storeRegistrationId(context, regid);
                } catch (Exception ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            private void storeRegistrationId(Context context, String regId) {
                final SharedPreferences prefs = getGcmPreferences(context);
                int appVersion = getAppVersion(context);
                Log.i("SavingregIdonappver ", appVersion + "");
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(PREFS_PROPERTY_REG_ID, regId);
                editor.putInt(PROPERTY_APP_VERSION, appVersion);
                editor.commit();
                Log.i("regId:", regId);

                // store it in database table notification

            }

            @Override
            protected void onPostExecute(String msg) {
                // Log.e("","Device Registered");
                // ((EditText) findViewById(R.id.txtPin)).setText(regid);
            }
        }.execute(null, null, null);
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable());
    }


    public static String post_server_call1(String url, RequestBody formBody) {

        long REQ_TIMEOUT = 1800;
		Log.i("post_server_call", "Url:" + url);
		try {
			OkHttpClient client = new OkHttpClient();
            client.newBuilder().connectTimeout(REQ_TIMEOUT, TimeUnit.SECONDS);
            client.newBuilder().readTimeout(REQ_TIMEOUT, TimeUnit.SECONDS);
            client.newBuilder().writeTimeout(REQ_TIMEOUT, TimeUnit.SECONDS);
            /*client.setConnectTimeout(1,TimeUnit.MINUTES);
            client.setReadTimeout(1,TimeUnit.MINUTES);*/
			Request request = new Request.Builder()
					.url(url)
					.post(formBody)
					.build();

			Response response = client.newCall(request).execute();
			Log.i("post_server_call", "ResponseStatus:Exception" + response.isSuccessful());
			return response.body().string();

		} catch (Exception e) {
			Log.i("post_server_call","ResponseStatus:Exception" + e);
			return "-0";
		}


        /*MyApplication.log("In post_server_call");
        MyApplication.log("URL->" + url);

        long REQ_TIMEOUT = 60;
        long READ_TIMEOUT = 60;

        try {

            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(REQ_TIMEOUT, TimeUnit.SECONDS);
            client.setReadTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            MyApplication.log("ResponseStatus:" + response.isSuccessful());
            MyApplication.log("ResponseCode:" + response.code());
            String str_response_message = response.body().string();
            MyApplication.log("ResponseMessage:" + str_response_message);
            return response.body().string();

        } catch (SocketTimeoutException e) {
            MyApplication.log("STE1->" + e + "");
            MyApplication.log("STE2->" + e.getMessage());
            MyApplication.log("STE3->" + e.getLocalizedMessage());
            return "-0";

        } catch (Exception e) {
            MyApplication.log("E1->" + e + "");
            MyApplication.log("E2->" + e.getMessage());
            MyApplication.log("E3->" + e.getLocalizedMessage());
            return "-0";
        }*/


    }


    public static String post_server_call(String url, RequestBody formBody) {

        long REQ_TIMEOUT = 1800;

       log("post_server_call", "Url:" + url);
        try {
            OkHttpClient client = new OkHttpClient();
            client.newBuilder().connectTimeout(REQ_TIMEOUT, TimeUnit.SECONDS);
            client.newBuilder().readTimeout(REQ_TIMEOUT, TimeUnit.SECONDS);
            client.newBuilder().writeTimeout(REQ_TIMEOUT, TimeUnit.SECONDS);
           /* client.setConnectTimeout(3,TimeUnit.MINUTES);
            client.setReadTimeout(3,TimeUnit.MINUTES);*/
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();
            log("post_server_call", "ResponseStatus:Status" + response.isSuccessful());
            return response.body().string();

        } catch (Exception e) {
            log(LOG_TAG,"ResponseStatus:Exception" + e);
            return "-0";
        }


        /*MyApplication.log("In post_server_call");
        MyApplication.log("URL->" + url);

        long REQ_TIMEOUT = 60;
        long READ_TIMEOUT = 60;

        try {

            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(REQ_TIMEOUT, TimeUnit.SECONDS);
            client.setReadTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            MyApplication.log("ResponseStatus:" + response.isSuccessful());
            MyApplication.log("ResponseCode:" + response.code());
            String str_response_message = response.body().string();
            MyApplication.log("ResponseMessage:" + str_response_message);
            return response.body().string();

        } catch (SocketTimeoutException e) {
            MyApplication.log("STE1->" + e + "");
            MyApplication.log("STE2->" + e.getMessage());
            MyApplication.log("STE3->" + e.getLocalizedMessage());
            return "-0";

        } catch (Exception e) {
            MyApplication.log("E1->" + e + "");
            MyApplication.log("E2->" + e.getMessage());
            MyApplication.log("E3->" + e.getLocalizedMessage());
            return "-0";
        }*/


    }


    public static String get_server_call(String url) {
        Log.i("get_server_call", "Url:" + url);
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            return "-0";
        }

    }


    public Integer getUserData() {

        int ret;
        String f = dbo.checkLogin(UserName, Password);
        // String f="1";

        if (f == "1") {
            String st = dbo.getStudentID(UserName, Password);

            Log.i("mobilesutra.feetracker", "Value of student id is " + st);

            // String st=StudentID;
            if (st.equalsIgnoreCase("n")) {
                Log.d("return 5", "5");
                ret = 5;
            } else {
                String re = dbo.getstudentinfo(st);

                Log.i("mobilesutra.feetracker",
                        "Value of re from get student info is " + re);

                separated19 = re.split("\\?");
                StudentID = separated19[2].toString();
                Standard = separated19[3].toString();
                StudentName = separated19[0].toString();
                ClassName = separated19[1].toString();

                Log.d("stinfo", "id-" + StudentID + ",std-" + Standard
                        + ",name-" + StudentName + ",classname-" + ClassName);
                Log.d("return 1", "1");
                ret = 1;
            }
        } else {
            Log.d("return 2", "2");
            ret = 2;

        }
        return ret;
    }

    public String changedateformat(String dateTime) {
        try {
            Log.d("tag", "tejas-" + dateTime);
            // String mytime="Jan 17, 2012";
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd/MM/yyyy");
            Date myDate = null;

            myDate = dateFormat.parse(dateTime);
            Log.d("tag", "tejas-" + myDate);


            SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy");

            finalDate = timeFormat.format(myDate);
            Log.d("tag", "tejas-" + finalDate);
        } catch (Exception e) {
            Log.d("tag", "tejas-" + e.getMessage());
        }
        return finalDate;
    }
	/*public void getall_userdata() {
		boolean flag1 = isNetworkAvailable();
		// dbo.deleteAllData();

		if (flag1 == true) {

			// final String URL =
			// "http://smartbridges.co.in/Android/Getdata.php?MobileID=1&UserName=&Password=ANAN60833&LastUpdate=2001-01-01";
			final String URL = "http://smartbridges.co.in/Android/Teacher2_Getdata.php?MobileID=1&UserName="
					+ UserName
					+ "&Password="
					+ Password
					+ "&ClassID="
					+ ClassID + "&LastUpdate=2001-01-01";
			// XML node keys
			final String KEY = "User"; // parent node
			final String ClassName1 = "ClassName";
			final String SubjectID2 = "SubID";
			final String Standard2 = "Std";
			final String StudentName2 = "StudName";
			final String StudentID2 = "StudID";
			final String SubjectName2 = "SubName";

			ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

			XMLParser parser = new XMLParser();

			String xml = parser.getXmlFromUrl(URL); // getting XML
			Log.d("getuserdata", URL.toString() + "---" + xml);
			if (xml.equals("n")) {
				// return 11;
			}
			Document doc = parser.getDomElement(xml); // getting DOM element

			NodeList n2 = doc.getElementsByTagName(KEY);
			for (int i = 0; i < n2.getLength(); i++) {

				String subname, studname, studid, subid, stdd, clname;
				Element e1 = (Element) n2.item(i);

				subname = parser.getValue(e1, SubjectName2);
				subid = parser.getValue(e1, SubjectID2);
				studname = parser.getValue(e1, StudentName2);
				studid = parser.getValue(e1, StudentID2);
				stdd = parser.getValue(e1, Standard2);
				clname = parser.getValue(e1, ClassName1);
				if (studid != "") {
					dbo.insertstudentinfotable(studname, clname, studid, stdd,
							subid);
					dbo.insertSubjectTable(subname, subid);
				}

			}

		}

	}

	public int getResultDataFromServer() {

		final String KEY = "Exam";
		final String ExamID = "ExamID";
		final String ExamName = "ExamName";
		final String ExamDate = "ExamDate";
		final String TotalMarks = "TotalMarks";
		final String Duration = "Duration";
		final String Marks = "Marks";
		final String Topper = "Topper";
		final String Average = "Average";
		final String SubjectID = "SubjectID";
		String ExamID1, ExamName1, ExamDate1, TotalMarks1, Duration1, Marks1, Topper1, Average1, SubjectID1;
		// final String URL =
		// "http://bhuumisoluutions.com/android/GetResult.php";

		ArrayList<String> subjectList = new ArrayList<String>();
		subjectList = dbo.getAllsubjectName(StudentID);
		Iterator<String> it = subjectList.iterator();
		String sub, subId;
		while (it.hasNext()) {
			sub = it.next();
			subId = dbo.getsubjectid(sub);
			final String URL = "http://smartbridges.co.in/Android/GetResult.php?MobileID=1&ClassID="
					+ ClassID
					+ "&Standard="
					+ Standard
					+ "&StudentID="
					+ StudentID
					+ "&SubjectID="
					+ subId
					+ "&LastUpdate=2001-01-01";
			ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

			XMLParser parser = new XMLParser();

			String xml = parser.getXmlFromUrl(URL); // getting XML
			if (xml.equals("n")) {
				return 11;
			}
			Document doc = parser.getDomElement(xml); // getting DOM element

			NodeList nl = doc.getElementsByTagName(KEY);
			for (int i = 0; i < nl.getLength(); i++) {
				// creating new HashMap
				// HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				// String str=parser.getValue(e,KEY_ID);
				// Toast.makeText(AndroidXMLParsingActivity.this,str.toString(),Toast.LENGTH_SHORT).show();
				// adding each child node to HashMap key => value

				ExamID1 = parser.getValue(e, ExamID);

				ExamName1 = parser.getValue(e, ExamName);
				ExamDate1 = parser.getValue(e, ExamDate);
				TotalMarks1 = parser.getValue(e, TotalMarks);
				Duration1 = parser.getValue(e, Duration);
				Marks1 = parser.getValue(e, Marks);
				Topper1 = parser.getValue(e, Topper);
				ExamID1 = parser.getValue(e, ExamID);
				Average1 = parser.getValue(e, Average);
				SubjectID1 = parser.getValue(e, SubjectID);
				if (ExamID1 != "")
					dbo.InserResultTable(StudentID, ExamDate1, Duration1,
							TotalMarks1, ExamName1, ExamID1, Marks1, Topper1,
							Average1, SubjectID1, Standard, CurDate);

			}
		}
		return 1;

	}*/

    public StringBuilder setEndCurrentDateOnView() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // dd/mm/yr
        sbforenddate = new StringBuilder().append(year).append("-")
                .append(month + 1).append("-").append(day).append(" ");

        return sbforenddate; // TODO Auto-generated method stub

    }

    public void CreateNewRow() {
        // TODO Auto-generated method stub
        row = new TableRow(this);
        row.setBackgroundColor(Color.BLACK);

    }

    public void CreateNewRow1() {
        // TODO Auto-generated method stub
        row1 = new TableRow(this);
        row1.setBackgroundColor(Color.BLACK);

    }

    public TextView CreateNewTextView(TextView textview) {
        textview = new TextView(this);
        textview.setPadding(5, 5, 5, 5);
        textview.setGravity(0x11);

        // textview.layout(5, 5, 5, 5);
        return textview;
    }

	/*public int getAttendanceDataFromServer() {
		ArrayList<String> subjectList = new ArrayList<String>();
		subjectList = dbo.getAllsubjectName(StudentID);
		Iterator<String> it = subjectList.iterator();
		String sub, subId;
		while (it.hasNext()) {
			sub = it.next();
			subId = dbo.getsubjectid(sub);
			final String URL = "http://smartbridges.co.in/Android/GetAttendanceData.php?MobileID=1&ClassID="
					+ ClassID
					+ "&LastUpdate=2012-01-01&Standard="
					+ Standard
					+ "&StudentID=" + StudentID + "&SubjectID=" + subId + "";
			// final String URL =
			// "http://bhuumisoluutions.com/android/GetA.php";
			// XML node keys
			final String KEY = "Lecture"; // parent node
			final String MonthName = "MonthName";
			final String NumberOfDays = "NumberOfDays";
			final String Year = "Year";
			// final String Standard = "Standard";
			// final String SubjectID="SubjectID";

			// ArrayList<HashMap<String, String>> menuItems = new
			// ArrayList<HashMap<String, String>>();

			XMLParser parser = new XMLParser();

			String xml = parser.getXmlFromUrl(URL); // getting XML
			if (xml.equals("n")) {
				return 11;
			}
			Document doc = parser.getDomElement(xml); // getting DOM element

			NodeList nl = doc.getElementsByTagName(KEY);
			String MonthName1, NumberOfDays1, Year1;
			for (int i = 0; i < nl.getLength(); i++) {
				// creating new HashMap
				// HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				// String str=parser.getValue(e,KEY_ID);
				// Toast.makeText(AndroidXMLParsingActivity.this,str.toString(),Toast.LENGTH_SHORT).show();
				// adding each child node to HashMap key => value
				MonthName1 = parser.getValue(e, MonthName);
				NumberOfDays1 = parser.getValue(e, NumberOfDays);
				Year1 = parser.getValue(e, Year);
				if (MonthName1 != "") {
					dbo.insertMonthlyLectureTable(MonthName1, NumberOfDays1,
							Year1, Standard, subId, this.CurDate.toString());
				}
				// map.put(SubjectID, parser.getValue(e, SubjectID));
				// adding HashList to ArrayList
				// menuItems.add(map);
			}
			final String KEY1 = "AbsentData";
			NodeList n2 = doc.getElementsByTagName(KEY1);
			String Date1;
			final String Dated = "Date";
			for (int i = 0; i < n2.getLength(); i++) {
				// HashMap<String, String> map1 = new HashMap<String, String>();
				Element e1 = (Element) n2.item(i);
				Date1 = parser.getValue(e1, Dated);
				if (Date1 != "") {
					dbo.insertAttendancetable(Date1, StudentID, subId,
							this.Standard, this.CurDate.toString());
				}
			}
		}
		return 1;
		// return menuItems;

	}

	public int getallsubjectfee() {

		final String URL = "http://smartbridges.co.in/Android/Teacher_GetAllSubjectFee.php?MobileID=1&ClassID="
				+ ClassID + "";
		// XML node keys
		final String KEY = "AllFee"; // parent node

		final String sub1 = "Subject";
		final String subID1 = "SubjectID";
		final String fee1 = "Fee";
		final String standard1 = "Standard";

		XMLParser parser = new XMLParser();

		String xml = parser.getXmlFromUrl(URL); // getting XML

		if (xml.equals("n")) {
			return 11;
		}
		Document doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(KEY);
		String Standard2, Subject2, Subid2, Fees2;
		for (int i = 0; i < nl.getLength(); i++) {

			Element e = (Element) nl.item(i);

			Standard2 = parser.getValue(e, standard1);
			Subject2 = parser.getValue(e, sub1);
			Subid2 = parser.getValue(e, subID1);
			Fees2 = parser.getValue(e, fee1);

			if (Fees2 != "") {
				Log.d("total fee", "fee :- " + Fees2 + " , Standard= "
						+ Standard2 + ",  Subject" + Subject2);
				dbo.Inserttotalfeetable(Subid2, Standard2, Fees2, Subject2);
			}

		}

		return 1;

	}
*/
	/*public int getFeeDataFromServer() {

		// final String URL = "http://bhuumisoluutions.com/android/GetF.php";
		// ArrayList<String> list1=new ArrayList<String>();
		// list1=dbo.getAllsubjectNameForFee(StudentID);
		// for(int j=0;j<list1.size();j++)
		// {
		// String subjectid=dbo.getsubjectidforfee(list1.get(j));
		String subjectid = "1";
		;
		final String URL = "http://smartbridges.co.in/Android/Teacher_GetFee.php?MobileID=1&ClassID="
				+ ClassID
				+ "&Standard="
				+ Standard
				+ "&StudentID="
				+ StudentID
				+ "&SubjectID=" + subjectid + "&LastUpdate=2001-01-01";
		// XML node keys
		final String KEY = "Fee"; // parent node
		final String FeesID = "FeesID";
		final String FeesPaid = "FeesPaid";
		final String PlannedFee = "PlannedFee";
		final String Balance = "Balance";
		final String CheckNo = "CheckNo";
		final String Date = "Date";
		final String PlannedDate = "PlannedDate";
		final String PaymentMode = "PaymentMode";
		final String AccountNumber = "AccountNumber";
		final String SubID = "SubjectID";
		final String StudentID = "StudentID";
		final String Remark = "Remark";
		final String SStandard = "Standard";
		final String batch1 = "Batch";

		// final String Standard = "Standard";
		// final String SubjectID="SubjectID";

		// ArrayList<HashMap<String, String>> menuItems = new
		// ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();

		String xml = parser.getXmlFromUrl(URL); // getting XML
		Log.i("GetFeeURL", URL);
		Log.i("GetFeeXML", xml);
		if (xml.equals("n")) {
			return 11;
		}
		Document doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(KEY);
		String FeesID1, FeesPaid1, PlannedFee1, Balance1, CheckNo1, Date1, PlannedDate1, batch, PaymentMode1, AccountNumber1, SubjectID1, StudentID1, Remark1, Standard1;
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			// HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// String str=parser.getValue(e,KEY_ID);
			// Toast.makeText(AndroidXMLParsingActivity.this,str.toString(),Toast.LENGTH_SHORT).show();
			// adding each child node to HashMap key => value
			FeesID1 = parser.getValue(e, FeesID);
			FeesPaid1 = parser.getValue(e, FeesPaid);
			PlannedFee1 = parser.getValue(e, PlannedFee);
			Balance1 = parser.getValue(e, Balance);
			CheckNo1 = parser.getValue(e, CheckNo);
			Date1 = parser.getValue(e, Date);
			PlannedDate1 = parser.getValue(e, PlannedDate);
			PaymentMode1 = parser.getValue(e, PaymentMode);
			AccountNumber1 = parser.getValue(e, AccountNumber);
			SubjectID1 = parser.getValue(e, SubID);
			StudentID1 = parser.getValue(e, StudentID);
			Remark1 = parser.getValue(e, Remark);
			Standard1 = parser.getValue(e, SStandard);
			batch = parser.getValue(e, batch1);
			if (FeesPaid1 != "") {
				Log.d("Student fee", "feesID :- " + FeesID1 + "fee :- "
						+ FeesPaid1 + " , ClassID= " + ClassID);
				dbo.InsertStudentfeetable(StudentID1, Standard1, SubjectID1,
						FeesID1, FeesPaid1, PlannedFee1, Balance1, CheckNo1,
						Date1, PlannedDate1, PaymentMode1, AccountNumber1,
						CurDate, Remark1, batch);
			}
			// map.put(SubjectID, parser.getValue(e, SubjectID));
			// adding HashList to ArrayList
			// menuItems.add(map);
		}
		// }
		return 1;

	}*/

	/*public int getFeeSubjectDataFromServer() {

		// final String URL = "http://bhuumisoluutions.com/android/GetF.php";

		final String URL = "http://smartbridges.co.in/Android/Teacher_GetFeeSubjectDetails.php?MobileID=1&ClassID="
				+ ClassID
				+ "&Standard="
				+ Standard
				+ "&StudentID="
				+ StudentID
				+ "&LastUpdate=2001-01-01";
		// XML node keys
		final String KEY = "FeeData"; // parent node
		final String SubjectID = "SubjectID";
		final String Subject = "Subject";
		final String TotalFee = "TotalFee";
		final String StudentID = "StudentID";
		final String SStandard = "Standard";
		final String batch1 = "Batch";

		XMLParser parser = new XMLParser();

		String xml = parser.getXmlFromUrl(URL); // getting XML
		if (xml.equals("n")) {
			return 11;
		}

		Document doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(KEY);
		String SubjectID1, Subject1, TotalFee1, StudentID1, Standard1, batch;
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			// HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// String str=parser.getValue(e,KEY_ID);
			// Toast.makeText(AndroidXMLParsingActivity.this,str.toString(),Toast.LENGTH_SHORT).show();
			// adding each child node to HashMap key => value
			SubjectID1 = parser.getValue(e, SubjectID);
			Subject1 = parser.getValue(e, Subject);
			TotalFee1 = parser.getValue(e, TotalFee);
			StudentID1 = parser.getValue(e, StudentID);
			Standard1 = parser.getValue(e, SStandard);
			batch = parser.getValue(e, batch1);

			if (SubjectID1 != "") {

				dbo.InsertSubjectFeeDetailTable(ClassID, StudentID1, Standard1,
						SubjectID1, Subject1, TotalFee1, CurDate, batch);

			}
			// map.put(SubjectID, parser.getValue(e, SubjectID));
			// adding HashList to ArrayList
			// menuItems.add(map);
		}
		return 1;

	}*/

	/*public int getExamScheduleFromServer() {

		// final String URL =
		// "http://bhuumisoluutions.com/android/getNotice.php";
		// http://athavaleclasses.com/GetExamSchedule.php?ClassID=1&SubjectID=1&Standard=XI&MobileID=1&LastUpdate=2012-01-10
		final String URL = "http://smartbridges.co.in/Android/GetExamSchedule.php?MobileID=1&ClassID="
				+ ClassID
				+ "&LastUpdate=2001-01-01&Standard="
				+ Standard
				+ "&StudentID=" + StudentID + "";
		// XML node keys
		final String KEY = "Exam"; // parent node
		final String ExamID = "ExamID";
		final String ExamName = "ExamName";
		final String ExamDate = "ExamDate";
		final String TotalMarks = "TotalMarks";
		final String Duration = "Duration";
		final String SubjectID = "SubjectID";

		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();

		String xml = parser.getXmlFromUrl(URL); // getting XML
		if (xml.equals("n")) {
			return 11;
		}

		Document doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(KEY);

		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			// HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// String str=parser.getValue(e,KEY_ID);
			// Toast.makeText(AndroidXMLParsingActivity.this,str.toString(),Toast.LENGTH_SHORT).show();
			// adding each child node to HashMap key => value
			if (parser.getValue(e, ExamID) != "") {
				dbo.insertExamSchedule(parser.getValue(e, ExamDate).toString(),
						parser.getValue(e, ExamID).toString(),
						parser.getValue(e, ExamName).toString(), Standard,
						parser.getValue(e, SubjectID).toString(), parser
								.getValue(e, TotalMarks).toString(), parser
								.getValue(e, Duration).toString(), CurDate
								.toString());

			}
		}
		// menuItems=dbo.getExamSchedule(CurDate,Standard);
		return 1;
	}*/

	/*public int getNoticeDataFromServer() {

		// final String URL =
		// "http://bhuumisoluutions.com/android/getNotice.php";
		final String URL = "http://smartbridges.co.in/Android/GetNotice.php?MobileID=1&ClassID="
				+ ClassID
				+ "&LastUpdate=2001-01-01&Standard="
				+ Standard
				+ "&StudentID=" + StudentID + "";
		// XML node keys
		final String KEY = "Notice"; // parent node
		final String Subject = "Subject";
		final String NoticeDate = "NoticeDate";
		final String NoticeData = "NoticeData";
		final String EnterDate = "EnterDate";

		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();

		String xml = parser.getXmlFromUrl(URL); // getting XML
		if (xml.equals("n")) {
			return 11;
		}
		Document doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(KEY);

		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			// HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// String str=parser.getValue(e,KEY_ID);
			// Toast.makeText(AndroidXMLParsingActivity.this,str.toString(),Toast.LENGTH_SHORT).show();
			// adding each child node to HashMap key => value
			if (parser.getValue(e, Subject) != "") {
				dbo.insertNotice(Standard, parser.getValue(e, NoticeDate)
						.toString(), parser.getValue(e, NoticeData).toString(),
						parser.getValue(e, Subject).toString(), CurDate
								.toString(), parser.getValue(e, EnterDate)
								.toString(), ClassID);

			}
		}

		// menuItems=dbo.getNotices(CurDate,Standard);
		return 1;

	}*/

    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }

    public static boolean is_marshmellow()
    {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

//		if (status != null)
//			// Setting alert dialog icon
//			alertDialog
//					.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private static PowerManager.WakeLock wakeLock;

    public static void acquire(Context context) {
        if (wakeLock != null)
            wakeLock.release();

        PowerManager pm = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "WakeLock");
        wakeLock.acquire();
    }

    public static void release() {
        if (wakeLock != null)
            wakeLock.release();
        wakeLock = null;
    }

    public static byte[] getBytes(InputStream iStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = iStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static String ConvertString(Bitmap thumbnail) {
        String phototostring = "";
        try {


            ByteArrayOutputStream compressphotobytearrray = new ByteArrayOutputStream();

            // Bitmap photobitmap = BitmapFactory.decodeFile(path_new);
            Bitmap photobitmap = thumbnail;

            photobitmap.compress(Bitmap.CompressFormat.PNG, 100, compressphotobytearrray);
            byte[] imagebytearray = compressphotobytearrray.toByteArray();


            compressphotobytearrray.close();


            photobitmap.recycle();
            phototostring = Base64.encodeBytes(imagebytearray);


        } catch (Exception e) {
            log("JARVIS", "ConvertString Error->" + e.getMessage());
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return phototostring;

    }

    public static class MySpinnerAdapter extends ArrayAdapter<String> {
        // Initialise custom font, for example:
        // Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/robotocondensed-light.ttf");
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/robrtocondensed-regular.ttf");

        // (In reality I used a manager which caches the Typeface objects)
        // Typeface font = FontManager.getInstance().getFont(getContext(), BLAMBOT);

        public MySpinnerAdapter(Context context, int resource, List<String> items) {
            super(context, resource, items);
        }

        // Affects default (closed) state of the spinner
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setTypeface(font);
            return view;
        }

        // Affects opened state of the spinner
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setTypeface(font);
            return view;
        }
    }


    public static String post_send_sms(String mobileno, String sms_text) {
        RequestBody formBody = new FormBody.Builder()
                .add("sms_text", sms_text)
                .add("mobile_no", mobileno)
                .build();
        String Response = MyApplication.post_server_call(MyApplication.url_send_sms, formBody);

        return Response;
    }

    public static void requestMarshMallowPermission(final Activity mActivity, View mLayout) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                Manifest.permission.CAMERA)
                || ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            Snackbar.make(mLayout, R.string.camera_read_write_permission_msg,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat
                                    .requestPermissions(mActivity, PERMISSION_CAMERA_READ_WRITE,
                                            PERMISSION_REQUEST_CODE_CAMERA_READ_WRITE);
                        }
                    }).show();

        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(mActivity, PERMISSION_CAMERA_READ_WRITE, PERMISSION_REQUEST_CODE_CAMERA_READ_WRITE);
        }
    }

    public static Bitmap decodeBase64Profile(String input) {
        Bitmap bitmap = null;
        if (input != null) {
            byte[] decodedByte = new byte[0];
            try {
                decodedByte = Base64.decode(input, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        }
        return bitmap;
    }


//	public static class MySpinnerAdd extends ArrayAdapter<String> {
//		// Initialise custom font, for example:
//		Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/robotocondensed-light.ttf");
//
//		// (In reality I used a manager which caches the Typeface objects)
//		// Typeface font = FontManager.getInstance().getFont(getContext(), BLAMBOT);
//
//		public MySpinnerAdapter(Context context, int resource,String items) {
//			super(context, resource, items);
//		}
//
//		// Affects default (closed) state of the spinner
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			TextView view = (TextView) super.getView(position, convertView, parent);
//			view.setTypeface(font);
//			return view;
//		}
//
//		// Affects opened state of the spinner
//		@Override
//		public View getDropDownView(int position, View convertView, ViewGroup parent) {
//			TextView view = (TextView) super.getDropDownView(position, convertView, parent);
//			view.setTypeface(font);
//			return view;
//		}
//	}
}
	/*public String GetEventFromServer() {
		String UpdateDate = dbo.getCalenderLastUpdate(Standard);
		if (UpdateDate == "") {
			final String URL = "http://smartbridges.co.in/Android/GetSchoolCalender.php?MobileID=1&ClassID="
					+ ClassID
					+ "&Standard="
					+ Standard
					+ "&LastUpdate=2001-01-01&StudentID=" + StudentID + "";
			// XML node keys
			final String KEY = "CALENDER"; // parent node
			final String UDate1 = "UDate";
			final String SRemark1 = "SRemark";
			final String LRemark1 = "LRemark";
			ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

			XMLParser parser = new XMLParser();

			String xml = parser.getXmlFromUrl(URL); // getting XML
			if (xml.equals("n")) {
				return "11";
			}

			Document doc = parser.getDomElement(xml); // getting DOM element

			NodeList nl = doc.getElementsByTagName(KEY);

			for (int i = 0; i < nl.getLength(); i++) {
				// creating new HashMap

				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				// String str=parser.getValue(e,KEY_ID);
				// Toast.makeText(AndroidXMLParsingActivity.this,str.toString(),Toast.LENGTH_SHORT).show();
				// adding each child node to HashMap key => value
				EventDate = parser.getValue(e, UDate1);
				ShortEvent = parser.getValue(e, SRemark1);
				LongEvent = parser.getValue(e, LRemark1);
				if (EventDate != "")
					dbo.insertDateandEvent(LongEvent, ShortEvent, EventDate,
							Standard, CurDate);

			}
			return LongEvent;
			// dbo.insertstudentinfotable(StudentName, ClassName, StudentID,
			// Standard, SubjectID);
			// adding HashList to ArrayList
		} // menuItems.add(map);
		else {
			final String URL = "http://smartbridges.co.in/Android/GetSchoolCalender.php?MobileID=1&ClassID="
					+ ClassID
					+ "&Standard="
					+ Standard
					+ "&LastUpdate="
					+ UpdateDate + "";
			// XML node keys
			final String KEY = "CALENDER"; // parent node
			final String UDate1 = "UDate";
			final String SRemark1 = "SRemark";
			final String LRemark1 = "LRemark";

			ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

			XMLParser parser = new XMLParser();

			String xml = parser.getXmlFromUrl(URL); // getting XML
			if (xml.equals("n")) {
				return "11";
			}

			Document doc = parser.getDomElement(xml); // getting DOM element

			NodeList nl = doc.getElementsByTagName(KEY);

			for (int i = 0; i < nl.getLength(); i++) {
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				// String str=parser.getValue(e,KEY_ID);
				// Toast.makeText(AndroidXMLParsingActivity.this,str.toString(),Toast.LENGTH_SHORT).show();
				// adding each child node to HashMap key => value
				EventDate = parser.getValue(e, UDate1);
				ShortEvent = parser.getValue(e, SRemark1);
				LongEvent = parser.getValue(e, LRemark1);
				if (EventDate != "")
					dbo.updateDateandEvent(LongEvent, ShortEvent, EventDate,
							Standard, CurDate);

			}
			return LongEvent;
		}
	}

	public Integer getClassData() {
		Log.d("Getclasss", "us--" + AdminUserName + "  pa--" + AdminPassword);
		// final String URL = "http://bhuumisoluutions.com/android/Getdata.php";
		final String URL = "http://smartbridges.co.in/Android/Teacher_GetClassInfo.php?MobileID=1&UserName="
				+ AdminUserName
				+ "&Password="
				+ AdminPassword
				+ "&LastUpdate=2001-01-01";
		// XML node keys
		final String KEY = "ClassData"; // parent node
		final String BranchName1 = "BranchName";
		final String ClassID1 = "ClassID";
		final String ClassName1 = "ClassName";
		final String Standard1 = "Standard";
		final String SubjectName1 = "SubjectName";
		final String SubjectID1 = "SubjectID";
		final String Batch1 = "Batch";
		final String ValidTill1 = "ValidTill";
		final String Lastlogin1 = "Lastlogin";

		String Standard2 = "", ClassID2 = "", ClassName2 = "", SubjectName2 = "", SubjectID2 = "", Batch2 = "", BranchName2 = "", ValidTill2 = "", Lastlogin2 = "";

		XMLParser parser = new XMLParser();

		String xml = parser.getXmlFromUrl(URL); // getting XML
		Log.d("XML", "Null" + xml);
		if (xml.equals("n")) {
			Log.d("Null", "Null" + URL);
			return 11;
		}
		Document doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(KEY);
		SQLiteDatabase db = dbo.getWritableDatabase();
		String str1 = "delete from ClassData";
		db.rawQuery(str1, null).moveToFirst();
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			// HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			BranchName2 = parser.getValue(e, BranchName1);
			Standard2 = parser.getValue(e, Standard1);
			ClassID2 = parser.getValue(e, ClassID1);
			ClassName2 = parser.getValue(e, ClassName1);
			SubjectID2 = parser.getValue(e, SubjectID1);
			SubjectName2 = parser.getValue(e, SubjectName1);
			Batch2 = parser.getValue(e, Batch1);
			ValidTill2 = parser.getValue(e, ValidTill1);
			Lastlogin2 = parser.getValue(e, Lastlogin1);
			Log.d("Getclasss", "in for loop");
			if (ClassID2 != "") {
				Log.d("Getclasss", "in if loop");
				dbo.insertintoclasstable(BranchName2, ClassID2, ClassName2,
						Standard2, SubjectID2, SubjectName2, Batch2,
						ValidTill2, Lastlogin2);
			}
		}
		ClassID = ClassID2;
		ClassName = ClassName2;
		if (ClassID != "") {
			Log.d("Getclasss", "return 3");
			dbo.insertuspa(AdminUserName, AdminPassword, ClassID, ClassName);
			// dbo.insertPersonalInfotable(UserName,Password,ClassID,CurDate.toString(),StudentID);
			return 3;
		} else {
			Log.d("Getclasss", "return 4");
			return 4;
		}
	}*/

//	public int insert_new_StudentData(String name, String sub, String phone,
//			String Phone2) {
//		int ret = 0;
//		// int ii;
//		String StudentID = "", StudentName = "", UserName = "", Password = "", SubjectID = "", Batch = "", Subject = "", Standardd = "", Phone1 = "", TotalFee = "";
//		Phone1 = phone;
//		StudentName = name;
//		if (name.length() < 4) {
//			UserName = name.substring(0, name.length()) + phone.substring(0, 4);
//		} else {
//			UserName = name.substring(0, 4) + phone.substring(0, 4);
//		}
//
//		Password = UserName;
//		String lastupdate = "2001-01-01";
//		Log.d("in function", "stname-- " + StudentName + phone);
//		StudentID = dbo.createstudentid();
//		// StudentID="15";
//
//		String subjject[] = sub.split("\\,");
//		Log.d("sub array", subjject.length + "---" + Arrays.toString(subjject));
//		for (int i = 0; i <= subjject.length - 1; i++) {
//			Log.d("in for", "-- " + subjject[i]);
//			if (subjject[i] != "") {
//				Log.d("in if", "subject " + subjject[i]);
//				SubjectID = dbo.getsubjectid2(subjject[i]);
//				Subject = subjject[i];
//				TotalFee = dbo.getsubjectfee(SubjectID, Subject, selectedstd);
//				Log.d("nin bottom if", "totalfee " + TotalFee);
//				if (StudentID != "") {
//					Log.d("last if ", "----- " + StudentID);
//					dbo.insertintoStudentinfo(StudentName, ClassID, ClassName,
//							StudentID, selectedstd, SubjectID, Subject,
//							multipleselectedbatches, Phone1, Phone2, CurDate,
//							selectedbranch, UserName, Password);
//					dbo.insertPersonalInfotable(UserName, Password, ClassID,
//							CurDate.toString(), StudentID);
//					dbo.insertstudentinfotable(StudentName, ClassName,
//							StudentID, selectedstd, SubjectID);
//					dbo.insertSubjectTable(Subject, SubjectID);
//					dbo.InsertSubjectFeeDetailTable(ClassID, StudentID,
//							selectedstd, SubjectID, Subject, TotalFee,
//							CurDate.toString(), multipleselectedbatches);
//					Log.d("insert_stud", "stid-" + StudentID);
//
//					ret++;
//				}
//			}
//		}
//		return ret;
//	}

	/*public int getStudentData() {

	 final String URL = "http://bhuumisoluutions.com/android/Getdata.php"; String lastupdate; // if(selectedbatch.equals("All")) // { // lastupdate="2001-01-01"; // } // else // { // lastupdate=dbo.getlastupdatedate(ClassID,selectedstd,selectedbatch); // if(lastupdate ==null) // { // lastupdate="2001-01-01"; // } // } lastupdate = "2001-01-01"; selectedbranch = selectedbranch.replace(" ", "%20"); final String URL = "http://smartbridges.co.in/Android/Teacher_GetStudentDataAndroid.php?MobileID=1&BranchName=" + selectedbranch + "&ClassID=" + ClassID + "&Standard=" + selectedstd + "&Batch=" + selectedbatch + "&LastUpdate=" + lastupdate; // final String // URL="http://smartbridges.co.in/Android/GetStudentDataAndroid.php?MobileID=1&BranchName=Laxmi%20Road%20Branch&ClassID=4&Standard=XII&Batch=&LastUpdate=2001-01-01"; // final String URL = // "http://smartbridges.co.in/Android/GetStudentDataAndroid.php?MobileID=1&BranchName="+selectedbranch+"&ClassID="+ClassID+"&Standard="+selectedstd+"&Batch=&LastUpdate="+lastupdate; // XML node keys final String KEY = "Student"; // parent node final String StudentID1 = "StudentID"; final String StudentName1 = "StudentName"; final String SubjectID1 = "SubjectID"; final String Subject1 = "Subject"; final String UserName1 = "UserName"; final String Password1 = "Password"; final String Batch1 = "Batch"; final String Standard1 = "Standard"; final String Phone1 = "Phone"; final String Phone2 = "Phone2"; String StudentID = "", StudentName = "", UserName = "", Password = "", SubjectID = "", Batch = "", Subject = "", Standardd = "", Phone = "", Phonee = ""; XMLParser parser = new XMLParser();

		String xml = parser.getXmlFromUrl(URL); // getting XML
		if (xml.equals("n")) {
			return 11;
		}
		Document doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(KEY);
		Log.d("DATA", "" + nl.getLength());
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			// HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// String str=parser.getValue(e,KEY_ID);
			// Toast.makeText(AndroidXMLParsingActivity.this,str.toString(),Toast.LENGTH_SHORT).show();
			// adding each child node to HashMap key => value

			StudentID = parser.getValue(e, StudentID1);
			StudentName = parser.getValue(e, StudentName1);
			UserName = parser.getValue(e, UserName1);
			Password = parser.getValue(e, Password1);
			SubjectID = parser.getValue(e, SubjectID1);
			Subject = parser.getValue(e, Subject1);		Batch = parser.getValue(e, Batch1);
			Standardd = parser.getValue(e, Standard1);
			Phone = parser.getValue(e, Phone1);
			Phonee = parser.getValue(e, Phone2);

			if (StudentID != "")
				dbo.insertintoStudentinfo(StudentName, ClassID, ClassName,
						StudentID, Standardd, SubjectID, Subject, Batch, Phone,
						Phonee, CurDate, selectedbranch, UserName, Password);
			dbo.insertPersonalInfotable(UserName, Password, ClassID,
					CurDate.toString(), StudentID);
			// dbo.insertstudentinfotable(StudentName,ClassName,StudentID,Standard,SubjectID);
			// dbo.insertSubjectTable(SubjectName,SubjectID);
		Log.d("insert_stud", "stid-" + StudentID);
			// adding HashList to ArrayList
			// menuItems.add(map);
		}
		return 1;
	}

}*/
