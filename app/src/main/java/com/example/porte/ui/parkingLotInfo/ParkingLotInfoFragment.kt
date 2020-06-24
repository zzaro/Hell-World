package com.example.porte.ui.parkingLotInfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.creageek.segmentedbutton.SegmentedButton
import com.example.porte.R
import com.example.porte.ValueObject.ParkingLotResponse
import com.example.porte.Util.ApiService
import com.example.porte.Util.ApiUtil
import com.example.porte.ValueObject.DepartureResponse
import com.example.porte.ValueObject.DepartureVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//import javax.security.auth.callback.Callback

/**
 * A simple [Fragment] subclass.
 */
class ParkingLotInfoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_parking_lot_info, container, false)

        // Segment Control 초기화
        val segmented: SegmentedButton = root.findViewById(R.id.terminal_segment_control)
        segmented {
            // set initial checked segment (null by default)
            initialCheckedIndex = 0

            // init with segments programmatically without RadioButton as a child in xml
            initWithItems {
                // takes only list of strings
                listOf("제1터미널", "제2터미널")
            }

           // notifies when segment was checked
            onSegmentChecked { segment ->
                Log.d("creageek:segmented", "Segment ${segment.text} checked")
            }
            // notifies when segment was unchecked
            onSegmentUnchecked { segment ->
                Log.d("creageek:segmented", "Segment ${segment.text} unchecked")
            }
            // notifies when segment was rechecked
            onSegmentRechecked { segment ->
                Log.d("creageek:segmented", "Segment ${segment.text} rechecked")
            }
        }

        ApiUtil.getParkingLotService(ApiService.PARKING).getTop(SERVICE_KEY, PAGE_NO, NUM_OF_ROWS)?.enqueue(object : Callback<ParkingLotResponse> {
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
            }
        })


        ApiUtil.getDepartureService(ApiService.DEPARTURE).getTop(SERVICE_KEY, TERNO)?.enqueue(object: Callback<DepartureResponse> {
            override fun onFailure(call: Call<DepartureResponse>, t: Throwable) {
                Log.d("API", "Fail(Departure)")
                Log.d("API", call.request().toString())
                Log.d("API", "${t}")
            }

            override fun onResponse(
                call: Call<DepartureResponse>,
                response: Response<DepartureResponse>
            ) {
                Log.d("API", "Success(Departure)")
                Log.d("API", call.request().toString())
                Log.d("API", response.body().toString())
            }
        })

        // Inflate the layout for this fragment
        return root



    }
    companion object {
        const val SERVICE_KEY = "pgJQkZVlRkVRdW6c0pWRBmu2bTdIQ1FMprnOLoRYLKX%2BHQRPkG%2BlaEJ28smMY0qp3EkcvxqmvjoqADaTBNMD%2FA%3D%3D"
        const val PAGE_NO = "1"
        const val NUM_OF_ROWS = "13"
        const val TERNO = "1"
    }
}
