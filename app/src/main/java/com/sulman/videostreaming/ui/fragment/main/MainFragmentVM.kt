package com.sulman.videostreaming.ui.fragment.main

import androidx.lifecycle.viewModelScope
import com.sulman.videostreaming.data.model.VideoModel
import com.sulman.videostreaming.data.repo.VideoRepository
import com.sulman.videostreaming.ui.fragment.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentVM @Inject constructor(private val videoRepository: VideoRepository) : BaseViewModel() {
    private val _videos = MutableStateFlow<List<VideoModel>>(emptyList())
    val videos = _videos.asStateFlow()

    fun fetchVideos() {
        showLoader()
        viewModelScope.launch {
            _videos.value = videoRepository.getAllVideos()
//            hideLoader()
        }
    }
}