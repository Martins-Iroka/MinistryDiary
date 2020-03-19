package com.martdev.android.ministrydiary.returnvisit.returnvisits

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.databinding.RvRecyclerViewBinding
import com.martdev.android.ministrydiary.utils.EventObserver
import com.martdev.android.ministrydiary.utils.getViewModelFactory
import com.martdev.android.ministrydiary.utils.setupActionCallNumber
import com.martdev.android.ministrydiary.utils.setupSnackbar

class ReturnVisitsFrag : Fragment() {

    private val viewModel by viewModels<ReturnVisitsViewModel> { getViewModelFactory() }

    private val args: ReturnVisitsFragArgs by navArgs()

    private lateinit var binding: RvRecyclerViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.rv_recycler_view, container, false)

        binding.rvModel = viewModel

        setHasOptionsMenu(true)
        setupSnackbar()
        setupListAdapter()
        setupNavigation()
        setupActionCallNumber(this, viewModel.callReturnVisit)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.rv_bs_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_rv_bs -> {
                viewModel.addNewReturnVisit()
                true
            }
            R.id.all_list -> {
                viewModel.loadData()
                true
            }
            R.id.filter_list -> {
                viewModel.filterByDataOfVisit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupNavigation() {
        viewModel.addNewReturnVisitEvent.observe(this, EventObserver {
            navigateToAddNewReturnVisit()
        })

        viewModel.openReturnVisitDetailEvent.observe(this, EventObserver {
            val rvId = it[0]
            val rvName = it[1]
            openReturnVisitDetail(rvId, rvName)
        })
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarMessage, Snackbar.LENGTH_SHORT)
        viewModel.checkResultMessage(args.message)
    }

    private fun navigateToAddNewReturnVisit() {
        val action = ReturnVisitsFragDirections
            .actionReturnVisitsFragToAddEditRVFrag(
                null, resources.getString(R.string.new_bs)
            )
        findNavController().navigate(action)
    }

    private fun openReturnVisitDetail(rvId: String, rvName: String) {
        val action = ReturnVisitsFragDirections
            .actionReturnVisitsFragToReturnVisitDetailFrag(rvId, rvName)
        findNavController().navigate(action)
    }

    private fun setupListAdapter() {
        binding.recyclerView.adapter = ReturnVisitAdapter(viewModel)
    }
}