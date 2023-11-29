package com.sulman.videostreaming.data.model

import android.net.Uri

data class VideoModel(
    val id: Long,
    val name: String,
    val duration: Long,
    val size: Long,
    val dateAdded: Long,
    val data: String,
    val contentUri: Uri
)