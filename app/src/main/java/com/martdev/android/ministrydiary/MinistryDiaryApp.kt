package com.martdev.android.ministrydiary

import android.app.Application
import com.martdev.android.ministrydiary.data.biblestudentrepo.BibleStudentRepo
import com.martdev.android.ministrydiary.data.ministryreportrepo.MinistryReportRepo
import com.martdev.android.ministrydiary.data.monthlyreport.MonthlyReportRepo
import com.martdev.android.ministrydiary.data.returnvisirepo.ReturnVisitRepo
import com.martdev.android.ministrydiary.utils.Injector
import timber.log.Timber
import timber.log.Timber.DebugTree

class MinistryDiaryApp : Application() {

    val monthlyReportRepo: MonthlyReportRepo
        get() = Injector.provideMonthlyReportRepo(this)

    val ministryReportRepo: MinistryReportRepo
        get() = Injector.provideMinistryReportRepo(this)

    val returnVisitRepo: ReturnVisitRepo
        get() = Injector.provideReturnVisitRepo(this)

    val bibleStudentRepo: BibleStudentRepo
        get() = Injector.provideBibleStudentRepo(this)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }
}