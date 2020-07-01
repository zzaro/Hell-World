package com.example.porte.ui.gateInfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.creageek.segmentedbutton.SegmentedButton
import com.example.porte.R

//import sun.jvm.hotspot.utilities.IntArray


/**
 * A simple [Fragment] subclass.
 */
class GateInfoContainer : Fragment() {

    private val gate1 by lazy {
        GateInfo()
    }
    private val gate2 by lazy {
        GateInfo2()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_gate_info_container, container, false)

        setChildFragment(gate1)

        // Segment Control 초기화
        val segmented: SegmentedButton = root.findViewById(R.id.gateInfo_container_segment_control)
        segmented {
            // set initial checked segment (null by default)
            initialCheckedIndex = 0

            // init with segments programmatically without RadioButton as a child in xml
            initWithItems {
                // takes only list of strings
                listOf("제1터미널", "제2터미널")
            }

            // notifies when segment was checked
            onSegmentChecked { segment ->
                Log.d("creageek:segmented", "Segment ${segment.text} checked")
                when (segment.text) {
                    "제1터미널" -> {
                        setChildFragment(gate1);
                    }

                    "제2터미널" -> {
                        setChildFragment(gate2);
                    }

                }
            }
            // notifies when segment was unchecked
            onSegmentUnchecked { segment ->
                Log.d("creageek:segmented", "Segment ${segment.text} unchecked")
            }
            // notifies when segment was rechecked
            onSegmentRechecked { segment ->
                Log.d("creageek:segmented", "Segment ${segment.text} rechecked")
            }
        }

        return root
    }

    private fun setChildFragment(child: Fragment) {
        val childFt: FragmentTransaction = childFragmentManager.beginTransaction()
        if (!child.isAdded) {
            childFt.replace(R.id.gateInfo_container_fragment, child)
            childFt.addToBackStack(null)
            childFt.commit()
        }
    }

}
