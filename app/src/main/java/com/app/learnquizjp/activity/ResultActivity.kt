package com.app.learnquizjp.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.app.learnquizjp.R
import com.app.learnquizjp.base.ConstantsPro.Companion.DATACHKQZ
import com.app.learnquizjp.base.ConstantsPro.Companion.LISTQUESTIONQRI
import com.app.learnquizjp.model.Question
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.content_result.*

class ResultActivity : AppCompatActivity() {
    var listQuestionQri : ArrayList<Question> = ArrayList()
    var dataChkQz : ArrayList<Question> = ArrayList()
    var totalTrue: Int=0
    var totalFail: Int=0
    var totalDoNot: Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val intent = intent
        val bd = intent.extras
        if (bd != null) {
            listQuestionQri = bd.get(LISTQUESTIONQRI) as ArrayList<Question>
            dataChkQz = bd.get(DATACHKQZ) as ArrayList<Question>
        }
        for (i in 0..(dataChkQz.size-1)){
            if (dataChkQz[i].qzstatuschk==0){
                totalDoNot+=1
            }
            else if  (dataChkQz[i].ascurrent.equals(listQuestionQri[i].ascortect)){
                totalTrue+=1
            }
            else if (dataChkQz[i].ascurrent!="" &&dataChkQz[i].ascurrent.equals(listQuestionQri[i].ascortect)==false){
                totalFail+=1
            }

        }
        tv_result.text="${totalTrue}/${dataChkQz.size}"
        tv_totalTrue.text="${totalTrue}/${dataChkQz.size}"
        tv_totalFail.text="${totalFail}/${dataChkQz.size}"
        tv_totalDoNot.text="${totalDoNot}/${dataChkQz.size}"

            btn_review.setOnClickListener {
                var inreview = Intent(this@ResultActivity,ReviewActivity::class.java)

                if (dataChkQz.size!=0){
                    inreview.putExtra(LISTQUESTIONQRI,listQuestionQri)
                    inreview.putExtra(DATACHKQZ,dataChkQz)
                    startActivity(inreview)
                }
                else{
                    dialogEr()
                }

        }
    }
    private fun dialogEr() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.test_ac_up))
        builder.setMessage(getString(R.string.result_check))
        builder.setPositiveButton(getString(R.string.test_ac_accept)) { _, _ ->
            var intent: Intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }
        builder.setNegativeButton(getString(R.string.test_ac_cancel)) { _, _ ->

        }
        builder.show()
    }


}
