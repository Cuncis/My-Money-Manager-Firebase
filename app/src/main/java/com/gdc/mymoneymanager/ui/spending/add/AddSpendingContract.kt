package com.gdc.mymoneymanager.ui.spending.add

import com.gdc.mymoneymanager.data.model.Spending
import com.google.firebase.database.DataSnapshot

interface AddSpendingContract {

    interface Presenter {
        fun inputSpending(username: String, type: String, spending: Spending)
        fun getCategory(username: String, type: String)
    }

    interface View {
        fun initListener()
        fun onCategoryResult(dataSnapshot: DataSnapshot)
        fun showMessage(message: String)
    }

}