package com.example.porte.ui.FlightInfo


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.example.porte.R

/**
 * A simple [Fragment] subclass.
 */
class FlightInfoBottomSheetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_flight_info_bottom_sheet, container, false)
        val searchView: SearchView = root.findViewById(R.id.flight_info_airport_search_view)
        Log.d("result", "Load!!!!")

        searchView.setOnSearchClickListener {
            Log.d("result", "Clicked!!!!")
        }
        // Inflate the layout for this fragment
        return root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//    }

}
