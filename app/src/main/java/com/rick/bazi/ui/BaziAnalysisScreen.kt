package com.rick.bazi.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rick.bazi.BaziScreen
import com.rick.bazi.data.*
import com.rick.bazi.ui.theme.BaziTheme
import com.rick.bazi.util.*
import com.rick.bazi.util.BaziYongShenAnalyzer
import com.rick.bazi.util.BaziFormatter.convertTianganToChar
import com.rick.bazi.util.BaziFormatter.convertDizhiToChar
import com.rick.bazi.util.BaziFormatter.formatTianganDizhi
import com.rick.bazi.util.BaziFormatter.shiShenToChinese
import com.rick.bazi.util.BaziFormatter.formatShiShenWithTianGan
import androidx.navigation.compose.rememberNavController
import com.rick.bazi.model.StrengthLevel
import com.rick.bazi.util.BaziFormatter.getTianGanWuxing

// 配色常量
private val GradientStart = Color(0xFF667eea)
private val GradientEnd = Color(0xFF764ba2)
private val CardBg = Color(0xFFFFFFFF)
private val AccentGold = Color(0xFFFFD700)
private val TextPrimary = Color(0xFF2C3E50)
private val TextSecondary = Color(0xFF95A5A6)
private val GoodColor = Color(0xFF27AE60)
private val BadColor = Color(0xFFE74C3C)
private val InfoColor = Color(0xFF3498DB)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaziAppAnalysisScreen(
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    baziModel: BaziViewModel,
    baziInfo: BaziInfo,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val baziData = baziInfo.baziData

    // 确保八字已经分析
    LaunchedEffect(baziData) {
        if (baziData.yongShenList.isEmpty()) {
            // 先计算五行权重和根气
//            WuXingWeightCalculator.calculateTotalWuXingWeights(baziData)
//            RootCounter.calculateRootCounts(baziData)

            // 选择核心用神
//            PrimaryYongShenSelector.selectAndUpdate(baziData)

//            BaziYongShenAnalyzer.analyze(baziData)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("命盘分析", fontWeight = FontWeight.Bold) },
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
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. 四柱展示
            FourPillarsCard(baziData)

            // 2. 日主旺衰分析
            DayMasterStrengthCard(baziData)

            // 3. 五行强弱分析
            WuXingStrengthCard(baziData)

            // 4. 喜用神与忌神
            YongShenAnalysisCard(baziData)
            YongShenJiShenCard(baziData)

            // 5. 日主格局
            GeJuCard(baziData)

            // 6. 性格分析
            CharacterAnalysisCard(baziData)

            // 7. 推荐职业
            CareerRecommendationCard(baziData)

            // 8. 六亲缘分
            SixRelationsCard(baziData)

            // 底部操作按钮
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        navController.navigate(BaziScreen.Pan.name)
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("返回")
                }

                Button(
                    onClick = {
                        // 发送分析结果
                        val analysisResult = buildAnalysisText(baziData)
                        onSendButtonClicked("八字命盘分析", analysisResult)
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GradientEnd)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("分享分析")
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String, icon: @Composable () -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        icon()
        Spacer(Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
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
        Text(
            text = label,
            fontSize = 14.sp,
            color = TextSecondary,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = valueColor,
            modifier = Modifier.weight(0.6f),
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun AnalysisCard(
    title: String,
    icon: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            SectionTitle(title = title, icon = icon)
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.LightGray.copy(alpha = 0.3f)
            )
            content()
        }
    }
}

@Composable
private fun FourPillarsCard(data: BaziData) {
    val tianGanUtil = TianGanUtil()
    val diZhiUtil = DiZhiUtil()

    AnalysisCard(
        title = "四柱八字",
        icon = { Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = GradientEnd) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            PillarColumn("年柱", data.yearTiangan, data.yearDizhi, tianGanUtil, diZhiUtil)
            PillarColumn("月柱", data.monthTiangan, data.monthDizhi, tianGanUtil, diZhiUtil)
            PillarColumn("日柱", data.dayTiangan, data.dayDizhi, tianGanUtil, diZhiUtil)
            PillarColumn("时柱", data.hourTiangan, data.hourDizhi, tianGanUtil, diZhiUtil)
        }
    }
}

@Composable
private fun PillarColumn(
    label: String,
    tianGan: TianGan,
    diZhi: DiZhi,
    tianGanUtil: TianGanUtil,
    diZhiUtil: DiZhiUtil
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = TextSecondary
        )
        Spacer(Modifier.height(4.dp))
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = GradientStart.copy(alpha = 0.1f),
            modifier = Modifier.padding(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = tianGanUtil.getTianGanText(tianGan),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = GradientEnd
                )
                Text(
                    text = diZhiUtil.getDiZhiText(diZhi),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = GradientStart
                )
            }
        }
    }
}

