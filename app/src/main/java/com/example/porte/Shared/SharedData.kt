package com.example.porte.Shared


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.porte.ValueObject.FlightVO

import com.example.porte.ValueObject.DepartureVO

import com.example.porte.ui.parkingLotInfo.AllParkingType

object SharedData {
    var sharedParkingData: AllParkingType? = null


    val sharedParkingLiveData = MutableLiveData<AllParkingType?>().apply { postValue(null) }
    fun getSharedParkingLiveData(): LiveData<AllParkingType?> = sharedParkingLiveData
    fun setSharedParkingLiveData(data: AllParkingType?) {
        sharedParkingLiveData.postValue(data)
    }


//    var sharedParkingLastUpdate
    var sharedGate1Data: DepartureVO? = null

    var sharedGate2Data: DepartureVO? = null

}