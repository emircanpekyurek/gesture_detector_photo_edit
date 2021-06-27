package com.pekyurek.emircan.presentation.core.extensions

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import java.io.BufferedOutputStream

fun Bitmap.saveExternalStorage(context: Context, fileName: String) {
    val values = ContentValues().apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.DATE_TAKEN, fileName)
        }
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(
            MediaStore.Images.ImageColumns.DISPLAY_NAME,
            fileName
        )
        put(MediaStore.Images.ImageColumns.TITLE, fileName)
    }

    val contentResolver = context.contentResolver

    contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)?.let { uri ->
        contentResolver.openOutputStream(uri)?.let { stream ->
            val bos = BufferedOutputStream(stream)
            this.compress(Bitmap.CompressFormat.PNG, 100, bos)
            bos.close()
        }
    }
}