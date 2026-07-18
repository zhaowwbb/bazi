package com.rick.bazi.ui


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.BaziUIDataGenerator
import com.rick.bazi.ui.tabs.DaYunTab
import com.rick.bazi.ui.tabs.LiuNianTab
import com.rick.bazi.ui.tabs.LiuYueTab
import com.rick.bazi.ui.theme.BaziTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaziSummaryScreen(
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    baziInfo: BaziInfo,
    modifier: Modifier = Modifier
) {
    val baZiData = remember { BaziUIDataGenerator().generateRealBaZiData(baziInfo.baziData) }

//    println("[Rick]baZiData=$baZiData")

    var selectedTab by remember { mutableStateOf(0) }
    var selectedDaYunIndex by remember { mutableStateOf(baZiData.currentDaYunIndex) }
    var selectedLiuNianIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("运势分析") },
                navigationIcon = {
                    IconButton(onClick = onCancelButtonClicked) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF667eea),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // 标签页
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("大运") })
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("流年") })
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text("流月") })
            }

            // 内容区域
            when (selectedTab) {
                0 -> DaYunTab(
                    baZiData = baZiData,
                    selectedIndex = selectedDaYunIndex,
                    onSelectDaYun = { selectedDaYunIndex = it })

                1 -> LiuNianTab(
                    baZiData = baZiData,
                    data = baziInfo.baziData,
                    selectedDaYunIndex = selectedDaYunIndex,
                    selectedLiuNianIndex = selectedLiuNianIndex,
                    onSelectLiuNian = { selectedLiuNianIndex = it })

                2 -> LiuYueTab(
                    baZiData = baZiData,
                    data = baziInfo.baziData,
                    selectedDaYunIndex = selectedDaYunIndex,
                    selectedLiuNianIndex = selectedLiuNianIndex
                )
            }
        }
    }
}

@Preview(
    name = "Phone Layout",
    showBackground = true,
    device = "spec:width=411dp,height=891dp" // 🌟 FORCE A REALISTIC DEVICE CEILING WINDOW
)
@Composable
fun BaziSummaryScreenPreview() {
    BaziTheme {
        BaziSummaryScreen(
            onSendButtonClicked = { subject: String, summary: String -> },
            onCancelButtonClicked = {},
            baziInfo = BaziInfo(name = ""),
//            modifier = Modifier.fillMaxHeight()
            modifier = Modifier.fillMaxSize()
        )
    }
}

//@Composable
//fun BaZiSummaryCard(baZiData: BaZiData) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 8.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surfaceVariant
//        )
//    ) {
//        Row(
//            modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
//                Text("年柱", style = MaterialTheme.typography.labelSmall)
//                Text(baZiData.yearGanZhi, style = MaterialTheme.typography.titleMedium)
//            }
//            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
//                Text("月柱", style = MaterialTheme.typography.labelSmall)
//                Text(baZiData.monthGanZhi, style = MaterialTheme.typography.titleMedium)
//            }
//            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
//                Text("日柱", style = MaterialTheme.typography.labelSmall)
//                Text(baZiData.dayGanZhi, style = MaterialTheme.typography.titleMedium)
//            }
//            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
//                Text("时柱", style = MaterialTheme.typography.labelSmall)
//                Text(baZiData.hourGanZhi, style = MaterialTheme.typography.titleMedium)
//            }
//            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
//                Text("日主", style = MaterialTheme.typography.labelSmall)
//                Text(baZiData.dayMaster, style = MaterialTheme.typography.titleMedium)
//            }
//        }
//    }
//}