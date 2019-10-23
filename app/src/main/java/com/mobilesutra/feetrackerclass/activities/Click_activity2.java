package com.mobilesutra.feetrackerclass.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobilesutra.feetrackerclass.ErrorHandler.ExceptionHandler;
import com.mobilesutra.feetrackerclass.R;

public class Click_activity2 extends Activity {
    LinearLayout step_1, step_2, step_3;
    TextView info_step_1, info_step_2, info_step_3;
    int j=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_click_activity2);

        step_1 = (LinearLayout) findViewById(R.id.step_1);
        step_2 = (LinearLayout) findViewById(R.id.step_2);
        step_3 = (LinearLayout) findViewById(R.id.step_3);

        info_step_1 = (TextView) findViewById(R.id.txt_info_1);
        info_step_2 = (TextView) findViewById(R.id.txt_info_2);
        info_step_3 = (TextView) findViewById(R.id.txt_info_3);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (j == 2) {

                step_1.setVisibility(View.INVISIBLE);
                info_step_1.setVisibility(View.GONE);
                info_step_2.setVisibility(View.VISIBLE);
                step_2.setVisibility(View.VISIBLE);

            }
            else if (j == 3) {

                info_step_2.setVisibility(View.GONE);
                info_step_3.setVisibility(View.VISIBLE);
                step_2.setVisibility(View.INVISIBLE);
                step_3.setVisibility(View.VISIBLE);

            }else
            {
                finish();
            }
            j++;
        }
        return true;
    }

}
