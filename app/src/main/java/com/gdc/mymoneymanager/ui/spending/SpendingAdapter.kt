package com.gdc.mymoneymanager.ui.spending

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gdc.mymoneymanager.R
import com.gdc.mymoneymanager.data.model.Spending
import kotlinx.android.synthetic.main.item_spending.view.*

class SpendingAdapter(private val context: Context): RecyclerView.Adapter<SpendingAdapter.SpendingHolder>() {

    private var spendingList: List<Spending> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendingHolder {
        return SpendingHolder(LayoutInflater.from(context).inflate(R.layout.item_spending, parent, false))
    }

    override fun getItemCount(): Int = spendingList.size

    override fun onBindViewHolder(holder: SpendingHolder, position: Int) {
        holder.bind(spendingList[position])
    }

    inner class SpendingHolder(view: View): RecyclerView.ViewHolder(view) {
        private val title: TextView = view.tv_title
        private val nominal: TextView = view.tv_nominal

        fun bind(spending: Spending) {
            title.text = spending.title
            nominal.text = spending.nominal.toString()
        }
    }

}