package com.example.weatherapp1;

import android.arch.persistence.room.ColumnInfo;
import android.os.Parcel;
import android.os.Parcelable;

public class System extends ClassLoader implements Parcelable {
    private String country;
    private String sunrise;
    private String sunset;
    @ColumnInfo(name = "system_id")
    private String id;
    @ColumnInfo(name = "system_type")
    private String type;
    private String message;

    public System(String country, String sunrise, String sunset, String id, String type, String message) {
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.id = id;
        this.type = type;
        this.message = message;
    }

//    public System() {
//    }

    public String getCountry () {
        return country;
    }
    public void setCountry (String country) {
        this.country = country;
    }
    public String getSunrise () {
        return sunrise;
    }
    public void setSunrise (String sunrise) {
        this.sunrise = sunrise;
    }
    public String getSunset () {
        return sunset;
    }
    public void setSunset (String sunset) {
        this.sunset = sunset;
    }
    public String getId () {
        return id;
    }
    public void setId (String id) {
        this.id = id;
    }
    public String getType () {
        return type;
    }
    public void setType (String type) {
        this.type = type;
    }
    public String getMessage () {
        return message;
    }
    public void setMessage (String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "System{" +
                "country='" + country + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.country);
        dest.writeString(this.sunrise);
        dest.writeString(this.sunset);
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeString(this.message);
    }

    protected System(Parcel in) {
        this.country = in.readString();
        this.sunrise = in.readString();
        this.sunset = in.readString();
        this.id = in.readString();
        this.type = in.readString();
        this.message = in.readString();
    }

    public static final Parcelable.Creator<System> CREATOR = new Parcelable.Creator<System>() {
        @Override
        public System createFromParcel(Parcel source) {
            return new System(source);
        }

        @Override
        public System[] newArray(int size) {
            return new System[size];
        }
    };
}
