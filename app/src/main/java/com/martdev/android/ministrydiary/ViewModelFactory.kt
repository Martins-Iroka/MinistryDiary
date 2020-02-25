package com.martdev.android.ministrydiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.martdev.android.ministrydiary.biblestudent.addeditbiblestudent.AddEditBibleStudentVM
import com.martdev.android.ministrydiary.data.biblestudentrepo.BibleStudentRepo
import com.martdev.android.ministrydiary.data.ministryreportrepo.MinistryReportRepo
import com.martdev.android.ministrydiary.data.monthlyreport.MonthlyReportRepo
import com.martdev.android.ministrydiary.data.returnvisirepo.ReturnVisitRepo
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val monthlyReportRepo: MonthlyReportRepo,
    private val ministryReportRepo: MinistryReportRepo,
    private val returnVisitRepo: ReturnVisitRepo,
    private val bibleStudentRepo: BibleStudentRepo
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.run {
            when {
                isAssignableFrom(AddEditBibleStudentVM::class.java) ->
                    AddEditBibleStudentVM(bibleStudentRepo)
                else -> throw IllegalArgumentException("Unknown ViewModel class")
            }
        } as T
    }
}