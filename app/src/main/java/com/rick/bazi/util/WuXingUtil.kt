package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.BaziStrength
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.RootLevel
import com.rick.bazi.data.ShiShen
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.WuXing
import com.rick.bazi.ui.BaziViewModel

class WuXingUtil {

//    @Composable
    fun getJinNumber(
        baziInfo: BaziData
    ): Int {
        var total = 0
        if (isTianGanJin(baziInfo.yearTiangan)) total++
        if (isTianGanJin(baziInfo.monthTiangan)) total++
        if (isTianGanJin(baziInfo.dayTiangan)) total++
        if (isTianGanJin(baziInfo.hourTiangan)) total++

        if (isDiZhiJin(baziInfo.yearDizhi)) total++
        if (isDiZhiJin(baziInfo.monthDizhi)) total++
        if (isDiZhiJin(baziInfo.dayDizhi)) total++
        if (isDiZhiJin(baziInfo.hourDizhi)) total++

        return total
    }

//    @Composable
    fun getMuNumber(
        baziInfo: BaziData
    ): Int {
        var total = 0
        if (isTianGanMu(baziInfo.yearTiangan)) total++
        if (isTianGanMu(baziInfo.monthTiangan)) total++
        if (isTianGanMu(baziInfo.dayTiangan)) total++
        if (isTianGanMu(baziInfo.hourTiangan)) total++

        if (isDiZhiMu(baziInfo.yearDizhi)) total++
        if (isDiZhiMu(baziInfo.monthDizhi)) total++
        if (isDiZhiMu(baziInfo.dayDizhi)) total++
        if (isDiZhiMu(baziInfo.hourDizhi)) total++

        return total
    }

//    @Composable
    fun getShuiNumber(
        baziInfo: BaziData
    ): Int {
        var total = 0
        if (isTianGanShui(baziInfo.yearTiangan)) total++
        if (isTianGanShui(baziInfo.monthTiangan)) total++
        if (isTianGanShui(baziInfo.dayTiangan)) total++
        if (isTianGanShui(baziInfo.hourTiangan)) total++

        if (isDiZhiShui(baziInfo.yearDizhi)) total++
        if (isDiZhiShui(baziInfo.monthDizhi)) total++
        if (isDiZhiShui(baziInfo.dayDizhi)) total++
        if (isDiZhiShui(baziInfo.hourDizhi)) total++

        return total
    }

//    @Composable
    fun getHuoNumber(
        baziInfo: BaziData
    ): Int {
        var total = 0
        if (isTianGanHuo(baziInfo.yearTiangan)) total++
        if (isTianGanHuo(baziInfo.monthTiangan)) total++
        if (isTianGanHuo(baziInfo.dayTiangan)) total++
        if (isTianGanHuo(baziInfo.hourTiangan)) total++

        if (isDiZhiHuo(baziInfo.yearDizhi)) total++
        if (isDiZhiHuo(baziInfo.monthDizhi)) total++
        if (isDiZhiHuo(baziInfo.dayDizhi)) total++
        if (isDiZhiHuo(baziInfo.hourDizhi)) total++

        return total
    }

//    @Composable
    fun getTuNumber(
        baziInfo: BaziData
    ): Int {
        var total = 0
        if (isTianGanTu(baziInfo.yearTiangan)) total++
        if (isTianGanTu(baziInfo.monthTiangan)) total++
        if (isTianGanTu(baziInfo.dayTiangan)) total++
        if (isTianGanTu(baziInfo.hourTiangan)) total++

        if (isDiZhiTu(baziInfo.yearDizhi)) total++
        if (isDiZhiTu(baziInfo.monthDizhi)) total++
        if (isDiZhiTu(baziInfo.dayDizhi)) total++
        if (isDiZhiTu(baziInfo.hourDizhi)) total++

        return total
    }

    fun isTianGanJin(tg: TianGan): Boolean {
        if (tg == TianGan.TIANGAN_GENG || tg == TianGan.TIANGAN_XIN) return true
        return false
    }

    fun isDiZhiJin(dz: DiZhi): Boolean {
        if (dz == DiZhi.DIZHI_SHEN || dz == DiZhi.DIZHI_YOU) return true
        return false
    }

    fun isTianGanMu(tg: TianGan): Boolean {
        if (tg == TianGan.TIANGAN_JIA || tg == TianGan.TIANGAN_YI) return true
        return false
    }

    fun isDiZhiMu(dz: DiZhi): Boolean {
        if (dz == DiZhi.DIZHI_YIN || dz == DiZhi.DIZHI_MOU) return true
        return false
    }

    fun isTianGanShui(tg: TianGan): Boolean {
        if (tg == TianGan.TIANGAN_REN || tg == TianGan.TIANGAN_GUI) return true
        return false
    }

