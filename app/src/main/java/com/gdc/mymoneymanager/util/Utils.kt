package com.gdc.mymoneymanager.util

import android.util.Log
import com.gdc.mymoneymanager.util.Constants.TAG
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        fun showLog(message: String) {
            Log.d(TAG, message)
        }

        fun getConvertDate(inputDate: String): String {
            val parsed: Date?
            var outputDated = ""

            val df_input = SimpleDateFormat("dd-MM-yyyy", Locale.US)
            val df_output = SimpleDateFormat("d MMM, yyyy", Locale.US)

            try {
                parsed = df_input.parse(inputDate)
                outputDated = df_output.format(parsed)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return outputDated
        }
    }
}