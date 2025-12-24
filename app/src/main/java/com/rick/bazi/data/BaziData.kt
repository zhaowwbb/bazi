package com.rick.bazi.data

const val MALE = "Male"
const val FEMALE = "Female"

data class BaziData(
//    val name: String = "",
//    var birthDateMili: Long = 0,

    var birthDateYear: Int = 0,
    var birthDateMonth: Int = 0,
    var birthDateDay: Int = 0,
    var birthHour: Int = 0,
//    val birthMinute: Int = 0,
    var gender: String = "",

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
    var daYunWeight : Int = 0,

    var baziYongShenList :  List<BaziYongShen> = listOf(),
)
