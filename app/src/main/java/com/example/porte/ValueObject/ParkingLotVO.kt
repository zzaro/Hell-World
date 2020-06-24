package com.example.porte.ValueObject

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root
data class ParkingLotResponse (
    @field: Element (name = "header", required = false)
    @param: Element (name = "header", required = false)
    var header: ParkingLotHeader,

    @field: Element (name = "body", required = false)
    @param: Element (name = "body", required = false)
    var body: ParkingLotBody
)

@Root(name = "header", strict = false)
data class ParkingLotHeader (
    @field: Element (name = "resultCode", required = false)
    @param: Element (name = "resultCode", required = false)
    var resultCode: String,

    @field: Element (name = "resultMsg", required = false)
    @param: Element (name = "resultMsg", required = false)
    var resultMsg: String
)

@Root(name = "body", strict = false)
data class ParkingLotBody (
    @field: Element (name = "items", required = false)
    @param: Element (name = "items", required = false)
    var parkingItems: ParkingItems,

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
data class ParkingItems (
    @field: ElementList (entry = "item", name = "item", inline = true, required = false)
    @param: ElementList (entry = "item", name = "item", inline = true, required = false)
    val itemList: List<ParkingLotVO>
)

@Root(name = "item", strict = false)
data class ParkingLotVO (
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