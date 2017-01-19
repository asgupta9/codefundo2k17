package com.example.jarvis.loginapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class cutomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutom);
        final RadioButton merchant = (RadioButton) findViewById(R.id.rd1);
        final RadioButton student = (RadioButton) findViewById(R.id.rd2);
        Button create = (Button) findViewById(R.id.btn);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(merchant.isChecked()){
                    Intent createIntent = new Intent(cutomActivity.this, userType.class); // <----- START "SEARCH" ACTIVITY
                    startActivity(createIntent);
                }
                else if(student.isChecked()) {
                        Intent typeIntent = new Intent(cutomActivity.this, register1.class); // <----- START "TYPE ENTRIES OUT" ACTIVITY
                        startActivity(typeIntent);
                    }
                }
        });
    }
}
