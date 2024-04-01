package com.zezekalo.pixabaytest.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.zezekalo.pixabaytest.presentation.R
import com.zezekalo.pixabaytest.presentation.databinding.FragmentPicturesBinding

class PicturesFragment : Fragment(R.layout.fragment_pictures) {

    private var _viewBinding: FragmentPicturesBinding? = null
    private val viewBinding
        get() = _viewBinding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewBinding = FragmentPicturesBinding.bind(view)
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}