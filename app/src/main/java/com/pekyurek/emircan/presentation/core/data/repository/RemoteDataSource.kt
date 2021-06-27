package com.pekyurek.emircan.presentation.core.data.repository

import android.content.Context
import com.pekyurek.emircan.R
import com.pekyurek.emircan.presentation.core.data.domain.exception.ServiceException
import com.pekyurek.emircan.presentation.core.data.domain.exception.service.FailResponseException
import com.pekyurek.emircan.presentation.core.data.domain.exception.service.NullResponseException
import com.pekyurek.emircan.presentation.core.data.model.response.candidate.overlay.Overlay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val context: Context,
    private val apiService: ApiService
) :
    Repository {

    override suspend fun candidatesOverlay(): Flow<ResultStatus<List<Overlay>>> {
        return execute { apiService.candidatesOverlay() }
    }

    private suspend fun <T> execute(suspendResponse: suspend () -> Response<T>): Flow<ResultStatus<T>> =
        flow<ResultStatus<T>> {
            val response = suspendResponse.invoke()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultStatus.Success(it))
                } ?: emit(ResultStatus.Exception(NullResponseException(context)))
            } else {
                emit(
                    ResultStatus.Exception(
                        FailResponseException(
                            context,
                            response.code()
                        )
                    )
                )
            }
        }.onStart {
            emit(ResultStatus.Loading(true))
        }.catch { e ->
            emit(
                ResultStatus.Exception(
                    ServiceException(
                        e.message ?: e.localizedMessage ?: context.getString(
                            R.string.exception_service_generic_error_message
                        )
                    )
                )
            )
        }.onCompletion {
            emit(ResultStatus.Loading(false))
        }.flowOn(Dispatchers.IO)

}