package com.gdc.mymoneymanager.ui.spending.add

import com.gdc.mymoneymanager.data.PrefsManager
import com.gdc.mymoneymanager.data.model.Spending
import com.gdc.mymoneymanager.util.Utils
import com.google.firebase.database.*

class AddSpendingPresenter(private val view: AddSpendingContract.View): AddSpendingContract.Presenter {

    private lateinit var database: DatabaseReference

    init {
        view.initListener()
    }

    override fun inputSpending(username: String, type: String, spending: Spending) {
        database = FirebaseDatabase.getInstance().reference.child("Data")
                .child(username).child("Spending")
                .child(type).push()
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.ref.child("date").setValue(spending.date)
                dataSnapshot.ref.child("nameOfCategory").setValue(spending.category)
                dataSnapshot.ref.child("nominal").setValue(spending.nominal)
                dataSnapshot.ref.child("extra_notes").setValue(spending.extra_notes)
            }
            override fun onCancelled(p0: DatabaseError) {
                Utils.showLog("Income Error: " + p0.message)
            }
        })
    }

    override fun getCategory(username: String, type: String) {
        database = FirebaseDatabase.getInstance().reference.child("Data")
                .child(username).child("Category")
                .child(type)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                view.onCategoryResult(dataSnapshot)
            }
            override fun onCancelled(p0: DatabaseError) {
                Utils.showLog("Category Error: " + p0.message)
            }
        })
    }

}