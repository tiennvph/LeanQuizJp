package com.app.learnquizjp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import com.app.learnquizjp.R
import com.app.learnquizjp.model.Question

class ChkAnswerAdapter : RecyclerView.Adapter<AnswerHolder> {
    override fun onBindViewHolder(holder: AnswerHolder, p1: Int) {
        holder.tv_position.text = (p1 + 1).toString()
        if (data[p1].qzstatuschk != 0) {
            when (data[p1].qzstatuschk) {
                1 -> holder.radioGroup.check(R.id.rad_a)
                2 -> holder.radioGroup.check(R.id.rad_b)
                3 -> holder.radioGroup.check(R.id.rad_c)
                4 -> holder.radioGroup.check(R.id.rad_d)
            }
        } else {
            holder.radioGroup.clearCheck()
        }
    }

    private var data: ArrayList<Question>

    constructor(data: ArrayList<Question>) : super() {
        this.data = data
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerHolder {
        return AnswerHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_resource_test, parent, false))
    }

}


class AnswerHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var radioGroup: RadioGroup = itemView!!.findViewById(R.id.rad_group)
        var tv_position: TextView = itemView!!.findViewById(R.id.tv_position)
        var ll_position: LinearLayout = itemView!!.findViewById(R.id.ll_position)

}


