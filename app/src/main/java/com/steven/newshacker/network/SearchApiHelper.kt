package com.steven.newshacker.network

import retrofit2.http.Query

class SearchApiHelper(private val searchApiInterface: SearchApiInterface) {

    suspend fun fetchSearchStoryByQuery(
            hitsPerPage: String,
            tags: String,
            attributesToRetrieve: String,
            attributesToHighlight: String,
            query: String,
    ) = searchApiInterface.searchStoryByQuery(
            hitsPerPage,
            tags,
            attributesToRetrieve,
            attributesToHighlight,
            query
    )

}