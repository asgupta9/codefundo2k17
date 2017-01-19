package com.example.jarvis.loginapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class sessionActivityMerchant extends AppCompatActivity implements View.OnClickListener{
    private TextView tv1,tv2,tv3,tv4;
    private SessionMerchant session;
    private Button btn;
    private DBHelper db;
    String[] result= new String[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_merchant);
        //String str = getIntent().getStringExtra("Mobile");
        session=new SessionMerchant(this);
        db=new DBHelper(this);

        result=db.getRow2();

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

        tv1.setText(result[2]);     //name
        tv2.setText(result[0]);     //mobile
        tv3.setText(result[4]);     //room
        tv4.setText(result[3]);     //address
    }
    public void updateDetails(View view){
        Intent intent = new Intent(sessionActivityMerchant.this, MerchantUpdate.class);
        startActivity(intent);
    }
    public void logout(View view){
        db.delRow2();
        int rooms=Integer.parseInt(result[4]);
        session.setLoggedin(false, result[0], result[4],result[1],rooms);
        finish();
        startActivity(new Intent(sessionActivityMerchant.this, MainActivity.class));
    }
    @Override
    public void onClick(View v) {
        logout(v);
    }
}

