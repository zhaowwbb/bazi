package com.rick.bazi.data

import com.rick.bazi.R
import com.rick.bazi.data.BaziSummaryMSG.Companion.TAOHUA_0
import com.rick.bazi.data.BaziSummaryMSG.Companion.TAOHUA_1
import com.rick.bazi.data.BaziSummaryMSG.Companion.TAOHUA_2
import com.rick.bazi.data.BaziSummaryMSG.Companion.TAOHUA_3
import com.rick.bazi.data.BaziSummaryMSG.Companion.TAOHUA_4

const val MALE = "Male"
const val FEMALE = "Female"

data class BaziInfo(
    val name: String = "",
    val birthDateMili: Long = 0,
    val birthDateYear: Int = 0,
    val birthDateMonth: Int = 0,
    val birthDateDay: Int = 0,
    val birthHour: Int = 0,
    val birthMinute: Int = 0,
    val gender: String = "",
    val yearTiangan: TianGan = TianGan.TIANGAN_JIA,
    val monthTiangan: TianGan = TianGan.TIANGAN_YI,
    val dayTiangan: TianGan = TianGan.TIANGAN_BING,
    val hourTiangan: TianGan = TianGan.TIANGAN_DING,
    val yearDizhi: DiZhi = DiZhi.DIZHI_ZI,
    val monthDizhi: DiZhi = DiZhi.DIZHI_CHOU,
    val dayDizhi: DiZhi = DiZhi.DIZHI_YIN,
    val hourDizhi: DiZhi = DiZhi.DIZHI_MOU,

    val yearBase : Int = 1,
    val monthBase : Int = 1,
    val dayBase : Int = 1,
    val dayunForward : Boolean = true,
    val dayunDays : Int = 0,
    val isDangLing : Boolean = false,
    val strongRootNum : Int = 0,
    val mediumRootNum : Int = 0,
    val weakRootNum : Int = 0,

    val baziStr : String = "",
    val ownerStr : String = "",
    val wuxingSummaryStr : String = "",
    val deLingCheckStr : String = "",
    val deDiCheckStr : String = "",
    val deHelpStr : String = "",
    val keXieHaoStr : String = "",
    val shishenYearStr : String = "",
    val shishenMonthStr : String = "",
    val shishenDayStr : String = "",
    val shishenHourStr : String = "",
    val helpElementNum : Int = 0,
    val impedeElementNum : Int = 0,

    val yinCount : Int = 0,
    val bijieCount : Int = 0,
    val guanshaCount : Int = 0,
    val shishangCount : Int = 0,
    val caiCount : Int = 0,
    val yinString : String = "",
    val bijieString : String = "",

    val yearTgShiShen : ShiShen = ShiShen.SHISHEN_SHI_SHEN,
    val yearDzShiShen : ShiShen = ShiShen.SHISHEN_SHI_SHEN,
    val monthTgShiShen : ShiShen = ShiShen.SHISHEN_SHI_SHEN,
    val monthDzShiShen : ShiShen = ShiShen.SHISHEN_SHI_SHEN,
    val dayDzShiShen : ShiShen = ShiShen.SHISHEN_SHI_SHEN,
    val hourTgShiShen : ShiShen = ShiShen.SHISHEN_SHI_SHEN,
    val hourDzShiShen : ShiShen = ShiShen.SHISHEN_SHI_SHEN,

    val baziStrength : BaziStrength = BaziStrength.BAZI_BALANCED,
    val baziStrengthSummary : String = "",
    val isDedi : Boolean = false,
    val baziXiJiSummary : String = "",
    var baziGJ : BaziGeJu = BaziGeJu.GJ_QI_SHA,
    val baziGJSummary : String = "",
    val baziGJString : String = "",
    val baziDayunSummary : String = "",
    val xiyongShenList : List<WuXing> = arrayListOf<WuXing>(),
    val jiShenList : List<WuXing> = arrayListOf<WuXing>(),
    val tiaohouShenList : List<WuXing> = arrayListOf<WuXing>(),

    val tianganStrMap: Map<TianGan, Int> = mapOf(
        TianGan.TIANGAN_JIA to R.string.tiangan_jia,
        TianGan.TIANGAN_YI to R.string.tiangan_yi,
        TianGan.TIANGAN_BING to R.string.tiangan_bing,
        TianGan.TIANGAN_DING to R.string.tiangan_ding,
        TianGan.TIANGAN_WU to R.string.tiangan_wu,
        TianGan.TIANGAN_JI to R.string.tiangan_ji,
        TianGan.TIANGAN_GENG to R.string.tiangan_geng,
        TianGan.TIANGAN_XIN to R.string.tiangan_xin,
        TianGan.TIANGAN_REN to R.string.tiangan_ren,
        TianGan.TIANGAN_GUI to R.string.tiangan_gui
    ),

    val dizhiStrMap: Map<DiZhi, Int> = mapOf(
        DiZhi.DIZHI_ZI to R.string.dizhi_zi,
        DiZhi.DIZHI_CHOU to R.string.dizhi_chou,
        DiZhi.DIZHI_YIN to R.string.dizhi_yin,
        DiZhi.DIZHI_MOU to R.string.dizhi_mou,
        DiZhi.DIZHI_CHEN to R.string.dizhi_chen,
        DiZhi.DIZHI_SI to R.string.dizhi_si,
        DiZhi.DIZHI_WU to R.string.dizhi_wu,
        DiZhi.DIZHI_WEI to R.string.dizhi_wei,
        DiZhi.DIZHI_SHEN to R.string.dizhi_shen,
        DiZhi.DIZHI_YOU to R.string.dizhi_you,
        DiZhi.DIZHI_XU to R.string.dizhi_xu,
        DiZhi.DIZHI_HAI to R.string.dizhi_hai
    ),

    val tgLookupMap: Map<Int, TianGan> = mapOf(
        1 to TianGan.TIANGAN_JIA,
        2 to TianGan.TIANGAN_YI,
        3 to TianGan.TIANGAN_BING,
        4 to TianGan.TIANGAN_DING,
        5 to TianGan.TIANGAN_WU,
        6 to TianGan.TIANGAN_JI,
        7 to TianGan.TIANGAN_GENG,
        8 to TianGan.TIANGAN_XIN,
        9 to TianGan.TIANGAN_REN,
        0 to TianGan.TIANGAN_GUI
    ),

    val dzLookupMap: Map<Int, DiZhi> = mapOf(
        1 to DiZhi.DIZHI_ZI,
        2 to DiZhi.DIZHI_CHOU,
        3 to DiZhi.DIZHI_YIN,
        4 to DiZhi.DIZHI_MOU,
        5 to DiZhi.DIZHI_CHEN,
        6 to DiZhi.DIZHI_SI,
        7 to DiZhi.DIZHI_WU,
        8 to DiZhi.DIZHI_WEI,
        9 to DiZhi.DIZHI_SHEN,
        10 to DiZhi.DIZHI_YOU,
        11 to DiZhi.DIZHI_XU,
        0 to DiZhi.DIZHI_HAI
    ),

    val msgMap: Map<String, Int> = mapOf(
        TAOHUA_0 to R.string.msg_taohua_0,
        TAOHUA_1 to R.string.msg_taohua_1,
        TAOHUA_2 to R.string.msg_taohua_2,
        TAOHUA_3 to R.string.msg_taohua_3,
        TAOHUA_4 to R.string.msg_taohua_4
    )
)