package com.martdev.android.ministrydiary.biblestudent.biblestudentdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martdev.android.ministrydiary.data.Result.Success
import com.martdev.android.ministrydiary.data.biblestudentrepo.BibleStudentRepo
import com.martdev.android.ministrydiary.data.model.BibleStudent
import com.martdev.android.ministrydiary.utils.DateUtils
import com.martdev.android.ministrydiary.utils.Event
import kotlinx.coroutines.launch
import timber.log.Timber

class BibleStudentDetailVM(
    private val bibleStudentRepo: BibleStudentRepo
) : ViewModel() {

    private var _bibleStudent = MutableLiveData<BibleStudent>()
    val bibleStudent: LiveData<BibleStudent> = _bibleStudent

    var date = MutableLiveData<String>()
    var time = MutableLiveData<String>()

    private val _editBibleStudent = MutableLiveData<Event<String>>()
    val editBibleStudent: LiveData<Event<String>> = _editBibleStudent

    private val _deleteBibleStudent = MutableLiveData<Event<Unit>>()
    val deleteBibleStudent: LiveData<Event<Unit>> = _deleteBibleStudent

    private val _snackbarMessage = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> = _snackbarMessage

    private val bsId: String?
        get() = _bibleStudent.value?.id

    fun start(bsId: String) {
        viewModelScope.launch {
            bibleStudentRepo.getItem(bsId).apply {
                if (this is Success) {
                    _bibleStudent.value = data
                    date.value = DateUtils.setFullDateFormat(data.date)
                    time.value = DateUtils.setTimeFormat(data.time)
                } else {
                    Timber.e("Bible student returned null")
                }
            }
        }
    }

    fun deleteBibleStudent() = viewModelScope.launch {
        bsId?.let {
            bibleStudentRepo.deleteItem(it)
            _deleteBibleStudent.value = Event(Unit)
        }
    }

    fun editBibleStudent() {
        _editBibleStudent.value = Event(bsId!!)
    }
}