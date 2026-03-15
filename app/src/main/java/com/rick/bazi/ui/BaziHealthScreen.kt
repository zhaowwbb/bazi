package com.rick.bazi.ui

import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import com.rick.bazi.R
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.BaziGeJu
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.WuXing
import com.rick.bazi.ui.theme.BaziTheme
import com.rick.bazi.util.BaziFormatter
import com.rick.bazi.util.BaziUtil
import com.rick.bazi.util.ConstUtil
import com.rick.bazi.util.DayMasterStrength
import com.rick.bazi.util.GrandLuckHealthDisplay
import com.rick.bazi.util.StrengthLevel
import com.rick.bazi.util.TaoHuaUtil
import com.rick.bazi.util.WuXingUtil
import com.rick.bazi.util.WuXingWeightCalculator
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun BaziHealthScreen(
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    baziInfo: BaziInfo,
    baziModel: BaziViewModel,
    modifier: Modifier = Modifier
) {
    BaziHealthReportScreen(baziInfo.baziData, modifier)
}

@Preview
@Composable
fun BaziHealthScreenPreview() {
    BaziTheme {
        BaziHealthScreen(
            onSendButtonClicked = { subject: String, summary: String -> },
            onCancelButtonClicked = {},
            baziInfo = BaziInfo(name = ""),
            baziModel = viewModel(),
            modifier = Modifier.fillMaxHeight()
        )
    }
}

// 五行对应表
private val ELEMENT_MAP = mapOf(
    "甲" to "木", "乙" to "木",
    "丙" to "火", "丁" to "火",
    "戊" to "土", "己" to "土",
    "庚" to "金", "辛" to "金",
    "壬" to "水", "癸" to "水"
)

private val WX_ELEMENT_MAP = mapOf(
    WuXing.WUXING_MU to "木",
    WuXing.WUXING_HUO to "火",
    WuXing.WUXING_TU to "土",
    WuXing.WUXING_JIN to "金",
    WuXing.WUXING_SHUI to "水"
)

// 器官五行对应
private val ORGAN_ELEMENTS = mapOf(
    "木" to listOf("肝", "胆", "眼睛", "筋"),
    "火" to listOf("心", "小肠", "舌", "血脉"),
    "土" to listOf("脾", "胃", "口", "肌肉"),
    "金" to listOf("肺", "大肠", "鼻", "皮肤"),
    "水" to listOf("肾", "膀胱", "耳", "骨")
)

// 地支三刑
private val THREE_PENALTIES = listOf(
    setOf(DiZhi.DIZHI_YIN, DiZhi.DIZHI_SI, DiZhi.DIZHI_SHEN),  // 无恩之刑
    setOf(DiZhi.DIZHI_CHOU, DiZhi.DIZHI_XU, DiZhi.DIZHI_WEI),  // 持势之刑
    setOf(DiZhi.DIZHI_ZI, DiZhi.DIZHI_MOU)         // 无礼之刑（二刑）
)

// 1. 健康预测数据模型
data class HealthPredictionData(
    val baziData: BaziData,
    val constitutionAnalysis: ConstitutionAnalysis,      // 先天体质分析
    val elementAnalysis: ElementAnalysis,                // 五行分析
    val healthRisk: HealthRisk,                          // 健康风险评估
    val grandLuckAnalysis: List<GrandLuckHealthAnalysis>,  // 大运健康分析
    val flowYearAnalysis: FlowYearHealthAnalysis,        // 流年健康分析
    val conflicts: List<ConflictAnalysis>,             // 刑冲克泄分析
    val recommendations: List<String>                   // 健康建议
)

data class ConstitutionAnalysis(
    val strongOrgans: List<Organ>,      // 强健的器官
    val weakOrgans: List<Organ>,        // 薄弱的器官
    val constitutionType: String,       // 体质类型
    val description: String            // 体质描述
)

data class ElementAnalysis(
    val elementBalances: Map<WuXing, ElementBalance>,  // 五行平衡情况
    val excessElements: List<WuXing>,                  // 过旺的五行
    var deficientElements: List<WuXing>,               // 不足的五行
    val dominantElement: WuXing?                      // 主导五行
)

data class ElementBalance(
    val element: WuXing,
    val weight: Float,         // 五行权重
    val status: ElementStatus, // 状态
    val relatedOrgans: List<Organ>
)

enum class ElementStatus {
    EXCESS,     // 过旺
    BALANCED,   // 平衡
    DEFICIENT,  // 不足
    VERY_DEFICIENT // 严重不足
}

data class HealthRisk(
    val riskLevel: RiskLevel,           // 风险等级
    val riskAreas: List<String>,        // 风险部位
    val highRiskPeriods: List<Period>,  // 高风险时期
    val score: Int                      // 风险评分
)

enum class RiskLevel {
    VERY_LOW,   // 很低
    LOW,        // 低
    MEDIUM,     // 中
    HIGH,       // 高
    VERY_HIGH   // 很高
}

data class Period(
    val startYear: Int,
    val endYear: Int,
    val description: String
)

data class GrandLuckHealthAnalysis(
    val startYear: Int,                 // 大运起始年
    val celestial: TianGan,             // 天干
    val terrestrial: DiZhi,             // 地支
    val luckPeriod: String,             // 大运期
    val healthTrend: HealthTrend,       // 健康趋势
    val warnings: List<String>,         // 健康预警
    val affectedElements: List<WuXing> // 影响的五行
)

data class FlowYearHealthAnalysis(
    val year: Int,                      // 流年年份
    val celestial: TianGan,            // 流年天干
    val terrestrial: DiZhi,            // 流年地支
    val healthImpact: HealthImpact,    // 健康影响
    val criticalMonths: List<Int>,     // 需关注的月份
    val elementChanges: Map<WuXing, Float> // 五行变化
)

enum class HealthTrend {
    IMPROVING,      // 改善
    STABLE,         // 稳定
    DECLINING,      // 下降
    FLUCTUATING,    // 波动
    CRITICAL        // 关键
}

enum class HealthImpact {
    POSITIVE,       // 正面
    NEUTRAL,        // 中性
    WARNING,        // 警告
    DANGEROUS       // 危险
}

data class ConflictAnalysis(
    val type: ConflictType,            // 冲突类型
    val sourceElement: WuXing,         // 来源五行
    val targetElement: WuXing,         // 目标五行
    val affectedOrgans: List<Organ>,   // 影响的器官
    val severity: Int,                 // 严重程度 1-5
    val effectDescription: String,    // 影响描述
    val period: Period?               // 发生时期
)

enum class ConflictType {
    PUNISHMENT,     // 刑
    CLASH,          // 冲
    OVERCOME,       // 克
    DRAIN,          // 泄
    HARM            // 害
}

data class Organ(
    val name: String,
    val element: WuXing,
    val system: String
)

// 2. 健康预测分析器
class BaziHealthAnalyzer {

