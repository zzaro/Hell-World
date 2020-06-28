package com.example.porte.Util

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper



    class DBHelper(context: Context?) :
        SQLiteAssetHelper(context, DBNAME, null, DBVERSION) {
        companion object {
            const val DBNAME = "airport.sqlite" //<<<< must be same as file name
            const val DBVERSION = 1
        }
    }
