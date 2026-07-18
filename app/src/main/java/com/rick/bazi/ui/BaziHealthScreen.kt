package com.rick.bazi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rick.bazi.data.*
import com.rick.bazi.util.*
import com.rick.bazi.util.BaziFormatter.getTianGanWuxing
import java.time.LocalDate

// 配色常量（与姻缘界面一致）
private val GradientStart = Color(0xFF667eea)
private val GradientEnd = Color(0xFF764ba2)
private val CardBg = Color(0xFFFFFFFF)
private val AccentGold = Color(0xFFFFD700)
private val TextPrimary = Color(0xFF2C3E50)
private val TextSecondary = Color(0xFF95A5A6)
private val GoodColor = Color(0xFF27AE60)
private val WarningColor = Color(0xFFE67E22)
private val DangerColor = Color(0xFFE74C3C)
private val InfoColor = Color(0xFF3498DB)

// 五行对应器官
private val WUXING_ORGANS = mapOf(
    WuXing.WUXING_JIN to listOf("肺", "大肠", "鼻", "皮肤"),
    WuXing.WUXING_MU to listOf("肝", "胆", "眼睛", "筋"),
    WuXing.WUXING_SHUI to listOf("肾", "膀胱", "耳", "骨"),
    WuXing.WUXING_HUO to listOf("心", "小肠", "舌", "血脉"),
    WuXing.WUXING_TU to listOf("脾", "胃", "口", "肌肉")
)

// 五行中文名
private val WUXING_NAMES = mapOf(
    WuXing.WUXING_JIN to "金",
    WuXing.WUXING_MU to "木",
    WuXing.WUXING_SHUI to "水",
    WuXing.WUXING_HUO to "火",
    WuXing.WUXING_TU to "土"
)

// 地支六冲
private val LIU_CHONG = mapOf(
    DiZhi.DIZHI_ZI to DiZhi.DIZHI_WU,
    DiZhi.DIZHI_WU to DiZhi.DIZHI_ZI,
    DiZhi.DIZHI_CHOU to DiZhi.DIZHI_WEI,
    DiZhi.DIZHI_WEI to DiZhi.DIZHI_CHOU,
    DiZhi.DIZHI_YIN to DiZhi.DIZHI_SHEN,
    DiZhi.DIZHI_SHEN to DiZhi.DIZHI_YIN,
    DiZhi.DIZHI_MOU to DiZhi.DIZHI_YOU,
    DiZhi.DIZHI_YOU to DiZhi.DIZHI_MOU,
    DiZhi.DIZHI_CHEN to DiZhi.DIZHI_XU,
    DiZhi.DIZHI_XU to DiZhi.DIZHI_CHEN,
    DiZhi.DIZHI_SI to DiZhi.DIZHI_HAI,
    DiZhi.DIZHI_HAI to DiZhi.DIZHI_SI
)

// 地支三刑
private val SAN_XING = listOf(
    setOf(DiZhi.DIZHI_YIN, DiZhi.DIZHI_SI, DiZhi.DIZHI_SHEN),
    setOf(DiZhi.DIZHI_CHOU, DiZhi.DIZHI_XU, DiZhi.DIZHI_WEI),
    setOf(DiZhi.DIZHI_ZI, DiZhi.DIZHI_MOU)
)

// 健康趋势等级
enum class HealthTrendLevel(val label: String, val color: Color) {
    EXCELLENT("优良", GoodColor),
    GOOD("良好", InfoColor),
    FAIR("一般", Color(0xFFF39C12)),
    WARNING("注意", WarningColor),
    DANGER("警示", DangerColor)
}

// 大运健康分析数据
data class DaYunHealthInfo(
    val range: String,
    val ganZhi: String,
    val trend: HealthTrendLevel,
    val warning: String,
    val details: String
)

// 流年健康分析数据
data class FlowYearHealthInfo(
    val year: Int,
    val ganZhi: String,
    val impact: HealthTrendLevel,
    val warning: String,
    val criticalMonths: List<Int>
)

// 器官健康状态
data class OrganHealth(
    val name: String,
    val wuxing: WuXing,
    val status: HealthTrendLevel,
    val description: String
)

/**
 * 八字健康分析器
 */
object BaziHealthAnalyzer {

