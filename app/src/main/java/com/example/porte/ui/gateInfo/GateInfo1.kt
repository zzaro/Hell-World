package com.example.porte.ui.gateInfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.porte.R
import com.example.porte.Shared.SharedData
import com.example.porte.Util.ApiUtil
import com.example.porte.ValueObject.DepartureVO
import com.example.porte.ui.parkingLotInfo.ParkingLotCardAdapter
import com.example.porte.ui.parkingLotInfo.ParkingLotInfoViewModel
import kotlinx.android.synthetic.main.fragment_gate_info.view.*

class GateInfo1 : Fragment() {
        val GateInfo1ViewModel by lazy {
                ViewModelProvider(this).get(GateInfo1ViewModel::class.java)
        }
        override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {
                val root = inflater.inflate(R.layout.fragment_gate_info, container, false)
                val recyclerView = root.findViewById<RecyclerView>(R.id.gateInfo1_rv)

//                GateInfo1ViewModel.requestAPI(
//                                complete = {
//                                        recyclerView.adapter = GateInfo1Adapter(it)
//                                        recyclerView.layoutManager = LinearLayoutManager(activity)
//                                },
//                                fail = {
//                                        Toast.makeText(context, "데이터를 불러오는데 문제가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                                }
//                        )

                if(SharedData.sharedGate1Data == null)
                {
                        GateInfo1ViewModel.requestAPI(
                                complete = {
                                        recyclerView.adapter = GateInfo1Adapter()
                                        recyclerView.layoutManager = LinearLayoutManager(activity)
                                },
                                fail = {
                                        Toast.makeText(context, "데이터를 불러오는데 문제가 발생했습니다.", Toast.LENGTH_SHORT).show()
                                }
                        )
                }
                else
                {
                        recyclerView.adapter = GateInfo1Adapter()
                        recyclerView.layoutManager = LinearLayoutManager(activity)
                }

                return root
        }
}