    companion object {
        // 器官五行对应表
        private val ORGAN_MAPPING = mapOf(
            WuXing.WUXING_MU to listOf(
                Organ("肝", WuXing.WUXING_MU, "消化系统"),
                Organ("胆", WuXing.WUXING_MU, "消化系统"),
                Organ("眼睛", WuXing.WUXING_MU, "感觉系统"),
                Organ("筋", WuXing.WUXING_MU, "运动系统")
            ),
            WuXing.WUXING_HUO to listOf(
                Organ("心", WuXing.WUXING_HUO, "循环系统"),
                Organ("小肠", WuXing.WUXING_HUO, "消化系统"),
                Organ("舌", WuXing.WUXING_HUO, "感觉系统"),
                Organ("血脉", WuXing.WUXING_HUO, "循环系统")
            ),
            WuXing.WUXING_TU to listOf(
                Organ("脾", WuXing.WUXING_TU, "消化系统"),
                Organ("胃", WuXing.WUXING_TU, "消化系统"),
                Organ("口", WuXing.WUXING_TU, "消化系统"),
                Organ("肌肉", WuXing.WUXING_TU, "运动系统")
            ),
            WuXing.WUXING_JIN to listOf(
                Organ("肺", WuXing.WUXING_JIN, "呼吸系统"),
                Organ("大肠", WuXing.WUXING_JIN, "消化系统"),
                Organ("鼻", WuXing.WUXING_JIN, "呼吸系统"),
                Organ("皮肤", WuXing.WUXING_JIN, "皮肤系统")
            ),
            WuXing.WUXING_SHUI to listOf(
                Organ("肾", WuXing.WUXING_SHUI, "泌尿系统"),
                Organ("膀胱", WuXing.WUXING_SHUI, "泌尿系统"),
                Organ("耳", WuXing.WUXING_SHUI, "感觉系统"),
                Organ("骨", WuXing.WUXING_SHUI, "运动系统")
            )
        )

        // 地支六冲关系
        private val SIX_CLASHES = mapOf(
            DiZhi.DIZHI_ZI to DiZhi.DIZHI_WU,
            DiZhi.DIZHI_CHOU to DiZhi.DIZHI_WEI,
            DiZhi.DIZHI_YIN to DiZhi.DIZHI_SHEN,
            DiZhi.DIZHI_MOU to DiZhi.DIZHI_YOU,
            DiZhi.DIZHI_CHEN to DiZhi.DIZHI_XU,
            DiZhi.DIZHI_SI to DiZhi.DIZHI_HAI
        )

        // 地支三刑
        private val THREE_PUNISHMENTS = listOf(
            setOf(DiZhi.DIZHI_YIN, DiZhi.DIZHI_SI, DiZhi.DIZHI_SHEN),  // 无恩之刑
            setOf(DiZhi.DIZHI_CHOU, DiZhi.DIZHI_XU, DiZhi.DIZHI_WEI),  // 持势之刑
            setOf(DiZhi.DIZHI_ZI, DiZhi.DIZHI_MOU)                     // 无礼之刑
        )

        // 天干相克关系
        private val STEM_OVERCOMING = mapOf(
            TianGan.TIANGAN_JIA to WuXing.WUXING_TU,  // 甲克戊
            TianGan.TIANGAN_YI to WuXing.WUXING_TU,   // 乙克己
            TianGan.TIANGAN_BING to WuXing.WUXING_JIN,  // 丙克庚
            TianGan.TIANGAN_DING to WuXing.WUXING_JIN,  // 丁克辛
            TianGan.TIANGAN_WU to WuXing.WUXING_SHUI,  // 戊克壬
            TianGan.TIANGAN_JI to WuXing.WUXING_SHUI,  // 己克癸
            TianGan.TIANGAN_GENG to WuXing.WUXING_MU,  // 庚克甲
            TianGan.TIANGAN_XIN to WuXing.WUXING_MU,   // 辛克乙
            TianGan.TIANGAN_REN to WuXing.WUXING_HUO,  // 壬克丙
            TianGan.TIANGAN_GUI to WuXing.WUXING_HUO   // 癸克丁
        )

        // 五行相生相克
        private val ELEMENT_GENERATING = mapOf(
            WuXing.WUXING_MU to WuXing.WUXING_HUO,  // 木生火
            WuXing.WUXING_HUO to WuXing.WUXING_TU,  // 火生土
            WuXing.WUXING_TU to WuXing.WUXING_JIN,  // 土生金
            WuXing.WUXING_JIN to WuXing.WUXING_SHUI,  // 金生水
            WuXing.WUXING_SHUI to WuXing.WUXING_MU   // 水生木
        )

        private val ELEMENT_OVERCOMING = mapOf(
            WuXing.WUXING_MU to WuXing.WUXING_TU,  // 木克土
            WuXing.WUXING_TU to WuXing.WUXING_SHUI,  // 土克水
            WuXing.WUXING_SHUI to WuXing.WUXING_HUO,  // 水克火
            WuXing.WUXING_HUO to WuXing.WUXING_JIN,  // 火克金
            WuXing.WUXING_JIN to WuXing.WUXING_MU   // 金克木
        )

        // 天干五行映射
        private val STEM_TO_ELEMENT = mapOf(
            TianGan.TIANGAN_JIA to WuXing.WUXING_MU,
            TianGan.TIANGAN_YI to WuXing.WUXING_MU,
            TianGan.TIANGAN_BING to WuXing.WUXING_HUO,
            TianGan.TIANGAN_DING to WuXing.WUXING_HUO,
            TianGan.TIANGAN_WU to WuXing.WUXING_TU,
            TianGan.TIANGAN_JI to WuXing.WUXING_TU,
            TianGan.TIANGAN_GENG to WuXing.WUXING_JIN,
            TianGan.TIANGAN_XIN to WuXing.WUXING_JIN,
            TianGan.TIANGAN_REN to WuXing.WUXING_SHUI,
            TianGan.TIANGAN_GUI to WuXing.WUXING_SHUI
        )

        // 地支五行映射
        private val BRANCH_TO_ELEMENT = mapOf(
            DiZhi.DIZHI_ZI to WuXing.WUXING_SHUI,
            DiZhi.DIZHI_CHOU to WuXing.WUXING_TU,
            DiZhi.DIZHI_YIN to WuXing.WUXING_MU,
            DiZhi.DIZHI_MOU to WuXing.WUXING_MU,
            DiZhi.DIZHI_CHEN to WuXing.WUXING_TU,
            DiZhi.DIZHI_SI to WuXing.WUXING_HUO,
            DiZhi.DIZHI_WU to WuXing.WUXING_HUO,
            DiZhi.DIZHI_WEI to WuXing.WUXING_TU,
            DiZhi.DIZHI_SHEN to WuXing.WUXING_JIN,
            DiZhi.DIZHI_YOU to WuXing.WUXING_JIN,
            DiZhi.DIZHI_XU to WuXing.WUXING_TU,
            DiZhi.DIZHI_HAI to WuXing.WUXING_SHUI
        )
    }

    // 3. 主分析函数
    fun analyzeHealth(baziData: BaziData): HealthPredictionData {
        // 先天八字分析
        val constitution = analyzeConstitution(baziData)

        // 计算五行权重
        val weights = WuXingWeightCalculator.calculateTotalWuXingWeights(baziData)
        val elementAnalysis = analyzeElements(weights)

        // 计算日主强度
        val dayMasterStrength = WuXingWeightCalculator.calculateDayMasterStrength(baziData)

        val healthRisk = assessHealthRisk(baziData, elementAnalysis, dayMasterStrength)

        // 大运流年分析
        val grandLuckAnalysis = analyzeGrandLuckHealth(baziData)
        val flowYearAnalysis = analyzeFlowYearHealth(baziData)

        // 刑冲克泄分析
        val conflicts = analyzeConflicts(baziData)

        // 生成建议
        val recommendations = generateRecommendations(
            constitution,
            elementAnalysis,
            healthRisk,
            conflicts
        )

        return HealthPredictionData(
            baziData = baziData,
            constitutionAnalysis = constitution,
            elementAnalysis = elementAnalysis,
            healthRisk = healthRisk,
            grandLuckAnalysis = grandLuckAnalysis,
            flowYearAnalysis = flowYearAnalysis,
            conflicts = conflicts,
            recommendations = recommendations
        )
    }