    /**
     * 分析整体健康
     */
    fun analyze(data: BaziData): List<OrganHealth> {
        val organs = mutableListOf<OrganHealth>()
        val weights = mapOf(
            WuXing.WUXING_JIN to data.jinWeight,
            WuXing.WUXING_MU to data.muWeight,
            WuXing.WUXING_SHUI to data.shuiWeight,
            WuXing.WUXING_HUO to data.huoWeight,
            WuXing.WUXING_TU to data.tuWeight
        )

        val avgWeight = weights.values.average().toFloat()

        weights.forEach { (key, value) ->
            val wx = key as WuXing
            val weight = value.toFloat()

//            val ratio = if (avgWeight > 0f) weight / avgWeight else 1f
//
//            val status = when {
//                ratio > 1.5f -> HealthTrendLevel.WARNING
//                ratio < 0.5f -> HealthTrendLevel.WARNING
//                ratio > 1.2f || ratio < 0.7f -> HealthTrendLevel.FAIR
//                else -> HealthTrendLevel.GOOD
//            }

//            (wx, weight) ->
//            val wx = key
//            val weight = value.toFloat()

            val ratio = if (avgWeight > 0) weight / avgWeight else 1f
            val status = when {
                ratio > 1.5f -> {
                    HealthTrendLevel.WARNING
                }
                ratio < 0.5f -> HealthTrendLevel.WARNING
                ratio > 1.2f || ratio < 0.7f -> HealthTrendLevel.FAIR
                else -> HealthTrendLevel.GOOD
            }

            val desc = when {
                ratio > 1.5f -> "${WUXING_NAMES[wx]}过旺，对应器官负担较重"
                ratio < 0.5f -> "${WUXING_NAMES[wx]}不足，对应器官功能偏弱"
                else -> "${WUXING_NAMES[wx]}平衡，对应器官状态良好"
            }

            WUXING_ORGANS[wx]?.forEach { organName ->
                organs.add(OrganHealth(organName, wx, status, desc))
            }
        }

        return organs
    }

    /**
     * 分析大运健康趋势
     */
    fun analyzeDaYunHealth(data: BaziData): List<DaYunHealthInfo> {
        val list = mutableListOf<DaYunHealthInfo>()
        val daYunList = DaYunScoringSystem.generateDaYunList(data)
        val dayMasterWx = getTianGanWuxing(data.dayTiangan)

        daYunList.forEachIndexed { index, dy ->
            val tg = parseTianGan(dy.ganZhi)
            val dz = parseDiZhi(dy.ganZhi)
            val tgWx = getTianGanWuxing(tg)
            val dzWx = getDiZhiWuXing(dz)

            // 判断大运对日主的影响
            val isSheng = isWuXingSheng(tgWx, dayMasterWx) || isWuXingSheng(dzWx, dayMasterWx)
            val isKe = isWuXingKe(tgWx, dayMasterWx) || isWuXingKe(dzWx, dayMasterWx)

            val trend = when {
                isSheng && isKe -> HealthTrendLevel.FAIR
                isSheng -> HealthTrendLevel.GOOD
                isKe -> {
                    // 检查是否有冲刑
                    val hasChong = hasChongWithBaZi(data, dz)
                    val hasXing = hasXingWithBaZi(data, dz)
                    if (hasChong || hasXing) HealthTrendLevel.DANGER
                    else HealthTrendLevel.WARNING
                }
                else -> HealthTrendLevel.FAIR
            }

            val warning = buildString {
                if (isKe) {
                    append("克日主，")
                    append("注意${WUXING_ORGANS[tgWx]?.joinToString("、") ?: ""}保养；")
                }
                if (hasChongWithBaZi(data, dz)) {
                    append("地支相冲，易有突发健康问题；")
                }
                if (hasXingWithBaZi(data, dz)) {
                    append("地支相刑，注意慢性疾病；")
                }
                if (isEmpty()) append("健康平稳")
            }

            list.add(
                DaYunHealthInfo(
                    range = dy.yearRange,
                    ganZhi = dy.ganZhi,
                    trend = trend,
                    warning = warning,
                    details = "大运五行：${WUXING_NAMES[tgWx]}${WUXING_NAMES[dzWx]}"
                )
            )
        }

        return list
    }

