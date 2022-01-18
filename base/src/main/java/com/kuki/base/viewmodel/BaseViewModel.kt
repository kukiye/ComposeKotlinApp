package com.kuki.base.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.kuki.base.model.BaseModel
import com.kuki.base.model.IBaseModelListener
import com.kuki.base.model.PagingResult

/**
author ：yeton
date : 2022/1/17 11:16
package：com.kuki.base.viewmodel
description :
 */
abstract class BaseViewModel<MODEL : BaseModel<*, *>?, DATA>(val savedStateHandle: SavedStateHandle?) :
    ViewModel(), LifecycleObserver,
    IBaseModelListener<List<DATA>> {

    protected var model: MODEL? = null
    var dataList = mutableStateListOf<DATA>()

    //可以动态改变的视图状态
    var viewStatusLiveData = mutableStateOf(ViewStatus.LOADING)

    init {
        model = createModel()
        tryToRefresh()
    }

    abstract fun createModel(): MODEL

    protected fun tryToRefresh() {
        if (model == null) {
            model = createModel()
        }
        model!!.refresh()
    }

    open fun tryToLoadNextPage() {
        if (model == null) {
            model = createModel()
        }
        model!!.loadNextPage()
    }


    override fun onLoadSuccess(data: List<DATA>, pageResult: PagingResult?) {
        if (model != null) {
            if (model!!.isPaging) {
                if (pageResult?.isFirstPage == true) {
                    dataList!!.clear()
                }
                if (pageResult?.isEmpty == true) {
                    if (pageResult?.isFirstPage) {
                        viewStatusLiveData.value = ViewStatus.EMPTY
                    } else {
                        viewStatusLiveData.value = ViewStatus.NO_MORE_DATA
                    }
                } else {
                    if (data != null) {
                        dataList!!.addAll(data)
                    }
                    viewStatusLiveData.value = ViewStatus.SHOW_CONTENT
                }
            } else {
                dataList!!.clear()
                if (data != null) {
                    dataList!!.addAll(data)
                }
                viewStatusLiveData.value = ViewStatus.SHOW_CONTENT
            }
        }
    }

    override fun onLoadFail(prompt: String?, pageResult: PagingResult?) {
        if (model!!.isPaging && !pageResult?.isFirstPage!!) {
            viewStatusLiveData.value = ViewStatus.LOAD_MORE_FAILED
        } else {
            viewStatusLiveData.value = ViewStatus.REFRESH_ERROR
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        if (dataList == null || dataList!!.size == 0) {
            model!!.refresh()
        } else {
            viewStatusLiveData.value = viewStatusLiveData.value
        }
    }
}