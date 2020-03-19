package com.gdc.mymoneymanager.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gdc.mymoneymanager.ui.category.CategoryFragment
import com.gdc.mymoneymanager.ui.spending.SpendingFragment
import com.gdc.mymoneymanager.ui.TransactionFragment

class MyPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val pages = listOf(
        SpendingFragment(),
            TransactionFragment(),
        CategoryFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Spending"
            1 -> "Transaction"
            else -> "Categories"
        }
    }
}