package com.xiangxue.news.homefragment.newslist.viewmodel

import android.os.Bundle
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.kuki.base.compose.composablemodel.IBaseComposableModel
import com.kuki.base.model.IBaseModelListener
import com.kuki.base.model.PagingResult
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
class NewslistViewModel(arguments: Bundle) : ViewModel(),
    IBaseModelListener<List<IBaseComposableModel>> {

    private var newsListModel: NewsListModel = NewsListModel(
        arguments?.getString(NewsListFragment.BUNDLE_KEY_PARAM_CHANNEL_ID),
        arguments?.getString(NewsListFragment.BUNDLE_KEY_PARAM_CHANNEL_NAME), this
    )
    val contentlist = mutableStateListOf<IBaseComposableModel>()

    init {
        newsListModel.refresh()
    }

    override fun onLoadSuccess(data: List<IBaseComposableModel>, pageResult: PagingResult?) {
        if (pageResult?.isFirstPage == true) {
            contentlist.clear()
        }
        if (data != null) {
            contentlist.addAll(data)
        }
    }

    override fun onLoadFail(prompt: String?, pageResult: PagingResult?) {
        /*lifecycleScope.launch(Dispatchers.Main) {
            Toast.makeText(context, prompt, Toast.LENGTH_LONG).show()
        }*/
    }

    fun  loadNextPage(){
        newsListModel.loadNextPage()
    }
}