    fun isDiZhiShui(dz: DiZhi): Boolean {
        if (dz == DiZhi.DIZHI_HAI || dz == DiZhi.DIZHI_ZI) return true
        return false
    }

    fun isTianGanHuo(tg: TianGan): Boolean {
        if (tg == TianGan.TIANGAN_BING || tg == TianGan.TIANGAN_DING) return true
        return false
    }

    fun isDiZhiHuo(dz: DiZhi): Boolean {
        if (dz == DiZhi.DIZHI_SI || dz == DiZhi.DIZHI_WU) return true
        return false
    }

    fun isTianGanTu(tg: TianGan): Boolean {
        if (tg == TianGan.TIANGAN_WU || tg == TianGan.TIANGAN_JI) return true
        return false
    }

    fun isDiZhiTu(dz: DiZhi): Boolean {
        if (dz == DiZhi.DIZHI_CHEN || dz == DiZhi.DIZHI_XU || dz == DiZhi.DIZHI_CHOU || dz == DiZhi.DIZHI_WEI) return true
        return false
    }

    fun getTianGanWuxing(tg: TianGan): WuXing {
        if (tg == TianGan.TIANGAN_GENG || tg == TianGan.TIANGAN_XIN) return WuXing.WUXING_JIN
        if (tg == TianGan.TIANGAN_JIA || tg == TianGan.TIANGAN_YI) return WuXing.WUXING_MU
        if (tg == TianGan.TIANGAN_REN || tg == TianGan.TIANGAN_GUI) return WuXing.WUXING_SHUI
        if (tg == TianGan.TIANGAN_WU || tg == TianGan.TIANGAN_JI) return WuXing.WUXING_TU
        if (tg == TianGan.TIANGAN_BING || tg == TianGan.TIANGAN_DING) return WuXing.WUXING_HUO

        return WuXing.WUXING_TU
    }

    fun isWuXingBorn(wx1: WuXing, wx2: WuXing): Boolean {
        if (wx1 == WuXing.WUXING_JIN && wx2 == WuXing.WUXING_TU) return true
        if (wx1 == WuXing.WUXING_MU && wx2 == WuXing.WUXING_SHUI) return true
        if (wx1 == WuXing.WUXING_SHUI && wx2 == WuXing.WUXING_JIN) return true
        if (wx1 == WuXing.WUXING_HUO && wx2 == WuXing.WUXING_MU) return true
        if (wx1 == WuXing.WUXING_TU && wx2 == WuXing.WUXING_HUO) return true
        return false;
    }

    @Composable
    fun isWuXingDerived(monthDz: DiZhi, dayTg: TianGan): Boolean {
        var tgWuXing = getTianGanWuxing(dayTg)
        var dzWuXing = getDiZhiWuxing(monthDz)
        return isWuXingBorn(tgWuXing, dzWuXing)
    }

    fun isWXDangLing(monthDz: DiZhi, wx: WuXing): Boolean {
        var isDangLing = false
        var tgWuXing = wx
        var dzWuXing = getDiZhiWuxing(monthDz)
        if (tgWuXing == dzWuXing) {
            isDangLing = true
        }
        if (isWuXingBorn(tgWuXing, dzWuXing)) {
            isDangLing = true
        }
        println("On monthDz:$monthDz, WuXing:$wx dangling flag=$isDangLing")
        return isDangLing
    }

    fun isDangling(monthDz: DiZhi, dayTg: TianGan): Boolean {
//        var isDangLing = false
        var tgWuXing = getTianGanWuxing(dayTg)
        return isWXDangLing(monthDz, tgWuXing)
//        var dzWuXing = getDiZhiWuxing(monthDz)
//        if (tgWuXing == dzWuXing){
//            isDangLing = true
//        }
//        if (isWuXingBorn(tgWuXing, dzWuXing)){
//            isDangLing = true
//        }
//        println("Bazi isDangling: $isDangLing")
//        return isDangLing
    }

    fun getTgWX(tg: TianGan): WuXing {
        var wx = WuXing.WUXING_SHUI
        if (tg == TianGan.TIANGAN_JIA || tg == TianGan.TIANGAN_YI) {
            wx = WuXing.WUXING_MU
        }
        if (tg == TianGan.TIANGAN_BING || tg == TianGan.TIANGAN_DING) {
            wx = WuXing.WUXING_HUO
        }
        if (tg == TianGan.TIANGAN_WU || tg == TianGan.TIANGAN_JI) {
            wx = WuXing.WUXING_TU
        }
        if (tg == TianGan.TIANGAN_GENG || tg == TianGan.TIANGAN_XIN) {
            wx = WuXing.WUXING_JIN
        }
        if (tg == TianGan.TIANGAN_REN || tg == TianGan.TIANGAN_GUI) {
            wx = WuXing.WUXING_SHUI
        }
        return wx
    }

