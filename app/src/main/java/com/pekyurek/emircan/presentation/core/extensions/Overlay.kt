package com.pekyurek.emircan.presentation.core.extensions

import android.content.Context
import com.pekyurek.emircan.presentation.core.data.model.response.candidate.overlay.Overlay

fun Overlay.previewImageUrlToLocalPath(context: Context) =
    "${context.filesDir.absolutePath}/overlay_preview$overlayId"

fun Overlay.imageUrlToPath(context: Context) = "${context.filesDir.absolutePath}/overlay_image$overlayId"