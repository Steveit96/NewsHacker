package com.steven.newshacker.repository

import com.steven.newshacker.network.SearchApiInterface

class SearchRepositoryImpl(
private val api: SearchApiInterface,
): SearchRepository {
    override suspend fun fetchSearchStoryByQuery(hitsPerPage: String, tags: String, attributesToRetrieve: String, attributesToHighlight: String, query: String) =
            api.searchStoryByQuery(hitsPerPage, tags, attributesToRetrieve, attributesToHighlight, query)
}