    @Composable
    fun getWuXingText(wx: WuXing): String {
        var str = ""
        if (wx == WuXing.WUXING_JIN) {
            str = stringResource(R.string.app_bazi_metal)
        }
        if (wx == WuXing.WUXING_MU) {
            str = stringResource(R.string.app_bazi_wood)
        }
        if (wx == WuXing.WUXING_SHUI) {
            str = stringResource(R.string.app_bazi_water)
        }
        if (wx == WuXing.WUXING_HUO) {
            str = stringResource(R.string.app_bazi_fire)
        }
        if (wx == WuXing.WUXING_TU) {
            str = stringResource(R.string.app_bazi_soil)
        }
        return str
    }

    @Composable
    fun getTianGanWuxingText(tg: TianGan): String {
        var tgWX = WuXing.WUXING_MU
        tgWX = getTianGanWuxing(tg)
        return getWuXingText(tgWX)
    }

    @Composable
    fun getDiZhiWuxingText(dz: DiZhi): String {
        var wx = getDiZhiWuxing(dz)
        return getWuXingText(wx)
    }

    //    @Composable
    fun getDiZhiWuxing(dz: DiZhi): WuXing {
        var ret = WuXing.WUXING_MU
        if (dz == DiZhi.DIZHI_HAI || dz == DiZhi.DIZHI_ZI) {
            ret = WuXing.WUXING_SHUI
        }
        if (dz == DiZhi.DIZHI_YIN || dz == DiZhi.DIZHI_MOU) {
            ret = WuXing.WUXING_MU
        }
        if (dz == DiZhi.DIZHI_SI || dz == DiZhi.DIZHI_WU) {
            ret = WuXing.WUXING_HUO
        }
        if (dz == DiZhi.DIZHI_SHEN || dz == DiZhi.DIZHI_YOU) {
            ret = WuXing.WUXING_JIN
        }
        if (dz == DiZhi.DIZHI_CHEN || dz == DiZhi.DIZHI_XU || dz == DiZhi.DIZHI_CHOU || dz == DiZhi.DIZHI_WEI) {
            ret = WuXing.WUXING_TU
        }
        return ret
    }

    @Composable
    fun getOwnerStr(
        baziInfo: BaziInfo
    ): String {
        var ownerStr = BaziUtil().getTianGanLabel(baziInfo.baziData.dayTiangan)
        ownerStr = ownerStr + WuXingUtil().getTianGanWuxingText(baziInfo.dayTiangan)
        return ownerStr
    }

    @Composable
    fun getOwnerString(tg: TianGan): String {
        var tianganStrKey = BaziUtil().tianganStrMap.getValue(tg)
        var str = stringResource(tianganStrKey)
        var ownerStr =
            str + WuXingUtil().getTianGanWuxingText(tg) + stringResource(R.string.bazi_owner_brief)
        return ownerStr
    }

