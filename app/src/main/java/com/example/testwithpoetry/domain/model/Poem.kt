package com.example.testwithpoetry.domain.model

data class Poem(
    val title: String,
    val author: String,
    val lines: List<String>,
    val lineCount: String
)