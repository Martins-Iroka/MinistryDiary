package com.martdev.android.ministrydiary.biblestudent.biblestudents

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.data.Result.Success
import com.martdev.android.ministrydiary.data.biblestudentrepo.BibleStudentRepo
import com.martdev.android.ministrydiary.data.model.BibleStudent
import com.martdev.android.ministrydiary.data.model.ReturnVisit
import com.martdev.android.ministrydiary.utils.ADD_RESULT_OK
import com.martdev.android.ministrydiary.utils.DELETE_RESULT_OK
import com.martdev.android.ministrydiary.utils.EDIT_RESULT_OK
import com.martdev.android.ministrydiary.utils.Event
import kotlinx.coroutines.launch
import java.util.*

class BibleStudentsViewModel(private val bibleStudentRepo: BibleStudentRepo) : ViewModel() {

    private val _items = MutableLiveData<List<BibleStudent>>().apply { value = emptyList() }
    val items: LiveData<List<BibleStudent>> = _items

    private val _snackbarMessage = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> = _snackbarMessage

    private val _noBibleStudentLabel = MutableLiveData<Int>()
    val noBibleStudentLabel: LiveData<Int> = _noBibleStudentLabel

    private val _callBibleStudent = MutableLiveData<Event<String>>()
    val callBibleStudent: LiveData<Event<String>> = _callBibleStudent

    private val _addNewBibleStudentEvent = MutableLiveData<Event<Unit>>()
    val addNewBibleStudentEvent = _addNewBibleStudentEvent

    private val _openBibleStudentDetailEvent = MutableLiveData<Event<Array<String>>>()
    val openBibleStudentDetailEvent: LiveData<Event<Array<String>>> = _openBibleStudentDetailEvent

    val empty: LiveData<Boolean> = Transformations.map(_items) {
        it.isEmpty()
    }

    val date: Date
        get() {
            Calendar.getInstance().apply {
                return GregorianCalendar(
                    get(Calendar.YEAR),
                    get(Calendar.MONTH),
                    get(Calendar.DAY_OF_MONTH)
                ).time
            }
        }

    init {
        loadData()
    }

    fun loadData() {

        viewModelScope.launch {
            val bibleStudentResult = bibleStudentRepo.getList()

            if (bibleStudentResult is Success) {
                val bibleStudents = bibleStudentResult.data.value
                val linkedList = LinkedList<BibleStudent>()

                if (bibleStudents.isNullOrEmpty()) {
                    _noBibleStudentLabel.value = R.string.no_bible_student
                } else {
                    for (bibleStudent in bibleStudents) {
                        linkedList.addFirst(bibleStudent)
                    }
                    _items.value = linkedList
                }
            } else {
                _items.value = emptyList()
            }
        }
    }

    fun filterByDateOfVisit() {

        viewModelScope.launch {
            val bibleStudentResult = bibleStudentRepo.filterByData(date)

            if (bibleStudentResult is Success) {
                val filteredBibleStudents = bibleStudentResult.data.value

                if (filteredBibleStudents.isNullOrEmpty()) {
                    _noBibleStudentLabel.value = R.string.no_bs_to_visit
                } else _items.value = filteredBibleStudents
            } else {
                _items.value = emptyList()
            }
        }
    }

    fun addNewBibleStudent() {
        _addNewBibleStudentEvent.value = Event(Unit)
    }

    fun openBibleStudentDetail(bibleStudentId: String, bibleStudentName: String) {
        val info = arrayOf(bibleStudentId, bibleStudentName)
        _openBibleStudentDetailEvent.value = Event(info)
    }

    fun checkResultMessage(result: Int) {
        when (result) {
            ADD_RESULT_OK -> showSnackbarMessage(R.string.added_bible_student)
            EDIT_RESULT_OK -> showSnackbarMessage(R.string.edited_bible_student)
            DELETE_RESULT_OK -> showSnackbarMessage(R.string.deleted_bible_student)
        }
    }

    private fun showSnackbarMessage(@StringRes message: Int) {
        _snackbarMessage.value = Event(message)
    }

    fun dialNumber(bibleStudent: BibleStudent) {
        val phoneNumber = bibleStudent.phoneNumber
        if (phoneNumber.isNotEmpty()) _callBibleStudent.value = Event(phoneNumber)
        else _snackbarMessage.value = Event(R.string.dial_error)
    }

}