package com.kuki.base.model

/**
author ：yeton
date : 2022/1/12 11:51
package：com.kuki.base.loadmore
description :Model返回给View的结果回调
 */
interface IBaseModelListener<RESULT_DATA> {

    fun onLoadSuccess(data: RESULT_DATA, pageResult: PagingResult? = null)
    fun onLoadFail(prompt: String?, pageResult: PagingResult? = null)

}