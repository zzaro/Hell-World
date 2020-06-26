package com.example.porte.ui.parkingLotInfo

import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
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
        val imageView: ImageView = root.findViewById(R.id.parkingLotImgView)


        // 스크롤 위치에 따라 지도 변경
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager: LinearLayoutManager =
                        recyclerView.layoutManager as LinearLayoutManager
                    val position = layoutManager.findFirstVisibleItemPosition()
                    changeImg(position)
                }
            }

            fun changeImg(row: Int) {
                val imageView: ImageView = root.findViewById(R.id.parkingLotImgView)
                if (row == 0) {
                    imageView.setImageResource(R.drawable.t1_short)
                }
                else {
                    imageView.setImageResource(R.drawable.t1_long)
                }
            }
        }

        parkingLotViewModel.requestAPI(
            complete = {
                recyclerView.adapter = parkingLotViewModel.allParking?.first()?.let {ParkingLotCardAdapter(it) }
                recyclerView.layoutManager = LinearLayoutManager(this@ParkingLotInfoFragment.context)
                recyclerView.addOnScrollListener(scrollListener)
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
                        imageView.setImageResource(R.drawable.t1_short)
                        recyclerView.adapter = parkingLotViewModel.allParking?.first()?.let { ParkingLotCardAdapter(it) }
                        recyclerView.addOnScrollListener(scrollListener)
                    }

                    "제2터미널" -> {
                        imageView.setImageResource(R.drawable.t2_long_short)
                        recyclerView.adapter = parkingLotViewModel.allParking?.last()?.let { ParkingLotCardAdapter(it) }
                        recyclerView.removeOnScrollListener(scrollListener)
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

        // Inflate the layout for this fragment
        return root

    }
}
