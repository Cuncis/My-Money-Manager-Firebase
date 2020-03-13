package com.gdc.mymoneymanager.ui.category

import com.gdc.mymoneymanager.util.Utils.Companion.showLog
import com.google.firebase.database.*

class CategoryPresenter(private val view: CategoryContract.View): CategoryContract.Presenter {

    private var database: DatabaseReference

    init {
        view.initActivity()
        view.initListener()
        database = FirebaseDatabase.getInstance().reference
    }

    override fun getExpense(username: String) {
        view.onLoading(true)
        database.child("Data")
                .child(username)
                .child("Category").child("Expense")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        view.onLoading(false)
                        view.onExpenseResult(dataSnapshot)
                    }
                    override fun onCancelled(p0: DatabaseError) {
                        view.onLoading(false)
                        view.showMessage("" + p0.message)
                        showLog("showData onCancelled: " + p0.message)
                    }
                })
    }

    override fun getIncome(username: String) {
        view.onLoading(true)
        database.child("Data")
                .child(username)
                .child("Category").child("Income")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        view.onLoading(false)
                        view.onIncomeResult(dataSnapshot)
                    }
                    override fun onCancelled(p0: DatabaseError) {
                        view.onLoading(false)
                        view.showMessage("" + p0.message)
                        showLog("showData onCancelled: " + p0.message)
                    }
                })
    }
}