package com.example.porte.ui.parkingLotInfo

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.porte.Util.ApiService
import com.example.porte.Util.ApiUtil
import com.example.porte.ValueObject.ParkingLotResponse
import com.example.porte.ValueObject.ParkingLotVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

typealias AllParkingType = List<TerminalParkingType>
typealias TerminalParkingType = List<DetailParkingType>
typealias DetailParkingType = MutableList<ParkingLotVO>


class ParkingLotInfoViewModel : ViewModel(){


    lateinit var allParking: AllParkingType

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

                var t1ShortTerm: MutableList<ParkingLotVO> = mutableListOf()
                var t1LongTerm: MutableList<ParkingLotVO> = mutableListOf()
                var t2ShortTerm: MutableList<ParkingLotVO> = mutableListOf()
                var t2LongTerm: MutableList<ParkingLotVO> = mutableListOf()

                // 터미널, 주차장에 따라서 데이터 구분.
                for (item in resultList) {
                    // 제 1 터미널
                    if (item.floor.contains("T1", false)) {
                        // 단기 주차장
                        if (item.floor.contains("단기", false)) {
                            t1ShortTerm.add(item)
                        }
                        // 장기 주차장
                        else {
                            t1LongTerm.add(item)
                        }
                    }
                    // 제 2 터미널
                    else {
                        // 단기 주차장
                        if (item.floor.contains("단기", false)) {
                            t2ShortTerm.add(item)
                        }
                        // 장기 주차장
                        else {
                            t2LongTerm.add(item)
                        }
                    }
                } // End of for statement

                allParking = listOf(listOf(t1ShortTerm, t1LongTerm), listOf(t2ShortTerm, t2LongTerm))
                Log.d("result", allParking.toString())
                Log.d("result", allParking.first().first().size.toString())
            }
        })
    }

    companion object {
        const val SERVICE_KEY = "pgJQkZVlRkVRdW6c0pWRBmu2bTdIQ1FMprnOLoRYLKX%2BHQRPkG%2BlaEJ28smMY0qp3EkcvxqmvjoqADaTBNMD%2FA%3D%3D"
        const val PAGE_NO = "1"
        const val NUM_OF_ROWS = "13"
    }

}


//[
//    [
//        [
//            ParkingLotVO(datetm=20200625161516.893, floor=T1 단기주차장지상층, parking=68, parkingarea=994),
//            ParkingLotVO(datetm=20200625161516.893, floor=T1 단기주차장지하1층, parking=221, parkingarea=983),
//            ParkingLotVO(datetm=20200625161516.893, floor=T1 단기주차장지하2층, parking=229, parkingarea=1339)
//        ],
//        [
//            ParkingLotVO(datetm=20200625161516.894, floor=T1 장기 P1 주차장, parking=212, parkingarea=2762),
//            ParkingLotVO(datetm=20200625161516.896, floor=T1 장기 P1 주차타워, parking=329, parkingarea=1023),
//            ParkingLotVO(datetm=20200625161516.895, floor=T1 장기 P2 주차장, parking=89, parkingarea=2592),
//            ParkingLotVO(datetm=20200625161516.897, floor=T1 장기 P2 주차타워, parking=264, parkingarea=1024),
//            ParkingLotVO(datetm=20200625161516.898, floor=T1 장기 P3 주차장, parking=2, parkingarea=0),
//            ParkingLotVO(datetm=20200625161516.899, floor=T1 장기 P4 주차장, parking=0, parkingarea=0)
//        ]
//    ],
//    [
//        [
//            ParkingLotVO(datetm=20200625161516.891, floor=T2 단기주차장지상1층, parking=269, parkingarea=988), ParkingLotVO(datetm=20200625161516.891, floor=T2 단기주차장지상2층, parking=162, parkingarea=514), ParkingLotVO(datetm=20200625161516.891, floor=T2 단기주차장지하M층, parking=209, parkingarea=751)], [ParkingLotVO(datetm=20200625161516.892, floor=T2 장기 주차장, parking=70, parkingarea=3898)]]]
