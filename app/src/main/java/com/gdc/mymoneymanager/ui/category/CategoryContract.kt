package com.gdc.mymoneymanager.ui.category

import com.google.firebase.database.DataSnapshot

interface CategoryContract {

    interface Presenter {
        fun getExpense(username: String)
        fun getIncome(username: String)
    }

    interface  View {
        fun initActivity()
        fun initListener()
        fun onExpenseResult(dataSnapshot: DataSnapshot)
        fun onIncomeResult(dataSnapshot: DataSnapshot)
        fun dialogAddCategory()
        fun onLoading(loading: Boolean)
        fun showMessage(message: String)
    }

}