package com.xynderous.vatole.jarvisassignment.ui

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.xynderous.vatole.jarvisassignment.Utils.ApplicationConstants
import com.xynderous.vatole.jarvisassignment.myinterface.FromStartDate
import com.xynderous.vatole.jarvisassignment.myinterface.ToSTartDate
import java.util.*

class ToDatePickerFragment : DialogFragment(), OnDateSetListener
{
    private var myInt: String = ""

    private var mOnInputListener: ToSTartDate? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mOnInputListener = activity as ToSTartDate?
        } catch (e: ClassCastException) {
            Log.e("TAG", "onAttach: ClassCastException: "+ e.message)
        }
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = this.arguments
        if (bundle != null) {
            myInt = bundle.getString(ApplicationConstants.START_DATE) ?: ""
        }
        val getfromdate: String = myInt
        val getfrom = getfromdate.split("/").toTypedArray()
        val year: Int = getfrom[2].toInt()
        val month: Int = getfrom[1].toInt()
        val day: Int = getfrom[0].toInt()
        val c = Calendar.getInstance()
        c[year, month] = day + 1
        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
        datePickerDialog.datePicker.minDate = c.timeInMillis
        return datePickerDialog
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        mOnInputListener?.sendToStartDate("$day/$month/$year")
    }
}