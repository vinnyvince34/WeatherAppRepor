package com.example.weatherapp1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<MainWeatherClass> weatherList;
    DBHelper helper;
    private List<MainWeatherClass> mWeathers;
    public static int index = 0;

    public ListAdapter(Activity activity, ArrayList<MainWeatherClass> weatherList, DBHelper helper) {
        this.activity = activity;
        this.weatherList = new ArrayList<>(weatherList);
        this.helper = helper;
    }

    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, updatedField;
        Button deleteButton, editButton;
        ListView lv;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_part, null);
            holder = new ViewHolder();
            holder.cityField = (TextView) convertView.findViewById(R.id.cityField);
            holder.detailsField = (TextView) convertView.findViewById(R.id.detailField);
            holder.currentTemperatureField = (TextView) convertView.findViewById(R.id.tempField);
            holder.humidity_field = (TextView) convertView.findViewById(R.id.humidField);
            holder.pressure_field = (TextView) convertView.findViewById(R.id.pressureField);
            holder.updatedField = (TextView) convertView.findViewById(R.id.updateField);
            holder.deleteButton = (Button) convertView.findViewById(R.id.deleteBTN);
            holder.editButton = (Button) convertView.findViewById(R.id.editBTN);
            holder.lv = (ListView) convertView.findViewById(R.id.weatherLV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.deleteButton.setTag(convertView);
        holder.deleteButton.setTag(position);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherList.remove(position);
                helper.delete(position);
                notifyDataSetChanged();
            }
        });

        Log.d("Network", "getView: " +weatherList.toString());

        holder.editButton.setTag(convertView);
        holder.editButton.setTag(position);
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                MainActivity.bEdit = true;
                MainActivity.bIntentEmpty = false;
                Intent intent = new Intent(v.getContext(), Main3Activity.class).putExtra("EditUser", (Parcelable) weatherList.get(position));;
                activity.startActivityForResult(intent, MainActivity.REQUEST_CODE);
            }
        });
        for(int i = 0; i < weatherList.size(); i++) {
            DateFormat df = DateFormat.getDateTimeInstance();

            Log.d("Network", "getView: " +  weatherList.get(i).toString());
            holder.cityField.setText("City: " + weatherList.get(i).getName());
            holder.detailsField.setText("Visibility: " + weatherList.get(i).getVisibility() + "m");
            holder.currentTemperatureField.setText("Temperature: " + weatherList.get(i).getMain().getTemp() + "C");
            holder.humidity_field.setText("Humidity: " + weatherList.get(i).getMain().getHumidity() + "g/m3");
            holder.pressure_field.setText("Pressure: " + weatherList.get(i).getMain().getPressure() + "Pa");
            holder.updatedField.setText("Wind speed:" + weatherList.get(i).getWind().getSpeed() + "m/s");
        }
        return convertView;
    }

    void setmWeathers(List<MainWeatherClass> weathers) {
        mWeathers = weathers;
        notifyDataSetChanged();
    }

    public void update(List<MainWeatherClass> newData) {
        weatherList.clear();
        weatherList.addAll(newData);
    }
}
