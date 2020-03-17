package com.martdev.android.ministrydiary.biblestudent.biblestudents

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.data.model.BibleStudent
import com.martdev.android.ministrydiary.databinding.BsItemListBinding
import com.martdev.android.ministrydiary.utils.DateUtils
import java.util.*

class BibleStudentsAdapter(
    private val viewModel: BibleStudentsViewModel,
    private val context: Context
) :
    ListAdapter<BibleStudent, BibleStudentsAdapter.BibleStudentViewHolder>(BibleStudentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BibleStudentViewHolder {
        return BibleStudentViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: BibleStudentViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item, context)
    }

    class BibleStudentViewHolder private constructor(val binding: BsItemListBinding) :
        ViewHolder(binding.root) {

        fun bind(viewModel: BibleStudentsViewModel, bibleStudent: BibleStudent, context: Context) {
            binding.bsModel = viewModel
            binding.bibleStudent = bibleStudent
            binding.bibleStudentDate.text = getDateInString(bibleStudent.date, viewModel.date,  context)
            binding.executePendingBindings()
        }

        private fun getDateInString(date: Date, currentDate: Date, context: Context): String {
            val dateString = DateUtils.getReadableDate(date.time, context)
            return when {
                dateString.contentEquals("Today") -> {
                    context.resources.getString(R.string.visiting)
                }
                date.time < currentDate.time -> {
                    String.format("Previous visit was %s", dateString)
                }
                else -> {
                    String.format("Next visit %s", dateString)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): BibleStudentViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BsItemListBinding.inflate(layoutInflater, parent, false)

                return BibleStudentViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between an old list and a new
 * list that's been passed to `submitList`.
 */
class BibleStudentDiffCallback : DiffUtil.ItemCallback<BibleStudent>() {
    override fun areItemsTheSame(oldItem: BibleStudent, newItem: BibleStudent): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BibleStudent, newItem: BibleStudent): Boolean {
        return oldItem == newItem
    }
}