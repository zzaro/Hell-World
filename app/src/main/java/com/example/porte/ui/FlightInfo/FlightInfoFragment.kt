package com.example.porte.ui.FlightInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.porte.R
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * A simple [Fragment] subclass.
 */
class FlightInfoFragment : Fragment(){


    val flightIfnoViewModel by lazy {
        ViewModelProvider(this).get(FlightInfoViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_flight_info, container, false)
        val cardView: CardView = root.findViewById(R.id.flight_info_card_view)
        val searchView: SearchView = root.findViewById(R.id.flight_info_flight_search_view)
        val textView: TextView = root.findViewById(R.id.flight_info_text_view)


        cardView.setOnClickListener(View.OnClickListener {
            textView.isVisible = false

            val view = layoutInflater.inflate(R.layout.fragment_flight_info_bottom_sheet, null)
            val dialog = BottomSheetDialog(this.context!!)
            dialog.setContentView(view)
            dialog.show()
        })


        return root
    }



}
