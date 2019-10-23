package com.mobilesutra.feetrackerclass.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobilesutra.feetrackerclass.ErrorHandler.ExceptionHandler;
import com.mobilesutra.feetrackerclass.R;

public class ClickActivity extends Activity {

	LinearLayout step_1, step_2, step_3, step_4, step_5, step_6,ll_step_1,ll_step_2,ll_step_3;
	int j=2;
	TextView txt_info_1 = null,txt_info_2 = null,txt_info_3 = null,txt_info_4 = null,txt_info_5 = null,txt_info_6 = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
		setContentView(R.layout.activity_click);

		initComponents();
	}

	public void initComponents()
	{
		txt_info_1 = (TextView) findViewById(R.id.txt_info_1);
		txt_info_1.setVisibility(View.VISIBLE);
		txt_info_2 = (TextView) findViewById(R.id.txt_info_2);
		txt_info_3 = (TextView) findViewById(R.id.txt_info_3);
		txt_info_4 = (TextView) findViewById(R.id.txt_info_4);
		txt_info_5 = (TextView) findViewById(R.id.txt_info_5);
		txt_info_6 = (TextView) findViewById(R.id.txt_info_6);
		step_1 = (LinearLayout) findViewById(R.id.step_11);
		step_2 = (LinearLayout) findViewById(R.id.step_2);
		step_3 = (LinearLayout) findViewById(R.id.step_3);
		step_4 = (LinearLayout) findViewById(R.id.step_4);
		step_5 = (LinearLayout) findViewById(R.id.step_5);
		step_6 = (LinearLayout) findViewById(R.id.step_6);
		ll_step_1 = (LinearLayout) findViewById(R.id.ll_step_1);
		ll_step_2 = (LinearLayout) findViewById(R.id.ll_step_2);
		ll_step_3 = (LinearLayout) findViewById(R.id.ll_step_3);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {


//				Toast.makeText(this,""+j,Toast.LENGTH_SHORT).show();


				 if (j == 2) {
					txt_info_1.setVisibility(View.GONE);
					txt_info_2.setVisibility(View.VISIBLE);
					step_1.setVisibility(View.INVISIBLE);
					step_2.setVisibility(View.VISIBLE);
				}
				else if (j == 3) {
					ll_step_1.setVisibility(View.GONE);
					txt_info_2.setVisibility(View.GONE);
					txt_info_3.setVisibility(View.VISIBLE);
					step_2.setVisibility(View.INVISIBLE);
					step_3.setVisibility(View.VISIBLE);
					 ll_step_2.setVisibility(View.VISIBLE);
				}
				else if (j == 4) {
					txt_info_3.setVisibility(View.GONE);
					txt_info_4.setVisibility(View.VISIBLE);
					step_3.setVisibility(View.INVISIBLE);
					step_4.setVisibility(View.VISIBLE);
					 ll_step_2.setVisibility(View.VISIBLE);

				}
				else if (j == 5) {
					ll_step_1.setVisibility(View.INVISIBLE);
					txt_info_4.setVisibility(View.GONE);
					txt_info_5.setVisibility(View.VISIBLE);
					ll_step_2.setVisibility(View.GONE);
					txt_info_5.setVisibility(View.VISIBLE);
					step_4.setVisibility(View.INVISIBLE);
					step_5.setVisibility(View.VISIBLE);
					 ll_step_3.setVisibility(View.VISIBLE);
				}else if(j==6) {
					 txt_info_5.setVisibility(View.GONE);
					 txt_info_6.setVisibility(View.VISIBLE);
					 step_5.setVisibility(View.INVISIBLE);
					 step_6.setVisibility(View.VISIBLE);
					 ll_step_3.setVisibility(View.VISIBLE);
				}else
				 {
					 finish();
				 }
	j++;

		}
		return true;
	}
}
