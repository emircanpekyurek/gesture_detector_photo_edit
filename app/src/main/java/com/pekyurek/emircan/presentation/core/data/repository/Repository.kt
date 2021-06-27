package com.pekyurek.emircan.presentation.core.data.repository

import com.pekyurek.emircan.presentation.core.data.model.response.candidate.overlay.Overlay
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun candidatesOverlay(): Flow<ResultStatus<List<Overlay>>>

}