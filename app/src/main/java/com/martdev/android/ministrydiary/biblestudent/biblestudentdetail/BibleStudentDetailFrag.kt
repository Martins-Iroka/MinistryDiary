package com.martdev.android.ministrydiary.biblestudent.biblestudentdetail

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.databinding.BibleStudentDetailBinding
import com.martdev.android.ministrydiary.utils.*

class BibleStudentDetailFrag : Fragment() {

    private lateinit var binding: BibleStudentDetailBinding

    private val args: BibleStudentDetailFragArgs by navArgs()

    private val viewModel by viewModels<BibleStudentDetailVM> { getViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bible_student_detail, container, false)
        binding.bsModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.start(args.bsId)

        view?.setupSnackbar(this, viewModel.snackbarMessage, Snackbar.LENGTH_SHORT)

        setupNavigation()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.generic_menu2, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit_ -> {
                viewModel.editBibleStudent()
                true
            }
            R.id.delete_ -> {
                viewModel.deleteBibleStudent()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupNavigation() {
        viewModel.editBibleStudent.observe(this, EventObserver {
            val action = BibleStudentDetailFragDirections
                .actionBibleStudentDetailFragToAddEditBibleStudentFrag(it, null, resources.getString(R.string.edit_bs))
            findNavController().navigate(action)
        })
        viewModel.deleteBibleStudent.observe(this, EventObserver {
            val action = BibleStudentDetailFragDirections
                .actionBibleStudentDetailFragToBibleStudentsFrag(DELETE_RESULT_OK)
            findNavController().navigate(action)
        })
    }
}