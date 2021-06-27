package com.pekyurek.emircan.presentation.core.data.repository

import com.pekyurek.emircan.presentation.core.data.model.response.candidate.overlay.Overlay
import com.pekyurek.emircan.presentation.core.data.repository.locale.LocaleDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localeDataSource: LocaleDataSource,
    private val remoteDataSource: RemoteDataSource
) : Repository {

    override suspend fun candidatesOverlay(): Flow<ResultStatus<List<Overlay>>> {
        return flow {
            remoteDataSource.candidatesOverlay().collect { result ->
                if (result is ResultStatus.Exception) {
                    val localeData = localeDataSource.getCandidatesOverlay().first()
                    emit(
                        if (localeData.isNullOrEmpty()) {
                            result
                        } else {
                            ResultStatus.Success(localeData)
                        }
                    )
                    return@collect
                } else if (result is ResultStatus.Success) {
                    localeDataSource.setCandidatesOverlay(result.data)
                }
                emit(result)
            }
        }
    }

}