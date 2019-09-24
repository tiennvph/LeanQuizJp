package com.app.learnquizjp.activity

import android.annotation.SuppressLint
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.app.learnquizjp.fragment.TestFragment
import kotlinx.android.synthetic.main.test_activity.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import com.app.learnquizjp.adapter.ChkAnswerAdapter
import com.app.learnquizjp.base.Communication
import com.app.learnquizjp.base.RecyclerItemClickListener
import com.app.learnquizjp.model.Question
import kotlinx.android.synthetic.main.dialog_status_test.view.*
import android.content.Intent
import com.app.learnquizjp.R
import com.app.learnquizjp.base.ConstantsPro.Companion.DATACHKQZ
import com.app.learnquizjp.base.ConstantsPro.Companion.LISTQUESTION
import com.app.learnquizjp.base.ConstantsPro.Companion.LISTQUESTIONQRI


class TestActivity : AppCompatActivity(), Communication {

    val NUM_PAGES = 35
    // timer of test (minute)
    val TOTAL_TIMER: Long = 45
    // total quiz checked
    var totalChecked: Int = 0
    var mPager: ViewPager? = null
    var mPagerAdapter: PagerAdapter? = null
    // arr sort
    var listQuestion : ArrayList<Question> = ArrayList()
    var listQuestionQri : ArrayList<Question> = ArrayList()
    //  mang arr de load len man hinh
    lateinit var timer: CounterClass
    var dataChkQz : ArrayList<Question> = ArrayList()

    override fun dataChk(datachk: ArrayList<Question>) {
        dataChkQz = datachk
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.app.learnquizjp.R.layout.test_activity)
        val intent = intent
        val bd = intent.extras
        if (bd != null) {
            listQuestion = bd.get(LISTQUESTION) as ArrayList<Question>
            //
            listQuestionQri = bd.get(LISTQUESTIONQRI) as ArrayList<Question>
        }
        // creat timet count downl
        timer = CounterClass(TOTAL_TIMER * 60 * 1000, 1000)
        // creat arr test

        //creat arr test end
        mPager = findViewById<ViewPager>(com.app.learnquizjp.R.id.pager)
        mPagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        mPager!!.adapter = mPagerAdapter
        mPager!!.setPageTransformer(true, DepthPageTransformer())
        mPager!!.setPageTransformer(true, DepthPageTransformer())
        mPager!!.addOnPageChangeListener(viewPagerPageChangeListener)
       /* tv_status_test.text="Đã làm: ${totalChecked}/${NUM_PAGES}"*/
       //    start clook
        timer.start()

        // check status test
        tv_status_test.setOnClickListener {
            showStatusTestDialog()

        }
        btn_submit.setOnClickListener {
            dialogSubmit()
        }
    }

    // change viewpager
    private val viewPagerPageChangeListener = object : ViewPager.OnPageChangeListener {
        // nhận giá trị của view hiện tại
        override fun onPageSelected(position: Int) {
            tv_status_quiz.text = (position + 1).toString() + "/" + NUM_PAGES
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {
        }

        override fun onPageScrollStateChanged(arg0: Int) {

        }
    }

    fun getData(): ArrayList<Question> {
        return listQuestion
    }

    inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        var testFragment: TestFragment = TestFragment()
        // vị trí hiện tại của view page
        override fun getItem(position: Int): Fragment {
            return testFragment.create(position)
        }
        override fun getCount(): Int {
            return NUM_PAGES
        }
    }
    //animation
    inner class DepthPageTransformer : ViewPager.PageTransformer {
        val MIN_SCALE = 0.75f
        override fun transformPage(view: View, position: Float) {
            val pageWidth = view.width

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.alpha = 0f

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.alpha = 1f
                view.translationX = 0f
                view.scaleX = 1f
                view.scaleY = 1f

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.alpha = 1 - position

                // Counteract the default slide transition
                view.translationX = pageWidth * -position

                // Scale the page down (between MIN_SCALE and 1)
                val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.alpha = 0f
            }
        }


    }

    // set clock countDown
    inner class CounterClass
    /**
     * @param millisInFuture    The number of millis in the future from the call
     * to [.start] until the countdown is done and [.onFinish]
     * is called.
     * @param countDownInterval The interval along the way to receive
     * [.onTick] callbacks.
     */
    //millisInFuture: 60*1000
    //countDownInterval:  1000
        (millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onTick(millisUntilFinished: Long) {
            val countTime = String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                )
            )
            //setText of textview time
            tv_clook.text = countTime
        }

        override fun onFinish() {
            tv_clook.text = "00:00"  //SetText cho textview hiện thị thời gian.
            /*result()*/
        }
    }



    @SuppressLint("ResourceType")
    private fun showStatusTestDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        //then we will inflate the custom alert dialog xml that we created
        val dialogView = LayoutInflater.from(this).inflate(com.app.learnquizjp.R.layout.dialog_status_test, viewGroup, false)
        //Now we need an AlertDialog.Builder object
        val builder = AlertDialog.Builder(this)
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView)
        //finally creating the alert dialog and displaying it
        val alertDialog = builder.create()
        //Creatr adater
        for (i in 0..(dataChkQz.size-1)) {
            if (dataChkQz[i].qzstatuschk == 0) {
                totalChecked += 1
            }
        }
        dialogView.tv_totalChecked.text= "Đã làm: ${35-totalChecked} câu"
        totalChecked=0
        val answerAdapter = ChkAnswerAdapter(dataChkQz)
        var viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        // create recycler view
        var recyclerView = dialogView.rev_result_test
        recyclerView.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = answerAdapter
        }
        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(this@TestActivity, object : RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    mPager!!.currentItem = position
                    alertDialog.dismiss()
                }
            })
        )
        dialogView.btn_cancel.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    // submit dialog
    private fun dialogSubmit() {
        val builder = AlertDialog.Builder(this@TestActivity)
        builder.setTitle(getString(R.string.test_ac_up))
        builder.setMessage(getString(R.string.test_ac_up_check))
        builder.setPositiveButton(getString(R.string.test_ac_accept)) { _, _ ->
            var intent: Intent=Intent(this@TestActivity,ResultActivity::class.java)
            intent.putExtra(LISTQUESTIONQRI,listQuestionQri)
            intent.putExtra(DATACHKQZ,dataChkQz)
            startActivity(intent)
        }
        builder.setNegativeButton(getString(R.string.test_ac_cancel)) { _, _ ->

        }
        builder.show()
    }

}










