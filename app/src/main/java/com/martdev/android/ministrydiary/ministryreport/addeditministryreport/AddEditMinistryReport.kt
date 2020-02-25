package com.martdev.android.ministrydiary.ministryreport.addeditministryreport

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.databinding.AddEditReportBinding

class AddEditMinistryReport : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(activity)
        val binding = DataBindingUtil
            .inflate<AddEditReportBinding>(inflater, R.layout.add_edit_report, null, false)

        return AlertDialog.Builder(activity!!)
            .setView(binding.root)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok) {_, _ ->

            }.create()
    }
}