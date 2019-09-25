package com.app.learnquizjp.fragment

import android.animation.LayoutTransition
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.app.learnquizjp.R
import kotlinx.android.synthetic.main.fragment_grammar.view.*

class GrammarFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.fragment_grammar, container, false)
        var slide_down : Animation = AnimationUtils.loadAnimation(context,R.anim.slide_down)
        var slide_up : Animation = AnimationUtils.loadAnimation(context,R.anim.slide_up)
        view.linear.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        view.btnToggle1.setOnClickListener {
            var params : ViewGroup.LayoutParams = view.tvToggle1.layoutParams
            if(params.height == 0){
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            } else{
                params.height = 0
            }
            view.tvToggle1.startAnimation(slide_down)
            view.tvToggle1.layoutParams = params
        }

        view.btnToggle2.setOnClickListener {
            var params : ViewGroup.LayoutParams = view.tvToggle2.layoutParams
            if(params.height == 0){
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            } else{
                params.height = 0
            }
            view.tvToggle2.startAnimation(slide_down)
            view.tvToggle2.layoutParams = params
        }

        view.btnToggle3.setOnClickListener {
            var params : ViewGroup.LayoutParams = view.tvToggle3.layoutParams
            if(params.height == 0){
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            } else{
                params.height = 0
            }
            view.tvToggle3.startAnimation(slide_down)
            view.tvToggle3.layoutParams = params
        }

        view.btnToggle4.setOnClickListener {
            var params : ViewGroup.LayoutParams = view.tvToggle4.layoutParams
            if(params.height == 0){
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            } else{
                params.height = 0
            }
            view.tvToggle4.startAnimation(slide_down)
            view.tvToggle4.layoutParams = params
        }

        view.btnToggle5.setOnClickListener {
            var params : ViewGroup.LayoutParams = view.tvToggle5.layoutParams
            if(params.height == 0){
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            } else{
                params.height = 0
            }
            view.tvToggle5.startAnimation(slide_down)
            view.tvToggle5.layoutParams = params
        }

        view.btnToggle6.setOnClickListener {
            var params : ViewGroup.LayoutParams = view.tvToggle6.layoutParams
            if(params.height == 0){
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            } else{
                params.height = 0
            }
            view.tvToggle6.startAnimation(slide_down)
            view.tvToggle6.layoutParams = params
        }

        return view
    }
}