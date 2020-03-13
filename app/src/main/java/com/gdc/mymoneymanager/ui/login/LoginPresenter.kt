package com.gdc.mymoneymanager.ui.login

import com.gdc.mymoneymanager.util.Utils.Companion.showLog
import com.google.firebase.database.*

class LoginPresenter(private val view: LoginContract.View): LoginContract.Presenter {

    private lateinit var datebase: DatabaseReference

    init {
        view.initActivity()
        view.initListener()
    }

    override fun doLogin(username: String, password: String) {
        view.onLoading(true)
        datebase = FirebaseDatabase.getInstance().reference
                .child("Users").child(username)

        datebase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                view.onLoading(false)
                if (dataSnapshot.exists()) {
                    val dbPassword = dataSnapshot.child("password").value.toString()

                    if (dbPassword == password) {
                        view.onLoginResult(username)
                    } else {
                        view.showMessage("Password Incorrect!")
                    }
                } else {
                    view.showMessage("Username not found!")
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                view.onLoading(false)
                showLog("onCancelled: " + p0.message)
            }

        })
    }
}