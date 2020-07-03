package com.example.porte.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.porte.MainActivity
import com.example.porte.R
import com.example.porte.Shared.UserFlightInfoDatabase
import com.example.porte.Shared.UserFlightInfoEntity
import com.example.porte.Shared.UserInfoDatabase
import com.example.porte.Shared.UserInfoEntity
import com.example.porte.Util.ImageTransferUtil
import com.example.porte.ui.signInUp.Profile
import com.example.porte.ui.signInUp.SignIn
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val userDao by lazy { UserInfoDatabase.getDatabase(requireContext()).userInfoDAO() }
    private val flightDao by lazy { UserFlightInfoDatabase.getDatabase(requireContext()).userFlightInfoDAO() }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)


        val logoutBtn = root.findViewById<ImageView>(R.id.home_logout)
        val profileImageiew = root.findViewById<ImageView>(R.id.home_profile)



        logoutBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                userDao.deleteAllUserInfo()
                flightDao.deleteAllUserFlightInfo()
            }
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(requireContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), SignIn::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        profileImageiew.setOnClickListener{
            val intent = Intent(requireContext(), Profile::class.java)
            intent.putExtra("isFromSignIn", false)
            startActivity(intent)
        }

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("log", userDao.selectUserInfo(FirebaseAuth.getInstance().currentUser?.email!!).userName)
            val stringImg = userDao.selectUserInfo(FirebaseAuth.getInstance().currentUser?.email!!).userImg
            profileImageiew.setImageBitmap(ImageTransferUtil.changeStirngToBitmap(stringImg!!))
            profileImageiew.clipToOutline = true
        }


        return root
    }
}
