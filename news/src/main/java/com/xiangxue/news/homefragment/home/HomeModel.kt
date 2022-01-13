package com.xiangxue.news.homefragment.home

import com.kuki.base.model.BaseMvvmModel
import com.kuki.base.model.IBaseModelListener
import com.xiangxue.network.TecentNetworkWithoutEnvelopeApi
import com.xiangxue.network.apiresponse.NetworkResponse
import com.xiangxue.news.homefragment.api.Channel
import com.xiangxue.news.homefragment.api.NewsApiInterface
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
author ：yeton
date : 2022/1/13 16:32
package：com.xiangxue.news.homefragment.home
description :
 */
class HomeModel(
    iBaseModelListener: IBaseModelListener<List<Channel>>
) : BaseMvvmModel<List<Channel>>(iBaseModelListener = iBaseModelListener) {

    override fun load() {
        GlobalScope.launch {
            when (val newsChannelsBean =
                TecentNetworkWithoutEnvelopeApi.getService(NewsApiInterface::class.java)
                    .getNewsChannelsWithoutEnvelope()) {
                is NetworkResponse.Success -> {
                    onSuccess(newsChannelsBean!!.body.channelList)
//                    adapter!!.setChannels(newsChannelsBean!!.body.channelList)
                }
                is NetworkResponse.ApiError -> {
                    onFailure(newsChannelsBean.body.toString())
                }
                is NetworkResponse.NetworkError -> {
                    onFailure(newsChannelsBean.message.toString())
                }
                is NetworkResponse.UnknownError -> {
                    onFailure(newsChannelsBean.error?.message)
                }
            }
        }
    }


    private fun onSuccess(channelList: List<Channel>) {
        notifyResultToListener(channelList)

    }

    private fun onFailure(errorMsg: String?) {
        notifyFailToListener(errorMsg)
    }

}