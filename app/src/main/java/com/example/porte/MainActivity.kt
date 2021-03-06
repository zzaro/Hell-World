package com.example.porte

import android.os.Bundle
import android.view.Window
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.porte.Shared.SharedData
import com.example.porte.ui.FlightInfo.FlightInfoFragment
import com.example.porte.ui.etc.EtcFragment
import com.example.porte.ui.gateInfo.GateInfoContainer
import com.example.porte.ui.home.HomeFragment
import com.example.porte.ui.parkingLotInfo.ParkingLotInfoFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val parkingLotInfoFragment = ParkingLotInfoFragment()
    private val homeFragment = HomeFragment()
    private val gateFragment = GateInfoContainer()
    private val flightInfoFragment = FlightInfoFragment()
    private val etcFragment = EtcFragment()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = homeFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 상단 타이틀바 제거
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)

        homeFragment.setMainActivity(this)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        fragmentManager.beginTransaction().apply {
            add(R.id.nav_host_fragment, parkingLotInfoFragment).hide(parkingLotInfoFragment)
            add(R.id.nav_host_fragment, gateFragment).hide(gateFragment)
            add(R.id.nav_host_fragment, homeFragment).hide(homeFragment)
            add(R.id.nav_host_fragment, flightInfoFragment).hide(flightInfoFragment)
            add(R.id.nav_host_fragment, etcFragment).hide(etcFragment)
        }.commit()
        initListeners()
        navView.selectedItemId = R.id.navigation_home

    }

    private fun initListeners() {
        nav_view.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_parkingLotInfo -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(parkingLotInfoFragment).commit()
                    activeFragment = parkingLotInfoFragment
                    true
                }

                R.id.navigation_home -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                    activeFragment = homeFragment
                    true
                }

                R.id.navigation_gateInfo_container -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(gateFragment).commit()
                    activeFragment = gateFragment
                    true
                }

                R.id.navigation_flightInfo -> {
                fragmentManager.beginTransaction().hide(activeFragment).show(flightInfoFragment).commit()
                activeFragment = flightInfoFragment
                true
            }

                R.id.navigation_etc -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(etcFragment).commit()
                    activeFragment = etcFragment
                    true
                }

                else -> false
            }
        }
    }

    fun goToFlightSearch() {
        fragmentManager.beginTransaction().hide(activeFragment).show(flightInfoFragment).commit()
        activeFragment = flightInfoFragment
        nav_view.selectedItemId = R.id.navigation_flightInfo
    }

    fun goToParkingLotInfo() {
        fragmentManager.beginTransaction().hide(activeFragment).show(parkingLotInfoFragment).commit()
        activeFragment = parkingLotInfoFragment
        nav_view.selectedItemId = R.id.navigation_parkingLotInfo
    }

    fun goToGateInfo() {
        fragmentManager.beginTransaction().hide(activeFragment).show(gateFragment).commit()
        activeFragment = gateFragment
        nav_view.selectedItemId = R.id.navigation_gateInfo_container
    }

}
