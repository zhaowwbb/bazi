package com.rick.bazi.util

import com.rick.bazi.data.*
import com.rick.bazi.model.PatternType
import com.rick.bazi.model.StrengthLevel
import com.rick.bazi.model.YongXiJiResult
import com.rick.bazi.util.BaziFormatter.getDiZhiMainElement
import com.rick.bazi.util.BaziFormatter.getShiShenListByWuXingList
import com.rick.bazi.util.BaziFormatter.getShiShenSetByWuXingSet
import com.rick.bazi.util.BaziFormatter.getTianGanWuxing

///**
// * 用神、喜神、忌神计算结果
// */
//data class YongXiJiResult(
//    val yongShen: WuXing,                   // 用神（主）
//    val yongShenName: String,               // 用神名称
//    val yongShenReason: String,             // 选择理由
//    val xiShen: WuXing?,                    // 喜神（生用神者）
//    val xiShenName: String,                 // 喜神名称
//    val jiShenList: List<WuXing>,           // 忌神列表
//    val jiShenNames: List<String>,          // 忌神名称列表
//    val tiaoHouShen: WuXing?,              // 调候用神
//    val tongGuanShen: WuXing?,             // 通关用神
//    val dayMasterPercent: Float,            // 日主占比%
//    val strengthLevel: StrengthLevel,       // 旺衰等级
//    val patternType: PatternType            // 格局类型
//)
//
///**
// * 格局类型枚举
// */
//enum class PatternType(val displayName: String) {
//    ZHENG_GE("正格"),
//    ZHUAN_WANG("专旺格"),
//    CONG_GE("从格"),
//    JIAN_LU("建禄格"),
//    YUE_REN("月刃格"),
//    HUA_QI("化气格")
//}

/**
 * 五行名称映射
 */
private val wuXingNames = mapOf(
    WuXing.WUXING_JIN to "金",
    WuXing.WUXING_MU to "木",
    WuXing.WUXING_SHUI to "水",
    WuXing.WUXING_HUO to "火",
    WuXing.WUXING_TU to "土"
)

/**
 * 获取五行中文名称
 */
private fun getWuXingName(wx: WuXing): String {
    return wuXingNames[wx] ?: "未知"
}

/**
 * 十神名称映射（基于日主）
 */
private fun getShiShenName(wx: WuXing, dayMasterWx: WuXing, isSameYinYang: Boolean? = null): String {
    if (wx == dayMasterWx) {
        return if (isSameYinYang == true) "比肩" else if (isSameYinYang == false) "劫财" else "比劫"
    }

    val relation = getWuXingRelation(wx, dayMasterWx)

    return when (relation) {
        "生我" -> if (isSameYinYang == true) "偏印" else if (isSameYinYang == false) "正印" else "印星"
        "我生" -> if (isSameYinYang == true) "食神" else if (isSameYinYang == false) "伤官" else "食伤"
        "克我" -> if (isSameYinYang == true) "七杀" else if (isSameYinYang == false) "正官" else "官杀"
        "我克" -> if (isSameYinYang == true) "偏财" else if (isSameYinYang == false) "正财" else "财星"
        else -> "未知"
    }
}

/**
 * 获取五行之间的关系
 */
private fun getWuXingRelation(wx1: WuXing, wx2: WuXing): String {
    val shengWo = getShengWuXing(wx2)
    val woSheng = getWoShengWuXing(wx2)
    val keWo = getKeWoWuXing(wx2)
    val woKe = getWoKeWuXing(wx2)

    return when (wx1) {
        shengWo -> "生我"
        woSheng -> "我生"
        keWo -> "克我"
        woKe -> "我克"
        else -> "同我"
    }
}

/**
 * 判断八字格局类型
 */
