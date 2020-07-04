package com.example.porte.ui.gateInfo

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.porte.Shared.SharedData
import com.example.porte.Util.ApiService
import com.example.porte.Util.ApiUtil
import com.example.porte.ValueObject.DepartureResponse
import com.example.porte.ValueObject.DepartureVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GateInfo1ViewModel: ViewModel() {
    fun requestAPI(complete: ((DepartureVO)-> Unit), fail: (()->Unit)){
        ApiUtil.getDepartureService(ApiService.DEPARTURE).getTop(SERVICE_KEY, "1")?.enqueue(object :
        Callback<DepartureResponse>{
            override fun onFailure(call: Call<DepartureResponse>, t: Throwable) {
                Log.d("API", "Fail(Departure)")
                Log.d("API", call.request().toString())
                Log.d("API","${t}")
                Log.d("API_s",t.toString())
                fail()
            }

            override fun onResponse(
                call: Call<DepartureResponse>,
                response: Response<DepartureResponse>
            ) {
                Log.d("API", "Success(Departure)")
                Log.d("API", call.request().toString())


                val resultList = response.body()!!.body.departureitems.itemList
                Log.d("API_s",resultList.toString())
                SharedData.sharedGate1Data = resultList
                complete(resultList)

            }
        }
        )
    }

    companion object{
        const val SERVICE_KEY = "6kL%2FQGHekHiKnaBIwJ%2F%2FmI9T%2Bo0pl%2BbKQkiVcC9U9G8N0xitDyoMPLMPKZjIyq3qzeKZiVH%2F4UyTDcteppSj4A%3D%3D"
    }
}
