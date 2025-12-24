package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziColumnPosition
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.ColumnPosition
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.DiZhiSanHeInfo
import com.rick.bazi.data.DiZhiSanHuiInfo
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.WuXing
import com.rick.bazi.util.TianGanUtil.TianGanPos
import com.rick.bazi.util.TianGanUtil.TianGanaHeInfo

class DiZhiUtil {
    val diZhiCangGanMap : Map<DiZhi, Array<TianGan>> = mapOf(
        DiZhi.DIZHI_ZI to arrayOf(TianGan.TIANGAN_GUI),
        DiZhi.DIZHI_CHOU to arrayOf(TianGan.TIANGAN_JI, TianGan.TIANGAN_GUI,TianGan.TIANGAN_XIN ),
        DiZhi.DIZHI_YIN to arrayOf(TianGan.TIANGAN_JIA, TianGan.TIANGAN_BING, TianGan.TIANGAN_WU),
        DiZhi.DIZHI_MOU to arrayOf(TianGan.TIANGAN_YI),
        DiZhi.DIZHI_CHEN to arrayOf(TianGan.TIANGAN_WU, TianGan.TIANGAN_YI, TianGan.TIANGAN_GUI),
        DiZhi.DIZHI_SI to arrayOf(TianGan.TIANGAN_BING, TianGan.TIANGAN_GENG, TianGan.TIANGAN_WU),
        DiZhi.DIZHI_WU to arrayOf(TianGan.TIANGAN_DING, TianGan.TIANGAN_JI),
        DiZhi.DIZHI_WEI to arrayOf(TianGan.TIANGAN_JI, TianGan.TIANGAN_DING, TianGan.TIANGAN_YI),
        DiZhi.DIZHI_SHEN to arrayOf(TianGan.TIANGAN_GENG, TianGan.TIANGAN_REN, TianGan.TIANGAN_WU),
        DiZhi.DIZHI_YOU to arrayOf(TianGan.TIANGAN_XIN),
        DiZhi.DIZHI_XU to arrayOf(TianGan.TIANGAN_WU, TianGan.TIANGAN_XIN, TianGan.TIANGAN_DING),
        DiZhi.DIZHI_HAI to arrayOf(TianGan.TIANGAN_REN, TianGan.TIANGAN_JIA)
    )

    private val nextDiZhiMap : Map<DiZhi, DiZhi> = mapOf(
        DiZhi.DIZHI_ZI to DiZhi.DIZHI_CHOU,
        DiZhi.DIZHI_CHOU to DiZhi.DIZHI_YIN,
        DiZhi.DIZHI_YIN to DiZhi.DIZHI_MOU,
        DiZhi.DIZHI_MOU to DiZhi.DIZHI_CHEN,
        DiZhi.DIZHI_CHEN to DiZhi.DIZHI_SI,
        DiZhi.DIZHI_SI to DiZhi.DIZHI_WU,
        DiZhi.DIZHI_WU to DiZhi.DIZHI_WEI,
        DiZhi.DIZHI_WEI to DiZhi.DIZHI_SHEN,
        DiZhi.DIZHI_SHEN to DiZhi.DIZHI_YOU,
        DiZhi.DIZHI_YOU to DiZhi.DIZHI_XU,
        DiZhi.DIZHI_XU to DiZhi.DIZHI_HAI,
        DiZhi.DIZHI_HAI to DiZhi.DIZHI_ZI,
    )

    fun getNextDiZhi(dz: DiZhi) : DiZhi{
        return nextDiZhiMap[dz]!!
    }

    fun getPrevDiZhi(dz: DiZhi) : DiZhi{
        for((key, value) in nextDiZhiMap){
            if(value == dz)return key
        }
        return dz
    }

    fun getCanggan(dz: DiZhi): Array<TianGan> {
        return diZhiCangGanMap.get(dz)!!
    }

    val sixHeWuXingMap: Map<DiZhi, WuXing> = mapOf(
        DiZhi.DIZHI_ZI to WuXing.WUXING_TU,
        DiZhi.DIZHI_CHOU to WuXing.WUXING_TU,

        DiZhi.DIZHI_MOU to WuXing.WUXING_HUO,
        DiZhi.DIZHI_XU to WuXing.WUXING_HUO,

        DiZhi.DIZHI_WU to WuXing.WUXING_TU,
        DiZhi.DIZHI_WEI to WuXing.WUXING_TU,

        DiZhi.DIZHI_YIN to WuXing.WUXING_MU,
        DiZhi.DIZHI_HAI to WuXing.WUXING_MU,

        DiZhi.DIZHI_CHEN to WuXing.WUXING_JIN,
        DiZhi.DIZHI_YOU to WuXing.WUXING_JIN,

        DiZhi.DIZHI_SI to WuXing.WUXING_SHUI,
        DiZhi.DIZHI_SHEN to WuXing.WUXING_SHUI
    )

    val sixHeDetailMap: Map<Int, Int> = mapOf(
        R.string.app_bazi_dizhi_6he_zichou_desc to R.string.app_bazi_dizhi_6he_zichou_detail,
        R.string.app_bazi_dizhi_6he_mouxu_desc to R.string.app_bazi_dizhi_6he_mouxu_detail,
        R.string.app_bazi_dizhi_6he_wuwei_desc to R.string.app_bazi_dizhi_6he_wuwei_detail,
        R.string.app_bazi_dizhi_6he_yinhai_desc to R.string.app_bazi_dizhi_6he_yinhai_detail,
        R.string.app_bazi_dizhi_6he_chenyou_desc to R.string.app_bazi_dizhi_6he_chenyou_detail,
        R.string.app_bazi_dizhi_6he_sishen_desc to R.string.app_bazi_dizhi_6he_sishen_detail
    )

    val sixHeDescMap: Map<DiZhi, Int> = mapOf(
        DiZhi.DIZHI_ZI to R.string.app_bazi_dizhi_6he_zichou_desc,
        DiZhi.DIZHI_CHOU to R.string.app_bazi_dizhi_6he_zichou_desc,

        DiZhi.DIZHI_MOU to R.string.app_bazi_dizhi_6he_mouxu_desc,
        DiZhi.DIZHI_XU to R.string.app_bazi_dizhi_6he_mouxu_desc,

        DiZhi.DIZHI_WU to R.string.app_bazi_dizhi_6he_wuwei_desc,
        DiZhi.DIZHI_WEI to R.string.app_bazi_dizhi_6he_wuwei_desc,

        DiZhi.DIZHI_YIN to R.string.app_bazi_dizhi_6he_yinhai_desc,
        DiZhi.DIZHI_HAI to R.string.app_bazi_dizhi_6he_yinhai_desc,

        DiZhi.DIZHI_CHEN to R.string.app_bazi_dizhi_6he_chenyou_desc,
        DiZhi.DIZHI_YOU to R.string.app_bazi_dizhi_6he_chenyou_desc,

        DiZhi.DIZHI_SI to R.string.app_bazi_dizhi_6he_sishen_desc,
        DiZhi.DIZHI_SHEN to R.string.app_bazi_dizhi_6he_sishen_desc
    )

