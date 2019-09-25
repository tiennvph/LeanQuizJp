package com.app.learnquizjp.dao

import com.app.learnquizjp.base.Constants
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.app.learnquizjp.database.DBHelper
import com.app.learnquizjp.model.Kotoba


class KotobaDAO(context : Context) : Constants(){

    private var dbHelper: DBHelper? = null

    init {
        dbHelper = DBHelper(context)
    }

    fun initKotobaData(){
        var db : SQLiteDatabase = dbHelper!!.writableDatabase
        dbHelper!!.onClearData(db)
        insertKotoba(Kotoba("さびしい","寂しい","buồn bã,nhàn rỗi",null))
        insertKotoba(Kotoba("たのしい","楽しい","dí dỏm,vui sướng",null))
        insertKotoba(Kotoba("すばらしい","素晴らしい","tuyệt vời,tráng lệ",null))
        insertKotoba(Kotoba("めずらしい","珍しい","hiếm có,ly kỳ",null))
        insertKotoba(Kotoba("かなしい","悲しい","phiền não,rầu rĩ",null))
        insertKotoba(Kotoba("おいしい","美味しい","ngon lành,ngon miệng",null))
        insertKotoba(Kotoba("いち","一","một",null))
        insertKotoba(Kotoba("に","二","hai",null))
        insertKotoba(Kotoba("さん","三","ba",null))
        insertKotoba(Kotoba("よん","四","bốn",null))
        insertKotoba(Kotoba("ご","五","năm",null))
        insertKotoba(Kotoba("ろく","六","sáu",null))
        insertKotoba(Kotoba("なな","七","bảy",null))
        insertKotoba(Kotoba("はち","八","tám",null))
        insertKotoba(Kotoba("きゅう","九","chín",null))
        insertKotoba(Kotoba("じゅう","十","mười",null))
        insertKotoba(Kotoba("はる","春","mùa xuân",null))
        insertKotoba(Kotoba("なつ","夏","mùa hạ",null))
        insertKotoba(Kotoba("あき","秋","mùa thu",null))
        insertKotoba(Kotoba("ふゆ","冬","mùa đông",null))
    }

    private fun insertKotoba(kotoba : Kotoba): Long {

        var result: Long = -1

        var cv = ContentValues()
        cv.put(KOTOBA_HIRAGANA, kotoba.KOTOBA_HIRAGANA)
        cv.put(KOTOBA_KANJI, kotoba.KOTOBA_KANJI)
        cv.put(KOTOBA_DESCRIPTION, kotoba.KOTOBA_DESCRIPTION)
        cv.put(KOTOBA_EXAMPLE, kotoba.KOTOBA_EXAMPLE)

        val sqLiteDatabase = dbHelper!!.writableDatabase
        result = sqLiteDatabase.insert(KOTOBA_TABLE, null, cv)
        sqLiteDatabase.close()

        return result

    }

    fun updateKotoba(id : Int): Long {

        var result: Long
        var cv = ContentValues()
        cv.put(KOTOBA_HIRAGANA, KOTOBA_HIRAGANA)
        cv.put(KOTOBA_KANJI, KOTOBA_KANJI)
        cv.put(KOTOBA_DESCRIPTION, KOTOBA_DESCRIPTION)
        cv.put(KOTOBA_EXAMPLE, KOTOBA_EXAMPLE)

        val sqLiteDatabase = dbHelper!!.writableDatabase
        result = sqLiteDatabase.update(KOTOBA_TABLE, cv, "$KOTOBA_ID = ?", arrayOf(id.toString())).toLong()
        sqLiteDatabase.close()

        return result

    }

    fun deleteAllKotoba(){


    }

    fun deleteKotoba(id : Int): Long {

        var result: Long = -1

        val sqLiteDatabase = dbHelper!!.writableDatabase
        result = sqLiteDatabase.delete(KOTOBA_TABLE, "$KOTOBA_ID = ?", arrayOf(id.toString())).toLong()
        sqLiteDatabase.close()

        return result

    }

    fun getAllKotoba(): MutableList<Kotoba> {

        val kotobas : MutableList<Kotoba> = mutableListOf()

        val QUERY = "SELECT * FROM $KOTOBA_TABLE"
        val sqLiteDatabase = dbHelper!!.writableDatabase
        var cursor = sqLiteDatabase.rawQuery(QUERY, null)

        if (cursor != null) {
            if (cursor.count > 0) {
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    var hiragana = cursor.getString(cursor.getColumnIndex(KOTOBA_HIRAGANA))
                    var kanji = cursor.getString(cursor.getColumnIndex(KOTOBA_KANJI))
                    var description = cursor.getString(cursor.getColumnIndex(KOTOBA_DESCRIPTION))
                    var example = cursor.getString(cursor.getColumnIndex(KOTOBA_EXAMPLE))

                    var kotoba = Kotoba(hiragana, kanji, description, example)

                    kotobas.add(kotoba)
                    cursor.moveToNext()
                }
                cursor.close()
                sqLiteDatabase.close()
            }
        }
        return kotobas
    }

    fun getSearchKotoba(search_key : String): Kotoba? {

        var kotoba : Kotoba? = null
        val db = dbHelper!!.readableDatabase
        var cursor = db.query(
            KOTOBA_TABLE,
            arrayOf(KOTOBA_HIRAGANA, KOTOBA_KANJI, KOTOBA_DESCRIPTION, KOTOBA_EXAMPLE),
            "$KOTOBA_HIRAGANA = ? ",
            arrayOf(search_key),
            null,
            null,
            null,
            null
        )

        // moveToFirst : kiem tra xem cursor co chua du lieu khong, ham nay tra ve gia tri la true or false
        if (cursor != null && cursor.moveToFirst()) {

            val hiragana = cursor.getString(cursor.getColumnIndex(KOTOBA_HIRAGANA))

            val kanji = cursor.getString(cursor.getColumnIndex(KOTOBA_KANJI))

            val description = cursor.getString(cursor.getColumnIndex(KOTOBA_DESCRIPTION))

            val example = cursor.getString(cursor.getColumnIndex(KOTOBA_EXAMPLE))

            // khoi tao kotoba voi cac gia tri lay duoc
            kotoba = Kotoba(hiragana, kanji, description, example)
        }
        cursor!!.close()
        return kotoba
    }

    fun getSearchKotoba(id : Int): Kotoba? {

        var kotoba : Kotoba? = null
        val db = dbHelper!!.readableDatabase
        var cursor = db.query(
            KOTOBA_TABLE,
            arrayOf(KOTOBA_HIRAGANA, KOTOBA_KANJI, KOTOBA_DESCRIPTION, KOTOBA_EXAMPLE),
            "$KOTOBA_ID = ? ",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )

        // moveToFirst : kiem tra xem cursor co chua du lieu khong, ham nay tra ve gia tri la true or false
        if (cursor != null && cursor.moveToFirst()) {

            val hiragana = cursor.getString(cursor.getColumnIndex(KOTOBA_HIRAGANA))

            val kanji = cursor.getString(cursor.getColumnIndex(KOTOBA_KANJI))

            val description = cursor.getString(cursor.getColumnIndex(KOTOBA_DESCRIPTION))

            val example = cursor.getString(cursor.getColumnIndex(KOTOBA_EXAMPLE))

            // khoi tao kotoba voi cac gia tri lay duoc
            kotoba = Kotoba(hiragana, kanji, description, example)
        }
        cursor!!.close()
        return kotoba!!
    }

}