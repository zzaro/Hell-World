package com.example.porte.ValueObject

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root
data class DepartureResponse (
    @field: Element (name = "header", required = false)
    @param: Element (name = "header", required = false)
    var header: DepartureHeader,

    @field: Element (name = "body", required = false)
    @param: Element (name = "body", required = false)
    var body: DepartureBody
)

@Root(name = "header", strict = false)
data class DepartureHeader (
    @field: Element (name = "resultCode", required = false)
    @param: Element (name = "resultCode", required = false)
    var resultCode: String,

    @field: Element (name = "resultMsg", required = false)
    @param: Element (name = "resultMsg", required = false)
    var resultMsg: String
)

@Root(name = "body", strict = false)
data class DepartureBody (
    @field: Element (name = "items", required = false)
    @param: Element (name = "items", required = false)
    val departureitems: DepartureItems
)

@Root(name = "items", strict = false)
data class DepartureItems (
    @field: Element ( name = "item",  required = false)
    @param: Element ( name = "item",  required = false)
    val itemList: DepartureVO
)

@Root(name = "item", strict = false)
data class DepartureVO (
    //지역구분
    @field: Element(name = "areadiv", required = false)
    @param: Element(name = "areadiv", required = false)
    val areadiv: String?,

    //혼잡일자
    @field: Element(name = "cgtdt", required = false)
    @param: Element(name = "cgtdt", required = false)
    val cgtdt: String?,

    //혼잡일시
    @field: Element(name = "cgthm", required = false)
    @param: Element(name = "cgthm", required = false)
    val cgthm: String?,

    //gate 1 출국장 혼잡도
    @field: Element(name = "gate1", required = false)
    @param: Element(name = "gate1", required = false)
    val gate1: String?,

    //gate 2 출국장 혼잡도
    @field: Element(name = "gate2", required = false)
    @param: Element(name = "gate2", required = false)
    val gate2: String?,

    //gate 3 출국장 혼잡도
    @field: Element(name = "gate3", required = false)
    @param: Element(name = "gate3", required = false)
    val gate3: String?,

    //gate 4 출국장 혼잡도
    @field: Element(name = "gate4", required = false)
    @param: Element(name = "gate4", required = false)
    val gate4: String?,

    //gate 5 출국장 혼잡도
    @field: Element(name = "gate5", required = false)
    @param: Element(name = "gate5", required = false)
    val gate5: String?,

    //gate 6 출국장 혼잡도
    @field: Element(name = "gate6", required = false)
    @param: Element(name = "gate6", required = false)
    val gate6: String?,

    //gate 1 출국장대기인수
    @field: Element(name = "gateinfo1", required = false)
    @param: Element(name = "gateinfo1", required = false)
    val gateinfo1: String?,

    //gate 2 출국장대기인수
    @field: Element(name = "gateinfo2", required = false)
    @param: Element(name = "gateinfo2", required = false)
    val gateinfo2: String?,

    //gate 3 출국장대기인수
    @field: Element(name = "gateinfo3", required = false)
    @param: Element(name = "gateinfo3", required = false)
    val gateinfo3: String?,

    //gate 4 출국장대기인수
    @field: Element(name = "gateinfo4", required = false)
    @param: Element(name = "gateinfo4", required = false)
    val gateinfo4: String?,

    //gate 5 출국장대기인수
    @field: Element(name = "gateinfo5", required = false)
    @param: Element(name = "gateinfo5", required = false)
    val gateinfo5: String?,

    //gate 6 출국장대기인수
    @field: Element(name = "gateinfo6", required = false)
    @param: Element(name = "gateinfo6", required = false)
    val gateinfo6: String?,

    //터미널구분
    @field: Element(name = "terno", required = false)
    @param: Element(name = "terno", required = false)
    val terno: String?
)
