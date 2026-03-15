package com.rick.bazi.util

import com.rick.bazi.data.BaziData
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.WuXing

object BaziFormatter {

    /**
     * 生成单行八字信息
     * 格式：年柱 月柱 日柱 时柱
     * 示例：甲子 丙寅 戊辰 庚申
     */
    fun formatBaziSingleLine(baziData: BaziData): String {
        val yearPillar = formatTianganDizhi(baziData.yearTiangan, baziData.yearDizhi)
        val monthPillar = formatTianganDizhi(baziData.monthTiangan, baziData.monthDizhi)
        val dayPillar = formatTianganDizhi(baziData.dayTiangan, baziData.dayDizhi)
        val hourPillar = formatTianganDizhi(baziData.hourTiangan, baziData.hourDizhi)

        return "$yearPillar $monthPillar $dayPillar $hourPillar"
    }

    /**
     * 生成带有五行信息的单行八字
     * 格式：年柱(五行) 月柱(五行) 日柱(五行) 时柱(五行)
     * 示例：甲子(木水) 丙寅(火木) 戊辰(土土) 庚申(金金)
     */
    fun formatBaziWithWuXing(baziData: BaziData): String {
        val yearPillar = formatTianganDizhiWithWuxing(baziData.yearTiangan, baziData.yearDizhi)
        val monthPillar = formatTianganDizhiWithWuxing(baziData.monthTiangan, baziData.monthDizhi)
        val dayPillar = formatTianganDizhiWithWuxing(baziData.dayTiangan, baziData.dayDizhi)
        val hourPillar = formatTianganDizhiWithWuxing(baziData.hourTiangan, baziData.hourDizhi)

        return "$yearPillar $monthPillar $dayPillar $hourPillar"
    }

    /**
     * 生成带有藏干的单行八字
     * 格式：年柱[藏干] 月柱[藏干] 日柱[藏干] 时柱[藏干]
     * 示例：甲子[癸] 丙寅[甲丙戊] 戊辰[乙戊癸] 庚申[戊庚壬]
     */
    fun formatBaziWithCangGan(baziData: BaziData): String {
        val yearPillar = formatTianganDizhiWithCangGan(baziData.yearTiangan, baziData.yearDizhi)
        val monthPillar = formatTianganDizhiWithCangGan(baziData.monthTiangan, baziData.monthDizhi)
        val dayPillar = formatTianganDizhiWithCangGan(baziData.dayTiangan, baziData.dayDizhi)
        val hourPillar = formatTianganDizhiWithCangGan(baziData.hourTiangan, baziData.hourDizhi)

        return "$yearPillar $monthPillar $dayPillar $hourPillar"
    }

    /**
     * 生成天干地支组合字符串
     */
    private fun formatTianganDizhi(tiangan: TianGan, dizhi: DiZhi): String {
        val tianganChar = convertTianganToChar(tiangan)
        val dizhiChar = convertDizhiToChar(dizhi)
        return "$tianganChar$dizhiChar"
    }

    /**
     * 生成天干地支+五行组合字符串
     */
    private fun formatTianganDizhiWithWuxing(tiangan: TianGan, dizhi: DiZhi): String {
        val pillar = formatTianganDizhi(tiangan, dizhi)
        val tianganWuxing = getTianganWuXing(tiangan)
        val dizhiWuxing = getDizhiWuXing(dizhi)
        return "$pillar(${tianganWuxing}${dizhiWuxing})"
    }

    /**
     * 生成天干地支+藏干组合字符串
     */
    private fun formatTianganDizhiWithCangGan(tiangan: TianGan, dizhi: DiZhi): String {
        val pillar = formatTianganDizhi(tiangan, dizhi)
        val cangGan = getCangGanFromDizhi(dizhi)
        return if (cangGan.isNotEmpty()) {
            "$pillar[$cangGan]"
        } else {
            pillar
        }
    }