    // 4. 先天体质分析
    private fun analyzeConstitution(baziData: BaziData): ConstitutionAnalysis {
        // 使用WuXingWeightCalculator计算五行权重
        val weights = WuXingWeightCalculator.calculateTotalWuXingWeights(baziData)

        // 计算日主强度
        val dayMasterStrength = WuXingWeightCalculator.calculateDayMasterStrength(baziData)
        val dayMasterWuxing = WuXingWeightCalculator.getTianGanWuxing(baziData.dayTiangan)

        // 日主强弱分析
        val isDayStrong = when (dayMasterStrength.strengthLevel) {
            StrengthLevel.STRONG, StrengthLevel.VERY_STRONG -> true
            else -> false
        }

        // 日主阴阳判断
        val dayElement = if (baziData.dayTiangan in listOf(
                TianGan.TIANGAN_JIA, TianGan.TIANGAN_BING,
                TianGan.TIANGAN_WU, TianGan.TIANGAN_GENG,
                TianGan.TIANGAN_REN
            )
        ) {
            "阳"
        } else {
            "阴"
        }

        // 1. 体质类型判断
        val constitutionType = determineConstitutionType(
            dayMasterWuxing = dayMasterWuxing,
            isDayStrong = isDayStrong,
            dayElement = dayElement,
            weights = weights,
            baziData = baziData
        )

        // 2. 器官强弱分析
        val (strongOrgans, weakOrgans) = analyzeOrgansStrength(
            weights = weights,
            averageWeight = weights.values.average().toFloat()
        )

        // 3. 体质描述
        val description = generateConstitutionDescription(
            constitutionType = constitutionType,
            dayMasterWuxing = dayMasterWuxing,
            dayMasterStrength = dayMasterStrength,
            weakOrgans = weakOrgans
        )

        return ConstitutionAnalysis(
            strongOrgans = strongOrgans,
            weakOrgans = weakOrgans,
            constitutionType = constitutionType,
            description = description
        )
    }

    /**
     * 判断体质类型
     */
    private fun determineConstitutionType(
        dayMasterWuxing: WuXing,
        isDayStrong: Boolean,
        dayElement: String,
        weights: Map<WuXing, Float>,
        baziData: BaziData
    ): String {
        val totalWeight = weights.values.sum()
        val averageWeight = totalWeight / 5

        // 分析五行平衡
        val excessElements = weights.filter { it.value > averageWeight * 1.5 }.keys
        val deficientElements = weights.filter { it.value < averageWeight * 0.5 }.keys

        // 1. 先根据日主强弱和阴阳判断
        var baseType = when {
            isDayStrong && dayElement == "阳" -> "阳盛"
            isDayStrong && dayElement == "阴" -> "阴盛"
            !isDayStrong && dayElement == "阳" -> "阳虚"
            !isDayStrong && dayElement == "阴" -> "阴虚"
            else -> "平和"
        }

        // 2. 根据五行过旺调整
        if (excessElements.isNotEmpty()) {
            when (excessElements.first()) {
                WuXing.WUXING_HUO -> {
                    if (baseType == "阳盛") baseType = "火盛阳亢"
                    else if (baseType == "阴虚") baseType = "阴虚火旺"
                }

                WuXing.WUXING_SHUI -> {
                    if (baseType == "阴盛") baseType = "寒湿内盛"
                    else if (baseType == "阳虚") baseType = "阳虚水泛"
                }

                WuXing.WUXING_TU -> {
                    if (baseType == "阳盛" || baseType == "阴盛") baseType = "痰湿体质"
                }

                WuXing.WUXING_MU -> {
                    if (baseType == "阳盛") baseType = "肝阳上亢"
                }

                WuXing.WUXING_JIN -> {
                    if (baseType == "阴虚") baseType = "肺燥阴虚"
                }
            }
        }

        // 3. 根据五行不足调整
        if (deficientElements.isNotEmpty()) {
            when (deficientElements.first()) {
                WuXing.WUXING_SHUI -> {
                    if (baseType == "阴虚") baseType = "肾阴亏虚"
                    else if (baseType == "阳盛") baseType = "阴虚阳亢"
                }

                WuXing.WUXING_HUO -> {
                    if (baseType == "阳虚") baseType = "心阳不振"
                }

                WuXing.WUXING_TU -> {
                    if (baseType == "阴虚") baseType = "脾胃阴虚"
                    else if (baseType == "阳虚") baseType = "脾阳不足"
                }

                WuXing.WUXING_MU -> {
                    if (baseType == "阴虚") baseType = "肝阴不足"
                }

                WuXing.WUXING_JIN -> {
                    if (baseType == "阴虚") baseType = "肺阴不足"
                }
            }
        }

        // 印重身强
        if (baziData.gj == BaziGeJu.GJ_ZHENG_YIN && isDayStrong) {
            baseType = "气血充足"  // 印重身强
        }

        // 杀重身弱
        if (baziData.gj == BaziGeJu.GJ_QI_SHA && !isDayStrong) {
            baseType = "气血两虚"  // 杀重身弱
        }

        //)
//        // 4. 特殊格局判断
//        if (baziData.gj == BaziGeJu.GJ_SHA_CAI) {
//            baseType = "气血两虚"  // 杀重身弱
//        } else if (baziData.gj == BaziGeJu.GJ_ZHENG_YIN) {
//            baseType = "气血充足"  // 印重身强
//        }

        return baseType
    }

    /**
     * 分析器官强弱
     */
    private fun analyzeOrgansStrength(
        weights: Map<WuXing, Float>,
        averageWeight: Float
    ): Pair<List<Organ>, List<Organ>> {
        val strongOrgans = mutableListOf<Organ>()
        val weakOrgans = mutableListOf<Organ>()

        // 器官五行对应表
        val organMapping = mapOf(
            WuXing.WUXING_JIN to listOf(
                Organ("肺", WuXing.WUXING_JIN, "呼吸系统"),
                Organ("大肠", WuXing.WUXING_JIN, "消化系统"),
                Organ("鼻", WuXing.WUXING_JIN, "呼吸系统"),
                Organ("皮肤", WuXing.WUXING_JIN, "皮肤系统")
            ),
            WuXing.WUXING_MU to listOf(
                Organ("肝", WuXing.WUXING_MU, "消化系统"),
                Organ("胆", WuXing.WUXING_MU, "消化系统"),
                Organ("眼睛", WuXing.WUXING_MU, "感觉系统"),
                Organ("筋", WuXing.WUXING_MU, "运动系统")
            ),
            WuXing.WUXING_SHUI to listOf(
                Organ("肾", WuXing.WUXING_SHUI, "泌尿系统"),
                Organ("膀胱", WuXing.WUXING_SHUI, "泌尿系统"),
                Organ("耳", WuXing.WUXING_SHUI, "感觉系统"),
                Organ("骨", WuXing.WUXING_SHUI, "运动系统"),
                Organ("骨髓", WuXing.WUXING_SHUI, "造血系统")
            ),
            WuXing.WUXING_HUO to listOf(
                Organ("心", WuXing.WUXING_HUO, "循环系统"),
                Organ("小肠", WuXing.WUXING_HUO, "消化系统"),
                Organ("舌", WuXing.WUXING_HUO, "感觉系统"),
                Organ("血脉", WuXing.WUXING_HUO, "循环系统")
            ),
            WuXing.WUXING_TU to listOf(
                Organ("脾", WuXing.WUXING_TU, "消化系统"),
                Organ("胃", WuXing.WUXING_TU, "消化系统"),
                Organ("口", WuXing.WUXING_TU, "消化系统"),
                Organ("肌肉", WuXing.WUXING_TU, "运动系统"),
                Organ("四肢", WuXing.WUXING_TU, "运动系统")
            )
        )

        // 分析每个五行的器官强弱
        weights.forEach { (wuxing, weight) ->
            val organs = organMapping[wuxing] ?: emptyList()

            when {
                // 过旺：权重大于平均值的1.5倍
                weight > averageWeight * 1.5 -> {
                    strongOrgans.addAll(organs)
                }
                // 不足：权重小于平均值的0.5倍
                weight < averageWeight * 0.5 -> {
                    weakOrgans.addAll(organs)
                }
                // 平衡：权重在平均值附近
                else -> {
                    // 不特别标记
                }
            }
        }

        return Pair(strongOrgans, weakOrgans)
    }

