package com.zezekalo.pixabaytest.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.zezekalo.pixabaytest.presentation.databinding.FragmentPicturesBinding


class PictureDetailsFragment : Fragment() {

    private var _viewBinding: FragmentPicturesBinding? = null
    private val viewBinding
        get() = _viewBinding!!

    val args: PictureDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _viewBinding = FragmentPicturesBinding.inflate(inflater, container, false)
        return viewBinding.root
    }
}