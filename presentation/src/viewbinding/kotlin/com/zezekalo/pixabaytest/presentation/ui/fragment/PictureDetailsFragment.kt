package com.zezekalo.pixabaytest.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.zezekalo.pixabaytest.presentation.R
import com.zezekalo.pixabaytest.presentation.databinding.FragmentPictureDetailsBinding
import com.zezekalo.pixabaytest.presentation.entity.UiPictureDetails
import com.zezekalo.pixabaytest.presentation.viewmodel.PictureDetailsViewModel
import com.zezekalo.pixabaytest.presentation.viewmodel.PicturesDetailsUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PictureDetailsFragment : Fragment(R.layout.fragment_picture_details) {

    private val viewModel by viewModels<PictureDetailsViewModel>()

    private var _viewBinding: FragmentPictureDetailsBinding? = null
    private val viewBinding
        get() = _viewBinding!!

    private val args: PictureDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewBinding = FragmentPictureDetailsBinding.bind(view)
        initViews(args.pictureId)
        observeState()
        if (savedInstanceState == null) {
            viewModel.fetchPictureDetails(args.pictureId)
        }
    }

    private fun initViews(id: Int) {
        with(viewBinding) {
            topAppBar.title = getString(R.string.picture_details_title, id)
            topAppBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.uiState.collect(::onUiStateChanged) }
            }
        }
    }

    private fun onUiStateChanged(uiState: PicturesDetailsUiState) {
        uiState.picture?.let {
            onPictureUpdated(it)    
        }
        onLoadingChanged(uiState.loading)
        onErrorReceived(uiState.errorMessage)
    }

    private fun onPictureUpdated(picture: UiPictureDetails) {
        viewBinding.bigPicture.load(picture.bigImageUrl) {
            crossfade(true)
            crossfade(500)
        }
        with(viewBinding.pictureDetails) {
            user.text = getString(R.string.user_template, picture.user)
            tags.text = getString(R.string.tags_template, picture.tagsAsString())
            likes.text = getString(R.string.likes_template, picture.likeCount)
            comments.text = getString(R.string.comments_template, picture.commentCount)
            downloads.text = getString(R.string.downloads_template, picture.downloadCount)
        }
    }

    private fun onLoadingChanged(loading: Boolean) {
        viewBinding.spinner.isVisible = loading
    }

    private fun onErrorReceived(errorMessage: String?) {
        errorMessage?.let {
            viewBinding.error.text = getString(R.string.error_message_pattern, it)
            viewBinding.errorLayout.isVisible = true
            viewBinding.returnButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}