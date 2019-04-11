package com.example.weatherapp1.Data.Model

import android.os.Parcel
import android.os.Parcelable

class Main : ClassLoader, Parcelable {
    var temp: String? = null
    var temp_min: String? = null
    var humidity: String? = null
    var pressure: String? = null
    var temp_max: String? = null

    constructor(temp: String, temp_min: String, humidity: String, pressure: String, temp_max: String) {
        this.temp = temp
        this.temp_min = temp_min
        this.humidity = humidity
        this.pressure = pressure
        this.temp_max = temp_max
    }

    override fun toString(): String {
        return "Other{" +
                "temp='" + temp + '\''.toString() +
                ", temp_min='" + temp_min + '\''.toString() +
                ", humidity='" + humidity + '\''.toString() +
                ", pressure='" + pressure + '\''.toString() +
                ", temp_max='" + temp_max + '\''.toString() +
                '}'.toString()
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.temp)
        dest.writeString(this.temp_min)
        dest.writeString(this.humidity)
        dest.writeString(this.pressure)
        dest.writeString(this.temp_max)
    }

    protected constructor(`in`: Parcel) {
        this.temp = `in`.readString()
        this.temp_min = `in`.readString()
        this.humidity = `in`.readString()
        this.pressure = `in`.readString()
        this.temp_max = `in`.readString()
    }

    companion object CREATOR : Parcelable.Creator<Main> {
        override fun createFromParcel(parcel: Parcel): Main {
            return Main(parcel)
        }

        override fun newArray(size: Int): Array<Main?> {
            return arrayOfNulls(size)
        }
    }
}
