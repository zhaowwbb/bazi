package com.rick.bazi.util

import com.rick.bazi.data.*
import com.rick.bazi.util.WuXingWeightCalculator
//import com.rick.bazi.util.TianGanToShiShenConverter
import com.rick.bazi.util.RootCounter.calculateRootCounts
import com.rick.bazi.util.BaziFormatter.getShengWuXing
import com.rick.bazi.util.BaziFormatter.getBeiShengWuXing
import com.rick.bazi.util.BaziFormatter.getKeWuXing
import com.rick.bazi.util.BaziFormatter.getBeiKeWuXing
import com.rick.bazi.util.BaziFormatter.getTongGuanWuXing
import com.rick.bazi.util.BaziFormatter.getTongGuanForKe


/**
 * 八字用神分析器
 * 基于子平法：扶抑、调候、通关三大原则
 */
object BaziYongShenAnalyzer {

    /**
     * 分析八字，计算用神、忌神、喜神、通关用神
     * @param data BaziData 对象（会被直接修改）
     */
    fun analyze(data: BaziData) {

        //计算通根
        calculateRootCounts(data)

        // 1. 计算五行权重
        val weights = WuXingWeightCalculator.calculateTotalWuXingWeights(data)
        data.jinWeight = weights[WuXing.WUXING_JIN] ?: 0f
        data.muWeight = weights[WuXing.WUXING_MU] ?: 0f
        data.shuiWeight = weights[WuXing.WUXING_SHUI] ?: 0f
        data.huoWeight = weights[WuXing.WUXING_HUO] ?: 0f
        data.tuWeight = weights[WuXing.WUXING_TU] ?: 0f

        // 2. 计算日主强度
        val dayMasterStrength = WuXingWeightCalculator.calculateDayMasterStrength(data)
        val dayMasterWx = WuXingWeightCalculator.getTianGanWuxing(data.dayTiangan)

        // 3. 获取月令五行
        val monthWx = WuXingWeightCalculator.getDiZhiSeasonElement(data.monthDizhi)

        // 4. 分析五行平衡
        val analyzer = WuXingBalanceAnalyzer()
        val balance = analyzer.analyzeBalance(weights)

        // 5. 判断日主强弱等级
        val isStrong = dayMasterStrength.strengthLevel == StrengthLevel.STRONG ||
                dayMasterStrength.strengthLevel == StrengthLevel.VERY_STRONG
        val isWeak = dayMasterStrength.strengthLevel == StrengthLevel.WEAK ||
                dayMasterStrength.strengthLevel == StrengthLevel.VERY_WEAK

        // 6. 计算用神、忌神、喜神
        val yongShenList = mutableListOf<ShiShen>()
        val jiShenList = mutableListOf<ShiShen>()
        val xiShenSet = mutableSetOf<ShiShen>()
        val tongguanShenList = mutableListOf<ShiShen>()

        // 6.1 扶抑法：根据日主强弱定用神
        when {
            isStrong -> {
                // 身强：喜克泄耗，忌生扶
                // 用神：克日主的五行（官杀）、泄日主的五行（食伤）、耗日主的五行（财）
                yongShenList.addAll(getKeXieHaoShiShen(data.dayTiangan))

                // 忌神：生日主的五行（印枭）、同五行（比劫）
                jiShenList.addAll(getShengFuShiShen(data.dayTiangan))
            }
            isWeak -> {
                // 身弱：喜生扶，忌克泄耗
                // 用神：生日主的五行（印枭）、同五行（比劫）
                yongShenList.addAll(getShengFuShiShen(data.dayTiangan))

                // 忌神：克日主的五行（官杀）、泄日主的五行（食伤）、耗日主的五行（财）
                jiShenList.addAll(getKeXieHaoShiShen(data.dayTiangan))
            }
            else -> {
                // 中和：视具体情况而定，通常以调候为主
                // 优先考虑调候用神
            }
        }

        // 6.2 调候法：根据月令气候定调候用神
        val tiaohouShenList = getTiaoHouShiShen(data, monthWx, dayMasterWx)
        if (tiaohouShenList.isNotEmpty()) {
            // 调候用神优先级高于扶抑用神
            yongShenList.addAll(0, tiaohouShenList)
        }

        // 6.3 通关法：解决五行相克冲突
        val tongguanList = getTongGuanShiShen(data, weights)
        tongguanShenList.addAll(tongguanList)

        // 7. 去重并排序
        val uniqueYongShen = yongShenList.distinct()
        val uniqueJiShen = jiShenList.distinct()

        // 8. 喜神：生助用神的五行
        for (ys in uniqueYongShen) {
            val ysWx = shiShenToWuXing(ys)
            // 生助用神的五行就是喜神
            val shengYsWx = getShengWuXing(ysWx)
            xiShenSet.add(wuXingToShiShen(shengYsWx))
        }

        // 9. 更新 BaziData
        data.yongShenList = uniqueYongShen
        data.jiShenList = uniqueJiShen
        data.xiyongShenSet = xiShenSet.toSet()
        data.tongguanShenList = tongguanShenList.distinct()

        // 10. 计算 allYongShenList（用神+喜神）
        val allYongShen = (uniqueYongShen + xiShenSet).distinct()
        data.allYongShenList = allYongShen
    }

