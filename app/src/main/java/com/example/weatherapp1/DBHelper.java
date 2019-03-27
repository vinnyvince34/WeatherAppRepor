package com.example.weatherapp1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "WeatherContent.db";
    public static final String CONTACTS_TABLE_NAME = "Weather";
    public static final String CONTACTS_COLUMN_ADDRESS = "Country";
    private HashMap hp;
    private SQLiteDatabase db;
    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
        this.context = context;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        this.db = db;
        db.execSQL("CREATE TABLE IF NOT EXISTS Weather(ID VARCHAR NOT NULL ,Country VARCHAR NOT NULL, Wind_speed VARCHAR NOT NULL, Visibility VARCHAR NOT NULL, Temperature VARCHAR NOT NULL, Humidity VARCHAR NOT NULL, Pressure VARCHAR NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS Weather;");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Weather;");
        onCreate(db);
    }

    public boolean insert (int id, String county, String windSpeed, String visibility, String temperature, String humidity, String pressure) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("Country", county);
        contentValues.put("Wind_speed", windSpeed);
        contentValues.put("Visibility", visibility);
        contentValues.put("Temperature", temperature);
        contentValues.put("Humidity", humidity);
        contentValues.put("Pressure", pressure);
        db.insert("Weather", null, contentValues);
        db.close();
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where ID=" + id + "", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean update (int id, String county, String windSpeed, String visibility, String temperature, String humidity, String pressure) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Country", county);
        contentValues.put("Wind_speed", windSpeed);
        contentValues.put("Visibility", visibility);
        contentValues.put("Temperature", temperature);
        contentValues.put("Humidity", humidity);
        contentValues.put("Pressure", pressure);
        db.update("Weather", contentValues, "ID = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer delete (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Weather",
                "ID = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAll() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Weather", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_ADDRESS)));
            res.moveToNext();
        }
        return array_list;
    }
}

