package com.martdev.android.ministrydiary.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.martdev.android.ministrydiary.R

@BindingAdapter("address_view")
fun setNameView(view: TextView, address: String) {
    if (address.isEmpty()) {
        view.text = view.context.getString(R.string.no_address)
    } else {
        view.text = address
    }
}

@BindingAdapter("phoneNumber_view")
fun setPhoneNumberView(view: TextView, phoneNumber: String) {
    if (phoneNumber.isEmpty()) {
        view.text = view.context.getString(R.string.no_phone_number)
    } else {
        view.text = phoneNumber
    }
}

@BindingAdapter("study_material_view")
fun setStudyMaterialView(view: TextView, studyMaterial: String) {
    if (studyMaterial.isEmpty()) {
        view.text = view.context.getString(R.string.no_study_material)
    } else {
        view.text = studyMaterial
    }
}

@BindingAdapter("chapter_lesson_view")
fun setChapterLessonView(view: TextView, chapterLesson: String) {
    if (chapterLesson.isEmpty()) {
        view.text = view.context.getString(R.string.no_chapter_lesson_to_study)
    } else {
        view.text = chapterLesson
    }
}

@BindingAdapter("paragraph_view")
fun setParagraphView(view: TextView, paragraph: String) {
    if (paragraph.isEmpty()) {
        view.text = view.context.getString(R.string.no_paragraph)
    } else {
        view.text = paragraph
    }
}

@BindingAdapter("placement_view")
fun setPlacementView(view: TextView, placement: String) {
    if (placement.isEmpty()) view.text = view.context.resources.getString(R.string.no_placement)
    else view.text = placement
}

@BindingAdapter("title_passage_read")
fun setTitlePassageView(view: TextView, titlePassage: String) {
    if (titlePassage.isEmpty()) view.text = view.context.resources.getString(R.string.no_title)
    else view.text = titlePassage
}

@BindingAdapter("question_left_view")
fun setQuestionView(view: TextView, questionLeft: String) {
    if (questionLeft.isEmpty()) view.text = view.context.resources.getString(R.string.no_question_to_answer)
    else view.text = questionLeft
}