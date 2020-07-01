package com.example.porte.Util

import android.util.Log

object QueryTextModifier {
    fun modifyText(query: String): String {
        var modifiedQuery = query?.toUpperCase()

        modifiedQuery = modifiedQuery.replace("\\s".toRegex(), "")


        modifiedQuery = modifiedQuery.replace("공항", "")

        Log.d("result", modifiedQuery)

        return modifiedQuery
    }
}