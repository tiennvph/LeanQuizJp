package com.app.learnquizjp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.app.learnquizjp.R
import com.app.learnquizjp.base.MyBounceInterpolator

class AboutFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_about, container, false)
        animateImageView(view)
        return view
    }

    private fun animateImageView(v : View) {
        // Load the animation
        val myAnim = AnimationUtils.loadAnimation(v.context, R.anim.bounce)
        //val animationDuration = 0.2 * 1000
        //myAnim.duration = animationDuration.toLong()

        // Use custom animation interpolator to achieve the bounce effect
        val interpolator = MyBounceInterpolator(0.2,20.0)

        myAnim.interpolator = interpolator

        // Animate the button
        val img = v.findViewById<View>(R.id.imgLogo) as ImageView
        img.startAnimation(myAnim)

        // Run button animation again after it finished
        myAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(arg0: Animation) {}

            override fun onAnimationRepeat(arg0: Animation) {}

            override fun onAnimationEnd(arg0: Animation) {
                animateImageView(v)
            }
        })
    }
}