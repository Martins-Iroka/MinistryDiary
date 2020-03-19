package com.martdev.android.ministrydiary.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.martdev.android.ministrydiary.biblestudent.biblestudents.BibleStudentsAdapter
import com.martdev.android.ministrydiary.data.model.BibleStudent
import com.martdev.android.ministrydiary.data.model.ReturnVisit
import com.martdev.android.ministrydiary.returnvisit.returnvisits.ReturnVisitAdapter

@BindingAdapter("bibleStudentList")
fun setBibleStudentList(listView: RecyclerView, items: List<BibleStudent>) {
    (listView.adapter as BibleStudentsAdapter).submitList(items)
}

@BindingAdapter("returnVisitList")
fun setReturnVisitList(listView: RecyclerView, items: List<ReturnVisit>) {
    (listView.adapter as ReturnVisitAdapter).submitList(items)
}

