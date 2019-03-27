package com.example.weatherapp1;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Observable;

@Entity(tableName = "WeatherContent")
public class MainWeatherClass extends Observable implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int uid = 0;
    @SerializedName("weather")
    @Expose(deserialize = true)
    @Embedded
    private Weather weather;
    @Embedded
    private Cloud cloud;
    @Embedded
    private Wind wind;
    @Embedded
    private Coord coord;
    @Embedded
    private System sys;
    @Embedded
    private Main main;
    private String base;
    private String visibility;
    private String dt;
    @ColumnInfo(name = "main_id")
    private String id;
    private String name;
    private String httpCode;

    public MainWeatherClass() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public System getSys() {
        return sys;
    }

    public void setSys(System sys) {
        this.sys = sys;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(String httpCode) {
        this.httpCode = httpCode;
    }

    @Override
    public String toString() {
        return "MainWeatherClass{" +
                "weather=" + weather +
                ", cloud=" + cloud +
                ", wind=" + wind +
                ", coord=" + coord +
                ", sys=" + sys +
                ", main=" + main +
                ", base='" + base + '\'' +
                ", visibility='" + visibility + '\'' +
                ", dt='" + dt + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", httpCode='" + httpCode + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeParcelable(this.weather, flags);
        dest.writeParcelable(this.cloud, flags);
        dest.writeParcelable(this.wind, flags);
        dest.writeParcelable(this.coord, flags);
        dest.writeParcelable(this.sys, flags);
        dest.writeParcelable(this.main, flags);
        dest.writeString(this.base);
        dest.writeString(this.visibility);
        dest.writeString(this.dt);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.httpCode);
    }

    protected MainWeatherClass(Parcel in) {
        this.uid = in.readInt();
        this.weather = in.readParcelable(Weather.class.getClassLoader());
        this.cloud = in.readParcelable(Cloud.class.getClassLoader());
        this.wind = in.readParcelable(Wind.class.getClassLoader());
        this.coord = in.readParcelable(Coord.class.getClassLoader());
        this.sys = in.readParcelable(System.class.getClassLoader());
        this.main = in.readParcelable(Main.class.getClassLoader());
        this.base = in.readString();
        this.visibility = in.readString();
        this.dt = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.httpCode = in.readString();
    }

    public static final Creator<MainWeatherClass> CREATOR = new Creator<MainWeatherClass>() {
        @Override
        public MainWeatherClass createFromParcel(Parcel source) {
            return new MainWeatherClass(source);
        }

        @Override
        public MainWeatherClass[] newArray(int size) {
            return new MainWeatherClass[size];
        }
    };
}

