package com.example.weatherapp1.Data.Model;

import android.arch.persistence.room.ColumnInfo;
import android.os.Parcel;
import android.os.Parcelable;

public class Cloud extends ClassLoader implements Parcelable {
    @ColumnInfo(name = "cloud_type")
    private String type;

    public Cloud(String type) {
        this.type = type;
    }

    public String getType () {
        return type;
    }
    public void setAll (String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Cloud{" +
                "type='" + type + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
    }

    protected Cloud(Parcel in) {
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Cloud> CREATOR = new Parcelable.Creator<Cloud>() {
        @Override
        public Cloud createFromParcel(Parcel source) {
            return new Cloud(source);
        }

        @Override
        public Cloud[] newArray(int size) {
            return new Cloud[size];
        }
    };
}
