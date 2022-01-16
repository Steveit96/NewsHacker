package com.steven.newshacker.repository

import com.steven.newshacker.model.SearchModel

interface SearchRepository {

    suspend fun fetchSearchStoryByQuery(
            hitsPerPage: String,
            tags: String,
            attributesToRetrieve: String,
            attributesToHighlight: String,
            query: String,
    ): SearchModel

}