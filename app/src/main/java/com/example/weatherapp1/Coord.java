package com.example.weatherapp1;

import android.os.Parcel;
import android.os.Parcelable;

public class Coord extends ClassLoader implements Parcelable {
    private String lon;
    private String lat;

    public Coord(String lon, String lat) {
        this.lon = lon;
        this.lat = lat;
    }

//    public Coord() {
//    }
//
    public String getLon () {
        return lon;
    }
    public void setLon (String lon) {
        this.lon = lon;
    }
    public String getLat () {
        return lat;
    }
    public void setLat (String lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lon);
        dest.writeString(this.lat);
    }

    protected Coord(Parcel in) {
        this.lon = in.readString();
        this.lat = in.readString();
    }

    public static final Parcelable.Creator<Coord> CREATOR = new Parcelable.Creator<Coord>() {
        @Override
        public Coord createFromParcel(Parcel source) {
            return new Coord(source);
        }

        @Override
        public Coord[] newArray(int size) {
            return new Coord[size];
        }
    };
}
