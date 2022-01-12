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

@Composable
fun TitleComposable(composableModel: TitleComposableModel){
    Box(modifier = Modifier) {
        Text(
            text = composableModel.title,
            color = Color.Black,
            fontSize = 25.sp,
            modifier = Modifier.align(Alignment.BottomStart).padding(15.dp)
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