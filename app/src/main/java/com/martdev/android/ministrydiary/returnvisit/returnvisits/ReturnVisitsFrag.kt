package com.martdev.android.ministrydiary.returnvisit.returnvisits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.databinding.GenericRecyclerViewBinding

class ReturnVisitsFrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<GenericRecyclerViewBinding>(inflater, R.layout.generic_recycler_view, container, false)

        return binding.root
    }
}