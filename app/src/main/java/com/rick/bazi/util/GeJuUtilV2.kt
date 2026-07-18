package com.rick.bazi.util

import com.rick.bazi.data.*
import com.rick.bazi.util.BaziFormatter.getDiZhiMainElement
import com.rick.bazi.util.BaziFormatter.getTianGanWuxing

/**
 * 判断八字格局
 *
 * 根据八字信息、五行权重、日主旺衰等数据，计算八字格局
 * 包括：普通十神格局、专旺格、从格等特殊格局
 *
 * @param data BaziData 八字数据对象
 * @return BaziGeJu 格局类型
 */
fun determineBaziGeJu(data: BaziData): BaziGeJu {

    val dayMasterTg = data.dayTiangan
    val dayMasterWx = getTianGanWuxing(dayMasterTg)
    val monthDz = data.monthDizhi
    val weights = data.weights

    // 计算日主权重百分比
    val dayMasterWeight = weights[dayMasterWx] ?: 0f
    val totalWeight = weights.values.sum()
    val dayMasterPercent = if (totalWeight > 0f) dayMasterWeight / totalWeight * 100f else 0f

    // 获取月令本气五行
    val monthBenQiWx = getDiZhiMainElement(monthDz)

    // 获取月令藏干
    val diZhiUtil = DiZhiUtil()
    val monthCangGan = diZhiUtil.getCanggan(monthDz)

    // 获取四柱天干
    val allTianGan = listOf(
        data.yearTiangan,
        data.monthTiangan,
        data.dayTiangan,
        data.hourTiangan
    )

    // 获取四柱地支
    val allDiZhi = listOf(
        data.yearDizhi,
        data.monthDizhi,
        data.dayDizhi,
        data.hourDizhi
    )

    // ==========================================
    // 第一步：检查特殊格局（优先级高于普通格局）
    // ==========================================

    // 1.1 检查专旺格（日主占比 > 75%，其他五行极弱）
    if (dayMasterPercent > 75f) {
        val otherWeights = weights.filterKeys { it != dayMasterWx }
        val maxOther = otherWeights.values.maxOrNull() ?: 0f
        val dayMasterWeightVal = weights[dayMasterWx] ?: 0f

        // 其他五行最大值不超过日主权重的 30%
        if (maxOther < dayMasterWeightVal * 0.3f) {
            // 根据日主五行确定专旺格类型
            return when (dayMasterWx) {
                WuXing.WUXING_MU -> BaziGeJu.GJ_QU_ZHI       // 曲直格（木）
                WuXing.WUXING_HUO -> BaziGeJu.GJ_YAN_SHANG   // 炎上格（火）
                WuXing.WUXING_TU -> BaziGeJu.GJ_JIA_SE       // 稼穑格（土）
                WuXing.WUXING_JIN -> BaziGeJu.GJ_CONG_GE     // 从革格（金）
                WuXing.WUXING_SHUI -> BaziGeJu.GJ_RUN_XIA    // 润下格（水）
            }
        }
    }

    // 1.2 检查从格（日主占比 < 25%）
    if (dayMasterPercent < 25f) {
        // 找出权重最大的五行
        val maxWeightWx = weights.maxByOrNull { it.value }?.key

        if (maxWeightWx != null) {
            val maxWeight = weights[maxWeightWx] ?: 0f

            // 从财格：财星（日主所克）最强
            val caiWx = getWoKeWuXing(dayMasterWx)
            if (maxWeightWx == caiWx && weights[caiWx] ?: 0f > totalWeight * 0.5f) {
                return BaziGeJu.GJ_CONG_CAI
            }

            // 从杀格：官杀（克日主）最强
            val shaWx = getKeWoWuXing(dayMasterWx)
            if (maxWeightWx == shaWx && weights[shaWx] ?: 0f > totalWeight * 0.5f) {
                return BaziGeJu.GJ_CONG_SHA
            }

            // 从儿格：食伤（日主所生）最强
            val erWx = getWoShengWuXing(dayMasterWx)
            if (maxWeightWx == erWx && weights[erWx] ?: 0f > totalWeight * 0.5f) {
                return BaziGeJu.GJ_CONG_ER
            }
        }
    }

    // ==========================================
    // 第二步：检查建禄格和月刃格
    // ==========================================

    // 月令本气与日主五行相同
    if (monthBenQiWx == dayMasterWx) {
        // 四长生月（寅、巳、申、亥）为建禄格
        val luMonths = setOf(DiZhi.DIZHI_YIN, DiZhi.DIZHI_SI, DiZhi.DIZHI_SHEN, DiZhi.DIZHI_HAI)
        // 四帝旺月（子、午、卯、酉）为月刃格
        val renMonths = setOf(DiZhi.DIZHI_ZI, DiZhi.DIZHI_WU, DiZhi.DIZHI_MOU, DiZhi.DIZHI_YOU)

        if (monthDz in luMonths) {
            return BaziGeJu.GJ_JIAN_LU
        }
        if (monthDz in renMonths) {
            return BaziGeJu.GJ_YANG_REN
        }
    }

    // ==========================================
    // 第三步：检查普通十神格局（月令透干取格）
    // ==========================================

    // 3.1 检查月令藏干是否透出天干
    for (cangGan in monthCangGan) {
        if (cangGan in allTianGan) {
            val cangGanWx = getTianGanWuxing(cangGan)
            val cangGanYinYang = getTianGanYinYang(cangGan)
            val dayMasterYinYang = getTianGanYinYang(dayMasterTg)

            // 根据与日主的关系确定十神格局
            val geJu = when {
                // 印星（生我）
                cangGanWx == getShengWuXing(dayMasterWx) -> {
                    if (cangGanYinYang != dayMasterYinYang) BaziGeJu.GJ_ZHENG_YIN
                    else BaziGeJu.GJ_PIAN_YIN
                }
                // 食伤（我生）
                cangGanWx == getWoShengWuXing(dayMasterWx) -> {
                    if (cangGanYinYang == dayMasterYinYang) BaziGeJu.GJ_SHI_SHEN
                    else BaziGeJu.GJ_SHANG_GUAN
                }
                // 官杀（克我）
                cangGanWx == getKeWoWuXing(dayMasterWx) -> {
                    if (cangGanYinYang != dayMasterYinYang) BaziGeJu.GJ_ZHENG_GUAN
                    else BaziGeJu.GJ_QI_SHA
                }
                // 财星（我克）
                cangGanWx == getWoKeWuXing(dayMasterWx) -> {
                    if (cangGanYinYang != dayMasterYinYang) BaziGeJu.GJ_ZHENG_CAI
                    else BaziGeJu.GJ_PIAN_CAI
                }
                // 比劫（同我）
                cangGanWx == dayMasterWx -> {
                    if (cangGanYinYang == dayMasterYinYang) BaziGeJu.GJ_BI_JIAN
                    else BaziGeJu.GJ_JIE_CAI
                }
                else -> null
            }

            if (geJu != null) {
                data.gj = geJu
                return geJu
            }
        }
    }

    // 3.2 月令藏干没有透出，但月令本气较强，以本气取格
    val monthBenQiWxName = monthBenQiWx
    val geJuByBenQi = when {
        monthBenQiWxName == getShengWuXing(dayMasterWx) -> BaziGeJu.GJ_ZHENG_YIN
        monthBenQiWxName == getWoShengWuXing(dayMasterWx) -> BaziGeJu.GJ_SHI_SHEN
        monthBenQiWxName == getKeWoWuXing(dayMasterWx) -> BaziGeJu.GJ_ZHENG_GUAN
        monthBenQiWxName == getWoKeWuXing(dayMasterWx) -> BaziGeJu.GJ_ZHENG_CAI
        monthBenQiWxName == dayMasterWx -> BaziGeJu.GJ_BI_JIAN
        else -> null
    }

    if (geJuByBenQi != null) {
        data.gj = geJuByBenQi
        return geJuByBenQi
    }

    // ==========================================
    // 第四步：检查三合三会局取格
    // ==========================================

    // 检查地支是否存在三合局或三会局
    val sanHeJu = checkSanHeJu(allDiZhi)
    val sanHuiJu = checkSanHuiJu(allDiZhi)

    if (sanHeJu != null) {
        val geJuBySanHe = when {
            sanHeJu == getShengWuXing(dayMasterWx) -> BaziGeJu.GJ_ZHENG_YIN
            sanHeJu == getWoShengWuXing(dayMasterWx) -> BaziGeJu.GJ_SHI_SHEN
            sanHeJu == getKeWoWuXing(dayMasterWx) -> BaziGeJu.GJ_ZHENG_GUAN
            sanHeJu == getWoKeWuXing(dayMasterWx) -> BaziGeJu.GJ_ZHENG_CAI
            sanHeJu == dayMasterWx -> BaziGeJu.GJ_BI_JIAN
            else -> null
        }

        if (geJuBySanHe != null) {
            data.gj = geJuBySanHe
            return geJuBySanHe
        }
    }

    if (sanHuiJu != null) {
        val geJuBySanHui = when {
            sanHuiJu == getShengWuXing(dayMasterWx) -> BaziGeJu.GJ_ZHENG_YIN
            sanHuiJu == getWoShengWuXing(dayMasterWx) -> BaziGeJu.GJ_SHI_SHEN
            sanHuiJu == getKeWoWuXing(dayMasterWx) -> BaziGeJu.GJ_ZHENG_GUAN
            sanHuiJu == getWoKeWuXing(dayMasterWx) -> BaziGeJu.GJ_ZHENG_CAI
            sanHuiJu == dayMasterWx -> BaziGeJu.GJ_BI_JIAN
            else -> null
        }

        if (geJuBySanHui != null) {
            data.gj = geJuBySanHui
            return geJuBySanHui
        }
    }

    // ==========================================
    // 第五步：如果以上都不满足，返回普通格局
    // ==========================================

    data.gj = BaziGeJu.GJ_NONE
    return BaziGeJu.GJ_NONE
}

