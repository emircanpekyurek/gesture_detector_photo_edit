package com.pekyurek.emircan.presentation.core.extensions

import androidx.annotation.DrawableRes
import com.pekyurek.emircan.BuildConfig

fun drawableToPath(@DrawableRes drawableResId: Int): String {
    return "android.resource://${BuildConfig.APPLICATION_ID}/$drawableResId"
}