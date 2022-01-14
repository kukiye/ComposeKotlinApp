package com.xiangxue.news.homefragment.newslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kuki.base.compose.composablemanager.ComposableItem
import com.kuki.base.compose.composablemanager.ComposableServiceManager
import com.kuki.base.compose.composablemodel.IBaseComposableModel
import com.kuki.base.compose.lazycolumn.LoadMoreListHandler
import com.kuki.base.model.IBaseModelListener
import com.kuki.base.model.PagingResult
import com.xiangxue.news.homefragment.newslist.model.NewsListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
class NewsListFragment : Fragment(), IBaseModelListener<List<IBaseComposableModel>> {

    private lateinit var newsListModel: NewsListModel
    private val contentlist = mutableStateListOf<IBaseComposableModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        ComposableServiceManager.collectServices()
        newsListModel = NewsListModel(
            arguments?.getString(BUNDLE_KEY_PARAM_CHANNEL_ID),
            arguments?.getString(BUNDLE_KEY_PARAM_CHANNEL_NAME), this
        )
        newsListModel.refresh()

        return ComposeView(requireContext()).apply {
            setContent {
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
                    items(contentlist) {
                       ComposableItem(item = it)
                    }
                }

                //上拉加载更多
                LoadMoreListHandler(listState = listState) {
                    newsListModel.loadNextPage()
                }
            }
        }
    }


    companion object {
        const val BUNDLE_KEY_PARAM_CHANNEL_ID = "bundle_key_param_channel_id"
        const val BUNDLE_KEY_PARAM_CHANNEL_NAME = "bundle_key_param_channel_name"
        fun newInstance(channelId: String?, channelName: String?): NewsListFragment {
            val fragment = NewsListFragment()
            val bundle = Bundle()
            bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_ID, channelId)
            bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_NAME, channelName)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onLoadSuccess(data: List<IBaseComposableModel>, pageResult: PagingResult?) {
        if (pageResult?.isFirstPage == true) {
            contentlist.clear()
        }
        if (data != null) {
            contentlist.addAll(data)
        }
    }

    override fun onLoadFail(prompt: String?, pageResult: PagingResult?) {
        lifecycleScope.launch(Dispatchers.Main) {
            Toast.makeText(context, prompt, Toast.LENGTH_LONG).show()
        }
    }
}