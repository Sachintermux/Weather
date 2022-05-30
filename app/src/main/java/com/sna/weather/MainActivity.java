package com.sna.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
TextView cloudStatus,location,temperature;
ViewModel_Main viewModel_main;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String lat = intent.getStringExtra("Lat");
        String longitude = intent.getStringExtra("Long");
        viewModel_main = new ViewModelProvider(MainActivity.this).get(ViewModel_Main.class);
        try {
            viewModel_main.getData(lat,longitude);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        cloudStatus = findViewById(R.id.weatherTxtView_main);
        location = findViewById(R.id.locationNameTxtView_main);
        temperature =findViewById(R.id.temperatureTxtView_main);

        viewModel_main.cloudStatus.observe(MainActivity.this,data->cloudStatus.setText(data));
        viewModel_main.temperature.observe(MainActivity.this,data->temperature.setText(data));
        viewModel_main.location.observe(MainActivity.this,data->location.setText(data));


    }



}