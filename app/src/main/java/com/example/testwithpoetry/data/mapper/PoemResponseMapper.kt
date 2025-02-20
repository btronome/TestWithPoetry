package com.example.testwithpoetry.data.mapper

import com.example.testwithpoetry.data.remote.model.PoemResponse
import com.example.testwithpoetry.domain.model.Poem

fun PoemResponse.toDomainModel(): Poem {
    return Poem(
        title = this.title,
        author = this.author,
        lines = this.lines,
        lineCount = this.linecount
    )
}