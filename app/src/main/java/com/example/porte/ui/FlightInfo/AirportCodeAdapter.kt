package com.example.porte.ui.FlightInfo

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.porte.R
import com.example.porte.ValueObject.AirportCodeVO
import kotlinx.android.synthetic.main.airport_code_cell.view.*

class AirportCodeAdapter(val data: List<AirportCodeVO>): RecyclerView.Adapter<AirportCodeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirportCodeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.airport_code_cell, parent, false)
        return AirportCodeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: AirportCodeViewHolder, position: Int) {
        holder.code.text = data[position].code
        val country = data[position].country
        val city = data[position].city
        holder.country_city.text = country + "-" + city
        holder.name.text = data[position].name + "공항"
    }

}

class AirportCodeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val code = itemView.airport_code_cell_code_text_view
    val country_city = itemView.airport_code_cell_country_city_text_view
    val name = itemView.airport_code_cell_name_text_view
}