/**
 * 检查地支是否存在三合局
 *
 * 三合局：
 * - 申子辰 → 水局
 * - 亥卯未 → 木局
 * - 寅午戌 → 火局
 * - 巳酉丑 → 金局
 *
 * @param diZhiList 地支列表
 * @return WuXing? 三合局的五行，如果没有则返回 null
 */
private fun checkSanHeJu(diZhiList: List<DiZhi>): WuXing? {
    val dzSet = diZhiList.toSet()

    // 水局：申子辰
    if (dzSet.containsAll(setOf(DiZhi.DIZHI_SHEN, DiZhi.DIZHI_ZI, DiZhi.DIZHI_CHEN))) {
        return WuXing.WUXING_SHUI
    }

    // 木局：亥卯未
    if (dzSet.containsAll(setOf(DiZhi.DIZHI_HAI, DiZhi.DIZHI_MOU, DiZhi.DIZHI_WEI))) {
        return WuXing.WUXING_MU
    }

    // 火局：寅午戌
    if (dzSet.containsAll(setOf(DiZhi.DIZHI_YIN, DiZhi.DIZHI_WU, DiZhi.DIZHI_XU))) {
        return WuXing.WUXING_HUO
    }

    // 金局：巳酉丑
    if (dzSet.containsAll(setOf(DiZhi.DIZHI_SI, DiZhi.DIZHI_YOU, DiZhi.DIZHI_CHOU))) {
        return WuXing.WUXING_JIN
    }

    return null
}

