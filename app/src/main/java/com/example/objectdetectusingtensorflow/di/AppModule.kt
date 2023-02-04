package com.example.objectdetectusingtensorflow.di

import android.app.Application
import com.example.objectdetectusingtensorflow.ml.BirdModel
import com.example.objectdetectusingtensorflow.repository.BirdRepo
import com.example.objectdetectusingtensorflow.repository.BirdRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideBirdModel(application: Application) : BirdModel{
        return BirdModel.newInstance(application)
    }

    @Provides
    fun provideBirdRepo(birdModel: BirdModel) : BirdRepo{
        return BirdRepoImpl(birdModel)
    }
}