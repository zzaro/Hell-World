package com.example.porte.Util

import kotlin.math.min

object DateTransferUtil {
    fun changeStringToDate(stringDate: String): HashMap<String, String> {
        val year = stringDate.substring(0, 4)
        val month = stringDate.substring(4, 6)
        val day = stringDate.substring(6, 8)
        val hour = stringDate.substring(8, 10)
        val minute = stringDate.substring(10, 12)

        val result = hashMapOf(
            "year" to year,
            "month" to month,
            "day" to day,
            "hour" to hour,
            "minute" to minute
        )

        return result
    }
}