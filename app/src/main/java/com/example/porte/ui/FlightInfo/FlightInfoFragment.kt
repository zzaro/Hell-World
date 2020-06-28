package com.example.porte.ui.FlightInfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.porte.R
import com.example.porte.Util.DBHelper
import com.example.porte.Util.QueryTextModifier
import com.example.porte.ValueObject.AirportCodeVO
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_flight_info.view.*
import kotlinx.coroutines.selects.select

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

        // 공항 코드 검색 Bottom Sheet Dialog 생성.
        cardView.setOnClickListener(View.OnClickListener {
            textView.isVisible = false

            val view = layoutInflater.inflate(R.layout.fragment_flight_info_bottom_sheet, null)
            val dialog = BottomSheetDialog(this.context!!)

            val alertImageView: ImageView = view.findViewById(R.id.flight_info_airport_alert_image_view)
            val searchView: SearchView = view.findViewById(R.id.flight_info_airport_search_view)
            val recyclerView: RecyclerView = view.findViewById(R.id.flight_info_airport_recycler_view)

            alertImageView.isVisible = false
            searchView.requestFocus()

            var dbHelper = DBHelper(this.context!!)
            var db = dbHelper.readableDatabase

            // 검색창 리스너 추가
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                var airportCodeList = mutableListOf<AirportCodeVO>()
                // 검색 클릭 시
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // 검색 클릭 시 검색 실패 메시지 삭 및 키보드 내리기
                    alertImageView.isVisible = false
                    searchView.clearFocus()

                    // 검색어 대문자로 변환 (공항코드가 대문자 영문)
                    val queryText = QueryTextModifier.modifyText(query!!)

                    // 새로운 검색 시 공항코드리스트 초기화
                    airportCodeList.clear()

                    // 조회 쿼리문
                    var cursor = db.rawQuery("SELECT * FROM airport\n" +
                            "WHERE name='${queryText}' or\n" +
                            "country = '${queryText}' or\n" +
                            "city = '${queryText}' or\n" +
                            "code = '${queryText}';"
                        , null)

                    // 조회 결과 리스트화
                    if (cursor.moveToFirst()){
                        do{
                            val code = cursor.getString(cursor.getColumnIndex("code"));
                            val country = cursor.getString(cursor.getColumnIndex("country"));
                            val city = cursor.getString(cursor.getColumnIndex("city"));
                            val name = cursor.getString(cursor.getColumnIndex("name"));

                            val airportCode = AirportCodeVO(code, country, city, name)
                            airportCodeList.add(airportCode)
                        }while(cursor.moveToNext());
                    }
                    cursor.close();

                    // 검색 결과가 존재하지 않을 경우
                    if (airportCodeList.isEmpty()) {
                        alertImageView.isVisible = true
                        Toast.makeText(activity, "검색 결과가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }

                    // 리사이클러뷰 초기 설정.
                    recyclerView.adapter = AirportCodeAdapter(airportCodeList)
                    recyclerView.layoutManager = LinearLayoutManager(activity)
                    recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                        }

                        // 터치 입력
                        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                            val child = rv.findChildViewUnder(e.x, e.y)
                            val position = rv.getChildAdapterPosition(child!!)

                            val selectedItem = airportCodeList[position]
                            val name = selectedItem.name
                            val code = selectedItem.code
                            cardView.flight_info_select_airport_btn.text = name + "\n" + code

                            dialog.dismiss()

                            return false
                        }

                        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                        }
                    })
                    return true
                } //End of OnQueryTextSubmit



                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
            dialog.setContentView(view)
            dialog.show()

            dialog.setOnDismissListener {
                // 아무 공항도 선택되지 않았다면
                if (cardView.flight_info_select_airport_btn.text == "공항 선택") {
                    textView.isVisible = true
                }
                else {
                    textView.isVisible = false
                }
            }
        })// End of cardView.setOnClickListener
        return root
    }



}
