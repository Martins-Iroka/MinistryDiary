package com.martdev.android.ministrydiary.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.martdev.android.ministrydiary.MinistryDiaryApp
import com.martdev.android.ministrydiary.ViewModelFactory
import com.martdev.android.ministrydiary.data.Result

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

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val monthlyReportRepo = ministryDiaryApp().monthlyReportRepo
    val ministryReportRepo = ministryDiaryApp().ministryReportRepo
    val returnVisitRepo = ministryDiaryApp().returnVisitRepo
    val bibleStudentRepo = ministryDiaryApp().bibleStudentRepo
    return ViewModelFactory(monthlyReportRepo, ministryReportRepo, returnVisitRepo, bibleStudentRepo)
}

private fun Fragment.ministryDiaryApp() =
    (requireContext().applicationContext as MinistryDiaryApp)