/**
 * 检查地支是否存在三会局
 *
 * 三会局：
 * - 寅卯辰 → 木局
 * - 巳午未 → 火局
 * - 申酉戌 → 金局
 * - 亥子丑 → 水局
 *
 * @param diZhiList 地支列表
 * @return WuXing? 三会局的五行，如果没有则返回 null
 */
private fun checkSanHuiJu(diZhiList: List<DiZhi>): WuXing? {
    val dzSet = diZhiList.toSet()

    // 木局：寅卯辰
    if (dzSet.containsAll(setOf(DiZhi.DIZHI_YIN, DiZhi.DIZHI_MOU, DiZhi.DIZHI_CHEN))) {
        return WuXing.WUXING_MU
    }

    // 火局：巳午未
    if (dzSet.containsAll(setOf(DiZhi.DIZHI_SI, DiZhi.DIZHI_WU, DiZhi.DIZHI_WEI))) {
        return WuXing.WUXING_HUO
    }

    // 金局：申酉戌
    if (dzSet.containsAll(setOf(DiZhi.DIZHI_SHEN, DiZhi.DIZHI_YOU, DiZhi.DIZHI_XU))) {
        return WuXing.WUXING_JIN
    }

    // 水局：亥子丑
    if (dzSet.containsAll(setOf(DiZhi.DIZHI_HAI, DiZhi.DIZHI_ZI, DiZhi.DIZHI_CHOU))) {
        return WuXing.WUXING_SHUI
    }

    return null
}

