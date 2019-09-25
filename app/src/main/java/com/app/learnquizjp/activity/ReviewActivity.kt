package com.app.learnquizjp.activity

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.learnquizjp.adapter.ChkAnswerAdapter
import com.app.learnquizjp.base.ConstantsPro.Companion.DATACHKQZ
import com.app.learnquizjp.base.ConstantsPro.Companion.LISTQUESTIONQRI
import com.app.learnquizjp.base.RecyclerItemClickListener
import com.app.learnquizjp.fragment.ReviewFragment
import com.app.learnquizjp.model.Question
import kotlinx.android.synthetic.main.activity_review.*
import kotlinx.android.synthetic.main.dialog_status_test.view.*

class ReviewActivity : AppCompatActivity() {
    val NUM_PAGES = 35
    // total quiz checked
    var totalChecked: Int = 0
    var mPager: ViewPager? = null
    var mPagerAdapter: PagerAdapter? = null
    // list danh sach da dao
    var listQuestion: ArrayList<Question> = ArrayList()
    var listQuestionQri: ArrayList<Question> = ArrayList()
    var dataChkQz: ArrayList<Question> = ArrayList()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.app.learnquizjp.R.layout.activity_review)
        val intent = intent
        val bd = intent.extras
        if (bd != null) {
            listQuestion = bd.get(DATACHKQZ) as ArrayList<Question>
            listQuestionQri = bd.get(LISTQUESTIONQRI) as ArrayList<Question>
        }

        mPager = findViewById<ViewPager>(com.app.learnquizjp.R.id.page_r)
        mPagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        mPager!!.adapter = mPagerAdapter
        mPager!!.setPageTransformer(true, DepthPageTransformer())
        mPager!!.setPageTransformer(true, DepthPageTransformer())
        mPager!!.addOnPageChangeListener(viewPagerPageChangeListener)
        // check status test
        tv_review.setOnClickListener {
            showStatusTestDialog()
        }
    }
    // change viewpager
    val viewPagerPageChangeListener = object : ViewPager.OnPageChangeListener {
        // nhận giá trị của view hiện tại
        override fun onPageSelected(position: Int) {
            tv_status_quiz_r.text = (position + 1).toString() + "/" + NUM_PAGES
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
        var reviewFragment: ReviewFragment = ReviewFragment()
        // vị trí hiện tại của view page
        override fun getItem(position: Int): Fragment {
            return reviewFragment.create(position)
        }

        override fun getCount(): Int {
            return NUM_PAGES
        }
    }

    //annimation
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

    private fun showStatusTestDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        //then we will inflate the custom alert dialog xml that we created
        val dialogView =
            LayoutInflater.from(this).inflate(com.app.learnquizjp.R.layout.dialog_status_test, viewGroup, false)
        //Now we need an AlertDialog.Builder object
        val builder = AlertDialog.Builder(this)
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView)
        //finally creating the alert dialog and displaying it
        val alertDialog = builder.create()
        //Creatr adater
        for (i in 0..(dataChkQz.size - 1)) {
            if (dataChkQz[i].qzstatuschk == 0) {
                totalChecked += 1
            }
        }
        dialogView.tv_totalChecked.text = "Đã làm: ${35 - totalChecked} câu"
        totalChecked = 0
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
            RecyclerItemClickListener(this@ReviewActivity, object : RecyclerItemClickListener.OnItemClickListener {
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


}