private fun determinePatternType(
    gj: BaziGeJu,
    dayMasterWx: WuXing,
    weights: Map<WuXing, Float>,
    dayMasterTg: TianGan,
    monthDz: DiZhi
): PatternType {

    // 1. 检查是否为建禄/月刃格
    val monthWx = getDiZhiMainElement(monthDz)
    if (monthWx == dayMasterWx) {
        val monthBranchName = monthDz.name
        val isLuMonth = listOf("DIZHI_YIN", "DIZHI_SI", "DIZHI_SHEN", "DIZHI_HAI").any { monthDz.name.contains(it) }
        val isRenMonth = listOf("DIZHI_MOU", "DIZHI_WU", "DIZHI_YOU", "DIZHI_ZI").any { monthDz.name.contains(it) }

        if (isLuMonth) return PatternType.JIAN_LU
        if (isRenMonth) return PatternType.YUE_REN
    }

    // 2. 检查是否为专旺格
    if (gj == BaziGeJu.GJ_QU_ZHI ||
        gj == BaziGeJu.GJ_YAN_SHANG ||
        gj == BaziGeJu.GJ_JIA_SE ||
        gj == BaziGeJu.GJ_CONG_GE ||
        gj == BaziGeJu.GJ_RUN_XIA ) {
        val otherWeights = weights.filterKeys { it != dayMasterWx }
        val maxOther = otherWeights.values.maxOrNull() ?: 0f
        val dayMasterWeight = weights[dayMasterWx] ?: 0f

        // 其他五行总和不超过日主的一半
        if (maxOther < dayMasterWeight * 0.3f) {
            return PatternType.ZHUAN_WANG
        }
    }

    // 3. 检查是否为从格
    if (gj == BaziGeJu.GJ_CONG_CAI ||
        gj == BaziGeJu.GJ_CONG_SHA ||
        gj == BaziGeJu.GJ_CONG_ER) {
        return PatternType.CONG_GE
    }

    // 4. 默认为正格
    return PatternType.ZHENG_GE
}

/**
 * 判断是否为冬季月份（需要火调候）
 */
private fun isWinterMonth(monthDz: DiZhi): Boolean {
    return monthDz == DiZhi.DIZHI_HAI || monthDz == DiZhi.DIZHI_ZI || monthDz == DiZhi.DIZHI_CHOU
}

/**
 * 判断是否为夏季月份（需要水调候）
 */
private fun isSummerMonth(monthDz: DiZhi): Boolean {
    return monthDz == DiZhi.DIZHI_SI || monthDz == DiZhi.DIZHI_WU || monthDz == DiZhi.DIZHI_WEI
}

/**
 * 获取调候用神
 */
private fun getTiaoHouShen(monthDz: DiZhi): WuXing? {
    return when {
        isWinterMonth(monthDz) -> WuXing.WUXING_HUO  // 冬生需火暖局
        isSummerMonth(monthDz) -> WuXing.WUXING_SHUI // 夏生需水润局
        else -> null
    }
}

/**
 * 获取通关用神
 */
private fun getTongGuanShen(
    weights: Map<WuXing, Float>,
    dayMasterWx: WuXing
): WuXing? {
    // 找出权重最大的两个五行
    val sortedWeights = weights.entries.sortedByDescending { it.value }
    if (sortedWeights.size < 2) return null

    val top1 = sortedWeights[0].key
    val top2 = sortedWeights[1].key

    // 检查是否相克
    val keTop1 = getKeWoWuXing(top1)
    val keTop2 = getKeWoWuXing(top2)

    // 如果前两名相克，找通关五行
    if (top1 == keTop2 || top2 == keTop1) {
        // 通关：金木战需水，水火战需木，土木战需火，金火战需土，水土战需金
        return when {
            (top1 == WuXing.WUXING_JIN && top2 == WuXing.WUXING_MU) ||
                    (top1 == WuXing.WUXING_MU && top2 == WuXing.WUXING_JIN) -> WuXing.WUXING_SHUI

            (top1 == WuXing.WUXING_SHUI && top2 == WuXing.WUXING_HUO) ||
                    (top1 == WuXing.WUXING_HUO && top2 == WuXing.WUXING_SHUI) -> WuXing.WUXING_MU

            (top1 == WuXing.WUXING_TU && top2 == WuXing.WUXING_MU) ||
                    (top1 == WuXing.WUXING_MU && top2 == WuXing.WUXING_TU) -> WuXing.WUXING_HUO

            (top1 == WuXing.WUXING_HUO && top2 == WuXing.WUXING_JIN) ||
                    (top1 == WuXing.WUXING_JIN && top2 == WuXing.WUXING_HUO) -> WuXing.WUXING_TU

            (top1 == WuXing.WUXING_SHUI && top2 == WuXing.WUXING_TU) ||
                    (top1 == WuXing.WUXING_TU && top2 == WuXing.WUXING_SHUI) -> WuXing.WUXING_JIN

            else -> null
        }
    }

    return null
}

