package com.sulman.videostreaming.ui.fragment.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.sulman.videostreaming.data.helpers.EqualSpacingItemDecoration
import com.sulman.videostreaming.databinding.FragmentMainBinding
import com.sulman.videostreaming.ui.fragment.base.fragments.BaseFragmentVMBP
import com.sulman.videostreaming.ui.fragment.base.viewModel.BaseViewModel
import com.sulman.videostreaming.utils.extensions.navigateBack
import com.sulman.videostreaming.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragmentVMBP<FragmentMainBinding, MainFragmentVM>(
    FragmentMainBinding::inflate,
    MainFragmentVM::class.java
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_VIDEO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        checkPermission(permission)

        binding.apply {
            val spacingInPixels = requireActivity().resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._4sdp)
            var layoutManagerRV = GridLayoutManager(requireContext(), 2)
            val itemDecoration = EqualSpacingItemDecoration(spacingInPixels)
            rv.apply {
                layoutManager = layoutManagerRV
                adapter = VideoAdapter(requireContext(), emptyList()) // Pass an empty list initiall
                addItemDecoration(itemDecoration)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.videos.collect { videos ->
                    viewModel.hideLoader()
                    if (videos.isNotEmpty()) {
                        binding.rv.adapter =
                            VideoAdapter(requireContext(), videos) // Pass an empty list initially
                    } else {
                        showToast("No Videos In the Device.")
                    }
                }
            }

            launch {
                viewModel.baseEvents.collect { event ->
                    when (event) {
                        is BaseViewModel.BaseEvent.ShowErrorMessage -> {
                            showToast(event.msg)
                        }
                    }
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission granted, you can now read videos from the device
                viewModel.fetchVideos()
            } else {
                showPermissionRationale()
            }
        }

    private fun checkPermission(permission: String) {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted
                viewModel.fetchVideos()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                showPermissionRationale()
            }

            else -> {
                // Request the permission
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun showPermissionRationale() {
        // You can show a dialog or any UI to explain why the permission is needed
    }

    override fun onBackPressed() {
        navigateBack()
    }

}