    @Composable
    fun getTiaohouWuXingText(data: BaziData): String {
        var summary = ""
        var tiaohouShenList: List<WuXing> = arrayListOf<WuXing>()
        var monthDz: DiZhi = data.monthDizhi
        //cold
        if (monthDz == DiZhi.DIZHI_HAI || monthDz == DiZhi.DIZHI_ZI || monthDz == DiZhi.DIZHI_CHOU) {
            summary =
                stringResource(R.string.app_bazi_tiaohou_cold) + "[" + getWuXingText(WuXing.WUXING_HUO) + "]"
            tiaohouShenList = arrayListOf<WuXing>(WuXing.WUXING_HUO)
        }
        //hot
        if (monthDz == DiZhi.DIZHI_SI || monthDz == DiZhi.DIZHI_WU || monthDz == DiZhi.DIZHI_WEI) {
            summary =
                stringResource(R.string.app_bazi_tiaohou_hot) + "[" + getWuXingText(WuXing.WUXING_SHUI) + "]"
            tiaohouShenList = arrayListOf<WuXing>(WuXing.WUXING_SHUI)
        }

        //dry
        if (monthDz == DiZhi.DIZHI_WEI || monthDz == DiZhi.DIZHI_XU) {
            summary =
                stringResource(R.string.app_bazi_tiaohou_dry) + "[" + getWuXingText(WuXing.WUXING_SHUI) + "]"
            tiaohouShenList = arrayListOf<WuXing>(WuXing.WUXING_SHUI)
        }

        //wet
        if (monthDz == DiZhi.DIZHI_CHOU || monthDz == DiZhi.DIZHI_CHEN) {
            summary =
                stringResource(R.string.app_bazi_tiaohou_wet) + "[" + getWuXingText(WuXing.WUXING_HUO) + "]"
            tiaohouShenList = arrayListOf<WuXing>(WuXing.WUXING_HUO)
        }

        //mild
        if (monthDz == DiZhi.DIZHI_YIN || monthDz == DiZhi.DIZHI_MOU || monthDz == DiZhi.DIZHI_SHEN || monthDz == DiZhi.DIZHI_YOU) {
            summary = stringResource(R.string.app_bazi_tiaohou_mild)
        }
        data.tiaohouList = tiaohouShenList

//        baziModel.setBaziTiaohouShenList(tiaohouShenList)
        return summary
    }

//    @Composable
    fun getBiJieWuXing(tg: TianGan): WuXing {
        var ret = WuXing.WUXING_MU
        var ownerWX = getTianGanWuxing(tg)
        if (ownerWX == WuXing.WUXING_MU) {
            return WuXing.WUXING_MU
        }
        if (ownerWX == WuXing.WUXING_SHUI) {
            return WuXing.WUXING_SHUI
        }
        if (ownerWX == WuXing.WUXING_TU) {
            return WuXing.WUXING_TU
        }
        if (ownerWX == WuXing.WUXING_HUO) {
            return WuXing.WUXING_HUO
        }
        if (ownerWX == WuXing.WUXING_JIN) {
            return WuXing.WUXING_JIN
        }
        return ret
    }

//    @Composable
    fun getGuanshaWuXing(tg: TianGan): WuXing {
        var ret = WuXing.WUXING_MU
        var ownerWX = getTianGanWuxing(tg)
        if (ownerWX == WuXing.WUXING_MU) {
            return WuXing.WUXING_JIN
        }
        if (ownerWX == WuXing.WUXING_SHUI) {
            return WuXing.WUXING_TU
        }
        if (ownerWX == WuXing.WUXING_TU) {
            return WuXing.WUXING_MU
        }
        if (ownerWX == WuXing.WUXING_HUO) {
            return WuXing.WUXING_SHUI
        }
        if (ownerWX == WuXing.WUXING_JIN) {
            return WuXing.WUXING_HUO
        }
        return ret
    }

//    @Composable
    fun getShishangWuXing(tg: TianGan): WuXing {
        var ret = WuXing.WUXING_MU
        var ownerWX = getTianGanWuxing(tg)
        if (ownerWX == WuXing.WUXING_MU) {
            return WuXing.WUXING_HUO
        }
        if (ownerWX == WuXing.WUXING_SHUI) {
            return WuXing.WUXING_MU
        }
        if (ownerWX == WuXing.WUXING_TU) {
            return WuXing.WUXING_JIN
        }
        if (ownerWX == WuXing.WUXING_HUO) {
            return WuXing.WUXING_TU
        }
        if (ownerWX == WuXing.WUXING_JIN) {
            return WuXing.WUXING_SHUI
        }
        return ret
    }

//    @Composable
    fun getCaiWuXing(tg: TianGan): WuXing {
        var ret = WuXing.WUXING_MU
        var ownerWX = getTianGanWuxing(tg)
        if (ownerWX == WuXing.WUXING_MU) {
            return WuXing.WUXING_TU
        }
        if (ownerWX == WuXing.WUXING_SHUI) {
            return WuXing.WUXING_HUO
        }
        if (ownerWX == WuXing.WUXING_TU) {
            return WuXing.WUXING_SHUI
        }
        if (ownerWX == WuXing.WUXING_HUO) {
            return WuXing.WUXING_JIN
        }
        if (ownerWX == WuXing.WUXING_JIN) {
            return WuXing.WUXING_MU
        }
        return ret
    }

    @Composable
    fun getCaiWuXingText(tg: TianGan): String {
        var wx = getCaiWuXing(tg)
        return getWuXingText(wx)
    }

    @Composable
    fun getShishangWuXingText(tg: TianGan): String {
        var wx = getShishangWuXing(tg)
        return getWuXingText(wx)
    }

    @Composable
    fun getGuanshaWuXingText(tg: TianGan): String {
        var wx = getGuanshaWuXing(tg)
        return getWuXingText(wx)
    }

    @Composable
    fun getBiJieWuXingText(tg: TianGan): String {
        var wx = getBiJieWuXing(tg)
        return getWuXingText(wx)
    }

//    @Composable
    fun getYinWuXing(tg: TianGan): WuXing {
        var ret = WuXing.WUXING_MU
        var ownerWX = getTianGanWuxing(tg)
        if (ownerWX == WuXing.WUXING_MU) {
            return WuXing.WUXING_SHUI
        }
        if (ownerWX == WuXing.WUXING_SHUI) {
            return WuXing.WUXING_JIN
        }
        if (ownerWX == WuXing.WUXING_TU) {
            return WuXing.WUXING_HUO
        }
        if (ownerWX == WuXing.WUXING_HUO) {
            return WuXing.WUXING_MU
        }
        if (ownerWX == WuXing.WUXING_JIN) {
            return WuXing.WUXING_TU
        }
        return ret
    }

