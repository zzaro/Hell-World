package com.example.porte.Shared


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.porte.ValueObject.FlightVO

import com.example.porte.ValueObject.DepartureVO

import com.example.porte.ui.parkingLotInfo.AllParkingType

object SharedData {
    val sharedParkingLiveData = MutableLiveData<AllParkingType?>().apply { postValue(null) }
    fun getSharedParkingLiveData(): LiveData<AllParkingType?> = sharedParkingLiveData
    fun setSharedParkingLiveData(data: AllParkingType?) {
        sharedParkingLiveData.postValue(data)
    }



    val sharedGate1LiveData = MutableLiveData<DepartureVO?>().apply { postValue(null) }
    fun getSharedGate1LiveData(): LiveData<DepartureVO?> = sharedGate1LiveData
    fun setSharedGate1LiveData(data: DepartureVO?) {
        sharedGate1LiveData.postValue(data)
    }



    val sharedGate2LiveData = MutableLiveData<DepartureVO?>().apply { postValue(null) }
    fun getSharedGate2LiveData(): LiveData<DepartureVO?> = sharedGate2LiveData
    fun setSharedGate2LiveData(data: DepartureVO?) {
        sharedGate2LiveData.postValue(data)
    }
}