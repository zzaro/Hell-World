package com.example.porte.ui.parkingLotInfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creageek.segmentedbutton.SegmentedButton
import com.example.porte.R


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

        val recyclerView: RecyclerView = root.findViewById(R.id.parkingLot_rv)

        parkingLotViewModel.requestAPI(
            complete = {
                recyclerView.adapter = parkingLotViewModel.allParking?.first()?.let {ParkingLotCardAdapter(it) }
                recyclerView.layoutManager = LinearLayoutManager(this@ParkingLotInfoFragment.context)
            },
            fail = {
                Toast.makeText(context, "데이터를 불러오는데 문제가 발생했습니다.", Toast.LENGTH_SHORT).show()
            })



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
                when (segment.text) {
                    "제1터미널" -> {
                        recyclerView.adapter = parkingLotViewModel.allParking?.first()?.let {
                            ParkingLotCardAdapter(it)
                        }
                        recyclerView.layoutManager = LinearLayoutManager(this@ParkingLotInfoFragment.context)
                        //                        Log.d("rv_result", parkingLotViewModel.allParking.first().size.toString())
                    }

                    "제2터미널" -> {
                        recyclerView.adapter = parkingLotViewModel.allParking?.last()?.let {
                            ParkingLotCardAdapter(it)
                        }
                        recyclerView.layoutManager = LinearLayoutManager(this@ParkingLotInfoFragment.context)
                        //                        Log.d("rv_result", parkingLotViewModel.allParking.last().size.toString())
                    }

                }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.parkingLot_rv)

    }

}