    @Composable
    fun getDangLingStr(
        baziInfo: BaziInfo,
        baziModel: BaziViewModel
    ): String {
        var result = WuXingUtil().getTianGanWuXingText(baziInfo.baziData.dayTiangan) +
                stringResource(R.string.app_bazi_born_label) +
                getDizhiStr(baziInfo, baziInfo.monthDizhi) +
                stringResource(R.string.app_label_month)

        if (WuXingUtil().isDangling(baziInfo.monthDizhi, baziInfo.dayTiangan)) {
            result =
                result + stringResource(R.string.bazi_owner) + stringResource(R.string.app_bazi_wangxiang_label) + " " + stringResource(
                    R.string.app_bazi_dangling_yes
                )
            baziModel.setDangLing(true)
        } else {
            result =
                result + stringResource(R.string.bazi_owner) + stringResource(R.string.app_bazi_xiuqiu_label) + " " + stringResource(
                    R.string.app_bazi_dangling_no
                )
            baziModel.setDangLing(false)
        }
        baziModel.setDeLingCheckStr(result)
        return result
    }

    @Composable
    fun getDeDiDescription(
        baziInfo: BaziData
    ): String {
        var deDiStr = ""
        var isDedi = isBaziDedi(baziInfo)

        deDiStr = deDiStr + stringResource(R.string.dizhi) + " " +
                baziInfo.strongRootCount + stringResource(R.string.app_bazi_strong_root) + " " +
                baziInfo.mediumRootCount + stringResource(R.string.app_bazi_weak_root) + " " +
                baziInfo.weakRootCount + stringResource(R.string.app_bazi_tiny_root)
        if (isDedi) {
            deDiStr = deDiStr + " " + stringResource(R.string.app_bazi_dedi_yes)
        } else {
            deDiStr = deDiStr + " " + stringResource(R.string.app_bazi_dedi_no)
        }

        return deDiStr
    }

    fun checkMuRoot(dz: DiZhi): RootLevel {
        var rootLevel = RootLevel.NO_ROOT
        if (dz == DiZhi.DIZHI_YIN || dz == DiZhi.DIZHI_MOU) {
            rootLevel = RootLevel.STRONG_ROOT
        } else if (dz == DiZhi.DIZHI_CHEN || dz == DiZhi.DIZHI_HAI) {
            rootLevel = RootLevel.MEDIUM_ROOT
        } else if (dz == DiZhi.DIZHI_WEI) {
            rootLevel = RootLevel.WEAK_ROOT
        } else {
            rootLevel = RootLevel.NO_ROOT
        }
        return rootLevel
    }

    fun checkHuoRoot(dz: DiZhi): RootLevel {
        var rootLevel = RootLevel.NO_ROOT
        if (dz == DiZhi.DIZHI_SI || dz == DiZhi.DIZHI_WU) {
            rootLevel = RootLevel.STRONG_ROOT
        } else if (dz == DiZhi.DIZHI_YIN || dz == DiZhi.DIZHI_WEI) {
            rootLevel = RootLevel.MEDIUM_ROOT
        } else if (dz == DiZhi.DIZHI_XU) {
            rootLevel = RootLevel.WEAK_ROOT
        } else {
            rootLevel = RootLevel.NO_ROOT
        }
        return rootLevel
    }

    fun checkShuiRoot(dz: DiZhi): RootLevel {
        var rootLevel = RootLevel.NO_ROOT
        if (dz == DiZhi.DIZHI_ZI || dz == DiZhi.DIZHI_HAI) {
            rootLevel = RootLevel.STRONG_ROOT
        } else if (dz == DiZhi.DIZHI_SHEN || dz == DiZhi.DIZHI_CHOU) {
            rootLevel = RootLevel.MEDIUM_ROOT
        } else if (dz == DiZhi.DIZHI_CHEN) {
            rootLevel = RootLevel.WEAK_ROOT
        } else {
            rootLevel = RootLevel.NO_ROOT
        }
        return rootLevel
    }

    fun checkJinRoot(dz: DiZhi): RootLevel {
        var rootLevel = RootLevel.NO_ROOT
        if (dz == DiZhi.DIZHI_SHEN || dz == DiZhi.DIZHI_YOU) {
            rootLevel = RootLevel.STRONG_ROOT
        } else if (dz == DiZhi.DIZHI_SI || dz == DiZhi.DIZHI_XU) {
            rootLevel = RootLevel.MEDIUM_ROOT
        } else if (dz == DiZhi.DIZHI_CHOU) {
            rootLevel = RootLevel.WEAK_ROOT
        } else {
            rootLevel = RootLevel.NO_ROOT
        }
        return rootLevel
    }

