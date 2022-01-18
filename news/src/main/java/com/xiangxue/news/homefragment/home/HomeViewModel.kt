package com.xiangxue.news.homefragment.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuki.base.model.IBaseModelListener
import com.kuki.base.model.PagingResult
import com.kuki.base.viewmodel.BaseViewModel
import com.xiangxue.news.homefragment.api.Channel

/**
author ：yeton
date : 2022/1/17 10:23
package：com.xiangxue.news.homefragment.home
description :
 */
class HomeViewModel(savedStateHandle: SavedStateHandle) :
    BaseViewModel<HomeModel, Channel>(savedStateHandle) {

    override fun createModel(): HomeModel {
        return HomeModel(viewModelScope, this)
    }
}