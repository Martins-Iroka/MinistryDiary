package com.martdev.android.ministrydiary.utils

import android.Manifest
import android.app.Activity

val CONTACT_PERMISSION = arrayOf(Manifest.permission.READ_CONTACTS)
const val REQUEST_CONTACT_NAME = 100
const val REQUEST_CONTACT_PERMISSION = 101
const val REQUEST_DATE = 102
const val REQUEST_TIME = 103

const val ADD_EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 1
const val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3

const val SHOW_DATE_DIALOG = 104
const val SHOW_TIME_DIALOG = 105

const val DATE_DIALOG = "DateDialog"
const val TIME_DIALOG = "TimeDialog"