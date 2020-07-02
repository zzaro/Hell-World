package com.example.porte.ui.etc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.porte.R
import com.example.porte.ui.parkingLotInfo.ParkingLotInfoViewModel
import kotlinx.android.synthetic.main.fragment_etc.*

class EtcFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_etc,container,false)

        val recycler1=root.findViewById<RecyclerView>(R.id.recycle_etc)
        recycler1.adapter = EtcRecyclerAdapter(this.context!!)
        recycler1.layoutManager = LinearLayoutManager(activity)

        return root
    }
}
