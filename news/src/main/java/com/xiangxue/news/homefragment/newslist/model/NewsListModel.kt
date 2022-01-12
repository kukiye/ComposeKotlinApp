package com.xiangxue.news.homefragment.newslist.model

import androidx.compose.runtime.mutableStateListOf
import com.kuki.base.compose.composablemodel.IBaseComposableModel
import com.kuki.base.loadmore.IBaseModelListener
import com.kuki.base.loadmore.PagingResult
import com.xiangxue.network.TecentNetworkWithoutEnvelopeApi
import com.xiangxue.network.apiresponse.NetworkResponse
import com.xiangxue.news.homefragment.api.NewsApiInterface
import com.xiangxue.news.homefragment.api.NewsListBean
import com.xiangxue.news.homefragment.newslist.composables.titlecomposable.TitleComposableModel
import com.xiangxue.news.homefragment.newslist.composables.titlepicturecomposable.TitlePictureComposableModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

/**
author ：yeton
date : 2022/1/12 11:46
package：com.xiangxue.news.homefragment.newslist.model
description :
 */
class NewsListModel(
    private val channelId: String?,
    private val channelName: String?,
    private val iBaseModelListener: IBaseModelListener<List<IBaseComposableModel>>
) {

    private var mPage: Int = 1

    fun refresh() {
        mPage = 1
        load()
    }

    fun load() {
        GlobalScope.launch {

            val newsListBean =
                TecentNetworkWithoutEnvelopeApi.getService(NewsApiInterface::class.java)
                    .getNewsList(
                        channelId,
                        channelName, mPage.toString()
                    )
            when (newsListBean) {
                is NetworkResponse.Success -> {
                    onSuccess(newsListBean.body)
                }
                is NetworkResponse.ApiError -> {
                    onFailure(newsListBean.body.toString())
                }
                is NetworkResponse.NetworkError -> {
                    onFailure(newsListBean.message.toString())
                }
                is NetworkResponse.UnknownError -> {
                    onFailure(newsListBean.error?.message)
                }
            }
        }
    }

    private fun onSuccess(body: NewsListBean?) {
        val baseViewModels: ArrayList<IBaseComposableModel> = ArrayList<IBaseComposableModel>()
        for (newsBean in body!!.pagebean!!.contentlist!!) {
            if (newsBean.imageurls != null && newsBean.imageurls.size > 1) {
                val viewModel =
                    TitlePictureComposableModel(
                        newsBean.title ?: "",
                        newsBean.imageurls[0].url ?: ""
                    )
                baseViewModels.add(viewModel)
            } else {
                val viewModel = TitleComposableModel(newsBean.title ?: "")
                baseViewModels.add(viewModel)
            }
        }
        iBaseModelListener.onLoadSuccess(
            baseViewModels,
            PagingResult(mPage == 1, baseViewModels.isEmpty(), baseViewModels.isNotEmpty())
        )
        mPage++

    }

    private fun onFailure(e: String?) {
        iBaseModelListener.onLoadFail(e)
    }

}