package com.pekyurek.emircan.presentation.core.data.repository

import com.pekyurek.emircan.presentation.core.data.model.response.candidate.overlay.Overlay
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/candidates/overlay.json")
    suspend fun candidatesOverlay(): Response<List<Overlay>>

}