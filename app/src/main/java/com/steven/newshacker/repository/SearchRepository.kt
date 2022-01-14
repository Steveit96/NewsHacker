package com.steven.newshacker.repository

import com.steven.newshacker.network.SearchApiHelper

class SearchRepository(private val searchApiHelper: SearchApiHelper) {

    suspend fun fetchSearchStoryByQuery(
            hitsPerPage: String,
            tags: String,
            attributesToRetrieve: String,
            attributesToHighlight: String,
            query: String,
    ) = searchApiHelper.fetchSearchStoryByQuery(
            hitsPerPage,
            tags,
            attributesToRetrieve,
            attributesToHighlight,
            query
    )

}