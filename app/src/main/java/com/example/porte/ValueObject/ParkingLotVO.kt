package com.example.porte.ValueObject

import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "body", strict = true)
data class ParkingLotResponse (
    @field: Element (name = "header", required = false)
    @param: Element (name = "header", required = false)
    var header: Header
//
//    @field: Element (name = "body", required = false)
//    @param: Element (name = "body", required = false)
//    var body: Body,
//
//    @field: Element (name = "items", required = false)
//    @param: Element (name = "items", required = false)
//    var items: Items,
//
//    @field: Element (name = "numOfRows", required = false)
//    @param: Element (name = "numOfRows", required = false)
//    var numOfRows: String,
//
//    @field: Element (name = "pageNo", required = false)
//    @param: Element (name = "pageNo", required = false)
//    var pageNo: String,
//
//    @field: Element (name = "totalCount", required = false)
//    @param: Element (name = "totalCount", required = false)
//    var totalCount: String


)

@Root(name = "header", strict = false)
data class Header (
    @field: Element (name = "resultCode", required = false)
    @param: Element (name = "resultCode", required = false)
    var resultCode: String,

    @field: Element (name = "resultMsg", required = false)
    @param: Element (name = "resultMsg", required = false)
    var resultMsg: String
)

@Root(name = "body", strict = false)
data class Body (
    @field: Element (name = "items", required = false)
    @param: Element (name = "items", required = false)
    var items: Items,

    @field: Element (name = "numOfRows", required = false)
    @param: Element (name = "numOfRows", required = false)
    var numOfRows: String,

    @field: Element (name = "pageNo", required = false)
    @param: Element (name = "pageNo", required = false)
    var pageNo: String,

    @field: Element (name = "totalCount", required = false)
    @param: Element (name = "totalCount", required = false)
    var totalCount: String
)

@Root(name = "items", strict = false)
data class Items (
    @field: ElementList (entry = "item", name = "item", inline = true, required = false)
    @param: ElementList (entry = "item", name = "item", inline = true, required = false)
    val itemList: List<ParkingLotDetailResponse>
)

@Root(name = "item", strict = false)
data class ParkingLotDetailResponse (
    @field:Element (name = "datetm")
    @param:Element (name = "datetm")
    var datetm: String,

    @field:Element (name = "floor")
    @param:Element (name = "floor")
    var floor: String,

    @field:Element (name = "parking")
    @param:Element (name = "parking")
    var parking: String,

    @field:Element (name = "parkingarea")
    @param:Element (name = "parkingarea")
    var parkingarea: String
)