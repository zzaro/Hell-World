package com.example.porte.ui.parkingLotInfo

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.porte.Util.ApiService
import com.example.porte.Util.ApiUtil
import com.example.porte.ValueObject.ParkingLotResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParkingLotInfoViewModel : ViewModel(){

    fun requestAPI() {
        ApiUtil.getParkingLotService(ApiService.PARKING).getTop(SERVICE_KEY, PAGE_NO, NUM_OF_ROWS)?.enqueue(object :
            Callback<ParkingLotResponse> {
            override fun onFailure(call: Call<ParkingLotResponse>, t: Throwable) {
                Log.d("API", "Fail(Parking)")
                Log.d("API", call.request().toString())
                Log.d("API", "${t}")

            }

            override fun onResponse(
                call: Call<ParkingLotResponse>,
                response: Response<ParkingLotResponse>
            ) {
                Log.d("API", "Success(Parking)")
                Log.d("API", call.request().toString())
                Log.d("API", response.body().toString())

                val resultList = response.body()!!.body.parkingItems.itemList

                for (item in resultList) {
                    Log.d("result", item.floor)
                    Log.d("result", item.parkingarea)
                    Log.d("result", item.parking)
                }
            }
        })
    }

    companion object {
        const val SERVICE_KEY = "pgJQkZVlRkVRdW6c0pWRBmu2bTdIQ1FMprnOLoRYLKX%2BHQRPkG%2BlaEJ28smMY0qp3EkcvxqmvjoqADaTBNMD%2FA%3D%3D"
        const val PAGE_NO = "1"
        const val NUM_OF_ROWS = "13"
    }

}