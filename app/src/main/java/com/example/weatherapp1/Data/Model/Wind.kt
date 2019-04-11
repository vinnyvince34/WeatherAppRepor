package com.example.weatherapp1.Data.Model

import android.arch.persistence.room.Ignore
import android.os.Parcel
import android.os.Parcelable

class Wind : ClassLoader, Parcelable {
    var speed: String? = null

    constructor(speed: String) {
        this.speed = speed
    }

    @Ignore
    constructor() {
    }

    override fun toString(): String {
        return "Wind{" +
                "speed='" + speed + '\''.toString() +
                '}'.toString()
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.speed)
    }

    protected constructor(`in`: Parcel) {
        this.speed = `in`.readString()
    }

    companion object CREATOR : Parcelable.Creator<Wind> {
        override fun createFromParcel(parcel: Parcel): Wind {
            return Wind(parcel)
        }

        override fun newArray(size: Int): Array<Wind?> {
            return arrayOfNulls(size)
        }
    }
}
