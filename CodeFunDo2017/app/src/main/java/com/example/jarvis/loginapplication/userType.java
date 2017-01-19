package com.example.jarvis.loginapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


public class userType extends AppCompatActivity {
    private EditText edt1, edt2, edt3, edt4,edt5;
    public int intRoom;
    private Button btn;
    private TextView tv;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        edt1=(EditText)findViewById(R.id.edt1);
        edt2=(EditText)findViewById(R.id.edt2);
        edt3=(EditText)findViewById(R.id.edt3);
        edt4=(EditText)findViewById(R.id.edt4);
        edt5=(EditText)findViewById(R.id.edt5);

        db=new DBHelper(this);
        btn= (Button)findViewById(R.id.register);
        tv =(TextView)findViewById(R.id.tv);

    }

    public void register(View view){
        final String name = edt1.getText().toString();
        final String mobile = edt2.getText().toString();
        final String address= edt3.getText().toString();
        final String password = edt4.getText().toString();
        final String room = edt5.getText().toString();

        edt4.setText("");
        edt2.setText("");

        if (name.isEmpty() || mobile.isEmpty() || address.isEmpty() || password.isEmpty() || room.isEmpty()){
            Toast.makeText(userType.this, "Field/s can't be left blank :)", Toast.LENGTH_LONG).show();
            return;
        }
        String type="register";
        class BackgroundWorker extends AsyncTask<String,Void,String> {
            Context context;
            AlertDialog alertDialog;
            public BackgroundWorker(Context ctx){
                context=ctx;
            }

            @Override
            protected String doInBackground(String... params) {
                String type = params[0];
                String register_url = "http://codefundof9.azurewebsites.net/merchantRegister.php";
                if(type.equals("register")){
                    try {
                        String name = params[1];
                        String mobile = params[2];
                        String address = params[3];
                        String password = params[4];
                        String room = params[5];
                        intRoom = Integer.parseInt(room);
                        URL url = new URL(register_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                                + URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8")+"&"
                                + URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8")+"&"
                                +URLEncoder.encode("password","UTF-8")+"="+ URLEncoder.encode(password,"UTF-8")+"&"
                                +URLEncoder.encode("room","UTF-8")+"="+URLEncoder.encode(room,"UTF-8")+"&";
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
                alertDialog.setTitle("Register Status");
                ProgressDialog pd = new ProgressDialog(userType.this);
                pd.setMessage("Please Wait");
                pd.show();
            }
            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                alertDialog.dismiss();
                if (s.equalsIgnoreCase("success")){

                    db.addUser2(name,mobile,address,password,intRoom);

                    Intent intent = new Intent(userType.this, fetchActivity2.class);
                    context.startActivity(intent);
                }
                else{
                    alertDialog.setMessage("Number Already Registered!!!");
                    alertDialog.show();
                }
            }
            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }
        BackgroundWorker backgroundWorker=new BackgroundWorker(this);
        backgroundWorker.execute(type,name,mobile,address,password,room);
    }
    public void goBack(View view){
        Intent intent = new Intent(userType.this, MainActivity.class);
        userType.this.startActivity(intent);
    }

}
