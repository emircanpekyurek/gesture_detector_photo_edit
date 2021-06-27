package com.pekyurek.emircan.presentation.core.data.repository.locale

import android.content.Context
import com.pekyurek.emircan.presentation.core.data.db.AppDatabase
import com.pekyurek.emircan.presentation.core.data.model.response.candidate.overlay.Overlay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocaleDataSource @Inject constructor(
    private val context: Context,
    private val appDatabase: AppDatabase
) : LocaleRepository {

    override suspend fun setCandidatesOverlay(list: List<Overlay>) {
        appDatabase.overlayDao().deleteAllOverlay()
        appDatabase.overlayDao().insertOverlayList(context, list)
    }

    override suspend fun getCandidatesOverlay(): Flow<List<Overlay>> {
        return appDatabase.overlayDao().getAllOverlay()
    }

}