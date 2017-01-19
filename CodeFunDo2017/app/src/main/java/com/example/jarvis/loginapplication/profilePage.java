package com.example.jarvis.loginapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class profilePage extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        tv=(TextView)findViewById(R.id.tv);
        String str = getIntent().getStringExtra("Mobile");
        tv.setText("Welcome  "+str);
    }
}
