package com.xiangxue.news.homefragment.home

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.xiangxue.news.homefragment.api.Channel
import com.xiangxue.news.homefragment.newslist.NewsListFragment

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
class HomeFragmentAdapter(fm: FragmentManager?) : FragmentPagerAdapter(
    fm!!
) {
    private var channelSize = -1
    private var mChannels: List<Channel>? = null
    fun setChannels(channels: List<Channel>?) {
        mChannels = channels
        channelSize = mChannels?.size?:-1
        notifyDataSetChanged()
    }

    override fun getItem(pos: Int): Fragment {
        return NewsListFragment.newInstance(
            mChannels!![pos].channelId,
            mChannels!![pos].name
        )
    }

    override fun getCount(): Int {
        return if (channelSize > 0) {
            channelSize
        } else 0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mChannels!![position].name
    }

    override fun restoreState(parcelable: Parcelable?, classLoader: ClassLoader?) {}
}