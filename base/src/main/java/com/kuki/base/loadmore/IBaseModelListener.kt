package com.kuki.base.loadmore

/**
author ：yeton
date : 2022/1/12 11:51
package：com.kuki.base.loadmore
description :加载更多监听器
 */
interface IBaseModelListener<T> {

    fun onLoadSuccess(data: T, pageResult: PagingResult? = null)
    fun onLoadFail(prompt: String?, pageResult: PagingResult? = null)

}