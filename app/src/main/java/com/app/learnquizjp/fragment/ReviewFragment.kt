package com.app.learnquizjp.fragment

import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.app.learnquizjp.R
import com.app.learnquizjp.activity.ReviewActivity
import com.app.learnquizjp.model.Question
import kotlinx.android.synthetic.main.dialog_show_answer.view.*
import kotlinx.android.synthetic.main.fragment_review.*
import java.util.*

class ReviewFragment : Fragment() {
    val ARG_PAGE_R = "page"
    // total quiz checked
    var totalChecked: Int = 0
    //review
    var mPageNumber: Int = 0
    var loatASls: ArrayList<Question> = ArrayList()
    var activitiReview: ReviewActivity? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_bmquiz_r.text = "Câu " + (mPageNumber + 1)
        tv_question_r.text = loatASls[mPageNumber].qzcontent
        rad_answerA_r.text = loatASls[mPageNumber].ascortect
        rad_answerB_r.text = loatASls[mPageNumber].asincortecT1
        rad_answerC_r.text = loatASls[mPageNumber].asincortecT2
        rad_answerD_r.text = loatASls[mPageNumber].asincortecT3
        when (loatASls[mPageNumber].qzstatuschk) {
            1 -> { rad_answerA_r.isChecked=true }
            2 -> { rad_answerB_r.isChecked=true }
            3 -> { rad_answerC_r.isChecked=true }
            4 -> { rad_answerD_r.isChecked=true }
        }
        btn_show_answer.setOnClickListener {
            val dialog = BottomSheetDialog(this.activity!!)
            val view = layoutInflater.inflate(R.layout.dialog_show_answer, null)
            val close = view.findViewById<ImageView>(R.id.iv_close)
            close.setOnClickListener {
                dialog.dismiss()
            }
            view.tv_answer.text=loatASls[mPageNumber].ascurrent
            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // call activy
        activitiReview = activity as ReviewActivity?

        if (activitiReview != null) {
            loatASls = activitiReview!!.getData()

        }
        mPageNumber = arguments!!.getInt(ARG_PAGE_R)

    }

    fun create(pageNumber: Int): ReviewFragment {
        val fragment = ReviewFragment()
        val args = Bundle()
        args.putInt(ARG_PAGE_R, pageNumber)
        fragment.arguments = args
        return fragment
    }

}


