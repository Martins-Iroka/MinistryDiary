package com.martdev.android.ministrydiary.returnvisit.returnvisitdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.databinding.ReturnVisitDetailBinding

class ReturnVisitDetailFrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ReturnVisitDetailBinding>(inflater, R.layout.bible_student_detail, container, false)

        return binding.root
    }
}