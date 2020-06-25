package com.example.porte.ui.parkingLotInfo

import android.graphics.Color
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
        // 주차장 이름 편집
        var parkingName = data[position].floor
        // 단기 주차장일 경우 8번 인덱스부터 출력
        if (parkingName.contains("단기")) {
            parkingName = parkingName.substring(8)
        }
        // 장기 주차장일 경우 6번 인덱스부터 출력
        else {
            parkingName = parkingName.substring(6)
        }
        holder.parkingLotName.text = (parkingName).toString()


        // 주차장 남은 공간 계산 및 출력
        val parkingSpace = (data[position].parkingarea.toInt())
        val nowParking = (data[position].parking).toInt()
        val parkingAvailable = (parkingSpace - nowParking)

        // 미운영 주차장 체크
        if (parkingSpace == 0) {
            holder.parkingLotName.setTextColor(Color.GRAY)
            holder.parkingStatus.text = "미운영"
            holder.parkingStatus.setTextColor(Color.RED)
            holder.parkingLotCarAmount.text = "주차 불가"
            holder.parkingLotCarAmount.setTextColor(Color.RED)
        }
        else {
            val parkingRatio = parkingAvailable.toDouble() / parkingSpace.toDouble()

            holder.parkingLotCarAmount.text = "${parkingAvailable}대 가능"
            if (parkingRatio >= 0.5) {
                holder.parkingStatus.text = "원활"
                holder.parkingStatus.setTextColor(Color.parseColor("#1167b1"))
            }
            else if (parkingRatio >= 0.3) {
                holder.parkingStatus.text = "보통"
                holder.parkingStatus.setTextColor(Color.parseColor("#ff9a00"))
            }
            else {
                holder.parkingStatus.text = "혼잡"
                holder.parkingStatus.setTextColor(Color.RED)
            }
        }
    }
}

class ParkingLotDetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var parkingLotName = itemView.parkingLotCellName_tv
    var parkingLotCarAmount = itemView.parkingLotCellCarAmount_tv
    var parkingStatus = itemView.parkingLotCellStatus_tv
}