package com.zezekalo.pixabaytest.presentation.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.search.SearchView
import com.zezekalo.pixabaytest.domain.logger.logW
import com.zezekalo.pixabaytest.presentation.R
import com.zezekalo.pixabaytest.presentation.databinding.FragmentPicturesBinding
import com.zezekalo.pixabaytest.presentation.entity.UiPicture
import com.zezekalo.pixabaytest.presentation.ui.adapter.PicturesAdapter
import com.zezekalo.pixabaytest.presentation.ui.adapter.PicturesDiffUtilCallback
import com.zezekalo.pixabaytest.presentation.viewmodel.PicturesUiState
import com.zezekalo.pixabaytest.presentation.viewmodel.PicturesViewModel
import com.zezekalo.pixabaytest.presentation.viewmodel.ShowDialogEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            hideSearchView()
        }
    }

    private var dialog: AlertDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewBinding = FragmentPicturesBinding.bind(view)

        initViews()
        observeState()
    }

    private fun initSearch(){
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), onBackPressedCallback)
        with(viewBinding.pictureSearchInput) {
            addTransitionListener { _, _, newState->
                onBackPressedCallback.isEnabled = newState == SearchView.TransitionState.SHOWN
            }

            editText.addTextChangedListener { text ->
                viewModel.setQuery(text.toString())
            }
            editText.setOnEditorActionListener { _, _, _ ->
                hideSearchView()
                return@setOnEditorActionListener false
            }
        }
    }

    private fun hideSearchView() {
        viewBinding.pictureSearchInput.hide()
    }

    private fun initViews() {
        initSearch()
        initAdapter()
    }

    private fun initAdapter() {
        viewBinding.picturesRv.adapter = adapter
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.uiState.collectLatest(::onUiStateChanged) }
                launch { viewModel.query.collectLatest(::onQueryUpdated) }
            }
        }
    }

    private fun onQueryUpdated(query: String) {
        viewBinding.pictureSearchBar.setText(query)
    }

    private fun onUiStateChanged(uiState: PicturesUiState) {
        onLoadingChanged(uiState.loading)
        onPicturesUpdated(uiState.pictures, uiState.shouldShowEmpty)
        onShowDialogEvent(uiState.showDialog)
        onErrorReceived(uiState.errorMessage)
    }

    private fun onLoadingChanged(isLoading: Boolean) {
        viewBinding.spinner.isVisible = isLoading
    }

    private fun onPicturesUpdated(pictures: List<UiPicture>, shouldShowEmpty: Boolean) {
        adapter.submitList(pictures)
        showEmptyMessage(pictures.isEmpty() && shouldShowEmpty)
    }

    private fun showEmptyMessage(showEmpty: Boolean) {
        viewBinding.queryResult.isVisible = showEmpty
    }

    private fun onShowDialogEvent(event: ShowDialogEvent?) {
        event?.let {
            showDialog(it.pictureId)
        }
    }

    private fun onErrorReceived(errorMessage: String?) {
        showError(errorMessage)
    }

    private fun showDialog(pictureId: Int) {
        dialog?.cancel()
        dialog = MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.alert_dialog_message, pictureId))
            .setNeutralButton(R.string.cancel_button) { dialog,  _ ->
                dismissDialogWithAction(dialog, null)
            }
            .setPositiveButton(R.string.accept_button) { dialog,  _ ->
                dismissDialogWithAction(dialog) {
                    navigateToPictureDetails(pictureId)
                }
            }.setOnDismissListener { notifyDialogIsShown() }
            .create().also { it.show() }
    }

    private fun dismissDialogWithAction(dialog: DialogInterface, action: (() -> Unit)?) {
        notifyDialogIsShown()
        dialog.dismiss()
        action?.invoke()
    }

    private fun navigateToPictureDetails(pictureId: Int) {
        val action = PicturesFragmentDirections.actionPicturesFragmentToPictureDetailsFragment(pictureId)
        findNavController().navigate(action)
    }

    private fun notifyDialogIsShown() {
        viewModel.updateUiStateToShowDialogOrHide(null)
    }

    private fun onItemClick(item: UiPicture?) {
        item?.let { viewModel.updateUiStateToShowDialogOrHide(item.id) } ?: logW("Unexpected null when picture item is clicked!")
    }

    private fun showError(message: String?) {
        viewBinding.error.text = getString(R.string.error_message_pattern, message)
        viewBinding.error.isVisible = message?.isNotEmpty() == true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}