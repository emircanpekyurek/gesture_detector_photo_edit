package com.pekyurek.emircan.presentation.core.di

import com.pekyurek.emircan.presentation.core.data.domain.usecase.candidates.overlay.CandidatesOverlayUseCase
import com.pekyurek.emircan.presentation.core.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object UseCaseModule {

    @Provides
    fun provideCandidatesOverlay(repository: Repository) = CandidatesOverlayUseCase(repository)

}