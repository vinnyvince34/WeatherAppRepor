package com.example.weatherapp1;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Observable;

@Entity(tableName = "WeatherContent")
public class DisplayClass extends Observable implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int uid = 0;
    private String name;
    private int visibility;
    @Embedded
    private Main main;
    @Embedded
    private Weather weather;
    @Embedded
    private Cloud cloud;
    @Embedded
    private Wind wind;

    public DisplayClass() {
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        this.cloud = cloud;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeString(this.name);
        dest.writeInt(this.visibility);
        dest.writeParcelable(this.main, flags);
        dest.writeParcelable(this.weather, flags);
        dest.writeParcelable(this.cloud, flags);
        dest.writeParcelable(this.wind, flags);
    }

    protected DisplayClass(Parcel in) {
        this.uid = in.readInt();
        this.name = in.readString();
        this.visibility = in.readInt();
        this.main = in.readParcelable(Main.class.getClassLoader());
        this.weather = in.readParcelable(Weather.class.getClassLoader());
        this.cloud = in.readParcelable(Cloud.class.getClassLoader());
        this.wind = in.readParcelable(Wind.class.getClassLoader());
    }

    public static final Creator<DisplayClass> CREATOR = new Creator<DisplayClass>() {
        @Override
        public DisplayClass createFromParcel(Parcel source) {
            return new DisplayClass(source);
        }

        @Override
        public DisplayClass[] newArray(int size) {
            return new DisplayClass[size];
        }
    };
}
