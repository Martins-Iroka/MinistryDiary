package com.martdev.android.ministrydiary.returnvisit.returnvisits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.martdev.android.ministrydiary.data.model.ReturnVisit
import com.martdev.android.ministrydiary.databinding.RvItemListBinding
import com.martdev.android.ministrydiary.returnvisit.returnvisits.ReturnVisitAdapter.ReturnVisitViewHolder
import com.martdev.android.ministrydiary.utils.showDate

class ReturnVisitAdapter(
    private val viewModel: ReturnVisitsViewModel
) : ListAdapter<ReturnVisit, ReturnVisitViewHolder>(ReturnVisitDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReturnVisitViewHolder {
        return ReturnVisitViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ReturnVisitViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    class ReturnVisitViewHolder private constructor(val binding: RvItemListBinding) :
        ViewHolder(binding.root) {

        fun bind(viewModel: ReturnVisitsViewModel, returnVisit: ReturnVisit) {
            binding.rvModel = viewModel
            binding.returnVisit = returnVisit
            binding.returnVDate.showDate(returnVisit.date, viewModel.date)

            if (returnVisit.phoneNumber.isEmpty()) {
                binding.dialRvNumber.visibility = View.GONE
            } else {
                binding.dialRvNumber.visibility = View.VISIBLE
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ReturnVisitViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RvItemListBinding.inflate(layoutInflater, parent, false)

                return ReturnVisitViewHolder(binding)
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
class ReturnVisitDiffCallback : DiffUtil.ItemCallback<ReturnVisit>() {
    override fun areItemsTheSame(oldItem: ReturnVisit, newItem: ReturnVisit): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReturnVisit, newItem: ReturnVisit): Boolean {
        return oldItem == newItem
    }

}