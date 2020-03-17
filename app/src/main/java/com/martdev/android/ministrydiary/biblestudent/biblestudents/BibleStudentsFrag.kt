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
import com.martdev.android.ministrydiary.utils.setupSnackbar
import timber.log.Timber

class BibleStudentsFrag : Fragment() {

    private val viewModel by viewModels<BibleStudentsViewModel> { getViewModelFactory() }

    private val args: BibleStudentsFragArgs by navArgs()

    private lateinit var binding: BsRecyclerViewBinding

    private lateinit var listAdapter: BibleStudentsAdapter

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
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.rv_bs_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
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
            navigationToAddNewBibleStudent()
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

    private fun navigationToAddNewBibleStudent() {
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
        val viewModel = binding.bsModel
        if (viewModel != null) {
            listAdapter = BibleStudentsAdapter(viewModel, activity!!)
            binding.recyclerView.adapter = listAdapter
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }
}