    /**
     * 获取生扶日主的十神（印枭、比劫）
     */
    private fun getShengFuShiShen(dayMaster: TianGan): List<ShiShen> {
        val result = mutableListOf<ShiShen>()
        val dayMasterWx = WuXingWeightCalculator.getTianGanWuxing(dayMaster)

        // 生日主的五行（印枭）
        val shengWx = getShengWuXing(dayMasterWx)
        result.add(wuXingToShiShen(shengWx))

        // 同五行（比劫）
        result.add(ShiShen.SHISHEN_BI_JIAN)
        result.add(ShiShen.SHISHEN_JIE_CAI)

        return result
    }

    /**
     * 获取克泄耗日主的十神（官杀、食伤、财）
     */
    private fun getKeXieHaoShiShen(dayMaster: TianGan): List<ShiShen> {
        val result = mutableListOf<ShiShen>()
        val dayMasterWx = WuXingWeightCalculator.getTianGanWuxing(dayMaster)

        // 克日主的五行（官杀）
        val keWx = getKeWuXing(dayMasterWx)
//        result.add(wuXingToShiShen(keWx))
        result.addAll(getShiShenListByWuXing(keWx, dayMaster))

        // 日主生的五行（食伤）
        val shengWx = getBeiShengWuXing(dayMasterWx)
//        result.add(wuXingToShiShen(shengWx))
        result.addAll(getShiShenListByWuXing(shengWx, dayMaster))

        // 日主克的五行（财）
        val beiKeWx = getBeiKeWuXing(dayMasterWx)
//        result.add(wuXingToShiShen(beiKeWx))
        result.addAll(getShiShenListByWuXing(beiKeWx, dayMaster))

        return result
    }

    /**
     * 获取调候用神（根据月令气候）
     */
    private fun getTiaoHouShiShen(
        data: BaziData,
        monthWx: WuXing,
        dayMasterWx: WuXing
    ): List<ShiShen> {
        val result = mutableListOf<ShiShen>()

        // 月令对应的气候特点
        when (data.birthDateMonth) {
            // 春季（寅卯辰月）：木旺，喜火调候（温暖）
            1, 2, 3 -> {
                if (dayMasterWx == WuXing.WUXING_MU) {
                    // 木旺，喜火泄秀
                    result.add(ShiShen.SHISHEN_SHI_SHEN)
                    result.add(ShiShen.SHISHEN_SHANG_GUAN)
                }
            }
            // 夏季（巳午未月）：火旺，喜水调候（降温）
            4, 5, 6 -> {
                if (dayMasterWx == WuXing.WUXING_HUO) {
                    // 火旺，喜水制火
                    result.add(ShiShen.SHISHEN_ZHENG_GUAN)
                    result.add(ShiShen.SHISHEN_QI_SHA)
                }
            }
            // 秋季（申酉戌月）：金旺，喜火调候（暖金）
            7, 8, 9 -> {
                if (dayMasterWx == WuXing.WUXING_JIN) {
                    // 金旺，喜火炼金
                    result.add(ShiShen.SHISHEN_ZHENG_GUAN)
                    result.add(ShiShen.SHISHEN_QI_SHA)
                }
            }
            // 冬季（亥子丑月）：水旺，喜火调候（取暖）
            10, 11, 12 -> {
                if (dayMasterWx == WuXing.WUXING_SHUI) {
                    // 水旺，喜火暖局
                    result.add(ShiShen.SHISHEN_ZHENG_CAI)
                    result.add(ShiShen.SHISHEN_PIAN_CAI)
                }
            }
        }

        return result
    }