    /**
     * 生成体质描述
     */
    private fun generateConstitutionDescription(
        constitutionType: String,
        dayMasterWuxing: WuXing,
        dayMasterStrength: DayMasterStrength,
        weakOrgans: List<Organ>
    ): String {
        val descriptions = mutableListOf<String>()

        // 基础描述
        descriptions.add(
            when (constitutionType) {
                "平和体质" -> "阴阳平衡，气血调和，先天体质良好。"
                "阳盛体质" -> "阳气旺盛，精力充沛，新陈代谢快。"
                "阴盛体质" -> "阴气偏盛，性情温和，新陈代谢较慢。"
                "阳虚体质" -> "阳气不足，畏寒怕冷，免疫力较弱。"
                "阴虚体质" -> "阴液不足，易生内热，需防干燥。"
                "火盛阳亢" -> "火气过旺，易上火，性情急躁。"
                "阴虚火旺" -> "阴虚内热，易口干舌燥，五心烦热。"
                "寒湿内盛" -> "寒湿重，怕冷，易水肿。"
                "阳虚水泛" -> "阳气不足，水湿停滞。"
                "痰湿体质" -> "痰湿重，体形偏胖，易生痰。"
                "肝阳上亢" -> "肝火旺盛，易怒，头晕目眩。"
                "肺燥阴虚" -> "肺燥津亏，易干咳，皮肤干燥。"
                "肾阴亏虚" -> "肾阴不足，腰膝酸软，耳鸣。"
                "心阳不振" -> "心气不足，心悸气短，乏力。"
                "脾胃阴虚" -> "脾胃阴亏，消化不良，口干。"
                "脾阳不足" -> "脾阳虚弱，消化不良，四肢不温。"
                "肝阴不足" -> "肝阴亏虚，眼干涩，视力模糊。"
                "肺阴不足" -> "肺阴亏虚，干咳少痰，声音嘶哑。"
                "气血两虚" -> "气血不足，面色萎黄，乏力。"
                "气血充足" -> "气血旺盛，精力充沛，抵抗力强。"
                else -> "特殊体质类型。"
            }
        )

        // 添加日主信息
        val dayMasterName = when (dayMasterWuxing) {
            WuXing.WUXING_JIN -> "金"
            WuXing.WUXING_MU -> "木"
            WuXing.WUXING_SHUI -> "水"
            WuXing.WUXING_HUO -> "火"
            WuXing.WUXING_TU -> "土"
        }

        val strengthDesc = when (dayMasterStrength.strengthLevel) {
            StrengthLevel.VERY_STRONG -> "非常强旺"
            StrengthLevel.STRONG -> "强旺"
            StrengthLevel.MEDIUM -> "中和"
            StrengthLevel.WEAK -> "偏弱"
            StrengthLevel.VERY_WEAK -> "很弱"
        }

        descriptions.add("日主为$dayMasterName，状态$strengthDesc。")

        // 添加薄弱器官信息
        if (weakOrgans.isNotEmpty()) {
            val weakOrganNames = weakOrgans.map { it.name }.distinct().take(3)
            descriptions.add("${weakOrganNames.joinToString("、")}功能相对较弱，需特别注意保养。")
        }

        // 添加健康提示
        descriptions.add("注意保持规律作息，根据季节变化调整生活习惯。")

        return descriptions.joinToString(" ")
    }

    /**
     * 扩展功能：体质健康评分
     */
    data class ConstitutionHealthScore(
        val constitutionType: String,
        val score: Int,  // 0-100分
        val strengths: List<String>,
        val weaknesses: List<String>,
        val improvementSuggestions: List<String>
    )

    private fun getOrgansByElement(element: WuXing): List<Organ> {
        return when (element) {
            WuXing.WUXING_JIN -> listOf(
                Organ("肺", WuXing.WUXING_JIN, "呼吸系统"),
                Organ("大肠", WuXing.WUXING_JIN, "消化系统")
            )

            WuXing.WUXING_MU -> listOf(
                Organ("肝", WuXing.WUXING_MU, "消化系统"),
                Organ("胆", WuXing.WUXING_MU, "消化系统")
            )

            WuXing.WUXING_SHUI -> listOf(
                Organ("肾", WuXing.WUXING_SHUI, "泌尿系统"),
                Organ("膀胱", WuXing.WUXING_SHUI, "泌尿系统")
            )

            WuXing.WUXING_HUO -> listOf(
                Organ("心", WuXing.WUXING_HUO, "循环系统"),
                Organ("小肠", WuXing.WUXING_HUO, "消化系统")
            )

            WuXing.WUXING_TU -> listOf(
                Organ("脾", WuXing.WUXING_TU, "消化系统"),
                Organ("胃", WuXing.WUXING_TU, "消化系统")
            )
        }
    }

    // 5. 五行分析
    private fun analyzeElements(weights: Map<WuXing, Float>): ElementAnalysis {
        val totalWeight = weights.values.sum()
        val averageWeight = totalWeight / weights.size

        val elementBalances = mutableMapOf<WuXing, ElementBalance>()
        val excessElements = mutableListOf<WuXing>()
        val deficientElements = mutableListOf<WuXing>()

        weights.forEach { (element, weight) ->
            val status = when {
                weight > averageWeight * 1.5 -> ElementStatus.EXCESS
                weight < averageWeight * 0.5 -> ElementStatus.VERY_DEFICIENT
                weight < averageWeight * 0.8 -> ElementStatus.DEFICIENT
                else -> ElementStatus.BALANCED
            }

            if (status == ElementStatus.EXCESS) {
                excessElements.add(element)
            } else if (status == ElementStatus.VERY_DEFICIENT || status == ElementStatus.DEFICIENT) {
                deficientElements.add(element)
            }

            elementBalances[element] = ElementBalance(
                element = element,
                weight = weight,
                status = status,
                relatedOrgans = getOrgansByElement(element)
            )
        }

        val dominantElement = weights.maxByOrNull { it.value }?.key

        return ElementAnalysis(
            elementBalances = elementBalances,
            excessElements = excessElements,
            deficientElements = deficientElements,
            dominantElement = dominantElement
        )
    }


    // 6. 健康风险评估
    private fun assessHealthRisk(
        baziData: BaziData,
        elementAnalysis: ElementAnalysis,
        dayMasterStrength: DayMasterStrength
    ): HealthRisk {
        var riskScore = 0
        val riskAreas = mutableListOf<String>()

        // 根据五行分析评估风险
        elementAnalysis.elementBalances.forEach { (_, balance) ->
            when (balance.status) {
                ElementStatus.VERY_DEFICIENT -> {
                    riskScore += 3
                    balance.relatedOrgans.forEach { organ ->
                        riskAreas.add("${organ.name}功能不足")
                    }
                }

                ElementStatus.DEFICIENT -> {
                    riskScore += 2
                }

                ElementStatus.EXCESS -> {
                    riskScore += 1
                    balance.relatedOrgans.forEach { organ ->
                        riskAreas.add("${organ.name}功能亢进")
                    }
                }

                else -> {}
            }
        }

//        // 根据用神分析
//        if (baziData.yongShenList.isNotEmpty()) {
//            riskScore += 1
//        }
//
//        // 根据忌神分析
//        if (baziData.jiShenList.isNotEmpty()) {
//            riskScore += 2
//        }

        // 根据格局评估
//        when (baziData.gj) {
//            BaziGeJu.GJ_QI_SHA -> {  // 假设是煞财格
//                riskScore += 1
//                riskAreas.add("需注意心血管健康")
//            }
//
//            BaziGeJu.GJ_ZHENG_CAI -> {  // 假设是煞财格
//                riskScore += 1
//                riskAreas.add("需注意心血管健康")
//            }
//
//            BaziGeJu.GJ_ZHENG_YIN -> {  // 正印格
//                riskScore -= 1
//            }
//            // 其他格局...
//            else -> {}
//        }

        val riskLevel = when {
            riskScore >= 8 -> RiskLevel.VERY_HIGH
            riskScore >= 6 -> RiskLevel.HIGH
            riskScore >= 4 -> RiskLevel.MEDIUM
            riskScore >= 2 -> RiskLevel.LOW
            else -> RiskLevel.VERY_LOW
        }

        // 高风险时期
        val highRiskPeriods = mutableListOf<Period>()

        // 根据大运起始年计算
        val startYear = baziData.daYunStartYear
        if (startYear > 0) {
            highRiskPeriods.add(
                Period(
                    startYear = startYear + 5,
                    endYear = startYear + 7,
                    description = "第一个大运转换期"
                )
            )

            highRiskPeriods.add(
                Period(
                    startYear = startYear + 15,
                    endYear = startYear + 17,
                    description = "第二个大运转换期"
                )
            )
        }

        return HealthRisk(
            riskLevel = riskLevel,
            riskAreas = riskAreas.distinct(),
            highRiskPeriods = highRiskPeriods,
            score = riskScore
        )
    }

