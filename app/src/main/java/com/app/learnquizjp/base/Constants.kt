package com.app.learnquizjp.base

open class Constants {

    companion object {
        const val isDEBUG = true
        //Table name
        const val KOTOBA_TABLE = "kotoba"
        //Table Column
        const val KOTOBA_ID = "id"
        const val KOTOBA_HIRAGANA = "hiragana"
        const val KOTOBA_KANJI = "kanji"
        const val KOTOBA_DESCRIPTION = "description"
        const val KOTOBA_EXAMPLE = "example"
        //Query create kotoba(id int primary key auto increment,hiragana nvarchar(50) not null, kanji nvarchar(50), description nvarchar(50) not null,example nvarchar(255))
        internal const val CREATE_KOTOBA_TABLE = "CREATE TABLE $KOTOBA_TABLE (" +
                    "$KOTOBA_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$KOTOBA_HIRAGANA NVARCHAR(50) NOT NULL," +
                    "$KOTOBA_KANJI NVARCHAR(50)," +
                    "$KOTOBA_DESCRIPTION NVARCHAR(50) NOT NULL," +
                    "$KOTOBA_EXAMPLE NVARCHAR(255)" +
                    ")"

        internal const val DELETE_KOTOBA_TABLE = "DROP TABLE IF EXISTS $KOTOBA_TABLE"

        internal const val DELETE_KOTOBA_DATA = "DELETE FROM $KOTOBA_TABLE"

        //internal const val RESET_SQUENCE_DATA = "ALTER TABLE $KOTOBA_TABLE AUTO_INCREMENT = SELECT MAX($KOTOBA_ID) FROM $KOTOBA_TABLE"

    }

}