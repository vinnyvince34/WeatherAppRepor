package com.example.weatherapp1;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;
import okhttp3.ResponseBody;

//Edit Page
public class Main3Activity extends AppCompatActivity {
    private static final String TAG = "Main3Activity";
    Intent intent;
    MainWeatherClass toEdit = null;
    MainWeatherClass toAdd = null;
    Completable observable = new Completable() {
        @Override
        protected void subscribeActual(CompletableObserver observer) {
            return;
        }
    };
    listResourcesApi service;
    Observable observableResponse;
    Observable<MainWeatherClass> response = null;
    DisplayClass DisplayAdd;
    DisplayClass DisplayEdit;
    WeatherRepository repository = new WeatherRepository(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        final TextInputEditText cityInput = (TextInputEditText) findViewById(R.id.CityInput);
        Button SaveBtn = (Button) findViewById(R.id.SaveBtn);
        TextView textView = (TextView) findViewById(R.id.TextView);

        observable.subscribeOn(Schedulers.newThread());
        service = Networking.getRetrofitObject();

        observable = service.getMainWeatherClassObserve()
                .flatMapCompletable(new Function<MainWeatherClass, CompletableSource>() {
                    @Override
                    public CompletableSource apply(MainWeatherClass mainWeatherClass) throws Exception {
                        WeatherRepository repository = null;
                        repository.getmAllWeather().getValue();
                        return (CompletableSource) repository;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


        if(MainActivity.bEdit == true) {
            intent = getIntent();
            toEdit = (MainWeatherClass) intent.getParcelableExtra("EditUser");

            textView.setText("Edit City");
            cityInput.setText(toEdit.getName());

            SaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String sNewAddress = String.valueOf(cityInput.getText());
                        toEdit.setName(sNewAddress);
                        Intent passIntent = new Intent(v.getContext(), MainActivity.class).putExtra("UpdateData", (Parcelable) toEdit);
                        setResult(RESULT_OK, passIntent);
                        finish();
                    } catch (Exception e) {
                        Log.d("Exception", "onClick: " + e.getMessage());
                    }
                }
            });
        } else {
            intent = getIntent();
            toAdd = new MainWeatherClass();

            textView.setText("Add City");

            SaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                    String sNewAddress = String.valueOf(cityInput.getText());
                    service.getMainWeatherClassJson(sNewAddress, "metric", "b60c7e86a1a0721e4f380436455f7f25")
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(Schedulers.io())
                            .map(mainWeatherClass -> mappingMainWeather(mainWeatherClass))
                            .flatMap(displayClass -> repository.insert(displayClass))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(o -> {});
                        Log.d("Network", "onClick: " + toAdd.toString());
                        Intent passIntent = new Intent(v.getContext(), MainActivity.class).putExtra("AddData", (Parcelable) toAdd);
                        setResult(RESULT_OK, passIntent);
                        finish();
                    } catch (Exception e) {
                        Log.d("Exception", "onClick: " + e.getMessage());
                    }
                }
            });
        }
    }

    private DisplayClass mappingMainWeather(MainWeatherClass mainWeatherClass){
        DisplayClass displayAdd = new DisplayClass();
        Log.e(TAG, "onNext: " + mainWeatherClass.toString());
        displayAdd.setWeather(mainWeatherClass.getWeather()[0]);
        displayAdd.setVisibility(Integer.parseInt(mainWeatherClass.getVisibility()));
        displayAdd.setMain(mainWeatherClass.getMain());
        displayAdd.setCloud(mainWeatherClass.getCloud());
        displayAdd.setName(mainWeatherClass.getName());
        displayAdd.setWind(mainWeatherClass.getWind());
        Log.e(TAG, "onNext: " + displayAdd.toString());
        return displayAdd;
    }

    private Completable dbInsert() {
        return Completable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                observableResponse = service.getMainWeatherClassObserve();
                WeatherDAO dao = WeatherRoomDB.getDB(getApplicationContext()).weatherDAO();
                dao.insert();
                return null;
            }
        });
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
