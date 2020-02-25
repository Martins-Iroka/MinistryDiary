package com.martdev.android.ministrydiary.returnvisit.addeditreturnvisit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.databinding.AddEditRvBinding

class AddEditRVFrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<AddEditRvBinding>(inflater, R.layout.add_edit_rv, container, false)

        return binding.root
    }
}