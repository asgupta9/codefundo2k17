package com.example.jarvis.loginapplication;

import java.sql.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private EditText editTextMobile;
    private EditText editTextPassword;
    RadioButton r1,r2;
    private DBHelper db;
    private Session session;
    public String Name="", DOB="", City="",Mobile,Password,Address="",Room="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        session = new Session(this);

        editTextMobile = (EditText) findViewById(R.id.editTextMobile);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        r1 = (RadioButton) findViewById(R.id.radioButton1);
        r2 = (RadioButton) findViewById(R.id.radioButton2);


        /*if (session.loggedin()){
            startActivity(new Intent(MainActivity.this, ProfileActivity.class) );
            finish();
        }*/
        int count = db.rowCount();
        int count2 = db.rowCount2();
        if (count == 1) {
            String[] result = new String[5];
            result = db.getRow();
            Intent intent = new Intent(MainActivity.this, fetchActivity.class);
//            intent.putExtra("Mobile", result[4]);
//            intent.putExtra("Password", result[3]);
//            intent.putExtra("Name", result[0]);
//            intent.putExtra("City", result[2]);
//            intent.putExtra("DOB", result[1]);
            startActivity(intent);
            finish();

        } else if (count2 == 1) {
            Intent intent = new Intent(MainActivity.this,fetchActivity2.class);
//            intent.putExtra("Mobile", result[4]);
//            intent.putExtra("Password", result[3]);
//            intent.putExtra("Name", result[0]);
//            intent.putExtra("City", result[2]);
//            intent.putExtra("DOB", result[1]);
            startActivity(intent);
            finish();
        }

    }

    public void invokeLogin(View view){
        if(r1.isChecked()){
            final String mobile = editTextMobile.getText().toString();
            final String password = editTextPassword.getText().toString();
            editTextMobile.setText("");
            editTextPassword.setText("");
//        Log.d("Log 1","Starting");
//        login(mobile, password);
//        Log.d("Log 3","Starting");
            String type="login";


            class BackgroundWorker extends AsyncTask<String,Void,String> {
                ProgressDialog progressDialog = null;
                Context context;
                AlertDialog alertDialog;
                public BackgroundWorker(Context ctx){
                    context=ctx;
                }

                @Override
                protected String doInBackground(String... params) {
                    String type = params[0];

                    String login_url = "http://codefundof9.azurewebsites.net/login.php";
                    if(type.equals("login")){
                        try {
                            String mobile = params[1];
                            String password = params[2];
                            URL url = new URL(login_url);
                            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.setDoInput(true);
                            OutputStream outputStream = httpURLConnection.getOutputStream();
                            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                            String post_data = URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8")+"&"
                                    +URLEncoder.encode("password","UTF-8")+"="+ URLEncoder.encode(password,"UTF-8");
                            bufferedWriter.write(post_data);
                            bufferedWriter.flush();
                            bufferedWriter.close();
                            outputStream.close();
                            InputStream inputStream = httpURLConnection.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                            String result="";
                            String line="";
                            while((line = bufferedReader.readLine())!= null) {
                                result += line;
                            }
                            bufferedReader.close();
                            inputStream.close();
                            httpURLConnection.disconnect();
                            return result;
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }

                @Override
                protected void onPreExecute() {
                    alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Login Status");
                    ProgressDialog pd = new ProgressDialog(MainActivity.this);
                    pd.setMessage("Please Wait");
                    pd.show();
                }
                @Override
                protected void onPostExecute(String result) {
                    String s = result;
                    String sentence = s;

                    alertDialog.dismiss();
                    if (!s.equalsIgnoreCase("failure")){
                        String[] resultStr = new String[3];
                        resultStr[0]="";
                        resultStr[1]="";
                        resultStr[2]="";

                        int flag=0,i=0;
                        while(i<sentence.length()){
                            if(sentence.charAt(i)!=' ')
                                resultStr[flag]+= sentence.charAt(i);
                            else if(sentence.charAt(i)==' '){
                                flag++;
                            }
                            i++;
                        }

                        Name=resultStr[0];
                        City=resultStr[1];
                        DOB=resultStr[2];

                        db.addUser(Name, City, DOB, password, mobile);

                        //Intent intent = new Intent(MainActivity.this,ProfileActivity.class);

                        ///A change is made
                        Intent intent = new Intent(MainActivity.this,fetchActivity.class);
//                    intent.putExtra("Mobile", mobile);
//                    intent.putExtra("Password", password);
//                    intent.putExtra("Name", Name);
//                    intent.putExtra("City", City);
//                    intent.putExtra("DOB", DOB);

                        context.startActivity(intent);
                        finish();
                    }
                    else{
                        alertDialog.setMessage("Invalid Credentials!!!");
                        alertDialog.show();
                    }
                }
                @Override
                protected void onProgressUpdate(Void... values) {
                    super.onProgressUpdate(values);
                }
            }
            BackgroundWorker backgroundWorker=new BackgroundWorker(this);
            backgroundWorker.execute(type,mobile,password);
        }
        else if(r2.isChecked()){
            final String mobile = editTextMobile.getText().toString();
            final String password = editTextPassword.getText().toString();
            editTextMobile.setText("");
            editTextPassword.setText("");
//        Log.d("Log 1","Starting");
//        login(mobile, password);
//        Log.d("Log 3","Starting");
            String type="login";

            class BackgroundWorker extends AsyncTask<String,Void,String> {
                ProgressDialog progressDialog = null;
                Context context;
                AlertDialog alertDialog;
                public BackgroundWorker(Context ctx){
                    context=ctx;
                }

                @Override
                protected String doInBackground(String... params) {
                    String type = params[0];

                    String login_url = "http://codefundof9.azurewebsites.net/merchantLogin.php";
                    if(type.equals("login")){
                        try {
                            String mobile = params[1];
                            String password = params[2];
                            URL url = new URL(login_url);
                            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.setDoInput(true);
                            OutputStream outputStream = httpURLConnection.getOutputStream();
                            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                            String post_data = URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8")+"&"
                                    +URLEncoder.encode("password","UTF-8")+"="+ URLEncoder.encode(password,"UTF-8");
                            bufferedWriter.write(post_data);
                            bufferedWriter.flush();
                            bufferedWriter.close();
                            outputStream.close();
                            InputStream inputStream = httpURLConnection.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                            String result="";
                            String line="";
                            while((line = bufferedReader.readLine())!= null) {
                                result += line;
                            }
                            bufferedReader.close();
                            inputStream.close();
                            httpURLConnection.disconnect();
                            return result;
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }

                @Override
                protected void onPreExecute() {
                    alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Login Status");
                    ProgressDialog pd = new ProgressDialog(MainActivity.this);
                    pd.setMessage("Please Wait");
                    pd.show();
                }
                @Override
                protected void onPostExecute(String result) {
                    String s = result;
                    String sentence = s;
                    Log.d("value = ",sentence);
                    alertDialog.dismiss();
                    if (!s.equalsIgnoreCase("failure")){
                        String[] resultStr = new String[3];
                        resultStr[0]="";
                        resultStr[1]="";
                        resultStr[2]="";

                        int flag=0,i=0;
                        while(i<sentence.length()){
                            if(sentence.charAt(i)!='+')
                                resultStr[flag]+= sentence.charAt(i);
                            else if(sentence.charAt(i)=='+'){
                                flag++;
                            }
                            i++;
                        }

                        Name=resultStr[0];
                        Address=resultStr[1];
                        Room=resultStr[2];
                        int intRoom =Integer.parseInt(Room);

                        db.addUser2(Name, mobile, Address, password, intRoom);

                        Intent intent = new Intent(MainActivity.this,fetchActivity2.class);
//                    intent.putExtra("Mobile", mobile);
//                    intent.putExtra("Password", password);
//                    intent.putExtra("Name", Name);
//                    intent.putExtra("City", City);
//                    intent.putExtra("DOB", DOB);

                        startActivity(intent);
                        finish();
                    }
                    else{
                        alertDialog.setMessage("Invalid Credentials!!!");
                        alertDialog.show();
                    }
                }
                @Override
                protected void onProgressUpdate(Void... values) {
                    super.onProgressUpdate(values);
                }
            }
            BackgroundWorker backgroundWorker=new BackgroundWorker(this);
            backgroundWorker.execute(type,mobile,password);
        }
        else{
            Toast.makeText(this, "Enter the proper choices...", Toast.LENGTH_LONG).show();
        }

    }

    public void register(View view){

        //Intent intent = new Intent(MainActivity.this, register1.class);
        //MainActivity.this.startActivity(intent);
        final CharSequence[] items = {"Merchant "," Student "};

        new AlertDialog.Builder(this)
                .setSingleChoiceItems(items, 0, null)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                        // Do something useful withe the position of the selected radio button
                        Log.d("MYINT", "value: " + selectedPosition);

                        if(selectedPosition==0){
                            Intent i=new Intent(MainActivity.this,userType.class);
                            startActivity(i);

                        }
                        if(selectedPosition==1) {
                            Intent j = new Intent(MainActivity.this,register1.class);
                            startActivity(j);

                        }
                    }
                })
                .show();



    }
//    public void register2(View view) {
//        Intent intent = new Intent(MainActivity.this, userType.class);
//        MainActivity.this.startActivity(intent);
//    }

}