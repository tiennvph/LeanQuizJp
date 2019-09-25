package com.app.learnquizjp.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.app.learnquizjp.R
import kotlinx.android.synthetic.main.activity_choose_lv.*

class ChooseLevelTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_lv)
        tv_lever_N5.setOnClickListener {
            startActivity(Intent(this@ChooseLevelTestActivity,PrepareActivity::class.java))
        }
    }
}
