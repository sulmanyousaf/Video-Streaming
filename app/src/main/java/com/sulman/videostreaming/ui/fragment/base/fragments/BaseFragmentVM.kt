package com.sulman.videostreaming.ui.fragment.base.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.sulman.videostreaming.ui.fragment.base.viewModel.BaseViewModel
import com.sulman.videostreaming.utils.extensions.observeLiveData
import com.sulman.videostreaming.utils.extensions.progressDialog
import com.sulman.videostreaming.utils.extensions.visibility

abstract class BaseFragmentVM<VB : ViewBinding, VM : BaseViewModel>(
    inflate: Inflate<VB>,
    private val t: Class<VM>,
) : BaseFragmentVB<VB>(inflate) {

    private lateinit var progress: AlertDialog
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(t)
        /*hideGenericButton()*/
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progress = progressDialog()

        observeLiveData(viewModel.progress) {
            progress.visibility(it)
        }
    }

    fun baseNavigateTo(direction: NavDirections) {
        findNavController().navigate(direction)
    }
}