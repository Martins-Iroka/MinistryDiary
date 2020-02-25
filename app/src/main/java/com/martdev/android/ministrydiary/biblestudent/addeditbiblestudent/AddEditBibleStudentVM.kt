package com.martdev.android.ministrydiary.biblestudent.addeditbiblestudent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martdev.android.ministrydiary.R
import com.martdev.android.ministrydiary.data.Result.Success
import com.martdev.android.ministrydiary.data.biblestudentrepo.BibleStudentRepo
import com.martdev.android.ministrydiary.data.model.BibleStudent
import com.martdev.android.ministrydiary.utils.DateUtils
import com.martdev.android.ministrydiary.utils.Event
import kotlinx.coroutines.launch

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

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _navigationEvent = MutableLiveData<Event<Unit>>()
    val navigationEvent: LiveData<Event<Unit>> = _navigationEvent

    private var mBibleStudent = BibleStudent()

    private var bsId: String? = null

    private fun setInfo() {
        mBibleStudent.name = bsName.value.orEmpty()
        mBibleStudent.address = bsAddress.value.orEmpty()
        mBibleStudent.phoneNumber = bsPhoneNumber.value.orEmpty()
        mBibleStudent.chapter = _chapter.value.orEmpty()
        mBibleStudent.paragraph = _paragraph.value.orEmpty()
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

    private fun loadData(bibleStudent: BibleStudent) {
        bibleStudent.apply {
            mBibleStudent = this
            bsName.value = name
            bsAddress.value = address
            bsPhoneNumber.value = phoneNumber
            _chapter.value = chapter
            _paragraph.value = bibleStudent.paragraph
            _date.value = DateUtils.setDateFormat(bibleStudent.date)
            _time.value = DateUtils.setTimeFormat(bibleStudent.time)
            _studyMaterialId.value = bibleStudent.studyMaterialId
        }
    }

    fun getContactName(name: String) {
        bsName.value = name
    }

    fun getContactNumber(number: String) {
        bsPhoneNumber.value = number
    }

    fun getStudyMaterialId(id: Int) {
        mBibleStudent.studyMaterialId = id
    }

    fun saveBibleStudent() {
        if (bsId == null) {
            if (bsName.value.isNullOrEmpty()) {
                _snackbarText.value = Event(R.string.empty_message)
                return
            } else {
                viewModelScope.launch {
                    bibleStudentRepo.insertItem(mBibleStudent)
                }
            }
        } else {
            viewModelScope.launch {
                bibleStudentRepo.updateItem(mBibleStudent)
            }
        }

        _navigationEvent.value = Event(Unit)
    }
}