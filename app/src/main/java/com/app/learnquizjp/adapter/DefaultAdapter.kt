package com.app.learnquizjp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.app.learnquizjp.R
import com.app.learnquizjp.model.Kotoba
import kotlinx.android.synthetic.main.item_kotoba.view.*

class DefaultAdapter : RecyclerView.Adapter<DefaultAdapter.ViewHolder>{

    private var data : MutableList<Kotoba>

    constructor(data : MutableList<Kotoba>) : super() {
        this.data = data
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.item_kotoba_favorite,
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.tvHiragana.text = data[i].KOTOBA_HIRAGANA
        holder.tvDescription.text = "Mean : " + data[i].KOTOBA_DESCRIPTION
        holder.tvKanji.text = "Kanji : " + data[i].KOTOBA_KANJI
    }

    override fun getItemCount(): Int {
        return if (data == null) 0 else data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHiragana : TextView = itemView.tvHiragana
        val tvDescription : TextView = itemView.tvDescription
        val tvKanji : TextView = itemView.tvKanji

    }
}