package com.pekyurek.emircan.presentation.core.data.repository.locale

import com.pekyurek.emircan.presentation.core.data.model.response.candidate.overlay.Overlay
import kotlinx.coroutines.flow.Flow

interface LocaleRepository {

    suspend fun setCandidatesOverlay(list: List<Overlay>)

    suspend fun getCandidatesOverlay(): Flow<List<Overlay>>

}

