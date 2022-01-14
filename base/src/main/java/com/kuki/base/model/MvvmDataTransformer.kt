package com.kuki.base.model

/**
author ：yeton
date : 2022/1/14 11:36
package：com.kuki.base.model
description :将网络数据转换成实体的接口
 */
interface MvvmDataTransformer<NETWORK_DATA> {

    fun onDataTransform(networkData: NETWORK_DATA, isFromCache: Boolean)
    fun onFailure(e: String?)

}