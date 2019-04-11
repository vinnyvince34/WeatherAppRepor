package com.example.weatherapp1.Misc

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import com.example.weatherapp1.Misc.DBHelper.Companion.CONTACTS_TABLE_NAME

import java.util.ArrayList
import java.util.HashMap

class DBHelper(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    private val hp: HashMap<*, *>? = null
    private var db: SQLiteDatabase? = null

    //hp = new HashMap();
    val all: ArrayList<String>
        get() {
            val array_list = ArrayList<String>()
            val db = this.readableDatabase
            val res = db.rawQuery("select * from Weather", null)
            res.moveToFirst()

            while (res.isAfterLast == false) {
                array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_ADDRESS)))
                res.moveToNext()
            }
            return array_list
        }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.setForeignKeyConstraintsEnabled(true)
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // TODO Auto-generated method stub
        this.db = db
        db.execSQL("CREATE TABLE IF NOT EXISTS Weather(ID VARCHAR NOT NULL ,Country VARCHAR NOT NULL, Wind_speed VARCHAR NOT NULL, Visibility VARCHAR NOT NULL, Temperature VARCHAR NOT NULL, Humidity VARCHAR NOT NULL, Pressure VARCHAR NOT NULL);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS Weather;")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Weather;")
        onCreate(db)
    }

    fun insert(id: Int, county: String, windSpeed: String, visibility: String, temperature: String, humidity: String, pressure: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("ID", id)
        contentValues.put("Country", county)
        contentValues.put("Wind_speed", windSpeed)
        contentValues.put("Visibility", visibility)
        contentValues.put("Temperature", temperature)
        contentValues.put("Humidity", humidity)
        contentValues.put("Pressure", pressure)
        db.insert("Weather", null, contentValues)
        db.close()
        return true
    }

    fun getData(id: Int): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("select * from contacts where ID=$id", null)
    }

    fun numberOfRows(): Int {
        val db = this.readableDatabase
        return DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME).toInt()
    }

    fun update(id: Int, county: String, windSpeed: String, visibility: String, temperature: String, humidity: String, pressure: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("Country", county)
        contentValues.put("Wind_speed", windSpeed)
        contentValues.put("Visibility", visibility)
        contentValues.put("Temperature", temperature)
        contentValues.put("Humidity", humidity)
        contentValues.put("Pressure", pressure)
        db.update("Weather", contentValues, "ID = ? ", arrayOf(Integer.toString(id)))
        return true
    }

    fun delete(id: Int): Int? {
        val db = this.writableDatabase
        return db.delete("Weather",
                "ID = ? ",
                arrayOf(Integer.toString(id)))
    }

    companion object {
        val DATABASE_NAME = "WeatherContent.db"
        val CONTACTS_TABLE_NAME = "Weather"
        val CONTACTS_COLUMN_ADDRESS = "Country"
    }
}

