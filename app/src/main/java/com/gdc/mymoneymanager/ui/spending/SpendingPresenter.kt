package com.gdc.mymoneymanager.ui.spending

import com.gdc.mymoneymanager.util.Utils.Companion.showLog
import com.google.firebase.database.*

class SpendingPresenter(private val view: SpendingContract.View): SpendingContract.Presenter {

    private lateinit var database: DatabaseReference

    init {
//        view.initActivity()
        view.initListener()
    }

    override fun getIncome(username: String) {
        view.onLoading(true)
        database = FirebaseDatabase.getInstance().reference.child("Data")
            .child(username).child("Spending").child("Income")
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                view.onLoading(false)
                view.onIncomeResult(dataSnapshot)
            }
            override fun onCancelled(p0: DatabaseError) {
                view.onLoading(false)
                showLog("getIncome Error: ${p0.message}")
            }
        })
    }

    override fun getExpense(username: String) {
        view.onLoading(true)
        database = FirebaseDatabase.getInstance().reference.child("Data")
            .child(username).child("Spending").child("Expense")
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                view.onLoading(false)
                view.onExpenseResult(dataSnapshot)
            }
            override fun onCancelled(p0: DatabaseError) {
                view.onLoading(false)
                showLog("getIncome Error: ${p0.message}")
            }
        })
    }
}