package com.example.porte.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.porte.ui.parkingLotInfo.AllParkingType

class HomeViewModel : ViewModel() {

    private val parkingData = MutableLiveData<AllParkingType>().apply {

    }
}