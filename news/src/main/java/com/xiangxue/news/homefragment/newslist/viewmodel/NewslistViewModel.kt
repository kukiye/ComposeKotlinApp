package com.xiangxue.news.homefragment.newslist.viewmodel

import android.os.Bundle
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.kuki.base.compose.composablemodel.IBaseComposableModel
import com.kuki.base.model.IBaseModelListener
import com.kuki.base.model.PagingResult
import com.kuki.base.viewmodel.BaseViewModel
import com.xiangxue.news.homefragment.newslist.NewsListFragment
import com.xiangxue.news.homefragment.newslist.model.NewsListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
author ：yeton
date : 2022/1/14 18:16
package：com.xiangxue.news.homefragment.newslist.viewmodel
description :
 */
class NewslistViewModel(savedStateHandle: SavedStateHandle) : BaseViewModel<NewsListModel, IBaseComposableModel>(savedStateHandle) {

    override fun createModel(): NewsListModel {
      return  NewsListModel(
          savedStateHandle?.get(NewsListFragment.BUNDLE_KEY_PARAM_CHANNEL_ID),
          savedStateHandle?.get(NewsListFragment.BUNDLE_KEY_PARAM_CHANNEL_NAME), this
        )
    }

}