// ==============================
// 五行关系辅助函数
// ==============================

/**
 * 获取生某五行的五行（印星）
 */
private fun getShengWuXing(wx: WuXing): WuXing = when (wx) {
    WuXing.WUXING_MU -> WuXing.WUXING_SHUI
    WuXing.WUXING_HUO -> WuXing.WUXING_MU
    WuXing.WUXING_TU -> WuXing.WUXING_HUO
    WuXing.WUXING_JIN -> WuXing.WUXING_TU
    WuXing.WUXING_SHUI -> WuXing.WUXING_JIN
}

/**
 * 获取克某五行的五行（官杀）
 */
private fun getKeWoWuXing(wx: WuXing): WuXing = when (wx) {
    WuXing.WUXING_MU -> WuXing.WUXING_JIN
    WuXing.WUXING_HUO -> WuXing.WUXING_SHUI
    WuXing.WUXING_TU -> WuXing.WUXING_MU
    WuXing.WUXING_JIN -> WuXing.WUXING_HUO
    WuXing.WUXING_SHUI -> WuXing.WUXING_TU
}

/**
 * 获取某五行所生的五行（食伤）
 */
private fun getWoShengWuXing(wx: WuXing): WuXing = when (wx) {
    WuXing.WUXING_MU -> WuXing.WUXING_HUO
    WuXing.WUXING_HUO -> WuXing.WUXING_TU
    WuXing.WUXING_TU -> WuXing.WUXING_JIN
    WuXing.WUXING_JIN -> WuXing.WUXING_SHUI
    WuXing.WUXING_SHUI -> WuXing.WUXING_MU
}

/**
 * 获取某五行所克的五行（财星）
 */
private fun getWoKeWuXing(wx: WuXing): WuXing = when (wx) {
    WuXing.WUXING_MU -> WuXing.WUXING_TU
    WuXing.WUXING_HUO -> WuXing.WUXING_JIN
    WuXing.WUXING_TU -> WuXing.WUXING_SHUI
    WuXing.WUXING_JIN -> WuXing.WUXING_MU
    WuXing.WUXING_SHUI -> WuXing.WUXING_HUO
}

/**
 * 获取天干的阴阳属性
 */
private fun getTianGanYinYang(tg: TianGan): String = when (tg) {
    TianGan.TIANGAN_JIA, TianGan.TIANGAN_BING,
    TianGan.TIANGAN_WU, TianGan.TIANGAN_GENG,
    TianGan.TIANGAN_REN -> "YANG"
    TianGan.TIANGAN_YI, TianGan.TIANGAN_DING,
    TianGan.TIANGAN_JI, TianGan.TIANGAN_XIN,
    TianGan.TIANGAN_GUI -> "YIN"
}

/**
 * 根据格局枚举类型，返回格局的中文名称
 *
 * @param geJu 格局枚举
 * @return String 格局中文名称
 */
