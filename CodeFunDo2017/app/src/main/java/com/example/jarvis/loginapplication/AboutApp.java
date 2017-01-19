package com.example.jarvis.loginapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        TextView textView = (TextView) findViewById(R.id.details);
        textView.setText("This app is created for the purpose for smooth search of rooms and food services in KOTA CITY " +
                ".\n" +
                "This app is created by \n\nAbhishek Sah\n \nAkash Gupta.\n\nAkshay Agrawal.");


    }
}
