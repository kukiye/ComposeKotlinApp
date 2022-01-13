package com.xiangxue.news.homefragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.kuki.base.model.IBaseModelListener
import com.kuki.base.model.PagingResult
import com.xiangxue.news.R
import com.xiangxue.news.databinding.FragmentHomeBinding
import com.xiangxue.news.homefragment.api.Channel
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), IBaseModelListener<List<Channel>> {
    var adapter: HomeFragmentAdapter? = null
    var viewDataBinding: FragmentHomeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        adapter = HomeFragmentAdapter(childFragmentManager)
        viewDataBinding!!.tablayout.tabMode = TabLayout.MODE_SCROLLABLE
        viewDataBinding!!.viewpager.adapter = adapter
        viewDataBinding!!.tablayout.setupWithViewPager(viewDataBinding!!.viewpager)
        viewDataBinding!!.viewpager.offscreenPageLimit = 1

        val homeModel = HomeModel(this)
        homeModel.refresh()
        return viewDataBinding!!.root
    }

    override fun onLoadSuccess(data: List<Channel>, pageResult: PagingResult?) {
        lifecycleScope.launch {
            adapter!!.setChannels(data)
        }
    }

    override fun onLoadFail(prompt: String?, pageResult: PagingResult?) {

        lifecycleScope.launch {
            Toast.makeText(context, prompt, Toast.LENGTH_SHORT).show()
        }

    }
}