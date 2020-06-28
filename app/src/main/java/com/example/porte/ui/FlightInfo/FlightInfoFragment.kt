package com.example.porte.ui.FlightInfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.porte.R
import com.example.porte.Util.DBHelper
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

            val searchView: SearchView = view.findViewById(R.id.flight_info_airport_search_view)
            val recyclerView: RecyclerView = view.findViewById(R.id.flight_info_airport_recycler_view)
            searchView.requestFocus()

            var dbHelper = DBHelper(this.context!!)
            var db = dbHelper.readableDatabase


            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()
                    Log.d("#DB", "TextSubmit")
                    var cursor = db.rawQuery("SELECT * FROM airport\n" +
                            "WHERE name='${query}' or\n" +
                            "country = '${query}' or\n" +
                            "city = '${query}' or\n" +
                            "code = '${query}';"
                        , null)

                    if (cursor.moveToFirst()){
                        do{
                            var code = cursor.getString(cursor.getColumnIndex("code"));
                            var country = cursor.getString(cursor.getColumnIndex("country"));
                            var city = cursor.getString(cursor.getColumnIndex("city"));
                            var name = cursor.getString(cursor.getColumnIndex("name"));
                            // do what ever you want here
                            Log.d("#DB", code.toString())
                            Log.d("#DB", country.toString())
                            Log.d("#DB", city.toString())
                            Log.d("#DB", name.toString())
                            Log.d("#DB", "-------------------")
                        }while(cursor.moveToNext());
                    }
                    cursor.close();

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })

            dialog.setContentView(view)
            dialog.show()
        })


        return root
    }



}
