package com.martdev.android.ministrydiary.biblestudent.addeditbiblestudent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.data.Result.Success
import com.martdev.android.ministrydiary.data.biblestudentrepo.BibleStudentRepo
import com.martdev.android.ministrydiary.data.model.BibleStudent
import com.martdev.android.ministrydiary.utils.*
import kotlinx.coroutines.launch
import java.util.*

class AddEditBibleStudentVM(
    private val bibleStudentRepo: BibleStudentRepo
) : ViewModel() {

    var bsName = MutableLiveData<String>()
    var bsAddress = MutableLiveData<String>()
    var bsPhoneNumber = MutableLiveData<String>()
    var _chapter = MutableLiveData<String>()
    var _paragraph = MutableLiveData<String>()
    var _date = MutableLiveData<String>()
    var _time = MutableLiveData<String>()
    var _studyMaterialId = MutableLiveData<Int>()

    private val _snackbarMessage = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> = _snackbarMessage

    private val _navigationEvent = MutableLiveData<Event<Int>>()
    val navigationEvent: LiveData<Event<Int>> = _navigationEvent

    private val _callBibleStudent = MutableLiveData<Event<String>>()
    val callBibleStudent: LiveData<Event<String>> = _callBibleStudent

    private var bibleStudent = BibleStudent()

    private var bsId: String? = null

    private fun setInfo() {
        bibleStudent.name = bsName.value.orEmpty()
        bibleStudent.address = bsAddress.value.orEmpty()
        bibleStudent.phoneNumber = bsPhoneNumber.value.orEmpty()
        bibleStudent.chapter = _chapter.value.orEmpty()
        bibleStudent.paragraph = _paragraph.value.orEmpty()
    }

    fun start(bsId: String?, rvDetails: Array<String>?) {

        if (!bsId.isNullOrEmpty()) {
            this.bsId = bsId

            viewModelScope.launch {
                bibleStudentRepo.getItem(bsId).apply {
                    if (this is Success) {
                        loadData(data)
                    }
                }
            }
            return
        }

        if (!rvDetails.isNullOrEmpty()) {
            bsName.value = rvDetails[0]
            bsAddress.value = rvDetails[1]
            bsPhoneNumber.value = rvDetails[2]
        }
    }

    private fun loadData(student: BibleStudent) {
        student.apply {
            this@AddEditBibleStudentVM.bibleStudent = this
            bsName.value = name
            bsAddress.value = address
            bsPhoneNumber.value = phoneNumber
            _chapter.value = chapter
            _paragraph.value = student.paragraph
            _date.value = DateUtils.setDateFormat(date)
            _time.value = DateUtils.setTimeFormat(time)
            _studyMaterialId.value = studyMaterialId
        }
    }

    fun setContactName(name: String) {
        bsName.value = name
    }

    fun setContactNumber(number: String) {
        bsPhoneNumber.value = number
    }

    fun setStudyMaterialId(id: Int) {
        bibleStudent.studyMaterialId = id
    }

    fun setStudyMaterial(studyMaterial: String) {
        bibleStudent.studyMaterial = studyMaterial
    }

    fun setDate(date: Date) {
        bibleStudent.date = date
        _date.value = DateUtils.setDateFormat(date)
    }

    fun setTime(time: Date) {
        bibleStudent.time = time
        _time.value = DateUtils.setTimeFormat(time)
    }

    fun getDate(): Date = bibleStudent.date

    fun getTime(): Date = bibleStudent.time

    fun navigateToDateDialog() {
        _navigationEvent.value = Event(SHOW_DATE_DIALOG)
    }

    fun navigateToTimeDialog() {
        _navigationEvent.value = Event(SHOW_TIME_DIALOG)
    }

    fun saveBibleStudent() {
        setInfo()
        if (bsId == null) {
            if (bsName.value.isNullOrEmpty()) {
                _snackbarMessage.value = Event(R.string.empty_message)
                return
            } else {
                viewModelScope.launch {
                    bibleStudentRepo.insertItem(bibleStudent)
                }
                _navigationEvent.value = Event(ADD_RESULT_OK)
            }
        } else {
            viewModelScope.launch {
                bibleStudentRepo.updateItem(bibleStudent)
            }
            _navigationEvent.value = Event(EDIT_RESULT_OK)
        }
    }

    fun dialNumber() {
        val phoneNumber = bsPhoneNumber.value
        if (!phoneNumber.isNullOrEmpty()) _callBibleStudent.value = Event(phoneNumber)
        else _snackbarMessage.value = Event(R.string.dial_error)
    }
}