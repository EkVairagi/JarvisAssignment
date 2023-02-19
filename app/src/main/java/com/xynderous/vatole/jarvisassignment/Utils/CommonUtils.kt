package com.xynderous.vatole.jarvisassignment.Utils

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*


object CommonUtils {

    private lateinit var datePickerDialog: DatePickerDialog

    fun showDatePickerWithButton(
        textView: WeakReference<AppCompatEditText>,
        act: WeakReference<FragmentActivity?>
    ) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = calendar.get(Calendar.DAY_OF_MONTH)
        act.get()?.let { activity ->
            val datePickerDialog = DatePickerDialog(
                activity,
                { datePicker: DatePicker, year: Int, month: Int, date: Int ->
                    textView.get()?.let { tv ->
                        val cal = Calendar.getInstance()
                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, month)
                        cal.set(Calendar.DAY_OF_MONTH, date)
                        val sdf =
                            SimpleDateFormat(ApplicationConstants.DDMMYYYY, Locale.ENGLISH)
                        tv.setText(sdf.format(cal.time))
                    }

                },
                year,
                month,
                date
            )
            datePickerDialog.show()
        }
    }


    inline fun <reified T> genericCastOrNull(anything: Any?): T? {
        return anything as? T
    }
}