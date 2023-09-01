package com.jay.sitemanager.navigationInfrastructure

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun TwoTabView(
    titles: List<String>,
    view1: @Composable () -> Unit,
    view2: @Composable () -> Unit,
) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabTitles = titles
    Column {
        TabRow(selectedTabIndex = tabIndex) {
            tabTitles.forEachIndexed { index, title ->
                Tab(selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    text = { Text(text = title) })
            }
        }
        when (tabIndex) {
            0 -> view1()
            1 -> view2()
        }
    }
}