package com.steven.newshacker.network

import com.steven.newshacker.model.SearchModel
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiInterface {

    @GET("search_by_date")
    suspend fun searchStoryByQuery(
            @Query("hitsPerPage") hitsPerPage: String,
            @Query("tags") tags: String,
            @Query("attributesToRetrieve") attributesToRetrieve: String,
            @Query("attributesToHighlight") attributesToHighlight: String,
            @Query("query") query: String,
    ): SearchModel
}