    /**
     * 分析流年健康
     */
    fun analyzeFlowYearHealth(data: BaziData): List<FlowYearHealthInfo> {
        val list = mutableListOf<FlowYearHealthInfo>()
        val now = LocalDate.now().year
        val dayMasterWx = getTianGanWuxing(data.dayTiangan)

        for (y in now..now + 5) {
            val gz = yearToGanZhi(y)
            val tg = parseTianGan(gz)
            val dz = parseDiZhi(gz)
            val tgWx = getTianGanWuxing(tg)

            val isKe = isWuXingKe(tgWx, dayMasterWx)
            val hasChong = hasChongWithBaZi(data, dz)
            val hasXing = hasXingWithBaZi(data, dz)

            val impact = when {
                isKe && (hasChong || hasXing) -> HealthTrendLevel.DANGER
                isKe || hasChong -> HealthTrendLevel.WARNING
                hasXing -> HealthTrendLevel.FAIR
                else -> HealthTrendLevel.GOOD
            }

            val criticalMonths = mutableListOf<Int>()
            if (hasChong) criticalMonths.addAll(listOf(3, 6, 9, 12))
            if (hasXing) criticalMonths.addAll(listOf(1, 5, 10))

            val warning = buildString {
                if (isKe) append("流年克日主，注意健康；")
                if (hasChong) append("冲夫妻宫/月柱，易有波动；")
                if (hasXing) append("刑动，注意慢性病；")
                if (isEmpty()) append("健康平稳")
            }

            list.add(
                FlowYearHealthInfo(
                    year = y,
                    ganZhi = gz,
                    impact = impact,
                    warning = warning,
                    criticalMonths = criticalMonths.distinct()
                )
            )
        }

        return list
    }

    // ===== 辅助函数 =====

    private fun parseTianGan(ganZhi: String): TianGan {
        return when (ganZhi.first()) {
            '甲' -> TianGan.TIANGAN_JIA
            '乙' -> TianGan.TIANGAN_YI
            '丙' -> TianGan.TIANGAN_BING
            '丁' -> TianGan.TIANGAN_DING
            '戊' -> TianGan.TIANGAN_WU
            '己' -> TianGan.TIANGAN_JI
            '庚' -> TianGan.TIANGAN_GENG
            '辛' -> TianGan.TIANGAN_XIN
            '壬' -> TianGan.TIANGAN_REN
            '癸' -> TianGan.TIANGAN_GUI
            else -> TianGan.TIANGAN_JIA
        }
    }

    private fun parseDiZhi(ganZhi: String): DiZhi {
        val c = ganZhi.getOrNull(1) ?: return DiZhi.DIZHI_ZI
        return when (c) {
            '子' -> DiZhi.DIZHI_ZI
            '丑' -> DiZhi.DIZHI_CHOU
            '寅' -> DiZhi.DIZHI_YIN
            '卯' -> DiZhi.DIZHI_MOU
            '辰' -> DiZhi.DIZHI_CHEN
            '巳' -> DiZhi.DIZHI_SI
            '午' -> DiZhi.DIZHI_WU
            '未' -> DiZhi.DIZHI_WEI
            '申' -> DiZhi.DIZHI_SHEN
            '酉' -> DiZhi.DIZHI_YOU
            '戌' -> DiZhi.DIZHI_XU
            '亥' -> DiZhi.DIZHI_HAI
            else -> DiZhi.DIZHI_ZI
        }
    }

    private fun getDiZhiWuXing(dz: DiZhi): WuXing = when (dz) {
        DiZhi.DIZHI_ZI -> WuXing.WUXING_SHUI
        DiZhi.DIZHI_CHOU -> WuXing.WUXING_TU
        DiZhi.DIZHI_YIN -> WuXing.WUXING_MU
        DiZhi.DIZHI_MOU -> WuXing.WUXING_MU
        DiZhi.DIZHI_CHEN -> WuXing.WUXING_TU
        DiZhi.DIZHI_SI -> WuXing.WUXING_HUO
        DiZhi.DIZHI_WU -> WuXing.WUXING_HUO
        DiZhi.DIZHI_WEI -> WuXing.WUXING_TU
        DiZhi.DIZHI_SHEN -> WuXing.WUXING_JIN
        DiZhi.DIZHI_YOU -> WuXing.WUXING_JIN
        DiZhi.DIZHI_XU -> WuXing.WUXING_TU
        DiZhi.DIZHI_HAI -> WuXing.WUXING_SHUI
    }

