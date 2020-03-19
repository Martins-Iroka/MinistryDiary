package com.martdev.android.ministrydiary.biblestudent.biblestudents

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.databinding.BsRecyclerViewBinding
import com.martdev.android.ministrydiary.utils.EventObserver
import com.martdev.android.ministrydiary.utils.getViewModelFactory
import com.martdev.android.ministrydiary.utils.setupActionCallNumber
import com.martdev.android.ministrydiary.utils.setupSnackbar

class BibleStudentsFrag : Fragment() {

    private val viewModel by viewModels<BibleStudentsViewModel> { getViewModelFactory() }

    private val args: BibleStudentsFragArgs by navArgs()

    private lateinit var binding: BsRecyclerViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bs_recycler_view, container, false)

        binding.bsModel = viewModel


        setHasOptionsMenu(true)
        setupSnackbar()
        setupListAdapter()
        setupNavigation()
        setupActionCallNumber(this, viewModel.callBibleStudent)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.rv_bs_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_rv_bs -> {
                viewModel.addNewBibleStudent()
                true
            }
            R.id.all_list -> {
                viewModel.loadData()
                true
            }
            R.id.filter_list -> {
                viewModel.filterByDateOfVisit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupNavigation() {
        viewModel.addNewBibleStudentEvent.observe(this, EventObserver {
            navigateToAddNewBibleStudent()
        })

        viewModel.openBibleStudentDetailEvent.observe(this, EventObserver {
            val bsId = it[0]
            val bsName = it[1]
            openBibleStudentDetail(bsId, bsName)
        })
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarMessage, Snackbar.LENGTH_SHORT)
        viewModel.checkResultMessage(args.message)
    }

    private fun navigateToAddNewBibleStudent() {
        val action = BibleStudentsFragDirections
            .actionBibleStudentsFragToAddEditBibleStudentFrag(
                null,
                null,
                resources.getString(R.string.new_bs)
            )
        findNavController().navigate(action)
    }

    private fun openBibleStudentDetail(bsId: String, bsName: String) {
        val action = BibleStudentsFragDirections
            .actionBibleStudentsFragToBibleStudentDetailFrag(bsId, bsName)
        findNavController().navigate(action)
    }

    private fun setupListAdapter() {
        binding.recyclerView.adapter = BibleStudentsAdapter(viewModel)
    }
}