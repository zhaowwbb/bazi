//package com.rick.bazi.util
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.rick.bazi.data.BaziInfo
//import com.rick.bazi.data.*
//import com.rick.bazi.ui.BaziViewModel
//import com.rick.bazi.util.*
//import java.time.LocalDate
//
///*
//  =========================
//  数据载体（分析用）
//  =========================
//*/
//
//data class MarriageDaYunInfo(
//    val yearRange: String,
//    val ganZhi: String,
//    val spouseStarText: String,
//    val palaceEffect: String,
//    val yinyuanLevel: YinyuanLevel,
//    val desc: String
//)
//
//data class PeachBlossomYearInfo(
//    val year: Int,
//    val ganZhi: String,
//    val peachFlower: String,
//    val reason: String,
//    val desc: String
//)
//
//data class CouplePalaceImpact(
//    val type: String, // 冲 / 合 / 刑 / 害
//    val source: String, // 大运 / 流年
//    val fromGanZhi: String,
//    val effect: String
//)
//
//enum class YinyuanLevel(val label: String, val color: Color) {
//    EXCELLENT("极佳", Color(0xFF27AE60)),
//    GOOD("良好", Color(0xFF3498DB)),
//    NORMAL("一般", Color(0xFF95A5A6)),
//    VOLATILE("波动较大", Color(0xFFE74C3C))
//}
//
///*
//  =========================
//  分析工具类：BaziMarriageAnalyzer
//  =========================
//*/
//object BaziMarriageAnalyzer {
//
//    /**
//     * 大运对姻缘的影响分析
//     */
//    fun analyzeDaYunMarriage(data: BaziData): List<MarriageDaYunInfo> {
//        val list = mutableListOf<MarriageDaYunInfo>()
//        val dayZhi = data.dayDizhi
//        val isMale = data.gender == MALE
//
//        val daYunList = generateDaYunList(data)
//
//        for (dy in daYunList) {
//            val tg = parseTianGan(dy.ganZhi)
//            val dz = parseDiZhi(dy.ganZhi)
//
//            // 配偶星（男财 / 女官）
//            val spouseShiShen = if (isMale) {
//                listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
//            } else {
//                listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
//            }
//
//            val spTg = shiShenToTianGan(spouseShiShen.first(), data.dayTiangan)
//            val hasSpouseStar = tg == spTg || spouseShiShen.any { shiShenToTianGan(it, data.dayTiangan) == tg }
//
//            val spouseStarText = if (isMale) "财星(${tianGanToChar(spTg)})"
//            else "官杀(${tianGanToChar(spTg)})"
//
//            // 夫妻宫引动
//            val palaceEffect = when {
//                dz == dayZhi -> "伏吟夫妻宫"
//                isLiuHe(dz, dayZhi) -> "六合入夫妻宫"
//                isSanHe(dz, dayZhi) -> "三合入夫妻宫"
//                isChong(dz, dayZhi) -> "冲动夫妻宫"
//                isXing(dz, dayZhi) -> "刑动夫妻宫"
//                else -> "无直接引动"
//            }
//
//            // 姻缘评级
//            val level = when {
//                hasSpouseStar && palaceEffect.contains("合入") -> YinyuanLevel.EXCELLENT
//                hasSpouseStar || palaceEffect.contains("合入") -> YinyuanLevel.GOOD
//                palaceEffect.contains("冲") -> YinyuanLevel.VOLATILE
//                else -> YinyuanLevel.NORMAL
//            }
//
//            val desc = buildString {
//                if (hasSpouseStar) append("大运透配偶星(${spouseStarText})；")
//                append("地支${palaceEffect}；")
//                val tg
//            }
//
//            list.add(
//                MarriageDaYunInfo(
//                    yearRange = dy.yearRange,
//                    ganZhi = dy.ganZhi,
//                    spouseStarText = spouseStarText,
//                    palaceEffect = palaceEffect,
//                    yinyuanLevel = level,
//                    desc = desc
//                )
//            )
//        }
//        return list
//    }
//
//    /**
//     * 未来10年桃花旺盛流年
//     */
//    fun analyzePeachBlossomYears(data: BaziData): List<PeachBlossomYearInfo> {
//        val list = mutableListOf<PeachBlossomYearInfo>()
//        val dayZhi = data.dayDizhi
//        val now = LocalDate.now().year
//        val peach = getPeachBlossomBranch(dayZhi)
//
//        for (y in now..now + 9) {
//            val gz = yearToGanZhi(y)
//            val dz = parseDiZhi(gz)
//
//            if (dz == peach) {
//                val isSpouse = isSpouseStarYear(data, y)
//                val reason = buildString {
//                    if (isSpouse) append("桃花+配偶星同现；")
//                    if (isLiuHe(dz, dayZhi) || isSanHe(dz, dayZhi))
//                        append("桃花合/会入日支；")
//                    if (isEmpty()) append("流年地支为四桃花星")
//                }
//
//                list.add(
//                    PeachBlossomYearInfo(
//                        year = y,
//                        ganZhi = gz,
//                        peachFlower = "${zhiToChar(dz)}(桃花)",
//                        reason = reason,
//                        desc = if (isSpouse)
//                            "易遇正缘或认真关系，异性缘强"
//                        else
//                            "桃花旺盛但需注意甄别烂桃花"
//                    )
//                )
//            }
//        }
//        return list
//    }
//
//    /**
//     * 夫妻宫被冲/合/刑/害的大运与流年影响
//     */
//    fun analyzeCouplePalaceImpacts(data: BaziData): List<CouplePalaceImpact> {
//        val impacts = mutableListOf<CouplePalaceImpact>()
//        val dayZhi = data.dayDizhi
//
//        // 大运
//        val daYunList = generateDaYunList(data)
//        for (dy in daYunList) {
//            val dz = parseDiZhi(dy.ganZhi)
//            when {
//                isChong(dz, dayZhi) ->
//                    impacts.add(
//                        CouplePalaceImpact(
//                            "冲",
//                            "大运 ${dy.yearRange}",
//                            dy.ganZhi,
//                            "夫妻宫被冲，单身易遇新缘、已婚需注意关系变动；若为忌神冲喜则成婚，若为喜冲则波动"
//                        )
//                    )
//                isLiuHe(dz, dayZhi) ->
//                    impacts.add(
//                        CouplePalaceImpact(
//                            "合",
//                            "大运 ${dy.yearRange}",
//                            dy.ganZhi,
//                            "夫妻宫被合，感情趋于稳定，易订婚、结婚、深化关系"
//                        )
//                    )
//                isXing(dz, dayZhi) ->
//                    impacts.add(
//                        CouplePalaceImpact(
//                            "刑",
//                            "大运 ${dy.yearRange}",
//                            dy.ganZhi,
//                            "夫妻宫刑动，易有摩擦、隐忍矛盾，需注意沟通与界限"
//                        )
//                    )
//            }
//        }
//
//        // 流年（未来10年）
//        val now = LocalDate.now().year
//        for (y in now..now + 9) {
//            val dz = parseDiZhi(yearToGanZhi(y))
//            when {
//                isChong(dz, dayZhi) ->
//                    impacts.add(
//                        CouplePalaceImpact(
//                            "冲",
//                            "流年 $y",
//                            yearToGanZhi(y),
//                            "流年冲动夫妻宫，旧状态打破，易结婚、分手或突然遇见新人"
//                        )
//                    )
//                isLiuHe(dz, dayZhi) ->
//                    impacts.add(
//                        CouplePalaceImpact(
//                            "合",
//                            "流年 $y",
//                            yearToGanZhi(y),
//                            "流年合入夫妻宫，该年易确定关系、领证、感情升温"
//                        )
//                    )
//            }
//        }
//
//        return impacts
//    }
//
//    /*
//      =========================
//      辅助：干支 / 桃花 / 冲合刑害 / 配偶星 / 流年
//      =========================
//    */
//
//    fun getPeachBlossomBranch(dayZhi: DiZhi): DiZhi = when (dayZhi) {
//        DiZhi.DIZHI_YIN, DiZhi.DIZHI_WU, DiZhi.DIZHI_XU -> DiZhi.DIZHI_MOU   // 寅午戌见卯
//        DiZhi.DIZHI_HAI, DiZhi.DIZHI_MOU, DiZhi.DIZHI_WEI -> DiZhi.DIZHI_ZI   // 亥卯未见子
//        DiZhi.DIZHI_SHEN, DiZhi.DIZHI_ZI, DiZhi.DIZHI_CHEN -> DiZhi.DIZHI_YOU // 申子辰见酉
//        DiZhi.DIZHI_SI, DiZhi.DIZHI_YOU, DiZhi.DIZHI_CHOU -> DiZhi.DIZHI_WU   // 巳酉丑见午
//        else -> dayZhi
//    }
//
//    fun isLiuHe(a: DiZhi, b: DiZhi): Boolean {
//        val m = mapOf(
//            DiZhi.DIZHI_ZI to DiZhi.DIZHI_CHOU,
//            DiZhi.DIZHI_CHOU to DiZhi.DIZHI_ZI,
//            DiZhi.DIZHI_YIN to DiZhi.DIZHI_HAI,
//            DiZhi.DIZHI_HAI to DiZhi.DIZHI_YIN,
//            DiZhi.DIZHI_MOU to DiZhi.DIZHI_XU,
//            DiZhi.DIZHI_XU to DiZhi.DIZHI_MOU,
//            DiZhi.DIZHI_CHEN to DiZhi.DIZHI_YOU,
//            DiZhi.DIZHI_YOU to DiZhi.DIZHI_CHEN,
//            DiZhi.DIZHI_SI to DiZhi.DIZHI_SHEN,
//            DiZhi.DIZHI_SHEN to DiZhi.DIZHI_SI,
//            DiZhi.DIZHI_WU to DiZhi.DIZHI_WEI,
//            DiZhi.DIZHI_WEI to DiZhi.DIZHI_WU
//        )
//        return m[a] == b || m[b] == a
//    }
//
//    fun isSanHe(a: DiZhi, b: DiZhi): Boolean {
//        val sets = listOf(
//            setOf(DiZhi.DIZHI_YIN, DiZhi.DIZHI_WU, DiZhi.DIZHI_XU),
//            setOf(DiZhi.DIZHI_SHEN, DiZhi.DIZHI_ZI, DiZhi.DIZHI_CHEN),
//            setOf(DiZhi.DIZHI_HAI, DiZhi.DIZHI_MOU, DiZhi.DIZHI_WEI),
//            setOf(DiZhi.DIZHI_SI, DiZhi.DIZHI_YOU, DiZhi.DIZHI_CHOU)
//        )
//        return sets.any { it.contains(a) && it.contains(b) }
//    }
//
//    fun isChong(a: DiZhi, b: DiZhi): Boolean {
//        val m = mapOf(
//            DiZhi.DIZHI_ZI to DiZhi.DIZHI_WU,
//            DiZhi.DIZHI_WU to DiZhi.DIZHI_ZI,
//            DiZhi.DIZHI_CHOU to DiZhi.DIZHI_WEI,
//            DiZhi.DIZHI_WEI to DiZhi.DIZHI_CHOU,
//            DiZhi.DIZHI_YIN to DiZhi.DIZHI_SHEN,
//            DiZhi.DIZHI_SHEN to DiZhi.DIZHI_YIN,
//            DiZhi.DIZHI_MOU to DiZhi.DIZHI_YOU,
//            DiZhi.DIZHI_YOU to DiZhi.DIZHI_MOU,
//            DiZhi.DIZHI_CHEN to DiZhi.DIZHI_XU,
//            DiZhi.DIZHI_XU to DiZhi.DIZHI_CHEN,
//            DiZhi.DIZHI_SI to DiZhi.DIZHI_HAI,
//            DiZhi.DIZHI_HAI to DiZhi.DIZHI_SI
//        )
//        return m[a] == b
//    }
//
//    fun isXing(a: DiZhi, b: DiZhi): Boolean {
//        // 寅巳申 / 丑未戌 / 子卯 / 辰辰午午酉酉亥亥
//        val sets = listOf(
//            setOf(DiZhi.DIZHI_YIN, DiZhi.DIZHI_SI, DiZhi.DIZHI_SHEN),
//            setOf(DiZhi.DIZHI_CHOU, DiZhi.DIZHI_WEI, DiZhi.DIZHI_XU),
//            setOf(DiZhi.DIZHI_ZI, DiZhi.DIZHI_MOU),
//        )
//        return sets.any { it.contains(a) && it.contains(b) } ||
//                (a == b && a in listOf(DiZhi.DIZHI_CHEN, DiZhi.DIZHI_WU, DiZhi.DIZHI_YOU, DiZhi.DIZHI_HAI))
//    }
//
//    fun parseTianGan(ganZhi: String): TianGan {
//        val c = ganZhi.first()
//        return when (c) {
//            '甲' -> TianGan.TIANGAN_JIA
//            '乙' -> TianGan.TIANGAN_YI
//            '丙' -> TianGan.TIANGAN_BING
//            '丁' -> TianGan.TIANGAN_DING
//            '戊' -> TianGan.TIANGAN_WU
//            '己' -> TianGan.TIANGAN_JI
//            '庚' -> TianGan.TIANGAN_GENG
//            '辛' -> TianGan.TIANGAN_XIN
//            '壬' -> TianGan.TIANGAN_REN
//            '癸' -> TianGan.TIANGAN_GUI
//            else -> TianGan.TIANGAN_JIA
//        }
//    }
//
//    fun parseDiZhi(ganZhi: String): DiZhi {
//        val c = ganZhi.getOrNull(1) ?: return DiZhi.DIZHI_ZI
//        return when (c) {
//            '子' -> DiZhi.DIZHI_ZI
//            '丑' -> DiZhi.DIZHI_CHOU
//            '寅' -> DiZhi.DIZHI_YIN
//            '卯' -> DiZhi.DIZHI_MOU
//            '辰' -> DiZhi.DIZHI_CHEN
//            '巳' -> DiZhi.DIZHI_SI
//            '午' -> DiZhi.DIZHI_WU
//            '未' -> DiZhi.DIZHI_WEI
//            '申' -> DiZhi.DIZHI_SHEN
//            '酉' -> DiZhi.DIZHI_YOU
//            '戌' -> DiZhi.DIZHI_XU
//            '亥' -> DiZhi.DIZHI_HAI
//            else -> DiZhi.DIZHI_ZI
//        }
//    }
//
//    fun tianGanToChar(tg: TianGan): String = when (tg) {
//        TianGan.TIANGAN_JIA -> "甲"
//        TianGan.TIANGAN_YI -> "乙"
//        TianGan.TIANGAN_BING -> "丙"
//        TianGan.TIANGAN_DING -> "丁"
//        TianGan.TIANGAN_WU -> "戊"
//        TianGan.TIANGAN_JI -> "己"
//        TianGan.TIANGAN_GENG -> "庚"
//        TianGan.TIANGAN_XIN -> "辛"
//        TianGan.TIANGAN_REN -> "壬"
//        TianGan.TIANGAN_GUI -> "癸"
//    }
//
//    fun zhiToChar(dz: DiZhi): String = when (dz) {
//        DiZhi.DIZHI_ZI -> "子"
//        DiZhi.DIZHI_CHOU -> "丑"
//        DiZhi.DIZHI_YIN -> "寅"
//        DiZhi.DIZHI_MOU -> "卯"
//        DiZhi.DIZHI_CHEN -> "辰"
//        DiZhi.DIZHI_SI -> "巳"
//        DiZhi.DIZHI_WU -> "午"
//        DiZhi.DIZHI_WEI -> "未"
//        DiZhi.DIZHI_SHEN -> "申"
//        DiZhi.DIZHI_YOU -> "酉"
//        DiZhi.DIZHI_XU -> "戌"
//        DiZhi.DIZHI_HAI -> "亥"
//    }
//
//    /**
//     * 公历年份 → 干支（甲子=4）
//     */
//    fun yearToGanZhi(year: Int): String {
//        val tgIdx = ((year - 4) % 10 + 10) % 10
//        val dzIdx = ((year - 4) % 12 + 12) % 12
//        val tg = TianGan.values()[tgIdx]
//        val dz = DiZhi.values()[dzIdx]
//        return tianGanToChar(tg) + zhiToChar(dz)
//    }
//
//    fun tianGanToChar(tg: TianGan): String = when (tg) {
//        TianGan.TIANGAN_JIA -> "甲"
//        TianGan.TIANGAN_YI -> "乙"
//        TianGan.TIANGAN_BING -> "丙"
//        TianGan.TIANGAN_DING -> "丁"
//        TianGan.TIANGAN_WU -> "戊"
//        TianGan.TIANGAN_JI -> "己"
//        TianGan.TIANGAN_GENG -> "庚"
//        TianGan.TIANGAN_XIN -> "辛"
//        TianGan.TIANGAN_REN -> "壬"
//        TianGan.TIANGAN_GUI -> "癸"
//    }
//
//    fun isSpouseStarYear(data: BaziData, year: Int): Boolean {
//        val tg = parseTianGan(yearToGanZhi(year))
//        val ss = TianGanToShiShenConverter.convertToShiShen(data.dayTiangan, tg)
//        return if (data.gender == MALE) {
//            ss == ShiShen.SHISHEN_ZHENG_CAI || ss == ShiShen.SHISHEN_PIAN_CAI
//        } else {
//            ss == ShiShen.SHISHEN_ZHENG_GUAN || ss == ShiShen.SHISHEN_QI_SHA
//        }
//    }
//
//    /**
//     * 十神 → 天干（复用你已有函数语义，这里兜底实现）
//     */
//    fun shiShenToTianGan(shiShen: ShiShen, dayMaster: TianGan): TianGan {
//        val dmWx = WuXingWeightCalculator.getTianGanWuxing(dayMaster)
//        val dmIsYang = dayMaster.ordinal % 2 == 0
//
//        val targetWx = when (shiShen) {
//            ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI -> dmWx
//            ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN -> getBeiShengWuXing(dmWx)
//            ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI -> getBeiKeWuXing(dmWx)
//            ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA -> getKeWuXing(dmWx)
//            ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN -> getShengWuXing(dmWx)
//        }
//
//        val targetIsYang = when (shiShen) {
//            ShiShen.SHISHEN_BI_JIAN,
//            ShiShen.SHISHEN_SHI_SHEN,
//            ShiShen.SHISHEN_PIAN_CAI,
//            ShiShen.SHISHEN_QI_SHA,
//            ShiShen.SHISHEN_PIAN_YIN -> dmIsYang
//            else -> !dmIsYang
//        }
//
//        return findTgByWxAndYang(targetWx, targetIsYang)
//    }
//
//    fun findTgByWxAndYang(wx: WuXing, yang: Boolean): TianGan = when (wx) {
//        WuXing.WUXING_MU -> if (yang) TianGan.TIANGAN_JIA else TianGan.TIANGAN_YI
//        WuXing.WUXING_HUO -> if (yang) TianGan.TIANGAN_BING else TianGan.TIANGAN_DING
//        WuXing.WUXING_TU -> if (yang) TianGan.TIANGAN_WU else TianGan.TIANGAN_JI
//        WuXing.WUXING_JIN -> if (yang) TianGan.TIANGAN_GENG else TianGan.TIANGAN_XIN
//        WuXing.WUXING_SHUI -> if (yang) TianGan.TIANGAN_REN else TianGan.TIANGAN_GUI
//    }
//
//    fun getShengWuXing(wx: WuXing): WuXing = when (wx) {
//        WuXing.WUXING_MU -> WuXing.WUXING_SHUI
//        WuXing.WUXING_HUO -> WuXing.WUXING_MU
//        WuXing.WUXING_TU -> WuXing.WUXING_HUO
//        WuXing.WUXING_JIN -> WuXing.WUXING_TU
//        WuXing.WUXING_SHUI -> WuXing.WUXING_JIN
//    }
//    fun getKeWuXing(wx: WuXing): WuXing = when (wx) {
//        WuXing.WUXING_MU -> WuXing.WUXING_JIN
//        WuXing.WUXING_HUO -> WuXing.WUXING_SHUI
//        WuXing.WUXING_TU -> WuXing.WUXING_MU
//        WuXing.WUXING_JIN -> WuXing.WUXING_HUO
//        WuXing.WUXING_SHUI -> WuXing.WUXING_TU
//    }
//    fun getBeiShengWuXing(wx: WuXing): WuXing = when (wx) {
//        WuXing.WUXING_MU -> WuXing.WUXING_HUO
//        WuXing.WUXING_HUO -> WuXing.WUXING_TU
//        WuXing.WUXING_TU -> WuXing.WUXING_JIN
//        WuXing.WUXING_JIN -> WuXing.WUXING_SHUI
//        WuXing.WUXING_SHUI -> WuXing.WUXING_MU
//    }
//    fun getBeiKeWuXing(wx: WuXing): WuXing = when (wx) {
//        WuXing.WUXING_MU -> WuXing.WUXING_TU
//        WuXing.WUXING_HUO -> WuXing.WUXING_JIN
//        WuXing.WUXING_TU -> WuXing.WUXING_SHUI
//        WuXing.WUXING_JIN -> WuXing.WUXING_MU
//        WuXing.WUXING_SHUI -> WuXing.WUXING_HUO
//    }
//}
//
///*
//  =========================
//  UI：BaziMarriageAnalysisScreen
//  =========================
//*/
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BaziMarriageAnalysisScreen(
//    onCancelButtonClicked: () -> Unit,
//    onSendButtonClicked: (String, String) -> Unit,
//    baziModel: BaziViewModel,
//    baziInfo: BaziInfo,
//    modifier: Modifier = Modifier
//) {
//    val data = baziInfo.baziData
//
//    val daYunList by remember(data) {
//        mutableStateOf(BaziMarriageAnalyzer.analyzeDaYunMarriage(data))
//    }
//    val peachYears by remember(data) {
//        mutableStateOf(BaziMarriageAnalyzer.analyzePeachBlossomYears(data))
//    }
//    val palaceImpacts by remember(data) {
//        mutableStateOf(BaziMarriageAnalyzer.analyzeCouplePalaceImpacts(data))
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("八字姻缘分析") },
//                navigationIcon = {
//                    IconButton(onClick = onCancelButtonClicked) {
//                        Icon(Icons.Default.ArrowBack, null)
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color(0xFF667eea),
//                    titleContentColor = Color.White,
//                    navigationIconContentColor = Color.White
//                )
//            )
//        }
//    ) { padding ->
//        Column(
//            modifier = modifier
//                .fillMaxSize()
//                .padding(padding)
//                .verticalScroll(rememberScrollState())
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//
//            // 夫妻宫概览
//            Card(
//                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(16.dp),
//                colors = CardDefaults.cardColors(containerColor = Color.White),
//                elevation = CardDefaults.cardElevation(4.dp)
//            ) {
//                Column(Modifier.padding(16.dp)) {
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Icon(Icons.Default.Favorite, tint = Color(0xFFE74C3C), contentDescription = null)
//                        Spacer(Modifier.width(8.dp))
//                        Text("夫妻宫（日支）与配偶星", fontWeight = FontWeight.Bold, fontSize = 18.sp)
//                    }
//                    Spacer(Modifier.height(8.dp))
//                    Text(
//                        "日支：${DiZhiUtil().getDiZhiText(data.dayDizhi)}  " +
//                                "日主：${TianGanUtil().getTianGanText(data.dayTiangan)}",
//                        fontSize = 14.sp
//                    )
//                    Text(
//                        "配偶星：${if (data.gender == MALE) "男命以财星为妻" else "女命以官杀为夫"}",
//                        fontSize = 14.sp
//                    )
//                    Text(
//                        "姻缘核心：大运引动配偶星与夫妻宫，流年合冲定应期；桃花旺于子午卯酉年",
//                        fontSize = 13.sp,
//                        color = Color(0xFF95A5A6),
//                        lineHeight = 20.sp
//                    )
//                }
//            }
//
//            // 大运姻缘
//            Text("大运姻缘分析（10年一运）", fontWeight = FontWeight.Bold, fontSize = 18.sp)
//            LazyColumn(
//                modifier = Modifier.height((daYunList.size * 110).coerceAtMost(440).dp),
//                verticalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                items(daYunList.size) { i ->
//                    val item = daYunList[i]
//                    Card(
//                        modifier = Modifier.fillMaxWidth(),
//                        shape = RoundedCornerShape(12.dp),
//                        colors = CardDefaults.cardColors(
//                            containerColor = when (item.yinyuanLevel) {
//                                YinyuanLevel.EXCELLENT -> Color(0xFF27AE60).copy(0.08f)
//                                YinyuanLevel.GOOD -> Color(0xFF3498DB).copy(0.08f)
//                                YinyuanLevel.VOLATILE -> Color(0xFFE74C3C).copy(0.08f)
//                                else -> Color.White
//                            }
//                        ),
//                        border = if (item.yinyuanLevel == YinyuanLevel.EXCELLENT)
//                            BorderStroke(1.dp, Color(0xFF27AE60)) else null
//                    ) {
//                        Column(Modifier.padding(12.dp)) {
//                            Row(
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                modifier = Modifier.fillMaxWidth()
//                            ) {
//                                Text(item.yearRange, fontWeight = FontWeight.Bold)
//                                Text(
//                                    item.yinyuanLevel.label,
//                                    color = item.yinyuanLevel.color,
//                                    fontWeight = FontWeight.Medium
//                                )
//                            }
//                            Spacer(Modifier.height(4.dp))
//                            Text("大运干支：${item.ganZhi}　配偶星：${item.spouseStarText}", fontSize = 14.sp)
//                            Text("夫妻宫引动：${item.palaceEffect}", fontSize = 14.sp)
//                            Text(item.desc, fontSize = 13.sp, color = Color(0xFF95A5A6), lineHeight = 20.sp)
//                        }
//                    }
//                }
//            }
//
//            // 桃花流年
//            Text("桃花旺盛流年（未来10年）", fontWeight = FontWeight.Bold, fontSize = 18.sp)
//            if (peachYears.isEmpty()) {
//                Text("近期无明显桃花流年", color = Color(0xFF95A5A6))
//            } else {
//                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
//                    peachYears.forEach {
//                        Surface(
//                            shape = RoundedCornerShape(12.dp),
//                            color = Color(0xFFFFD700).copy(alpha = 0.12f),
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            Column(Modifier.padding(12.dp)) {
//                                Row(
//                                    horizontalArrangement = Arrangement.SpaceBetween,
//                                    modifier = Modifier.fillMaxWidth()
//                                ) {
//                                    Text("${it.year}年 ${it.ganZhi}", fontWeight = FontWeight.Bold)
//                                    Text(it.peachFlower, color = Color(0xFFE67E22), fontWeight = FontWeight.Medium)
//                                }
//                                Spacer(Modifier.height(4.dp))
//                                Text("旺盛原因：${it.reason}", fontSize = 14.sp)
//                                Text(it.desc, fontSize = 13.sp, color = Color(0xFF95A5A6), lineHeight = 20.sp)
//                            }
//                        }
//                    }
//                }
//            }
//
//            // 夫妻宫冲合影响
//            Text("夫妻宫冲 / 合 / 刑影响", fontWeight = FontWeight.Bold, fontSize = 18.sp)
//            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
//                palaceImpacts.forEach {
//                    val color = when (it.type) {
//                        "冲" -> Color(0xFFE74C3C)
//                        "合" -> Color(0xFF27AE60)
//                        "刑" -> Color(0xFFE67E22)
//                        else -> Color(0xFF95A5A6)
//                    }
//                    Card(
//                        modifier = Modifier.fillMaxWidth(),
//                        shape = RoundedCornerShape(12.dp)
//                    ) {
//                        Column(Modifier.padding(12.dp)) {
//                            Row(
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                modifier = Modifier.fillMaxWidth()
//                            ) {
//                                Text(it.source, fontWeight = FontWeight.Medium)
//                                Text(it.type, color = color, fontWeight = FontWeight.Bold)
//                            }
//                            Spacer(Modifier.height(4.dp))
//                            Text("干支：${it.fromGanZhi}", fontSize = 14.sp)
//                            Text(it.effect, fontSize = 13.sp, color = Color(0xFF95A5A6), lineHeight = 20.sp)
//                        }
//                    }
//                }
//            }
//
//            Spacer(Modifier.height(24.dp))
//        }
//    }
//}