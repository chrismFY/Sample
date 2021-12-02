package com.joker.utils.erros

import com.joker.data.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
