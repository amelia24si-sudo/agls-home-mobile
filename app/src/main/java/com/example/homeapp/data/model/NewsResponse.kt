package com.example.homeapp.data.model

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val results: List<Article>
)

data class Article(
    val title: String?,
    val description: String?,
    val image_url: String?,
    val link: String?
)