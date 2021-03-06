package com.martdev.android.ministrydiary.ministryreport.monthlyreportcards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.databinding.ReportsRecyclerViewBinding

class MonthlyReportsFrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<ReportsRecyclerViewBinding>(inflater, R.layout.bs_recycler_view, container, false)

        return binding.root
    }
}