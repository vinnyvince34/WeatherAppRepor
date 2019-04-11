package com.example.weatherapp1.Data.Model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

import java.util.Observable

@Entity(tableName = "WeatherContent")
class DisplayClass : Observable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    var uid = 0
    var name: String? = null
    var visibility: Int = 0
    @Embedded
    var main: Main? = null
    @Embedded
    var weather: Weather? = null
    @Embedded
    var cloud: Cloud? = null
    @Embedded
    var wind: Wind? = null

    constructor() {}


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.uid)
        dest.writeString(this.name)
        dest.writeInt(this.visibility)
        dest.writeParcelable(this.main, flags)
        dest.writeParcelable(this.weather, flags)
        dest.writeParcelable(this.cloud, flags)
        dest.writeParcelable(this.wind, flags)
    }

    protected constructor(`in`: Parcel) {
        this.uid = `in`.readInt()
        this.name = `in`.readString()
        this.visibility = `in`.readInt()
        this.main = `in`.readParcelable(Main::class.java!!.getClassLoader())
        this.weather = `in`.readParcelable(Weather::class.java!!.getClassLoader())
        this.cloud = `in`.readParcelable(Cloud::class.java!!.getClassLoader())
        this.wind = `in`.readParcelable(Wind::class.java!!.getClassLoader())
    }

    companion object CREATOR : Parcelable.Creator<DisplayClass> {
        override fun createFromParcel(parcel: Parcel): DisplayClass {
            return DisplayClass(parcel)
        }

        override fun newArray(size: Int): Array<DisplayClass?> {
            return arrayOfNulls(size)
        }
    }
}
