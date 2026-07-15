package com.rick.bazi.model

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
    val details: List<ScoreDetail>
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
data class BaZiData(
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