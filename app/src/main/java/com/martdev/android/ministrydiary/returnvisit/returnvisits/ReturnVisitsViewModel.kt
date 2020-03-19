package com.martdev.android.ministrydiary.returnvisit.returnvisits

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.data.Result
import com.martdev.android.ministrydiary.data.Result.Success
import com.martdev.android.ministrydiary.data.model.ReturnVisit
import com.martdev.android.ministrydiary.data.returnvisirepo.ReturnVisitRepo
import com.martdev.android.ministrydiary.utils.ADD_RESULT_OK
import com.martdev.android.ministrydiary.utils.DELETE_RESULT_OK
import com.martdev.android.ministrydiary.utils.EDIT_RESULT_OK
import com.martdev.android.ministrydiary.utils.Event
import kotlinx.coroutines.launch
import java.util.*

class ReturnVisitsViewModel(
    private val returnVisitRepo: ReturnVisitRepo
) : ViewModel() {

    private val _items = MutableLiveData<List<ReturnVisit>>().apply { value = emptyList() }
    val items: LiveData<List<ReturnVisit>> = _items

    private val _snackbarMessage = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> = _snackbarMessage

    private val _noReturnVisitLabel = MutableLiveData<Int>()
    val noReturnVisitLabel: LiveData<Int> = _noReturnVisitLabel

    private val _callReturnVisit = MutableLiveData<Event<String>>()
    val callReturnVisit: LiveData<Event<String>> = _callReturnVisit

    private val _addNewReturnVisitEvent = MutableLiveData<Event<Unit>>()
    val addNewReturnVisitEvent: LiveData<Event<Unit>> = _addNewReturnVisitEvent

    private val _openReturnVisitDetailEvent = MutableLiveData<Event<Array<String>>>()
    val openReturnVisitDetailEvent: LiveData<Event<Array<String>>> = _openReturnVisitDetailEvent

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
            val returnVisitResult = returnVisitRepo.getList()

            if (returnVisitResult is Success) {
                val returnVisits = returnVisitResult.data.value
                val linkedList = LinkedList<ReturnVisit>()

                if (returnVisits.isNullOrEmpty()) {
                    _noReturnVisitLabel.value = R.string.no_return_visit
                } else {
                    for (returnVisit in returnVisits) {
                        linkedList.addFirst(returnVisit)
                    }
                    _items.value = linkedList
                }
            } else {
                _items.value = emptyList()
            }
        }
    }

    fun filterByDataOfVisit() {

        viewModelScope.launch {
            val returnVisitResult = returnVisitRepo.filterByData(date)

            if (returnVisitResult is Success) {
                val filteredReturnVisits = returnVisitResult.data.value

                if (filteredReturnVisits.isNullOrEmpty()) {
                    _noReturnVisitLabel.value = R.string.no_rv_to_see_
                } else _items.value = filteredReturnVisits
            } else {
                _items.value = emptyList()
            }
        }
    }

    fun addNewReturnVisit() {
        _addNewReturnVisitEvent.value = Event(Unit)
    }

    fun openReturnVisitDetail(returnVisitId: String, returnVisitName: String) {
        val info = arrayOf(returnVisitId, returnVisitName)
        _openReturnVisitDetailEvent.value = Event(info)
    }

    fun checkResultMessage(result: Int) {
        when(result) {
            ADD_RESULT_OK -> showSnackbarMessage(R.string.added_return_visit)
            EDIT_RESULT_OK -> showSnackbarMessage(R.string.edited_bible_student)
            DELETE_RESULT_OK -> showSnackbarMessage(R.string.deleted_bible_student)
        }
    }

    private fun showSnackbarMessage(@StringRes message: Int) {
        _snackbarMessage.value = Event(message)
    }

    fun dialNumber(returnVisit: ReturnVisit) {
        val phoneNumber = returnVisit.phoneNumber
        if (phoneNumber.isNotEmpty()) _callReturnVisit.value = Event(phoneNumber)
        else _snackbarMessage.value = Event(R.string.dial_error)
    }
}