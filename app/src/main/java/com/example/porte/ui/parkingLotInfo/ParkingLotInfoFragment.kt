package com.example.porte.ui.parkingLotInfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.creageek.segmentedbutton.SegmentedButton
import com.example.porte.R
import com.example.porte.ValueObject.ParkingLotResponse
import com.example.porte.Util.ApiService
import com.example.porte.Util.ApiUtil
import com.example.porte.ValueObject.DepartureResponse
import com.example.porte.ValueObject.DepartureVO
import com.example.porte.ValueObject.ParkingLotVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//import javax.security.auth.callback.Callback

/**
 * A simple [Fragment] subclass.
 */
class ParkingLotInfoFragment : Fragment() {

    val parkingLotViewModel by lazy {
        ViewModelProvider(this).get(ParkingLotInfoViewModel::class.java)
    }

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


        parkingLotViewModel.requestAPI()





//        ApiUtil.getDepartureService(ApiService.DEPARTURE).getTop(SERVICE_KEY, "2")?.enqueue(object: Callback<DepartureResponse> {
//            override fun onFailure(call: Call<DepartureResponse>, t: Throwable) {
//                Log.d("API", "Fail(Departure)")
//                Log.d("API", call.request().toString())
//                Log.d("API", "${t}")
//            }
//
//            override fun onResponse(
//                call: Call<DepartureResponse>,
//                response: Response<DepartureResponse>
//            ) {
//                Log.d("API", "Success(Departure)")
//                Log.d("API", call.request().toString())
//                Log.d("API", response.body().toString())
//
//                val resultList = response.body()!!.body.departureitems.item
//            }
//        })

        // Inflate the layout for this fragment
        return root



    }

}
