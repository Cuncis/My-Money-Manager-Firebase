package com.gdc.mymoneymanager.data.model

data class Spending(var date: String? = "",
                    val title: String? = "",
                    var nominal: String? = "",
                    var category: String? = "",
                    var extra_notes: String? = "")