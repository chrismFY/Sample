package com.joker.viewModel

import androidx.lifecycle.ViewModel
import com.joker.utils.erros.ErrorManager
import javax.inject.Inject


/**
 * Created by Yan
 */


abstract class BaseViewModel : ViewModel() {
    /**Inject Singleton ErrorManager
     * Use this errorManager to get the Errors
     */
    @Inject
    lateinit var errorManager: ErrorManager
}
