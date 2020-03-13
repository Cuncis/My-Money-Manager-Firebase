package com.gdc.mymoneymanager.ui.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gdc.mymoneymanager.R
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(private val context: Context): RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    private var categoryList: List<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(LayoutInflater.from(context).inflate(R.layout.item_category, parent, false))
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.textCategory.text = categoryList[position]
    }

    fun setCategoryList(categoryList: List<String>) {
        this.categoryList = categoryList;
        notifyDataSetChanged()
    }

    inner class CategoryHolder(view: View): RecyclerView.ViewHolder(view) {
        val textCategory: TextView = view.tv_textCategory
    }

}