    val sanHeMap: Map<DiZhi, WuXing> = mapOf(
        DiZhi.DIZHI_YIN to WuXing.WUXING_HUO,
        DiZhi.DIZHI_WU to WuXing.WUXING_HUO,
        DiZhi.DIZHI_XU to WuXing.WUXING_HUO,

        DiZhi.DIZHI_SHEN to WuXing.WUXING_SHUI,
        DiZhi.DIZHI_ZI to WuXing.WUXING_SHUI,
        DiZhi.DIZHI_CHEN to WuXing.WUXING_SHUI,

        DiZhi.DIZHI_HAI to WuXing.WUXING_MU,
        DiZhi.DIZHI_MOU to WuXing.WUXING_MU,
        DiZhi.DIZHI_WEI to WuXing.WUXING_MU,

        DiZhi.DIZHI_SI to WuXing.WUXING_JIN,
        DiZhi.DIZHI_YOU to WuXing.WUXING_JIN,
        DiZhi.DIZHI_CHOU to WuXing.WUXING_JIN
    )

    val sanHuiMap: Map<DiZhi, WuXing> = mapOf(
        DiZhi.DIZHI_SI to WuXing.WUXING_HUO,
        DiZhi.DIZHI_WU to WuXing.WUXING_HUO,
        DiZhi.DIZHI_WEI to WuXing.WUXING_HUO,

        DiZhi.DIZHI_HAI to WuXing.WUXING_SHUI,
        DiZhi.DIZHI_ZI to WuXing.WUXING_SHUI,
        DiZhi.DIZHI_CHOU to WuXing.WUXING_SHUI,

        DiZhi.DIZHI_YIN to WuXing.WUXING_MU,
        DiZhi.DIZHI_MOU to WuXing.WUXING_MU,
        DiZhi.DIZHI_CHEN to WuXing.WUXING_MU,

        DiZhi.DIZHI_SHEN to WuXing.WUXING_JIN,
        DiZhi.DIZHI_YOU to WuXing.WUXING_JIN,
        DiZhi.DIZHI_XU to WuXing.WUXING_JIN
    )

    val dizhiChongMap: Map<DiZhi, DiZhi> = mapOf(
        DiZhi.DIZHI_ZI to DiZhi.DIZHI_WU,
        DiZhi.DIZHI_WU to DiZhi.DIZHI_ZI,

        DiZhi.DIZHI_CHOU to DiZhi.DIZHI_WEI,
        DiZhi.DIZHI_WEI to DiZhi.DIZHI_CHOU,

        DiZhi.DIZHI_YIN to DiZhi.DIZHI_SHEN,
        DiZhi.DIZHI_SHEN to DiZhi.DIZHI_YIN,

        DiZhi.DIZHI_MOU to DiZhi.DIZHI_YOU,
        DiZhi.DIZHI_YOU to DiZhi.DIZHI_MOU,

        DiZhi.DIZHI_CHEN to DiZhi.DIZHI_XU,
        DiZhi.DIZHI_XU to DiZhi.DIZHI_CHEN,

        DiZhi.DIZHI_SI to DiZhi.DIZHI_HAI,
        DiZhi.DIZHI_HAI to DiZhi.DIZHI_SI
    )

    val dizhiChongStrMap: Map<DiZhi, Int> = mapOf(
        DiZhi.DIZHI_ZI to R.string.app_bazi_dizhi_6chong_ziwu,
        DiZhi.DIZHI_WU to R.string.app_bazi_dizhi_6chong_ziwu,

        DiZhi.DIZHI_CHOU to R.string.app_bazi_dizhi_6chong_chouwei,
        DiZhi.DIZHI_WEI to R.string.app_bazi_dizhi_6chong_chouwei,

        DiZhi.DIZHI_YIN to R.string.app_bazi_dizhi_6chong_yinshen,
        DiZhi.DIZHI_SHEN to R.string.app_bazi_dizhi_6chong_yinshen,

        DiZhi.DIZHI_MOU to R.string.app_bazi_dizhi_6chong_mouyou,
        DiZhi.DIZHI_YOU to R.string.app_bazi_dizhi_6chong_mouyou,

        DiZhi.DIZHI_CHEN to R.string.app_bazi_dizhi_6chong_chenxu,
        DiZhi.DIZHI_XU to R.string.app_bazi_dizhi_6chong_chenxu,

        DiZhi.DIZHI_SI to R.string.app_bazi_dizhi_6chong_sishai,
        DiZhi.DIZHI_HAI to R.string.app_bazi_dizhi_6chong_sishai
    )

    val dizhiHaiMap: Map<DiZhi, DiZhi> = mapOf(
        DiZhi.DIZHI_ZI to DiZhi.DIZHI_WEI,
        DiZhi.DIZHI_WEI to DiZhi.DIZHI_ZI,

        DiZhi.DIZHI_CHOU to DiZhi.DIZHI_WU,
        DiZhi.DIZHI_WU to DiZhi.DIZHI_CHOU,

        DiZhi.DIZHI_YIN to DiZhi.DIZHI_SI,
        DiZhi.DIZHI_SI to DiZhi.DIZHI_YIN,

        DiZhi.DIZHI_MOU to DiZhi.DIZHI_CHEN,
        DiZhi.DIZHI_CHEN to DiZhi.DIZHI_MOU,

        DiZhi.DIZHI_SHEN to DiZhi.DIZHI_HAI,
        DiZhi.DIZHI_HAI to DiZhi.DIZHI_SHEN,

        DiZhi.DIZHI_YOU to DiZhi.DIZHI_XU,
        DiZhi.DIZHI_XU to DiZhi.DIZHI_YOU
    )

