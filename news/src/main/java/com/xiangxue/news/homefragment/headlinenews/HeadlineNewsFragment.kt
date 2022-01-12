package com.xiangxue.news.homefragment.headlinenews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.xiangxue.network.TecentNetworkWithoutEnvelopeApi
import com.xiangxue.network.apiresponse.NetworkResponse
import com.xiangxue.news.homefragment.api.NewsApiInterface
import com.xiangxue.news.R
import com.xiangxue.news.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HeadlineNewsFragment : Fragment() {
    var adapter: HeadlineNewsFragmentAdapter? = null
    var viewDataBinding: FragmentHomeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        adapter = HeadlineNewsFragmentAdapter(childFragmentManager)
        viewDataBinding!!.tablayout.tabMode = TabLayout.MODE_SCROLLABLE
        viewDataBinding!!.viewpager.adapter = adapter
        viewDataBinding!!.tablayout.setupWithViewPager(viewDataBinding!!.viewpager)
        viewDataBinding!!.viewpager.offscreenPageLimit = 1
        load()
        return viewDataBinding!!.root
    }

    protected fun load() {
        lifecycleScope.launch {
            when (val newsChannelsBean =
                TecentNetworkWithoutEnvelopeApi.getService(NewsApiInterface::class.java)
                    .getNewsChannelsWithoutEnvelope()) {
                is NetworkResponse.Success -> {
                    adapter!!.setChannels(newsChannelsBean!!.body.channelList)

                }
            }
        }
    }
}