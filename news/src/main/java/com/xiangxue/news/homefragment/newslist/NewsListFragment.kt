package com.xiangxue.news.homefragment.newslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiangxue.network.TecentNetworkWithoutEnvelopeApi
import com.xiangxue.network.apiresponse.NetworkResponse
import com.xiangxue.news.homefragment.api.NewsApiInterface
import com.xiangxue.news.homefragment.api.NewsListBean
import com.xiangxue.news.R
import com.xiangxue.news.databinding.FragmentNewsBinding
import com.xiangxue.news.homefragment.api.Contentlist
import com.xiangxue.news.homefragment.newslist.composables.titlecomposable.TitleComposable
import com.xiangxue.news.homefragment.newslist.composables.titlecomposable.TitleComposableModel
import com.xiangxue.news.homefragment.newslist.composables.titlepicturecomposable.TitlePictureComposable
import com.xiangxue.news.homefragment.newslist.composables.titlepicturecomposable.TitlePictureComposableModel
import kotlinx.coroutines.launch
import java.util.ArrayList
import com.kuki.base.compose.lazycolumn.LoadMoreListHandler

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
class NewsListFragment : Fragment() {
    private val contentlist = mutableStateListOf<Any>()
    private var mPage = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        load()
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
                    items(contentlist.size) {
                        if (contentlist[it] is TitleComposableModel) {
                            TitleComposable(contentlist[it] as TitleComposableModel)
                        } else if (contentlist[it] is TitlePictureComposableModel) {
                            TitlePictureComposable(contentlist[it] as TitlePictureComposableModel)
                        }
                    }
                }

                //上拉加载更多
                LoadMoreListHandler(listState = listState) {
                    load()
                }
            }
        }
    }

    private fun load() {
        lifecycleScope.launch {

            val newsListBean =
                TecentNetworkWithoutEnvelopeApi.getService(NewsApiInterface::class.java)
                    .getNewsList(
                        arguments?.getString(BUNDLE_KEY_PARAM_CHANNEL_ID),
                        arguments?.getString(BUNDLE_KEY_PARAM_CHANNEL_NAME), mPage.toString()
                    )
            when (newsListBean) {
                is NetworkResponse.Success -> {
                    if (mPage == 1) {
                        contentlist.clear()
                    }
                    for (source in (newsListBean.body)!!.pagebean!!.contentlist!!) {
                        if (source.imageurls != null && source.imageurls.size > 1) {
                            contentlist.add(
                                TitlePictureComposableModel(
                                    source.title ?: "",
                                    source.imageurls[0].url ?: ""
                                )
                            )
                        } else {
                            contentlist.add(TitleComposableModel(source.title ?: ""))
                        }
                    }

                    mPage++
                }
                else -> {

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
}