    // 7. 大运健康分析
    private fun analyzeGrandLuckHealth(baziData: BaziData): List<GrandLuckHealthAnalysis> {
        val analyses = mutableListOf<GrandLuckHealthAnalysis>()

        // 计算未来5步大运
        val startYear = baziData.daYunStartYear
        if (startYear <= 0) return analyses

        val currentYear = 2026  // 当前年份

        for (i in 0..4) {
            val daYunYear = startYear + i * 10

            if (daYunYear > currentYear + 50) break  // 最多预测50年

            val healthTrend = when (i) {
                0 -> if (baziData.daYunWeight > 0) HealthTrend.IMPROVING else HealthTrend.DECLINING
                1 -> HealthTrend.FLUCTUATING
                2 -> if (baziData.yongShenList.isNotEmpty()) HealthTrend.IMPROVING else HealthTrend.STABLE
                3 -> HealthTrend.CRITICAL
                4 -> HealthTrend.STABLE
                else -> HealthTrend.STABLE
            }

            val warnings = mutableListOf<String>()
            val affectedElements = mutableListOf<WuXing>()

            // 根据大运位置添加警告
            when (i) {
                0 -> {  // 第一步大运
                    warnings.add("体质适应期，注意气候变化")
                    warnings.add("可能出现水土不服")
                }

                2 -> {  // 第三步大运
                    warnings.add("中年转折期，需注意劳逸结合")
                    warnings.add("定期体检很重要")
                }

                3 -> {  // 第四步大运
                    warnings.add("健康关键期，尤其注意心脑血管")
                }
            }

            analyses.add(
                GrandLuckHealthAnalysis(
                    startYear = daYunYear,
                    celestial = getNextTiangan(baziData.monthTiangan, i, baziData.dayunForward),
                    terrestrial = getNextDizhi(baziData.monthDizhi, i, baziData.dayunForward),
                    luckPeriod = "${daYunYear}-${daYunYear + 9}年",
                    healthTrend = healthTrend,
                    warnings = warnings,
                    affectedElements = affectedElements
                )
            )
        }

        return analyses
    }

    // 8. 流年健康分析
    private fun analyzeFlowYearHealth(baziData: BaziData): FlowYearHealthAnalysis {
        val currentYear = 2026

        // 计算流年天干地支
        val flowYearTiangan = getYearTiangan(currentYear)
        val flowYearDizhi = getYearDizhi(currentYear)

        val conflicts = checkYearConflicts(baziData, flowYearDizhi)
        val healthImpact = if (conflicts.isNotEmpty()) {
            val maxSeverity = conflicts.maxOf { it.severity }
            when {
                maxSeverity >= 4 -> HealthImpact.DANGEROUS
                maxSeverity >= 3 -> HealthImpact.WARNING
                else -> HealthImpact.NEUTRAL
            }
        } else {
            HealthImpact.NEUTRAL
        }

        val criticalMonths = mutableListOf<Int>()

        // 检查与八字的刑冲
        val branches = listOf(
            baziData.yearDizhi,
            baziData.monthDizhi,
            baziData.dayDizhi,
            baziData.hourDizhi
        )

        branches.forEach { branch ->
            if (SIX_CLASHES[flowYearDizhi] == branch || SIX_CLASHES[branch] == flowYearDizhi) {
                criticalMonths.addAll(listOf(3, 6, 9, 12))  // 四季转换月
            }
        }

        // 计算五行变化
        val elementChanges = calculateElementChanges(baziData, flowYearTiangan, flowYearDizhi)

        return FlowYearHealthAnalysis(
            year = currentYear,
            celestial = flowYearTiangan,
            terrestrial = flowYearDizhi,
            healthImpact = healthImpact,
            criticalMonths = criticalMonths.distinct(),
            elementChanges = elementChanges
        )
    }

    // 9. 刑冲克泄分析
    private fun analyzeConflicts(baziData: BaziData): List<ConflictAnalysis> {
        val conflicts = mutableListOf<ConflictAnalysis>()

        val stems = listOf(
            baziData.yearTiangan,
            baziData.monthTiangan,
            baziData.dayTiangan,
            baziData.hourTiangan
        )

        val branches = listOf(
            baziData.yearDizhi,
            baziData.monthDizhi,
            baziData.dayDizhi,
            baziData.hourDizhi
        )

        // 检查地支相冲
        for (i in branches.indices) {
            for (j in i + 1 until branches.size) {
                if (SIX_CLASHES[branches[i]] == branches[j]) {
                    conflicts.add(
                        createConflictAnalysis(
                            type = ConflictType.CLASH,
                            sourceElement = BRANCH_TO_ELEMENT[branches[i]] ?: WuXing.WUXING_MU,
                            targetElement = BRANCH_TO_ELEMENT[branches[j]] ?: WuXing.WUXING_MU,
                            branches[i] to branches[j]
                        )
                    )
                }
            }
        }


        // 检查地支相刑
        THREE_PENALTIES.forEach { penaltySet ->

            val intersection = branches.filter { it in penaltySet }
            if (intersection.size >= 2) {
                intersection.forEach { branch ->
                    conflicts.add(
                        createConflictAnalysis(
                            type = ConflictType.PUNISHMENT,
                            sourceElement = BRANCH_TO_ELEMENT[branch] ?: WuXing.WUXING_MU,
                            targetElement = BRANCH_TO_ELEMENT[branch] ?: WuXing.WUXING_MU,
                            branch to null
                        )
                    )
                }
            }
        }

        // 检查天干相克
        for (i in stems.indices) {
            for (j in i + 1 until stems.size) {
                val sourceElement = STEM_TO_ELEMENT[stems[i]]
                val targetElement = STEM_TO_ELEMENT[stems[j]]

                if (sourceElement != null && targetElement != null) {
                    if (ELEMENT_OVERCOMING[sourceElement] == targetElement) {
                        conflicts.add(
                            createConflictAnalysis(
                                type = ConflictType.OVERCOME,
                                sourceElement = sourceElement,
                                targetElement = targetElement,
                                stems[i] to stems[j]
                            )
                        )
                    }
                }
            }
        }

        return conflicts
    }