    /**
     * 获取通关用神（解决五行相克冲突）
     */
    private fun getTongGuanShiShen(
        data: BaziData,
        weights: Map<WuXing, Float>
    ): List<ShiShen> {
        val result = mutableListOf<ShiShen>()

        // 检查五行是否严重不平衡
        val sortedWeights = weights.entries.sortedByDescending { it.value }

        if (sortedWeights.size >= 2) {
            val maxWx = sortedWeights[0].key
            val minWx = sortedWeights[sortedWeights.size - 1].key

            // 如果最强和最弱五行相差过大，需要通关
            if (sortedWeights[0].value > sortedWeights[sortedWeights.size - 1].value * 3) {
                // 找出中间五行作为通关
                val tongguanWx = getTongGuanWuXing(maxWx, minWx)
                if (tongguanWx != null) {
                    result.add(wuXingToShiShen(tongguanWx))
                }
            }
        }

        // 检查具体的天干相克关系
        val tianGanUtil = TianGanUtil()

        // 年干克日干
        if (tianGanUtil.isTianGanKe(data.yearTiangan, data.dayTiangan)) {
            val tongguanWx = getTongGuanForKe(
                WuXingWeightCalculator.getTianGanWuxing(data.yearTiangan),
                WuXingWeightCalculator.getTianGanWuxing(data.dayTiangan)
            )
            if (tongguanWx != null) {
                result.add(wuXingToShiShen(tongguanWx))
            }
        }

        // 月干克日干
        if (tianGanUtil.isTianGanKe(data.monthTiangan, data.dayTiangan)) {
            val tongguanWx = getTongGuanForKe(
                WuXingWeightCalculator.getTianGanWuxing(data.monthTiangan),
                WuXingWeightCalculator.getTianGanWuxing(data.dayTiangan)
            )
            if (tongguanWx != null) {
                result.add(wuXingToShiShen(tongguanWx))
            }
        }

        // 时干克日干
        if (tianGanUtil.isTianGanKe(data.hourTiangan, data.dayTiangan)) {
            val tongguanWx = getTongGuanForKe(
                WuXingWeightCalculator.getTianGanWuxing(data.hourTiangan),
                WuXingWeightCalculator.getTianGanWuxing(data.dayTiangan)
            )
            if (tongguanWx != null) {
                result.add(wuXingToShiShen(tongguanWx))
            }
        }

        return result
    }

//    /**
//     * 获取生某五行的五行
//     * 例如：生木的是水
//     */
//    private fun getShengWuXing(wx: WuXing): WuXing = when (wx) {
//        WuXing.WUXING_MU -> WuXing.WUXING_SHUI
//        WuXing.WUXING_HUO -> WuXing.WUXING_MU
//        WuXing.WUXING_TU -> WuXing.WUXING_HUO
//        WuXing.WUXING_JIN -> WuXing.WUXING_TU
//        WuXing.WUXING_SHUI -> WuXing.WUXING_JIN
//    }
//
//    /**
//     * 获取被某五行生的五行
//     * 例如：木生火
//     */
//    private fun getBeiShengWuXing(wx: WuXing): WuXing = when (wx) {
//        WuXing.WUXING_MU -> WuXing.WUXING_HUO
//        WuXing.WUXING_HUO -> WuXing.WUXING_TU
//        WuXing.WUXING_TU -> WuXing.WUXING_JIN
//        WuXing.WUXING_JIN -> WuXing.WUXING_SHUI
//        WuXing.WUXING_SHUI -> WuXing.WUXING_MU
//    }
//
//    /**
//     * 获取克某五行的五行
//     * 例如：克木的是金
//     */
//    private fun getKeWuXing(wx: WuXing): WuXing = when (wx) {
//        WuXing.WUXING_MU -> WuXing.WUXING_JIN
//        WuXing.WUXING_HUO -> WuXing.WUXING_SHUI
//        WuXing.WUXING_TU -> WuXing.WUXING_MU
//        WuXing.WUXING_JIN -> WuXing.WUXING_HUO
//        WuXing.WUXING_SHUI -> WuXing.WUXING_TU
//    }
//
//    /**
//     * 获取被某五行克的五行
//     * 例如：木克土
//     */
//    private fun getBeiKeWuXing(wx: WuXing): WuXing = when (wx) {
//        WuXing.WUXING_MU -> WuXing.WUXING_TU
//        WuXing.WUXING_HUO -> WuXing.WUXING_JIN
//        WuXing.WUXING_TU -> WuXing.WUXING_SHUI
//        WuXing.WUXING_JIN -> WuXing.WUXING_MU
//        WuXing.WUXING_SHUI -> WuXing.WUXING_HUO
//    }
//
//    /**
//     * 获取通关五行（解决两种五行相克）
//     * 例如：木克土，火通关（木生火，火生土）
//     */
//    private fun getTongGuanWuXing(ke: WuXing, beiKe: WuXing): WuXing? {
//        return when {
//            ke == WuXing.WUXING_MU && beiKe == WuXing.WUXING_TU -> WuXing.WUXING_HUO
//            ke == WuXing.WUXING_HUO && beiKe == WuXing.WUXING_JIN -> WuXing.WUXING_TU
//            ke == WuXing.WUXING_TU && beiKe == WuXing.WUXING_SHUI -> WuXing.WUXING_JIN
//            ke == WuXing.WUXING_JIN && beiKe == WuXing.WUXING_MU -> WuXing.WUXING_SHUI
//            ke == WuXing.WUXING_SHUI && beiKe == WuXing.WUXING_HUO -> WuXing.WUXING_MU
//            else -> null
//        }
//    }
//
//    /**
//     * 获取相克关系的通关五行
//     */
//    private fun getTongGuanForKe(keWx: WuXing, beiKeWx: WuXing): WuXing? {
//        return getTongGuanWuXing(keWx, beiKeWx)
//    }

