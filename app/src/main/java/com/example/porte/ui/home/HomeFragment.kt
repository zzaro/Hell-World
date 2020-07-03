package com.example.porte.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.porte.R
import com.example.porte.Shared.UserInfoDatabase
import com.example.porte.Shared.UserInfoEntity
import com.example.porte.Util.ImageTransferUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val dao by lazy { UserInfoDatabase.getDatabase(requireContext()).userInfoDAO() }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        root.findViewById<Button>(R.id.home_buttom).setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                dao.deleteAllUserInfo()
            }
            FirebaseAuth.getInstance().signOut()
        }



        CoroutineScope(Dispatchers.IO).launch {
            Log.d("log", dao.selectUserInfo(FirebaseAuth.getInstance().currentUser?.email!!).userName)
            val stringImg = dao.selectUserInfo(FirebaseAuth.getInstance().currentUser?.email!!).userImg
            root.findViewById<ImageView>(R.id.home_imageView).setImageBitmap(ImageTransferUtil.changeStirngToBitmap(stringImg!!))
        }


        return root
    }
}
