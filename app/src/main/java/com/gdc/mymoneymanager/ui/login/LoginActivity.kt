package com.gdc.mymoneymanager.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.gdc.mymoneymanager.ui.home.HomeActivity
import com.gdc.mymoneymanager.R
import com.gdc.mymoneymanager.data.PrefsManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(this)
        if (PrefsManager.isLogin(this)) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun initActivity() {
        toolbar.title = "Login My Money Manager"
    }

    override fun initListener() {
        btn_login.setOnClickListener {
            presenter.doLogin(et_username.text.toString(), et_password.text.toString())
        }
    }

    override fun onLoginResult(username: String) {
        PrefsManager.setUsername(this, username)
        PrefsManager.saveLoginState(this, true)

        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onLoading(loading: Boolean) {
        when (loading) {
            true -> {
                btn_login.isEnabled = false
                btn_login.text = "Loading..."
            }
            false -> {
                btn_login.isEnabled = true
                btn_login.text = "Login"
            }
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
