package com.example.weatherapp1;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    protected TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, updatedField;
    public static String city = "London";
    public static String OPEN_WEATHER_MAP_API = "b60c7e86a1a0721e4f380436455f7f25";
    protected ListView lv = null;
    protected JSONObject json = null;
    protected Activity activity = getParent();
    public ArrayList<MainWeatherClass> weatherList;
    protected ListAdapter adapter;
    protected static Gson gson = new Gson();
    protected DisplayItem display = new DisplayItem();
    protected ArrayList<DisplayItem> displayItems = new ArrayList<DisplayItem>();
    protected Intent mainIntent = new Intent(this, Main3Activity.class);
    protected Retrofit retrofit = null;
    public static String endpointA = "weather?q=" + city + "&units=metric&appid=" + OPEN_WEATHER_MAP_API;
    public static SQLiteDatabase db = null;
    public static DBHelper helper;
    public static Cursor result;
    public static final int REQUEST_CODE = 1;
    public static boolean bEdit = false;
    public static boolean bIntentEmpty = true;
    protected MainWeatherClass main;
    private Completable observable = new Completable() {
        @Override
        protected void subscribeActual(CompletableObserver observer) {

        }
    };
    private WeatherViewModel mWeatherViewModel;
    private WeatherRoomDB mWeatherRoomDB = new WeatherRoomDB() {
        @Override
        public WeatherDAO weatherDAO() {
            return null;
        }

        @NonNull
        @Override
        protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
            return null;
        }

        @NonNull
        @Override
        protected InvalidationTracker createInvalidationTracker() {
            return null;
        }

        @Override
        public void clearAllTables() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        mWeatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        mWeatherViewModel.getmAllWeather().observe(this, new Observer <List<MainWeatherClass>>() {
            @Override
            public void onChanged(@Nullable List<MainWeatherClass> mainWeatherClasses) {
                adapter.setmWeathers(mainWeatherClasses);
            }
        });

        cityField = (TextView) findViewById(R.id.cityField);
        updatedField = (TextView) findViewById(R.id.updateField);
        detailsField = (TextView) findViewById(R.id.detailField);
        currentTemperatureField = (TextView) findViewById(R.id.tempField);
        humidity_field = (TextView) findViewById(R.id.humidField);
        pressure_field = (TextView) findViewById(R.id.pressureField);
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

        helper = new DBHelper(this);
        weatherList = new ArrayList<MainWeatherClass>();
        adapter = new ListAdapter(this, weatherList, helper);

        lv = findViewById(R.id.weatherLV);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void update(MainWeatherClass param) {
        weatherList.add(param);
        adapter.update(weatherList);
        adapter.notifyDataSetChanged();
        mWeatherViewModel.insert(param);
        adapter.notifyDataSetInvalidated();
    }

    @Override
    protected void onResume() {
        super.onResume();

        observable.subscribeOn(Schedulers.io());
        observable.subscribe(new Action() {
            @Override
            public void run() throws Exception {
                if(weatherList != null) {
                    update(main);
                    Log.d("Done", "run: success" );
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d("Error", "Exception: " + throwable.getLocalizedMessage());
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
        if (requestCode == REQUEST_CODE) {
            if(bIntentEmpty == true) {
                Intent intent = getIntent();
            } else {
                if(bEdit == true) {
                    MainWeatherClass receiver = (MainWeatherClass) data.getParcelableExtra("UpdateData");
                    main = receiver;
                    Log.d("MainActivity",weatherList.get(0).getBase() + "\n");
                    if (resultCode == Activity.RESULT_OK) {
                        mWeatherViewModel.insert(receiver);
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        return;
                    }
                } else {
                    MainWeatherClass receiver = (MainWeatherClass) data.getParcelableExtra("AddData");
                    main = receiver;
                    Log.d("MainActivity",receiver.getBase()  + "\n");
                    if (resultCode == Activity.RESULT_OK) {
                        mWeatherViewModel.insert(receiver);
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        return;
                    }
                }
            }
        }
    }
}
