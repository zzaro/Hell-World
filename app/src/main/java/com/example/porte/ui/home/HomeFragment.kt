package com.example.porte.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.porte.MainActivity
import com.example.porte.R
import com.example.porte.Shared.UserFlightInfoDatabase
import com.example.porte.Shared.UserFlightInfoEntity
import com.example.porte.Shared.UserInfoDatabase
import com.example.porte.Shared.UserInfoEntity
import com.example.porte.Util.DateTransferUtil
import com.example.porte.Util.ImageTransferUtil
import com.example.porte.ui.signInUp.Profile
import com.example.porte.ui.signInUp.SignIn
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.sign

class HomeFragment : Fragment() {

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
            }
            else {
                noflightInfoLayout.isVisible = true
            }
        })

//        signOut()

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
}
