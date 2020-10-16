package com.example.weather1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //private static
    //public static sear cityName;
    public static SearchView seacrhKey;
    public static TextView temperatureText;
    public static TextView cityText;
    public static TextView detailsText;
    public static TextView windText;
    public static TextView humidityText;
    public static TextView pressureText;
    public static ImageView imageIcon;
    public static TextView feelsText;
    public static TextView sunriseText;

    public static LinearLayout mainlayout;

    Button searchbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchbtn = (Button) findViewById(R.id.searchbtn);

        temperatureText = (TextView) findViewById(R.id.temperature);

        seacrhKey = (SearchView) findViewById(R.id.search_bar); // inititate a search view
        CharSequence query = seacrhKey.getQuery(); // get the query string currently

        cityText = (TextView) findViewById(R.id.city);
        detailsText = (TextView) findViewById(R.id.details);
        windText = (TextView) findViewById(R.id.windSpeed);
        humidityText = (TextView) findViewById(R.id.humidity);
        pressureText = (TextView) findViewById(R.id.pressure);
        feelsText = (TextView) findViewById(R.id.feels);
        sunriseText = (TextView) findViewById(R.id.sunrise);

        imageIcon = (ImageView) findViewById(R.id.image);

        mainlayout = (LinearLayout) findViewById(R.id.maincontent) ;
        mainlayout.setVisibility(View.INVISIBLE);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String TempSearchKey = MainActivity.seacrhKey.getQuery().toString();
                if (TempSearchKey.length()==0){
                    Toast.makeText(getApplicationContext(), "City name is empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    //mainlayout.setVisibility(View.VISIBLE);
                    fetchData process = new fetchData();
                    process.execute();
                }
            }
        });

    }

    public void msg(){
        Toast.makeText(getApplicationContext(), "No results found",Toast.LENGTH_SHORT).show();
    }
}
