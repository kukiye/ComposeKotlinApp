package com.xiangxue.news.homefragment.newslist.composables.titlecomposable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.auto.service.AutoService
import com.kuki.base.compose.composablemanager.IComposableService

//创建服务来实现IComposableService接口
@AutoService(IComposableService::class)
class TitleComposableService() : IComposableService<TitleComposableModel> {
    override val content: @Composable (item: TitleComposableModel) -> Unit = {
        TitleComposable(composableModel = it)
    }
    override val name: String = TitleComposableModel::class.java.name
}


@Composable
fun TitleComposable(composableModel: TitleComposableModel) {
    Box(modifier = Modifier) {
        Text(
            text = composableModel.title,
            color = Color.Black,
            fontSize = 25.sp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(15.dp)
        )
    }

    val pxValue = with(LocalDensity.current) { 1.dp.toPx() }

    Spacer(
        Modifier
            .background(Color.Gray)
            .fillMaxWidth()
            .height((1 / pxValue).dp)
    )
}