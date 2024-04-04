package com.zezekalo.pixabaytest.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zezekalo.pixabaytest.domain.logger.logD
import com.zezekalo.pixabaytest.domain.logger.logI
import com.zezekalo.pixabaytest.domain.logger.logW
import com.zezekalo.pixabaytest.presentation.R
import com.zezekalo.pixabaytest.presentation.databinding.FragmentPicturesBinding
import com.zezekalo.pixabaytest.presentation.entity.UiPicture
import com.zezekalo.pixabaytest.presentation.ui.adapter.PicturesAdapter
import com.zezekalo.pixabaytest.presentation.ui.adapter.PicturesDiffUtilCallback
import com.zezekalo.pixabaytest.presentation.viewmodel.PicturesUiState
import com.zezekalo.pixabaytest.presentation.viewmodel.PicturesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PicturesFragment : Fragment(R.layout.fragment_pictures) {

    private val viewModel by viewModels<PicturesViewModel>()

    private var _viewBinding: FragmentPicturesBinding? = null
    private val viewBinding
        get() = _viewBinding!!

    private val adapter: PicturesAdapter by lazy {
        PicturesAdapter(PicturesDiffUtilCallback(), ::onItemClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewBinding = FragmentPicturesBinding.bind(view)

        initViews()
        observeState()
    }

    private fun initViews() {
        initAdapter()

        viewBinding.pictureSearchInput.editText.addTextChangedListener { text ->
            logD("addTextChangedListener: text: $text")
        }
        viewBinding.pictureSearchInput.editText.setOnEditorActionListener { view, actionId, event ->
            logI("setOnEditorActionListener: actionId: $actionId; event: $event; text: ${view.text}")
            //TODO
            return@setOnEditorActionListener false
        }

//        QueryTextListener(
//            object : SearchView.OnQueryTextListener {
//
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                viewModel.debounce.offer(newText)
//                return true
//            }
//        })
    }

    private fun initAdapter() {
        viewBinding.picturesRv.adapter = adapter
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.uiState.collect(::onUiStateChanged) }
            }
        }
    }

    private fun onUiStateChanged(uiState: PicturesUiState) {
        logI("fragment: pictures comes from view model, size: ${uiState.pictures.size}")
        onLoadingChanged(uiState.loading)
        onPicturesUpdated(uiState.pictures)
    }

    private fun onLoadingChanged(isLoading: Boolean) {
        viewBinding.spinner.isVisible = isLoading
    }

    private fun onPicturesUpdated(pictures: List<UiPicture>) {
        adapter.submitList(pictures)
    }

    private fun onItemClick(item: UiPicture?) {
        item?.let { showDialog(it) } ?: logW("Unexpected null on picture click!")
    }

    private fun showDialog(item: UiPicture) {
        logI("Item click on item $item")
        //TODO
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}