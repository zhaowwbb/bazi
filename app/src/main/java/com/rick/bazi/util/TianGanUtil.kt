package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.WuXing

class TianGanUtil {
    enum class TianGanPos {
        TIANGAN_YEAR,
        TIANGAN_MONTH,
        TIANGAN_DAY,
        TIANGAN_HOUR
    }

    data class TianGanaHeInfo(
        val srcTgPos: TianGanPos,
        val srcTg: TianGan,
        val dstTgPos: TianGanPos,
        val dstTg: TianGan,
        val wx: WuXing
    )

    val tgLuLookupMap: Map<TianGan, DiZhi> = mapOf(
        TianGan.TIANGAN_JIA to DiZhi.DIZHI_YIN,
        TianGan.TIANGAN_YI to DiZhi.DIZHI_MOU,
        TianGan.TIANGAN_BING to DiZhi.DIZHI_SI,
        TianGan.TIANGAN_DING to DiZhi.DIZHI_WU,
        TianGan.TIANGAN_WU to DiZhi.DIZHI_SI,
        TianGan.TIANGAN_JI to DiZhi.DIZHI_WU,
        TianGan.TIANGAN_GENG to DiZhi.DIZHI_SHEN,
        TianGan.TIANGAN_XIN to DiZhi.DIZHI_YOU,
        TianGan.TIANGAN_REN to DiZhi.DIZHI_HAI,
        TianGan.TIANGAN_GUI to DiZhi.DIZHI_ZI
    )

    fun getTianGanLu(tg : TianGan) : DiZhi{
        return tgLuLookupMap.get(tg)!!
    }

    val tianGanHeMap: Map<TianGan, TianGan> = mapOf(
        TianGan.TIANGAN_JIA to TianGan.TIANGAN_JI,
        TianGan.TIANGAN_YI to TianGan.TIANGAN_GENG,
        TianGan.TIANGAN_BING to TianGan.TIANGAN_XIN,
        TianGan.TIANGAN_DING to TianGan.TIANGAN_REN,
        TianGan.TIANGAN_WU to TianGan.TIANGAN_GUI,

        TianGan.TIANGAN_JI to TianGan.TIANGAN_JIA,
        TianGan.TIANGAN_GENG to TianGan.TIANGAN_YI,
        TianGan.TIANGAN_XIN to TianGan.TIANGAN_BING,
        TianGan.TIANGAN_REN to TianGan.TIANGAN_DING,
        TianGan.TIANGAN_GUI to TianGan.TIANGAN_WU
    )

    val tianGanHeWuXingMap: Map<TianGan, WuXing> = mapOf(
        TianGan.TIANGAN_JIA to WuXing.WUXING_TU,
        TianGan.TIANGAN_YI to WuXing.WUXING_JIN,
        TianGan.TIANGAN_BING to WuXing.WUXING_SHUI,
        TianGan.TIANGAN_DING to WuXing.WUXING_MU,
        TianGan.TIANGAN_WU to WuXing.WUXING_HUO,

        TianGan.TIANGAN_JI to WuXing.WUXING_TU,
        TianGan.TIANGAN_GENG to WuXing.WUXING_JIN,
        TianGan.TIANGAN_XIN to WuXing.WUXING_SHUI,
        TianGan.TIANGAN_REN to WuXing.WUXING_MU,
        TianGan.TIANGAN_GUI to WuXing.WUXING_HUO
    )


    val tianGanPosStrMap: Map<TianGanPos, Int> = mapOf(
        TianGanPos.TIANGAN_YEAR to R.string.app_bazi_tiangan_year_label,
        TianGanPos.TIANGAN_MONTH to R.string.app_bazi_tiangan_month_label,
        TianGanPos.TIANGAN_DAY to R.string.app_bazi_tiangan_day_label,
        TianGanPos.TIANGAN_HOUR to R.string.app_bazi_tiangan_hour_label
    )

    fun isTianGanHe(srcTg: TianGan, dstTg: TianGan): Boolean {
        var ret = false
        var tg = tianGanHeMap.get(srcTg)!!
        if (tg == dstTg) {
            ret = true
        } else {
            tg = tianGanHeMap.get(dstTg)!!
            if (tg == srcTg) {
                ret = true
            }
        }

        return ret
    }

