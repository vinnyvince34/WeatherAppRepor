package com.example.weatherapp1.Data.Model;

import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

public class Wind extends ClassLoader implements Parcelable {
    private String speed;

    public Wind(String speed) {
        this.speed = speed;
    }

    @Ignore
    public Wind() {
    }

    public String getSpeed () {
        return speed;
    }
    public void setSpeed (String speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "speed='" + speed + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.speed);
    }

    protected Wind(Parcel in) {
        this.speed = in.readString();
    }

    public static final Parcelable.Creator<Wind> CREATOR = new Parcelable.Creator<Wind>() {
        @Override
        public Wind createFromParcel(Parcel source) {
            return new Wind(source);
        }

        @Override
        public Wind[] newArray(int size) {
            return new Wind[size];
        }
    };
}
