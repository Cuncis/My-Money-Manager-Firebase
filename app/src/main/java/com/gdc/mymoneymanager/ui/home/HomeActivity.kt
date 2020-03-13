package com.gdc.mymoneymanager.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gdc.mymoneymanager.R
import com.gdc.mymoneymanager.util.MyPagerAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        toolbar.title = "My Money Manager"

        viewpager_main.adapter = MyPagerAdapter(supportFragmentManager)
        tabs_main.setupWithViewPager(viewpager_main)
    }
}
