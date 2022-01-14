package com.xiangxue.news.homefragment.newslist.model

import com.kuki.base.compose.composablemodel.IBaseComposableModel
import com.kuki.base.model.BaseMvvmModel
import com.kuki.base.model.IBaseModelListener
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
    iBaseModelListener: IBaseModelListener<List<IBaseComposableModel>>
) : BaseMvvmModel<NewsListBean, List<IBaseComposableModel>>(
    true,
    iBaseModelListener = iBaseModelListener,
    mCachedPreferenceKey = "news_list_model_${channelId}"
) {

    override fun load() {
        GlobalScope.launch {

            val newsListBean =
                TecentNetworkWithoutEnvelopeApi.getService(NewsApiInterface::class.java)
                    .getNewsList(
                        channelId,
                        channelName, mPageNumber.toString()
                    )
            when (newsListBean) {
                is NetworkResponse.Success -> {
                    onDataTransform(newsListBean.body, false)
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

    override fun onFailure(errorMsg: String?) {
        notifyFailToListener(errorMsg)
    }

    override fun onDataTransform(networkData: NewsListBean, isFromCache: Boolean) {
        val baseViewModels: ArrayList<IBaseComposableModel> = ArrayList<IBaseComposableModel>()
        for (newsBean in networkData!!.pagebean!!.contentlist!!) {
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
        notifyResultToListener(networkData, baseViewModels, isFromCache)
    }

}