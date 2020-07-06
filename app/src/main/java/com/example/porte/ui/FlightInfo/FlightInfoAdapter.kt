package com.example.porte.ui.FlightInfo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.RoomDatabase
import com.creageek.segmentedbutton.orElse
import com.example.porte.R
import com.example.porte.Shared.UserFlightInfoDatabase
import com.example.porte.Shared.UserFlightInfoEntity
import com.example.porte.Util.DateTransferUtil
import com.example.porte.ValueObject.FlightVO
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.flight_info_cell.view.*
import kotlinx.android.synthetic.main.flight_info_detail_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_flight_info.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class FlightInfoAdapter(val data: List<FlightVO>?, val rootView: View,val destination: String): RecyclerView.Adapter<FlightInfoAdapter.FlightInfoViewHolder>(), Filterable {

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
            val date = scheduleDateTime?.let { it1 -> DateTransferUtil.changeStringToDate(it1) }

            holder.arriveDate.text = "${date?.get("year")} / ${date?.get("month")} / ${date?.get("day")}"
            holder.arriveTime.text = "${date?.get("hour")} : ${date?.get("minute")}" + " 출발"

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

        fun setDialog(
            data: FlightVO,
            airlineParam: String,
            flightIdParam: String,
            destinationParam: String,
            arriveDateParam: String,
            arriveTimeParam: String
        ) {
            itemView.setOnClickListener {
                rootView.flight_info_flight_search_view.clearFocus()

                val dialog = BottomSheetDialog(parentView?.context!!)
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

                airline.text = airlineParam
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

                addFlightBtn.setOnClickListener {
                    val dao = UserFlightInfoDatabase.getDatabase(itemView.context).userFlightInfoDAO()
                    val userFlightInfo = UserFlightInfoEntity(
                        flightInfoIdx = data.flightId + data.scheduleDateTime,
                        flightId = data.flightId,
                        airline = data.airline,
                        destination = destination.text.toString(),
                        airportcode = data.airportcode,
                        chkinrange = data.chkinrange,
                        scheduleDateTime = data.scheduleDateTime,
                        estimatedDateTime = data.estimatedDateTime,
                        gatenumber = data.gatenumber,
                        remark = data.remark,
                        terminalid = data.terminalid
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        dao.deleteAllUserFlightInfo()
                        dao.insertUserFlightInfo(userFlightInfo)
                        Log.d("log", dao.selectUserFlightInfo(data.flightId + data.scheduleDateTime).toString())
                    }
                    Toast.makeText(itemView.context, "내 항공편으로 설정했습니다", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }

               dialog.show()
            }


        }
    }
}

