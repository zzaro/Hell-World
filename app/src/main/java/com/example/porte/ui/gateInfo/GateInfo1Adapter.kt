package com.example.porte.ui.gateInfo

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.porte.R
import com.example.porte.Shared.SharedData
import com.example.porte.ValueObject.DepartureVO
import kotlinx.android.synthetic.main.gate_info_cell.view.*


class GateInfo1Adapter():RecyclerView.Adapter<GateInfo1Adapter.GateInfoViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GateInfo1Adapter.GateInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gate_info_cell,parent,false)
        return GateInfoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder: GateInfoViewHolder, position: Int) {

        if(position==0)
        {
            var state_num_filter: String = SharedData.sharedGate1Data!!.gate1.toString()
            filter(state_num_filter, holder)
            holder.gate_num.text = "GATE1"
            holder.wait_num.text = SharedData.sharedGate1Data!!.gateinfo1.toString()

            //holder.state.text = state_num_filter

        }
        else if(position==1)
        {
            var state_num_filter: String = SharedData.sharedGate1Data!!.gate2.toString()
            filter(state_num_filter, holder)
            holder.gate_num.text = "GATE2"
            holder.wait_num.text = SharedData.sharedGate1Data!!.gateinfo2.toString()
           // holder.state.text = state_num_filter
        }
        else if(position==2)
        {
            var state_num_filter: String = SharedData.sharedGate1Data!!.gate3.toString()
            filter(state_num_filter, holder)
            holder.gate_num.text = "GATE3"
            holder.wait_num.text = SharedData.sharedGate1Data!!.gateinfo3.toString()
            //holder.state.text = state_num_filter
        }
        else if(position==3)
        {
            var state_num_filter: String = SharedData.sharedGate1Data!!.gate4.toString()
            filter(state_num_filter, holder)
            holder.gate_num.text = "GATE4"
            holder.wait_num.text = SharedData.sharedGate1Data!!.gateinfo4.toString()
            //holder.state.text = state_num_filter
        }
        else if(position==4)
        {
            var state_num_filter: String = SharedData.sharedGate1Data!!.gate5.toString()
            filter(state_num_filter, holder)
            holder.gate_num.text = "GATE5"
            holder.wait_num.text = SharedData.sharedGate1Data!!.gateinfo5.toString()
            //holder.state.text = state_num_filter
        }
        else if(position==5)
        {
            var state_num_filter: String = SharedData.sharedGate1Data!!.gate6.toString()
            filter(state_num_filter, holder)
            holder.gate_num.text = "GATE6"
            holder.wait_num.text = SharedData.sharedGate1Data!!.gateinfo6.toString()
            //holder.state.text = state_num_filter
        }
    }

    inner class GateInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val gate_num = itemView.gate_num
        val wait_num = itemView.wait_num
        val state = itemView.state
    }


    fun filter(state_num_filter2: String, holder: GateInfoViewHolder)
    {
            if(state_num_filter2=="0")
            {
                holder.state.text = "원활"
                holder.state.setTextColor(Color.rgb(63,164,253))
            }
            else if(state_num_filter2=="1")
            {
                holder.state.text = "보통"
                holder.state.setTextColor(Color.rgb(25,199,66))
            }
            else if(state_num_filter2=="2")
            {
                holder.state.text = "혼잡"
                holder.state.setTextColor(Color.rgb(250,152,94))
            }
            else if(state_num_filter2=="3")
            {
                holder.state.text = "매우혼잡"
                holder.state.setTextColor(Color.rgb(252,82,91))
            }
            else if(state_num_filter2=="9")
            {
                holder.state.text = "CLOSED"
                holder.state.setTextColor(Color.rgb(127,127,127))
            }
            else
            {
                holder.state.text = "공사중"
                holder.state.setTextColor(Color.rgb(127,127,127))
            }
    }
}