    fun getTianGanHeList(data: BaziData): MutableList<TianGanaHeInfo> {
//        var ret = false
        val heList: MutableList<TianGanaHeInfo> = mutableListOf()
        var tg = data.yearTiangan
        var wx = WuXing.WUXING_MU
//        var heTg = tianGanHeMap.get(tg)
        //year
        if (isTianGanHe(tg, data.monthTiangan)) {
            wx = tianGanHeWuXingMap.get(tg)!!
            heList.add(
                TianGanaHeInfo(
                    TianGanPos.TIANGAN_YEAR,
                    tg,
                    TianGanPos.TIANGAN_MONTH,
                    data.monthTiangan,
                    wx
                )
            )
        }
        if (isTianGanHe(tg, data.dayTiangan)) {
            wx = tianGanHeWuXingMap.get(tg)!!
            heList.add(
                TianGanaHeInfo(
                    TianGanPos.TIANGAN_YEAR,
                    tg,
                    TianGanPos.TIANGAN_DAY,
                    data.dayTiangan,
                    wx
                )
            )
        }
        if (isTianGanHe(tg, data.hourTiangan)) {
            wx = tianGanHeWuXingMap.get(tg)!!
            heList.add(
                TianGanaHeInfo(
                    TianGanPos.TIANGAN_YEAR,
                    tg,
                    TianGanPos.TIANGAN_HOUR,
                    data.hourTiangan,
                    wx
                )
            )
        }

        tg = data.monthTiangan
        if (isTianGanHe(tg, data.dayTiangan)) {
            wx = tianGanHeWuXingMap.get(tg)!!
            heList.add(
                TianGanaHeInfo(
                    TianGanPos.TIANGAN_MONTH,
                    tg,
                    TianGanPos.TIANGAN_DAY,
                    data.dayTiangan,
                    wx
                )
            )
        }
        if (isTianGanHe(tg, data.hourTiangan)) {
            wx = tianGanHeWuXingMap.get(tg)!!
            heList.add(
                TianGanaHeInfo(
                    TianGanPos.TIANGAN_MONTH,
                    tg,
                    TianGanPos.TIANGAN_HOUR,
                    data.hourTiangan,
                    wx
                )
            )
        }

        tg = data.dayTiangan
        if (isTianGanHe(tg, data.hourTiangan)) {
            wx = tianGanHeWuXingMap.get(tg)!!
            heList.add(
                TianGanaHeInfo(
                    TianGanPos.TIANGAN_DAY,
                    tg,
                    TianGanPos.TIANGAN_HOUR,
                    data.hourTiangan,
                    wx
                )
            )
        }

        return heList
    }

    fun isTianGanAdjecent(info: TianGanaHeInfo): Boolean {
        var ret = false
        if (info.srcTgPos == TianGanPos.TIANGAN_YEAR && info.dstTgPos == TianGanPos.TIANGAN_MONTH) {
            ret = true
        } else if (info.srcTgPos == TianGanPos.TIANGAN_MONTH && info.dstTgPos == TianGanPos.TIANGAN_DAY) {
            ret = true
        } else if (info.srcTgPos == TianGanPos.TIANGAN_DAY && info.dstTgPos == TianGanPos.TIANGAN_HOUR) {
            ret = true
        }

        return ret
    }

    fun hasTianGanHeHua(data: BaziData): Boolean {
        var ret = false
        val heList = getTianGanHeList(data)
        for (info in heList) {
            if (isTianGanHeHua(data, info)) {
                ret = true
                break
            }
        }

        return ret
    }

