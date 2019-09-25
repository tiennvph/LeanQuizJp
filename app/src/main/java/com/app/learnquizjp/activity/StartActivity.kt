package com.app.learnquizjp.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.app.learnquizjp.R
import com.app.learnquizjp.base.MyBounceInterpolator
import java.util.*


class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        animateImageView()
        // Count down interval 3 second
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@StartActivity,LoginActivity::class.java))
                finish()
            }
        }, 3000)
    }

    private fun animateImageView() {
        // Load the animation
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        //val animationDuration = 0.2 * 1000
        //myAnim.duration = animationDuration.toLong()

        // Use custom animation interpolator to achieve the bounce effect
        val interpolator = MyBounceInterpolator(0.2,20.0)

        myAnim.interpolator = interpolator

        // Animate the button
        val img = findViewById<View>(R.id.imgLogo) as ImageView
        img.startAnimation(myAnim)

        // Run button animation again after it finished
        myAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(arg0: Animation) {}

            override fun onAnimationRepeat(arg0: Animation) {}

            override fun onAnimationEnd(arg0: Animation) {
                animateImageView()
            }
        })
    }

}
