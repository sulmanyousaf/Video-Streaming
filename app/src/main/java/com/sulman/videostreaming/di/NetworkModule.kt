package com.sulman.videostreaming.di

import android.content.Context
import com.sulman.videostreaming.data.database.VideoDataSource
import com.sulman.videostreaming.data.repo.VideoRepository
import com.sulman.videostreaming.ui.fragment.main.MainFragmentVM
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideAppContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Singleton
    @Provides
    fun provideVideoDataSource( appContext: Context): VideoDataSource {
        return VideoDataSource(context = appContext)
    }

    @Singleton
    @Provides
    fun provideVideoRepository( videoDataSource: VideoDataSource): VideoRepository {
        return VideoRepository(videoDataSource = videoDataSource)
    }

    @Singleton
    @Provides
    fun provideVideoViewModel( videoRepository: VideoRepository): MainFragmentVM {
        return MainFragmentVM(videoRepository = videoRepository)
    }
}