    /**
     * ShiShen → WuXing 映射
     */
    private fun shiShenToWuXing(shiShen: ShiShen): WuXing = when (shiShen) {
        ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI -> WuXing.WUXING_MU
        ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN -> WuXing.WUXING_SHUI
        ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN -> WuXing.WUXING_HUO
        ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI -> WuXing.WUXING_TU
        ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA -> WuXing.WUXING_JIN
    }

    /**
     * WuXing → ShiShen 映射（取正的一面）
     */
    private fun wuXingToShiShen(wx: WuXing): ShiShen = when (wx) {
        WuXing.WUXING_MU -> ShiShen.SHISHEN_BI_JIAN
        WuXing.WUXING_HUO -> ShiShen.SHISHEN_SHI_SHEN
        WuXing.WUXING_TU -> ShiShen.SHISHEN_ZHENG_CAI
        WuXing.WUXING_JIN -> ShiShen.SHISHEN_ZHENG_GUAN
        WuXing.WUXING_SHUI -> ShiShen.SHISHEN_ZHENG_YIN
    }

    /**
     * 根据五行和日主天干，返回对应的十神列表
     * @param wuXing 五行类型
     * @param dayMaster 日主天干
     * @return 对应的十神列表
     */
    fun getShiShenListByWuXing(wuXing: WuXing, dayMaster: TianGan): List<ShiShen> {
        val dayMasterWx = WuXingWeightCalculator.getTianGanWuxing(dayMaster)
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
     * 判断五行相生关系
     * @param sheng 生方
     * @param beiSheng 被生方
     * @return 是否相生
     */
    private fun isSheng(sheng: WuXing, beiSheng: WuXing): Boolean {
        return when (sheng) {
            WuXing.WUXING_MU -> beiSheng == WuXing.WUXING_HUO
            WuXing.WUXING_HUO -> beiSheng == WuXing.WUXING_TU
            WuXing.WUXING_TU -> beiSheng == WuXing.WUXING_JIN
            WuXing.WUXING_JIN -> beiSheng == WuXing.WUXING_SHUI
            WuXing.WUXING_SHUI -> beiSheng == WuXing.WUXING_MU
        }
    }

    /**
     * 判断五行相克关系
     * @param ke 克方
     * @param beiKe 被克方
     * @return 是否相克
     */
    private fun isKe(ke: WuXing, beiKe: WuXing): Boolean {
        return when (ke) {
            WuXing.WUXING_MU -> beiKe == WuXing.WUXING_TU
            WuXing.WUXING_TU -> beiKe == WuXing.WUXING_SHUI
            WuXing.WUXING_SHUI -> beiKe == WuXing.WUXING_HUO
            WuXing.WUXING_HUO -> beiKe == WuXing.WUXING_JIN
            WuXing.WUXING_JIN -> beiKe == WuXing.WUXING_MU
        }
    }
}