    fun checkTuRoot(dz: DiZhi): RootLevel {
        var rootLevel = RootLevel.NO_ROOT
        if (dz == DiZhi.DIZHI_CHEN || dz == DiZhi.DIZHI_XU || dz == DiZhi.DIZHI_CHOU || dz == DiZhi.DIZHI_WEI) {
            rootLevel = RootLevel.STRONG_ROOT
        } else if (dz == DiZhi.DIZHI_WU) {
            rootLevel = RootLevel.MEDIUM_ROOT
        } else if (dz == DiZhi.DIZHI_YIN || dz == DiZhi.DIZHI_SI || dz == DiZhi.DIZHI_SHEN) {
            rootLevel = RootLevel.WEAK_ROOT
        } else {
            rootLevel = RootLevel.NO_ROOT
        }
        return rootLevel
    }

    fun checkRootToTianGan(baziInfo: BaziData, tg: TianGan): Boolean {
        var ret = false
        var tg = baziInfo.dayTiangan
        var dz = baziInfo.yearDizhi
        var rootLevel = RootLevel.NO_ROOT
        var strongRootCount = 0
        var mediumRootCount = 0
        var weakRootCount = 0
        if (tg == TianGan.TIANGAN_JIA || tg == TianGan.TIANGAN_YI) {
            dz = baziInfo.yearDizhi
            baziInfo.yearDzRootLevel = checkMuRoot(dz)
            dz = baziInfo.monthDizhi
            baziInfo.monthDzRootLevel = checkMuRoot(dz)
            dz = baziInfo.dayDizhi
            baziInfo.dayDzRootLevel = checkMuRoot(dz)
            dz = baziInfo.hourDizhi
            baziInfo.hourDzRootLevel = checkMuRoot(dz)
        }
        if (tg == TianGan.TIANGAN_BING || tg == TianGan.TIANGAN_DING) {
            dz = baziInfo.yearDizhi
            baziInfo.yearDzRootLevel = checkHuoRoot(dz)
            dz = baziInfo.monthDizhi
            baziInfo.monthDzRootLevel = checkHuoRoot(dz)
            dz = baziInfo.dayDizhi
            baziInfo.dayDzRootLevel = checkHuoRoot(dz)
            dz = baziInfo.hourDizhi
            baziInfo.hourDzRootLevel = checkHuoRoot(dz)
        }
        if (tg == TianGan.TIANGAN_WU || tg == TianGan.TIANGAN_JI) {
            dz = baziInfo.yearDizhi
            baziInfo.yearDzRootLevel = checkTuRoot(dz)
            dz = baziInfo.monthDizhi
            baziInfo.monthDzRootLevel = checkTuRoot(dz)
            dz = baziInfo.dayDizhi
            baziInfo.dayDzRootLevel = checkTuRoot(dz)
            dz = baziInfo.hourDizhi
            baziInfo.hourDzRootLevel = checkTuRoot(dz)
        }
        if (tg == TianGan.TIANGAN_GENG || tg == TianGan.TIANGAN_XIN) {
            dz = baziInfo.yearDizhi
            baziInfo.yearDzRootLevel = checkJinRoot(dz)
            dz = baziInfo.monthDizhi
            baziInfo.monthDzRootLevel = checkJinRoot(dz)
            dz = baziInfo.dayDizhi
            baziInfo.dayDzRootLevel = checkJinRoot(dz)
            dz = baziInfo.hourDizhi
            baziInfo.hourDzRootLevel = checkJinRoot(dz)
        }
        if (tg == TianGan.TIANGAN_REN || tg == TianGan.TIANGAN_GUI) {
            dz = baziInfo.yearDizhi
            baziInfo.yearDzRootLevel = checkShuiRoot(dz)
            dz = baziInfo.monthDizhi
            baziInfo.monthDzRootLevel = checkShuiRoot(dz)
            dz = baziInfo.dayDizhi
            baziInfo.dayDzRootLevel = checkShuiRoot(dz)
            dz = baziInfo.hourDizhi
            baziInfo.hourDzRootLevel = checkShuiRoot(dz)
        }
        if (baziInfo.yearDzRootLevel != RootLevel.NO_ROOT ||
            baziInfo.monthDzRootLevel != RootLevel.NO_ROOT ||
            baziInfo.dayDzRootLevel != RootLevel.NO_ROOT ||
            baziInfo.hourDzRootLevel != RootLevel.NO_ROOT
        ) {
            ret = true
        }

        return ret
    }

