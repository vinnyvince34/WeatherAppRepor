package com.example.weatherapp1.WeatherManipulator;

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

import com.example.weatherapp1.Data.Model.DisplayClass;
import com.example.weatherapp1.Data.Model.MainWeatherClass;
import com.example.weatherapp1.Data.Source.Remote.listResourcesApi;
import com.example.weatherapp1.Data.Source.WeatherRepository;
import com.example.weatherapp1.WeatherList.MainActivity;
import com.example.weatherapp1.Misc.Networking;
import com.example.weatherapp1.R;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
            DisplayEdit = new DisplayClass();

            textView.setText("Edit City");
            cityInput.setText(toEdit.getName());

            SaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sNewAddress = String.valueOf(cityInput.getText());
                    service.getMainWeatherClassJson(sNewAddress, "metric", "b60c7e86a1a0721e4f380436455f7f25")
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(Schedulers.io())
                            .map(mainWeatherClass -> mappingMainWeather(mainWeatherClass))
                            .flatMap(displayClass -> repository.insert(displayClass))
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnError(new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Log.e(TAG, "accept: " + throwable.getCause());
                                }
                            })
                            .doOnNext(new Consumer<DisplayClass>() {
                                @Override
                                public void accept(DisplayClass displayClass) throws Exception {
                                    DisplayEdit = displayClass;
                                }
                            })
                            .doOnComplete(new Action() {
                                @Override
                                public void run() throws Exception {
                                    Intent passIntent = new Intent(v.getContext(), DisplayClass.class).putExtra("UpdateData", (Parcelable) toEdit);
                                    setResult(RESULT_OK, passIntent);
                                    finish();
                                }
                            })
                            .subscribe();
                }
            });
        } else {
            intent = getIntent();
            toAdd = new MainWeatherClass();
            DisplayAdd = new DisplayClass();

            textView.setText("Add City");

            SaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sNewAddress = String.valueOf(cityInput.getText());
                    service.getMainWeatherClassJson(sNewAddress, "metric", "b60c7e86a1a0721e4f380436455f7f25")
                            .subscribeOn(Schedulers.newThread())
                            .map(mainWeatherClass -> mappingMainWeather(mainWeatherClass))
                            .flatMap(displayClass -> repository.insert(displayClass))
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnError(new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Log.e(TAG, "accept: " + throwable.getCause());
                                }
                            })
                            .doOnNext(new Consumer<DisplayClass>() {
                                @Override
                                public void accept(DisplayClass displayClass) throws Exception {
                                    DisplayAdd = displayClass;
                                }
                            })
                            .doOnComplete(new Action() {
                                @Override
                                public void run() throws Exception {
                                    Intent passIntent = new Intent(v.getContext(), DisplayClass.class).putExtra("AddData", (Parcelable) DisplayAdd);
                                    setResult(RESULT_OK, passIntent);
                                    finish();
                                }
                            })
                            .subscribe();
                }
            });
        }
    }

    private DisplayClass mappingMainWeather(@NotNull MainWeatherClass mainWeatherClass){
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
