package com.pekyurek.emircan.presentation.core.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

fun String.urlToBitmap(context: Context, onBitmap: (bitmap: Bitmap) -> Unit) {
    Glide.with(context).asBitmap().load(this).into(object : CustomTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            onBitmap.invoke(resource)
        }

        override fun onLoadCleared(placeholder: Drawable?) {}
    })
}

fun String.saveImageFromUrl(context: Context, path: String): String {
    Glide.with(context).asBitmap().load(this).into(object : CustomTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            context.openFileOutput(path.split("/").last(), Context.MODE_PRIVATE).use { fos ->
                resource.compress(Bitmap.CompressFormat.WEBP, 100, fos)
            }
        }

        override fun onLoadCleared(placeholder: Drawable?) {}
    })
    return path
}