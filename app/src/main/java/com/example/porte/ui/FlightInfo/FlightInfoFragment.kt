package com.example.porte.ui.FlightInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.porte.R
import com.example.porte.Util.ApiService
import com.example.porte.Util.ApiUtil
import com.example.porte.ui.parkingLotInfo.ParkingLotInfoViewModel

/**
 * A simple [Fragment] subclass.
 */
class FlightInfoFragment : Fragment() {


    val flightIfnoViewModel by lazy {
        ViewModelProvider(this).get(FlightInfoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_flight_info, container, false)

        flightIfnoViewModel.requestAPI()

        return root
    }



}
