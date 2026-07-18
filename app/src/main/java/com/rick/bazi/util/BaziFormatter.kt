package com.rick.bazi.util

import com.rick.bazi.data.BaziData
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.ShiShen
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.WuXing
import com.rick.bazi.model.HiddenGan
import com.rick.bazi.util.WuXingExt.isKe
import com.rick.bazi.util.WuXingExt.isSheng
import java.time.LocalDate
import java.time.Year

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
    fun formatTianganDizhi(tiangan: TianGan, dizhi: DiZhi): String {
        val tianganChar = convertTianganToChar(tiangan)
        val dizhiChar = convertDizhiToChar(dizhi)
        return "$tianganChar$dizhiChar"
    }

    /**
     * 根据 BaziData 中的出生日期和时间，生成格式化字符串
     * 格式："1990年1月15日 08:30"
     */
    fun formatBirthDateTime(data: BaziData): String {
        val year = data.birthDateYear
        val month = data.birthDateMonth
        val day = data.birthDateDay
        val hour = data.birthHour
        val minute = data.birthMinute

        return String.format("%04d年%d月%d日 %02d:%02d", year, month, day, hour, minute)
    }

    fun formatGender(data: BaziData): String {
        return if (data.gender == "Male") "男" else "女"
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

    fun formatTianGanWithWuxing(tiangan: TianGan): String{
        val tianganChar = convertTianganToChar(tiangan)
        val tianganWuxing = getTianganWuXing(tiangan)
        return "$tianganChar(${tianganWuxing})"
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

    /**
     * 根据日主天干和目标天干，返回十神的枚举类型
     * @param dayMaster 日主天干（日柱的天干）
     * @param targetGan 需要转换的目标天干
     * @return 对应的十神枚举
     */
    fun convertToShiShen(dayMaster: TianGan, targetGan: TianGan): ShiShen {
        // 计算天干的阴阳属性
        val dayMasterIndex = dayMaster.ordinal
        val targetIndex = targetGan.ordinal

        // 计算五行关系
        // 天干五行：甲乙(0,1)=木, 丙丁(2,3)=火, 戊己(4,5)=土, 庚辛(6,7)=金, 壬癸(8,9)=水
        val dayMasterWuXing = dayMasterIndex / 2
        val targetWuXing = targetIndex / 2

        // 判断阴阳是否相同（阳干：甲丙戊庚壬，索引为偶数；阴干：乙丁己辛癸，索引为奇数）
        val dayMasterIsYang = dayMasterIndex % 2 == 0
        val targetIsYang = targetIndex % 2 == 0

        // 五行生克关系（0=木, 1=火, 2=土, 3=金, 4=水）
        // 我生者：木生火(0→1)，火生土(1→2)，土生金(2→3)，金生水(3→4)，水生木(4→0)
        val shengRelation = (targetWuXing - dayMasterWuXing + 5) % 5
        // 我克者：木克土(0→2)，火克金(1→3)，土克水(2→4)，金克木(3→0)，水克火(4→1)
        val keRelation = (targetWuXing - dayMasterWuXing + 5) % 5

        return when {
            // 比肩/劫财：五行相同
            targetWuXing == dayMasterWuXing -> {
                if (dayMasterIsYang == targetIsYang) ShiShen.SHISHEN_BI_JIAN
                else ShiShen.SHISHEN_JIE_CAI
            }
            // 食神/伤官：我生者（日主生目标）
            shengRelation == 1 -> {
                if (dayMasterIsYang == targetIsYang) ShiShen.SHISHEN_SHI_SHEN
                else ShiShen.SHISHEN_SHANG_GUAN
            }
            // 正财/偏财：我克者（日主克目标）
            keRelation == 2 -> {
                if (dayMasterIsYang == targetIsYang) ShiShen.SHISHEN_ZHENG_CAI
                else ShiShen.SHISHEN_PIAN_CAI
            }
            // 正官/七杀：克我者（目标克日主）
            keRelation == 3 -> {
                if (dayMasterIsYang == targetIsYang) ShiShen.SHISHEN_QI_SHA
                else ShiShen.SHISHEN_ZHENG_GUAN
            }
            // 正印/偏印：生我者（目标生日主）
            shengRelation == 4 -> {
                if (dayMasterIsYang == targetIsYang) ShiShen.SHISHEN_PIAN_YIN
                else ShiShen.SHISHEN_ZHENG_YIN
            }
            else -> throw IllegalArgumentException("无法计算十神关系")
        }
    }

    /**
     * 根据日主天干和目标天干，返回十神的中文名称
     * @param dayMaster 日主天干（日柱的天干）
     * @param targetGan 需要转换的目标天干
     * @return 十神的中文名称
     */
    fun convertToShiShenCn(dayMaster: TianGan, targetGan: TianGan): String {
//        val shiShen = convertToShiShen(dayMaster, targetGan)

        val shiShen = ShiShenUtil().getShiShen(targetGan,dayMaster)
        return when (shiShen) {
            ShiShen.SHISHEN_BI_JIAN -> "比肩"
            ShiShen.SHISHEN_JIE_CAI -> "劫财"
            ShiShen.SHISHEN_ZHENG_YIN -> "正印"
            ShiShen.SHISHEN_PIAN_YIN -> "偏印"
            ShiShen.SHISHEN_SHI_SHEN -> "食神"
            ShiShen.SHISHEN_SHANG_GUAN -> "伤官"
            ShiShen.SHISHEN_ZHENG_CAI -> "正财"
            ShiShen.SHISHEN_PIAN_CAI -> "偏财"
            ShiShen.SHISHEN_ZHENG_GUAN -> "正官"
            ShiShen.SHISHEN_QI_SHA -> "七杀"
        }
    }

    /**
     * 根据地支和日主天干，获取藏干及其对应的十神列表
     * @param dz 地支
     * @param dayMaster 日主天干
     * @return HiddenGan列表
     */
    fun getHiddenGanList(dz: DiZhi, dayMaster: TianGan): List<HiddenGan> {
        val diZhiUtil = DiZhiUtil()
        val tianGanUtil = TianGanUtil()

        // 获取地支的藏干列表
        val cangGans = diZhiUtil.getCanggan(dz)

        // 转换为HiddenGan列表
        return cangGans.map { tg ->
            val ganCn = convertTianganToChar(tg)  // 天干中文
            val shiShenCn = convertToShiShenCn(dayMaster, tg)  // 十神中文
            HiddenGan(
                gan = ganCn,
                shiShen = shiShenCn
            )
        }
    }

    /**
     * 返回当前时间对应的流年干支字符串
     * 格式："2026 丙午年"
     *
     * 天干地支中文转换调用外部函数：
     * - convertTianganToChar(tiangan)
     * - convertDizhiToChar(dizhi)
     */
    fun getCurrentYearGanZhi(): String {
        val year = Year.now().value

        val tianGan = arrayOf("甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸")
        val diZhi   = arrayOf("子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥")

        // 公元4年为甲子年，year - 4 对齐六十甲子起点
        val ganIndex = (year - 4) % 10
        val zhiIndex = (year - 4) % 12

        val ganZhi = tianGan[ganIndex] + diZhi[zhiIndex]

        val yearLabel = "年"

        return "$year $ganZhi$yearLabel"
    }

    /**
     * 根据 BaziData 中记录的大运起始时间，
     * 计算【当前时间】所在的大运序号（index，从 1 开始）
     *
     * 规则：
     * - 每步大运 10 年
     * - 起运时间为 daYunFirstYear / daYunFirstMonth（按当月 1 日起算）
     * - 若当前时间早于起运时间，返回 0（表示尚未起运）
     */
    fun getCurrentDaYunIndex(data: BaziData): Int {
        val today = LocalDate.now()

        val daYunStart = LocalDate.of(
            data.daYunFirstYear,
            data.daYunFirstMonth,
            1
        )

        // 尚未起运
        if (today.isBefore(daYunStart)) {
            return 0
        }

        val yearsSinceStart = today.year - daYunStart.year

        // 处理同一年（起运当年算第 1 个大运）
        val daYunIndex = (yearsSinceStart / 10) + 1

        return daYunIndex
    }

    fun currentDaYunWithAge(data: BaziData): String {
        val today = LocalDate.now()

        val daYunStart = LocalDate.of(
            data.daYunFirstYear,
            data.daYunFirstMonth,
            1
        )

        val birthDate = LocalDate.of(
            data.birthDateYear,
            data.birthDateMonth,
            data.birthDateDay
        )

        // 如果还没到大运开始时间
        if (today.isBefore(daYunStart)) {
            return "尚未起运"
        }

        // ✅ 复用新函数
        val daYunIndex = getCurrentDaYunIndex(data)

        // 取该大运天干地支
        val daYunGz = DaYunUtil().getDaYun(daYunIndex, data)
        val ganZhiStr =
            "${convertTianganToChar(daYunGz.tg)}${convertDizhiToChar(daYunGz.dz)}"

        // 大运起始年龄（按出生年算虚龄差，业内常用）
//        val startAge = daYunStart.year - data.birthDateYear
        val startAge = daYunStart.year + (daYunIndex - 1)* 10
        val endAge = startAge + 9  // 每运10年，显示闭区间

        return "${ganZhiStr}大运（${startAge}–${endAge}）"
    }

    fun shiShenToChinese(shiShen: ShiShen): String = when (shiShen) {
        ShiShen.SHISHEN_BI_JIAN -> "比肩"
        ShiShen.SHISHEN_JIE_CAI -> "劫财"
        ShiShen.SHISHEN_ZHENG_YIN -> "正印"
        ShiShen.SHISHEN_PIAN_YIN -> "偏印"
        ShiShen.SHISHEN_SHI_SHEN -> "食神"
        ShiShen.SHISHEN_SHANG_GUAN -> "伤官"
        ShiShen.SHISHEN_ZHENG_CAI -> "正财"
        ShiShen.SHISHEN_PIAN_CAI -> "偏财"
        ShiShen.SHISHEN_ZHENG_GUAN -> "正官"
        ShiShen.SHISHEN_QI_SHA -> "七杀"
    }

    /**
     * 获取生某五行的五行
     * 例如：生木的是水
     */
    fun getShengWuXing(wx: WuXing): WuXing = when (wx) {
        WuXing.WUXING_MU -> WuXing.WUXING_SHUI
        WuXing.WUXING_HUO -> WuXing.WUXING_MU
        WuXing.WUXING_TU -> WuXing.WUXING_HUO
        WuXing.WUXING_JIN -> WuXing.WUXING_TU
        WuXing.WUXING_SHUI -> WuXing.WUXING_JIN
    }

    /**
     * 获取被某五行生的五行
     * 例如：木生火
     */
    fun getBeiShengWuXing(wx: WuXing): WuXing = when (wx) {
        WuXing.WUXING_MU -> WuXing.WUXING_HUO
        WuXing.WUXING_HUO -> WuXing.WUXING_TU
        WuXing.WUXING_TU -> WuXing.WUXING_JIN
        WuXing.WUXING_JIN -> WuXing.WUXING_SHUI
        WuXing.WUXING_SHUI -> WuXing.WUXING_MU
    }

    /**
     * 获取克某五行的五行
     * 例如：克木的是金
     */
    fun getKeWuXing(wx: WuXing): WuXing = when (wx) {
        WuXing.WUXING_MU -> WuXing.WUXING_JIN
        WuXing.WUXING_HUO -> WuXing.WUXING_SHUI
        WuXing.WUXING_TU -> WuXing.WUXING_MU
        WuXing.WUXING_JIN -> WuXing.WUXING_HUO
        WuXing.WUXING_SHUI -> WuXing.WUXING_TU
    }

    /**
     * 获取被某五行克的五行
     * 例如：木克土
     */
    fun getBeiKeWuXing(wx: WuXing): WuXing = when (wx) {
        WuXing.WUXING_MU -> WuXing.WUXING_TU
        WuXing.WUXING_HUO -> WuXing.WUXING_JIN
        WuXing.WUXING_TU -> WuXing.WUXING_SHUI
        WuXing.WUXING_JIN -> WuXing.WUXING_MU
        WuXing.WUXING_SHUI -> WuXing.WUXING_HUO
    }

    /**
     * 获取通关五行（解决两种五行相克）
     * 例如：木克土，火通关（木生火，火生土）
     */
    fun getTongGuanWuXing(ke: WuXing, beiKe: WuXing): WuXing? {
        return when {
            ke == WuXing.WUXING_MU && beiKe == WuXing.WUXING_TU -> WuXing.WUXING_HUO
            ke == WuXing.WUXING_HUO && beiKe == WuXing.WUXING_JIN -> WuXing.WUXING_TU
            ke == WuXing.WUXING_TU && beiKe == WuXing.WUXING_SHUI -> WuXing.WUXING_JIN
            ke == WuXing.WUXING_JIN && beiKe == WuXing.WUXING_MU -> WuXing.WUXING_SHUI
            ke == WuXing.WUXING_SHUI && beiKe == WuXing.WUXING_HUO -> WuXing.WUXING_MU
            else -> null
        }
    }

    /**
     * 获取相克关系的通关五行
     */
    fun getTongGuanForKe(keWx: WuXing, beiKeWx: WuXing): WuXing? {
        return getTongGuanWuXing(keWx, beiKeWx)
    }


    /**
     * 将十神转换为对应的天干
     * @param shiShen 要转换的十神
     * @param dayMaster 日主天干
     * @return 对应的天干
     */
    fun shiShenToTianGan(shiShen: ShiShen, dayMaster: TianGan): TianGan {
        val dayMasterWx = getTianGanWuxing(dayMaster)
        val dayMasterIndex = dayMaster.ordinal
        val dayMasterIsYang = dayMasterIndex % 2 == 0

        // 根据十神确定对应的五行关系
        val targetWx = when (shiShen) {
            // 比劫：与日主同五行
            ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI -> dayMasterWx

            // 食伤：日主生的五行
            ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN -> getBeiShengWuXing(dayMasterWx)

            // 财星：日主克的五行
            ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI -> getBeiKeWuXing(dayMasterWx)

            // 官杀：克日主的五行
            ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA -> getKeWuXing(dayMasterWx)

            // 印星：生日主的五行
            ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN -> getShengWuXing(dayMasterWx)
        }

        // 根据十神和日主阴阳，确定天干的阴阳
        val targetIsYang = when (shiShen) {
            // 比肩/食神/偏财/七杀/偏印：同性
            ShiShen.SHISHEN_BI_JIAN,
            ShiShen.SHISHEN_SHI_SHEN,
            ShiShen.SHISHEN_PIAN_CAI,
            ShiShen.SHISHEN_QI_SHA,
            ShiShen.SHISHEN_PIAN_YIN -> dayMasterIsYang

            // 劫财/伤官/正财/正官/正印：异性
            ShiShen.SHISHEN_JIE_CAI,
            ShiShen.SHISHEN_SHANG_GUAN,
            ShiShen.SHISHEN_ZHENG_CAI,
            ShiShen.SHISHEN_ZHENG_GUAN,
            ShiShen.SHISHEN_ZHENG_YIN -> !dayMasterIsYang
        }

        // 根据五行和阴阳找到对应的天干
        return findTianGanByWuXingAndYinYang(targetWx, targetIsYang)
    }

    /**
     * 根据五行和阴阳找到对应的天干
     * @param wx 五行
     * @param isYang 是否阳干
     * @return 对应的天干
     */
    private fun findTianGanByWuXingAndYinYang(wx: WuXing, isYang: Boolean): TianGan {
        return when (wx) {
            WuXing.WUXING_MU -> if (isYang) TianGan.TIANGAN_JIA else TianGan.TIANGAN_YI
            WuXing.WUXING_HUO -> if (isYang) TianGan.TIANGAN_BING else TianGan.TIANGAN_DING
            WuXing.WUXING_TU -> if (isYang) TianGan.TIANGAN_WU else TianGan.TIANGAN_JI
            WuXing.WUXING_JIN -> if (isYang) TianGan.TIANGAN_GENG else TianGan.TIANGAN_XIN
            WuXing.WUXING_SHUI -> if (isYang) TianGan.TIANGAN_REN else TianGan.TIANGAN_GUI
        }
    }

    /**
     * 将十神和对应的天干转换为带括号的中文格式
     * @param shiShen 十神类型
     * @param tianGan 对应的天干
     * @return 格式化的字符串，例如 "正印(甲)"
     */
    fun formatShiShenWithTianGan(shiShen: ShiShen, tianGan: TianGan): String {
        val shiShenCn = shiShenToChinese(shiShen)
        val ssTianGan = shiShenToTianGan(shiShen, tianGan)
        val tianGanCn = convertTianganToChar(ssTianGan)
        return "${shiShenCn}(${tianGanCn})"
    }

    /**
     * 根据公历年份返回对应的天干和地支
     * @param year 公历年份
     * @return Pair<天干, 地支>
     */
    fun getGanZhiByYear(year: Int): Pair<TianGan, DiZhi> {
        val adjustedYear = if (year >= 0) year else year - 1

        val tianGanIndex = ((adjustedYear - 4) % 10 + 10) % 10
        val diZhiIndex = ((adjustedYear - 4) % 12 + 12) % 12

        return Pair(
            TianGan.values()[tianGanIndex],
            DiZhi.values()[diZhiIndex]
        )
    }

    /**
     * 根据流年年份，计算当年 12 个流月（寅月～丑月）的天干地支
     *
     * @param year 公历年份（如 2026、1984 等）
     * @return List<Pair<TianGan, DiZhi>>，size=12
     *          index 0  = 正月（寅月）
     *          index 11 = 十二月（丑月）
     */
    fun calcYearMonthGanZhi(year: Int): List<Pair<TianGan, DiZhi>> {
        // 1. 计算年干索引（0=甲,1=乙,...9=癸）
        // 公元 4 年为甲子年
        val adjustedYear = if (year >= 0) year else year - 1
        val yearGanIndex = ((adjustedYear - 4) % 10 + 10) % 10

        // 2. 五虎遁：根据年干确定正月天干索引
        // 甲己 -> 丙(2), 乙庚 -> 戊(4), 丙辛 -> 庚(6), 丁壬 -> 壬(8), 戊癸 -> 甲(0)
        val firstMonthGanIndex = when (yearGanIndex) {
            0, 5 -> 2   // 甲、己
            1, 6 -> 4   // 乙、庚
            2, 7 -> 6   // 丙、辛
            3, 8 -> 8   // 丁、壬
            4, 9 -> 0   // 戊、癸
            else -> 0
        }

        // 3. 月支固定：寅(2)、卯(3)、辰(4)、巳(5)、午(6)、未(7)、
        //          申(8)、酉(9)、戌(10)、亥(11)、子(0)、丑(1)
        val monthZhiBase = DiZhi.values() // 按 enum 顺序：子=0,丑=1,...,亥=11
        val yinZhiIndex = 2 // 寅在地支数组中的索引

        val result = mutableListOf<Pair<TianGan, DiZhi>>()

        for (i in 0 until 12) {
            // 月干 = 正月天干 + i，顺推
            val ganIndex = (firstMonthGanIndex + i) % 10
            val monthGan = TianGan.values()[ganIndex]

            // 月支 = 寅 + i，顺推（超过 11 回绕）
            val zhiIndex = (yinZhiIndex + i) % 12
            val monthZhi = monthZhiBase[zhiIndex]

            result.add(monthGan to monthZhi)
        }

        return result
    }

    /**
     * 根据五行和日主天干，返回对应的十神列表
     * @param wuXing 五行类型
     * @param dayMaster 日主天干
     * @return 对应的十神列表
     */
    fun getShiShenListByWuXing(wuXing: WuXing, dayMaster: TianGan): List<ShiShen> {
        val dayMasterWx = getTianGanWuxing(dayMaster)
        val dayMasterIndex = dayMaster.ordinal
        val dayMasterIsYang = dayMasterIndex % 2 == 0

        return when {
            // 1. 比劫：五行相同
            wuXing == dayMasterWx -> {
                if (dayMasterIsYang) {
                    listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                } else {
                    listOf(ShiShen.SHISHEN_JIE_CAI, ShiShen.SHISHEN_BI_JIAN)
                }
            }

            // 2. 食伤：我生者（日主生五行）
            isSheng(dayMasterWx, wuXing) -> {
                if (dayMasterIsYang) {
                    listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                } else {
                    listOf(ShiShen.SHISHEN_SHANG_GUAN, ShiShen.SHISHEN_SHI_SHEN)
                }
            }

            // 3. 财星：我克者（日主克五行）
            isKe(dayMasterWx, wuXing) -> {
                if (dayMasterIsYang) {
                    listOf(ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_ZHENG_CAI)
                } else {
                    listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                }
            }

            // 4. 官杀：克我者（五行克日主）
            isKe(wuXing, dayMasterWx) -> {
                if (dayMasterIsYang) {
                    listOf(ShiShen.SHISHEN_QI_SHA, ShiShen.SHISHEN_ZHENG_GUAN)
                } else {
                    listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
                }
            }

            // 5. 印星：生我者（五行生日主）
            isSheng(wuXing, dayMasterWx) -> {
                if (dayMasterIsYang) {
                    listOf(ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_ZHENG_YIN)
                } else {
                    listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                }
            }

            else -> emptyList()
        }
    }

    /**
     * 将五行列表转换为十神列表（去重）
     *
     * @param wuXingList 五行列表
     * @param dayMaster 日主天干
     * @return List<ShiShen> 十神列表（已去重）
     */
    fun getShiShenListByWuXingList(wuXingList: List<WuXing>, dayMaster: TianGan): List<ShiShen> {
        val result = mutableSetOf<ShiShen>()

        for (wx in wuXingList) {
            result.addAll(getShiShenListByWuXing(wx, dayMaster))
        }

        return result.toList()
    }

    /**
     * 将五行集合转换为十神集合
     *
     * @param wuXingSet 五行集合
     * @param dayMaster 日主天干
     * @return Set<ShiShen> 十神集合
     */
    fun getShiShenSetByWuXingSet(wuXingSet: Set<WuXing>, dayMaster: TianGan): Set<ShiShen> {
        val result = mutableSetOf<ShiShen>()

        for (wx in wuXingSet) {
            result.addAll(getShiShenListByWuXing(wx, dayMaster))
        }

        return result.toSet()
    }

    /**
     * 天干对应的五行
     */
    fun getTianGanWuxing(tiangan: TianGan): WuXing {
        return when (tiangan) {
            TianGan.TIANGAN_JIA, TianGan.TIANGAN_YI -> WuXing.WUXING_MU
            TianGan.TIANGAN_BING, TianGan.TIANGAN_DING -> WuXing.WUXING_HUO
            TianGan.TIANGAN_WU, TianGan.TIANGAN_JI -> WuXing.WUXING_TU
            TianGan.TIANGAN_GENG, TianGan.TIANGAN_XIN -> WuXing.WUXING_JIN
            TianGan.TIANGAN_REN, TianGan.TIANGAN_GUI -> WuXing.WUXING_SHUI
        }
    }

    /**
     * 地支对应的季节五行（月令）
     */
    fun getDiZhiSeasonElement(dizhi: DiZhi): WuXing {
        return when (dizhi) {
            DiZhi.DIZHI_ZI -> WuXing.WUXING_SHUI  // 冬
            DiZhi.DIZHI_CHOU -> WuXing.WUXING_TU  // 季冬
            DiZhi.DIZHI_YIN -> WuXing.WUXING_MU   // 春
            DiZhi.DIZHI_MOU -> WuXing.WUXING_MU   // 春
            DiZhi.DIZHI_CHEN -> WuXing.WUXING_TU  // 季春
            DiZhi.DIZHI_SI -> WuXing.WUXING_HUO   // 夏
            DiZhi.DIZHI_WU -> WuXing.WUXING_HUO   // 夏
            DiZhi.DIZHI_WEI -> WuXing.WUXING_TU   // 季夏
            DiZhi.DIZHI_SHEN -> WuXing.WUXING_JIN  // 秋
            DiZhi.DIZHI_YOU -> WuXing.WUXING_JIN  // 秋
            DiZhi.DIZHI_XU -> WuXing.WUXING_TU    // 季秋
            DiZhi.DIZHI_HAI -> WuXing.WUXING_SHUI  // 冬
        }
    }

    /**
     * 地支本气对应的五行
     */
    fun getDiZhiMainElement(dizhi: DiZhi): WuXing {
        return when (dizhi) {
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
    }

    /**
     * 地支余气对应的五行
     */
    fun getDiZhiResidualElement(dizhi: DiZhi): WuXing? {
        return when (dizhi) {
            DiZhi.DIZHI_YIN -> WuXing.WUXING_HUO  // 寅中丙火
            DiZhi.DIZHI_SI -> WuXing.WUXING_JIN   // 巳中庚金
            DiZhi.DIZHI_SHEN -> WuXing.WUXING_SHUI // 申中壬水
            DiZhi.DIZHI_HAI -> WuXing.WUXING_MU   // 亥中甲木

            DiZhi.DIZHI_CHEN -> WuXing.WUXING_MU  // 辰中乙木
            DiZhi.DIZHI_XU -> WuXing.WUXING_JIN   // 戌中辛金
            DiZhi.DIZHI_CHOU -> WuXing.WUXING_SHUI // 丑中癸水
            DiZhi.DIZHI_WEI -> WuXing.WUXING_HUO  // 未中了火

            // 子、午、卯、酉无余气
            else -> null
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

