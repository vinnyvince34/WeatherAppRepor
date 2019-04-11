package com.example.weatherapp1.WeatherList;

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

import com.example.weatherapp1.Data.Model.DisplayClass;
import com.example.weatherapp1.R;
import com.example.weatherapp1.WeatherList.MainActivity;
import com.example.weatherapp1.WeatherManipulator.Main3Activity;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<DisplayClass> weatherList;
    public static int index = 0;

    public ListAdapter(Activity activity, ArrayList<DisplayClass> weatherList) {
        this.activity = activity;
        this.weatherList = new ArrayList<>(weatherList);
    }

    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
                notifyDataSetChanged();
            }
        });

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

        Log.d("Network", "getView: " +  weatherList.get(position).toString());
        holder.cityField.setText("City: " + weatherList.get(position).getName());
        holder.detailsField.setText("Visibility: " + weatherList.get(position).getVisibility() + " m");
        holder.currentTemperatureField.setText("Temperature: " + weatherList.get(position).getMain().getTemp() + " C");
        holder.humidity_field.setText("Humidity: " + weatherList.get(position).getMain().getPressure() + "g/m3");
        holder.pressure_field.setText("Pressure: " + weatherList.get(position).getMain().getPressure() + "Pa");
        holder.updatedField.setText("Wind speed:" + weatherList.get(position).getWind().getSpeed() + "m/s");
        return convertView;
    }

    public void setmWeathers(List<DisplayClass> weathers) {
        weatherList.clear();
        weatherList.addAll(weathers);
        notifyDataSetChanged();
    }
}