/**
 * 确定用神、喜神、忌神（主入口函数）
 *
 * @param data BaziData 八字数据
 * @param weights 五行权重 Map（来自 calculateTotalWuXingWeightsV2）
 * @param strengthLevel 旺衰等级（来自 calculateDayMasterStrength）
 * @param dayMasterPercent 日主权重百分比
 * @return YongXiJiResult 用神喜神忌神结果
 */
fun determineYongXiJi(
    data: BaziData,
    weights: Map<WuXing, Float>,
    strengthLevel: StrengthLevel,
    dayMasterPercent: Float
): YongXiJiResult {

    val dayMasterTg = data.dayTiangan
    val dayMasterWx = getTianGanWuxing(dayMasterTg)
    val monthDz = data.monthDizhi

    // ==========================================
    // Step 1：定格局类型
    // ==========================================
    val patternType = determinePatternType(data.gj, dayMasterWx, weights, dayMasterTg, monthDz)

    println("patternType：${patternType}")
    // ==========================================
    // Step 2：初判用忌五行集合
    // ==========================================
    val shengWo = getShengWuXing(dayMasterWx)     // 印星五行（生我）
    val tongWo = dayMasterWx                      // 比劫五行（同我）
    val keWo = getKeWoWuXing(dayMasterWx)         // 官杀五行（克我）
    val woSheng = getWoShengWuXing(dayMasterWx)   // 食伤五行（我生）
    val woKe = getWoKeWuXing(dayMasterWx)         // 财星五行（我克）

    var candidateYongSet: Set<WuXing>
    var candidateJiSet: Set<WuXing>
    var yongShenReason: String

    when {
        // 特殊格局：专旺格
        patternType == PatternType.ZHUAN_WANG -> {
            candidateYongSet = setOf(shengWo, tongWo)  // 顺其旺势
            candidateJiSet = setOf(keWo, woSheng, woKe) // 忌克泄耗
            yongShenReason = "日主占比${String.format("%.1f%%", dayMasterPercent)}，专旺格，顺其旺势，喜生扶"
        }

        // 特殊格局：从格
        patternType == PatternType.CONG_GE -> {
            candidateYongSet = setOf(keWo, woSheng, woKe) // 顺其弱势
            candidateJiSet = setOf(shengWo, tongWo)       // 忌生扶
            yongShenReason = "日主占比${String.format("%.1f%%", dayMasterPercent)}，从格，顺其弱势，喜克泄耗"
        }

        // 日主极强
        strengthLevel == StrengthLevel.VERY_STRONG || dayMasterPercent > 75f -> {
            candidateYongSet = setOf(keWo, woSheng, woKe)  // 喜克泄耗
            candidateJiSet = setOf(shengWo, tongWo)        // 忌生扶
            yongShenReason = "日主极强（${String.format("%.1f%%", dayMasterPercent)}），喜克泄耗，忌生扶"
        }

        // 日主偏强
        strengthLevel == StrengthLevel.STRONG || dayMasterPercent >= 60f -> {
            candidateYongSet = setOf(keWo, woSheng, woKe)
            candidateJiSet = setOf(shengWo, tongWo)
            yongShenReason = "日主偏强（${String.format("%.1f%%", dayMasterPercent)}），喜克泄耗，忌生扶"
        }

        // 日主偏弱
        strengthLevel == StrengthLevel.WEAK || dayMasterPercent >= 25f -> {
            candidateYongSet = setOf(shengWo, tongWo)      // 喜生扶
            candidateJiSet = setOf(keWo, woSheng, woKe)    // 忌克泄耗
            yongShenReason = "日主偏弱（${String.format("%.1f%%", dayMasterPercent)}），喜生扶，忌克泄耗"
        }

        // 日主极弱
        strengthLevel == StrengthLevel.VERY_WEAK || dayMasterPercent < 25f -> {
            candidateYongSet = setOf(shengWo, tongWo)
            candidateJiSet = setOf(keWo, woSheng, woKe)
            yongShenReason = "日主极弱（${String.format("%.1f%%", dayMasterPercent)}），急需生扶，忌克泄耗"
        }

        // 中和
        else -> {
            // 找全局最弱的五行补充
            val weakestWx = weights.minByOrNull { it.value }?.key ?: dayMasterWx
            candidateYongSet = setOf(weakestWx)
            candidateJiSet = setOf(weights.maxByOrNull { it.value }?.key ?: dayMasterWx)
            yongShenReason = "日主中和（${String.format("%.1f%%", dayMasterPercent)}），补充最弱五行"
        }
    }
    println("candidateYongSet：${candidateYongSet}")
    println("candidateJiSet：${candidateJiSet}")
    println("yongShenReason：${yongShenReason}")

    // ==========================================
    // Step 3：在候选集中按权重排序用神优先级
    // ==========================================
    // 用神优先级：候选集中权重最小的五行最需要补益
    val yongShen = candidateYongSet.minByOrNull { weights[it] ?: 0f } ?: dayMasterWx

    // ==========================================
    // Step 4：加入调候与通关修正
    // ==========================================
    val tiaoHouShen = getTiaoHouShen(monthDz)
    val tongGuanShen = getTongGuanShen(weights, dayMasterWx)

    // 最终用神（调候优先于扶抑）
    val finalYongShen = when {
        // 调候用神在候选用神集内，提升为第一用神
        tiaoHouShen != null && tiaoHouShen in candidateYongSet -> {
            yongShenReason += "，且需${getWuXingName(tiaoHouShen)}调候"
            tiaoHouShen
        }
        // 调候用神不在候选集但全局确实需要调候
        tiaoHouShen != null && (isWinterMonth(monthDz) || isSummerMonth(monthDz)) -> {
            yongShenReason += "，优先${getWuXingName(tiaoHouShen)}调候"
            tiaoHouShen
        }
        // 通关用神加入候选
        tongGuanShen != null && tongGuanShen !in candidateYongSet -> {
            yongShenReason += "，需${getWuXingName(tongGuanShen)}通关"
            yongShen  // 仍用原用神，但通关作为辅助
        }
        else -> yongShen
    }

    // ==========================================
    // Step 5：定喜神（生用神者）
    // ==========================================
    val xiShen = getShengWuXing(finalYongShen)

    // 忌神列表（按权重从大到小排序）
    val jiShenList = candidateJiSet
        .filter { it != finalYongShen }
        .sortedByDescending { weights[it] ?: 0f }

    // ==========================================
    // 构建返回结果（修正类型推断问题）
    // ==========================================
    val yongShenName = getShiShenName(finalYongShen, dayMasterWx)
    val xiShenName = getShiShenName(xiShen, dayMasterWx)

    // 修正：使用明确的类型转换
    val jiShenNames = jiShenList.map { wx: WuXing ->
        "${getShiShenName(wx, dayMasterWx)}（${getWuXingName(wx)}）"
    }

    return YongXiJiResult(
        yongShen = finalYongShen,
        yongShenName = "$yongShenName（${getWuXingName(finalYongShen)}）",
        yongShenReason = yongShenReason,
        xiShen = xiShen,
        xiShenName = "$xiShenName（${getWuXingName(xiShen)}）",
        jiShenList = jiShenList,
        jiShenNames = jiShenNames,
        tiaoHouShen = tiaoHouShen,
        tongGuanShen = tongGuanShen,
        dayMasterPercent = dayMasterPercent,
        strengthLevel = strengthLevel,
        patternType = patternType
    )
}