    private fun isWuXingSheng(a: WuXing, b: WuXing): Boolean = when (a) {
        WuXing.WUXING_MU -> b == WuXing.WUXING_HUO
        WuXing.WUXING_HUO -> b == WuXing.WUXING_TU
        WuXing.WUXING_TU -> b == WuXing.WUXING_JIN
        WuXing.WUXING_JIN -> b == WuXing.WUXING_SHUI
        WuXing.WUXING_SHUI -> b == WuXing.WUXING_MU
    }

    private fun isWuXingKe(a: WuXing, b: WuXing): Boolean = when (a) {
        WuXing.WUXING_MU -> b == WuXing.WUXING_TU
        WuXing.WUXING_TU -> b == WuXing.WUXING_SHUI
        WuXing.WUXING_SHUI -> b == WuXing.WUXING_HUO
        WuXing.WUXING_HUO -> b == WuXing.WUXING_JIN
        WuXing.WUXING_JIN -> b == WuXing.WUXING_MU
    }

    private fun hasChongWithBaZi(data: BaziData, dz: DiZhi): Boolean {
        val branches = listOf(data.yearDizhi, data.monthDizhi, data.dayDizhi, data.hourDizhi)
        return branches.any { LIU_CHONG[it] == dz || LIU_CHONG[dz] == it }
    }

    private fun hasXingWithBaZi(data: BaziData, dz: DiZhi): Boolean {
        val branches = listOf(data.yearDizhi, data.monthDizhi, data.dayDizhi, data.hourDizhi)
        return SAN_XING.any { xingSet ->
            xingSet.contains(dz) && branches.any { it != dz && xingSet.contains(it) }
        }
    }

    private fun yearToGanZhi(year: Int): String {
        val tgIdx = ((year - 4) % 10 + 10) % 10
        val dzIdx = ((year - 4) % 12 + 12) % 12
        val tg = TianGan.values()[tgIdx]
        val dz = DiZhi.values()[dzIdx]
        return tianGanToChar(tg) + zhiToChar(dz)
    }

    private fun tianGanToChar(tg: TianGan): String = when (tg) {
        TianGan.TIANGAN_JIA -> "甲"
        TianGan.TIANGAN_YI -> "乙"
        TianGan.TIANGAN_BING -> "丙"
        TianGan.TIANGAN_DING -> "丁"
        TianGan.TIANGAN_WU -> "戊"
        TianGan.TIANGAN_JI -> "己"
        TianGan.TIANGAN_GENG -> "庚"
        TianGan.TIANGAN_XIN -> "辛"
        TianGan.TIANGAN_REN -> "壬"
        TianGan.TIANGAN_GUI -> "癸"
    }

    private fun zhiToChar(dz: DiZhi): String = when (dz) {
        DiZhi.DIZHI_ZI -> "子"
        DiZhi.DIZHI_CHOU -> "丑"
        DiZhi.DIZHI_YIN -> "寅"
        DiZhi.DIZHI_MOU -> "卯"
        DiZhi.DIZHI_CHEN -> "辰"
        DiZhi.DIZHI_SI -> "巳"
        DiZhi.DIZHI_WU -> "午"
        DiZhi.DIZHI_WEI -> "未"
        DiZhi.DIZHI_SHEN -> "申"
        DiZhi.DIZHI_YOU -> "酉"
        DiZhi.DIZHI_XU -> "戌"
        DiZhi.DIZHI_HAI -> "亥"
    }
}

