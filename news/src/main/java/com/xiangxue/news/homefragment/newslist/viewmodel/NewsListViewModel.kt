package com.xiangxue.news.homefragment.newslist.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kuki.base.compose.composablemodel.IBaseComposableModel
import com.kuki.base.viewmodel.BaseViewModel
import com.xiangxue.news.homefragment.newslist.NewsListFragment
import com.xiangxue.news.homefragment.newslist.model.NewsListModel

/**
author ：yeton
date : 2022/1/14 18:16
package：com.xiangxue.news.homefragment.newslist.viewmodel
description :
 */
class NewsListViewModel(savedStateHandle: SavedStateHandle) :
    BaseViewModel<NewsListModel, IBaseComposableModel>(savedStateHandle) {

    override fun createModel(): NewsListModel {
        return NewsListModel(
            viewModelScope,
            savedStateHandle?.get(NewsListFragment.BUNDLE_KEY_PARAM_CHANNEL_ID),
            savedStateHandle?.get(NewsListFragment.BUNDLE_KEY_PARAM_CHANNEL_NAME), this
        )
    }

}