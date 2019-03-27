package com.example.weatherapp1;

import android.os.Parcel;
import android.os.Parcelable;

class Main extends ClassLoader implements Parcelable {
    private String temp;
    private String temp_min;
    private String humidity;
    private String pressure;
    private String temp_max;

    public Main(String temp, String temp_min, String humidity, String pressure, String temp_max) {
        this.temp = temp;
        this.temp_min = temp_min;
        this.humidity = humidity;
        this.pressure = pressure;
        this.temp_max = temp_max;
    }

//    public Other() {
//    }

    public String getTemp () {
        return temp;
    }
    public void setTemp (String temp) {
        this.temp = temp;
    }
    public String getTemp_min () {
        return temp_min;
    }
    public void setTemp_min (String temp_min) {
        this.temp_min = temp_min;
    }
    public String getHumidity () {
        return humidity;
    }
    public void setHumidity (String humidity) {
        this.humidity = humidity;
    }
    public String getPressure () {
        return pressure;
    }
    public void setPressure (String pressure) {
        this.pressure = pressure;
    }
    public String getTemp_max () {
        return temp_max;
    }
    public void setTemp_max (String temp_max) {
        this.temp_max = temp_max;
    }

    @Override
    public String toString() {
        return "Other{" +
                "temp='" + temp + '\'' +
                ", temp_min='" + temp_min + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pressure='" + pressure + '\'' +
                ", temp_max='" + temp_max + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.temp);
        dest.writeString(this.temp_min);
        dest.writeString(this.humidity);
        dest.writeString(this.pressure);
        dest.writeString(this.temp_max);
    }

    protected Main(Parcel in) {
        this.temp = in.readString();
        this.temp_min = in.readString();
        this.humidity = in.readString();
        this.pressure = in.readString();
        this.temp_max = in.readString();
    }

    public static final Parcelable.Creator<Main> CREATOR = new Parcelable.Creator<Main>() {
        @Override
        public Main createFromParcel(Parcel source) {
            return new Main(source);
        }

        @Override
        public Main[] newArray(int size) {
            return new Main[size];
        }
    };
}