@Composable
private fun DayMasterStrengthCard(data: BaziData) {
    val strength = calculateDayMasterStrength(data)

    AnalysisCard(
        title = "日主旺衰",
        icon = { Icon(Icons.Default.Favorite, contentDescription = null, tint = BadColor) }
    ) {
        val strengthText = when (strength.strengthLevel) {
            StrengthLevel.VERY_STRONG -> "极强"
            StrengthLevel.STRONG -> "偏强"
            StrengthLevel.MEDIUM -> "中和"
            StrengthLevel.WEAK -> "偏弱"
            StrengthLevel.VERY_WEAK -> "极弱"
        }

        val strengthColor = when (strength.strengthLevel) {
            StrengthLevel.VERY_STRONG, StrengthLevel.STRONG -> BadColor
            StrengthLevel.MEDIUM -> GoodColor
            StrengthLevel.VERY_WEAK, StrengthLevel.WEAK -> InfoColor
        }

        InfoItem("日主五行", WuXingExt.getWuXingText(strength.wuxing))
        InfoItem("旺衰程度", strengthText, strengthColor)
        InfoItem(
            "得月令", if (strength.isSeasonStrong) "是 ✓" else "否 ✗",
            if (strength.isSeasonStrong) GoodColor else BadColor
        )
        InfoItem("地支根数", "${strength.rootCount}个")
        InfoItem("生助权重", String.format("%.2f", strength.supportWeight))
        InfoItem("旺衰评分", "${strength.score}/20")

        // 进度条显示强度
        Spacer(Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { strength.score.toFloat() / 20f },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = strengthColor,
            trackColor = Color.LightGray.copy(alpha = 0.3f)
        )
    }
}

@Composable
private fun WuXingStrengthCard(data: BaziData) {
    val weights = mapOf(
        "金" to data.jinWeight,
        "木" to data.muWeight,
        "水" to data.shuiWeight,
        "火" to data.huoWeight,
        "土" to data.tuWeight
    )

    AnalysisCard(
        title = "五行强弱",
        icon = { Icon(Icons.Default.Analytics, contentDescription = null, tint = InfoColor) }
    ) {
        val maxWeight = weights.values.maxOrNull() ?: 1f

        weights.forEach { (name, weight) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.width(30.dp)
                )

                LinearProgressIndicator(
                    progress = { (weight / maxWeight).takeIf { !it.isNaN() } ?: 0f },
                    modifier = Modifier
                        .weight(1f)
                        .height(12.dp),
                    color = getWuXingColor(name),
                    trackColor = Color.LightGray.copy(alpha = 0.3f)
                )

                Spacer(Modifier.width(8.dp))
                Text(
                    text = String.format("%.1f", weight),
                    fontSize = 12.sp,
                    color = TextSecondary,
                    modifier = Modifier.width(40.dp),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Composable
private fun getWuXingColor(name: String): Color = when (name) {
    "金" -> Color(0xFFFFD700)
    "木" -> Color(0xFF27AE60)
    "水" -> Color(0xFF3498DB)
    "火" -> Color(0xFFE74C3C)
    "土" -> Color(0xFF8B4513)
    else -> TextSecondary
}

@Composable
private fun YongShenJiShenCard(data: BaziData) {
    AnalysisCard(
        title = "喜用神与忌神",
        icon = { Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = AccentGold) }
    ) {

        
        // 用神
        Text(
            text = "用神",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = GoodColor
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = data.yongShenList.joinToString("、") { formatShiShenWithTianGan(it, data.dayTiangan) },
            fontSize = 14.sp,
            color = TextPrimary
        )

        Spacer(Modifier.height(12.dp))

        // 喜神
        Text(
            text = "喜神",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = InfoColor
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = data.xiyongShenSet.joinToString("、") { formatShiShenWithTianGan(it, data.dayTiangan) },
            fontSize = 14.sp,
            color = TextPrimary
        )

        Spacer(Modifier.height(12.dp))

        // 忌神
        Text(
            text = "忌神",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = BadColor
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = data.jiShenList.joinToString("、") { formatShiShenWithTianGan(it, data.dayTiangan) },
            fontSize = 14.sp,
            color = TextPrimary
        )

        if (data.tiaohouShenList.isNotEmpty()) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = "调候用神",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = AccentGold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = data.tiaohouShenList.joinToString("、") { formatShiShenWithTianGan(it, data.dayTiangan) },
                fontSize = 14.sp,
                color = TextPrimary
            )
        }
        if (data.tongguanShenList.isNotEmpty()) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = "通关用神",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = AccentGold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = data.tongguanShenList.joinToString("、") { formatShiShenWithTianGan(it, data.dayTiangan) },
                fontSize = 14.sp,
                color = TextPrimary
            )
        }
    }
}

