package com.example.porte.ui.parkingLotInfo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.porte.R
import kotlinx.android.synthetic.main.parking_lot_card_cell.view.*

class ParkingLotCardAdapter(val data: TerminalParkingType) : RecyclerView.Adapter<ParkingLotCardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingLotCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.parking_lot_card_cell, parent, false)

        return ParkingLotCardViewHolder(view, parent)
    }

    override fun getItemCount(): Int {
        return data.size

    }

    override fun onBindViewHolder(holder: ParkingLotCardViewHolder, position: Int) {
        if (position == 0) {
            holder.category.text = "단기 주차장"
        }
        else {
            holder.category.text = "장기 주차장"
        }
        holder.cardRecyclerView.adapter = ParkingLotDetailAdapter(data[position])
        holder.cardRecyclerView.layoutManager = LinearLayoutManager(holder.cardView.context)
    }
}

class ParkingLotCardViewHolder(itemView: View, parent: ViewGroup): RecyclerView.ViewHolder(itemView) {
    val cardRecyclerView = itemView.parkingLotCardCell_cv.parkingLotCardCell_rv
    val cardView = itemView
    val category = cardView.parkingLotCategory_tv
}