/**
 * 八字健康分析界面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaziHealthScreen(
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    baziModel: BaziViewModel,
    baziInfo: BaziInfo,
    modifier: Modifier = Modifier
) {
    val data = baziInfo.baziData

    // 分析数据
    val organHealth by remember(data) { mutableStateOf(BaziHealthAnalyzer.analyze(data)) }
    val daYunHealth by remember(data) { mutableStateOf(BaziHealthAnalyzer.analyzeDaYunHealth(data)) }
    val flowYearHealth by remember(data) { mutableStateOf(BaziHealthAnalyzer.analyzeFlowYearHealth(data)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("八字健康分析", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onCancelButtonClicked) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GradientStart,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. 整体健康概览
            HealthOverviewCard(data, organHealth)

            // 2. 五行与器官健康
            OrganHealthCard(organHealth)

            // 3. 大运健康趋势
            DaYunHealthCard(daYunHealth)

            // 4. 流年健康预警
            FlowYearHealthCard(flowYearHealth)

            // 5. 健康建议
            HealthAdviceCard(data, organHealth, daYunHealth, flowYearHealth)

            // 底部按钮
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onCancelButtonClicked,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("返回")
                }

                Button(
                    onClick = {
                        val report = buildHealthReport(data, organHealth, daYunHealth, flowYearHealth)
                        onSendButtonClicked("八字健康分析", report)
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GradientEnd)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("分享报告")
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String, icon: ImageVector, tint: Color = GradientEnd) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(8.dp))
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
    }
}

@Composable
private fun InfoItem(label: String, value: String, valueColor: Color = TextPrimary) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 14.sp, color = TextSecondary, modifier = Modifier.weight(0.4f))
        Text(value, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = valueColor,
            modifier = Modifier.weight(0.6f), textAlign = TextAlign.End)
    }
}

@Composable
private fun HealthOverviewCard(data: BaziData, organs: List<OrganHealth>) {
    val dayMasterWx = getTianGanWuxing(data.dayTiangan)
    val warningCount = organs.count { it.status == HealthTrendLevel.WARNING || it.status == HealthTrendLevel.DANGER }
    val goodCount = organs.count { it.status == HealthTrendLevel.GOOD || it.status == HealthTrendLevel.EXCELLENT }

    val overallLevel = when {
        warningCount > 3 -> HealthTrendLevel.DANGER
        warningCount > 1 -> HealthTrendLevel.WARNING
        goodCount > organs.size / 2 -> HealthTrendLevel.GOOD
        else -> HealthTrendLevel.FAIR
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg)
    ) {
        Column(Modifier.padding(16.dp)) {
            SectionTitle("整体健康概览", Icons.Default.Favorite, overallLevel.color)
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
            Spacer(Modifier.height(8.dp))

            InfoItem("日主五行", WUXING_NAMES[dayMasterWx] ?: "")
            InfoItem("健康状况", overallLevel.label, overallLevel.color)
            InfoItem("需关注器官", "${warningCount}个", if (warningCount > 0) WarningColor else GoodColor)
            InfoItem("状态良好器官", "${goodCount}个", GoodColor)

            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { (organs.size - warningCount).toFloat() / organs.size },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = overallLevel.color,
                trackColor = Color.LightGray.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
private fun OrganHealthCard(organs: List<OrganHealth>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg)
    ) {
        Column(Modifier.padding(16.dp)) {
            SectionTitle("五行与器官健康", Icons.Default.Biotech, InfoColor)
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
            Spacer(Modifier.height(8.dp))

            // 按五行分组
            WuXing.values().forEach { wx ->
                val wxOrgans = organs.filter { it.wuxing == wx }
                if (wxOrgans.isNotEmpty()) {
                    val wxStatus = wxOrgans.maxByOrNull { it.status.ordinal }?.status ?: HealthTrendLevel.GOOD

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = wxStatus.color.copy(alpha = 0.08f)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "${WUXING_NAMES[wx]}行",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    wxStatus.label,
                                    color = wxStatus.color,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(
                                wxOrgans.joinToString("、") { it.name },
                                fontSize = 13.sp,
                                color = TextSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DaYunHealthCard(daYunList: List<DaYunHealthInfo>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg)
    ) {
        Column(Modifier.padding(16.dp)) {
            SectionTitle("大运健康趋势", Icons.Default.TrendingUp, GradientEnd)
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
            Spacer(Modifier.height(8.dp))

            daYunList.forEach { dy ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = when (dy.trend) {
                        HealthTrendLevel.DANGER -> DangerColor.copy(alpha = 0.08f)
                        HealthTrendLevel.WARNING -> WarningColor.copy(alpha = 0.08f)
                        else -> Color.Transparent
                    },
                    border = if (dy.trend == HealthTrendLevel.DANGER)
                        androidx.compose.foundation.BorderStroke(1.dp, DangerColor)
                    else null
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(dy.range, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(dy.trend.label, color = dy.trend.color, fontSize = 12.sp,
                                fontWeight = FontWeight.Medium)
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(dy.details, fontSize = 13.sp, color = TextSecondary)
                        Text(dy.warning, fontSize = 13.sp, color = dy.trend.color, lineHeight = 18.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun FlowYearHealthCard(flowYearList: List<FlowYearHealthInfo>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg)
    ) {
        Column(Modifier.padding(16.dp)) {
            SectionTitle("流年健康预警", Icons.Default.NotificationsActive, WarningColor)
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
            Spacer(Modifier.height(8.dp))

            flowYearList.forEach { fy ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = when (fy.impact) {
                        HealthTrendLevel.DANGER -> DangerColor.copy(alpha = 0.08f)
                        HealthTrendLevel.WARNING -> WarningColor.copy(alpha = 0.08f)
                        else -> Color.Transparent
                    },
                    border = if (fy.impact == HealthTrendLevel.DANGER)
                        androidx.compose.foundation.BorderStroke(1.dp, DangerColor)
                    else null
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("${fy.year}年 ${fy.ganZhi}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(fy.impact.label, color = fy.impact.color, fontSize = 12.sp,
                                fontWeight = FontWeight.Medium)
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(fy.warning, fontSize = 13.sp, color = fy.impact.color, lineHeight = 18.sp)
                        if (fy.criticalMonths.isNotEmpty()) {
                            Text(
                                "重点关注月份：${fy.criticalMonths.joinToString("、") { "${it}月" }}",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HealthAdviceCard(
    data: BaziData,
    organs: List<OrganHealth>,
    daYunList: List<DaYunHealthInfo>,
    flowYearList: List<FlowYearHealthInfo>
) {
    val adviceList = remember {
        mutableListOf<String>().apply {
            // 基于器官健康的建议
            organs.filter { it.status == HealthTrendLevel.WARNING || it.status == HealthTrendLevel.DANGER }
                .groupBy { it.wuxing }
                .forEach { (wx, orgs) ->
                    add("注意${WUXING_NAMES[wx]}行对应器官（${orgs.joinToString("、") { it.name }}）的保养")
                }

            // 基于大运的建议
            daYunList.filter { it.trend == HealthTrendLevel.DANGER || it.trend == HealthTrendLevel.WARNING }
                .forEach { dy ->
                    add("${dy.range}期间需特别注意健康，${dy.warning}")
                }

            // 基于流年的建议
            flowYearList.filter { it.impact == HealthTrendLevel.DANGER || it.impact == HealthTrendLevel.WARNING }
                .forEach { fy ->
                    add("${fy.year}年健康需警惕，${fy.warning}")
                }

            // 通用建议
            add("保持规律作息，均衡饮食，适度运动")
            add("建议每年进行一次全面体检")

            // ❗ 关键修复：判断当前 list 是否为空
            if (isEmpty()) {
                add("当前健康状况良好，继续保持良好的生活习惯")
            }
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg)
    ) {
        Column(Modifier.padding(16.dp)) {
            SectionTitle("健康建议", Icons.Default.Lightbulb, AccentGold)
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
            Spacer(Modifier.height(8.dp))

            adviceList.forEach { advice ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = GoodColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(advice, fontSize = 14.sp, color = TextPrimary, lineHeight = 20.sp)
                }
            }
        }
    }
}

private fun buildHealthReport(
    data: BaziData,
    organs: List<OrganHealth>,
    daYunList: List<DaYunHealthInfo>,
    flowYearList: List<FlowYearHealthInfo>
): String {
    val sb = StringBuilder()
    sb.append("🏥 八字健康分析报告\n\n")
    sb.append("出生时间：${data.birthDateYear}年${data.birthDateMonth}月${data.birthDateDay}日 ${data.birthHour}时\n")
    sb.append("性别：${if (data.gender == "Male") "男" else "女"}\n\n")

    sb.append("【五行与器官健康】\n")
    organs.groupBy { it.wuxing }.forEach { (wx, orgs) ->
        val status = orgs.maxByOrNull { it.status.ordinal }?.status ?: HealthTrendLevel.GOOD
        sb.append("${WUXING_NAMES[wx]}行（${status.label}）：${orgs.joinToString("、") { it.name }}\n")
    }

    sb.append("\n【大运健康趋势】\n")
    daYunList.forEach { sb.append("${it.range} ${it.ganZhi}：${it.trend.label} - ${it.warning}\n") }

    sb.append("\n【流年健康预警】\n")
    flowYearList.forEach { sb.append("${it.year}年 ${it.ganZhi}：${it.impact.label} - ${it.warning}\n") }

    return sb.toString()
}

@Preview
@Composable
fun BaziHealthScreenPreview() {
    BaziHealthScreen(
        onSendButtonClicked = { _, _ -> },
        onCancelButtonClicked = {},
        baziModel = viewModel(),
        baziInfo = BaziInfo(name = "")
    )
}