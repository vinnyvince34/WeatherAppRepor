package com.example.weatherapp1;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Weather implements Parcelable {
    private String icon;
    private String description;
    private String main;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "weather_id")
    private String id;

    public Weather(String icon, String description, String main, String id) {
        this.icon = icon;
        this.description = description;
        this.main = main;
        this.id = id;
    }

    @Ignore
    public Weather() {
    }

    public String getIcon () {
        return icon;
    }
    public void setIcon (String icon) {
        this.icon = icon;
    }
    public String getDescription () {
        return description;
    }
    public void setDescription (String description) {
        this.description = description;
    }
    public String getMain () {
        return main;
    }
    public void setMain (String main) {
        this.main = main;
    }
    public String getId () {
        return id;
    }
    public void setId (String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "icon='" + icon + '\'' +
                ", description='" + description + '\'' +
                ", main='" + main + '\'' +
                ", id='" + id + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.icon);
        dest.writeString(this.description);
        dest.writeString(this.main);
        dest.writeString(this.id);
    }

    protected Weather(Parcel in) {
        this.icon = in.readString();
        this.description = in.readString();
        this.main = in.readString();
        this.id = in.readString();
    }


    public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel source) {
            return new Weather(source);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };
}
