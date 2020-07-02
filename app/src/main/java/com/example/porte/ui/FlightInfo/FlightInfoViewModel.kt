package com.example.porte.ui.FlightInfo

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.porte.Util.ApiService
import com.example.porte.Util.ApiUtil
import com.example.porte.ValueObject.FlightResponse
import com.example.porte.ValueObject.FlightVO
import com.example.porte.ValueObject.ParkingLotResponse
import com.example.porte.ValueObject.ParkingLotVO
import com.example.porte.ui.parkingLotInfo.ParkingLotInfoViewModel
import com.example.porte.ui.parkingLotInfo.ParkingLotInfoViewModel.Companion.SERVICE_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FlightInfoViewModel: ViewModel() {

    fun requestAPI(code: String, complete: ((List<FlightVO>) -> Unit), fail: (() -> Unit)) {
        ApiUtil.getFlightService(ApiService.Flight).getTop(SERVICE_KEY, code)?.enqueue(object :
            Callback<FlightResponse> {
            override fun onFailure(call: Call<FlightResponse>, t: Throwable) {
                Log.d("API", "Fail(Flight)")
                Log.d("API", call.request().toString())
                Log.d("API", "${t}")
                fail()
            }

            override fun onResponse(
                call: Call<FlightResponse>,
                response: Response<FlightResponse>
            ) {
                Log.d("API", "Success(Flight)")
                Log.d("API", call.request().toString())


                val resultList = response.body()!!.body.items.itemList
                complete(resultList)
            }
        })
    }

    companion object {
        const val SERVICE_KEY = "pgJQkZVlRkVRdW6c0pWRBmu2bTdIQ1FMprnOLoRYLKX%2BHQRPkG%2BlaEJ28smMY0qp3EkcvxqmvjoqADaTBNMD%2FA%3D%3D"
    }
}