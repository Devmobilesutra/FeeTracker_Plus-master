package com.mobilesutra.feetrackerclass.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.mobilesutra.feetrackerclass.ErrorHandler.ExceptionHandler;
import com.mobilesutra.feetrackerclass.Fragment.Add_fee_with_list;
import com.mobilesutra.feetrackerclass.Fragment.Class_profile_fragement;

import com.mobilesutra.feetrackerclass.Fragment.Personal_profile_fragment;
import com.mobilesutra.feetrackerclass.MyApplication;
import com.mobilesutra.feetrackerclass.R;
import com.mobilesutra.feetrackerclass.tabs.SlidingTabLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


public class Activity_profileNew extends AppCompatActivity implements  Class_profile_fragement.OnFragmentInteractionListener{


   final private List<SamplePagerItem> mTabs = new ArrayList<SamplePagerItem>();
    private com.mobilesutra.feetrackerclass.tabs.SlidingTabLayout mSlidingTabLayout;
    private SampleFragmentPagerAdapter sampleFragmentPagerAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_activity_profile_new);
        infopage();
        populateTable();

    }
    private void populateTable() {
        runOnUiThread(new Runnable() {
            public void run() {
                //If there are stories, add them to the table
                if (mSlidingTabLayout == null) {
                    try {
                        final ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
                        //setupViewPager(viewPager);
                        LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
                        lhm.put("profile", "profile");
                        lhm.put("class profile", "class profile");
                      //  lhm.put("Activate Batches", "Activate Batches");
                        lhm.put("Define Fee", "Define Fee");
                        int lhmLength = lhm.size();
                        Set<String> keys = lhm.keySet();
                        for (String key : keys) {
                            mTabs.add(new SamplePagerItem(
                                    key, // Title
                                    getResources().getColor(R.color.blue_900), // Indicator color
                                    android.R.color.transparent // Divider color
                            ));
                        }
                        sampleFragmentPagerAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager());
                        mViewPager.setAdapter(sampleFragmentPagerAdapter);



                        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
                        mSlidingTabLayout.setViewPager(mViewPager);

                        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                Log.d("tag", "onPageScrolled22->" + position);
                                Log.d("tag", "onPageScrolled->" + position);
                                MyApplication.set_session(MyApplication.SESSION_CURRENT_TAB, position + "");
                                Fragment fragmentDemo = getSupportFragmentManager().findFragmentById(R.id.viewpager);
                                Log.d("tag", "fragmentDemo->" + fragmentDemo);
                                if (position == 2) {
                                    /*Create_batch first = (Create_batch) sampleFragmentPagerAdapter.instantiateItem(mViewPager, position);
                                    first.refreshData();*/

                                    Add_fee_with_list first = (Add_fee_with_list) sampleFragmentPagerAdapter.instantiateItem(mViewPager, position);
                                    first.refreshData();
                                }
                                /*if(position==3) {

                                }*/

                            }

                            @Override
                            public void onPageScrollStateChanged(int position) {


                            }
                        });

                        Bundle b = getIntent().getExtras();
                        if(b!= null)
                        {
                            MyApplication.log("dialog_tab: bundle "+b);
                            int tab = 0;
                            try {
                                tab = Integer.parseInt(b.getString("dialog_tab"));
                                MyApplication.log("dialog_tab"+tab);

                            }catch (Exception e)
                            {

                            }
                            mSlidingTabLayout.set_current_tab(tab);
                        }
                        else{
                            mSlidingTabLayout.set_current_tab(0);
                        }


                        // BEGIN_INCLUDE (tab_colorizer)
                        // Set a TabColorizer to customize the indicator and divider colors. Here we just retrieve
                        // the tab at the position, and return it's set color
                        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

                            @Override
                            public int getIndicatorColor(int position) {
                                if (mTabs.size() > position)
                                    return mTabs.get(position).getIndicatorColor();
                                else
                                    return getResources().getColor(R.color.add_subject_color);
                            }

                            @Override
                            public int getDividerColor(int position) {
                                if (mTabs.size() > position)
                                    return Color.parseColor("#000000");//mTabs.get(position).getDividerColor();
                                else
                                    return Color.parseColor("#000000");
                            }


                        });
                    } catch (final Exception ex) {
                        Log.i("---", "Exception in thread");
                    }

                } else {

                    Toast.makeText(Activity_profileNew.this, "This populate else message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onFragmentInteraction(int uri) {
//        Toast.makeText(Activity_profileNew.this,"onFragmentInteraction"+uri,Toast.LENGTH_SHORT).show();
        mSlidingTabLayout.set_current_tab(uri);


    }

    class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

        SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return the {@link android.support.v4.app.Fragment} to be displayed at {@code position}.
         * <p/>
         * Here we return the value returned from {@link SamplePagerItem#createFragment()}.
         */
        @Override
        public android.support.v4.app.Fragment getItem(int i) {
            return mTabs.get(i).createFragment();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)

        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         * <p/>
         * Here we return the value returned from {@link SamplePagerItem#getTitle()}.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs.get(position).getTitle();
        }
        // END_INCLUDE (pageradapter_getpagetitle)

    }

    static class SamplePagerItem {
        private final CharSequence mTitle;
        private final int mIndicatorColor;
        private final int mDividerColor;

        SamplePagerItem(CharSequence title, int indicatorColor, int dividerColor) {
            mTitle = title;
            mIndicatorColor = indicatorColor;
            mDividerColor = dividerColor;
        }

        /**
         * @return A new {@link android.support.v4.app.Fragment} to be displayed by a {@link ViewPager}
         */
        android.support.v4.app.Fragment createFragment() {
            if (mTitle.toString().equalsIgnoreCase("profile"))
                return Personal_profile_fragment.newInstance(mTitle.toString(), mTitle.toString());
            else if (mTitle.toString().equalsIgnoreCase("class profile"))
                return Class_profile_fragement.newInstance(mTitle.toString(), mTitle.toString());
           /* else if (mTitle.toString().equalsIgnoreCase("Activate Batches"))
                 return Create_batch.newInstance(mTitle.toString(), mTitle.toString());*/
            else if (mTitle.toString().equalsIgnoreCase("Define Fee"))
                return Add_fee_with_list.newInstance(mTitle.toString(), mTitle.toString());
            else
                return BlankFragment.newInstance(mTitle.toString(), mTitle.toString());
        }

        /**
         * @return the title which represents this tab. In this sample this is used directly by
         * {@link android.support.v4.view.PagerAdapter#getPageTitle(int)}
         */
        CharSequence getTitle() {
            return mTitle;
        }

        /**
         * @return the color to be used for indicator on the {@link SlidingTabLayout}
         */
        int getIndicatorColor() {
            return mIndicatorColor;
        }

        /**
         * @return the color to be used for right divider on the {@link SlidingTabLayout}
         */
        int getDividerColor() {
            return mDividerColor;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_profile_new, menu);
        return true;
    }
    public void infopage()
    {
        if(MyApplication.get_session("check1")=="")
        {
            MyApplication.set_session("check1","check");
            Intent openMainActivity2 = new Intent(Activity_profileNew.this, Click_activity2.class);
            startActivity(openMainActivity2);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // TODO Auto-generated method stub
        Intent i = new Intent(Activity_profileNew.this, ActivityDashboard.class);
        startActivity(i);
        finish();
    }
}

