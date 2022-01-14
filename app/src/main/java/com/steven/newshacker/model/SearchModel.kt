package com.steven.newshacker.model

data class SearchModel(
        val hits: List<HitsModel> = emptyList(),
        val nbHits: Long = 0L,
        val page: Int = 0,
        val nbPages: Int = 0,
        val hitsPerPage: Int = 0,
        val exhaustiveNbHits: Boolean = false,
        val exhaustiveTypo: Boolean = false,
        val query: String = "",
        val params: String = "",

)