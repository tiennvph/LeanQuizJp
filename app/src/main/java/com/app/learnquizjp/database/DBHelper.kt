package com.app.learnquizjp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.app.learnquizjp.base.Constants.Companion.CREATE_KOTOBA_TABLE
import com.app.learnquizjp.base.Constants.Companion.DELETE_KOTOBA_DATA
import com.app.learnquizjp.base.Constants.Companion.DELETE_KOTOBA_TABLE

class DBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    init {
        Log.i("CREATE_KOTOBA_TABLE",CREATE_KOTOBA_TABLE)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_KOTOBA_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DELETE_KOTOBA_TABLE)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db,oldVersion,newVersion)
    }

    fun onClearData(db: SQLiteDatabase){
        db.execSQL(DELETE_KOTOBA_DATA)
        //db.execSQL(RESET_SQUENCE_DATA)
    }

    companion object{
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "dictionary.db"
    }

}