@Composable
private fun GeJuCard(data: BaziData) {
    val geJu = data.gj

    AnalysisCard(
        title = "日主格局",
        icon = { Icon(Icons.Default.Star, contentDescription = null, tint = AccentGold) }
    ) {
        InfoItem("格局类型", geJuToChinese(geJu))
        InfoItem("格局特点", getGeJuFeature(geJu))

        Spacer(Modifier.height(8.dp))
        Text(
            text = getGeJuDescription(geJu),
            fontSize = 14.sp,
            color = TextSecondary,
            lineHeight = 22.sp
        )
    }
}

@Composable
private fun YongShenAnalysisCard(data: BaziData) {
//    val character = data.yongXiJiResult.yongShenReason

    println("data.yongXiJiResult：${data.yongXiJiResult}")

    AnalysisCard(
        title = "用神分析",
        icon = { Icon(Icons.Default.CheckCircle, contentDescription = null, tint = InfoColor) }
    ) {
        Text(
            text = "${data.yongXiJiResult.yongShenReason}",
            fontSize = 14.sp,
            color = TextPrimary,
            lineHeight = 22.sp
        )
    }
}

@Composable
private fun CharacterAnalysisCard(data: BaziData) {
    val character = analyzeCharacter(data)

    AnalysisCard(
        title = "性格分析",
        icon = { Icon(Icons.Default.Person, contentDescription = null, tint = InfoColor) }
    ) {
        Text(
            text = character,
            fontSize = 14.sp,
            color = TextPrimary,
            lineHeight = 22.sp
        )
    }
}

@Composable
private fun CareerRecommendationCard(data: BaziData) {
    val careers = recommendCareers(data)

    AnalysisCard(
        title = "推荐职业",
        icon = { Icon(Icons.Default.Work, contentDescription = null, tint = GoodColor) }
    ) {
        careers.forEach { career ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = GoodColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = career,
                    fontSize = 14.sp,
                    color = TextPrimary
                )
            }
        }
    }
}

@Composable
private fun SixRelationsCard(data: BaziData) {
    val relations = analyzeSixRelations(data)

    AnalysisCard(
        title = "六亲缘分",
        icon = { Icon(Icons.Default.FamilyRestroom, contentDescription = null, tint = AccentGold) }
    ) {
        relations.forEach { (relation, description) ->
            InfoItem(relation, description)
        }
    }
}

// ========== 辅助函数 ==========

//private fun shiShenToChinese(shiShen: ShiShen): String = when (shiShen) {
//    ShiShen.SHISHEN_BI_JIAN -> "比肩"
//    ShiShen.SHISHEN_JIE_CAI -> "劫财"
//    ShiShen.SHISHEN_ZHENG_YIN -> "正印"
//    ShiShen.SHISHEN_PIAN_YIN -> "偏印"
//    ShiShen.SHISHEN_SHI_SHEN -> "食神"
//    ShiShen.SHISHEN_SHANG_GUAN -> "伤官"
//    ShiShen.SHISHEN_ZHENG_CAI -> "正财"
//    ShiShen.SHISHEN_PIAN_CAI -> "偏财"
//    ShiShen.SHISHEN_ZHENG_GUAN -> "正官"
//    ShiShen.SHISHEN_QI_SHA -> "七杀"
//}

