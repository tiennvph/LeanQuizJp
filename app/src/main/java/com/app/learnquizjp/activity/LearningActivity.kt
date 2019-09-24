package com.app.learnquizjp.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.app.learnquizjp.R
import kotlinx.android.synthetic.main.activity_learning.*

class LearningActivity : AppCompatActivity(),View.OnClickListener {


    override fun onClick(v: View?) {
        var id : Int = v!!.id
        when(id){
            R.id.imgN1 -> onActionChooseLearningLevel(1)
            R.id.imgN2 -> onActionChooseLearningLevel(2)
            R.id.imgN3 -> onActionChooseLearningLevel(3)
            R.id.imgN4 -> onActionChooseLearningLevel(4)
            R.id.imgN5 -> onActionChooseLearningLevel(5)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learning)

        imgN1.setOnClickListener(this)
        imgN2.setOnClickListener(this)
        imgN3.setOnClickListener(this)
        imgN4.setOnClickListener(this)
        imgN5.setOnClickListener(this)
    }

    private fun onActionChooseLearningLevel(level : Int){
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("N$level level required 2000 hour to master, do you want to start learning ?")
        dialog.setNegativeButton("Yes") { _, _ ->
            var intent = Intent(this@LearningActivity,ChooseLevelLearningActivity::class.java)
            intent.putExtra("level",level)
            startActivity(intent)
        }
        dialog.setPositiveButton(
            "No"
        ) { dialog, _ -> dialog.dismiss() }
        dialog.show()
    }
}
