package com.mobilesutra.feetrackerclass.Tour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProductTourFragment extends Fragment {
 
    final static String LAYOUT_ID = "layoutid";
 
    public static ProductTourFragment newInstance(int layoutId) {
        ProductTourFragment pane = new ProductTourFragment();
        Bundle args = new Bundle();
        Log.d("tag","---"+layoutId);
        args.putInt(LAYOUT_ID, layoutId);
        pane.setArguments(args);
        return pane;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(getArguments().getInt(LAYOUT_ID, -1), container, false);
        return rootView;
    }
}