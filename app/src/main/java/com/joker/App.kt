package com.joker

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import dagger.hilt.android.HiltAndroidApp
import java.util.*

/**
 * Created by Yan
 */
@HiltAndroidApp
open class App : Application(), ViewModelStoreOwner {

    private val appViewModelStore: ViewModelStore by lazy {
        ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return appViewModelStore
    }

    override fun onCreate() {
        super.onCreate()

//        AutoSize.initCompatMultiProcess(this)
//
//        AutoSizeConfig.getInstance()
//            .setCustomFragment(true)
//            .setExcludeFontScale(true)
//            .onAdaptListener = object : onAdaptListener {
//            override fun onAdaptBefore(target: Any, activity: Activity) {
//                AutoSizeLog.d(
//                    String.format(
//                        Locale.ENGLISH,
//                        "%s onAdaptBefore!",
//                        target.javaClass.name
//                    )
//                )
//            }
//
//            override fun onAdaptAfter(target: Any, activity: Activity) {
//                AutoSizeLog.d(
//                    String.format(
//                        Locale.ENGLISH,
//                        "%s onAdaptAfter!",
//                        target.javaClass.name
//                    )
//                )
//            }
//        }
    }
}
