package com.example.weatherapp1.Data.Model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

class Weather : Parcelable {
    var icon: String? = null
    var description: String? = null
    var main: String? = null
    @PrimaryKey
    @ColumnInfo(name = "weather_id")
    lateinit var id: String

    constructor(icon: String, description: String, main: String, id: String) {
        this.icon = icon
        this.description = description
        this.main = main
        this.id = id
    }

    @Ignore
    constructor() {
    }

    override fun toString(): String {
        return "Weather{" +
                "icon='" + icon + '\''.toString() +
                ", description='" + description + '\''.toString() +
                ", main='" + main + '\''.toString() +
                ", id='" + id + '\''.toString() +
                '}'.toString()
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.icon)
        dest.writeString(this.description)
        dest.writeString(this.main)
        dest.writeString(this.id)
    }

    protected constructor(`in`: Parcel) {
        this.icon = `in`.readString()
        this.description = `in`.readString()
        this.main = `in`.readString()
        this.id = `in`.readString()
    }

    companion object CREATOR : Parcelable.Creator<Weather> {
        override fun createFromParcel(parcel: Parcel): Weather {
            return Weather(parcel)
        }

        override fun newArray(size: Int): Array<Weather?> {
            return arrayOfNulls(size)
        }
    }
}
