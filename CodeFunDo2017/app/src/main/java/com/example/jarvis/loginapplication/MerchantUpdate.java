package com.example.jarvis.loginapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MerchantUpdate extends AppCompatActivity {
    public EditText edt1;
    public DBHelper db;
    public String room;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_update);
        edt1=(EditText)findViewById(R.id.edt1);
        db=new DBHelper(this);
        room = edt1.getText().toString();
    }

    public void update(View view){
        String[] result=new String[5];
        result=db.getRow2();

        final String mobile = result[0];
        final int  intRoom = Integer.parseInt(result[4]);

        String type="update";

        class BackgroundWorker extends AsyncTask<String,Void,String> {
            Context context;
            //AlertDialog alertDialog;
            public BackgroundWorker(Context ctx){
                context=ctx;
            }

            @Override
            protected String doInBackground(String... params) {
                String type = params[0];
                String register_url = "http://codefundof9.azurewebsites.net/merchantUpdate.php";
                if(type.equals("update")){
                    try {
                        String name = params[1];
                        String room = params[2];
                        //int updatedRoom = Integer.parseInt(room);
                        URL url = new URL(register_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String post_data = URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8")+"&"
                                +URLEncoder.encode("room","UTF-8")+"="+ URLEncoder.encode(room,"UTF-8");
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
                /*alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Update Status");
                ProgressDialog pd = new ProgressDialog(MerchantUpdate.this);
                pd.setMessage("Please Wait");
                pd.show();*/
            }
            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                //alertDialog.dismiss();
                if (s.equalsIgnoreCase("success")){
                    //db.update2(mobile, intRoom);
                    Toast.makeText(MerchantUpdate.this, "Updated",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MerchantUpdate.this, MainActivity.class);
                    context.startActivity(intent);
                    finish();
                }
//                else{
//                    //alertDialog.setMessage("Error Occurred!!!");
//                    //alertDialog.show();
//                }
            }
            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }
        BackgroundWorker backgroundWorker=new BackgroundWorker(this);
        backgroundWorker.execute(type,mobile,room);
    }
    //db.update();
    }