//private fun geJuToChinese(geJu: BaziGeJu): String = when (geJu) {
//    BaziGeJu.GJ_NONE -> "普通格局"
//    BaziGeJu.GJ_ZHENG_GUAN -> "正官格"
//    BaziGeJu.GJ_QI_SHA -> "七杀格"
//    BaziGeJu.GJ_ZHENG_CAI -> "正财格"
//    BaziGeJu.GJ_PIAN_CAI -> "偏财格"
//    BaziGeJu.GJ_ZHENG_YIN -> "正印格"
//    BaziGeJu.GJ_PIAN_YIN -> "偏印格"
//    BaziGeJu.GJ_SHI_SHEN -> "食神格"
//    BaziGeJu.GJ_SHANG_GUAN -> "伤官格"
//    BaziGeJu.GJ_BI_JIAN -> "建禄格"
//    BaziGeJu.GJ_JIE_CAI -> "劫财格"
//    BaziGeJu.GJ_YANG_REN -> "羊刃格"
//
//    BaziGeJu.GJ_JIAN_LU -> "建禄格"
//    BaziGeJu.GJ_CONG_SHA -> "从杀格"
//    BaziGeJu.GJ_CONG_CAI -> "从财格"
//    BaziGeJu.GJ_CONG_ER -> "从儿格"
//    BaziGeJu.GJ_QU_ZHI -> "曲直格"
//    BaziGeJu.GJ_YAN_SHANG -> "炎上格"
//    BaziGeJu.GJ_JIA_SE -> "稼穑格"
//    BaziGeJu.GJ_CONG_GE -> "从格"
//    BaziGeJu.GJ_RUN_XIA -> "润下格"
//    else -> "未知格局"
//}
//
//private fun getGeJuFeature(geJu: BaziGeJu): String = when (geJu) {
//    BaziGeJu.GJ_NONE -> "无明显格局特征"
//    BaziGeJu.GJ_ZHENG_GUAN -> "为人正直，有领导力"
//    BaziGeJu.GJ_QI_SHA -> "果断勇敢，有魄力"
//    BaziGeJu.GJ_ZHENG_CAI -> "财运稳定，善于理财"
//    BaziGeJu.GJ_PIAN_CAI -> "偏财运好，敢闯敢拼"
//    BaziGeJu.GJ_ZHENG_YIN -> "学业有成，文化修养高"
//    BaziGeJu.GJ_PIAN_YIN -> "思维独特，创新能力强"
//    BaziGeJu.GJ_SHI_SHEN -> "性格温和，有艺术天赋"
//    BaziGeJu.GJ_SHANG_GUAN -> "才华横溢，表达能力好"
//    BaziGeJu.GJ_BI_JIAN -> "独立自主，朋友众多"
//    BaziGeJu.GJ_JIE_CAI -> "竞争意识强，适合创业"
//    BaziGeJu.GJ_YANG_REN -> "意志坚强，有大将之风"
//    else -> "未知格局"
//}
//
//private fun getGeJuDescription(geJu: BaziGeJu): String = when (geJu) {
//    BaziGeJu.GJ_NONE -> "八字格局较为普通，但可通过后天努力弥补先天不足。"
//    BaziGeJu.GJ_ZHENG_GUAN -> "正官格的人通常品行端正，有责任感和领导才能，适合从事管理岗位。"
//    BaziGeJu.GJ_QI_SHA -> "七杀格的人果断勇敢，具有开拓精神，适合挑战性的工作。"
//    BaziGeJu.GJ_ZHENG_CAI -> "正财格的人财运稳定，善于理财，适合稳定的职业发展。"
//    BaziGeJu.GJ_PIAN_CAI -> "偏财格的人善于把握机会，适合经商或投资领域。"
//    BaziGeJu.GJ_ZHENG_YIN -> "正印格的人学识渊博，文化修养高，适合教育、研究等工作。"
//    BaziGeJu.GJ_PIAN_YIN -> "偏印格的人思维独特，有创新精神，适合创意、设计等领域。"
//    BaziGeJu.GJ_SHI_SHEN -> "食神格的人性格温和，有艺术天赋，适合文艺创作类工作。"
//    BaziGeJu.GJ_SHANG_GUAN -> "伤官格的人才华出众，表达能力强，适合演艺、演讲等职业。"
//    BaziGeJu.GJ_BI_JIAN -> "建禄格的人独立自主，社交能力强，适合团队合作的工作。"
//    BaziGeJu.GJ_JIE_CAI -> "劫财格的人竞争意识强，适合创业或销售类工作。"
//    BaziGeJu.GJ_YANG_REN -> "羊刃格的人意志坚定，有大将风范，适合军警或管理岗位。"
//    else -> "未知格局"
//}