    val dizhiXingMap: Map<DiZhi, DiZhi> = mapOf(
        DiZhi.DIZHI_ZI to DiZhi.DIZHI_MOU,
        DiZhi.DIZHI_MOU to DiZhi.DIZHI_ZI,

        DiZhi.DIZHI_YIN to DiZhi.DIZHI_SI,
        DiZhi.DIZHI_SI to DiZhi.DIZHI_SHEN,
        DiZhi.DIZHI_SHEN to DiZhi.DIZHI_YIN,

        DiZhi.DIZHI_CHOU to DiZhi.DIZHI_WEI,
        DiZhi.DIZHI_WEI to DiZhi.DIZHI_XU,
        DiZhi.DIZHI_XU to DiZhi.DIZHI_CHOU,

        DiZhi.DIZHI_CHEN to DiZhi.DIZHI_CHEN,
        DiZhi.DIZHI_WU to DiZhi.DIZHI_WU,
        DiZhi.DIZHI_YOU to DiZhi.DIZHI_YOU,
        DiZhi.DIZHI_HAI to DiZhi.DIZHI_HAI
    )

    val dizhiXingStrMap: Map<DiZhi, Int> = mapOf(
        DiZhi.DIZHI_ZI to R.string.app_bazi_dizhi_xing_zimou_desc,
        DiZhi.DIZHI_MOU to R.string.app_bazi_dizhi_xing_zimou_desc,

        DiZhi.DIZHI_YIN to R.string.app_bazi_dizhi_xing_yisishen_desc,
        DiZhi.DIZHI_SI to R.string.app_bazi_dizhi_xing_yisishen_desc,
        DiZhi.DIZHI_SHEN to R.string.app_bazi_dizhi_xing_yisishen_desc,

        DiZhi.DIZHI_CHOU to R.string.app_bazi_dizhi_xing_chouweixu_desc,
        DiZhi.DIZHI_WEI to R.string.app_bazi_dizhi_xing_chouweixu_desc,
        DiZhi.DIZHI_XU to R.string.app_bazi_dizhi_xing_chouweixu_desc,

        DiZhi.DIZHI_CHEN to R.string.app_bazi_dizhi_xing_chenchen_desc,
        DiZhi.DIZHI_WU to R.string.app_bazi_dizhi_xing_wuwu_desc,
        DiZhi.DIZHI_YOU to R.string.app_bazi_dizhi_xing_youyou_desc,
        DiZhi.DIZHI_HAI to R.string.app_bazi_dizhi_xing_haihai_desc
    )

    fun isDiZhiTouTianGan(tg: TianGan, data: BaziData): Boolean {
        if (tg == data.yearTiangan) return true
        if (tg == data.monthTiangan) return true
//        if (tg == baziInfo.dayTiangan) return true
        if (tg == data.hourTiangan) return true
        return false
    }

    fun isDiZhiXiangXing(dz1: DiZhi, dz2: DiZhi): Boolean {
        var ret = false
        var dz = dizhiXingMap.get(dz1)!!
        if (dz == dz2) {
            ret = true
        }
        return ret
    }


    fun isDiZhiHai(dz1: DiZhi, dz2: DiZhi): Boolean {
        var ret = false
        var dz = dizhiHaiMap.get(dz1)!!
        if (dz == dz2) {
            ret = true
        }
        return ret
    }

    fun isDiZhiChong(dz1: DiZhi, dz2: DiZhi): Boolean {
        var ret = false
        var dz = dizhiChongMap.get(dz1)!!
        if (dz == dz2) {
            ret = true
        }
        return ret
    }

    fun isDiZhiSanHe(dz1: DiZhi, dz2: DiZhi, dz3: DiZhi): Boolean {
        var ret = false
        //sort 3 DiZhi
        var dzSmall = dz1
        var dzMedium = dz2
        var dzLarge = dz1
        if (dz2 < dzSmall) {
            dzSmall = dz2
        }
        if (dz3 < dzSmall) {
            dzSmall = dz3
        }
        if (dz2 > dzLarge) {
            dzLarge = dz2
        }
        if (dz3 > dzLarge) {
            dzLarge = dz3
        }
        if (dz1 != dzSmall && dz1 != dzLarge) {
            dzMedium = dz1
        }
        if (dz2 != dzSmall && dz2 != dzLarge) {
            dzMedium = dz2
        }
        if (dz3 != dzSmall && dz3 != dzLarge) {
            dzMedium = dz3
        }

        //寅午戌三合火局
        if (dzSmall == DiZhi.DIZHI_YIN && dzMedium == DiZhi.DIZHI_WU && dzLarge == DiZhi.DIZHI_XU) {
            ret = true
        }
        //申子辰三合水局
        if (dzSmall == DiZhi.DIZHI_ZI && dzMedium == DiZhi.DIZHI_CHEN && dzLarge == DiZhi.DIZHI_SHEN) {
            ret = true
        }
        //亥卯未三合木局
        if (dzSmall == DiZhi.DIZHI_MOU && dzMedium == DiZhi.DIZHI_WEI && dzLarge == DiZhi.DIZHI_HAI) {
            ret = true
        }
        //巳酉丑三合金局
        if (dzSmall == DiZhi.DIZHI_CHOU && dzMedium == DiZhi.DIZHI_SI && dzLarge == DiZhi.DIZHI_YOU) {
            ret = true
        }
        return ret
    }

    fun isDiZhiSanHui(dz1: DiZhi, dz2: DiZhi, dz3: DiZhi): Boolean {
        var ret = false
        //sort 3 DiZhi
        var dzSmall = dz1
        var dzMedium = dz2
        var dzLarge = dz1
        if (dz2 < dzSmall) {
            dzSmall = dz2
        }
        if (dz3 < dzSmall) {
            dzSmall = dz3
        }
        if (dz2 > dzLarge) {
            dzLarge = dz2
        }
        if (dz3 > dzLarge) {
            dzLarge = dz3
        }
        if (dz1 != dzSmall && dz1 != dzLarge) {
            dzMedium = dz1
        }
        if (dz2 != dzSmall && dz2 != dzLarge) {
            dzMedium = dz2
        }
        if (dz3 != dzSmall && dz3 != dzLarge) {
            dzMedium = dz3
        }

        //寅卯辰会成东方木
        if (dzSmall == DiZhi.DIZHI_YIN && dzMedium == DiZhi.DIZHI_MOU && dzLarge == DiZhi.DIZHI_CHEN) {
            ret = true
        }
        //巳午未会成南方火
        if (dzSmall == DiZhi.DIZHI_SI && dzMedium == DiZhi.DIZHI_WU && dzLarge == DiZhi.DIZHI_WEI) {
            ret = true
        }
        //申酉戌会成西方金
        if (dzSmall == DiZhi.DIZHI_SHEN && dzMedium == DiZhi.DIZHI_YOU && dzLarge == DiZhi.DIZHI_XU) {
            ret = true
        }
        //亥子丑会成北方水
        if (dzSmall == DiZhi.DIZHI_ZI && dzMedium == DiZhi.DIZHI_CHOU && dzLarge == DiZhi.DIZHI_HAI) {
            ret = true
        }
        return ret
    }

