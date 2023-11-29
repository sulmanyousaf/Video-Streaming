package com.sulman.videostreaming.ui.fragment.main

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sulman.videostreaming.data.model.VideoModel
import com.sulman.videostreaming.databinding.ListItemBinding

class VideoAdapter(private val context: Context, private val videoUris: List<VideoModel>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videoUris[position].contentUri)
    }

    override fun getItemCount(): Int {
        return videoUris.size
    }

    inner class VideoViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(uri: Uri) {
            binding.apply {
                simpleVideoView.setVideoURI(uri)
                ivPlayPause.visibility = View.VISIBLE
                ivReplay.visibility = View.GONE

                ivPlayPause.setOnClickListener {
                    ivPlayPause.visibility = View.GONE
                    simpleVideoView.start()
                }

                ivReplay.setOnClickListener {
                    ivReplay.visibility = View.GONE
                    simpleVideoView.seekTo(0)
                    simpleVideoView.start()
                }

                simpleVideoView.setOnCompletionListener {
                    ivReplay.visibility = View.VISIBLE
                }
            }
        }
    }
}


