package com.zezekalo.pixabaytest.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zezekalo.pixabaytest.domain.common.CoroutineExecutor
import com.zezekalo.pixabaytest.domain.entity.Picture
import com.zezekalo.pixabaytest.domain.logger.logW
import com.zezekalo.pixabaytest.domain.repository.PictureRepository
import com.zezekalo.pixabaytest.presentation.entity.UiPicture
import com.zezekalo.pixabaytest.presentation.entity.mapper.UiPictureMapper
import com.zezekalo.pixabaytest.presentation.util.Debounce
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

const val DEFAULT_QUERY = "fruits"

data class PicturesUiState(
    val pictures: List<UiPicture> = emptyList(),
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val showDialog: ShowDialogEvent? = null
)

data class ShowDialogEvent(val pictureId: Int)

@HiltViewModel
class PicturesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: PictureRepository,
    private val pictureMapper: UiPictureMapper,
): ViewModel(), CoroutineExecutor {

    override val scope: CoroutineScope
        get() = viewModelScope
    override val backgroundDispatcher: CoroutineDispatcher
        get() = Dispatchers.Default

    override val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->

        }

    val debounce: Debounce = Debounce(scope, 1_000L) {
        queryForPicturesByKey(it)
    }

    private val _uiState = MutableStateFlow(PicturesUiState())
    val uiState: StateFlow<PicturesUiState>
        get() = _uiState

    private val _query = MutableStateFlow(DEFAULT_QUERY)
    val query: StateFlow<String>
        get() = _query

    private val mutex = Mutex()

    init {
        launchSafe { repository.picturesFlow.collect(::onPicturesUpdated) }
        queryForPicturesByKey(DEFAULT_QUERY)
    }

    fun updateUiStateToShowDialogOrHide(pictureId: Int?) {
        launchSafe {
            updateUiState {
                copy(showDialog = pictureId?.let {
                    ShowDialogEvent(pictureId = it)
                })
            }
        }
    }

    fun updateUiStateToShowErrorOrNot(errorMessage: String?) {
        launchSafe {
            updateUiState {
                copy(errorMessage = errorMessage)
            }
        }
    }

    private fun queryForPicturesByKey(key: String) {
        launchSafe(context = Dispatchers.IO) {
            updateUiState { copy(loading = true) }
            repository.queryPictures(key)
                .onFailure(::onFail)
                .onSuccess(::onSuccess)
        }
    }

    private fun onFail(throwable: Throwable) {
        launchSafe {
            updateUiState { copy(loading = false, errorMessage = throwable.message) }
        }
    }

    private fun onSuccess(event: Unit) {
        launchSafe {
            updateUiState { copy(loading = false) }
        }
    }

    private suspend fun onPicturesUpdated(pictures: List<Picture>) {
        logW("pictures comes to view model, size: ${pictures.size}")
        updateUiState { copy(loading = false, pictures = pictures.map(pictureMapper::map)) }
    }

    private suspend fun updateUiState(update: PicturesUiState.() -> PicturesUiState) {
        mutex.withLock {
            _uiState.getAndUpdate(update)
        }
    }
}