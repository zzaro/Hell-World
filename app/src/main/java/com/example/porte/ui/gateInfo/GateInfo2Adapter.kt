package com.example.porte.ui.gateInfo

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.porte.R
import com.example.porte.Shared.SharedData
import com.example.porte.ValueObject.DepartureVO
import kotlinx.android.synthetic.main.gate_info_cell.view.*


class GateInfo2Adapter():RecyclerView.Adapter<GateInfo2Adapter.GateInfo2ViewHolder>(){

    var count: Int = 1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GateInfo2Adapter.GateInfo2ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gate_info_cell,parent,false)
        return GateInfo2ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: GateInfo2Adapter.GateInfo2ViewHolder, position: Int) {

        if(count==1)
        {
            var state_num_filter: String = SharedData.sharedGate2Data!!.gate1.toString()

            if(state_num_filter=="0")
            {
                state_num_filter = "원활"
                holder.state.setTextColor(Color.rgb(63,164,253))
            }
            else if(state_num_filter=="1")
            {
                state_num_filter = "보통"
                holder.state.setTextColor(Color.rgb(25,199,66))
            }
            else if(state_num_filter=="2")
            {
                state_num_filter = "혼잡"
                holder.state.setTextColor(Color.rgb(250,152,94))
            }
            else if(state_num_filter=="3")
            {
                state_num_filter = "매우혼잡"
                holder.state.setTextColor(Color.rgb(252,82,91))
            }
            else if(state_num_filter=="9")
            {
                state_num_filter = "CLOSED"
                holder.state.setTextColor(Color.rgb(127,127,127))
            }
            holder.gate_num.text = "GATE1"
            holder.wait_num.text = SharedData.sharedGate2Data!!.gateinfo1.toString()
            holder.state.text = state_num_filter

        }
        else if(count==2)
        {
            var state_num_filter: String = SharedData.sharedGate2Data!!.gate2.toString()

            if(state_num_filter=="0")
            {
                state_num_filter = "원활"
                holder.state.setTextColor(Color.rgb(63,164,253))
            }
            else if(state_num_filter=="1")
            {
                state_num_filter = "보통"
                holder.state.setTextColor(Color.rgb(25,199,66))
            }
            else if(state_num_filter=="2")
            {
                state_num_filter = "혼잡"
                holder.state.setTextColor(Color.rgb(250,152,94))
            }
            else if(state_num_filter=="3")
            {
                state_num_filter = "매우혼잡"
                holder.state.setTextColor(Color.rgb(252,82,91))
            }
            else if(state_num_filter=="9")
            {
                state_num_filter = "CLOSED"
                holder.state.setTextColor(Color.rgb(127,127,127))
            }
            holder.gate_num.text = "GATE2"
            holder.wait_num.text = SharedData.sharedGate2Data!!.gateinfo2.toString()
            holder.state.text = state_num_filter
        }

        count += 1

    }

    inner class GateInfo2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val gate_num = itemView.gate_num
        val wait_num = itemView.wait_num
        val state = itemView.state
    }
}