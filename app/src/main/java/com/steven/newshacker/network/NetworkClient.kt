package com.steven.newshacker.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StoryNetWorkApiClient {


    val STORY_BASE_URL = "https://hacker-news.firebaseio.com/v0/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(STORY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val STORY_API_SERVICE: StoryApiInterface = getRetrofit().create(StoryApiInterface::class.java)
}

object SearchNetworkApiClient {

    val SEARCH_BASE_URL = "https://hn.algolia.com/api/v1/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(SEARCH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val SEARCH_API_SERVICE: SearchApiInterface = getRetrofit().create(SearchApiInterface::class.java)
}