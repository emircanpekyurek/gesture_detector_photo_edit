package com.pekyurek.emircan.presentation.core.data.domain.usecase.candidates.overlay

import com.pekyurek.emircan.presentation.core.data.domain.usecase.UseCase
import com.pekyurek.emircan.presentation.core.data.model.response.candidate.overlay.Overlay
import com.pekyurek.emircan.presentation.core.data.repository.Repository
import com.pekyurek.emircan.presentation.core.data.repository.ResultStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CandidatesOverlayUseCase @Inject constructor(
    private val repository: Repository
) :
    UseCase<Any, List<Overlay>> {

    override suspend fun execute(request: Any?): Flow<ResultStatus<List<Overlay>>> {
        return repository.candidatesOverlay()
    }

}