package com.example.jarvis.loginapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class SessionActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv1,tv2,tv3,tv4;
    private Session session;
    private Button btn;
    private DBHelper db;
    String[] result= new String[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        //String str = getIntent().getStringExtra("Mobile");
        session=new Session(this);
        db=new DBHelper(this);

        result=db.getRow();

        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        tv3=(TextView)findViewById(R.id.tv3);
        tv4=(TextView)findViewById(R.id.tv4);
        btn=(Button)findViewById(R.id.logout_button);

        //if(!session.loggedin()){
         //   logout();
        //}
//        HashMap<String,String> user= session.getUserDetails();
//        String name=user.get(Session.KEY_NAME);
//        String dob=user.get(Session.KEY_DOB);
//        String city=user.get(Session.KEY_CITY);
//        String mobile=user.get(Session.KEY_MOBILE);

        tv1.setText(result[0]);
        tv2.setText(result[4]);
        tv3.setText(result[1]);
        tv4.setText(result[2]);
    }
    public void logout(View view){
        db.delRow();
        session.setLoggedin(false, result[0], result[4],result[1],result[2]);
        finish();
        startActivity(new Intent(SessionActivity.this, MainActivity.class));
    }
    @Override
    public void onClick(View v) {
        logout(v);
    }
}