// ==============================
// 五行关系辅助函数
// ==============================

/**
 * 获取生某五行的五行（印星）
 */
private fun getShengWuXing(wx: WuXing): WuXing = when (wx) {
    WuXing.WUXING_MU -> WuXing.WUXING_SHUI  // 水生木
    WuXing.WUXING_HUO -> WuXing.WUXING_MU   // 木生火
    WuXing.WUXING_TU -> WuXing.WUXING_HUO   // 火生土
    WuXing.WUXING_JIN -> WuXing.WUXING_TU   // 土生金
    WuXing.WUXING_SHUI -> WuXing.WUXING_JIN // 金生水
}

/**
 * 获取克某五行的五行（官杀）
 */
private fun getKeWoWuXing(wx: WuXing): WuXing = when (wx) {
    WuXing.WUXING_MU -> WuXing.WUXING_JIN  // 金克木
    WuXing.WUXING_HUO -> WuXing.WUXING_SHUI // 水克火
    WuXing.WUXING_TU -> WuXing.WUXING_MU   // 木克土
    WuXing.WUXING_JIN -> WuXing.WUXING_HUO  // 火克金
    WuXing.WUXING_SHUI -> WuXing.WUXING_TU  // 土克水
}

/**
 * 获取某五行所生的五行（食伤）
 */
