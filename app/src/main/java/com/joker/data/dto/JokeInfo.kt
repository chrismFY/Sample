package com.joker.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class JokeInfo{
    @PrimaryKey(autoGenerate = true)
    var index: Int = 0
    var id: String? = null
    var joke: String?= null
    var isFavorite:Boolean = false
    var status: Int? = null


    override fun equals(other: Any?): Boolean {
        return joke == ((other as JokeInfo).joke)
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (joke?.hashCode() ?: 0)
        result = 31 * result + isFavorite.hashCode()
        result = 31 * result + (status ?: 0)
        result = 31 * result + index
        return result
    }


}