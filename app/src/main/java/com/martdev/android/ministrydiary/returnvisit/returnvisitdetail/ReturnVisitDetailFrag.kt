package com.martdev.android.ministrydiary.returnvisit.returnvisitdetail

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.databinding.ReturnVisitDetailBinding
import com.martdev.android.ministrydiary.utils.DELETE_RESULT_OK
import com.martdev.android.ministrydiary.utils.EventObserver
import com.martdev.android.ministrydiary.utils.getViewModelFactory
import com.martdev.android.ministrydiary.utils.setupSnackbar

class ReturnVisitDetailFrag : Fragment() {

    private lateinit var binding: ReturnVisitDetailBinding

    private val args: ReturnVisitDetailFragArgs by navArgs()

    private val viewModel by viewModels<ReturnVisitDetailVM> { getViewModelFactory() }

    private lateinit var action: NavDirections

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.return_visit_detail, container, false)
        binding.rvModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.start(args.rvId)

        view?.setupSnackbar(this, viewModel.snackbarMessage, Snackbar.LENGTH_SHORT)

        setupNavigation()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.rv_detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.edit_ -> {
                viewModel.editReturnVisit()
                true
            }
            R.id.delete_ -> {
                viewModel.deleteReturnVisit()
                true
            }
            R.id.move_to_bs -> {
                viewModel.moveToBibleStudent()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupNavigation() {
        viewModel.editReturnVisit.observe(this, EventObserver {
            action = ReturnVisitDetailFragDirections
                .actionReturnVisitDetailFragToAddEditRVFrag(it, resources.getString(R.string.edit_rv))
            findNavController().navigate(action)
        })
        viewModel.deleteReturnVisit.observe(this, EventObserver {
            action = ReturnVisitDetailFragDirections
                .actionReturnVisitDetailFragToReturnVisitsFrag(DELETE_RESULT_OK)
            findNavController().navigate(action)
        })
        viewModel.navigateToBibleStudent.observe(this, EventObserver {
            action = ReturnVisitDetailFragDirections
                .actionReturnVisitDetailFragToAddEditBibleStudentFrag(null,it, null)
            findNavController().navigate(action)
        })
    }
}