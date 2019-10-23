package com.mobilesutra.feetrackerclass;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesutra.feetrackerclass.ErrorHandler.ExceptionHandler;
import com.mobilesutra.feetrackerclass.Tour.ProductTour3Activity;
import com.mobilesutra.feetrackerclass.activities.ActivityDashboard;

import org.jsoup.Jsoup;

public class Splash extends Activity {
	boolean active = true;
	final int splashTime = 100; // time to display the splash screen in m
	String newVersion = "", LOG_TAG = "ActivitySplashScreen";
	Context context;
	String currDate = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);


		MyApplication.log(LOG_TAG, "in OnCretate splash screen");
		context = this;
		// by bhavesh
		//check_for_upgrade();
		proceed();
	}

	public void proceed() {
		MyApplication.log("aaa", "in for proced");
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (active && (waited < splashTime)) {
						sleep(1000);
						if (active) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {

					if(MyApplication.get_session(MyApplication.Key_product_info).equalsIgnoreCase("")) {
						MyApplication.set_session(MyApplication.Key_product_info,"Y");
						Intent openMainActivity2 = new Intent(Splash.this, ProductTour3Activity.class);
						startActivity(openMainActivity2);
						finish();
					}else {
						//Intent openMainActivity2=new Intent(Splash.this,ActivityDashboard.class);
						Intent openMainActivity=new Intent(Splash.this,Login.class);

						openMainActivity.putExtra("iferrorpresent", "0");
						startActivity(openMainActivity);
						finish();
					}
					//	intent1.putExtra("FromNotification", "0");
				}
			}
		};
		splashTread.start();

	}

	void check_for_upgrade() {
		if (((MyApplication) getApplication()).isNetworkAvailable()) {
		//	pDialog = ProgressDialog.show(context, getResources().getString(R.string.app_name), "Please Wait...", false, false);
			Log.d("Exception :","Exception :"+"if cond");
			new FetchAppVersionFromGooglePlayStore().execute();

		} else {
			proceed();
		}
	}

	class FetchAppVersionFromGooglePlayStore extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... urls) {
			try {
				MyApplication.log("JARVIS", "in do in background");
				return
						Jsoup.connect("https://play.google.com/store/apps/details?id=" + "com.mobilesutra.feetrackerclass" + "&hl=en")
								.timeout(30000)
								.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
								.referrer("http://www.google.com")
								.get()
								.select(".hAyfc .htlgb")
								.get(5)
								.ownText();

			} catch (Exception e) {
				return "";
			}
		}

		protected void onPostExecute(String string) {
			newVersion = string;
			MyApplication.log("JARVIS", "in do in onPostExecute");
			Log.d("new Version", newVersion);
			MyApplication.log("JARVIS", "in do in onPostExecute newVersion" + newVersion);
			if (newVersion != "") {
				float server_version = Float.parseFloat(newVersion);
				float app_version = 0.0f;
				PackageManager manager = context.getPackageManager();
				PackageInfo info = null;
				try {
					info = manager.getPackageInfo(context.getPackageName(), 0);
					app_version = Float.parseFloat(info.versionName);
					//      if(value(info.versionName)<value(newVersion));
				} catch (PackageManager.NameNotFoundException e) {
					MyApplication.log(LOG_TAG, " VersionE->" + e);
				}
				MyApplication.log(LOG_TAG, "ActivitySplash AppVersion->" + info.versionName);
				MyApplication.log(LOG_TAG, "ActivitySplash ServerVersion->" + newVersion);

				MyApplication.log(LOG_TAG, "ActivitySplash AsyncOnPostExecuteIf");


				if (server_version > app_version) {
					//current application is not updatedMy
					MyApplication.log(LOG_TAG, "in update is available ");
					MyApplication.set_session("UPDATE_APP", "TRUE");
					show_upgrade_dialog();
				} else {
					MyApplication.log(LOG_TAG, "in no update");
					proceed();
				}

				//show_upgrade_dialog();
			}
			else
			{
				proceed();
			}
		}
	}

	void show_upgrade_dialog() {
		final Dialog dialog1 = new Dialog(Splash.this);
		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog1.setContentView(R.layout.layout_upgrade1);

		dialog1.show();
		dialog1.setCancelable(false);
		dialog1.setCanceledOnTouchOutside(false);
		final TextView title = (TextView) dialog1.findViewById(R.id.txt_head);
		final TextView message = (TextView) dialog1.findViewById(R.id.txt_title);
		final TextView txt_upgrade = (TextView) dialog1.findViewById(R.id.txt_upgrade);
		final TextView txt_cancel = (TextView) dialog1.findViewById(R.id.txt_cancel);

        /*title.setTypeface(custom_font);
        message.setTypeface(custom_font);
        txt_upgrade.setTypeface(custom_font);
        txt_cancel.setTypeface(custom_font);*/

		txt_cancel.setVisibility(View.GONE);
		txt_upgrade.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				Uri uri = Uri.parse("market://details?id=" + getApplication().getPackageName());
				Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
				// To count with Play market backstack, After pressing back button,
				// to taken back to our application, we need to add following flags to intent.
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
				try {
					MyApplication.log(LOG_TAG, "ActivitySplash 1");
					startActivity(goToMarket);
					MyApplication.log(LOG_TAG, "ActivitySplash 2");
				} catch (ActivityNotFoundException e) {
					MyApplication.log(LOG_TAG, "ActivitySplash 3");
					startActivity(new Intent(Intent.ACTION_VIEW,
							Uri.parse("http://play.google.com/store/apps/details?id=" + getApplication().getPackageName())));
				}

				//new FetchAppVersionFromGooglePlayStore().execute();
				dialog1.dismiss();
			}
		});

		txt_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//finish();
				dialog1.dismiss();
				//    proceed();
			}
		});
	}
}
