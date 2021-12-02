package com.joker.utils.erros

import com.joker.data.error.mapper.ErrorMapper
import javax.inject.Inject
import com.joker.data.error.Error

/**
 * Created by Yan
 */

class ErrorManager @Inject constructor(private val errorMapper: ErrorMapper) : ErrorUseCase {
    override fun getError(errorCode: Int): Error {
        return Error(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }
}
