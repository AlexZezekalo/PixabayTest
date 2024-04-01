package com.zezekalo.pixabaytest.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zezekalo.pixabaytest.presentation.databinding.FragmentPicturesBinding


class PictureDetailsFragment : Fragment() {

    private var _viewBinding: FragmentPicturesBinding? = null
    private val viewBinding
        get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _viewBinding = FragmentPicturesBinding.inflate(inflater, container, false)
        return viewBinding.root
    }
}