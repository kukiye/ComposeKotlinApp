package com.kuki.base.model

import com.kuki.base.preference.BasicDataPreferenceUtil
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.xiangxue.base.utils.MoshiUtils

@JsonClass(generateAdapter = true)
class CacheData {
    @Json(name = "networkData")
    var networkData: Any? = null

    companion object {
        fun saveDataToPreference(cachedPreferenceKey: String?, data: Any?) {
            if (data != null) {
                val cachedData = CacheData()
                cachedData.networkData = data
                BasicDataPreferenceUtil.getInstance()
                    .setString(cachedPreferenceKey, MoshiUtils.toJson(cachedData))
            }
        }
    }

    fun getNetworkDataString(): String? {
        return if (networkData != null) {
            MoshiUtils.toJson(networkData)
        } else ""
    }
}