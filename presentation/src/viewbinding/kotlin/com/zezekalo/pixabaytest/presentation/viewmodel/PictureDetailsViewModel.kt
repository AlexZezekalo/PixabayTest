package com.zezekalo.pixabaytest.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zezekalo.pixabaytest.domain.common.CoroutineExecutor
import com.zezekalo.pixabaytest.domain.common.PresentationDataDelegate
import com.zezekalo.pixabaytest.domain.entity.Picture
import com.zezekalo.pixabaytest.domain.repository.PictureRepository
import com.zezekalo.pixabaytest.presentation.entity.UiPictureDetails
import com.zezekalo.pixabaytest.presentation.entity.mapper.UiPictureDetailsMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

data class PicturesDetailsUiState(
    val picture: UiPictureDetails? = null,
    val loading: Boolean = true,
    val errorMessage: String? = null,
)

@HiltViewModel
class PictureDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: PictureRepository,
    private val mapper: UiPictureDetailsMapper,
    private val presentationDataDelegate: PresentationDataDelegate,
): ViewModel(), CoroutineExecutor, PresentationDataDelegate by presentationDataDelegate {

    override val scope: CoroutineScope = viewModelScope

    private val _uiState: MutableStateFlow<PicturesDetailsUiState> = MutableStateFlow(PicturesDetailsUiState())
    val uiState: StateFlow<PicturesDetailsUiState>
        get() = _uiState

    private val mutex = Mutex()

    fun fetchPictureDetails(id: Int) {
        launchSafe {
            repository.getPictureById(id)
                .onSuccess(::onSuccess)
                .onFailure(::onFailure)
        }
    }

    private fun onSuccess(picture: Picture) {
        launchSafe {
            updateUiState { copy(picture = mapper.map(picture), loading = false) }
        }
    }

    private fun onFailure(throwable: Throwable) {
        launchSafe {
            updateUiState { copy(errorMessage = throwable.message, loading = false) }
        }
    }

    private suspend fun updateUiState(update: PicturesDetailsUiState.() -> PicturesDetailsUiState) {
        mutex.withLock {
            _uiState.getAndUpdate(update)
        }
    }
}