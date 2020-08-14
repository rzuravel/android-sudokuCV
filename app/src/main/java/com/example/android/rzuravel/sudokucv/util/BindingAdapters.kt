package com.example.android.rzuravel.sudokucv.util

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.example.android.rzuravel.sudokucv.R.*

@BindingAdapter("app:background")
fun valueSelectBackground(view: View, hint: Boolean) {
    view.background = if (hint) ContextCompat.getDrawable(view.context, drawable.value_select_background_checked) else ContextCompat.getDrawable(view.context, drawable.value_select_background)
}