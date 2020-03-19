package com.gdc.mymoneymanager.ui.spending

import com.google.firebase.database.DataSnapshot

interface SpendingContract {

    interface Presenter {
        fun getIncome(username: String)
        fun getExpense(username: String)
    }

    interface View {
//        fun initActivity()
        fun initListener()
        fun onIncomeResult(dataSnapshot: DataSnapshot)
        fun onExpenseResult(dataSnapshot: DataSnapshot)
        fun onLoading(loading: Boolean)
        fun showMessage(message: String)
    }

}