    fun hasDiZhiSanHe(data: BaziData): Boolean {
        var isSanHe = false
        isSanHe = isDiZhiSanHe(data.yearDizhi, data.monthDizhi, data.dayDizhi)
        if (isSanHe) {
            return true
        }

        isSanHe = isDiZhiSanHe(data.yearDizhi, data.monthDizhi, data.hourDizhi)
        if (isSanHe) {
            return true
        }

        isSanHe = isDiZhiSanHe(data.monthDizhi, data.dayDizhi, data.hourDizhi)
        if (isSanHe) {
            return true
        }

        isSanHe = isDiZhiSanHe(data.yearDizhi, data.dayDizhi, data.hourDizhi)
        if (isSanHe) {
            return true
        }
        return isSanHe
    }

    fun hasDiZhiSanHui(data: BaziData): Boolean {
        var isSanHui = false
        isSanHui = isDiZhiSanHui(data.yearDizhi, data.monthDizhi, data.dayDizhi)
        if (isSanHui) {
            return true
        }

        isSanHui = isDiZhiSanHui(data.yearDizhi, data.monthDizhi, data.hourDizhi)
        if (isSanHui) {
            return true
        }

        isSanHui = isDiZhiSanHui(data.monthDizhi, data.dayDizhi, data.hourDizhi)
        if (isSanHui) {
            return true
        }

        isSanHui = isDiZhiSanHui(data.yearDizhi, data.dayDizhi, data.hourDizhi)
        if (isSanHui) {
            return true
        }
        return isSanHui
    }

    fun getSanHeWuXing(data: BaziData): WuXing {
        var wx = WuXing.WUXING_MU
        var wx1 = sanHeMap.get(data.yearDizhi)!!
        var wx2 = sanHeMap.get(data.monthDizhi)!!
        var wx3 = sanHeMap.get(data.dayDizhi)!!
        if (wx1 == wx2) {
            return wx1
        } else if (wx1 == wx3) {
            return wx3
        } else if (wx2 == wx3) {
            return wx2
        }

        return wx
    }

    fun getSanHuiWuXing(data: BaziData): WuXing {
        var wx = WuXing.WUXING_MU
        var wx1 = sanHuiMap.get(data.yearDizhi)!!
        var wx2 = sanHuiMap.get(data.monthDizhi)!!
        var wx3 = sanHuiMap.get(data.dayDizhi)!!
        if (wx1 == wx2) {
            return wx1
        } else if (wx1 == wx3) {
            return wx3
        } else if (wx2 == wx3) {
            return wx2
        }
        return wx
    }

    @Composable
    fun getSanHeStringByWuXing(wx: WuXing): String {
        var sb = StringBuilder()
        sb.append("    ")
        if (wx == WuXing.WUXING_HUO) {
            sb.append(stringResource(R.string.app_bazi_dizhi_3he_huo_desc))
        } else if (wx == WuXing.WUXING_SHUI) {
            sb.append(stringResource(R.string.app_bazi_dizhi_3he_shui_desc))
        } else if (wx == WuXing.WUXING_MU) {
            sb.append(stringResource(R.string.app_bazi_dizhi_3he_mu_desc))
        } else if (wx == WuXing.WUXING_JIN) {
            sb.append(stringResource(R.string.app_bazi_dizhi_3he_jin_desc))
        }
        return sb.toString()
    }

    @Composable
    fun getSanHuiStringByWuXing(wx: WuXing): String {
        var sb = StringBuilder()
        sb.append("    ")
        if (wx == WuXing.WUXING_HUO) {
            sb.append(stringResource(R.string.app_bazi_dizhi_3hui_huo_desc))
        } else if (wx == WuXing.WUXING_SHUI) {
            sb.append(stringResource(R.string.app_bazi_dizhi_3hui_shui_desc))
        } else if (wx == WuXing.WUXING_MU) {
            sb.append(stringResource(R.string.app_bazi_dizhi_3hui_mu_desc))
        } else if (wx == WuXing.WUXING_JIN) {
            sb.append(stringResource(R.string.app_bazi_dizhi_3hui_jin_desc))
        }
        return sb.toString()
    }

    fun getDiZhi3He(data: BaziData): MutableList<DiZhiSanHeInfo> {
        var isSanHe = false
        var wx = WuXing.WUXING_MU
        val heList: MutableList<DiZhiSanHeInfo> = mutableListOf()
        isSanHe = isDiZhiSanHe(data.yearDizhi, data.monthDizhi, data.dayDizhi)
        if (isSanHe) {
            wx = sanHeMap.get(data.yearDizhi)!!
            heList.add(
                DiZhiSanHeInfo(
                    ColumnPosition.COLUMN_YEAR,
                    data.yearDizhi,
                    ColumnPosition.COLUMN_MONTH,
                    data.monthDizhi,
                    ColumnPosition.COLUMN_DAY,
                    data.dayDizhi,
                    wx
                )
            )
        }

        isSanHe = isDiZhiSanHe(data.yearDizhi, data.monthDizhi, data.hourDizhi)
        if (isSanHe) {
            wx = sanHeMap.get(data.yearDizhi)!!
            heList.add(
                DiZhiSanHeInfo(
                    ColumnPosition.COLUMN_YEAR,
                    data.yearDizhi,
                    ColumnPosition.COLUMN_MONTH,
                    data.monthDizhi,
                    ColumnPosition.COLUMN_HOUR,
                    data.hourDizhi,
                    wx
                )
            )
        }

        isSanHe = isDiZhiSanHe(data.monthDizhi, data.dayDizhi, data.hourDizhi)
        if (isSanHe) {
            wx = sanHeMap.get(data.monthDizhi)!!
            heList.add(
                DiZhiSanHeInfo(
                    ColumnPosition.COLUMN_MONTH,
                    data.monthDizhi,
                    ColumnPosition.COLUMN_DAY,
                    data.dayDizhi,
                    ColumnPosition.COLUMN_HOUR,
                    data.hourDizhi,
                    wx
                )
            )
        }

        isSanHe = isDiZhiSanHe(data.yearDizhi, data.dayDizhi, data.hourDizhi)
        if (isSanHe) {
            wx = sanHeMap.get(data.yearDizhi)!!
            heList.add(
                DiZhiSanHeInfo(
                    ColumnPosition.COLUMN_YEAR,
                    data.yearDizhi,
                    ColumnPosition.COLUMN_DAY,
                    data.dayDizhi,
                    ColumnPosition.COLUMN_HOUR,
                    data.hourDizhi,
                    wx
                )
            )
        }
        return heList
    }

