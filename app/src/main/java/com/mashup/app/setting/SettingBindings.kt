package com.mashup.app.setting

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mashup.BuildConfig

@BindingAdapter("app:email")
fun TextView.setEmailText(string: String) {
    text = string
    paintFlags = Paint.UNDERLINE_TEXT_FLAG
}