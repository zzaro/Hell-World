package com.example.porte.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.porte.MainActivity
import com.example.porte.R
import com.example.porte.Shared.SharedData
import com.example.porte.Shared.UserFlightInfoDatabase
import com.example.porte.Shared.UserFlightInfoEntity
import com.example.porte.Shared.UserInfoDatabase
import com.example.porte.Util.DateTransferUtil
import com.example.porte.Util.ImageTransferUtil
import com.example.porte.ValueObject.ParkingLotVO
import com.example.porte.ui.signInUp.Profile
import com.example.porte.ui.signInUp.SignIn
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class HomeFragment : Fragment() {

    private var mainActivity: MainActivity? = null

    private val homeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    private val userDao by lazy { UserInfoDatabase.getDatabase(requireContext()).userInfoDAO() }
    private val flightDao by lazy { UserFlightInfoDatabase.getDatabase(requireContext()).userFlightInfoDAO() }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val logoutBtn = root.findViewById<ImageView>(R.id.home_logout)
        val profileImageiew = root.findViewById<ImageView>(R.id.home_profile)
        val flightInfolayout = root.findViewById<LinearLayout>(R.id.home_flight_info_layout)
        val noflightInfoLayout = root.findViewById<LinearLayout>(R.id.home_no_flight_info_layout)
        
        profileImageiew.clipToOutline = true
        noflightInfoLayout.bringToFront()


        userDao.selectAllUserInfo().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                profileImageiew.setImageBitmap(ImageTransferUtil.changeStirngToBitmap(it.userImg!!))
            }
        })

        flightDao.selectAllUserFlightInfo().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                noflightInfoLayout.isVisible = false
                setFlightInfoCardView(root, it)
                setGateSummary(root, it)
                setPakringLotSummary(root, it)
            }
            else {
                noflightInfoLayout.isVisible = true
            }
        })

