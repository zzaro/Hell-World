package com.example.portemainui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass.
 */
class MyPageFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_page2, container, false)
    }//end of onCreateView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        /* 아직 구현한 기능이 없어서 리스너는 주석 처리
        view. .setOnClickListener{

        }
        */

        findNavController().navigate(R.id.action_global_resultFragment)
    }//end of onViewCreated
}
