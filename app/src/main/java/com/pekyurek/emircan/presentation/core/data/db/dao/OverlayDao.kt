package com.pekyurek.emircan.presentation.core.data.db.dao

import android.content.Context
import androidx.room.*
import com.pekyurek.emircan.presentation.core.data.model.response.candidate.overlay.Overlay
import com.pekyurek.emircan.presentation.core.extensions.imageUrlToPath
import com.pekyurek.emircan.presentation.core.extensions.previewImageUrlToLocalPath
import com.pekyurek.emircan.presentation.core.extensions.saveImageFromUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.io.File

@Dao
interface OverlayDao {

    @Transaction
    @Query("DELETE FROM offline_overlay")
    fun _deleteAllOverlay()

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun _insert(overlay: Overlay)

    @Query("SELECT * FROM offline_overlay")
    fun getAllOverlay(): Flow<List<Overlay>>

    fun insertOverlayList(context: Context, list: List<Overlay>) {
        list.forEach {
            it.overlayPreviewIconUrl = it.overlayPreviewIconUrl.saveImageFromUrl(context, it.previewImageUrlToLocalPath(context))
            it.overlayUrl = it.overlayUrl?.saveImageFromUrl(context, it.imageUrlToPath(context))
            _insert(it)
        }
    }

    suspend fun deleteAllOverlay() {
        getAllOverlay().firstOrNull()?.forEach { overlay ->
            overlay.overlayUrl?.let { File(it).delete() }
            File(overlay.overlayPreviewIconUrl).delete()
        }
        _deleteAllOverlay()
    }
}