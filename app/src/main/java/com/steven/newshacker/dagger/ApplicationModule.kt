package com.steven.newshacker.dagger

import android.app.Application
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.steven.newshacker.db.DatabaseConst
import com.steven.newshacker.db.StoryDatabase
import com.steven.newshacker.network.SearchApiInterface
import com.steven.newshacker.network.StoryApiInterface
import com.steven.newshacker.repository.SearchRepository
import com.steven.newshacker.repository.SearchRepositoryImpl
import com.steven.newshacker.repository.StoryRepository
import com.steven.newshacker.repository.StoryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideStoryDataApi(): StoryApiInterface =
        Retrofit.Builder()
            .baseUrl(StoryApiInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(StoryApiInterface::class.java)

    @Provides
    fun provideStoryRepository(api: StoryApiInterface, storyDb: StoryDatabase): StoryRepository = StoryRepositoryImpl(api, storyDb)

    @Singleton
    @Provides
    fun provideSearchDataApi(): SearchApiInterface =
            Retrofit.Builder()
                    .baseUrl(SearchApiInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                    .build()
                    .create(SearchApiInterface::class.java)

    @Provides
    fun provideSearchRepository(api: SearchApiInterface): SearchRepository = SearchRepositoryImpl(api)


    @Singleton
    @Provides
    fun provideDatabase(app: Application) =
        Room.databaseBuilder(app, StoryDatabase::class.java, DatabaseConst.STORY_DATABASE_NAME)
            .addMigrations()
            .build()

}