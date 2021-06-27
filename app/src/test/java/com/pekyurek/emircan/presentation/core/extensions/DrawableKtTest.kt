package com.pekyurek.emircan.presentation.core.extensions

import com.pekyurek.emircan.R
import org.junit.Assert.*
import org.junit.Test

class DrawableKtTest {

    @Test
    fun drawableToPath() {
        val drawableId = R.drawable.ic_none_overlay
        assertEquals(drawableToPath(drawableId), "android.resource://com.pekyurek.emircan/$drawableId")

    }

}