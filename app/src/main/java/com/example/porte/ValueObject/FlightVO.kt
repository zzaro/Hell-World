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
    @field: Element(name = "airline", required = false)
    @param: Element(name = "airline", required = false)
    val airline: String?,

    @field: Element(name = "airport", required = false)
    @param: Element(name = "airport", required = false)
    val airport: String?,

    @field: Element(name = "airportcode", required = false)
    @param: Element(name = "airportcode", required = false)
    val airportcode: String?,

    @field: Element(name = "chkinrange", required = false)
    @param: Element(name = "chkinrange", required = false)
    val chkinrange: String?,

    @field: Element(name = "estimatedDateTime", required = false)
    @param: Element(name = "estimatedDateTime", required = false)
    val estimatedDateTime: String?,

    @field: Element(name = "flightId", required = false)
    @param: Element(name = "flightId", required = false)
    val flightId: String?,

    @field: Element(name = "gatenumber", required = false)
    @param: Element(name = "gatenumber", required = false)
    val gatenumber: String?,

    @field: Element(name = "remark", required = false)
    @param: Element(name = "remark", required = false)
    val remark: String?,

    @field: Element(name = "scheduleDateTime", required = false)
    @param: Element(name = "scheduleDateTime", required = false)
    val scheduleDateTime: String?,

    @field: Element(name = "terminalid", required = false)
    @param: Element(name = "terminalid", required = false)
    val terminalid: String?
)