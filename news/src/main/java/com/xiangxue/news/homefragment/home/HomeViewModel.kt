package com.xiangxue.news.homefragment.home

import androidx.lifecycle.ViewModel
import com.kuki.base.model.IBaseModelListener
import com.kuki.base.model.PagingResult
import com.xiangxue.news.homefragment.api.Channel

/**
author ：yeton
date : 2022/1/17 10:23
package：com.xiangxue.news.homefragment.home
description :
 */
class HomeViewModel(private val view: HomeFragment) : ViewModel(),
    IBaseModelListener<List<Channel>> {

    private val homeModel = HomeModel(this)

    fun refresh(){
        homeModel.refresh()
    }

    override fun onLoadSuccess(data: List<Channel>, pageResult: PagingResult?) {
        view.onLoadSuccess(data, pageResult)
    }

    override fun onLoadFail(prompt: String?, pageResult: PagingResult?) {
        view.onLoadFail(prompt, pageResult)
    }


}