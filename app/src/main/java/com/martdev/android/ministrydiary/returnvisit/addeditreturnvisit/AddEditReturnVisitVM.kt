package com.martdev.android.ministrydiary.returnvisit.addeditreturnvisit

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.data.Result.Success
import com.martdev.android.ministrydiary.data.model.ReturnVisit
import com.martdev.android.ministrydiary.data.returnvisirepo.ReturnVisitRepo
import com.martdev.android.ministrydiary.utils.*
import kotlinx.coroutines.launch
import java.util.*

class AddEditReturnVisitVM(
    private val returnVisitRepo: ReturnVisitRepo
) : ViewModel() {

    var rvName = MutableLiveData<String>()
    var rvAddress = MutableLiveData<String>()
    var rvPhoneNumber = MutableLiveData<String>()
    var title_passage_read = MutableLiveData<String>()
    var placementId = MutableLiveData<Int>()
    var placement = MutableLiveData<String>()
    var question_left = MutableLiveData<String>()
    var _date = MutableLiveData<String>()
    var _time = MutableLiveData<String>()

    private val _snackbarMessage = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> = _snackbarMessage

    private val _navigationEvent = MutableLiveData<Event<Int>>()
    val navigationEvent: LiveData<Event<Int>> = _navigationEvent

    private val _callReturnVisit = MutableLiveData<Event<String>>()
    val callReturnVisit: LiveData<Event<String>> = _callReturnVisit

    private var returnVisit = ReturnVisit()

    private var rvId: String? = null

    private fun setInfo() {
        returnVisit.run {
            name = rvName.value.orEmpty()
            address = rvAddress.value.orEmpty()
            phoneNumber = rvPhoneNumber.value.orEmpty()
            title_passage = title_passage_read.value.orEmpty()
            question = question_left.value.orEmpty()
        }
    }

    fun start(rvId: String?) {

        if (!rvId.isNullOrEmpty()) {
            this.rvId = rvId

            viewModelScope.launch {
                returnVisitRepo.getItem(rvId).apply {
                    if (this is Success) {
                        loadData(data)
                    }
                }
            }
        }
    }

    private fun loadData(returnVisit: ReturnVisit) {
        returnVisit.apply {
            this@AddEditReturnVisitVM.returnVisit = this
            rvName.value = name
            rvAddress.value = address
            rvPhoneNumber.value = phoneNumber
            this@AddEditReturnVisitVM.placementId.value = placementId
            this@AddEditReturnVisitVM.placement.value = placement
            title_passage_read.value = title_passage
            question_left.value = question
            this@AddEditReturnVisitVM._date.value = DateUtils.setDateFormat(date)
            this@AddEditReturnVisitVM._time.value = DateUtils.setTimeFormat(time)
        }
    }

    fun setContactName(name: String) {
        rvName.value = name
    }

    fun setContactNumber(number: String) {
        rvPhoneNumber.value = number
    }

    fun setPlacementId(id: Int) {
        returnVisit.placementId = id
    }

    fun setPlacement(placement: String) {
        returnVisit.placement = placement
    }

    fun setDate(date: Date) {
        returnVisit.date = date
        _date.value = DateUtils.setDateFormat(date)
    }

    fun setTime(time: Date) {
        returnVisit.time = time
        _time.value = DateUtils.setTimeFormat(time)
    }

    fun getDate() = returnVisit.date

    fun getTime() = returnVisit.time

    fun navigateToDateDialog() {
        _navigationEvent.value = Event(SHOW_DATE_DIALOG)
    }

    fun navigateToTimeDialog() {
        _navigationEvent.value = Event(SHOW_TIME_DIALOG)
    }

    fun saveBibleStudent() {
        setInfo()
        if (rvId.isNullOrEmpty()) {
            if (rvName.value.isNullOrEmpty()) {
                showSnackbarMessage(R.string.empty_message)
                return
            } else {
                viewModelScope.launch {
                    returnVisitRepo.insertItem(returnVisit)
                }
                _navigationEvent.value = Event(ADD_RESULT_OK)
            }
        } else {
            viewModelScope.launch {
                returnVisitRepo.updateItem(returnVisit)
            }
            _navigationEvent.value = Event(EDIT_RESULT_OK)
        }
    }

    fun dialNumber() {
        val phoneNumber = rvPhoneNumber.value
        if (!phoneNumber.isNullOrEmpty()) _callReturnVisit.value = Event(phoneNumber)
        else showSnackbarMessage(R.string.dial_error)
    }

    private fun showSnackbarMessage(@StringRes message: Int) {
        _snackbarMessage.value = Event(message)
    }

}