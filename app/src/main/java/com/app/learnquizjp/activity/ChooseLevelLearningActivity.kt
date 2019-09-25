package com.app.learnquizjp.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.app.learnquizjp.R
import com.app.learnquizjp.adapter.LearningAdapter
import com.app.learnquizjp.base.RecyclerItemClickListener
import kotlinx.android.synthetic.main.activity_choose_level_learning.*
import kotlinx.android.synthetic.main.content_choose_level_learning.*

class ChooseLevelLearningActivity : AppCompatActivity() {

    private var data : MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_level_learning)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        addLessonData()
        addLessonList()

    }

    private fun addLessonData(){
        data.add(0,"はじめまして")
        data.add(1,"ほんの気持ちです")
        data.add(2,"これをください")
        data.add(3,"そちらは何時から何時までですか")
        data.add(4,"甲子園へ行きますか")
        data.add(5,"いっしょにいきませんか")
        data.add(6,"ごめんください")
        data.add(7,"そろそろ失礼します")
        data.add(8,"残念です")
        data.add(9,"チリソースがありませんか")
    }

    private fun addLessonList(){
        var learningAdapter = LearningAdapter(data)
        var linearLayoutManager = LinearLayoutManager(this)
        rvLesson.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = learningAdapter
        }
        rvLesson.addOnItemTouchListener(
            RecyclerItemClickListener(this@ChooseLevelLearningActivity,object : RecyclerItemClickListener.OnItemClickListener{
                override fun onItemClick(view: View, position: Int) {
                    startActivity(Intent(this@ChooseLevelLearningActivity,LearningDetailActivity::class.java))
                }
            })
        )
    }

}
