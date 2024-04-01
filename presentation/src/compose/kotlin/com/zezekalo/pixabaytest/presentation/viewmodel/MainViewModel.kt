package com.zezekalo.pixabaytest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zezekalo.pixabaytest.domain.common.CoroutineExecutor
import com.zezekalo.pixabaytest.domain.entity.Picture
import com.zezekalo.pixabaytest.domain.repository.PictureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiState(
    val pictures: List<Picture> = emptyList(),
    val loading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PictureRepository,
): ViewModel(), CoroutineExecutor {

    override val scope: CoroutineScope
        get() = viewModelScope
    override val backgroundDispatcher: CoroutineDispatcher
        get() = TODO("Not yet implemented")

    override val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->

        }

    init {
        scope.launch { repository.picturesFlow.collect(::onPicturesUpdated) }
        queryForPicturesByKey(DEFAULT_QUERY)
    }

    fun queryForPicturesByKey(key: String) {
        scope.launch {
            repository.queryPictures(key)
                .onFailure {  }
                .onSuccess {  }
        }
    }

    private suspend fun onPicturesUpdated(pictures: List<Picture>) { }

    companion object {

        private const val DEFAULT_QUERY = "fruits"
    }
}