    fun isBaziStrong(data: BaziData): Boolean {
        var ret = false
        var danglingWeight = 0
        var dediWeight = 0
        var totalWeight = 0
        var elementDiffWeight = getBaziHelpCount(data)
//        var baziStrength = BaziStrength.BAZI_BALANCED
//        var goodElementCount =

        if (isDangling(data.monthDizhi, data.dayTiangan)) {
            danglingWeight = 3
        } else {
            danglingWeight = -3
        }

        if (isBaziDedi(data)) {
            dediWeight = 2
        } else {
            dediWeight = -2
        }

        totalWeight = danglingWeight + dediWeight + elementDiffWeight

        //todo, for balanced case,
        if (totalWeight > 0) {
            ret = true
        }
        return ret
    }

    //return total good element - bad element
    fun getBaziHelpCount(data: BaziData): Int {
        var ret = false
        var yinCount = 0
        var shiShangCount = 0
        var biJieCount = 0
        var guanShaCount = 0
        var caiCount = 0
        var goodCount = 0
        yinCount = ShiShenUtil().getShiShenCount(
            data,
            ShiShen.SHISHEN_ZHENG_YIN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_YIN)
        shiShangCount = ShiShenUtil().getShiShenCount(
            data,
            ShiShen.SHISHEN_SHI_SHEN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHANG_GUAN)
        biJieCount = ShiShenUtil().getShiShenCount(
            data,
            ShiShen.SHISHEN_BI_JIAN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_JIE_CAI)
        guanShaCount = ShiShenUtil().getShiShenCount(
            data,
            ShiShen.SHISHEN_ZHENG_GUAN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_QI_SHA)
        caiCount = ShiShenUtil().getShiShenCount(
            data,
            ShiShen.SHISHEN_ZHENG_CAI
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_CAI)

        goodCount = yinCount + biJieCount - guanShaCount - shiShangCount - caiCount
        return goodCount
    }

    fun isBaziDedi(baziInfo: BaziData): Boolean {
        var strongRootCount = 0
        var mediumRootCount = 0
        var weakRootCount = 0
        var isDeDi = false
        var yearRootLevel = RootLevel.NO_ROOT
        var monthRootLevel = RootLevel.NO_ROOT
        var dayRootLevel = RootLevel.NO_ROOT
        var hourRootLevel = RootLevel.NO_ROOT

        //found root, check root strength
        if (checkRootToTianGan(baziInfo, baziInfo.dayTiangan)) {
            yearRootLevel = baziInfo.yearDzRootLevel
            monthRootLevel = baziInfo.monthDzRootLevel
            dayRootLevel = baziInfo.dayDzRootLevel
            hourRootLevel = baziInfo.hourDzRootLevel
            //strong
            if (yearRootLevel == RootLevel.STRONG_ROOT) {
                strongRootCount++
            }
            if (monthRootLevel == RootLevel.STRONG_ROOT) {
                strongRootCount++
            }
            if (dayRootLevel == RootLevel.STRONG_ROOT) {
                strongRootCount++
            }
            if (hourRootLevel == RootLevel.STRONG_ROOT) {
                strongRootCount++
            }

            //medium
            if (yearRootLevel == RootLevel.MEDIUM_ROOT) {
                mediumRootCount++
            }
            if (monthRootLevel == RootLevel.MEDIUM_ROOT) {
                mediumRootCount++
            }
            if (dayRootLevel == RootLevel.MEDIUM_ROOT) {
                mediumRootCount++
            }
            if (hourRootLevel == RootLevel.MEDIUM_ROOT) {
                mediumRootCount++
            }

            //weak
            if (yearRootLevel == RootLevel.WEAK_ROOT) {
                weakRootCount++
            }
            if (monthRootLevel == RootLevel.WEAK_ROOT) {
                weakRootCount++
            }
            if (dayRootLevel == RootLevel.WEAK_ROOT) {
                weakRootCount++
            }
            if (hourRootLevel == RootLevel.WEAK_ROOT) {
                weakRootCount++
            }

            baziInfo.strongRootCount = strongRootCount
            baziInfo.mediumRootCount = mediumRootCount
            baziInfo.weakRootCount = weakRootCount
            println("strongRootCount=$strongRootCount, mediumRootCount=$mediumRootCount, weakRootCount=$weakRootCount")
            //2 weak root is dedi, other root 1 is dedi
            if (strongRootCount > 0 || mediumRootCount > 0 || weakRootCount > 1) {
                isDeDi = true
            }
        }
        println("Bazi isDeDi=$isDeDi")
        return isDeDi
    }

