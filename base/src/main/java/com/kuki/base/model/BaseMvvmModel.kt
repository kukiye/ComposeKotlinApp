package com.kuki.base.model

import android.util.Log

/**
author ：yeton
date : 2022/1/13 16:58
package：com.kuki.base.model
description :
 */
abstract class BaseMvvmModel<NETWORK_DATA, RESULT_DATA>(

    private val isPaging: Boolean = false,//是否分页获取数据
    private val initPagerNumber: Int = 1,//初始化从第几页开始加载
    private val iBaseModelListener: IBaseModelListener<RESULT_DATA>,
    private val mCachedPreferenceKey: String? = null,//缓存到SharedPerference的Key，有就缓存，没有就不缓存
    private val apkPredefinedData: String? = null//apk预置缓存数据
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

    protected open fun isFirstPage(): Boolean {
        return mPageNumber == initPagerNumber
    }

    protected abstract fun load()

    protected open fun notifyResultToListener(networkData: NETWORK_DATA, resultData: RESULT_DATA,
                                              isFromCache: Boolean) {
        if (iBaseModelListener != null) {
            if (isPaging) {
                iBaseModelListener.onLoadSuccess(
                    resultData,
                    PagingResult(
                        if (resultData == null) true else (resultData as List<*>).isEmpty(),
                        isFirstPage(),
                        if (resultData == null) false else (resultData as List<*>).size > 0
                    )
                )
//                Log.d("notifyResultToListener", "mCachedPreferenceKey:${mCachedPreferenceKey}")
//                Log.d("notifyResultToListener", "mPageNumber:${mPageNumber}")
//                Log.d("notifyResultToListener", "isFromCache:${isFromCache}")
                //save NetworkData to preference 保存第一页的数据
                if (mCachedPreferenceKey != null && isFirstPage() && !isFromCache) {
                    CacheData.saveDataToPreference(mCachedPreferenceKey, networkData)
                }

                //更新页数
                if (resultData != null && (resultData as List<*>).size > 0) {
                    mPageNumber++
                }
            } else {
                iBaseModelListener.onLoadSuccess(resultData)
                // save NetworkData to preference
                if (mCachedPreferenceKey != null && !isFromCache) {
                    CacheData.saveDataToPreference(mCachedPreferenceKey, networkData)
                }
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