package com.example.portemainui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NavigationUI.setupWithNavController(
            nav_view, findNavController(R.id.navigation_host)
        )

        NavigationUI.setupActionBarWithNavController(
            this,
            findNavController(R.id.navigation_host),
            AppBarConfiguration(
                setOf(
                    R.id.parkingFragment,
                    R.id.departureFragment,
                    R.id.myPageFragment2,
                    R.id.flightFragment,
                    R.id.ETCFragment
                )
            )
        )
    }//end of onCreate

    //뒤로가기
    override fun onSupportNavigateUp() = findNavController(R.id.navigation_host).navigateUp()
}//end of MainActivity