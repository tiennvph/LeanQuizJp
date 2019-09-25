package com.app.learnquizjp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.app.learnquizjp.R
import com.app.learnquizjp.model.Kotoba
import kotlinx.android.synthetic.main.item_kotoba.view.*

class KotobaAdapter : RecyclerView.Adapter<KotobaAdapter.KotobaHolder> {

    private var data : List<Kotoba>

    constructor(data : List<Kotoba>) : super() {
        this.data = data
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): KotobaHolder {
        return KotobaHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.item_kotoba,
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: KotobaHolder, i: Int) {
        holder.tvHiragana.text = data[i].KOTOBA_HIRAGANA
        holder.tvDescription.text = "Mean : " + data[i].KOTOBA_DESCRIPTION
        holder.tvKanji.text = "Kanji : " + data[i].KOTOBA_KANJI
        //holder.tvExample.text = data[i].KOTOBA_EXAMPLE
    }

    override fun getItemCount(): Int {
        return if (data == null) 0 else data.size
    }

    inner class KotobaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvHiragana : TextView = itemView.tvHiragana
        var tvDescription : TextView = itemView.tvDescription
        var tvKanji : TextView = itemView.tvKanji
        //var tvExample : TextView = itemView.tvExample

    }

}