    fun getDiZhi3Hui(data: BaziData): MutableList<DiZhiSanHuiInfo> {
        var isSanHui = false
        var wx = WuXing.WUXING_MU
        val huiList: MutableList<DiZhiSanHuiInfo> = mutableListOf()
        isSanHui = isDiZhiSanHui(data.yearDizhi, data.monthDizhi, data.dayDizhi)
        if (isSanHui) {
            wx = sanHuiMap.get(data.yearDizhi)!!
            huiList.add(
                DiZhiSanHuiInfo(
                    ColumnPosition.COLUMN_YEAR,
                    data.yearDizhi,
                    ColumnPosition.COLUMN_MONTH,
                    data.monthDizhi,
                    ColumnPosition.COLUMN_DAY,
                    data.dayDizhi,
                    wx
                )
            )
        }

        isSanHui = isDiZhiSanHui(data.yearDizhi, data.monthDizhi, data.hourDizhi)
        if (isSanHui) {
            wx = sanHuiMap.get(data.yearDizhi)!!
            huiList.add(
                DiZhiSanHuiInfo(
                    ColumnPosition.COLUMN_YEAR,
                    data.yearDizhi,
                    ColumnPosition.COLUMN_MONTH,
                    data.monthDizhi,
                    ColumnPosition.COLUMN_HOUR,
                    data.hourDizhi,
                    wx
                )
            )
        }

        isSanHui = isDiZhiSanHui(data.monthDizhi, data.dayDizhi, data.hourDizhi)
        if (isSanHui) {
            wx = sanHuiMap.get(data.monthDizhi)!!
            huiList.add(
                DiZhiSanHuiInfo(
                    ColumnPosition.COLUMN_MONTH,
                    data.monthDizhi,
                    ColumnPosition.COLUMN_DAY,
                    data.dayDizhi,
                    ColumnPosition.COLUMN_HOUR,
                    data.hourDizhi,
                    wx
                )
            )
        }

        isSanHui = isDiZhiSanHui(data.yearDizhi, data.dayDizhi, data.hourDizhi)
        if (isSanHui) {
            wx = sanHuiMap.get(data.yearDizhi)!!
            huiList.add(
                DiZhiSanHuiInfo(
                    ColumnPosition.COLUMN_YEAR,
                    data.yearDizhi,
                    ColumnPosition.COLUMN_DAY,
                    data.dayDizhi,
                    ColumnPosition.COLUMN_HOUR,
                    data.hourDizhi,
                    wx
                )
            )
        }
        return huiList
    }

    @Composable
    fun getDiZhi3HeString(data: BaziData): String {
        var sb = StringBuilder()
        var isSanHe = false
        var wx = WuXing.WUXING_MU

        isSanHe = isDiZhiSanHe(data.yearDizhi, data.monthDizhi, data.dayDizhi)
        if (isSanHe) {
            wx = sanHeMap.get(data.yearDizhi)!!
            sb.append(getSanHeStringByWuXing(wx))
        }

        isSanHe = isDiZhiSanHe(data.yearDizhi, data.monthDizhi, data.hourDizhi)
        if (isSanHe) {
            wx = sanHeMap.get(data.yearDizhi)!!
            sb.append(getSanHeStringByWuXing(wx))
        }

        isSanHe = isDiZhiSanHe(data.monthDizhi, data.dayDizhi, data.hourDizhi)
        if (isSanHe) {
            wx = sanHeMap.get(data.monthDizhi)!!
            sb.append(getSanHeStringByWuXing(wx))
        }

        isSanHe = isDiZhiSanHe(data.yearDizhi, data.dayDizhi, data.hourDizhi)
        if (isSanHe) {
            wx = sanHeMap.get(data.yearDizhi)!!
            sb.append(getSanHeStringByWuXing(wx))
        }

        //no sanhe
        if (sb.length == 0) {
            sb.append("    ")
            sb.append(stringResource(R.string.app_bazi_dizhi_3he_no_label))
        }
        return sb.toString()
    }

    @Composable
    fun getDiZhi3HuiString(data: BaziData): String {
        var sb = StringBuilder()
        var isSanHui = false
        var wx = WuXing.WUXING_MU

        isSanHui = isDiZhiSanHui(data.yearDizhi, data.monthDizhi, data.dayDizhi)
        if (isSanHui) {
            wx = sanHuiMap.get(data.yearDizhi)!!
            sb.append(getSanHuiStringByWuXing(wx))
        }

        isSanHui = isDiZhiSanHui(data.yearDizhi, data.monthDizhi, data.hourDizhi)
        if (isSanHui) {
            wx = sanHuiMap.get(data.yearDizhi)!!
            sb.append(getSanHuiStringByWuXing(wx))
        }

        isSanHui = isDiZhiSanHui(data.monthDizhi, data.dayDizhi, data.hourDizhi)
        if (isSanHui) {
            wx = sanHuiMap.get(data.monthDizhi)!!
            sb.append(getSanHuiStringByWuXing(wx))
        }

        isSanHui = isDiZhiSanHui(data.yearDizhi, data.dayDizhi, data.hourDizhi)
        if (isSanHui) {
            wx = sanHuiMap.get(data.yearDizhi)!!
            sb.append(getSanHuiStringByWuXing(wx))
        }

        //no sanhui
        if (sb.length == 0) {
            sb.append("    ")
            sb.append(stringResource(R.string.app_bazi_dizhi_3hui_no_label))
        }
        return sb.toString()
    }

