package com.kuki.base.compose.composablemanager;

import androidx.compose.runtime.Composable;

import kotlin.Unit;

/**
 * author ：yeton
 * date : 2022/1/12 10:52
 * package：com.kuki.base.compose.composablemanager
 * description :新闻列表条目服务类
 */
interface IComposableService<T> {
    val content: @Composable (item: T) -> Unit

    val name: String

    @Suppress("UNCHECKED_CAST")
    @Composable
    fun ComposableItem(item: Any) {
        (item as? T)?.let {
            content(item)
        }
    }

}
