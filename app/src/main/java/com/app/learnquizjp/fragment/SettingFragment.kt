package com.app.learnquizjp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.learnquizjp.R
import kotlinx.android.synthetic.main.fragment_setting.view.*

class SettingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_setting, container, false)
        view.switchOnOff.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                // The toggle is enabled
            }else{
                // The toggle is disabled
            }
        }
        return view
    }
}