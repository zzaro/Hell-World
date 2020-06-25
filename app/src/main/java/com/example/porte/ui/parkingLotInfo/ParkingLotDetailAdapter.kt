package com.example.porte.ui.parkingLotInfo

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.porte.R
import kotlinx.android.synthetic.main.parking_lot_cell.view.*

class ParkingLotDetailAdapter(val data: DetailParkingType): RecyclerView.Adapter<ParkingLotDetailViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParkingLotDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.parking_lot_cell, parent, false)
        return ParkingLotDetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: ParkingLotDetailViewHolder, position: Int) {
        val parkingSpace = (data[position].parkingarea.toInt())
        val nowParking = (data[position].parking).toInt()
        val parkingAvailable = (parkingSpace - nowParking)

        val parkingRatio = parkingAvailable.toDouble() / parkingSpace.toDouble()

        holder.parkingLotName.text = (data[position].floor).toString()
        holder.parkingLotCarAmount.text = "${parkingAvailable}대 주차가능"
        if (parkingRatio >= 0.5) {
            holder.parkingStatus.text = "원활"
        }
        else if (parkingRatio >= 0.3) {
            holder.parkingStatus.text = "보통"
        }
        else {
            holder.parkingStatus.text = "혼잡"
       }
    }
}

class ParkingLotDetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var parkingLotName = itemView.parkingLotCellName_tv
    var parkingLotCarAmount = itemView.parkingLotCellCarAmount_tv
    var parkingStatus = itemView.parkingLotCellStatus_tv
}