    private fun createConflictAnalysis(
        type: ConflictType,
        sourceElement: WuXing,
        targetElement: WuXing,
        pair: Pair<Any, Any?>
    ): ConflictAnalysis {
        val sourceOrgans = ORGAN_MAPPING[sourceElement] ?: emptyList()
        val targetOrgans = ORGAN_MAPPING[targetElement] ?: emptyList()
        val affectedOrgans = (sourceOrgans + targetOrgans).distinctBy { it.name }

        val severity = when (type) {
            ConflictType.PUNISHMENT -> 4
            ConflictType.CLASH -> 5
            ConflictType.OVERCOME -> 3
            ConflictType.DRAIN -> 2
            ConflictType.HARM -> 3
        }

        val effectDescription = when (type) {
            ConflictType.PUNISHMENT -> "相刑关系，易导致对应器官功能紊乱"
            ConflictType.CLASH -> "相冲关系，易引发急性问题或波动"
            ConflictType.OVERCOME -> "相克关系，主克方受损，被克方受压"
            ConflictType.DRAIN -> "相泄关系，消耗主泄方能量"
            ConflictType.HARM -> "相害关系，暗藏隐患"
        }

        return ConflictAnalysis(
            type = type,
            sourceElement = sourceElement,
            targetElement = targetElement,
            affectedOrgans = affectedOrgans,
            severity = severity,
            effectDescription = effectDescription,
            period = null
        )
    }

    // 10. 生成健康建议
    private fun generateRecommendations(
        constitution: ConstitutionAnalysis,
        elementAnalysis: ElementAnalysis,
        healthRisk: HealthRisk,
        conflicts: List<ConflictAnalysis>
    ): List<String> {
        val recommendations = mutableListOf<String>()

        // 体质建议
        when (constitution.constitutionType) {
            "阳盛体质" -> {
                recommendations.add("宜多食滋阴降火食物，如梨、百合、银耳")
                recommendations.add("避免辛辣燥热食物，注意情绪调节")
            }

            "阴盛体质" -> {
                recommendations.add("宜多食温阳散寒食物，如姜、桂圆、羊肉")
                recommendations.add("注意保暖，适当运动增强阳气")
            }

            "阳虚体质" -> {
                recommendations.add("宜多食温补食物，如核桃、韭菜、虾")
                recommendations.add("避免生冷，适当晒太阳")
            }

            "阴虚体质" -> {
                recommendations.add("宜多食滋阴润燥食物，如芝麻、枸杞、鸭肉")
                recommendations.add("避免熬夜，保持充足睡眠")
            }
        }

        // 五行建议
        elementAnalysis.deficientElements.forEach { element ->
            when (element) {
                WuXing.WUXING_JIN -> {
                    recommendations.add("金弱，宜多食白色食物，如百合、白萝卜")
                    recommendations.add("注意呼吸系统保健，避免空气污染")
                }

                WuXing.WUXING_MU -> {
                    recommendations.add("木弱，宜多食绿色蔬菜，保持良好情绪")
                    recommendations.add("注意肝胆功能，避免过量饮酒")
                }

                WuXing.WUXING_SHUI -> {
                    recommendations.add("水弱，宜多食黑色食物，如黑豆、黑芝麻")
                    recommendations.add("注意肾脏保健，避免过度劳累")
                }

                WuXing.WUXING_HUO -> {
                    recommendations.add("火弱，宜多食红色食物，如红枣、枸杞")
                    recommendations.add("注意心血管健康，保持心态平和")
                }

                WuXing.WUXING_TU -> {
                    recommendations.add("土弱，宜多食黄色食物，如山药、小米")
                    recommendations.add("注意脾胃保养，饮食定时定量")
                }
            }
        }

        // 风险建议
        when (healthRisk.riskLevel) {
            RiskLevel.HIGH, RiskLevel.VERY_HIGH -> {
                recommendations.add("高风险体质，建议每年体检一次")
                recommendations.add("建立健康档案，定期监测身体状况")
            }

            RiskLevel.MEDIUM -> {
                recommendations.add("中等风险，建议每两年体检一次")
                recommendations.add("注意季节变化时的身体调节")
            }

            else -> {
                recommendations.add("保持良好生活习惯，定期锻炼")
            }
        }

        // 刑冲建议
        conflicts.forEach { conflict ->
            when (conflict.type) {
                ConflictType.CLASH -> {
                    recommendations.add("有相冲关系，注意避免剧烈情绪波动")
                }

                ConflictType.PUNISHMENT -> {
                    recommendations.add("有相刑关系，注意慢性病的预防和管理")
                }

                ConflictType.OVERCOME -> {
                    recommendations.add("有相克关系，注意平衡饮食结构")
                }

                else -> {}
            }
        }

        return recommendations.distinct().take(10)  // 最多10条建议
    }

    // 11. 工具函数
    private fun getNextTiangan(current: TianGan, steps: Int, isForward: Boolean): TianGan {
        val allTiangan = TianGan.values()
        val currentIndex = allTiangan.indexOf(current)
        var newIndex = if (isForward) {
            (currentIndex + steps) % 10
        } else {
            (currentIndex - steps + 10) % 10
        }
        if (newIndex < 0) newIndex += 10
        return allTiangan[newIndex]
    }

    private fun getNextDizhi(current: DiZhi, steps: Int, isForward: Boolean): DiZhi {
        val allDizhi = DiZhi.values()
        val currentIndex = allDizhi.indexOf(current)
        var newIndex = if (isForward) {
            (currentIndex + steps) % 12
        } else {
            (currentIndex - steps + 12) % 12
        }
        if (newIndex < 0) newIndex += 12
        return allDizhi[newIndex]
    }

    private fun getYearTiangan(year: Int): TianGan {
        val index = (year - 4) % 10
        return TianGan.values()[index]
    }

    private fun getYearDizhi(year: Int): DiZhi {
        val index = (year - 4) % 12
        return DiZhi.values()[index]
    }

    private fun checkYearConflicts(
        baziData: BaziData,
        flowYearDizhi: DiZhi
    ): List<ConflictAnalysis> {
        val conflicts = mutableListOf<ConflictAnalysis>()

        val branches = listOf(
            baziData.yearDizhi,
            baziData.monthDizhi,
            baziData.dayDizhi,
            baziData.hourDizhi
        )

        branches.forEach { branch ->
            if (SIX_CLASHES[flowYearDizhi] == branch) {
                conflicts.add(
                    createConflictAnalysis(
                        type = ConflictType.CLASH,
                        sourceElement = BRANCH_TO_ELEMENT[flowYearDizhi] ?: WuXing.WUXING_MU,
                        targetElement = BRANCH_TO_ELEMENT[branch] ?: WuXing.WUXING_MU,
                        flowYearDizhi to branch
                    )
                )
            }
        }

        return conflicts
    }

    private fun calculateElementChanges(
        baziData: BaziData,
        flowYearTiangan: TianGan,
        flowYearDizhi: DiZhi
    ): Map<WuXing, Float> {
        val changes = mutableMapOf<WuXing, Float>()

        val flowYearStemElement = STEM_TO_ELEMENT[flowYearTiangan]
        val flowYearBranchElement = BRANCH_TO_ELEMENT[flowYearDizhi]

        // 计算五行增强
        flowYearStemElement?.let { element ->
            changes[element] = 0.2f
        }

        flowYearBranchElement?.let { element ->
            changes[element] = (changes[element] ?: 0f) + 0.3f
        }

        return changes
    }
}