private fun getWoShengWuXing(wx: WuXing): WuXing = when (wx) {
    WuXing.WUXING_MU -> WuXing.WUXING_HUO  // 木生火
    WuXing.WUXING_HUO -> WuXing.WUXING_TU  // 火生土
    WuXing.WUXING_TU -> WuXing.WUXING_JIN  // 土生金
    WuXing.WUXING_JIN -> WuXing.WUXING_SHUI // 金生水
    WuXing.WUXING_SHUI -> WuXing.WUXING_MU // 水生木
}

/**
 * 获取某五行所克的五行（财星）
 */
private fun getWoKeWuXing(wx: WuXing): WuXing = when (wx) {
    WuXing.WUXING_MU -> WuXing.WUXING_TU    // 木克土
    WuXing.WUXING_HUO -> WuXing.WUXING_JIN  // 火克金
    WuXing.WUXING_TU -> WuXing.WUXING_SHUI  // 土克水
    WuXing.WUXING_JIN -> WuXing.WUXING_MU   // 金克木
    WuXing.WUXING_SHUI -> WuXing.WUXING_HUO // 水克火
}

/**
 * 将用神、喜神、忌神分析结果更新到 BaziData 对象中
 *
 * @param data BaziData 八字数据对象（将被修改）
 * @param result YongXiJiResult 用神分析结果
 */
fun updateBaziDataWithYongXiJi(
    data: BaziData,
    result: YongXiJiResult
) {
    val dayMaster = data.dayTiangan

    // 1. 用神列表（主用神放在第一位）
    val yongShenList = mutableListOf(result.yongShen)
    // 如果存在调候用神且与主用神不同，也加入用神列表
    if (result.tiaoHouShen != null && result.tiaoHouShen != result.yongShen) {
        yongShenList.add(result.tiaoHouShen)
    }
    // 如果存在通关用神且与主用神和调候用神不同，也加入用神列表
    if (result.tongGuanShen != null &&
        result.tongGuanShen != result.yongShen &&
        result.tongGuanShen != result.tiaoHouShen) {
        yongShenList.add(result.tongGuanShen)
    }
    data.yongShenList = getShiShenListByWuXingList(yongShenList, dayMaster)

    // 2. 喜神列表（生用神者）
    val xiyongShenSet = mutableSetOf<WuXing>()
    if (result.xiShen != null) {
        xiyongShenSet.add(result.xiShen)
    }
    // 如果用神本身也是喜神（如专旺格中用神和喜神相同的情况）
    // 但通常喜神是生用神的五行，与用神不同
    data.xiyongShenSet = getShiShenSetByWuXingSet(xiyongShenSet, dayMaster)
    // 3. 忌神列表（从忌神列表中取出，按权重从大到小排序）
    val jiShenList = result.jiShenList.toList()
    data.jiShenList = getShiShenListByWuXingList(jiShenList, dayMaster)

    // 4. 通关用神列表
    val tongguanShenList = mutableListOf<WuXing>()
    if (result.tongGuanShen != null) {
        tongguanShenList.add(result.tongGuanShen)
    }

    data.tongguanShenList = getShiShenListByWuXingList(tongguanShenList, dayMaster)

    // 5. 调候用神列表
    val tiaohouShenList = mutableListOf<WuXing>()
    if (result.tiaoHouShen != null) {
        tiaohouShenList.add(result.tiaoHouShen)
    }
    data.tiaohouShenList = getShiShenListByWuXingList(tiaohouShenList, dayMaster)

    // 2. 合并用神和喜神为一个集合（用于快速查找）
    val protectSet = mutableSetOf<Any>()
    protectSet.addAll(data.yongShenList)
    protectSet.addAll(data.xiyongShenSet)

    // 3. 过滤忌神列表：保留不在保护集合中的元素
    val filteredJiShen = data.jiShenList.filterNot { it in protectSet }

//    val cleanedJiShenList = data.jiShenList.filter { it !in protectSet }

    println("filteredJiShen :${filteredJiShen}, protectSet=${protectSet}")
    // 4. 更新 data.jiShenList
    data.jiShenList = filteredJiShen

    data.yongXiJiResult = result
}