    fun isDiZhi6He(dz1: DiZhi, dz2: DiZhi): Boolean {
        var ret = false
        if (dz1 == DiZhi.DIZHI_ZI && dz2 == DiZhi.DIZHI_CHOU) {
            return true
        }
        if (dz1 == DiZhi.DIZHI_CHOU && dz2 == DiZhi.DIZHI_ZI) {
            return true
        }

        if (dz1 == DiZhi.DIZHI_YIN && dz2 == DiZhi.DIZHI_HAI) {
            return true
        }
        if (dz1 == DiZhi.DIZHI_HAI && dz2 == DiZhi.DIZHI_YIN) {
            return true
        }

        if (dz1 == DiZhi.DIZHI_MOU && dz2 == DiZhi.DIZHI_XU) {
            return true
        }
        if (dz1 == DiZhi.DIZHI_XU && dz2 == DiZhi.DIZHI_MOU) {
            return true
        }

        if (dz1 == DiZhi.DIZHI_CHEN && dz2 == DiZhi.DIZHI_YOU) {
            return true
        }
        if (dz1 == DiZhi.DIZHI_YOU && dz2 == DiZhi.DIZHI_CHEN) {
            return true
        }

        if (dz1 == DiZhi.DIZHI_SI && dz2 == DiZhi.DIZHI_SHEN) {
            return true
        }
        if (dz1 == DiZhi.DIZHI_SHEN && dz2 == DiZhi.DIZHI_SI) {
            return true
        }

        if (dz1 == DiZhi.DIZHI_WU && dz2 == DiZhi.DIZHI_WEI) {
            return true
        }
        if (dz1 == DiZhi.DIZHI_WEI && dz2 == DiZhi.DIZHI_WU) {
            return true
        }

        return ret
    }

    @Composable
    fun getDiZhi6HeText(
        dz1: DiZhi,
        dz1Column: BaziColumnPosition,
        dz2: DiZhi,
        dz2Column: BaziColumnPosition
    ): String {
        var sb = StringBuilder()
        var dz1StrId = 0
        var dz2StrId = 0
        if (dz1Column == BaziColumnPosition.COLUMN_YEAR) {
            dz1StrId = R.string.app_bazi_dizhi_year_label
        } else if (dz1Column == BaziColumnPosition.COLUMN_MONTH) {
            dz1StrId = R.string.app_bazi_dizhi_month_label
        } else if (dz1Column == BaziColumnPosition.COLUMN_DAY) {
            dz1StrId = R.string.app_bazi_dizhi_day_label
        } else if (dz1Column == BaziColumnPosition.COLUMN_HOUR) {
            dz1StrId = R.string.app_bazi_dizhi_hour_label
        }

        if (dz2Column == BaziColumnPosition.COLUMN_YEAR) {
            dz2StrId = R.string.app_bazi_dizhi_year_label
        } else if (dz2Column == BaziColumnPosition.COLUMN_MONTH) {
            dz2StrId = R.string.app_bazi_dizhi_month_label
        } else if (dz2Column == BaziColumnPosition.COLUMN_DAY) {
            dz2StrId = R.string.app_bazi_dizhi_day_label
        } else if (dz2Column == BaziColumnPosition.COLUMN_HOUR) {
            dz2StrId = R.string.app_bazi_dizhi_hour_label
        }
        sb.append("    ")
        sb.append(stringResource(dz1StrId))
        sb.append("[")
        sb.append(BaziUtil().getDizhiText(dz1))
        sb.append("] ")
        sb.append(stringResource(dz2StrId))
        sb.append("[")
        sb.append(BaziUtil().getDizhiText(dz2))
        sb.append("] ")
        sb.append(stringResource(sixHeDescMap.get(dz1)!!))
        sb.append(" ")
        return sb.toString()
    }

