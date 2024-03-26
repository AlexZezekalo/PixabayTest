package com.zezekalo.pixabaytest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.zezekalo.pixabaytest.domain.repository.PictureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: PictureRepository
): ViewModel() {
}