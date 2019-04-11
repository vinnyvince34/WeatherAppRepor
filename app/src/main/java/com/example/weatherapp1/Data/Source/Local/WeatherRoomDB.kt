package com.example.weatherapp1.Data.Source.Local

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask

import com.example.weatherapp1.Data.Model.DisplayClass

@Database(entities = [DisplayClass::class], version = 1, exportSchema = false)
abstract class WeatherRoomDB : RoomDatabase() {
    abstract fun weatherDAO(): WeatherDAO

    private class PopulateDbAsync internal constructor(db: WeatherRoomDB) : AsyncTask<Void, Void, Void>() {

        private val mDao: WeatherDAO

        init {
            mDao = db.weatherDAO()
        }

        override fun doInBackground(vararg params: Void): Void? {
            return null
        }
    }

    companion object {
        @Volatile
        var INSTANCE: WeatherRoomDB? = null

        fun getDB(context: Context): WeatherRoomDB? {
            if (INSTANCE == null) {
                synchronized(WeatherRoomDB::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder<WeatherRoomDB>(context.applicationContext,
                                WeatherRoomDB::class.java!!, "WeatherContent")
                                .fallbackToDestructiveMigration()
                                .addCallback(sRoomDatabaseCallback)
                                .build()
                    }
                }
            }
            return INSTANCE
        }

        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE!!).execute()
            }
        }
    }
}
