package com.xiangxue.news.homefragment.newslist

import android.app.Activity
import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner
import com.kuki.base.compose.composablemanager.ComposableItem
import com.kuki.base.compose.lazycolumn.LoadMoreListHandler
import com.xiangxue.news.homefragment.api.Channel
import com.xiangxue.news.homefragment.newslist.viewmodel.NewsListViewModel

/**
author ：yeton
date : 2022/1/18 14:58
package：com.xiangxue.news.homefragment.newslist
description :
 */
const val BUNDLE_KEY_PARAM_CHANNEL_ID = "bundle_key_param_channel_id"
const val BUNDLE_KEY_PARAM_CHANNEL_NAME = "bundle_key_param_channel_name"

@Composable
fun NewslistComposable(channel: Channel) {

    val context = LocalContext.current as Activity

    val bundle = Bundle()
    bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_ID, channel.channelId)
    bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_NAME, channel.name)

    val viewModel = ViewModelProvider(
        context as ViewModelStoreOwner,
        SavedStateViewModelFactory(
            context.application,
            context as SavedStateRegistryOwner,
            bundle
        )
    )[channel.channelId + channel.name, NewsListViewModel::class.java]

    //记录列表状态
    val listState = rememberLazyListState()
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 10.dp,
            top = 10.dp
        ),
        state = listState
    ) {
        items(viewModel.dataList) {
            ComposableItem(item = it)
        }
    }

    //上拉加载更多
    LoadMoreListHandler(listState = listState) {
        viewModel.tryToLoadNextPage()
    }

}