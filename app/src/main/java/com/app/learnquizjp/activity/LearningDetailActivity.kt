package com.app.learnquizjp.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.app.learnquizjp.R
import com.app.learnquizjp.fragment.GrammarFragment
import com.app.learnquizjp.fragment.KanjiFragment
import com.app.learnquizjp.fragment.WordFragment
import kotlinx.android.synthetic.main.activity_learning_detail.*

class LearningDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learning_detail)
        loadFragment(savedInstanceState)
    }

    private fun loadFragment(savedInstanceState : Bundle?){
        val fragmentList : MutableList<Fragment> = mutableListOf()
        fragmentList.add(WordFragment())
        fragmentList.add(GrammarFragment())
        fragmentList.add(KanjiFragment())
        navigation.initialize(viewPager,supportFragmentManager,fragmentList,savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        navigation.saveState(outState)
        super.onSaveInstanceState(outState)
    }

}
