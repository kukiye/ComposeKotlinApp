package com.xiangxue.news.homefragment.home

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.android.material.tabs.TabLayout
import com.xiangxue.news.R
import com.xiangxue.news.homefragment.newslist.NewslistComposable
import kotlinx.coroutines.launch

/**
author ：yeton
date : 2022/1/18 14:12
package：com.xiangxue.news.homefragment.home
description :
 */

@ExperimentalPagerApi
@Composable
fun HomeComposable() {
    val viewModel: HomeViewModel = viewModel()
    var tabIndex by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

    Column {
        //水平可滑动的Tab
        ScrollableTabRow(
            backgroundColor = colorResource(R.color.colorAccent),
            selectedTabIndex = pagerState.currentPage,
            contentColor = Color.White,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }
        ) {
            //设置数据
            viewModel.dataList.forEachIndexed { index, channel ->
                val selected = tabIndex == index
                val interactionSource = remember { MutableInteractionSource() }
                val isPress = interactionSource.collectIsPressedAsState().value
                Tab(
                    selected = selected,
                    text = {
                        Text(
                            text = channel.name,
                            color = if (isPress || pagerState.currentPage == index) Color.White else Color.Gray,
                        )
                    },
                    onClick = {
                        tabIndex = index
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }


        }

        //水平分页器
        HorizontalPager(count = viewModel.dataList.size, state = pagerState) { index ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NewslistComposable(viewModel.dataList[index])
            }
        }


    }

}