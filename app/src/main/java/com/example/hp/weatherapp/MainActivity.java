package com.example.hp.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;
/*
public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        editText=findViewById(R.id.editText);
        textView=findViewById(R.id.textView);

    }
    public void download_function(View view)
    {
        String city_name=editText.getText().toString();

        DownloadTask downloadTask=new DownloadTask();
        try
        {
            downloadTask.execute("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22").get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public class DownloadTask extends AsyncTask<String,Void,String>
    {
        URL url;
        String string="";
        @Override
        protected String doInBackground(String... urls)
        {
            try
            {
                url=new URL(urls[0]);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                InputStream in=connection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);

                int data=reader.read();
                while(data!=-1)
                {
                    char ch= (char) data;
                    string+=ch;
                }
                return string;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try
            {
                JSONObject jsonObject=new JSONObject(s);
                String data=jsonObject.getString("weather");
                JSONArray array=new JSONArray(data);

                String message="";
                for(int i=0;i<array.length();i++)
                {
                    JSONObject object=array.getJSONObject(i);
                    String main=object.getString("main");
                    String description=object.getString("description");

                    if( (!main.equals("")) && (!description.equals("")) )
                    {
                        message+=main+" : "+description;
                    }
                }

                if(!message.equals(""))
                {
                    textView.setText(message);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
 */


public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        resultTextView = findViewById(R.id.textView);
    }

    public void getWeather(View view) {
        try {
            DownloadTask task = new DownloadTask();
            String CityName =  editText.getText().toString();

            task.execute("https://samples.openweathermap.org/data/2.5/weather?q="+CityName+"&appid=0972d1490b516d2860baae977b9dde60");

            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Could not find weather :(",Toast.LENGTH_SHORT).show();
        }
    }

    public class DownloadTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();

                Toast.makeText(getApplicationContext(),"Could not find weather :(",Toast.LENGTH_SHORT).show();

                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);

                String weatherInfo = jsonObject.getString("weather");

                Log.i("Weather content", weatherInfo);

                JSONArray arr = new JSONArray(weatherInfo);

                String message = "";

                for (int i=0; i < arr.length(); i++) {
                    JSONObject jsonPart = arr.getJSONObject(i);

                    String main = jsonPart.getString("main");
                    String description = jsonPart.getString("description");

                    if (!main.equals("") && !description.equals("")) {
                        message += main + ": " + description + "\r\n";
                    }
                }

                if (!message.equals("")) {
                    resultTextView.setText(message);
                } else {
                    Toast.makeText(getApplicationContext(),"Could not find weather :(",Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {

                Toast.makeText(getApplicationContext(),"Could not find weather :(",Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
    }
}