fun geJuToChinese(geJu: BaziGeJu): String {
    return when (geJu) {
        BaziGeJu.GJ_JIAN_LU -> "建禄格"
        BaziGeJu.GJ_YANG_REN -> "月刃格"
        BaziGeJu.GJ_ZHENG_YIN -> "正印格"
        BaziGeJu.GJ_PIAN_YIN -> "偏印格"
        BaziGeJu.GJ_SHI_SHEN -> "食神格"
        BaziGeJu.GJ_SHANG_GUAN -> "伤官格"
        BaziGeJu.GJ_ZHENG_CAI -> "正财格"
        BaziGeJu.GJ_PIAN_CAI -> "偏财格"
        BaziGeJu.GJ_ZHENG_GUAN -> "正官格"
        BaziGeJu.GJ_QI_SHA -> "七杀格"
        BaziGeJu.GJ_CONG_SHA -> "从杀格"
        BaziGeJu.GJ_CONG_CAI -> "从财格"
        BaziGeJu.GJ_CONG_ER -> "从儿格"
        BaziGeJu.GJ_QU_ZHI -> "曲直格"
        BaziGeJu.GJ_YAN_SHANG -> "炎上格"
        BaziGeJu.GJ_JIA_SE -> "稼穑格"
        BaziGeJu.GJ_CONG_GE -> "从革格"
        BaziGeJu.GJ_RUN_XIA -> "润下格"
        BaziGeJu.GJ_BI_JIAN -> "比肩格"
        BaziGeJu.GJ_JIE_CAI -> "劫财格"
        BaziGeJu.GJ_NONE -> "普通格局"
    }
}

/**
 * 根据格局枚举类型，返回格局的中文特点（≤12个中文）
 *
 * 特点是指该格局的主要特征、性格倾向等
 *
 * @param geJu 格局枚举
 * @return String 格局特点（≤12个中文）
 */
fun getGeJuFeature(geJu: BaziGeJu): String {
    return when (geJu) {
        BaziGeJu.GJ_JIAN_LU -> "独立自主，有领导力"
        BaziGeJu.GJ_YANG_REN -> "果断勇敢，有魄力"
        BaziGeJu.GJ_ZHENG_YIN -> "学业有成，文化修养高"
        BaziGeJu.GJ_PIAN_YIN -> "思维独特，创新能力强"
        BaziGeJu.GJ_SHI_SHEN -> "性格温和，有艺术天赋"
        BaziGeJu.GJ_SHANG_GUAN -> "才华横溢，表达能力好"
        BaziGeJu.GJ_ZHENG_CAI -> "财运稳定，善于理财"
        BaziGeJu.GJ_PIAN_CAI -> "偏财运好，敢闯敢拼"
        BaziGeJu.GJ_ZHENG_GUAN -> "为人正直，有领导力"
        BaziGeJu.GJ_QI_SHA -> "果断勇敢，有魄力"
        BaziGeJu.GJ_CONG_SHA -> "善于借力，顺势而为"
        BaziGeJu.GJ_CONG_CAI -> "财运旺盛，善抓机遇"
        BaziGeJu.GJ_CONG_ER -> "才华出众，技艺超群"
        BaziGeJu.GJ_QU_ZHI -> "仁慈正直，事业稳健"
        BaziGeJu.GJ_YAN_SHANG -> "热情奔放，有领袖气质"
        BaziGeJu.GJ_JIA_SE -> "诚信稳重，踏实肯干"
        BaziGeJu.GJ_CONG_GE -> "果断刚毅，有改革精神"
        BaziGeJu.GJ_RUN_XIA -> "智慧深邃，善于谋略"
        BaziGeJu.GJ_BI_JIAN -> "独立自主，朋友众多"
        BaziGeJu.GJ_JIE_CAI -> "竞争意识强，适合创业"
        BaziGeJu.GJ_NONE -> "无明显格局特征"
    }
}

/**
 * 根据格局枚举类型，返回格局的中文描述
 *
 * 描述是对该格局的详细说明，包括形成条件、喜忌、运势特点等
 *
 * @param geJu 格局枚举
 * @return String 格局详细描述
 */
