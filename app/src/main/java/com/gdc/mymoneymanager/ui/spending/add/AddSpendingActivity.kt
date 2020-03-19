package com.gdc.mymoneymanager.ui.spending.add

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.gdc.mymoneymanager.R
import com.gdc.mymoneymanager.data.PrefsManager
import com.gdc.mymoneymanager.data.model.Spending
import com.gdc.mymoneymanager.util.Constants.SPENDING_EXTRA
import com.gdc.mymoneymanager.util.Utils.Companion.getConvertDate
import com.gdc.mymoneymanager.util.Utils.Companion.showLog
import com.google.firebase.database.DataSnapshot
import kotlinx.android.synthetic.main.activity_add_spending.*
import java.util.*
import kotlin.collections.ArrayList

class AddSpendingActivity : AppCompatActivity(), AddSpendingContract.View {

    private lateinit var presenter: AddSpendingPresenter
    private var categoryList: ArrayList<String> = ArrayList()
    private var strTypeOfCategory = "Expense"
    private var strMessage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_spending)
        presenter = AddSpendingPresenter(this)
        presenter.getCategory(PrefsManager.getUsername(this), "Expense")
    }

    override fun initListener() {
        btn_expense.setOnClickListener {
            strTypeOfCategory = "Expense"
            categoryList.clear()
            btn_expense.setTextColor(resources.getColor(R.color.colorWhite))
            btn_income.setTextColor(resources.getColor(R.color.colorPrimary))
            btn_expense.setBackgroundDrawable(resources.getDrawable(R.drawable.cell_button_selected))
            btn_income.setBackgroundDrawable(resources.getDrawable(R.drawable.cell_button_unselected))
            et_category.setText("")
            presenter.getCategory(PrefsManager.getUsername(this), "Expense")
        }

        btn_income.setOnClickListener {
            strTypeOfCategory = "Income"
            categoryList.clear()
            btn_expense.setTextColor(resources.getColor(R.color.colorPrimary))
            btn_income.setTextColor(resources.getColor(R.color.colorWhite))
            btn_expense.setBackgroundDrawable(resources.getDrawable(R.drawable.cell_button_unselected))
            btn_income.setBackgroundDrawable(resources.getDrawable(R.drawable.cell_button_selected))
            et_category.setText("")
            presenter.getCategory(PrefsManager.getUsername(this), "Income")
        }

        et_date.setOnClickListener {
            dialogDatePicker()
        }

        et_category.setOnClickListener {
            dialogCategory()
        }

        btn_save.setOnClickListener {
            val spending = Spending()
            spending.date = "" + et_date.text.toString()
            spending.category = "" + et_category.text.toString()
            spending.nominal = "" + et_amount.text.toString()
            spending.extra_notes = "" + et_extraNotes.text.toString()
            presenter.inputSpending(PrefsManager.getUsername(this), strTypeOfCategory, spending)

            strMessage = if (strTypeOfCategory == "Expense") {
                "Expense Added Successfully"
            } else {
                "Income Added Successfully"
            }

            val intent = Intent()
            intent.putExtra(SPENDING_EXTRA, strMessage)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onCategoryResult(dataSnapshot: DataSnapshot) {
        for (data in dataSnapshot.children) {
            showLog("Category Data: $data")
            categoryList.add(data.child("name").value.toString())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun dialogDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, mYear, monthOfYear, dayOfMonth ->
            val mMonth = if ((monthOfYear+1) < 10) {
                "0${monthOfYear+1}"
            } else {
                "${monthOfYear+1}"
            }

            val mDay = if (dayOfMonth < 10) {
                "0$dayOfMonth"
            } else {
                "$dayOfMonth"
            }
            showLog("Date: $mDay-$mMonth-$year")
            showLog("Month: ${monthOfYear+1}")
            et_date.setText(getConvertDate("$mDay-$mMonth-$mYear"))
        }, year, month, day)
        datePickerDialog.show()
    }

    private fun dialogCategory() {

        val itemList = arrayOfNulls<String>(categoryList.size)
        categoryList.toArray(itemList)

        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_item, null)
        builder.setView(view)

        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList)

        val dialog = builder.create()

        val tvTitle = view.findViewById<TextView>(R.id.title_text)
        tvTitle.text = "Categories"
        val listview = view.findViewById(R.id.listview) as ListView
        listview.adapter = arrayAdapter
        listview.setOnItemClickListener { adapterView, view, i, l ->
            val strName = itemList[i]

            et_category.setText(strName)
            et_category.error = null
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
