package com.example.porte.ValueObject

data class ParkingLotVO (
    var terminal: String,
    var parkingLotName: String,
    var currentParking: Int,
    var totalParking: Int,
    var rate: Double
)

data class ParkingLotModel (
    var datetm: String,
    var floor: String,
    var parking: String,
    var parkingarea: String
)