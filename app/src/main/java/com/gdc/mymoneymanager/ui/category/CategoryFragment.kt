package com.gdc.mymoneymanager.ui.category


import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.gdc.mymoneymanager.R
import com.gdc.mymoneymanager.data.PrefsManager
import com.gdc.mymoneymanager.util.Utils.Companion.showLog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dialog_category.view.*
import kotlinx.android.synthetic.main.fragment_categories.*


class CategoryFragment : Fragment(), CategoryContract.View {

    private lateinit var categoryAdapter: CategoryAdapter
    private var categoryList: ArrayList<String> = ArrayList()
    private lateinit var presenter: CategoryPresenter
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = CategoryPresenter(this)
        presenter.getExpense(PrefsManager.getUsername(activity!!))
    }

    override fun initActivity() {
        database = FirebaseDatabase.getInstance().reference
        categoryAdapter = CategoryAdapter(activity!!)

        rv_expenseCategory.layoutManager = LinearLayoutManager(activity!!)
        rv_expenseCategory.setHasFixedSize(true)
        categoryAdapter.setCategoryList(categoryList)
        rv_expenseCategory.adapter = categoryAdapter

        rv_incomeCategory.layoutManager = LinearLayoutManager(activity!!)
        rv_incomeCategory.setHasFixedSize(true)
        categoryAdapter.setCategoryList(categoryList)
        rv_incomeCategory.adapter = categoryAdapter
    }

    override fun initListener() {
        fab_addCategory.setOnClickListener {
            dialogAddCategory()
        }

        btn_expense.setOnClickListener {
            btn_expense.setTextColor(resources.getColor(R.color.colorWhite))
            btn_income.setTextColor(resources.getColor(R.color.colorPrimary))
            btn_expense.setBackgroundDrawable(resources.getDrawable(R.drawable.cell_button_selected))
            btn_income.setBackgroundDrawable(resources.getDrawable(R.drawable.cell_button_unselected))
            rv_incomeCategory.visibility = View.GONE
            rv_expenseCategory.visibility = View.VISIBLE
            presenter.getExpense(PrefsManager.getUsername(activity!!))
        }

        btn_income.setOnClickListener {
            btn_expense.setTextColor(resources.getColor(R.color.colorPrimary))
            btn_income.setTextColor(resources.getColor(R.color.colorWhite))
            btn_expense.setBackgroundDrawable(resources.getDrawable(R.drawable.cell_button_unselected))
            btn_income.setBackgroundDrawable(resources.getDrawable(R.drawable.cell_button_selected))
            rv_incomeCategory.visibility = View.VISIBLE
            rv_expenseCategory.visibility = View.GONE
            presenter.getIncome(PrefsManager.getUsername(activity!!))
        }
    }

    override fun onExpenseResult(dataSnapshot: DataSnapshot) {

        categoryList.clear()

        for (data in dataSnapshot.children) {
            val category = data.child("name")
            showLog("show category list: ${category.value}")
            categoryList.add(category.value.toString())
        }
        categoryAdapter.setCategoryList(categoryList)
    }

    override fun onIncomeResult(dataSnapshot: DataSnapshot) {

        categoryList.clear()

        for (data in dataSnapshot.children) {
            val category = data.child("name")
            showLog("show category list: ${category.value}")
            categoryList.add(category.value.toString())
        }
        categoryAdapter.setCategoryList(categoryList)
    }

    override fun dialogAddCategory() {
        val builder = AlertDialog.Builder(activity!!)
        builder.setCancelable(true)
        val view = LayoutInflater.from(activity!!).inflate(R.layout.dialog_category, null)
        builder.setView(view)

        val dialog = builder.create()
        val categoryName = view.et_categoryName
        val rgCategory = view.rg_categoryDialog
        val rbExpense = view.rb_expenseDialog
        val rbIncome = view.rb_incomeDialog

        var strRadio = ""

        view.btn_addCategory.setOnClickListener {

            when (rgCategory.checkedRadioButtonId) {
                rbExpense.id -> {
                    strRadio = rbExpense.text.toString()
                }
                rbIncome.id -> {
                    strRadio = rbIncome.text.toString()
                }
                else -> {
                    showMessage("Choose Category First!")
                }
            }

            database = FirebaseDatabase.getInstance().reference
                    .child("Data").child(PrefsManager.getUsername(activity!!))
                    .child("Category").child(strRadio)

            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.ref.push().child("name").setValue(categoryName.text.toString())
                }
                override fun onCancelled(p0: DatabaseError) {
                    showLog("onCancelled: " + p0.message)
                }
            })
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onLoading(loading: Boolean) {
        when (loading) {
            true -> {
                progressBar.visibility = View.VISIBLE
                rv_expenseCategory.visibility = View.GONE
                rv_incomeCategory.visibility = View.GONE
            }
            false -> {
                progressBar.visibility = View.GONE
                rv_expenseCategory.visibility = View.VISIBLE
                rv_incomeCategory.visibility = View.VISIBLE
            }
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }


}
