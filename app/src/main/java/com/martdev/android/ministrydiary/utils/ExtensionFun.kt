package com.martdev.android.ministrydiary.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.martdev.android.ministrydiary.MinistryDiaryApp
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.ViewModelFactory
import com.martdev.android.ministrydiary.data.Result
import java.util.*

fun <T> T.getResultStatus(): Result<T> {
    Result.Loading
    return when {
        this != null -> Result.Success(this)
        else -> Result.Error(Exception("Can't retrieve information"))
    }
}

fun <T> T.checkResultStatus(): Result<Any> {
    return when(this) {
        is Result.Success<*> -> Result.Success(this.data!!)
        is Result.Error -> Result.Error(Exception("Can't retrieve information from local"))
        else -> Result.Loading
    }
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<Int>>,
    timeLength: Int
) {

    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            showSnackbar(context.getString(it), timeLength)
        }
    })
}

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).run {
        addCallback(object : Snackbar.Callback() {
            override fun onShown(sb: Snackbar?) {

            }

            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {

            }
        })
        show()
    }
}

/**
 * Called when a user is about to call a number
 */
fun Fragment.setupActionCallNumber(
    lifecycleOwner: LifecycleOwner,
    callNumberEvent: LiveData<Event<String>>
) {

    callNumberEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { phoneNumber ->
            Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel: $phoneNumber")
                startActivity(this)
            }
        }
    })
}

fun Fragment.showContactList() {
    val getContactName = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
    startActivityForResult(getContactName, REQUEST_CONTACT_NAME)
}

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val monthlyReportRepo = ministryDiaryApp().monthlyReportRepo
    val ministryReportRepo = ministryDiaryApp().ministryReportRepo
    val returnVisitRepo = ministryDiaryApp().returnVisitRepo
    val bibleStudentRepo = ministryDiaryApp().bibleStudentRepo
    return ViewModelFactory(monthlyReportRepo, ministryReportRepo, returnVisitRepo, bibleStudentRepo)
}

private fun Fragment.ministryDiaryApp() =
    (requireContext().applicationContext as MinistryDiaryApp)

private var contactId = ""
private var contactName = ""

fun Uri.queryContacts(context: Context?): String {

    val query = arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME)

    val cursor = context?.contentResolver?.query(this, query, null, null, null)

    cursor.use {
        it?.let {
            if (it.count == 0) {
                return contactName
            }

            it.moveToFirst()
            contactId = it.getString(0)
            contactName = it.getString(1)
        }
    }
    return contactName
}

fun getContactNumber(context: Context?): String? {

    var contactNumber: String? = null

    val contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

    val queryColumns = arrayOf(ContactsContract.Data.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE)

    val c = context?.contentResolver?.query(contactUri, queryColumns,
        ContactsContract.Data.CONTACT_ID + " = ?", arrayOf(contactId), null)

    c.use {
        it?.let {
            if (it.count == 0)
                return null

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

fun Fragment.hasContactPermission(): Boolean {
    val result = context?.let { ContextCompat.checkSelfPermission(it, CONTACT_PERMISSION[0]) }
    return result == PackageManager.PERMISSION_GRANTED
}

fun TextView.showDate(date: Date, currentDate: Date) {
    val context = this.context
    val dateString = DateUtils.getReadableDate(date.time, context)
    this.text = when {
        dateString.contentEquals("Today") -> context.resources.getString(R.string.visiting)
        date.time < currentDate.time -> String.format("Previous visit was %s", dateString)
        else -> String.format("Next visit %s", dateString)
    }
}