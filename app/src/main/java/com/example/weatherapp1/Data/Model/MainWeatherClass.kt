package com.example.weatherapp1.Data.Model

import android.os.Parcel
import android.os.Parcelable

class MainWeatherClass : Parcelable {
    var weather: Array<Weather>? = null
    var cloud: Cloud? = null
    var wind: Wind? = null
    var coord: Coord? = null
    var sys: Sys? = null
    var main: Main? = null
    var base: String? = null
    var visibility: String? = null
    var dt: String? = null
    var id: String? = null
    var name: String? = null
    var httpCode: String? = null

    constructor() {}

    override fun toString(): String {
        return "MainWeatherClass{" +
                "weather=" + weather +
                ", cloud=" + cloud +
                ", wind=" + wind +
                ", coord=" + coord +
                ", sys=" + sys +
                ", main=" + main +
                ", base='" + base + '\''.toString() +
                ", visibility='" + visibility + '\''.toString() +
                ", dt='" + dt + '\''.toString() +
                ", id='" + id + '\''.toString() +
                ", name='" + name + '\''.toString() +
                ", httpCode='" + httpCode + '\''.toString() +
                '}'.toString()
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeParcelableArray(this.weather, flags)
        dest.writeParcelable(this.cloud, flags)
        dest.writeParcelable(this.wind, flags)
        dest.writeParcelable(this.coord, flags)
        dest.writeParcelable(this.sys, flags)
        dest.writeParcelable(this.main, flags)
        dest.writeString(this.base)
        dest.writeString(this.visibility)
        dest.writeString(this.dt)
        dest.writeString(this.id)
        dest.writeString(this.name)
        dest.writeString(this.httpCode)
    }

    protected constructor(`in`: Parcel) {
        this.weather = `in`.readParcelableArray(Weather::class.java!!.getClassLoader()) as Array<Weather>
        this.cloud = `in`.readParcelable(Cloud::class.java!!.getClassLoader())
        this.wind = `in`.readParcelable(Wind::class.java!!.getClassLoader())
        this.coord = `in`.readParcelable(Coord::class.java!!.getClassLoader())
        this.sys = `in`.readParcelable(Sys::class.java!!.getClassLoader())
        this.main = `in`.readParcelable(Main::class.java!!.getClassLoader())
        this.base = `in`.readString()
        this.visibility = `in`.readString()
        this.dt = `in`.readString()
        this.id = `in`.readString()
        this.name = `in`.readString()
        this.httpCode = `in`.readString()
    }

    companion object CREATOR : Parcelable.Creator<MainWeatherClass> {
        override fun createFromParcel(parcel: Parcel): MainWeatherClass {
            return MainWeatherClass(parcel)
        }

        override fun newArray(size: Int): Array<MainWeatherClass?> {
            return arrayOfNulls(size)
        }
    }
}

