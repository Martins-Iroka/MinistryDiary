package com.martdev.android.ministrydiary.biblestudent.biblestudents

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.martdev.android.ministrydiary.data.model.BibleStudent

@BindingAdapter("list")
fun setItems(listView: RecyclerView, items: List<BibleStudent>) {
    (listView.adapter as BibleStudentsAdapter).submitList(items)
}