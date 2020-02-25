package com.martdev.android.ministrydiary.utils

import android.content.Context
import com.martdev.android.ministrydiary.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {

    fun setDateFormat(date: Date): String = SimpleDateFormat("d, MMM, yyyy", Locale.getDefault()).format(date)

    fun setShortDateFormat(date: Date): String = SimpleDateFormat("MMMM, yyyy", Locale.getDefault())
        .format(date)

    fun setFullDateFormat(date: Date): String = SimpleDateFormat("EEEE, MMM, d, yyyy", Locale.getDefault()).format(date)

    fun setTimeFormat(time: Date): String = DateFormat.getTimeInstance(DateFormat.SHORT).format(time)

    private fun elapsedDaysSinceEpoch(date: Long): Long {
        return TimeUnit.MILLISECONDS.toDays(date)
    }

    fun getReadableDate(datePicker: Long, context: Context): String {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val date = GregorianCalendar(year, month, day).time.time

        val currentDate = elapsedDaysSinceEpoch(date)
        val daysAfterToday = elapsedDaysSinceEpoch(datePicker)

        return when((daysAfterToday - currentDate).toInt()) {
            0 -> context.getString(R.string.today)
            1 -> context.getString(R.string.tomorrow)
            else -> SimpleDateFormat("EEEE, d", Locale.getDefault()).format(datePicker)
        }
    }
}