fun getGeJuDescription(geJu: BaziGeJu): String {
    return when (geJu) {
        BaziGeJu.GJ_JIAN_LU ->
            "建禄格：月令为日主临官禄地（寅申巳亥月），日主得令而旺。" +
                    "身旺者喜克泄耗（官杀、食伤、财星），身弱者喜生扶（印星、比劫）。" +
                    "性格独立自主，有领导才能，适合创业或从事管理工作。" +
                    "女命婚姻较晚或配偶能力较弱，男命事业有成但易固执。"

        BaziGeJu.GJ_YANG_REN ->
            "月刃格：月令为日主帝旺之地（子午卯酉月），日主极旺。" +
                    "刃为凶神，需官杀制刃或食伤泄秀方能成贵。" +
                    "性格刚猛果断，有魄力和执行力，适合军警、外科医生等职业。" +
                    "需防血光之灾、意外伤害，中年后运势逐渐平稳。"

        BaziGeJu.GJ_ZHENG_YIN ->
            "正印格：月令藏干透出正印，或月令本气为正印。" +
                    "印星为生我之神，代表学业、长辈、贵人。" +
                    "身旺者喜官杀或食伤，身弱者喜印比相助。" +
                    "性格仁慈善良，重视名誉，学业有成，适合教育、文化、医疗等行业。"

        BaziGeJu.GJ_PIAN_YIN ->
            "偏印格：月令藏干透出偏印，或月令本气为偏印。" +
                    "偏印为生我之神，但同性相生，力量不如正印纯粹。" +
                    "性格独特，思维敏锐，有特殊才艺或偏门技能。" +
                    "适合科研、玄学、艺术等需要创造力的行业，但人际关系较淡薄。"

        BaziGeJu.GJ_SHI_SHEN ->
            "食神格：月令藏干透出食神，或月令本气为食神。" +
                    "食神为我生之神，代表才华、享受、子女。" +
                    "身旺者喜财星或官杀，身弱者喜印比相助。" +
                    "性格温和善良，才华横溢，生活安逸，适合文艺、餐饮、教育等行业。"

        BaziGeJu.GJ_SHANG_GUAN ->
            "伤官格：月令藏干透出伤官，或月令本气为伤官。" +
                    "伤官为我生之神，同性相生，力量强劲。" +
                    "身旺者喜财星或印星，身弱者喜印比相助。" +
                    "性格聪明伶俐，口才出众，有艺术天赋，适合演艺、设计、律师等行业。" +
                    "需防言语伤人，中年后运势渐佳。"

        BaziGeJu.GJ_ZHENG_CAI ->
            "正财格：月令藏干透出正财，或月令本气为正财。" +
                    "正财为我克之神，阴阳相异，代表稳定的收入、妻子。" +
                    "身旺者喜官杀或食伤，身弱者喜印比相助。" +
                    "性格勤俭持家，脚踏实地，理财有道，适合财务、商贸、管理等行业。" +
                    "婚姻稳定，财运平顺。"

        BaziGeJu.GJ_PIAN_CAI ->
            "偏财格：月令藏干透出偏财，或月令本气为偏财。" +
                    "偏财为我克之神，阴阳相同，代表意外的收入、父亲。" +
                    "身旺者喜官杀或食伤，身弱者喜印比相助。" +
                    "性格慷慨大方，善于交际，有商业头脑，适合贸易、投资、销售等行业。" +
                    "财运起伏较大，需防投机失败。"

        BaziGeJu.GJ_ZHENG_GUAN ->
            "正官格：月令藏干透出正官，或月令本气为正官。" +
                    "正官为克我之神，阴阳相异，代表事业、权力、丈夫。" +
                    "身旺者喜财星或食伤，身弱者喜印比相助。" +
                    "性格遵纪守法，责任心强，有管理才能，适合公务员、企业管理等职业。" +
                    "女命婚姻美满，男命事业有成。"

        BaziGeJu.GJ_QI_SHA ->
            "七杀格：月令藏干透出七杀，或月令本气为七杀。" +
                    "七杀为克我之神，阴阳相同，代表压力、挑战、权威。" +
                    "身旺者喜印星化杀或食伤制杀，身弱者喜印比相助。" +
                    "性格果断勇敢，有魄力，适合军警、司法、外科医生等高压职业。" +
                    "需防健康问题和人际冲突，中年后运势转好。"

        BaziGeJu.GJ_CONG_SHA ->
            "从杀格：日主极弱，官杀极旺，日主不得不从官杀之势。" +
                    "喜官杀、财星，忌印星、比劫。" +
                    "性格顺从大势，善于借力，能在逆境中崛起。" +
                    "适合在大型机构或强势领导下发展，不宜独立创业。" +
                    "需防健康不佳，受制于人。"

        BaziGeJu.GJ_CONG_CAI ->
            "从财格：日主极弱，财星极旺，日主不得不从财之势。" +
                    "喜财星、食伤，忌印星、比劫。" +
                    "善于理财投资，能得财富，但需防因财致祸。" +
                    "适合金融、商贸、投资等行业，不宜合伙经营。" +
                    "需防身体劳累，婚姻不顺。"

        BaziGeJu.GJ_CONG_ER ->
            "从儿格：日主极弱，食伤极旺，日主不得不从食伤之势。" +
                    "喜食伤、财星，忌印星、比劫。" +
                    "才华出众，能靠技艺成名，适合艺术、创作、表演等行业。" +
                    "需防过于放纵，缺乏自律，中年后运势渐佳。"

        BaziGeJu.GJ_QU_ZHI ->
            "曲直格：木气专旺，日主为甲乙木，生于寅卯辰月，地支亥卯未三合木局或寅卯辰三会木局。" +
                    "喜木、水，忌金、土。" +
                    "性格仁慈正直，有仁者之风，事业稳步发展。" +
                    "适合文化、教育、环保等行业。" +
                    "需防过于柔顺，缺乏决断力。"

        BaziGeJu.GJ_YAN_SHANG ->
            "炎上格：火气专旺，日主为丙丁火，生于巳午未月，地支寅午戌三合火局或巳午未三会火局。" +
                    "喜火、木，忌水、金。" +
                    "性格热情奔放，有领袖气质，声名远扬。" +
                    "适合演艺、传媒、能源等行业。" +
                    "需防过于急躁，缺乏耐心。"

        BaziGeJu.GJ_JIA_SE ->
            "稼穑格：土气专旺，日主为戊己土，生于辰戌丑未月，地支四库齐全或土势厚重。" +
                    "喜土、火，忌木、水。" +
                    "性格诚信稳重，踏实肯干，能守成业。" +
                    "适合房地产、农业、建筑等行业。" +
                    "需防过于保守，缺乏创新。"

        BaziGeJu.GJ_CONG_GE ->
            "从革格：金气专旺，日主为庚辛金，生于申酉戌月，地支巳酉丑三合金局或申酉戌三会金局。" +
                    "喜金、土，忌火、木。" +
                    "性格果断刚毅，有改革精神，能成大器。" +
                    "适合金融、法律、机械制造等行业。" +
                    "需防过于刚硬，缺乏圆融。"

        BaziGeJu.GJ_RUN_XIA ->
            "润下格：水气专旺，日主为壬癸水，生于亥子丑月，地支申子辰三合水局或亥子丑三会水局。" +
                    "喜水、金，忌土、火。" +
                    "性格智慧深邃，善于谋略，能屈能伸。" +
                    "适合科研、咨询、水利等行业。" +
                    "需防过于阴柔，缺乏担当。"

        BaziGeJu.GJ_BI_JIAN ->
            "比肩格：月令为日主之比肩，日主得令而旺。" +
                    "身旺者喜官杀或食伤，身弱者喜印比相助。" +
                    "性格独立自主，朋友众多，有合作精神。" +
                    "适合团队合作、体育竞技等行业。" +
                    "需防竞争激烈，易有小人争夺。"

        BaziGeJu.GJ_JIE_CAI ->
            "劫财格：月令为日主之劫财，日主得令而旺。" +
                    "身旺者喜官杀或食伤，身弱者喜印比相助。" +
                    "性格豪爽仗义，兄弟朋友助力，但易破财。" +
                    "适合社交、中介、娱乐等行业。" +
                    "需防合作纠纷，财务纠纷。"

        BaziGeJu.GJ_NONE ->
            "普通格局：月令藏干未透出明显十神，或五行较为均衡，无明显特出之处。" +
                    "此类格局运势平稳，无大起大落，需结合大运流年判断吉凶。" +
                    "性格中庸，适应能力强，适合多种行业发展。" +
                    "建议根据日主旺衰选择用神，顺势而为即可获得良好发展。"
    }
}