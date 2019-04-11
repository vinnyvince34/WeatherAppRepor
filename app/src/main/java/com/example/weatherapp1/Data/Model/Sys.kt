package com.example.weatherapp1.Data.Model

import android.arch.persistence.room.ColumnInfo
import android.os.Parcel
import android.os.Parcelable

class Sys : ClassLoader, Parcelable {
    //    public Sys() {
    //    }

    var country: String? = null
    var sunrise: String? = null
    var sunset: String? = null
    @ColumnInfo(name = "system_id")
    var id: String? = null
    @ColumnInfo(name = "system_type")
    var type: String? = null
    var message: String? = null

    constructor(country: String, sunrise: String, sunset: String, id: String, type: String, message: String) {
        this.country = country
        this.sunrise = sunrise
        this.sunset = sunset
        this.id = id
        this.type = type
        this.message = message
    }

    override fun toString(): String {
        return "Sys{" +
                "country='" + country + '\''.toString() +
                ", sunrise='" + sunrise + '\''.toString() +
                ", sunset='" + sunset + '\''.toString() +
                ", id='" + id + '\''.toString() +
                ", type='" + type + '\''.toString() +
                ", message='" + message + '\''.toString() +
                '}'.toString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.country)
        dest.writeString(this.sunrise)
        dest.writeString(this.sunset)
        dest.writeString(this.id)
        dest.writeString(this.type)
        dest.writeString(this.message)
    }

    protected constructor(`in`: Parcel) {
        this.country = `in`.readString()
        this.sunrise = `in`.readString()
        this.sunset = `in`.readString()
        this.id = `in`.readString()
        this.type = `in`.readString()
        this.message = `in`.readString()
    }

    companion object CREATOR : Parcelable.Creator<Sys> {
        override fun createFromParcel(parcel: Parcel): Sys {
            return Sys(parcel)
        }

        override fun newArray(size: Int): Array<Sys?> {
            return arrayOfNulls(size)
        }
    }
}
