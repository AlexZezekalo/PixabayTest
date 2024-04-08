package com.zezekalo.pixabaytest.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zezekalo.pixabaytest.domain.common.CoroutineExecutor
import com.zezekalo.pixabaytest.domain.common.PresentationDataDelegate
import com.zezekalo.pixabaytest.domain.entity.Picture
import com.zezekalo.pixabaytest.domain.repository.PictureRepository
import com.zezekalo.pixabaytest.presentation.entity.UiPicture
import com.zezekalo.pixabaytest.presentation.entity.mapper.UiPictureMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

const val DEFAULT_QUERY = "fruits"

data class PicturesUiState(
    val pictures: List<UiPicture> = emptyList(),
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val shouldShowEmpty: Boolean = false,
    val showDialog: ShowDialogEvent? = null
)

data class ShowDialogEvent(val pictureId: Int)

@HiltViewModel
class PicturesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: PictureRepository,
    private val pictureMapper: UiPictureMapper,
    private val presentationDataDelegate: PresentationDataDelegate,
): ViewModel(), CoroutineExecutor, PresentationDataDelegate by presentationDataDelegate {

    override val scope: CoroutineScope = viewModelScope

    private val _uiState = MutableStateFlow(PicturesUiState())
    val uiState: StateFlow<PicturesUiState>
        get() = _uiState

    private val _query = MutableStateFlow("")
    val query: StateFlow<String>
        get() = _query

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val startQueryResult: Flow<Unit> = _query.debounce(1_000).mapLatest { key ->
        queryForPicturesByKey(key)
    }

    private val mutex = Mutex()

    init {
        launchSafe { repository.picturesFlow.collect(::onPicturesUpdated) }
        launchSafe { startQueryResult.collect() }
        _query.value = DEFAULT_QUERY
    }

    fun setQuery(query: String) {
        _query.tryEmit(query)
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

    private fun queryForPicturesByKey(key: String) {
        launchSafe(context = Dispatchers.IO) {
            updateUiState { copy(loading = true, errorMessage = null, showDialog = null, shouldShowEmpty = false) }
            repository.queryPictures(key)
                .onFailure(::onFail)
                .onSuccess(::onSuccess)
        }
    }

    private fun onFail(throwable: Throwable) {
        launchSafe {
            updateUiState { copy(loading = false, errorMessage = throwable.message, shouldShowEmpty = false) }
        }
    }

    private fun onSuccess(event: Unit) {
        launchSafe {
            updateUiState { copy(loading = false, errorMessage = null, shouldShowEmpty = true) }
        }
    }

    private suspend fun onPicturesUpdated(pictures: List<Picture>) {
        updateUiState { copy(loading = false, pictures = pictures.map(pictureMapper::map)) }
    }

    private suspend fun updateUiState(update: PicturesUiState.() -> PicturesUiState) {
        mutex.withLock {
            _uiState.getAndUpdate(update)
        }
    }
}