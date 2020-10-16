package com.example.weather1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class fetchData extends AsyncTask<Void, Void, Void> {

    String data="";
    String temperature="";
    String city="";
    String description = "";
    String speed = "";
    String humidity = "";
    String pressure = "";
    String feelslike ="";
    String sunrise = "";

    String imageUrl = "";
    Bitmap mIcon;
    String imageIcon = "";
    String formattedDate = "";

    String TempSearchKey = MainActivity.seacrhKey.getQuery().toString();

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+TempSearchKey+"&units=metric&appid=db8a7fe0a4acfcd6bfc3d482d509f772");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            //all data
            JSONObject reader = new JSONObject(data);

            //main data
            JSONObject maindata = reader.getJSONObject("main");
            temperature = maindata.getString("temp");
            humidity = maindata.getString("humidity");
            pressure = maindata.getString("pressure");
            feelslike = maindata.getString("feels_like");

            //sys data
            JSONObject sysdata = reader.getJSONObject("sys");
            sunrise = sysdata.getString("sunrise");

            long value = (long) Double.parseDouble(sunrise);

            Date date = new java.util.Date(value*1000L);
// the format of your date
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss z");
// give a timezone reference for formatting (see comment at the bottom)
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT +05:30"));
            formattedDate = sdf.format(date);


            //city name
            city = reader.getString("name");

            //weather data
            JSONArray weatherdata = reader.getJSONArray("weather");
            //-> 0
            JSONObject zerodata = weatherdata.getJSONObject(0);
            //description
            description = zerodata.getString("description");
            imageIcon = zerodata.getString("icon");


            URL photoUrl = new URL("http://openweathermap.org/img/wn/"+imageIcon+"@2x.png");
            mIcon = BitmapFactory.decodeStream(photoUrl.openConnection() .getInputStream());

            //wind
            JSONObject winddata = reader.getJSONObject("wind");
            speed = winddata.getString("speed");


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (city.length()==0){
            MainActivity.mainlayout.setVisibility(View.INVISIBLE);
        }
        else

            MainActivity.mainlayout.setVisibility(View.VISIBLE);

            MainActivity.temperatureText.setText(temperature);
            MainActivity.cityText.setText(this.city);
            MainActivity.detailsText.setText(this.description);
            MainActivity.windText.setText("WIND | "+this.speed+" km/h");
            MainActivity.humidityText.setText(this.humidity+"%");
            MainActivity.pressureText.setText(this.pressure+" Pa");
            MainActivity.feelsText.setText("Feels like "+this.feelslike+" c");
            MainActivity.sunriseText.setText(this.formattedDate);

            MainActivity.imageIcon.setImageBitmap(this.mIcon);


    }
}
