package com.rick.bazi.model

//package com.example.bazipan.model

/** 天干十神（以日干为基准） */
val SHI_SHEN_MAP = mapOf(
    "比肩" to "比肩", "劫财" to "劫财",
    "食神" to "食神", "伤官" to "伤官",
    "偏财" to "偏财", "正财" to "正财",
    "七杀" to "七杀", "正官" to "正官",
    "偏印" to "偏印", "正印" to "正印"
)

/** 地支藏干表：本气/中气/余气 + 对应五行 */
data class HiddenGan(
    var gan: String,
    var shiShen: String   // 相对于日主的十神
)

data class Pillar(
    var label: String,      // "年柱" "月柱" "日柱" "时柱"
    var tianGan: String,   // 甲～癸
    var ganShiShen: String,// 天干十神（日柱写"日主"）
    var diZhi: String,     // 子～亥
    var hiddenGans: List<HiddenGan>
)

data class BaZiDataMock(
    var name: String = "张三",
    var gender: String = "男",
    var birthTime: String = "1990年 01月 15日 08:30（公历）",
    var dayMaster: String = "甲",  // 日干
    var pillars: List<Pillar>,
    var currentDaYun: String = "庚辰大运 (28–37岁)",
    var currentLiuNian: String = "2026 丙午年"
)