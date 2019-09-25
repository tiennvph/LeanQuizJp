package com.app.learnquizjp.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import com.app.learnquizjp.R
import com.app.learnquizjp.base.ConstantsPro.Companion.JSON_FILE
import com.app.learnquizjp.base.ConstantsPro.Companion.LISTQUESTION
import com.app.learnquizjp.base.ConstantsPro.Companion.LISTQUESTIONQRI
import com.app.learnquizjp.base.MyBounceInterpolator
import com.app.learnquizjp.model.ABCDQuestion
import com.app.learnquizjp.model.Question
import com.app.learnquizjp.service.QuestionService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_prepare.*
import kotlinx.android.synthetic.main.content_prepare.*
import java.io.IOException
import java.nio.charset.Charset

class PrepareActivity : AppCompatActivity() {

    val charset: Charset = Charsets.UTF_8
    // list for change position
    var lsQS: ArrayList<String> = ArrayList()
    var arrtest: ArrayList<Question> = ArrayList()
    var listQuestion: ArrayList<Question> = ArrayList()
    val arrAnswer: ArrayList<ABCDQuestion> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.app.learnquizjp.R.layout.activity_prepare)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        animateButton()
        // get data by json sever
        listQuestion= getData() as ArrayList<Question>
        arrtest=listQuestion
        //sort
        for (i in 0..34) {
            var abcdQuestion = ABCDQuestion()
            lsQS.add(arrtest[i].ascortect!!)
            lsQS.add(arrtest[i].asincortecT1!!)
            lsQS.add(arrtest[i].asincortecT2!!)
            lsQS.add(arrtest[i].asincortecT3!!)
            lsQS.shuffle()
            abcdQuestion.ascortect = lsQS[0]
            abcdQuestion.asincortecT1 = lsQS[1]
            abcdQuestion.asincortecT2 = lsQS[2]
            abcdQuestion.asincortecT3 = lsQS[3]
            arrAnswer.add(abcdQuestion)
            arrtest[i].ascortect = arrAnswer[i].ascortect
            arrtest[i].asincortecT1 = arrAnswer[i].asincortecT1
            arrtest[i].asincortecT2 = arrAnswer[i].asincortecT2
            arrtest[i].asincortecT3 = arrAnswer[i].asincortecT3
            lsQS.removeAll(lsQS)
        }

        btn_start.setOnClickListener { onStartQuiz() }
    }

    private fun onStartQuiz(){
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(getString(R.string.prepare_ac_up))
        dialog.setMessage(getString(R.string.prepare_ac_up_check))
        dialog.setNegativeButton(getString(R.string.prepare_ac_yes)) { _, _ ->
            var intent = Intent(this@PrepareActivity,TestActivity::class.java)
            intent.putExtra(LISTQUESTION,arrtest)
            //load to db
            intent.putExtra(LISTQUESTIONQRI,listQuestion)
            startActivity(intent)
        }
        dialog.setPositiveButton(getString(R.string.prepare_ac_no)) { dialog, _ -> dialog.dismiss() }
        dialog.show()
    }

    internal fun animateButton() {
        // Load the animation
        val myAnim = AnimationUtils.loadAnimation(this, com.app.learnquizjp.R.anim.bounce)
        //val animationDuration = 0.2 * 1000
        //myAnim.duration = animationDuration.toLong()

        // Use custom animation interpolator to achieve the bounce effect
        val interpolator = MyBounceInterpolator(0.2,20.0)

        myAnim.interpolator = interpolator

        // Animate the button
        val button = findViewById<View>(com.app.learnquizjp.R.id.btn_start) as Button
        button.startAnimation(myAnim)

        // Run button animation again after it finished
        myAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(arg0: Animation) {}

            override fun onAnimationRepeat(arg0: Animation) {}

            override fun onAnimationEnd(arg0: Animation) {
                animateButton()
            }
        })
    }
    /* Convert JSON String Model via GSON */
    fun getData(): List<Question> {
        val jsonString = getAssetsJSON(JSON_FILE)
        val gson = Gson()
        val base = gson.fromJson(jsonString, QuestionService::class.java)
        return base.question
    }
    /* Get File in Assets Folder */
    private fun getAssetsJSON(fileName: String): String? {
        var json: String? = null
        try {
            val inputStream = this.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return json
    }
}