package com.martdev.android.ministrydiary.returnvisit.returnvisits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.databinding.RvRecyclerViewBinding

class ReturnVisitsFrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<RvRecyclerViewBinding>(inflater, R.layout.rv_recycler_view, container, false)

        return binding.root
    }
}