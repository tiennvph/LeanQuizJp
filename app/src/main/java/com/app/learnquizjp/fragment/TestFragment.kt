package com.app.learnquizjp.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.learnquizjp.R
import com.app.learnquizjp.activity.TestActivity
import com.app.learnquizjp.base.Communication
import com.app.learnquizjp.model.Question
import kotlinx.android.synthetic.main.test_activity.*
import kotlinx.android.synthetic.main.test_fragment.*
import java.util.*


class TestFragment : Fragment(){
    val ARG_PAGE = "page"
    // total quiz checked
    var totalChecked: Int = 0
    //review
    var review:Boolean = false
    var totalDoNot: Int = 0
    var mPageNumber: Int = 0


    var loatASls: ArrayList<Question> = ArrayList()
    var dataChkQz: ArrayList<Question> = ArrayList()
    var listQuestionQri: ArrayList<Question> = ArrayList()
    lateinit var communication:Communication
    var activitiTest: TestActivity? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.test_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_bmquiz.text = "Câu " + (mPageNumber + 1)
        tv_question.text = loatASls[mPageNumber].qzcontent
        rad_answerA.text = loatASls[mPageNumber].ascortect
        rad_answerB.text = loatASls[mPageNumber].asincortecT1
        rad_answerC.text = loatASls[mPageNumber].asincortecT2
        rad_answerD.text = loatASls[mPageNumber].asincortecT3
        radGroupQz.setOnCheckedChangeListener { _, checkedId ->
            loatASls[mPageNumber].qzstatuschk=getChoiceFromID(checkedId)
            loatASls[mPageNumber].ascurrent=getTextFromID(checkedId)
            communication.dataChk(loatASls)
            for (i in 0..(loatASls.size-1)) {
                if (loatASls[i].qzstatuschk == 0) {
                    totalDoNot += 1
                }
            }
            totalChecked=35-totalDoNot
            activity!!.tv_status_test.text="Đã làm: $totalChecked/35"
            totalDoNot=0

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // call activy
        activitiTest = activity as TestActivity?

        if (activitiTest != null) {
            loatASls = activitiTest!!.getData()
        }
        mPageNumber = arguments!!.getInt(ARG_PAGE)


    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        communication=context as TestActivity
    }

    fun create(pageNumber: Int): TestFragment {
        val fragment: TestFragment = TestFragment()
        val args = Bundle()
        args.putInt(ARG_PAGE, pageNumber)
        fragment.arguments = args
        return fragment
    }

    private fun getChoiceFromID(ID: Int): Int {
        return when (ID) {
            R.id.rad_answerA -> 1
            R.id.rad_answerB -> 2
            R.id.rad_answerC -> 3
            R.id.rad_answerD -> 4
            else -> 0
        }
    }
    private fun getTextFromID(ID: Int): String {
        return when (ID) {
            R.id.rad_answerA -> rad_answerA.text.toString()
            R.id.rad_answerB -> rad_answerB.text.toString()
            R.id.rad_answerC -> rad_answerC.text.toString()
            R.id.rad_answerD -> rad_answerD.text.toString()
            else -> ""
        }
    }
}




