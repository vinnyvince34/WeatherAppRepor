package com.example.weatherapp1.Data.Model

import android.arch.persistence.room.ColumnInfo
import android.os.Parcel
import android.os.Parcelable

class Cloud : ClassLoader, Parcelable {
    @ColumnInfo(name = "cloud_type")
    var type: String? = null
        private set

    constructor(type: String) {
        this.type = type
    }

    fun setAll(type: String) {
        this.type = type
    }

    override fun toString(): String {
        return "Cloud{" +
                "type='" + type + '\''.toString() +
                '}'.toString()
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.type)
    }

    protected constructor(`in`: Parcel) {
        this.type = `in`.readString()
    }

    companion object CREATOR : Parcelable.Creator<Cloud> {
        override fun createFromParcel(parcel: Parcel): Cloud {
            return Cloud(parcel)
        }

        override fun newArray(size: Int): Array<Cloud?> {
            return arrayOfNulls(size)
        }
    }
}
