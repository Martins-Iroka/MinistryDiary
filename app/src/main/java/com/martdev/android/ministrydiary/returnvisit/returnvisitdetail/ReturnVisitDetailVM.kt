package com.martdev.android.ministrydiary.returnvisit.returnvisitdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martdev.android.ministrydiary.data.Result.Success
import com.martdev.android.ministrydiary.data.model.ReturnVisit
import com.martdev.android.ministrydiary.data.returnvisirepo.ReturnVisitRepo
import com.martdev.android.ministrydiary.utils.DateUtils
import com.martdev.android.ministrydiary.utils.Event
import kotlinx.coroutines.launch
import timber.log.Timber

class ReturnVisitDetailVM(
    private val returnVisitRepo: ReturnVisitRepo
) : ViewModel() {

    private var _returnVisit = MutableLiveData<ReturnVisit>()
    val returnVisit: LiveData<ReturnVisit> = _returnVisit

    var date = MutableLiveData<String>()
    var time = MutableLiveData<String>()

    private var _editReturnVisit = MutableLiveData<Event<String>>()
    var editReturnVisit: LiveData<Event<String>> = _editReturnVisit

    private val _deleteReturnVisit = MutableLiveData<Event<Unit>>()
    var deleteReturnVisit: LiveData<Event<Unit>> = _deleteReturnVisit

    private val _snackbarMessage = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> = _snackbarMessage

    private val _navigateToBibleStudent = MutableLiveData<Event<Array<String>>>()
    val navigateToBibleStudent: LiveData<Event<Array<String>>> = _navigateToBibleStudent

    private val rvId: String? = _returnVisit.value?.id

    fun start(rvId: String) {
        viewModelScope.launch {
            returnVisitRepo.getItem(rvId).apply {
                if (this is Success) {
                    _returnVisit.value = data
                    date.value = DateUtils.setFullDateFormat(data.date)
                    time.value = DateUtils.setTimeFormat(data.time)
                } else {
                    Timber.e("Return visit returned null")
                }
            }
        }
    }

    fun deleteReturnVisit() = viewModelScope.launch {
        _deleteReturnVisit.value = Event(Unit)
        rvId?.let {
            returnVisitRepo.deleteItem(it)
        }
    }

    private fun moveToBsDeleteRv(rvInfo: Array<String>) = viewModelScope.launch {
        _navigateToBibleStudent.value = Event(rvInfo)
        rvId?.let {
            returnVisitRepo.deleteItem(it)
        }
    }

    fun editReturnVisit() {
        _editReturnVisit.value = Event(rvId!!)
    }

    fun moveToBibleStudent() {

        val rvInfo = arrayOf(
            _returnVisit.value!!.name,
            _returnVisit.value!!.address,
            _returnVisit.value!!.phoneNumber
        )
        moveToBsDeleteRv(rvInfo = rvInfo)
    }
}