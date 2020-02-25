package com.martdev.android.ministrydiary.utils

import android.content.Context
import com.martdev.android.ministrydiary.data.biblestudentrepo.BibleStudentLocalDataSource
import com.martdev.android.ministrydiary.data.biblestudentrepo.BibleStudentRepo
import com.martdev.android.ministrydiary.data.database.MinistryDiaryDB
import com.martdev.android.ministrydiary.data.ministryreportrepo.MinistryReportDataSource
import com.martdev.android.ministrydiary.data.ministryreportrepo.MinistryReportRepo
import com.martdev.android.ministrydiary.data.monthlyreport.MonthlyReportRepo
import com.martdev.android.ministrydiary.data.monthlyreport.MonthlyReportSource
import com.martdev.android.ministrydiary.data.returnvisirepo.ReturnVisitDataSource
import com.martdev.android.ministrydiary.data.returnvisirepo.ReturnVisitRepo

object Injector {

    fun provideReturnVisitRepo(context: Context): ReturnVisitRepo {
        val rvDao = database(context).rvDao()
        val dataSource = ReturnVisitDataSource(rvDao)
        return ReturnVisitRepo(dataSource)
    }

    fun provideBibleStudentRepo(context: Context): BibleStudentRepo {
        val bsDao = database(context).bsDao()
        val dataSource = BibleStudentLocalDataSource(bsDao)
        return BibleStudentRepo(dataSource)
    }

    fun provideMonthlyReportRepo(context: Context): MonthlyReportRepo {
        val monthlyReportDao = database(context).monthlyReportDao()
        val dataSource = MonthlyReportSource(monthlyReportDao)
        return MonthlyReportRepo(dataSource)
    }

    fun provideMinistryReportRepo(context: Context): MinistryReportRepo {
        val ministryReportDao = database(context).ministryReportDao()
        val dataSource = MinistryReportDataSource(ministryReportDao)
        return MinistryReportRepo(dataSource)
    }

    private fun database(context: Context): MinistryDiaryDB {
        return MinistryDiaryDB.getInstance(context)
    }
}