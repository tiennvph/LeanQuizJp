package com.app.learnquizjp.base

import com.app.learnquizjp.model.Question

interface Communication {
    fun dataChk(datachk : ArrayList<Question>)
}