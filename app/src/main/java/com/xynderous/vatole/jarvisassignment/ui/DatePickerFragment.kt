package com.xynderous.vatole.jarvisassignment.ui

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.xynderous.vatole.jarvisassignment.myinterface.FromStartDate
import java.util.*


class DatePickerFragment : DialogFragment(), OnDateSetListener {
    var mOnInputListener: FromStartDate? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mOnInputListener = activity as FromStartDate?
        } catch (e: ClassCastException) {
            Log.e("TAG", "onAttach: ClassCastException: "+ e.message)
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(
            requireContext(), this, year,
            month, day
        )
    }
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        //fromdate.setText("$day/$month/$year")

        mOnInputListener?.sendInput("$day/$month/$year")
    }
}