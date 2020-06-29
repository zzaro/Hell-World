package com.example.porte.ui.FlightInfo

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.porte.R
import com.example.porte.ValueObject.FlightVO
import kotlinx.android.synthetic.main.flight_info_cell.view.*
import kotlinx.android.synthetic.main.fragment_flight_info.view.*
import java.util.*
import java.util.logging.Filter
import kotlin.collections.ArrayList
import kotlin.math.min

class FlightInfoAdapter(val data: List<FlightVO>?, val destination: String): RecyclerView.Adapter<FlightInfoViewHolder>(), Filterable {

    private val unFilteredList = data
    private var filteredList = data


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flight_info_cell, parent, false)
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
            holder.flightid.text = it[position].flightId
            holder.destination.text = destination

            val scheduleDateTime = it[position].scheduleDateTime
            val year = scheduleDateTime?.substring(0, 4)
            val month = scheduleDateTime?.substring(4, 6)
            val day = scheduleDateTime?.substring(6, 8)
            val hour = scheduleDateTime?.substring(8, 10)
            val minute = scheduleDateTime?.substring(10, 12)

            holder.arriveDate.text = "${year} / ${month} / ${day}"
            holder.arriveTime.text = "${hour} : ${minute}" + "도착"
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
                            if (item.airline!!.contains(charString) || item.flightId!!.contains(charString)) filteringList.add(item)
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


//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//
//            }
//
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//
//            }
//        }
//    }
}

class FlightInfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val airline = itemView.flight_info_airline_text_view
    val flightid = itemView.flight_info_flightid_text_view
    val arriveDate = itemView.flight_info_arrive_date_text_view
    val arriveTime = itemView.flight_info_arrive_time_text_view
    val destination = itemView.flight_info_destination_text_view
}