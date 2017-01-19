package com.example.jarvis.loginapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jarvis on 1/11/2017.
 */

public class TimeLine extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.timeline, container, false);
        return rootView;

    }
}
