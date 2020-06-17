package com.example.porte.ui.parkingLotInfo

import android.net.DnsResolver
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.creageek.segmentedbutton.SegmentedButton
import com.example.porte.R
import com.example.porte.ValueObject.ParkingLotModel
import com.example.porte.ValueObject.ParkingLotVO
import com.example.porte.api_util.ParkingLotAPI
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

        ParkingLotAPI.getService().getTop(SERVICE_KEY, PAGE_NO, NUM_OF_ROWS)?.enqueue(object : Callback<ParkingLotModel> {
            override fun onFailure(call: Call<ParkingLotModel>, t: Throwable) {
                Log.d("AAA", "${t}")
            }

            override fun onResponse(
                call: Call<ParkingLotModel>,
                response: Response<ParkingLotModel>
            ) {
                Log.d("AAA", response.body().toString())
                println(response.body())
            }
        })

        // Inflate the layout for this fragment
        return root



    }
    companion object {
        const val SERVICE_KEY = "pgJQkZVlRkVRdW6c0pWRBmu2bTdIQ1FMprnOLoRYLKX%2BHQRPkG%2BlaEJ28smMY0qp3EkcvxqmvjoqADaTBNMD%2FA%3D%3D"
        const val PAGE_NO = "1"
        const val NUM_OF_ROWS = "13"
    }
}
