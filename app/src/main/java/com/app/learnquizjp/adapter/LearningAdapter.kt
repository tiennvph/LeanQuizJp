package com.app.learnquizjp.adapter

import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.learnquizjp.R.*

import kotlinx.android.synthetic.main.item_learning.view.*


class LearningAdapter : RecyclerView.Adapter<LearningAdapter.LearningHolder> {

    private var data : List<String>

    constructor(data : List<String>) : super() {
        this.data = data
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): LearningHolder {
        return LearningHolder(LayoutInflater.from(viewGroup.context).inflate(layout.item_learning, viewGroup, false))
    }

    override fun onBindViewHolder(holder: LearningHolder, i: Int) {
        holder.tvLesson.text = "Lesson " + (i + 1)
        holder.tvTitle.text = data[i]
    }

    override fun getItemCount(): Int {
        return if (data == null) 0 else data.size
    }

    inner class LearningHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvLesson: TextView = itemView.tvLesson
        var tvTitle: TextView = itemView.tvTitle
    }

}