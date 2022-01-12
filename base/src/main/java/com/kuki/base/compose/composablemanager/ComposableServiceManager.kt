package com.kuki.base.compose.composablemanager

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.kuki.base.compose.composablemodel.IBaseComposableModel
import java.util.*
import kotlin.collections.HashMap

/**
author ：yeton
date : 2022/1/12 10:51
package：com.kuki.base.compose.composablemanager
description :
 */
@Composable
inline fun <reified T> ComposableItem(item: T) {
    ComposableServiceManager.getComposable(item!!::class.java.name)?.also { service ->
        service.ComposableItem(item)
    } ?: run {
        Log.e("ComposableService", "Error !" + item!!::class.java.name + " not register")
    }
}

object ComposableServiceManager {
    private val composableMap = HashMap<String, IComposableService<*>>()

    fun getComposable(key: String): IComposableService<*>? {
        return composableMap[key]
    }

    //ServiceLoader 去加载所有 实现了IComposableService接口 的类
    fun collectServices() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            ServiceLoader.load(IComposableService::class.java).forEach { service ->
                composableMap[service.name] = service
            }
        }
    }

}