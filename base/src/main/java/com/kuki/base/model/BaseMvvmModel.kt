package com.kuki.base.model

/**
author ：yeton
date : 2022/1/13 16:58
package：com.kuki.base.model
description :
 */
abstract class BaseMvvmModel<RESULT_DATA>(

    private val isPaging: Boolean = false,//是否分页获取数据
    private val initPagerNumber: Int = 1,//初始化从第几页开始加载
    private val iBaseModelListener: IBaseModelListener<RESULT_DATA>
) {

    //是否在加载中 预防重复加载
    protected var mIsLoading = false

    //页码
    var mPageNumber = initPagerNumber

    open fun refresh() {
        if (!mIsLoading) {
            mIsLoading = true
            if (isPaging) {
                mPageNumber = initPagerNumber
            }
            load()
        }
    }

    open fun loadNextPage() {
        if (!mIsLoading) {
            mIsLoading = true
            load()
        }
    }

    protected open fun isRefresh(): Boolean {
        return mPageNumber == initPagerNumber
    }

    protected abstract fun load()

    protected open fun notifyResultToListener(resultData: RESULT_DATA) {
        if (iBaseModelListener != null) {
            if (isPaging) {
                iBaseModelListener.onLoadSuccess(
                    resultData,
                    PagingResult(
                        if (resultData == null) true else (resultData as List<*>).isEmpty(),
                        isRefresh(),
                        if (resultData == null) false else (resultData as List<*>).size > 0
                    )
                )

                //更新页数
                if (resultData != null && (resultData as List<*>).size > 0) {
                    mPageNumber++
                }
            } else {
                iBaseModelListener.onLoadSuccess(resultData)
            }
        }
        mIsLoading = false
    }

    protected open fun notifyFailToListener(errorMsg: String?) {
        if (iBaseModelListener != null) {
            iBaseModelListener.onLoadFail(errorMsg)
        }
        mIsLoading = false
    }

}