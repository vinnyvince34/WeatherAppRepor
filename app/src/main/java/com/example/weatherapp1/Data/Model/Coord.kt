package com.example.weatherapp1.Data.Model

import android.os.Parcel
import android.os.Parcelable

class Coord : ClassLoader, Parcelable {
    var lon: String? = null
    var lat: String? = null

    constructor(lon: String, lat: String) {
        this.lon = lon
        this.lat = lat
    }

    override fun toString(): String {
        return "Coord{" +
                "lon='" + lon + '\''.toString() +
                ", lat='" + lat + '\''.toString() +
                '}'.toString()
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.lon)
        dest.writeString(this.lat)
    }

    protected constructor(`in`: Parcel) {
        this.lon = `in`.readString()
        this.lat = `in`.readString()
    }

    companion object CREATOR : Parcelable.Creator<Coord> {
        override fun createFromParcel(parcel: Parcel): Coord {
            return Coord(parcel)
        }

        override fun newArray(size: Int): Array<Coord?> {
            return arrayOfNulls(size)
        }
    }
}
