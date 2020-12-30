package com.quick.asthkt.models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TwitDatabaseOffline(var context : Context?) : SQLiteOpenHelper(
    context,
    "db_twit",
    null,
    1
) {
    private lateinit var mQuery : String

    override fun onCreate(db: SQLiteDatabase?) {
        mQuery = "CREATE TABLE IF NOT EXISTS tb_twit(" +
                "_id INTEGER PRIMARY KEY," +
                "documentID TEXT," +
                "username TEXT," +
                "twit TEXT," +
                "mood TEXT," +
                "date TEXT,"+
                "real_date TEXT"+
                ")"
        db?.execSQL(mQuery)
//        makeTable()
    }

    fun makeTable(){
        val db = this.writableDatabase
        mQuery = "CREATE TABLE IF NOT EXISTS tb_twit(" +
                "_id INTEGER PRIMARY KEY," +
                "documentID TEXT," +
                "username TEXT," +
                "twit TEXT," +
                "mood TEXT," +
                "date TEXT,"+
                "real_date TEXT"+
                ")"
        db.execSQL(mQuery)
    }
    fun insertData(values: ContentValues) {
        val db = this.writableDatabase
        db.insert("tb_twit", null, values)
    }

    fun select(): Cursor? {
        val db = this.writableDatabase
        mQuery = "select * from tb_twit order by real_date desc"
        return db.rawQuery(mQuery, null)
    }

    fun delete() {
        val db = this.writableDatabase
        mQuery = "DELETE FROM tb_twit"
        db.execSQL(mQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}