package com.martdev.android.ministrydiary.biblestudent.addeditbiblestudent

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.databinding.AddEditBsBinding
import com.martdev.android.ministrydiary.dialog.DateDialog
import com.martdev.android.ministrydiary.dialog.TimeDialog
import com.martdev.android.ministrydiary.utils.*
import java.util.*

class AddEditBibleStudentFrag : Fragment() {

    private lateinit var binding: AddEditBsBinding

    private val args: AddEditBibleStudentFragArgs by navArgs()

    private val viewModel by viewModels<AddEditBibleStudentVM> { getViewModelFactory() }

    private var action: NavDirections? = null

    private var contactId: String? = null

    private var date: Date? = null

    private var time: Date? = null

    private var manager: FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSnackbar()
        setupNavigation()
        viewModel.start(args.bsId, args.rvToBsDetail)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_edit_bs, container, false)
        binding.bsModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        manager = parentFragmentManager

        binding.getBsName.setOnClickListener {
            showContactList()
        }

        setupSpinner(binding)
        setupActionCallNumber(this, viewModel.callBibleStudent)
        return binding.root
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarMessage, Snackbar.LENGTH_SHORT)
    }

    private fun setupNavigation() {
        viewModel.navigationEvent.observe(this, EventObserver {
            when (it) {
                ADD_EDIT_RESULT_OK -> {
                    action = AddEditBibleStudentFragDirections
                        .actionAddEditBibleStudentFragToBibleStudentsFrag(ADD_EDIT_RESULT_OK)
                    findNavController().navigate(action!!)
                }
                EDIT_RESULT_OK -> {
                    action = AddEditBibleStudentFragDirections
                        .actionAddEditBibleStudentFragToBibleStudentsFrag(EDIT_RESULT_OK)
                    findNavController().navigate(action!!)
                }
                SHOW_DATE_DIALOG -> {
                    date = viewModel.getDate()
                    DateDialog.newInstance(date!!).apply {
                        setTargetFragment(this@AddEditBibleStudentFrag, REQUEST_DATE)
                        manager?.let { it1 -> show(it1, DATE_DIALOG) }
                    }
                }
                SHOW_TIME_DIALOG -> {
                    time = viewModel.getTime()
                    TimeDialog.newInstance(time!!).apply {
                        setTargetFragment(this@AddEditBibleStudentFrag, REQUEST_TIME)
                        manager?.let { it1 -> show(it1, TIME_DIALOG) }
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_edit_bs, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save) {
            viewModel.saveBibleStudent()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupSpinner(binding: AddEditBsBinding) {

        binding.bsStudyMaterial.adapter = ArrayAdapter.createFromResource(activity!!,
            R.array.study_material, android.R.layout.simple_spinner_item).apply {
            setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        }
        binding.bsStudyMaterial.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selection = parent?.getItemAtPosition(position) as String
                if (selection != "Study Material") {
                    viewModel.setStudyMaterial(selection)
                    viewModel.setStudyMaterialId(position)
                }
            }
        }
    }

    private fun showContactList() {
        val getContactName = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(getContactName, REQUEST_CONTACT_NAME)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode != Activity.RESULT_OK) return

        if (requestCode == REQUEST_CONTACT_NAME) {
            val contactUri = data?.data

            if (contactUri != null) {
                val query = arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME)

                val cursor = context?.contentResolver?.query(contactUri, query, null, null, null)

                cursor.use {
                    it?.let {
                        if (it.count == 0) {
                            return
                        }

                        it.moveToFirst()
                        contactId = it.getString(0)
                        val contactName = it.getString(1)
                        viewModel.setContactName(contactName)
                    }
                }
            }
        } else if (requestCode == REQUEST_DATE && data != null) {
            date = DateDialog.getNewDate(data)
            viewModel.setDate(date!!)
        } else if (requestCode == REQUEST_TIME && data != null) {
            time = TimeDialog.getNewTime(data)
            viewModel.setTime(time!!)
        }

        if (hasContactPermission()) {
            getContactNumber()
        } else {
            requestPermissions(CONTACT_PERMISSION, REQUEST_CONTACT_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CONTACT_PERMISSION) {
            if (hasContactPermission())
                getContactNumber()
        }
    }

    private fun hasContactPermission(): Boolean {
        val result = context?.let { ContextCompat.checkSelfPermission(it, CONTACT_PERMISSION[0]) }
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun getContactNumber() {
        val contactNumber = getContactNumber(contactId)
        contactNumber?.let { viewModel.setContactNumber(it) }
    }

    private fun getContactNumber(contactId: String?): String? {
        var contactNumber: String? = null

        val contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

        val queryColumns = arrayOf(ContactsContract.Data.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE)

        val c = context?.contentResolver?.query(contactUri, queryColumns,
            ContactsContract.Data.CONTACT_ID + " = ?", contactId?.let { arrayOf(it) }, null)

        c.use {
            it?.let {
                if (it.count == 0) {
                    return null
                }

                it.moveToFirst()
                while (!it.isAfterLast) {
                    val phoneType = it.getInt(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))
                    if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_HOME
                        || phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
                        || phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_WORK) {
                        contactNumber = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    }
                    it.moveToNext()
                }
            }
        }
        return contactNumber
    }

//    private fun dialNumber() {
//        val phoneNumber = viewModel.getPhoneNumber()
//        if (phoneNumber.isNotEmpty()) {
//            Intent(Intent.ACTION_CALL).apply {
//                data = Uri.parse("tel: ${viewModel.getPhoneNumber()}")
//                startActivity(this)
//            }
//            return
//        }
//        viewModel.setSnackbarText(R.string.dial_error)
//    }
}