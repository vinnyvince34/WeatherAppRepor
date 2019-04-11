package com.example.weatherapp1.Data.Source.Local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;

import com.example.weatherapp1.Data.Model.DisplayClass;

@Database(entities = {DisplayClass.class}, version = 1, exportSchema = false)
public abstract class WeatherRoomDB extends RoomDatabase {
    public abstract WeatherDAO weatherDAO();
    public static volatile WeatherRoomDB INSTANCE;

    public static WeatherRoomDB getDB(final Context context) {
        if(INSTANCE == null) {
            synchronized (WeatherRoomDB.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WeatherRoomDB.class, "WeatherContent")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WeatherDAO mDao;

        PopulateDbAsync(WeatherRoomDB db) {
            mDao = db.weatherDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }
}
