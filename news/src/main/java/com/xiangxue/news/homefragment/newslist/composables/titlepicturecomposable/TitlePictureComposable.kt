package com.xiangxue.news.homefragment.newslist.composables.titlepicturecomposable

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
import com.xiangxue.news.R
import com.xiangxue.news.homefragment.newslist.commoncomposables.NetworkImage
import com.xiangxue.news.homefragment.newslist.composables.titlecomposable.TitleComposable
import com.xiangxue.news.homefragment.newslist.composables.titlecomposable.TitleComposableModel

@AutoService(IComposableService::class)
class TitlePictureComposableService() : IComposableService<TitlePictureComposableModel> {
    override val content: @Composable (item: TitlePictureComposableModel) -> Unit = {
        TitlePictureComposable(composableModel = it)
    }
    override val name: String = TitlePictureComposableModel::class.java.name
}

@Composable
fun TitlePictureComposable(composableModel: TitlePictureComposableModel){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box {
            NetworkImage(
                url = composableModel.pictureUrl,
                modifier = Modifier
                    .requiredHeight(height = 220.dp)
                    .fillMaxWidth(),
            )
        }
        Text(
            text = composableModel.title,
            color = Color.Black,
            fontSize = 25.sp,
            modifier = Modifier.align(Alignment.Start).padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 15.dp)
        )
    }
    val pxValue = with(LocalDensity.current) { 1.dp.toPx() }

    Spacer(
        Modifier
            .background(Color.Gray)
            .fillMaxWidth()
            .height((1/pxValue).dp)
    )
}