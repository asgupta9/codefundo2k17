package com.example.jarvis.loginapplication;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
public class fetchActivity extends Activity {

    ProgressDialog pd;
    String JSON_STRING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch);

    }

    public void visitProfile(View view){
        Intent intent=new Intent(getApplicationContext(),SessionActivity.class);
        startActivity(intent);

    }
    public void openAppabout(View view){
        Intent intent=new Intent(getApplicationContext(),AboutApp.class);
        startActivity(intent);

    }

    public void fetchJSON(View view){
        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void,String>{
        String json_url;
        @Override
        protected void onPreExecute(){
            json_url="http://codefundof9.azurewebsites.net/fetchMerchant.php";

            pd = new ProgressDialog(fetchActivity.this);
            pd.setMessage("Please Wait");
            pd.show();

        }
        @Override
        protected String doInBackground(Void... voids){
            try{
                URL url =new URL (json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((JSON_STRING = bufferedReader.readLine())!=null){
                    stringBuilder.append(JSON_STRING+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();
            }
            catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return null;

        }


        @Override
        protected void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }



        @Override
        protected void onPostExecute(String result){
            pd.dismiss();

            String test="",finalResult="";
            int i;
            for(i=1;i<result.length()-1;i++){
                if(result.charAt(i)=='[')
                        test+="\n";
                else if(result.charAt(i)==']')
                    test+="\n";
                else if(result.charAt(i)=='"')
                        continue;
                else if(result.charAt(i)=='+') {
                    finalResult += "\n" + test;
                    test="";
                }
                else if(result.charAt(i)==',')
                    continue;
                else{
                    test+=result.charAt(i);

                }

            }




            TextView tv=(TextView)findViewById(R.id.tv);
            tv.setText(finalResult);
        }
    }
}
