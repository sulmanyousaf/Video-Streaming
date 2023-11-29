package com.sulman.videostreaming.data.repo

import com.sulman.videostreaming.data.database.VideoDataSource
import com.sulman.videostreaming.data.model.VideoModel
import javax.inject.Inject

class VideoRepository @Inject constructor(private val videoDataSource: VideoDataSource) {

    suspend fun getAllVideos(): List<VideoModel> {
        return videoDataSource.getAllVideos()
    }
}