    @Composable
    fun getOwnerWuXingString(
        baziInfo: BaziInfo,
        baziModel: BaziViewModel
    ): String {
        var data = baziInfo.baziData
        var wuxingStr =
            stringResource(R.string.app_bazi_metal) + "(" + WuXingUtil().getJinNumber(data) + ") " + stringResource(
                R.string.app_bazi_wood
            ) + "(" + WuXingUtil().getMuNumber(data) + ") " + stringResource(R.string.app_bazi_water) + "(" + WuXingUtil().getShuiNumber(
                data
            ) + ") " + stringResource(R.string.app_bazi_fire) + "(" + WuXingUtil().getHuoNumber(
                data
            ) + ") " + stringResource(R.string.app_bazi_soil) + "(" + WuXingUtil().getTuNumber(
                data
            ) + ")"
        baziModel.setWuxingSummaryStr(wuxingStr)
        return wuxingStr
    }

    @Composable
    fun getTianGanWuXingText(
        tg: TianGan
    ): String {
        var str = BaziUtil().getTianGanLabel(tg)
        str = str + WuXingUtil().getTianGanWuxingText(tg)
        return str
    }

    @Composable
    fun getBaziWuxingStatString(data: BaziData, targetWx: WuXing): String {
        val sb = StringBuilder()
        var tg = data.yearTiangan
        var dz = data.yearDizhi
        var tgWx = WuXing.WUXING_TU
        var dzWx = WuXing.WUXING_TU
        tgWx = getTianGanWuxing(tg)
        if (tgWx == targetWx) {
            sb.append(stringResource(R.string.app_label_year))
            sb.append(stringResource(R.string.app_label_gan))
            sb.append(" ")
            sb.append(WuXingUtil().getTianGanWuXingText(tg))
            sb.append("(")
            sb.append(ShiShenUtil().getShiShenText(tg, data.dayTiangan))
            sb.append(") ")
        }
        tg = data.monthTiangan
        tgWx = getTianGanWuxing(tg)
        if (tgWx == targetWx) {
            sb.append(stringResource(R.string.app_label_month))
            sb.append(stringResource(R.string.app_label_gan))
            sb.append(" ")
            sb.append(WuXingUtil().getTianGanWuXingText(tg))
            sb.append("(")
            sb.append(ShiShenUtil().getShiShenText(tg, data.dayTiangan))
            sb.append(") ")
        }
        tg = data.hourTiangan
        tgWx = getTianGanWuxing(tg)
        if (tgWx == targetWx) {
            sb.append(stringResource(R.string.app_label_hour))
            sb.append(stringResource(R.string.app_label_gan))
            sb.append(" ")
            sb.append(WuXingUtil().getTianGanWuXingText(tg))
            sb.append("(")
            sb.append(ShiShenUtil().getShiShenText(tg, data.dayTiangan))
            sb.append(") ")
        }

        //check dizhi
        dz = data.yearDizhi
        dzWx = getDiZhiWuxing(dz)
        if (dzWx == targetWx) {
            sb.append(stringResource(R.string.app_label_year))
            sb.append(stringResource(R.string.app_label_zhi))
            sb.append(" ")
            sb.append(BaziUtil().getDizhiText(dz))
            sb.append(WuXingUtil().getDiZhiWuxingText(dz))
            sb.append("(")
            sb.append(ShiShenUtil().getDiZhiShiShenText(dz, data.dayTiangan))
            sb.append(") ")
        }
        dz = data.monthDizhi
        dzWx = getDiZhiWuxing(dz)
        if (dzWx == targetWx) {
            sb.append(stringResource(R.string.app_label_month))
            sb.append(stringResource(R.string.app_label_zhi))
            sb.append(" ")
            sb.append(BaziUtil().getDizhiText(dz))
            sb.append(WuXingUtil().getDiZhiWuxingText(dz))
            sb.append("(")
            sb.append(ShiShenUtil().getDiZhiShiShenText(dz, data.dayTiangan))
            sb.append(") ")
        }
        dz = data.dayDizhi
        dzWx = getDiZhiWuxing(dz)
        if (dzWx == targetWx) {
            sb.append(stringResource(R.string.app_label_day))
            sb.append(stringResource(R.string.app_label_zhi))
            sb.append(" ")
            sb.append(BaziUtil().getDizhiText(dz))
            sb.append(WuXingUtil().getDiZhiWuxingText(dz))
            sb.append("(")
            sb.append(ShiShenUtil().getDiZhiShiShenText(dz, data.dayTiangan))
            sb.append(") ")
        }
        dz = data.hourDizhi
        dzWx = getDiZhiWuxing(dz)
        if (dzWx == targetWx) {
            sb.append(stringResource(R.string.app_label_hour))
            sb.append(stringResource(R.string.app_label_zhi))
            sb.append(" ")
            sb.append(BaziUtil().getDizhiText(dz))
            sb.append(WuXingUtil().getDiZhiWuxingText(dz))
            sb.append("(")
            sb.append(ShiShenUtil().getDiZhiShiShenText(dz, data.dayTiangan))
            sb.append(") ")
        }

        val finalString = sb.toString()
        return finalString
    }

}