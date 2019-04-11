package com.example.weatherapp1.WeatherList;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.weatherapp1.Data.Model.DisplayClass;
import com.example.weatherapp1.R;
import com.example.weatherapp1.WeatherManipulator.Main3Activity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, updatedField;
    ArrayList<DisplayClass> weatherList = new ArrayList<>();
    ListAdapter adapter;
    public static final int REQUEST_CODE = 1;
    public static boolean bEdit = false;
    public static boolean bIntentEmpty = true;
    ListView lv;
    private Completable  observable = new Completable() {
        @Override
        protected void subscribeActual(CompletableObserver observer) {

        }
    };
    private WeatherViewModel mWeatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        weatherList.clear();
        mWeatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);

        cityField = (TextView) findViewById(R.id.cityField);
        updatedField = (TextView) findViewById(R.id.updateField);
        detailsField = (TextView) findViewById(R.id.detailField);
        currentTemperatureField = (TextView) findViewById(R.id.tempField);
        humidity_field = (TextView) findViewById(R.id.humidField);
        pressure_field = (TextView) findViewById(R.id.pressureField);
        lv = (ListView) findViewById(R.id.weatherLV);
        Button addButton = (Button) findViewById(R.id.AddBTN);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bEdit = false;
                bIntentEmpty = false;
                Intent intent = new Intent(v.getContext(), Main3Activity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        adapter = new ListAdapter(this, weatherList);
        lv.setAdapter(adapter);
        mWeatherViewModel.getmAllWeather().observe(this, new Observer<List<DisplayClass>>() {
            @Override
            public void onChanged(@Nullable List<DisplayClass> displayClasses) {
                adapter.setmWeathers(displayClasses);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mWeatherViewModel.getmAllWeather().observe(this, new Observer<List<DisplayClass>>() {
            @Override
            public void onChanged(@Nullable List<DisplayClass> displayClasses) {
                adapter.setmWeathers(displayClasses);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        observable.unsubscribeOn(Schedulers.io());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(bEdit == true) {
            DisplayClass receiver = data.getParcelableExtra("UpdateData");
            int position = receiver.getUid();
            weatherList.set(position, receiver);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
