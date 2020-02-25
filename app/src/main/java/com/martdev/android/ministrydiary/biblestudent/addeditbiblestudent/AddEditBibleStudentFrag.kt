package com.martdev.android.ministrydiary.biblestudent.addeditbiblestudent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.databinding.AddEditBsBinding

class AddEditBibleStudentFrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<AddEditBsBinding>(inflater, R.layout.add_edit_bs, container, false)

        return binding.root
    }
}