package com.rick.bazi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.BaziUIDataGenerator
import com.rick.bazi.data.TianGan
import com.rick.bazi.model.HiddenGan
import com.rick.bazi.model.Pillar
import com.rick.bazi.util.BaziMeasureUtil
import com.rick.bazi.util.ShiShenUtil
import com.rick.bazi.util.TianGanUtil

// 鲜艳配色常量
private val GradientStart = Color(0xFF667eea)
private val GradientEnd = Color(0xFF764ba2)
private val CardBgLight = Color(0xFFFFF8E1)
private val CardBgDark = Color(0xFF2D3436)
private val AccentGold = Color(0xFFFFD700)
private val AccentRed = Color(0xFFE74C3C)
private val AccentGreen = Color(0xFF27AE60)
private val AccentBlue = Color(0xFF3498DB)
private val AccentPurple = Color(0xFF9B59B6)
private val TextWhite = Color(0xFFFFFFFF)
private val TextDark = Color(0xFF2C3E50)

@Composable
fun replaceHiddenGans(
    pillar: Pillar,
    ganList: Array<TianGan>,
    dayTianGan: TianGan
//    dayGan: String
) {
    val newList = mutableListOf<HiddenGan>()

    for (gan in ganList) {
        val ganText = TianGanUtil().getTianGanText(gan)
        val shiShen = ShiShenUtil().getShiShenText(gan, dayTianGan)   // your logic
        newList.add(HiddenGan(ganText, shiShen))
    }

    pillar.hiddenGans = newList
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaZiPanScreen(
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    baziModel: BaziViewModel,
    baziInfo: BaziInfo,
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val preProcessBazi = remember { mutableStateOf(false) }
    if (preProcessBazi.value) {
        BaziMeasureUtil().analyzeBaziAndSaveStat(baziInfo, baziModel)
    }

    val data = BaziUIDataGenerator().generateBaziPanData(baziInfo.baziData)

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("八字排盘") },
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
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(GradientStart, GradientEnd)
                    )
                )
                .verticalScroll(scrollState)
                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ── 顶部渐变区域 ──
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(GradientStart, GradientEnd)
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                // 姓名和性别行
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = AccentGold,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
//                        text = "${data.name}",
                            text = " ",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = AccentGold.copy(alpha = 0.3f)
                        ) {
                            Text(
                                text = data.gender,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                color = AccentGold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }

                    // 日主信息 - 右侧突出显示
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = AccentGold.copy(alpha = 0.2f),
                        shadowElevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "日主",
                                fontSize = 12.sp,
                                color = AccentGold.copy(alpha = 0.8f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${data.dayMaster}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = AccentGreen
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 出生时间
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        tint = TextWhite.copy(alpha = 0.7f),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = data.birthTime,
                        fontSize = 13.sp,
                        color = TextWhite.copy(alpha = 0.7f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 大运和流年 - 两行对齐
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = TextWhite.copy(alpha = 0.15f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.TrendingUp,
                                contentDescription = null,
                                tint = AccentGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "当前大运",
                                fontSize = 13.sp,
                                color = TextWhite.copy(alpha = 0.8f),
                                modifier = Modifier.width(80.dp)
                            )
                            Text(
                                text = data.currentDaYun,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextWhite
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = AccentGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "当前流年",
                                fontSize = 13.sp,
                                color = TextWhite.copy(alpha = 0.8f),
                                modifier = Modifier.width(80.dp)
                            )
                            Text(
                                text = data.currentLiuNian,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextWhite
                            )
                        }
                    }
                }
            }

            // ── 四柱排盘卡片 ──
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBgLight
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 表头
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        data.pillars.forEach { pillar ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = when (pillar.label) {
                                    "年柱" -> AccentBlue.copy(alpha = 0.1f)
                                    "月柱" -> AccentGreen.copy(alpha = 0.1f)
                                    "日柱" -> AccentPurple.copy(alpha = 0.15f)
                                    "时柱" -> AccentRed.copy(alpha = 0.1f)
                                    else -> Color.Transparent
                                }
                            ) {
                                Text(
                                    text = pillar.label,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = when (pillar.label) {
                                        "年柱" -> AccentBlue
                                        "月柱" -> AccentGreen
                                        "日柱" -> AccentPurple
                                        "时柱" -> AccentRed
                                        else -> TextDark
                                    },
                                    modifier = Modifier
                                        .width(70.dp)
                                        .padding(vertical = 4.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    // 天干 + 十神
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        data.pillars.forEach { pillar ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.width(70.dp)
                            ) {
                                Text(
                                    text = pillar.ganShiShen,
                                    fontSize = 11.sp,
                                    color = TextDark.copy(alpha = 0.6f)
                                )
                                Text(
                                    text = pillar.tianGan,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = when (pillar.tianGan) {
                                        "甲", "乙" -> AccentGreen
                                        "丙", "丁" -> AccentRed
                                        "戊", "己" -> Color(0xFF8B4513)
                                        "庚", "辛" -> Color(0xFF708090)
                                        "壬", "癸" -> AccentBlue
                                        else -> TextDark
                                    }
                                )
                            }
                        }
                    }

                    HorizontalDivider(
                        thickness = 2.dp,
                        color = AccentGold.copy(alpha = 0.3f)
                    )

                    // 地支
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        data.pillars.forEach { pillar ->
                            Box(
                                modifier = Modifier
                                    .width(70.dp)
                                    .background(
                                        if (pillar.label == "日柱")
                                            AccentPurple.copy(alpha = 0.2f)
                                        else Color.Transparent,
                                        RoundedCornerShape(8.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = pillar.diZhi,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                            }
                        }
                    }

                    // 地支藏干
                    data.pillars.maxOf { it.hiddenGans.size }.let { maxHidden ->
                        (0 until maxHidden).forEach { rowIdx ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                data.pillars.forEach { pillar ->
                                    val hiddenGan = pillar.hiddenGans.getOrNull(rowIdx)
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.width(70.dp)
                                    ) {
                                        if (hiddenGan != null) {
                                            Text(
                                                text = hiddenGan.shiShen,
                                                fontSize = 9.sp,
                                                color = TextDark.copy(alpha = 0.5f)
                                            )
                                            Text(
                                                text = hiddenGan.gan,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = when (hiddenGan.gan) {
                                                    "甲", "乙" -> AccentGreen
                                                    "丙", "丁" -> AccentRed
                                                    "戊", "己" -> Color(0xFF8B4513)
                                                    "庚", "辛" -> Color(0xFF708090)
                                                    "壬", "癸" -> AccentBlue
                                                    else -> TextDark
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ── 四个分析按钮 ──
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "分析工具",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AnalysisButton(
                        label = "命盘分析",
                        icon = Icons.Default.AccountTree,
                        gradientColors = listOf(Color(0xFF667eea), Color(0xFF764ba2)),
                        modifier = Modifier.weight(1f),
                        onClick = {
                            navController.navigate(com.rick.bazi.BaziScreen.Analysis.name)
                        }
                    )
                    AnalysisButton(
                        label = "健康分析",
                        icon = Icons.Default.Check,
                        gradientColors = listOf(Color(0xFFE74C3C), Color(0xFFC0392B)),
                        modifier = Modifier.weight(1f),
                        onClick = {
                            navController.navigate(com.rick.bazi.BaziScreen.Health.name)
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AnalysisButton(
                        label = "运势分析",
                        icon = Icons.Default.TrendingUp,
                        gradientColors = listOf(Color(0xFF27AE60), Color(0xFF1E8449)),
                        modifier = Modifier.weight(1f),
                        onClick = {
                            navController.navigate(com.rick.bazi.BaziScreen.Summary.name)
                        }
                    )
                    AnalysisButton(
                        label = "姻缘分析",
                        icon = Icons.Default.Favorite,
                        gradientColors = listOf(Color(0xFFFFC0CB), Color(0xFFFF1493)),
                        modifier = Modifier.weight(1f),
                        onClick = {
                            navController.navigate(com.rick.bazi.BaziScreen.Marriage.name)
                        }
                    )
                }
            }

            // 底部留白
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun AnalysisButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    gradientColors: List<Color>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = TextWhite,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = label,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            }
        }
    }
}