private fun analyzeCharacter(data: BaziData): String {
    val sb = StringBuilder()
    val dayMasterWx = getTianGanWuxing(data.dayTiangan)

    // 根据日主五行分析性格
    sb.append("日主五行属${WuXingExt.getWuXingText(dayMasterWx)}，")
    sb.append(
        when (dayMasterWx) {
            WuXing.WUXING_MU -> "木主仁，性格仁慈宽厚，有上进心，善于规划。"
            WuXing.WUXING_HUO -> "火主礼，热情开朗，积极向上，有感染力。"
            WuXing.WUXING_TU -> "土主信，稳重踏实，诚信可靠，有包容心。"
            WuXing.WUXING_JIN -> "金主义，果断刚毅，有决断力，追求完美。"
            WuXing.WUXING_SHUI -> "水主智，聪明灵活，善于变通，有洞察力。"
        }
    )

    // 根据十神分析性格特点
    val yongShenNames = data.yongShenList.map { shiShenToChinese(it) }
    if (yongShenNames.isNotEmpty()) {
        sb.append("\n\n用神为${yongShenNames.joinToString("、")}，")
        sb.append("在这些方面有较好的天赋和表现。")
    }

    return sb.toString()
}

private fun recommendCareers(data: BaziData): List<String> {
    val careers = mutableListOf<String>()
    val dayMasterWx = getTianGanWuxing(data.dayTiangan)

    // 根据日主五行推荐职业
    when (dayMasterWx) {
        WuXing.WUXING_MU -> {
            careers.addAll(
                listOf(
                    "教育行业（教师、培训师）",
                    "文化创意（写作、设计）",
                    "医疗健康（中医、养生）",
                    "环保绿化（园林、生态）"
                )
            )
        }

        WuXing.WUXING_HUO -> {
            careers.addAll(
                listOf(
                    "传媒娱乐（演艺、主持）",
                    "科技互联网（IT、新媒体）",
                    "教育培训（讲师、咨询）",
                    "餐饮服务（美食、烘焙）"
                )
            )
        }

        WuXing.WUXING_TU -> {
            careers.addAll(
                listOf(
                    "房地产（建筑、物业）",
                    "金融保险（银行、证券）",
                    "行政管理（HR、后勤）",
                    "农业食品（种植、养殖）"
                )
            )
        }

        WuXing.WUXING_JIN -> {
            careers.addAll(
                listOf(
                    "金融投资（基金、证券）",
                    "法律咨询（律师、法务）",
                    "精密制造（工程师、质检）",
                    "军警安保（警察、军人）"
                )
            )
        }

        WuXing.WUXING_SHUI -> {
            careers.addAll(
                listOf(
                    "贸易物流（外贸、运输）",
                    "信息技术（编程、数据分析）",
                    "旅游服务（导游、酒店）",
                    "心理咨询（心理医生、顾问）"
                )
            )
        }
    }

    // 根据用神调整推荐
    val yongShenNames = data.yongShenList.map { shiShenToChinese(it) }
    if (yongShenNames.contains("正印") || yongShenNames.contains("偏印")) {
        careers.add(0, "学术研究（科研、教授）")
    }
    if (yongShenNames.contains("正财") || yongShenNames.contains("偏财")) {
        careers.add(0, "商业经营（创业、管理）")
    }

    return careers.distinct()
}

