package com.app.learnquizjp.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_quiz.*
import com.app.learnquizjp.R


class QuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        tvPractice.setOnClickListener {
            startActivity(Intent(this,ChooseLevelTestActivity::class.java))
        }
        tvTest.setOnClickListener {
            startActivity(Intent(this,ChooseLevelTestActivity::class.java))
        }
    }

}
