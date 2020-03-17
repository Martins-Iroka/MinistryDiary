package com.martdev.android.ministrydiary.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.martdev.android.ministrydiary.R
import java.util.*

@Suppress("DEPRECATION")
class TimeDialog : DialogFragment() {

    private var mTimePicker: TimePicker? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

       var theTime = arguments?.getSerializable(ARG_TIME) as Date

        val calendar = Calendar.getInstance()
        calendar.time = theTime
        var hour = calendar.get(Calendar.HOUR)
        var minute = calendar.get(Calendar.MINUTE)

        val view = LayoutInflater.from(activity).inflate(R.layout.time_picker_dialog, null)

        mTimePicker = view.findViewById(R.id.time_picker)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker!!.hour = hour
            mTimePicker!!.minute = minute
        } else {
            mTimePicker!!.currentHour = hour
            mTimePicker!!.currentMinute = minute
        }

        return AlertDialog.Builder(activity)
            .setView(view)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = mTimePicker!!.hour
                    minute = mTimePicker!!.minute
                } else {
                    hour = mTimePicker!!.currentHour
                    minute = mTimePicker!!.currentMinute
                }

                Calendar.getInstance().run {
                    time = theTime
                    set(Calendar.HOUR, hour)
                    set(Calendar.MINUTE, minute)
                    theTime = time
                    sendResult(theTime)
                }
            }.create()
    }

    private fun sendResult(time: Date) {
        if (targetFragment == null) {
            return
        }

        val intent = Intent()
        intent.putExtra(EXTRA_TIME, time)
        targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
    }

    companion object {
        const val EXTRA_TIME = "com.martdev.android.mdbeta.dialog.rv_time"
        private const val ARG_TIME = "rv_time"

        fun newInstance(time: Date): TimeDialog {
            return TimeDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_TIME, time)
                }
            }
        }

        fun getNewTime(data: Intent): Date {
            return data.getSerializableExtra(EXTRA_TIME) as Date
        }
    }
}
