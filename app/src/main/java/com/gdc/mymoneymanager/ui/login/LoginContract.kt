package com.gdc.mymoneymanager.ui.login

interface LoginContract {

    interface Presenter {
        fun doLogin(username: String, password: String)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoginResult(username: String)
        fun onLoading(loading: Boolean)
        fun showMessage(message: String)
    }

}