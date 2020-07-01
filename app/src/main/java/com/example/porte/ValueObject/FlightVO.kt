package com.example.porte.ValueObject

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root
data class FlightResponse (
    @field: Element(name = "header", required = false)
    @param: Element(name = "header", required = false)
    var header: FlightHeader,

    @field: Element(name = "body", required = false)
    @param: Element(name = "body", required = false)
    var body: FlightBody
)

@Root(name = "header", strict = false)
data class FlightHeader (
    @field: Element(name = "resultCode", required = false)
    @param: Element(name = "resultCode", required = false)
    var resultCode: String,

    @field: Element(name = "resultMsg", required = false)
    @param: Element(name = "resultMsg", required = false)
    var resultMsg: String
)

@Root(name = "body", strict = false)
data class FlightBody (
    @field: Element(name = "items", required = false)
    @param: Element(name = "items", required = false)
    val items: FlightItems
)

@Root(name = "items", strict = false)
data class FlightItems (
    @field: ElementList(entry = "item", name = "item", inline = true, required = false)
    @param: ElementList(entry = "item", name = "item", inline = true, required = false)
    val itemList: List<FlightVO>
)

@Root(name = "item", strict = false)
data class FlightVO (
    // 항공사
    @field: Element(name = "airline", required = false)
    @param: Element(name = "airline", required = false)
    val airline: String?,

    // 도착공항
    @field: Element(name = "airport", required = false)
    @param: Element(name = "airport", required = false)
    val airport: String?,

    // 공항코드
    @field: Element(name = "airportcode", required = false)
    @param: Element(name = "airportcode", required = false)
    val airportcode: String?,

    // 체크인 카운터
    @field: Element(name = "chkinrange", required = false)
    @param: Element(name = "chkinrange", required = false)
    val chkinrange: String?,

    // 변경시간
    @field: Element(name = "estimatedDateTime", required = false)
    @param: Element(name = "estimatedDateTime", required = false)
    val estimatedDateTime: String?,

    // 편명
    @field: Element(name = "flightId", required = false)
    @param: Element(name = "flightId", required = false)
    val flightId: String?,

    // 탑승구
    @field: Element(name = "gatenumber", required = false)
    @param: Element(name = "gatenumber", required = false)
    val gatenumber: String?,

    // 현황(출발, 결항, 지연, 탑승중, 마감예정, 탑승마감, 탑승준비)
    @field: Element(name = "remark", required = false)
    @param: Element(name = "remark", required = false)
    val remark: String?,

    // 예정시간
    @field: Element(name = "scheduleDateTime", required = false)
    @param: Element(name = "scheduleDateTime", required = false)
    val scheduleDateTime: String?,

    // 터미널구분
    /*
        P01: 제1 터미널
        P02: 탑승동
        P03: 제2 터미널
        C01 : 화물터미널 남측
        C02 : 화물터미널 북측
        C03 : 제2 화물터미널
     */
    @field: Element(name = "terminalid", required = false)
    @param: Element(name = "terminalid", required = false)
    val terminalid: String?
)