//        signOut()

        noflightInfoLayout.setOnClickListener{
            mainActivity?.goToFlightSearch()

        }

        logoutBtn.setOnClickListener {
            signOut()
        }


        profileImageiew.setOnClickListener{
            val intent = Intent(requireContext(), Profile::class.java)
            intent.putExtra("isFromSignIn", false)
            startActivity(intent)
        }


        return root
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        Toast.makeText(requireContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()

        val intent = Intent(requireContext(), SignIn::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)

        CoroutineScope(Dispatchers.IO).launch {
            userDao.deleteAllUserInfo()
            flightDao.deleteAllUserFlightInfo()
        }
    }

    fun setMainActivity(activity: MainActivity) {
        mainActivity = activity
    }

    fun setFlightInfoCardView(view: View, data: UserFlightInfoEntity) {
        val airline_tv = view.home_airline_text_view
        val flightId_tv = view.home_flightid_text_view
        val arriveDate_tv = view.home_arrive_date_text_view
        val arriveTime_tv = view.home_arrive_time_text_view
        val destination_tv = view.home_destination_text_view
        val terminal_tv = view.home_terminal_text_view
        val checkIn_tv = view.home_checkin_text_view
        val gate_tv = view.home_gate_text_view
        val remark_tv = view.home_remark_text_view

        airline_tv.text = data.airline
        flightId_tv.text = data.flightId
        gate_tv.text = data.gatenumber
        destination_tv.text = data.destination

        val scheduleDateTime = data.scheduleDateTime
        val date = scheduleDateTime?.let { DateTransferUtil.changeStringToDate(it) }
        arriveDate_tv.text = "${date?.get("year")} / ${date?.get("month")} / ${date?.get("day")}"
        arriveTime_tv.text = "${date?.get("hour")} : ${date?.get("minute")}" + " 출발"

        gate_tv.text = data.gatenumber ?: "-"
        remark_tv.text = data.remark ?: ""

        var modifiedCheckIn = data.chkinrange?.replace(" ", "     ")
        modifiedCheckIn =  modifiedCheckIn?.replace("-", "\n-\n")
        checkIn_tv.text = modifiedCheckIn

        terminal_tv.text = when(data.terminalid) {
            "P01" -> "제 1 터미널"
            "P02" -> "탑승동"
            "P03" -> "제 2 터미널"
            else -> ""
        }
    }

    fun setGateSummary(view: View, data: UserFlightInfoEntity) {
        val title = view.home_gate_summary_title_text_view

        val gate2 = view.home_gate_summary_gate2_text_view
        val gate5 = view.home_gate_summary_gate5_text_view

        val gate1Status = view.home_gate_summary_gate1_status_text_view
        val gate2Status = view.home_gate_summary_gate2_status_text_view
        val gate3Status = view.home_gate_summary_gate3_status_text_view
        val gate4Status = view.home_gate_summary_gate4_status_text_view
        val gate5Status = view.home_gate_summary_gate5_status_text_view
        val gate6Status = view.home_gate_summary_gate6_status_text_view

        val gate1CardView = view.home_gate_summary_gate1_card_view
        val gate3CardView = view.home_gate_summary_gate3_card_view
        val gate4CardView = view.home_gate_summary_gate4_card_view
        val gate6CardView = view.home_gate_summary_gate6_card_view


        when(data.terminalid) {
            //제 2 터미널일 경우
            "P03" -> {
                title.text = "제 2 터미널 현황"
                gate1CardView.alpha = 0.0F
                gate3CardView.alpha = 0.0F
                gate4CardView.alpha = 0.0F
                gate6CardView.alpha = 0.0F
                gate2.text = "1"
                gate5.text = "2"
            }

            else -> {
                title.text = "제 1 터미널 현황"
                gate1CardView.alpha = 1.0F
                gate3CardView.alpha = 1.0F
                gate4CardView.alpha = 1.0F
                gate6CardView.alpha = 1.0F
                gate2.text = "2"
                gate5.text = "5"
            }
        }
    }

    fun setPakringLotSummary(view: View, data: UserFlightInfoEntity) {
        val shortTitle = view.home_short_parking_title_text_view
        val longTitle = view.home_long_parking_title_text_view

        val shortState = view.home_short_parking_status_text_view
        val longState = view.home_long_parking_status_text_view

        val shortAvailability = view.home_short_parking_available_text_view
        val longAvailability = view.home_long_parking_available_text_view

        when(data.terminalid) {
            //제 2 터미널일 경우
            "P03" -> {
                val data = SharedData.sharedParkingData?.last()
                val short = data?.first()
                val long = data?.last()
                val mostEmptyShort = short?.maxBy { it.parkingarea.toInt() - it.parking.toInt() }
                val mostEmptyLong = long?.maxBy { it.parkingarea.toInt() - it.parking.toInt() }

                // 단기주차
                if (mostEmptyShort != null) {
                    setParkingStatus(
                        mostEmptyShort,
                        shortTitle,
                        shortState,
                        shortAvailability)
                }

                // 장기주차
                if (mostEmptyLong != null) {
                    setParkingStatus(
                        mostEmptyLong,
                        longTitle,
                        longState,
                        longAvailability)
                }
            }

            else -> {
                val data = SharedData.sharedParkingData?.first()
                val short = data?.first()
                val long = data?.last()
                val mostEmptyShort = short?.maxBy { it.parkingarea.toInt() - it.parking.toInt() }
                val mostEmptyLong = long?.maxBy { it.parkingarea.toInt() - it.parking.toInt() }

                // 단기주차
                if (mostEmptyShort != null) {
                    setParkingStatus(
                        mostEmptyShort,
                        shortTitle,
                        shortState,
                        shortAvailability)
                }

                // 장기주차
                if (mostEmptyLong != null) {
                    setParkingStatus(
                        mostEmptyLong,
                        longTitle,
                        longState,
                        longAvailability)
                }
            }
        }
    }

    fun setParkingStatus(data: ParkingLotVO, titleView: TextView, statusView: TextView, availableView: TextView) {

        val parkingArea = data.parkingarea.toInt()
        val parking = data.parking.toInt()
        val parkingAvailable = (parkingArea - parking)


        setParkingTitle(data.floor, titleView)

        if (parkingArea == 0) {
            statusView.setTextColor(Color.GRAY)
            statusView.text = "미운영"
            statusView.setTextColor(Color.RED)
            availableView.text = "주차 불가"
            availableView.setTextColor(Color.RED)
        }
        else {
            val parkingRatio = parkingAvailable.toDouble() / parkingArea.toDouble()

            availableView.text = "${parkingAvailable}대 가능"
            if (parkingRatio >= 0.5) {
                statusView.text = "원활"
                statusView.setTextColor(Color.parseColor("#1167b1"))
            }
            else if (parkingRatio >= 0.3) {
                statusView.text = "보통"
                statusView.setTextColor(Color.parseColor("#ff9a00"))
            }
            else {
                statusView.text = "혼잡"
                statusView.setTextColor(Color.RED)
            }
        }
    }

    fun setParkingTitle(parkingFloor: String, titleView: TextView) {
        if (parkingFloor.contains("단기")) {
            titleView.text = "단기 " + parkingFloor.substring(8)
        }
        // 장기 주차장일 경우 6번 인덱스부터 출력
        else {
            titleView.text = "장기 " + parkingFloor.substring(6)
        }
    }
}