    fun isTianGanHeHua(data: BaziData, info: TianGanaHeInfo): Boolean {
        var ret = false

        var isDangLing = false
        var isAdjecent = false
        var hasRoot = false

        isDangLing = WuXingUtil().isWXDangLing(data.monthDizhi, info.wx)
        isAdjecent = isTianGanAdjecent(info)


        if (info.wx == WuXing.WUXING_TU) {
            hasRoot = WuXingUtil().checkRootToTianGan(data, TianGan.TIANGAN_WU)
        } else if (info.wx == WuXing.WUXING_JIN) {
            hasRoot = WuXingUtil().checkRootToTianGan(data, TianGan.TIANGAN_GENG)
        } else if (info.wx == WuXing.WUXING_SHUI) {
            hasRoot = WuXingUtil().checkRootToTianGan(data, TianGan.TIANGAN_REN)
        } else if (info.wx == WuXing.WUXING_MU) {
            hasRoot = WuXingUtil().checkRootToTianGan(data, TianGan.TIANGAN_JIA)
        } else if (info.wx == WuXing.WUXING_HUO) {
            hasRoot = WuXingUtil().checkRootToTianGan(data, TianGan.TIANGAN_BING)
        }
        //san he, san hui, todo
        if(!hasRoot){
            if(DiZhiUtil().hasDiZhiSanHe(data)){
                if(DiZhiUtil().getSanHeWuXing(data) == info.wx){
                    //Dizhi sanhe equal strong root
                    hasRoot = true
                }
            }
        }
        if(!hasRoot){
            if(DiZhiUtil().hasDiZhiSanHui(data)){
                if(DiZhiUtil().getSanHuiWuXing(data) == info.wx){
                    //Dizhi sanhui equal strong root
                    hasRoot = true
                }
            }
        }

        if (isDangLing && isAdjecent && hasRoot) {
            ret = true
        }
        return ret
    }

    @Composable
    fun getTianGanHeString(data: BaziData): String {
        var sb = StringBuilder()
        val heList = getTianGanHeList(data)
        var isHeHua = false
        var hehuaMap: MutableMap<WuXing, TianGanaHeInfo> = mutableMapOf()

        for (info in heList) {
            isHeHua = isTianGanHeHua(data, info)
            if (sb.length > 0) {
                sb.append("\n")
            }
            sb.append("    ")
            sb.append(stringResource(tianGanPosStrMap.get(info.srcTgPos)!!))
            sb.append("(")
            sb.append(WuXingUtil().getTianGanWuXingText(info.srcTg))
            sb.append(") ")
            sb.append(stringResource(tianGanPosStrMap.get(info.dstTgPos)!!))
            sb.append("(")
            sb.append(WuXingUtil().getTianGanWuXingText(info.dstTg))
            sb.append(") ")
            sb.append(stringResource(R.string.app_bazi_tiangan_he_label))
//            sb.append(" ")
            sb.append(WuXingUtil().getWuXingText(tianGanHeWuXingMap.get(info.srcTg)!!))
            sb.append(" ")
            if (isHeHua) {
                sb.append(stringResource(R.string.app_bazi_tiangan_hehua_success))
            } else {
                sb.append(stringResource(R.string.app_bazi_tiangan_hehua_failed))
            }
            if (!hehuaMap.containsKey(info.wx)) {
                hehuaMap.put(info.wx, info)
            }
        }
        if (heList.size > 0) {
            var desc = ""
            sb.append("    ")
            sb.append("\n")
            for (info in hehuaMap.values) {
                if (desc.length > 0) {
                    sb.append("\n")
                }
                if (info.wx == WuXing.WUXING_TU) {
                    sb.append(stringResource(R.string.app_bazi_tiangan_hehua_jj_desc))
                } else if (info.wx == WuXing.WUXING_JIN) {
                    sb.append(stringResource(R.string.app_bazi_tiangan_hehua_yg_desc))
                } else if (info.wx == WuXing.WUXING_SHUI) {
                    sb.append(stringResource(R.string.app_bazi_tiangan_hehua_bx_desc))
                } else if (info.wx == WuXing.WUXING_MU) {
                    sb.append(stringResource(R.string.app_bazi_tiangan_hehua_dr_desc))
                } else if (info.wx == WuXing.WUXING_HUO) {
                    sb.append(stringResource(R.string.app_bazi_tiangan_hehua_wg_desc))
                }
            }
        } else {
            sb.append("    ")
            sb.append(stringResource(R.string.app_bazi_tiangan_hehua_no))
        }
        return sb.toString()
    }
}