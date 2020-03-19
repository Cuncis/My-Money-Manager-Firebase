package com.gdc.mymoneymanager.ui.spending


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gdc.mymoneymanager.R
import com.gdc.mymoneymanager.data.PrefsManager
import com.gdc.mymoneymanager.ui.spending.add.AddSpendingActivity
import com.gdc.mymoneymanager.util.Constants
import com.gdc.mymoneymanager.util.Constants.SPENDING_EXPENSE_CODE
import com.gdc.mymoneymanager.util.Constants.SPENDING_INCOME_CODE
import com.gdc.mymoneymanager.util.Utils.Companion.showLog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_spending.*


class SpendingFragment : Fragment(), SpendingContract.View {

    private lateinit var presenter: SpendingPresenter
    private lateinit var database: DatabaseReference

    private var totalIncome = 0
    private var totalExpense = 0
    private var totalBalance = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SpendingPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.getExpense(PrefsManager.getUsername(activity!!))
        presenter.getIncome(PrefsManager.getUsername(activity!!))
    }

    override fun initListener() {
        btn_expense.setOnClickListener {
            val intent = Intent(activity!!, AddSpendingActivity::class.java)
            startActivityForResult(intent, SPENDING_EXPENSE_CODE)
        }

        btn_income.setOnClickListener {
            val intent = Intent(activity!!, AddSpendingActivity::class.java)
            startActivityForResult(intent, SPENDING_INCOME_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SPENDING_EXPENSE_CODE && resultCode == Activity.RESULT_OK) {
            val strMessage = data?.getStringExtra(Constants.SPENDING_EXTRA)
            showMessage(strMessage!!)
        } else if (requestCode == SPENDING_INCOME_CODE && resultCode == Activity.RESULT_OK) {
            val strMessage = data?.getStringExtra(Constants.SPENDING_EXTRA)
            showMessage(strMessage!!)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onIncomeResult(dataSnapshot: DataSnapshot) {
        if (dataSnapshot.exists()) {
            for (data in dataSnapshot.children) {
                val nominal = data.child("nominal").value.toString()
                totalIncome += nominal.toInt()
                showLog("Income: $nominal")
            }
            tv_nominalIncome.text = "" + totalIncome

            totalBalance = totalIncome - totalExpense
            tv_nominalBalance.text = "" + totalBalance
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onExpenseResult(dataSnapshot: DataSnapshot) {
        if (dataSnapshot.exists()) {
            for (data in dataSnapshot.children) {
                val nominal = data.child("nominal").value.toString()
                totalExpense += nominal.toInt()
                showLog("Expense: $nominal")
            }
            tv_nominalExpense.text = "" + totalExpense
        }
    }

    override fun onLoading(loading: Boolean) {
        when (loading) {
            true -> {
                progressBar.visibility = View.VISIBLE
                linear_spending.visibility = View.GONE
            }
            false -> {
                progressBar.visibility = View.GONE
                linear_spending.visibility = View.VISIBLE
            }
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }


}