    @Composable
    fun getDiZhi6HeString(data: BaziData): String {
        var dz1 = data.yearDizhi
        var dz2 = data.monthDizhi
        var detailSet: MutableSet<Int> = mutableSetOf()
        var strId = 0
        var detailId = 0
        var sb = StringBuilder()
        if (isDiZhi6He(dz1, dz2)) {
            strId = sixHeDescMap.get(dz1)!!
            detailSet.add(strId)
            sb.append(
                getDiZhi6HeText(
                    dz1,
                    BaziColumnPosition.COLUMN_YEAR,
                    dz2,
                    BaziColumnPosition.COLUMN_MONTH
                )
            )
        }
        dz2 = data.dayDizhi
        if (isDiZhi6He(dz1, dz2)) {
            strId = sixHeDescMap.get(dz1)!!
            detailSet.add(strId)
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhi6HeText(
                    dz1,
                    BaziColumnPosition.COLUMN_YEAR,
                    dz2,
                    BaziColumnPosition.COLUMN_DAY
                )
            )
        }
        dz2 = data.hourDizhi
        if (isDiZhi6He(dz1, dz2)) {
            strId = sixHeDescMap.get(dz1)!!
            detailSet.add(strId)
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhi6HeText(
                    dz1,
                    BaziColumnPosition.COLUMN_YEAR,
                    dz2,
                    BaziColumnPosition.COLUMN_HOUR
                )
            )
        }
        dz1 = data.monthDizhi
        dz2 = data.dayDizhi
        if (isDiZhi6He(dz1, dz2)) {
            strId = sixHeDescMap.get(dz1)!!
            detailSet.add(strId)
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhi6HeText(
                    dz1,
                    BaziColumnPosition.COLUMN_MONTH,
                    dz2,
                    BaziColumnPosition.COLUMN_DAY
                )
            )
        }

        dz1 = data.monthDizhi
        dz2 = data.hourDizhi
        if (isDiZhi6He(dz1, dz2)) {
            strId = sixHeDescMap.get(dz1)!!
            detailSet.add(strId)
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhi6HeText(
                    dz1,
                    BaziColumnPosition.COLUMN_MONTH,
                    dz2,
                    BaziColumnPosition.COLUMN_HOUR
                )
            )
        }

        dz1 = data.dayDizhi
        dz2 = data.hourDizhi
        if (isDiZhi6He(dz1, dz2)) {
            strId = sixHeDescMap.get(dz1)!!
            detailSet.add(strId)
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhi6HeText(
                    dz1,
                    BaziColumnPosition.COLUMN_DAY,
                    dz2,
                    BaziColumnPosition.COLUMN_HOUR
                )
            )
        }

        if (sb.length == 0) {
            sb.append("    ")
            sb.append(stringResource(R.string.app_bazi_dizhi_6he_no_label))
        } else {
            sb.append("\n")
            var sbb = StringBuilder()
            for (descId in detailSet) {
                if (sbb.length > 0) {
                    sbb.append("\n")
                }
                detailId = sixHeDetailMap.get(descId)!!
                sbb.append("    ")
                sbb.append(stringResource(detailId))
            }
            sb.append(sbb)
        }
        return sb.toString()
    }

    @Composable
    fun getDiZhi6ChongText(
        dz1: DiZhi,
        dz1Column: BaziColumnPosition,
        dz2: DiZhi,
        dz2Column: BaziColumnPosition
    ): String {
        var sb = StringBuilder()
        var dz1StrId = 0
        var dz2StrId = 0
        if (dz1Column == BaziColumnPosition.COLUMN_YEAR) {
            dz1StrId = R.string.app_bazi_dizhi_year_label
        } else if (dz1Column == BaziColumnPosition.COLUMN_MONTH) {
            dz1StrId = R.string.app_bazi_dizhi_month_label
        } else if (dz1Column == BaziColumnPosition.COLUMN_DAY) {
            dz1StrId = R.string.app_bazi_dizhi_day_label
        } else if (dz1Column == BaziColumnPosition.COLUMN_HOUR) {
            dz1StrId = R.string.app_bazi_dizhi_hour_label
        }

        if (dz2Column == BaziColumnPosition.COLUMN_YEAR) {
            dz2StrId = R.string.app_bazi_dizhi_year_label
        } else if (dz2Column == BaziColumnPosition.COLUMN_MONTH) {
            dz2StrId = R.string.app_bazi_dizhi_month_label
        } else if (dz2Column == BaziColumnPosition.COLUMN_DAY) {
            dz2StrId = R.string.app_bazi_dizhi_day_label
        } else if (dz2Column == BaziColumnPosition.COLUMN_HOUR) {
            dz2StrId = R.string.app_bazi_dizhi_hour_label
        }
        sb.append("    ")
        sb.append(stringResource(dz1StrId))
        sb.append("[")
        sb.append(BaziUtil().getDizhiText(dz1))
        sb.append("] ")
        sb.append(stringResource(dz2StrId))
        sb.append("[")
        sb.append(BaziUtil().getDizhiText(dz2))
        sb.append("] ")
        sb.append(stringResource(dizhiChongStrMap.get(dz1)!!))
        sb.append(" ")
        return sb.toString()
    }

    @Composable
    fun getDiZhi6ChongString(data: BaziData): String {
        var dz1 = data.yearDizhi
        var dz2 = data.monthDizhi
        var sb = StringBuilder()
        if (isDiZhiChong(dz1, dz2)) {
            sb.append(
                getDiZhi6ChongText(
                    dz1,
                    BaziColumnPosition.COLUMN_YEAR,
                    dz2,
                    BaziColumnPosition.COLUMN_MONTH
                )
            )
        }

        dz2 = data.dayDizhi
        if (isDiZhiChong(dz1, dz2)) {
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhi6ChongText(
                    dz1,
                    BaziColumnPosition.COLUMN_YEAR,
                    dz2,
                    BaziColumnPosition.COLUMN_DAY
                )
            )
        }
        dz2 = data.hourDizhi
        if (isDiZhiChong(dz1, dz2)) {
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhi6ChongText(
                    dz1,
                    BaziColumnPosition.COLUMN_YEAR,
                    dz2,
                    BaziColumnPosition.COLUMN_HOUR
                )
            )
        }
        dz1 = data.monthDizhi
        dz2 = data.dayDizhi
        if (isDiZhiChong(dz1, dz2)) {
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhi6ChongText(
                    dz1,
                    BaziColumnPosition.COLUMN_MONTH,
                    dz2,
                    BaziColumnPosition.COLUMN_DAY
                )
            )
        }

        dz1 = data.monthDizhi
        dz2 = data.hourDizhi
        if (isDiZhiChong(dz1, dz2)) {
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhi6ChongText(
                    dz1,
                    BaziColumnPosition.COLUMN_MONTH,
                    dz2,
                    BaziColumnPosition.COLUMN_HOUR
                )
            )
        }

        dz1 = data.dayDizhi
        dz2 = data.hourDizhi
        if (isDiZhiChong(dz1, dz2)) {
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhi6ChongText(
                    dz1,
                    BaziColumnPosition.COLUMN_DAY,
                    dz2,
                    BaziColumnPosition.COLUMN_HOUR
                )
            )
        }

        if (sb.length == 0) {
            sb.append("    ")
            sb.append(stringResource(R.string.app_bazi_dizhi_6chong_no_label))
        } else {
//            sb.append("\n")
//            var sbb = StringBuilder()
//            for(descId in detailSet){
//                if(sbb.length > 0){
//                    sbb.append("\n")
//                }
//                detailId = sixHeDetailMap.get(descId)!!
//                sbb.append("    ")
//                sbb.append(stringResource(detailId))
//            }
//            sb.append(sbb)
        }
        return sb.toString()
    }

    @Composable
    fun getDiZhi6HaiText(
        dz1: DiZhi,
        dz1Column: BaziColumnPosition,
        dz2: DiZhi,
        dz2Column: BaziColumnPosition
    ): String {
        var sb = StringBuilder()
        var dz1StrId = 0
        var dz2StrId = 0
        if (dz1Column == BaziColumnPosition.COLUMN_YEAR) {
            dz1StrId = R.string.app_bazi_dizhi_year_label
        } else if (dz1Column == BaziColumnPosition.COLUMN_MONTH) {
            dz1StrId = R.string.app_bazi_dizhi_month_label
        } else if (dz1Column == BaziColumnPosition.COLUMN_DAY) {
            dz1StrId = R.string.app_bazi_dizhi_day_label
        } else if (dz1Column == BaziColumnPosition.COLUMN_HOUR) {
            dz1StrId = R.string.app_bazi_dizhi_hour_label
        }

        if (dz2Column == BaziColumnPosition.COLUMN_YEAR) {
            dz2StrId = R.string.app_bazi_dizhi_year_label
        } else if (dz2Column == BaziColumnPosition.COLUMN_MONTH) {
            dz2StrId = R.string.app_bazi_dizhi_month_label
        } else if (dz2Column == BaziColumnPosition.COLUMN_DAY) {
            dz2StrId = R.string.app_bazi_dizhi_day_label
        } else if (dz2Column == BaziColumnPosition.COLUMN_HOUR) {
            dz2StrId = R.string.app_bazi_dizhi_hour_label
        }
        sb.append("    ")
        sb.append(stringResource(dz1StrId))
        sb.append("[")
        sb.append(BaziUtil().getDizhiText(dz1))
        sb.append("] ")

        sb.append(stringResource(R.string.app_bazi_hai_label))
        sb.append(" ")
        sb.append(stringResource(dz2StrId))
        sb.append("[")
        sb.append(BaziUtil().getDizhiText(dz2))
        sb.append("] ")
//        sb.append(stringResource(dizhiChongStrMap.get(dz1)!!))
        sb.append(" ")
        return sb.toString()
    }

    @Composable
    fun getDiZhi6HaiString(data: BaziData): String {
        var dz1 = data.yearDizhi
        var dz2 = data.monthDizhi
        var sb = StringBuilder()
        if (isDiZhiHai(dz1, dz2)) {
            sb.append(
                getDiZhi6HaiText(
                    dz1,
                    BaziColumnPosition.COLUMN_YEAR,
                    dz2,
                    BaziColumnPosition.COLUMN_MONTH
                )
            )
        }

        dz2 = data.dayDizhi
        if (isDiZhiHai(dz1, dz2)) {
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhi6HaiText(
                    dz1,
                    BaziColumnPosition.COLUMN_YEAR,
                    dz2,
                    BaziColumnPosition.COLUMN_DAY
                )
            )
        }
        dz2 = data.hourDizhi
        if (isDiZhiHai(dz1, dz2)) {
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhi6HaiText(
                    dz1,
                    BaziColumnPosition.COLUMN_YEAR,
                    dz2,
                    BaziColumnPosition.COLUMN_HOUR
                )
            )
        }
        dz1 = data.monthDizhi
        dz2 = data.dayDizhi
        if (isDiZhiHai(dz1, dz2)) {
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhi6HaiText(
                    dz1,
                    BaziColumnPosition.COLUMN_MONTH,
                    dz2,
                    BaziColumnPosition.COLUMN_DAY
                )
            )
        }

        dz1 = data.monthDizhi
        dz2 = data.hourDizhi
        if (isDiZhiHai(dz1, dz2)) {
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhi6HaiText(
                    dz1,
                    BaziColumnPosition.COLUMN_MONTH,
                    dz2,
                    BaziColumnPosition.COLUMN_HOUR
                )
            )
        }

        dz1 = data.dayDizhi
        dz2 = data.hourDizhi
        if (isDiZhiHai(dz1, dz2)) {
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhi6HaiText(
                    dz1,
                    BaziColumnPosition.COLUMN_DAY,
                    dz2,
                    BaziColumnPosition.COLUMN_HOUR
                )
            )
        }

        if (sb.length == 0) {
            sb.append("    ")
            sb.append(stringResource(R.string.app_bazi_dizhi_6hai_no_label))
        }
        return sb.toString()
    }

    @Composable
    fun getDiZhiXingText(
        dz1: DiZhi,
        dz1Column: BaziColumnPosition,
        dz2: DiZhi,
        dz2Column: BaziColumnPosition
    ): String {
        var sb = StringBuilder()
        var dz1StrId = 0
        var dz2StrId = 0
        if (dz1Column == BaziColumnPosition.COLUMN_YEAR) {
            dz1StrId = R.string.app_bazi_dizhi_year_label
        } else if (dz1Column == BaziColumnPosition.COLUMN_MONTH) {
            dz1StrId = R.string.app_bazi_dizhi_month_label
        } else if (dz1Column == BaziColumnPosition.COLUMN_DAY) {
            dz1StrId = R.string.app_bazi_dizhi_day_label
        } else if (dz1Column == BaziColumnPosition.COLUMN_HOUR) {
            dz1StrId = R.string.app_bazi_dizhi_hour_label
        }

        if (dz2Column == BaziColumnPosition.COLUMN_YEAR) {
            dz2StrId = R.string.app_bazi_dizhi_year_label
        } else if (dz2Column == BaziColumnPosition.COLUMN_MONTH) {
            dz2StrId = R.string.app_bazi_dizhi_month_label
        } else if (dz2Column == BaziColumnPosition.COLUMN_DAY) {
            dz2StrId = R.string.app_bazi_dizhi_day_label
        } else if (dz2Column == BaziColumnPosition.COLUMN_HOUR) {
            dz2StrId = R.string.app_bazi_dizhi_hour_label
        }
        sb.append("    ")
        sb.append(stringResource(dz1StrId))
        sb.append("[")
        sb.append(BaziUtil().getDizhiText(dz1))
        sb.append("] ")

        sb.append(stringResource(R.string.app_bazi_xing_label))
        sb.append(" ")
        sb.append(stringResource(dz2StrId))
        sb.append("[")
        sb.append(BaziUtil().getDizhiText(dz2))
        sb.append("] ")
        sb.append(stringResource(dizhiXingStrMap.get(dz1)!!))
        sb.append(" ")
        return sb.toString()
    }

    @Composable
    fun getDiZhiXingString(data: BaziData): String {
        var dz1 = data.yearDizhi
        var dz2 = data.monthDizhi
        var sb = StringBuilder()
        if (isDiZhiXiangXing(dz1, dz2)) {
            sb.append(
                getDiZhiXingText(
                    dz1,
                    BaziColumnPosition.COLUMN_YEAR,
                    dz2,
                    BaziColumnPosition.COLUMN_MONTH
                )
            )
        }

        dz2 = data.dayDizhi
        if (isDiZhiXiangXing(dz1, dz2)) {
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhiXingText(
                    dz1,
                    BaziColumnPosition.COLUMN_YEAR,
                    dz2,
                    BaziColumnPosition.COLUMN_DAY
                )
            )
        }
        dz2 = data.hourDizhi
        if (isDiZhiXiangXing(dz1, dz2)) {
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhiXingText(
                    dz1,
                    BaziColumnPosition.COLUMN_YEAR,
                    dz2,
                    BaziColumnPosition.COLUMN_HOUR
                )
            )
        }
        dz1 = data.monthDizhi
        dz2 = data.dayDizhi
        if (isDiZhiXiangXing(dz1, dz2)) {
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhiXingText(
                    dz1,
                    BaziColumnPosition.COLUMN_MONTH,
                    dz2,
                    BaziColumnPosition.COLUMN_DAY
                )
            )
        }

        dz1 = data.monthDizhi
        dz2 = data.hourDizhi
        if (isDiZhiXiangXing(dz1, dz2)) {
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhiXingText(
                    dz1,
                    BaziColumnPosition.COLUMN_MONTH,
                    dz2,
                    BaziColumnPosition.COLUMN_HOUR
                )
            )
        }

        dz1 = data.dayDizhi
        dz2 = data.hourDizhi
        if (isDiZhiXiangXing(dz1, dz2)) {
            if (sb.length > 0) sb.append("\n")
            sb.append(
                getDiZhiXingText(
                    dz1,
                    BaziColumnPosition.COLUMN_DAY,
                    dz2,
                    BaziColumnPosition.COLUMN_HOUR
                )
            )
        }

        if (sb.length == 0) {
            sb.append("    ")
            sb.append(stringResource(R.string.app_bazi_dizhi_xing_no_label))
        }
        return sb.toString()
    }
}