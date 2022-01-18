package com.xiangxue.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kuki.base.compose.composablemanager.ComposableServiceManager
import com.xiangxue.news.homefragment.home.HomeComposable

/**
author ：yeton
date : 2022/1/18 10:54
package：com.xiangxue.compose
description :
 */
class MainActivity : ComponentActivity() {

    @ExperimentalMaterialApi
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ComposableServiceManager.collectServices()
        setContent {
            MainScreen()
        }
    }

    @ExperimentalMaterialApi
    @ExperimentalPagerApi
    @Composable
    private fun MainScreen() {
        val navController = rememberNavController()
        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomNavigationBar(navController) }
        ) {
            Navigation(navController)
        }
    }

    @ExperimentalPagerApi
    @Composable
    private fun Navigation(navController: NavHostController) {
        NavHost(navController, startDestination = NavigationItem.Home.route) {
            composable(NavigationItem.Home.route) {
                HomeComposable()
            }
            composable(NavigationItem.Movies.route) {
                MoviesScreen()
            }
            composable(NavigationItem.Books.route) {
                BooksScreen()
            }
            composable(NavigationItem.Profile.route) {
                ProfileScreen()
            }

        }

    }


    @ExperimentalMaterialApi
    @Composable
    private fun BottomNavigationBar(navController: NavHostController) {
        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Movies,
            NavigationItem.Books,
            NavigationItem.Profile
        )

        BottomNavigation(
            backgroundColor = colorResource(R.color.colorPrimary),
            contentColor = Color.White
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        if (item.messageCount != 0) {
                            BadgeBox(
                                backgroundColor = Color.White,
                                contentColor = Color.Red,
                                badgeContent = { Text("8") }

                            ) {
                                Icon(
                                    ImageVector.Companion.vectorResource(id = item.icon),
                                    contentDescription = item.title
                                )
                            }

                        } else {
                            Icon(painterResource(id = item.icon), contentDescription = item.title)
                        }
                    },
                    label = { Text(text = item.title) },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }

                )

            }
        }


    }

    @Composable
    private fun TopBar() {
        TopAppBar(
            title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
            backgroundColor = colorResource(R.color.colorPrimary),
            contentColor = Color.White
        )

    }


}