private fun analyzeSixRelations(data: BaziData): List<Pair<String, String>> {
    val relations = mutableListOf<Pair<String, String>>()

    val ssUtil = ShiShenUtil()

    // 年柱代表祖辈
    val yearShiShen = ssUtil.getShiShen(data.yearTiangan, data.dayTiangan)
    relations.add(
        "祖辈缘分" to "年干为${shiShenToChinese(yearShiShen)}，祖辈关系${
            getRelationQuality(
                yearShiShen
            )
        }"
    )

    // 月柱代表父母
    val monthShiShen = ssUtil.getShiShen(data.monthTiangan, data.dayTiangan)
    relations.add(
        "父母缘分" to "月干为${shiShenToChinese(monthShiShen)}，父母关系${
            getRelationQuality(
                monthShiShen
            )
        }"
    )

    // 日支代表配偶
    val dayZhiShiShen = ssUtil.getShiShen(DiZhiUtil().getTianGan(data.dayDizhi), data.dayTiangan)

    relations.add(
        "配偶缘分" to "日支藏干为${shiShenToChinese(dayZhiShiShen)}，婚姻关系${
            getRelationQuality(
                dayZhiShiShen
            )
        }"
    )

    // 时柱代表子女
    val hourShiShen = ssUtil.getShiShen(data.hourTiangan, data.dayTiangan)
    relations.add(
        "子女缘分" to "时干为${shiShenToChinese(hourShiShen)}，子女关系${
            getRelationQuality(
                hourShiShen
            )
        }"
    )

    return relations
}

private fun getRelationQuality(shiShen: ShiShen): String {
    val shiShenText = shiShenToChinese(shiShen)
    return getRelationQualityText(shiShenText)
}

private fun getRelationQualityText(shiShen: String): String = when (shiShen) {
    "正印", "偏印", "正官", "食神" -> "和谐融洽，相互扶持"
    "正财", "偏财", "比肩" -> "关系平等，互帮互助"
    "劫财", "七杀", "伤官" -> "略有摩擦，需多沟通"
    else -> "一般，需用心经营"
}

private fun buildAnalysisText(data: BaziData): String {
    val sb = StringBuilder()
    sb.append("📊 八字命盘分析报告\n\n")
    sb.append("出生时间：${data.birthDateYear}年${data.birthDateMonth}月${data.birthDateDay}日 ${data.birthHour}时\n")
    sb.append("性别：${if (data.gender == "Male") "男" else "女"}\n\n")

    sb.append("【四柱八字】\n")
//    val tianGanUtil = TianGanUtil()
//    val diZhiUtil = DiZhiUtil()
    sb.append("年柱：${formatTianganDizhi(data.yearTiangan, data.yearDizhi)}\n")
    sb.append("月柱：${formatTianganDizhi(data.monthTiangan, data.monthDizhi)}\n")
    sb.append("日柱：${formatTianganDizhi(data.dayTiangan, data.dayDizhi)}\n")
    sb.append("时柱：${formatTianganDizhi(data.hourTiangan, data.hourDizhi)}\n\n")

    sb.append("【喜用神】${data.yongShenList.joinToString("、") { shiShenToChinese(it) }}\n")
    sb.append("【忌神】${data.jiShenList.joinToString("、") { shiShenToChinese(it) }}\n\n")

    sb.append("【性格分析】\n${analyzeCharacter(data)}\n\n")

    sb.append("【推荐职业】\n")
    recommendCareers(data).forEach { sb.append("• $it\n") }

    return sb.toString()
}

@Preview
@Composable
fun BaziAppAnalysisScreenPreview() {

    val navController = rememberNavController()
    BaziTheme {
        BaziAppAnalysisScreen(
            onSendButtonClicked = { subject: String, summary: String -> },
            onCancelButtonClicked = {},
            baziInfo = BaziInfo(name = ""),
            baziModel = viewModel(),
            navController = navController,
            modifier = Modifier.fillMaxHeight()
        )
    }
}