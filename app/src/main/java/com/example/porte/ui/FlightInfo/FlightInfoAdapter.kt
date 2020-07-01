package com.example.porte.ui.FlightInfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.porte.R
import com.example.porte.ValueObject.FlightVO
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.flight_info_cell.view.*
import kotlinx.android.synthetic.main.flight_info_detail_bottom_sheet.*
import kotlinx.android.synthetic.main.flight_info_detail_bottom_sheet.view.*
import kotlin.collections.ArrayList

class FlightInfoAdapter(val data: List<FlightVO>?, val destination: String): RecyclerView.Adapter<FlightInfoAdapter.FlightInfoViewHolder>(), Filterable {

    private val unFilteredList = data
    private var filteredList = data
    private var parentView: View? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flight_info_cell, parent, false)
        parentView = view
        return FlightInfoViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (filteredList == null) {
            return 0
        }
        else {
            return filteredList!!.size
        }
    }

    override fun onBindViewHolder(holder: FlightInfoViewHolder, position: Int) {
        filteredList.let {
            holder.airline.text = it!![position].airline
            holder.flightId.text = it[position].flightId
            holder.destination.text = destination

            val scheduleDateTime = it[position].scheduleDateTime
            val year = scheduleDateTime?.substring(0, 4)
            val month = scheduleDateTime?.substring(4, 6)
            val day = scheduleDateTime?.substring(6, 8)
            val hour = scheduleDateTime?.substring(8, 10)
            val minute = scheduleDateTime?.substring(10, 12)

            holder.arriveDate.text = "${year} / ${month} / ${day}"
            holder.arriveTime.text = "${hour} : ${minute}" + " 출발"

            holder.setDialog(it[position],
                holder.airline.text.toString(),
                holder.flightId.text.toString(),
                holder.destination.text.toString(),
                holder.arriveDate.text.toString(),
                holder.arriveTime.text.toString()
                )
        }
    }

    override fun getFilter(): android.widget.Filter {
        return object: android.widget.Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                filteredList = if (charString.isEmpty()) { //⑶
                    unFilteredList
                } else {
                    var filteringList = ArrayList<FlightVO>()
                    if (unFilteredList != null) {
                        for (item in unFilteredList) {
                            if (item.airline!!.contains(charString.toUpperCase()) || item.flightId!!.contains(charString.toUpperCase())) filteringList.add(item)
                        }
                    }
                    filteringList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<FlightVO>
                notifyDataSetChanged()
            }

        }
    }

    inner class FlightInfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val airline = itemView.flight_info_cell_airline_text_view
        val flightId = itemView.flight_info_cell_flightid_text_view
        val arriveDate = itemView.flight_info_cell_arrive_date_text_view
        val arriveTime = itemView.flight_info_cell_arrive_time_text_view
        val destination = itemView.flight_info_cell_destination_text_view

        fun setDialog(data: FlightVO,
                      arlineParam: String,
                      flightIdParam: String,
                      destinationParam: String,
                      arriveDateParam: String,
                      arriveTimeParam: String
                         ) {
            itemView.setOnClickListener {
                val dialog = BottomSheetDialog(parentView!!.context)
                dialog.setContentView(R.layout.flight_info_detail_bottom_sheet)

                val airline = dialog.flight_info_detail_airline_text_view
                val flightId = dialog.flight_info_detail_flightid_text_view
                val arriveDate = dialog.flight_info_detail_arrive_date_text_view
                val arriveTime = dialog.flight_info_detail_arrive_time_text_view
                val destination = dialog.flight_info_detail_destination_text_view
                val checkIn = dialog.flight_info_detail_checkin_text_view
                val gate = dialog.flight_info_detail_gate_text_view
                val remark = dialog.flight_info_detail_remark_text_view
                val terminal = dialog.flight_info_detail_terminal_text_view

                val addFlightBtn = dialog.flight_info_detail_add_btn
                val closeBtn = dialog.flight_info_detail_close_btn

                addFlightBtn.setOnClickListener {

                }

                closeBtn.setOnClickListener {
                    dialog.dismiss()
                }

                airline.text = arlineParam
                flightId.text = flightIdParam
                destination.text = destinationParam
                arriveDate.text = arriveDateParam
                arriveTime.text = arriveTimeParam

                gate.text = data.gatenumber ?: "-"
                remark.text = data.remark ?: ""

                var modifiedCheckIn = data.chkinrange?.replace(" ", "     ")
                modifiedCheckIn =  modifiedCheckIn?.replace("-", "\n-\n")
                checkIn.text = modifiedCheckIn

                terminal.text = when(data.terminalid) {
                    "P01" -> "제 1 터미널"
                    "P02" -> "탑승동"
                    "P03" -> "제 2 터미널"
                    else -> ""
                }

               dialog.show()
            }


        }
    }
}

