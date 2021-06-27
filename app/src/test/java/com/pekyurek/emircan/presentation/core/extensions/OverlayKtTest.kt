package com.pekyurek.emircan.presentation.core.extensions

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.pekyurek.emircan.presentation.core.data.model.response.candidate.overlay.Overlay
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class OverlayKtTest {

    val overlayId = 1
    val overlayName = "overlayName"
    val previewUrl = "previewUrl"
    val imageUrl = "imageUrl"
    val dummyOverlay = Overlay(overlayId, overlayName, previewUrl, imageUrl)

    val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun previewImageUrlToLocalPath() {
        assert(
            dummyOverlay.previewImageUrlToLocalPath(context)
                .endsWith("com.pekyurek.emircan-dataDir/files/overlay_preview$overlayId"),
        )
    }
    @Test
    fun imageUrlToPath() {
        assert(
            dummyOverlay.imageUrlToPath(context)
                .endsWith("com.pekyurek.emircan-dataDir/files/overlay_image$overlayId"),
        )

    }
}