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
)
