package com.joker.data.dto

data class JokeInfo(
    var id: String?,
    var joke: String?,
    var isRead:Boolean = false,
    var status: Int?
)