@Composable
fun BaziDisplayScreen(baziData: BaziData) {
    Column {
        Text(
            text = BaziFormatter.formatBaziSingleLine(baziData),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = BaziFormatter.formatBaziWithWuXing(baziData),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
//
//        Spacer(modifier = Modifier.height(16.dp))

//        // 五行权重显示
//        Card {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Text(
//                    text = "五行权重分布",
//                    style = MaterialTheme.typography.titleMedium
//                )
//                Text(
//                    text = BaziFormatter.formatWuXingWeights(baziData)
//                )
//            }
//        }
    }
}

// 12. UI组件 - 健康报告界面
@Composable
fun BaziHealthReportScreen(baziData: BaziData,modifier: Modifier) {
    val analyzer = remember { BaziHealthAnalyzer() }
    val healthData = remember(baziData) { analyzer.analyzeHealth(baziData) }

//    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // 报告标题
        Text(
            text = "八字健康分析报告",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2196F3),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "分析时间: ${SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date())}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Text(
            text = BaziFormatter.formatBaziWithWuXing(baziData),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

//        BaziDisplayScreen(baziData)

        // 1. 先天体质分析
        ConstitutionCard(healthData.constitutionAnalysis, healthData.healthRisk)

        Spacer(modifier = Modifier.height(16.dp))

        // 2. 五行平衡分析
        ElementBalanceCard(healthData.elementAnalysis)

        Spacer(modifier = Modifier.height(16.dp))

//        // 3. 健康风险评估
//        HealthRiskCard(healthData.healthRisk)
//
//        Spacer(modifier = Modifier.height(16.dp))

//        // 4. 大运健康趋势
//        GrandLuckCard(healthData.grandLuckAnalysis)
//
//        Spacer(modifier = Modifier.height(16.dp))

//        // 5. 当前流年分析
//        FlowYearCard(healthData.flowYearAnalysis)
//
//        Spacer(modifier = Modifier.height(16.dp))

//        // 6. 刑冲克泄分析
//        ConflictAnalysisCard(healthData.conflicts)
//
//        Spacer(modifier = Modifier.height(16.dp))

//        // 7. 健康建议
//        HealthRecommendationsCard(healthData.recommendations)

        Spacer(modifier = Modifier.height(16.dp))

        GrandLuckHealthDisplay(baziData, modifier)

        Spacer(modifier = Modifier.height(16.dp))

        // 免责声明
        DisclaimerCard()
    }
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun ConstitutionCard(constitution: ConstitutionAnalysis, healthRisk: HealthRisk) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE8F5E9)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "先天体质分析",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Chip(
                    onClick = {},
                    colors = ChipDefaults.chipColors(
                        backgroundColor = when (constitution.constitutionType) {
                            "平和体质" -> Color(0xFF4CAF50)
                            "阳虚体质" -> Color(0xFFFFC107)
                            "阴虚体质" -> Color(0xFFFF9800)
                            "阳盛体质" -> Color(0xFFF44336)
                            "阴盛体质" -> Color(0xFF9C27B0)
                            else -> Color.Gray
                        }
                    ),
                    shape = RoundedCornerShape(16.dp),
                    label = {

                        Text(
                            text = constitution.constitutionType,
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = constitution.description,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (constitution.strongOrgans.isNotEmpty()) {
                Text(
                    text = "强健器官:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    constitution.strongOrgans.forEach { organ ->
                        Chip(
                            onClick = {},
                            colors = ChipDefaults.chipColors(
                                backgroundColor = Color(0xFFC8E6C9)
                            ),
                            label = {
                                Text(text = organ.name)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (constitution.weakOrgans.isNotEmpty()) {
                Text(
                    text = "薄弱器官:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    constitution.weakOrgans.forEach { organ ->
                        Chip(
                            onClick = {},
                            colors = ChipDefaults.chipColors(
                                backgroundColor = Color(0xFFFFCDD2),

                                ),
                            label = {
                                Text(organ.name)
                            }
                        )
                    }
                }
            }

            if (healthRisk.riskAreas.isNotEmpty()) {
                Text(
                    text = "需关注部位:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    healthRisk.riskAreas.forEach { area ->
                        Chip(
                            onClick = {},
                            colors = ChipDefaults.chipColors(
                                backgroundColor = Color(0xFFFFEB3B)
                            ),
                            label = {
                                Text(area, fontSize = 12.sp)
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun ElementBalanceCard(elementAnalysis: ElementAnalysis) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "五行平衡分析",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // 五行权重图
            ElementWeightChart(elementAnalysis.elementBalances)

            Spacer(modifier = Modifier.height(12.dp))

            if (elementAnalysis.excessElements.isNotEmpty()) {
                Text(
                    text = "五行过旺:",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Red
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    elementAnalysis.excessElements.forEach { element ->
                        Chip(
                            onClick = {},
                            colors = ChipDefaults.chipColors(
                                backgroundColor = Color(0xFFFFCDD2),
                            ),
                            label = {
                                Text(text = getElementName(element))
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (elementAnalysis.deficientElements.isNotEmpty()) {
                Text(
                    text = "五行不足:",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Blue
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    elementAnalysis.deficientElements.forEach { element ->
                        Chip(
                            onClick = {},
                            colors = ChipDefaults.chipColors(

                            ),
                            label = {
                                Text(getElementName(element))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ElementWeightChart(balances: Map<WuXing, ElementBalance>) {
    val maxWeight = balances.values.maxOfOrNull { it.weight } ?: 1f

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        balances.values.sortedByDescending { it.weight }.forEach { balance ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = getElementName(balance.element),
                    modifier = Modifier.width(40.dp),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.width(8.dp))

                // 进度条
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFE0E0E0))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
//                            .width((balance.weight / maxWeight).coerceIn(0f, 1f).dp)
                            .fillMaxWidth((balance.weight / maxWeight).coerceIn(0f, 1f))
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                when (balance.status) {
                                    ElementStatus.EXCESS -> Color(0xFFFF5252)
                                    ElementStatus.VERY_DEFICIENT -> Color(0xFF2196F3)
                                    ElementStatus.DEFICIENT -> Color(0xFF64B5F6)
                                    else -> Color(0xFF4CAF50)
                                }
                            )
                    )

                    Text(
                        text = String.format("%.2f", balance.weight),
                        fontSize = 12.sp,
//                        color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = when (balance.status) {
                        ElementStatus.EXCESS -> "过旺"
                        ElementStatus.BALANCED -> "平衡"
                        ElementStatus.DEFICIENT -> "不足"
                        ElementStatus.VERY_DEFICIENT -> "严重不足"
                    },
                    fontSize = 12.sp,
                    modifier = Modifier.width(60.dp)
                )
            }
        }
    }
}

fun getElementName(element: WuXing): String {
    return when (element) {
        WuXing.WUXING_JIN -> "金"
        WuXing.WUXING_MU -> "木"
        WuXing.WUXING_SHUI -> "水"
        WuXing.WUXING_HUO -> "火"
        WuXing.WUXING_TU -> "土"
    }
}

@Composable
fun HealthRiskCard(healthRisk: HealthRisk) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (healthRisk.riskLevel) {
                RiskLevel.VERY_HIGH -> Color(0xFFFFEBEE)
                RiskLevel.HIGH -> Color(0xFFFFF3E0)
                RiskLevel.MEDIUM -> Color(0xFFFFF9C4)
                RiskLevel.LOW -> Color(0xFFE8F5E9)
                RiskLevel.VERY_LOW -> Color(0xFFE3F2FD)
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "健康风险评估",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .background(
                            color = when (healthRisk.riskLevel) {
                                RiskLevel.VERY_HIGH -> Red
                                RiskLevel.HIGH -> Color(0xFFFF9800)
                                RiskLevel.MEDIUM -> Color(0xFFFFC107)
                                RiskLevel.LOW -> Color(0xFF4CAF50)
                                RiskLevel.VERY_LOW -> Color(0xFF2196F3)
                            },
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = when (healthRisk.riskLevel) {
                            RiskLevel.VERY_HIGH -> "很高"
                            RiskLevel.HIGH -> "高"
                            RiskLevel.MEDIUM -> "中"
                            RiskLevel.LOW -> "低"
                            RiskLevel.VERY_LOW -> "很低"
                        },
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 风险评分
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("风险评分: ", fontWeight = FontWeight.Medium)
                Text("${healthRisk.score}/10")

                Spacer(modifier = Modifier.weight(1f))

                // 风险指示器
                LinearProgressIndicator(
                    progress = healthRisk.score / 10f,
                    modifier = Modifier
                        .width(120.dp)
                        .height(8.dp),
                    color = when (healthRisk.riskLevel) {
                        RiskLevel.VERY_HIGH, RiskLevel.HIGH -> Red
                        RiskLevel.MEDIUM -> Color(0xFFFF9800)
                        else -> Color(0xFF4CAF50)
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (healthRisk.riskAreas.isNotEmpty()) {
                Text(
                    text = "需关注部位:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    healthRisk.riskAreas.forEach { area ->
                        Chip(
                            onClick = {},
                            colors = ChipDefaults.chipColors(
                                backgroundColor = Color(0xFFFFEB3B)
                            ),
                            label = {
                                Text(area, fontSize = 12.sp)
                            }
                        )
                    }
                }
            }

            if (healthRisk.highRiskPeriods.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "高风险时期:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                healthRisk.highRiskPeriods.forEach { period ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("${period.startYear}-${period.endYear}年")
                            Text(period.description, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GrandLuckCard(grandLuckAnalysis: List<GrandLuckHealthAnalysis>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "大运健康趋势",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyColumn(
                modifier = Modifier.height(300.dp)
            ) {
                items(grandLuckAnalysis) { analysis ->
                    GrandLuckItem(analysis)
                }
            }
        }
    }
}

@Composable
fun GrandLuckItem(analysis: GrandLuckHealthAnalysis) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (analysis.healthTrend) {
                HealthTrend.IMPROVING -> Color(0xFFE8F5E9)
                HealthTrend.STABLE -> Color(0xFFE3F2FD)
                HealthTrend.DECLINING -> Color(0xFFFFF3E0)
                HealthTrend.FLUCTUATING -> Color(0xFFFFF8E1)
                HealthTrend.CRITICAL -> Color(0xFFFFEBEE)
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = analysis.luckPeriod,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                Chip(
                    onClick = {},
                    colors = ChipDefaults.chipColors(
                        backgroundColor = when (analysis.healthTrend) {
                            HealthTrend.IMPROVING -> Color(0xFF4CAF50)
                            HealthTrend.STABLE -> Color(0xFF2196F3)
                            HealthTrend.DECLINING -> Color(0xFFFF9800)
                            HealthTrend.FLUCTUATING -> Color(0xFFFFC107)
                            HealthTrend.CRITICAL -> Color(0xFFF44336)
                        }
                    ),
                    shape = RoundedCornerShape(12.dp),
                    label = {
                        Text(
                            text = when (analysis.healthTrend) {
                                HealthTrend.IMPROVING -> "改善"
                                HealthTrend.STABLE -> "稳定"
                                HealthTrend.DECLINING -> "下降"
                                HealthTrend.FLUCTUATING -> "波动"
                                HealthTrend.CRITICAL -> "关键"
                            },
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    },

                    )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "大运: ${getTianganName(analysis.celestial)}${getDizhiName(analysis.terrestrial)}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            if (analysis.warnings.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                analysis.warnings.forEach { warning ->
                    Text(
                        text = "⚠ $warning",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFFF9800)
                    )
                }
            }
        }
    }
}

fun getTianganName(tiangan: TianGan): String {
    return when (tiangan) {
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
}

fun getDizhiName(dizhi: DiZhi): String {
    return when (dizhi) {
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

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun FlowYearCard(flowYearAnalysis: FlowYearHealthAnalysis) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (flowYearAnalysis.healthImpact) {
                HealthImpact.DANGEROUS -> Color(0xFFFFEBEE)
                HealthImpact.WARNING -> Color(0xFFFFF3E0)
                HealthImpact.NEUTRAL -> Color(0xFFE8F5E9)
                HealthImpact.POSITIVE -> Color(0xFFE3F2FD)
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "当前流年分析 (${flowYearAnalysis.year}年)",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${getTianganName(flowYearAnalysis.celestial)}${
                        getDizhiName(
                            flowYearAnalysis.terrestrial
                        )
                    }",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2196F3)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "健康影响: ",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = when (flowYearAnalysis.healthImpact) {
                        HealthImpact.DANGEROUS -> "较高风险"
                        HealthImpact.WARNING -> "需警惕"
                        HealthImpact.NEUTRAL -> "平稳"
                        HealthImpact.POSITIVE -> "有利"
                    },
                    color = when (flowYearAnalysis.healthImpact) {
                        HealthImpact.DANGEROUS -> Red
                        HealthImpact.WARNING -> Color(0xFFFF9800)
                        HealthImpact.NEUTRAL -> Color.Gray
                        HealthImpact.POSITIVE -> Color.Green
                    }
                )
            }

            if (flowYearAnalysis.criticalMonths.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "需关注月份:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    flowYearAnalysis.criticalMonths.forEach { month ->
                        Chip(
                            onClick = {},
                            colors = ChipDefaults.chipColors(
                                backgroundColor = Color(0xFFFFCDD2)
                            ),
                            label = {
                                Text(text = "${month}月")
                            }
                        )
                    }
                }
            }

            if (flowYearAnalysis.elementChanges.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "五行变化:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    flowYearAnalysis.elementChanges.forEach { (element, change) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(getElementName(element))
                            Text(
                                text = if (change > 0) "+${String.format("%.0f", change * 100)}%"
                                else "${String.format("%.0f", change * 100)}%",
                                color = if (change > 0) Color.Green else Red
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConflictAnalysisCard(conflicts: List<ConflictAnalysis>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "刑冲克泄分析",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Badge(
                    modifier = Modifier.background(
                        color = if (conflicts.isNotEmpty()) Red else Color.Green,
                        shape = RoundedCornerShape(12.dp)
                    )
                ) {
                    Text(
                        text = if (conflicts.isNotEmpty()) "${conflicts.size}项" else "无",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (conflicts.isEmpty()) {
                Text(
                    text = "✅ 八字中无明显刑冲克泄关系，先天格局较好",
                    color = Color(0xFF4CAF50)
                )
            } else {
                conflicts.sortedByDescending { it.severity }.forEachIndexed { index, conflict ->
                    ConflictItem(conflict, index)
                }
            }
        }
    }
}

@Composable
fun ConflictItem(conflict: ConflictAnalysis, index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (conflict.severity) {
                5 -> Color(0xFFFFEBEE)
                4 -> Color(0xFFFFF3E0)
                3 -> Color(0xFFFFF9C4)
                else -> Color(0xFFE3F2FD)
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            color = when (conflict.severity) {
                                5 -> Red
                                4 -> Color(0xFFFF9800)
                                3 -> Color(0xFFFFC107)
                                else -> Color(0xFF9E9E9E)
                            },
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index + 1}",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = when (conflict.type) {
                                ConflictType.PUNISHMENT -> "刑"
                                ConflictType.CLASH -> "冲"
                                ConflictType.OVERCOME -> "克"
                                ConflictType.DRAIN -> "泄"
                                ConflictType.HARM -> "害"
                            },
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 8.dp)
                        )

                        Text("${getElementName(conflict.sourceElement)}→${getElementName(conflict.targetElement)}")
                    }

                    Text(
                        text = conflict.effectDescription,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    if (conflict.affectedOrgans.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "影响: ${conflict.affectedOrgans.joinToString { it.name }}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF757575)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HealthRecommendationsCard(recommendations: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE3F2FD)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "健康调理建议",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "建议",
                    tint = Color(0xFF2196F3)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.height(300.dp)
            ) {
                items(recommendations) { recommendation ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF4CAF50),
                                modifier = Modifier.padding(end = 12.dp)
                            )

                            Text(
                                text = recommendation,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DisclaimerCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF3E0)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "免责声明",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9800)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "本分析基于传统八字理论，仅供文化参考和健康意识提升。结果不能替代专业医疗诊断，如有健康问题请及时就医。预测结果会随时间变化，建议定期关注身体状况。",
                style = MaterialTheme.typography.bodySmall,
                lineHeight = 16.sp
            )
        }
    }
}

//// 13. 主调用函数
//@Composable
//fun BaziHealthReportView(baziData: BaziData) {
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = Color(0xFFF5F5F5)
//    ) {
//        BaziHealthReportScreen(baziData)
//    }
//}

