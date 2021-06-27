package com.pekyurek.emircan.presentation.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pekyurek.emircan.R
import com.pekyurek.emircan.presentation.core.data.domain.usecase.candidates.overlay.CandidatesOverlayUseCase
import com.pekyurek.emircan.presentation.core.data.model.response.candidate.overlay.Overlay
import com.pekyurek.emircan.presentation.core.di.ResourceProvider
import com.pekyurek.emircan.presentation.core.extensions.drawableToPath
import com.pekyurek.emircan.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val resourcesProvider : ResourceProvider,
    private val candidatesOverlayUseCase: CandidatesOverlayUseCase
) : BaseViewModel() {

    val overlayList = MutableLiveData<List<Overlay>>()

    init {
        getCandidatesOverlay()
    }

    private fun getCandidatesOverlay() = viewModelScope.launch(Dispatchers.IO) {
        request(
            flow = candidatesOverlayUseCase.execute(),
            onSuccess = { list ->
                val firstIndex = 0
                overlayList.postValue(list.toMutableList().apply { add(firstIndex, noneOverlay()) })
            }
        )
    }

    private fun noneOverlay(): Overlay {
        return Overlay(
            0,
            resourcesProvider.getString(R.string.common_label_none),
            drawableToPath(R.drawable.ic_none_overlay),
            null
        )
    }
}