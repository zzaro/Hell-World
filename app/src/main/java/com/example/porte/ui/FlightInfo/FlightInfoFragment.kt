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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.porte.R
import com.example.porte.Util.DBHelper
import com.example.porte.Util.QueryTextModifier
import com.example.porte.ValueObject.AirportCodeVO
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_flight_info.view.*

/**
 * A simple [Fragment] subclass.
 */
class FlightInfoFragment : Fragment(){


    val flightIfnoViewModel by lazy {
        ViewModelProvider(this).get(FlightInfoViewModel::class.java)
    }

    lateinit var adapter: FlightInfoAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_flight_info, container, false)
        val cardView: CardView = root.findViewById(R.id.flight_info_card_view)
        val searchView: SearchView = root.findViewById(R.id.flight_info_flight_search_view)
        val recyclerView: RecyclerView = root.findViewById(R.id.flight_info_recycler_view)
        val textView: TextView = root.findViewById(R.id.flight_info_text_view)
        val imageView: ImageView = root.findViewById(R.id.flight_info_image_view)


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                adapter.filter.filter(newText)

                return true
            }

        })


        // Bottom Sheet Dialog(공항 코드 검색) 생성.
        cardView.setOnClickListener(View.OnClickListener {
            textView.isVisible = false
            imageView.isVisible = false


            val bottomView = layoutInflater.inflate(R.layout.flight_info_airport_bottom_sheet, null)
            val dialog = BottomSheetDialog(this.context!!)

            val alertImageView: ImageView = bottomView.findViewById(R.id.flight_info_airport_alert_image_view)
            val bottomSearchView: SearchView = bottomView.findViewById(R.id.flight_info_airport_search_view)
            val bottomRecyclerView: RecyclerView = bottomView.findViewById(R.id.flight_info_airport_recycler_view)

            bottomRecyclerView.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

            alertImageView.isVisible = false
            bottomSearchView.requestFocus()

            var dbHelper = DBHelper(this.context!!)
            var db = dbHelper.readableDatabase

            // 검색창 리스너 추가
            bottomSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                var airportCodeList = mutableListOf<AirportCodeVO>()
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
                // 검색 클릭 시
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // 검색 클릭 시 검색 실패 메시지 삭 및 키보드 내리기
                    alertImageView.isVisible = false
                    bottomSearchView.clearFocus()

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
                    bottomRecyclerView.adapter = AirportCodeAdapter(airportCodeList)
                    bottomRecyclerView.layoutManager = LinearLayoutManager(activity)
                    bottomRecyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                        var lastAction: Int? = null

                        // 공항 선택 시
                        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                            lastAction.let {
                                if (e.action == MotionEvent.ACTION_UP) {
                                    if (it == MotionEvent.ACTION_DOWN) {
                                        lastAction = e.action
                                        // 공항 선택
                                        airportSelected(rv, e)
                                        return true
                                    }
                                    else {
                                        lastAction = e.action
                                        Log.d("result", "222")
                                        return false
                                    }
                                }
                            }
                            lastAction = e.action
                            Log.d("result", "333")
                            return false
                        }
                        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                        }
                        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                        }
                    }) // End of bottomRecyclerView.addOnItemTouchListener
                    return true
                } //End of OnQueryTextSubmit

                fun airportSelected(rv: RecyclerView, e: MotionEvent) {
                    Log.d("result", e.action.toString())

                    val child = rv.findChildViewUnder(e.x, e.y)
                    child.let {
                        val position = rv.getChildAdapterPosition(it!!)


                        // 선택 공항 표시
                        val selectedItem = airportCodeList[position]
                        val name = selectedItem.name
                        val code = selectedItem.code
                        val selectedAirportText = name + "\n" + code
                        cardView.flight_info_select_airport_btn.text = selectedAirportText

                        // 운항 정보 불러오기
                        flightIfnoViewModel.requestAPI(code,
                            complete = {
                                adapter = FlightInfoAdapter(it, selectedAirportText)
                                recyclerView.adapter = adapter
                                recyclerView.layoutManager = LinearLayoutManager(activity)

                                // SearchView 보이기
                                searchView.isVisible = true
                            },
                            fail = {
                                Toast.makeText(context, "데이터가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                                // 검색 결과가 없을 떄 리사이클러 뷰 결과 표시하지 않기
                                recyclerView.adapter = FlightInfoAdapter(null, selectedAirportText)
                                // SearchView 가리기
                                searchView.isVisible = false
                                textView.isVisible = true
                                imageView.isVisible = true
                            }
                        )
                        // Bottom Sheet 내리기
                        dialog.dismiss()
                    }
                }
            })
            dialog.setContentView(bottomView)
            dialog.show()

            dialog.setOnDismissListener {
                // 아무 공항도 선택되지 않았다면
                if (cardView.flight_info_select_airport_btn.text == "공항 선택") {
                    textView.isVisible = true
                    imageView.isVisible = true
                }
                else {
                    textView.isVisible = false
                    imageView.isVisible = false
                }
            }
        })// End of cardView.setOnClickListener
        return root
    }

}