    /**
     * 天干转字符
     */
    fun convertTianganToChar(tiangan: TianGan): String {
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

    /**
     * 地支转字符
     */
    fun convertDizhiToChar(dizhi: DiZhi): String {
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

    /**
     * 获取天干的五行
     */
    fun getTianganWuXing(tiangan: TianGan): String {
        return when (tiangan) {
            TianGan.TIANGAN_JIA, TianGan.TIANGAN_YI -> "木"
            TianGan.TIANGAN_BING, TianGan.TIANGAN_DING -> "火"
            TianGan.TIANGAN_WU, TianGan.TIANGAN_JI -> "土"
            TianGan.TIANGAN_GENG, TianGan.TIANGAN_XIN -> "金"
            TianGan.TIANGAN_REN, TianGan.TIANGAN_GUI -> "水"
        }
    }

    /**
     * 获取地支的五行
     */
    fun getDizhiWuXing(dizhi: DiZhi): String {
        return when (dizhi) {
            DiZhi.DIZHI_ZI, DiZhi.DIZHI_HAI -> "水"
            DiZhi.DIZHI_CHOU, DiZhi.DIZHI_CHEN,
            DiZhi.DIZHI_XU, DiZhi.DIZHI_WEI -> "土"
            DiZhi.DIZHI_YIN, DiZhi.DIZHI_MOU -> "木"
            DiZhi.DIZHI_SI, DiZhi.DIZHI_WU -> "火"
            DiZhi.DIZHI_SHEN, DiZhi.DIZHI_YOU -> "金"
        }
    }

    /**
     * 获取地支对应的藏干
     */
    fun getCangGanFromDizhi(dizhi: DiZhi): String {
        return when (dizhi) {
            DiZhi.DIZHI_ZI -> "癸"  // 子中藏癸
            DiZhi.DIZHI_CHOU -> "己癸辛"  // 丑中藏己癸辛
            DiZhi.DIZHI_YIN -> "甲丙戊"  // 寅中藏甲丙戊
            DiZhi.DIZHI_MOU -> "乙"  // 卯中藏乙
            DiZhi.DIZHI_CHEN -> "乙戊癸"  // 辰中藏乙戊癸
            DiZhi.DIZHI_SI -> "丙庚戊"  // 巳中藏丙庚戊
            DiZhi.DIZHI_WU -> "丁己"  // 午中藏丁己
            DiZhi.DIZHI_WEI -> "己丁乙"  // 未中藏己丁乙
            DiZhi.DIZHI_SHEN -> "庚壬戊"  // 申中藏庚壬戊
            DiZhi.DIZHI_YOU -> "辛"  // 酉中藏辛
            DiZhi.DIZHI_XU -> "辛丁戊"  // 戌中藏辛丁戊
            DiZhi.DIZHI_HAI -> "壬甲"  // 亥中藏壬甲
        }
    }

    /**
     * 生成详细的八字信息（包含大运）
     * 格式：八字：甲子 丙寅 戊辰 庚申 | 大运：丁卯(7岁) 戊辰(17岁) 己巳(27岁) ...
     */
    fun formatBaziWithGrandLuck(baziData: BaziData, grandLuck: List<String> = emptyList()): String {
        val baziStr = formatBaziSingleLine(baziData)

        return if (grandLuck.isNotEmpty()) {
            val grandLuckStr = grandLuck.joinToString(" ")
            "八字：$baziStr | 大运：$grandLuckStr"
        } else {
            "八字：$baziStr"
        }
    }

    /**
     * 生成五行权重分布信息
     */
    fun formatWuXingWeights(baziData: BaziData): String {
        return "金:${String.format("%.2f", baziData.jinWeight)} " +
                "木:${String.format("%.2f", baziData.muWeight)} " +
                "水:${String.format("%.2f", baziData.shuiWeight)} " +
                "火:${String.format("%.2f", baziData.huoWeight)} " +
                "土:${String.format("%.2f", baziData.tuWeight)}"
    }

    /**
     * 生成十神权重分布信息
     */
    fun formatShiShenWeights(baziData: BaziData): String {
        return "比劫:${String.format("%.2f", baziData.bijieWeight)} " +
                "食伤:${String.format("%.2f", baziData.shishangWeight)} " +
                "财星:${String.format("%.2f", baziData.caiWeight)} " +
                "官杀:${String.format("%.2f", baziData.guanshaWeight)} " +
                "印枭:${String.format("%.2f", baziData.yinWeight)}"
    }

    /**
     * 生成完整的八字摘要信息
     */
    fun formatBaziSummary(baziData: BaziData): String {
        val baziStr = formatBaziSingleLine(baziData)
        val wuxingStr = formatWuXingWeights(baziData)
        val shishenStr = formatShiShenWeights(baziData)
        val genderStr = if (baziData.gender == "Male") "男" else "女"
        val dayunAge = baziData.daYunStartYear

        return buildString {
            appendLine("八字：$baziStr")
            appendLine("性别：$genderStr")
            appendLine("五行分布：$wuxingStr")
            appendLine("十神分布：$shishenStr")
            if (dayunAge > 0) {
                appendLine("起运：$dayunAge 岁")
            }
            if (baziData.yongShenList.isNotEmpty()) {
                val yongshenStr = baziData.yongShenList.joinToString { it.name }
                appendLine("用神：$yongshenStr")
            }
            if (baziData.jiShenList.isNotEmpty()) {
                val jishenStr = baziData.jiShenList.joinToString { it.name }
                appendLine("忌神：$jishenStr")
            }
        }
    }
}

/**
 * 直接在BaziData中扩展函数
 */
fun BaziData.toSingleLineString(): String {
    return BaziFormatter.formatBaziSingleLine(this)
}

fun BaziData.toWuXingString(): String {
    return BaziFormatter.formatBaziWithWuXing(this)
}

fun BaziData.toSummaryString(): String {
    return BaziFormatter.formatBaziSummary(this)
}