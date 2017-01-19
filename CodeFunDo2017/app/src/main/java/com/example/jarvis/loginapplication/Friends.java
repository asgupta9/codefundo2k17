package com.example.jarvis.loginapplication;

import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

/**
* Created by jarvis on 1/7/2017.
*/
public class Friends extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friends, container, false);
        return rootView;
    }
}
