package com.rick.bazi.data

import com.rick.bazi.model.DayMasterStrength
import com.rick.bazi.model.PatternType
import com.rick.bazi.model.StrengthLevel
import com.rick.bazi.model.YongXiJiResult
//import com.rick.bazi.util.getWuXingName

//import com.rick.bazi.util.StrengthLevel

const val MALE = "Male"
const val FEMALE = "Female"

data class BaziData(
//    val name: String = "",
//    var birthDateMili: Long = 0,

    var birthDateYear: Int = 0,
    var birthDateMonth: Int = 0,
    var birthDateDay: Int = 0,
    var birthHour: Int = 0,

    var gender: String = "",

    var birthMinute: Int = 0,
    var birthSecond: Int = 0,

    var yearTiangan: TianGan = TianGan.TIANGAN_JIA,
    var monthTiangan: TianGan = TianGan.TIANGAN_YI,
    var dayTiangan: TianGan = TianGan.TIANGAN_BING,
    var hourTiangan: TianGan = TianGan.TIANGAN_DING,
    var yearDizhi: DiZhi = DiZhi.DIZHI_ZI,
    var monthDizhi: DiZhi = DiZhi.DIZHI_CHOU,
    var dayDizhi: DiZhi = DiZhi.DIZHI_YIN,
    var hourDizhi: DiZhi = DiZhi.DIZHI_MOU,

    var yearBase : Int = 1,
    var monthBase : Int = 1,
    var dayBase : Int = 1,
    var dayunForward : Boolean = true,
    var dayunDays : Int = 0,
    var isDangLing : Boolean = false,
    var strongRootCount : Int = 0,
    var mediumRootCount : Int = 0,
    var weakRootCount : Int = 0,

    var yearDzRootLevel : RootLevel = RootLevel.NO_ROOT,
    var monthDzRootLevel : RootLevel = RootLevel.NO_ROOT,
    var dayDzRootLevel : RootLevel = RootLevel.NO_ROOT,
    var hourDzRootLevel : RootLevel = RootLevel.NO_ROOT,

    var yongShenList : List<ShiShen> = listOf(),
    var jiShenList : List<ShiShen> = listOf(),
    var xiShenList : List<ShiShen> = listOf(),
    var tiaohouShenList : List<ShiShen> = listOf(),
    var tongguanShenList : List<ShiShen> = listOf(),
    var tiaohouList : List<WuXing> = listOf(),
    var allYongShenList : List<ShiShen> = listOf(),

    var jinWeight : Float = 0f,
    var muWeight : Float = 0f,
    var shuiWeight : Float = 0f,
    var huoWeight : Float = 0f,
    var tuWeight : Float = 0f,
    var weights: MutableMap<WuXing, Float> = mutableMapOf(
        WuXing.WUXING_JIN to 0f,
        WuXing.WUXING_MU to 0f,
        WuXing.WUXING_SHUI to 0f,
        WuXing.WUXING_HUO to 0f,
        WuXing.WUXING_TU to 0f
    ),
//    var strengthLevel : StrengthLevel = StrengthLevel.MEDIUM,
    var dayMasterTotalScore : Float = 0f,
    var dayMasterWeightPercent : Float = 0f,
    var dayMasterStrength :DayMasterStrength = DayMasterStrength(
        wuxing = WuXing.WUXING_JIN,
        score = 0f.toInt(),
        isSeasonStrong = false,
        rootCount = 0,
        supportWeight = 0f,
        strengthLevel = StrengthLevel.MEDIUM
    ),
    var yongXiJiResult : YongXiJiResult = YongXiJiResult(
        yongShen = WuXing.WUXING_JIN,
        yongShenName = "",
        yongShenReason = "",
        xiShen = WuXing.WUXING_JIN,
        xiShenName = "",
        jiShenList = listOf(),
        jiShenNames = listOf(),
        tiaoHouShen = WuXing.WUXING_JIN,
        tongGuanShen = WuXing.WUXING_JIN,
        dayMasterPercent = 0f,
        strengthLevel = StrengthLevel.MEDIUM,
        patternType = PatternType.ZHENG_GE
    ),

    var yinWeight : Float = 0f,
    var bijieWeight : Float = 0f,
    var caiWeight : Float = 0f,
    var shishangWeight : Float = 0f,
    var guanshaWeight : Float = 0f,

    var tongguanYongShen : TongGuanYongShen = TongGuanYongShen.TONG_GUAN_NONE,
    var tiaohouYongShen : TiaoHouYongShen = TiaoHouYongShen.TIAO_HOU_NONE,
    var gj : BaziGeJu = BaziGeJu.GJ_NONE,
    var daYunStartSeconds : Int = 0,
    var daYunStartYear : Int = 0,
    var daYunStartMonth : Int = 0,
    var daYunStartDay : Int = 0,
    var daYunFirstYear : Int = 0,
    var daYunFirstMonth : Int = 0,
    var daYunWeight : Float = 0f,
    var liuNianWeight : Float = 0f,

    var baziYongShenList :  List<BaziYongShen> = listOf(),
    var xiyongShenSet : Set<ShiShen> = setOf(),
    var jiShenSet : Set<ShiShen> = setOf(),
    var yongShenPickMode: String = "",
)
