package com.rick.bazi.model

import com.rick.bazi.data.WuXing

// 评分维度
enum class ScoreDimension(val displayName: String, val icon: String) {
    LUCK("运势", "\uD83C\uDF1F"),

//    HEALTH("健康", "\uD83C\uDF1F"),
//    WEALTH("财富", "\uD83D\uDCB0"),
//    CAREER("事业", "\uD83D\uDCBC"),
//    FAMILY("家庭", "\uD83C\uDFE0"),
//    LOVE("爱情", "\u2764\uFE0F")
}

// 评分详情项
data class ScoreDetail(
    val dimension: ScoreDimension,
    val score: Float, // 0-100
    val logicExplanation: String // 计算逻辑说明
)

// 大运数据
data class DaYun(
    val yearRange: String, // 例如 "2020-2029"
    val startAge: Int,
    val ganZhi: String, // 干支
    val overallScore: Float, // 综合评分 0-100
    val details: List<ScoreDetail>,
    val logMessages : List<String>, // 日志信息
)

// 流年数据
data class LiuNian(
    val year: Int,
    val ganZhi: String,
    val overallScore: Float,
    val details: List<ScoreDetail>
)

// 流月数据
data class LiuYue(
    val month: Int,
    val monthName: String,
    val ganZhi: String,
    val overallScore: Float,
    val details: List<ScoreDetail>
)

// 用户八字数据
data class BaziDaYunLiuNianLiuYueData(
    val name: String,
    val birthDate: String,
    val yearGanZhi: String,
    val monthGanZhi: String,
    val dayGanZhi: String,
    val hourGanZhi: String,
    val dayMaster: String, // 日主
    val daYunList: List<DaYun>,
    val currentDaYunIndex: Int = 0,
    val currentLiuNianIndex: Int = 0
)

data class MarriageDaYunInfo(
    val daYunRange: String,   // "2028–2037"
    val ganZhi: String,       // "庚辰"
    val spouseStar: String,   // "偏财(庚)" / "七杀(庚)"
    val spousePalaceEffect: String, // "合入夫妻宫" / "生扶夫妻宫"
    val yinyuanLevel: String, // "极佳" / "良好" / "一般"
    val desc: String
)

data class PeachBlossomYear(
    val year: Int,
    val ganZhi: String,
    val peachFlower: String,  // "卯(桃花)"
    val strongReason: String, // "桃花+财星同现" / "桃花合日支"
    val desc: String
)

/**
 * 日主强度数据类
 */
data class DayMasterStrength(
    val wuxing: WuXing,
    val score: Int,
    val isSeasonStrong: Boolean,
    val rootCount: Int,
    val supportWeight: Float,
    val strengthLevel: StrengthLevel
)

enum class StrengthLevel {
    VERY_WEAK,   // 很弱
    WEAK,        // 弱
    MEDIUM,      // 中等
    STRONG,      // 强
    VERY_STRONG  // 很强
}

/**
 * 用神、喜神、忌神计算结果
 */
data class YongXiJiResult(
    val yongShen: WuXing,                   // 用神（主）
    val yongShenName: String,               // 用神名称
    val yongShenReason: String,             // 选择理由
    val xiShen: WuXing?,                    // 喜神（生用神者）
    val xiShenName: String,                 // 喜神名称
    val jiShenList: List<WuXing>,           // 忌神列表
    val jiShenNames: List<String>,          // 忌神名称列表
    val tiaoHouShen: WuXing?,              // 调候用神
    val tongGuanShen: WuXing?,             // 通关用神
    val dayMasterPercent: Float,            // 日主占比%
    val strengthLevel: StrengthLevel,       // 旺衰等级
    val patternType: PatternType            // 格局类型
)

/**
 * 格局类型枚举
 */
enum class PatternType(val displayName: String) {
    ZHENG_GE("正格"),
    ZHUAN_WANG("专旺格"),
    CONG_GE("从格"),
    JIAN_LU("建禄格"),
    YUE_REN("月刃格"),
    HUA_QI("化气格")
}