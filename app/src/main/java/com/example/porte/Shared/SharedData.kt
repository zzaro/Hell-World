package com.example.porte.Shared

import com.example.porte.ValueObject.DepartureVO
import com.example.porte.ui.parkingLotInfo.AllParkingType

object SharedData {
    var sharedParkingData: AllParkingType? = null
//    var sharedParkingLastUpdate
    var sharedGate1Data: DepartureVO? = null

    var sharedGate2Data: DepartureVO? = null
}