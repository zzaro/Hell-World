package com.example.porte.Shared

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserInfoEntity")
data class UserInfoEntity(
    @PrimaryKey var userID: String,
    var userName: String,
    var userImg: Image
)


@Entity(tableName = "UserFlightInfoEntity")
data class UserFlightInfoEntity(
    @PrimaryKey(autoGenerate = true)
    var flightInfoIdx: Long? = null,
    // 항공사
    var airline: String?,

    // 도착공항
    var airport: String?,

    // 공항코드
    var airportcode: String?,

    // 체크인 카운터
    var chkinrange: String?,

    // 변경시간
    var estimatedDateTime: String?,

    // 편명
    var flightId: String?,

    // 탑승구
    var gatenumber: String?,

    // 현황(출발, 결항, 지연, 탑승중, 마감예정, 탑승마감, 탑승준비)
    var remark: String?,

    // 예정시간
    var scheduleDateTime: String?,

    // 터미널구분
    var terminalid: String?
    /*
        P01: 제1 터미널
        P02: 탑승동
        P03: 제2 터미널
        C01 : 화물터미널 남측
        C02 : 화물터미널 북측
        C03 : 제2 화물터미널
     */
)
