package com.example.jetpacktablayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpacktablayout.ui.theme.JetpackTablayoutTheme
import com.example.jetpacktablayout.ui.theme.TabColorOne
import com.example.jetpacktablayout.view.TabScreenOne
import com.example.jetpacktablayout.view.TabScreenThree
import com.example.jetpacktablayout.view.TabScreenTwo
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @OptIn(ExperimentalPagerApi::class)
        setContent {
            JetpackTablayoutTheme {
                Column {
                    Toolbar()
                    TabScreen()
                }
            }
        }
    }
}

@Composable
fun Toolbar() {
    TopAppBar(
        title = { Text(text = "TabLayout", color = Color.White) },
        backgroundColor = TabColorOne,
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
                tint = Color.White
            )
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabScreen() {
    val pagerState = rememberPagerState(pageCount = 3)
    Column(
        modifier = Modifier.background(Color.White)
    ) {
        Tabs(pagerState)
        TabsContent(pagerState = pagerState)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(pagerState: PagerState) {
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> TabScreenOne(tabName = "This is a Home Layout")
            1 -> TabScreenTwo(tabName = "This is a Market Layout")
            2 -> TabScreenThree(tabName = "This is a Films Layout")
        }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf("Home", "Market", "Films")
    val lists = listOf(
        Icons.Filled.Home,
        Icons.Filled.ShoppingCart,
        Icons.Filled.AccountBox
    )

    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = TabColorOne,
        contentColor = Color.White,
        divider = {
            TabRowDefaults.Divider(
                thickness = 3.dp,
                color = Color.White
            )
        },
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 3.dp,
                color = Color.Red
            )
        }
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(
                        text = list[index],
                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                icon = {
                    Icon(imageVector = lists[index], contentDescription = null)
                }
            )
        }
    }
}
