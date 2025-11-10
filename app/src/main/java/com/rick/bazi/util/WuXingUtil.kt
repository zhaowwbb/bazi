package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.WuXing
import com.rick.bazi.ui.BaziViewModel

class WuXingUtil {

    @Composable
    fun getJinNumber(
        baziInfo: BaziInfo
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

    @Composable
    fun getMuNumber(
        baziInfo: BaziInfo
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

    @Composable
    fun getShuiNumber(
        baziInfo: BaziInfo
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

    @Composable
    fun getHuoNumber(
        baziInfo: BaziInfo
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

    @Composable
    fun getTuNumber(
        baziInfo: BaziInfo
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

//    fun getDiZhiWuxing(dz: DiZhi): WuXing {
//        if (dz == DiZhi.DIZHI_SHEN || dz == DiZhi.DIZHI_YOU) return WuXing.WUXING_JIN
//        if (dz == DiZhi.DIZHI_YIN || dz == DiZhi.DIZHI_MOU) return WuXing.WUXING_MU
//        if (dz == DiZhi.DIZHI_HAI || dz == DiZhi.DIZHI_ZI) return WuXing.WUXING_SHUI
//        if (dz == DiZhi.DIZHI_SI || dz == DiZhi.DIZHI_WU) return WuXing.WUXING_HUO
//        if (dz == DiZhi.DIZHI_CHEN || dz == DiZhi.DIZHI_XU || dz == DiZhi.DIZHI_CHOU || dz == DiZhi.DIZHI_WEI) return WuXing.WUXING_TU
//
//        return WuXing.WUXING_TU
//    }

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

    @Composable
    fun isDangling(monthDz: DiZhi, dayTg: TianGan): Boolean {
        var tgWuXing = getTianGanWuxing(dayTg)
        var dzWuXing = getDiZhiWuxing(monthDz)
        if (tgWuXing == dzWuXing) return true
        if (isWuXingBorn(tgWuXing, dzWuXing)) return true
        return false
    }

    fun getTgWX(tg : TianGan) : WuXing{
        var wx = WuXing.WUXING_SHUI
        if(tg == TianGan.TIANGAN_JIA || tg == TianGan.TIANGAN_YI){
            wx = WuXing.WUXING_MU
        }
        if(tg == TianGan.TIANGAN_BING || tg == TianGan.TIANGAN_DING){
            wx = WuXing.WUXING_HUO
        }
        if(tg == TianGan.TIANGAN_WU || tg == TianGan.TIANGAN_JI){
            wx = WuXing.WUXING_TU
        }
        if(tg == TianGan.TIANGAN_GENG || tg == TianGan.TIANGAN_XIN){
            wx = WuXing.WUXING_JIN
        }
        if(tg == TianGan.TIANGAN_REN || tg == TianGan.TIANGAN_GUI){
            wx = WuXing.WUXING_SHUI
        }
        return wx
    }

//    @Composable
//    fun getTianGanWuxing(tg : TianGan) : WuXing {
//        var ret = WuXing.WUXING_MU
//        if(tg == TianGan.TIANGAN_JIA || tg == TianGan.TIANGAN_YI){
//            ret = WuXing.WUXING_MU
//        }
//        if(tg == TianGan.TIANGAN_BING || tg == TianGan.TIANGAN_DING){
//            ret = WuXing.WUXING_HUO
//        }
//        if(tg == TianGan.TIANGAN_WU || tg == TianGan.TIANGAN_JI){
//            ret = WuXing.WUXING_TU
//        }
//        if(tg == TianGan.TIANGAN_GENG || tg == TianGan.TIANGAN_XIN){
//            ret = WuXing.WUXING_JIN
//        }
//        if(tg == TianGan.TIANGAN_REN || tg == TianGan.TIANGAN_GUI){
//            ret = WuXing.WUXING_SHUI
//        }
//        return ret;
//    }

    @Composable
    fun getWuXingText(wx : WuXing) : String {
        var str = ""
        if(wx == WuXing.WUXING_JIN){
            str = stringResource(R.string.app_bazi_metal)
        }
        if(wx == WuXing.WUXING_MU){
            str = stringResource(R.string.app_bazi_wood)
        }
        if(wx == WuXing.WUXING_SHUI){
            str = stringResource(R.string.app_bazi_water)
        }
        if(wx == WuXing.WUXING_HUO){
            str = stringResource(R.string.app_bazi_fire)
        }
        if(wx == WuXing.WUXING_TU){
            str = stringResource(R.string.app_bazi_soil)
        }
        return str
    }

    @Composable
    fun getTianGanWuxingText(tg : TianGan) : String {
        var tgWX = WuXing.WUXING_MU
        tgWX = getTianGanWuxing(tg)
        return getWuXingText(tgWX)
    }

    @Composable
    fun getDiZhiWuxingText(dz : DiZhi) : String {
        var wx = getDiZhiWuxing(dz)
        return getWuXingText(wx)
    }

    @Composable
    fun getDiZhiWuxing(dz : DiZhi) : WuXing {
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
    fun createBaziStr(baziInfo: BaziInfo) : String{
        var baziStr = " " + getTianganStr(baziInfo, baziInfo.yearTiangan) + getDizhiStr(baziInfo, baziInfo.yearDizhi) + " " + getTianganStr(baziInfo, baziInfo.monthTiangan) + getDizhiStr(baziInfo, baziInfo.monthDizhi) + " " + getTianganStr(baziInfo, baziInfo.dayTiangan) + getDizhiStr(baziInfo, baziInfo.dayDizhi) + " " + getTianganStr(baziInfo, baziInfo.hourTiangan) + getDizhiStr(baziInfo, baziInfo.hourDizhi)

        return baziStr
    }

    @Composable
    fun getOwnerStr(
        baziInfo: BaziInfo
    ): String {
        var ownerStr = getTianganStr(baziInfo, baziInfo.dayTiangan)
        ownerStr = ownerStr + WuXingUtil().getTianGanWuxingText(baziInfo.dayTiangan)
        return ownerStr
    }

    @Composable
    fun getTiaohouWuXingText(baziInfo: BaziInfo, baziModel: BaziViewModel) : String{
        var summary = ""
        var tiaohouShenList : List<WuXing> = arrayListOf<WuXing>()
        var monthDz : DiZhi = baziInfo.monthDizhi
        //cold
        if(monthDz == DiZhi.DIZHI_HAI || monthDz == DiZhi.DIZHI_ZI || monthDz == DiZhi.DIZHI_CHOU){
            summary = stringResource(R.string.app_bazi_tiaohou_cold)  + "[" + getWuXingText(WuXing.WUXING_HUO) + "]"
            tiaohouShenList = arrayListOf<WuXing>(WuXing.WUXING_HUO)
        }
        //hot
        if(monthDz == DiZhi.DIZHI_SI || monthDz == DiZhi.DIZHI_WU || monthDz == DiZhi.DIZHI_WEI){
            summary = stringResource(R.string.app_bazi_tiaohou_hot) + "[" + getWuXingText(WuXing.WUXING_SHUI) + "]"
            tiaohouShenList = arrayListOf<WuXing>(WuXing.WUXING_SHUI)
        }

        //dry
        if(monthDz == DiZhi.DIZHI_WEI || monthDz == DiZhi.DIZHI_XU){
            summary = stringResource(R.string.app_bazi_tiaohou_dry) + "[" + getWuXingText(WuXing.WUXING_SHUI) + "]"
            tiaohouShenList = arrayListOf<WuXing>(WuXing.WUXING_SHUI)
        }

        //wet
        if(monthDz == DiZhi.DIZHI_CHOU || monthDz == DiZhi.DIZHI_CHEN){
            summary = stringResource(R.string.app_bazi_tiaohou_wet) + "[" + getWuXingText(WuXing.WUXING_HUO) + "]"
            tiaohouShenList = arrayListOf<WuXing>(WuXing.WUXING_HUO)
        }

        //mild
        if(monthDz == DiZhi.DIZHI_YIN || monthDz == DiZhi.DIZHI_MOU || monthDz == DiZhi.DIZHI_SHEN || monthDz == DiZhi.DIZHI_YOU){
            summary = stringResource(R.string.app_bazi_tiaohou_mild)
        }

        baziModel.setBaziTiaohouShenList(tiaohouShenList)
        return summary
    }

    @Composable
    fun getBiJieWuXing(tg : TianGan) : WuXing{
        var ret = WuXing.WUXING_MU
        var ownerWX = getTianGanWuxing(tg)
        if(ownerWX == WuXing.WUXING_MU){
            return WuXing.WUXING_MU
        }
        if(ownerWX == WuXing.WUXING_SHUI){
            return WuXing.WUXING_SHUI
        }
        if(ownerWX == WuXing.WUXING_TU){
            return WuXing.WUXING_TU
        }
        if(ownerWX == WuXing.WUXING_HUO){
            return WuXing.WUXING_HUO
        }
        if(ownerWX == WuXing.WUXING_JIN){
            return WuXing.WUXING_JIN
        }
        return ret
    }

    @Composable
    fun getGuanshaWuXing(tg : TianGan) : WuXing{
        var ret = WuXing.WUXING_MU
        var ownerWX = getTianGanWuxing(tg)
        if(ownerWX == WuXing.WUXING_MU){
            return WuXing.WUXING_JIN
        }
        if(ownerWX == WuXing.WUXING_SHUI){
            return WuXing.WUXING_TU
        }
        if(ownerWX == WuXing.WUXING_TU){
            return WuXing.WUXING_MU
        }
        if(ownerWX == WuXing.WUXING_HUO){
            return WuXing.WUXING_SHUI
        }
        if(ownerWX == WuXing.WUXING_JIN){
            return WuXing.WUXING_HUO
        }
        return ret
    }

    @Composable
    fun getShishangWuXing(tg : TianGan) : WuXing{
        var ret = WuXing.WUXING_MU
        var ownerWX = getTianGanWuxing(tg)
        if(ownerWX == WuXing.WUXING_MU){
            return WuXing.WUXING_HUO
        }
        if(ownerWX == WuXing.WUXING_SHUI){
            return WuXing.WUXING_MU
        }
        if(ownerWX == WuXing.WUXING_TU){
            return WuXing.WUXING_JIN
        }
        if(ownerWX == WuXing.WUXING_HUO){
            return WuXing.WUXING_TU
        }
        if(ownerWX == WuXing.WUXING_JIN){
            return WuXing.WUXING_SHUI
        }
        return ret
    }

    @Composable
    fun getCaiWuXing(tg : TianGan) : WuXing{
        var ret = WuXing.WUXING_MU
        var ownerWX = getTianGanWuxing(tg)
        if(ownerWX == WuXing.WUXING_MU){
            return WuXing.WUXING_TU
        }
        if(ownerWX == WuXing.WUXING_SHUI){
            return WuXing.WUXING_HUO
        }
        if(ownerWX == WuXing.WUXING_TU){
            return WuXing.WUXING_SHUI
        }
        if(ownerWX == WuXing.WUXING_HUO){
            return WuXing.WUXING_JIN
        }
        if(ownerWX == WuXing.WUXING_JIN){
            return WuXing.WUXING_MU
        }
        return ret
    }

    @Composable
    fun getCaiWuXingText(tg : TianGan) : String{
        var wx = getCaiWuXing(tg)
        return getWuXingText(wx)
    }

    @Composable
    fun getShishangWuXingText(tg : TianGan) : String{
        var wx = getShishangWuXing(tg)
        return getWuXingText(wx)
    }

    @Composable
    fun getGuanshaWuXingText(tg : TianGan) : String{
        var wx = getGuanshaWuXing(tg)
        return getWuXingText(wx)
    }

    @Composable
    fun getBiJieWuXingText(tg : TianGan) : String{
        var wx = getBiJieWuXing(tg)
        return getWuXingText(wx)
    }

    @Composable
    fun getYinWuXing(tg : TianGan) : WuXing{
        var ret = WuXing.WUXING_MU
        var ownerWX = getTianGanWuxing(tg)
        if(ownerWX == WuXing.WUXING_MU){
            return WuXing.WUXING_SHUI
        }
        if(ownerWX == WuXing.WUXING_SHUI){
            return WuXing.WUXING_JIN
        }
        if(ownerWX == WuXing.WUXING_TU){
            return WuXing.WUXING_HUO
        }
        if(ownerWX == WuXing.WUXING_HUO){
            return WuXing.WUXING_MU
        }
        if(ownerWX == WuXing.WUXING_JIN){
            return WuXing.WUXING_TU
        }
        return ret
    }

    @Composable
    fun getYinWuXingText(tg : TianGan) : String{
        var wx = getYinWuXing(tg)
        return getWuXingText(wx)
    }


    @Composable
    fun getKeXieHaoStr(baziInfo: BaziInfo, baziModel: BaziViewModel) : String{
        var baziStr = " " + getTianganStr(baziInfo, baziInfo.yearTiangan) + getDizhiStr(baziInfo, baziInfo.yearDizhi) + " " + getTianganStr(baziInfo, baziInfo.monthTiangan) + getDizhiStr(baziInfo, baziInfo.monthDizhi) + " " + getTianganStr(baziInfo, baziInfo.dayTiangan) + getDizhiStr(baziInfo, baziInfo.dayDizhi) + " " + getTianganStr(baziInfo, baziInfo.hourTiangan) + getDizhiStr(baziInfo, baziInfo.hourDizhi)

        return baziStr
    }

    @Composable
    fun getBaziStr(baziInfo: BaziInfo) : String{
        var baziStr = " " + getTianganStr(baziInfo, baziInfo.yearTiangan) + getDizhiStr(baziInfo, baziInfo.yearDizhi) + " " + getTianganStr(baziInfo, baziInfo.monthTiangan) + getDizhiStr(baziInfo, baziInfo.monthDizhi) + " " + getTianganStr(baziInfo, baziInfo.dayTiangan) + getDizhiStr(baziInfo, baziInfo.dayDizhi) + " " + getTianganStr(baziInfo, baziInfo.hourTiangan) + getDizhiStr(baziInfo, baziInfo.hourDizhi)

        return baziStr
    }

    @Composable
    fun getDangLingStr(
        baziInfo: BaziInfo,
        baziModel: BaziViewModel
    ): String {
        var result = WuXingUtil().getOwnerStr(baziInfo) +
                stringResource(R.string.app_bazi_born_label) +
                getDizhiStr(baziInfo, baziInfo.monthDizhi) +
                stringResource(R.string.app_label_month)

        if(WuXingUtil().isDangling(baziInfo.monthDizhi, baziInfo.dayTiangan)){
            result = result + stringResource(R.string.bazi_owner) + stringResource(R.string.app_bazi_wangxiang_label) + " " + stringResource(R.string.app_bazi_dangling_yes)
            baziModel.setDangLing(true)
        }else{
            result = result + stringResource(R.string.bazi_owner) + stringResource(R.string.app_bazi_xiuqiu_label) + " " + stringResource(R.string.app_bazi_dangling_no)
            baziModel.setDangLing(false)
        }


        return result
    }

    @Composable
    fun getDeDiCheckStr(
        baziInfo: BaziInfo,
        baziModel: BaziViewModel
    ): String {
        var isDedi = false
        var deDiStr = "  "

        deDiStr = deDiStr + stringResource(R.string.dizhi) + " " + baziInfo.strongRootNum + stringResource(R.string.app_bazi_strong_root) + " " + baziInfo.weakRootNum + stringResource(R.string.app_bazi_weak_root)
        if(baziInfo.strongRootNum > 0 || baziInfo.weakRootNum > 0) {
            deDiStr = deDiStr + " " + stringResource(R.string.app_bazi_dedi_yes)
        }else{
            deDiStr = deDiStr + " " + stringResource(R.string.app_bazi_dedi_no)
        }
        return deDiStr

    }

    fun isDeDi( baziInfo: BaziInfo, baziModel: BaziViewModel): Boolean{
        var tg = baziInfo.dayTiangan
        var dz = baziInfo.yearDizhi
        var strongRootNum = 0
        var weakRootNum = 0
        if(tg == TianGan.TIANGAN_JIA || tg == TianGan.TIANGAN_YI){
            //yearDz
            if(dz == DiZhi.DIZHI_YIN || dz == DiZhi.DIZHI_MOU){
                strongRootNum++;
            }
            if(dz == DiZhi.DIZHI_WEI || dz == DiZhi.DIZHI_CHEN || dz == DiZhi.DIZHI_HAI || dz == DiZhi.DIZHI_ZI || dz == DiZhi.DIZHI_CHOU){
                weakRootNum++
            }

            //month
            dz = baziInfo.monthDizhi
            if(dz == DiZhi.DIZHI_YIN || dz == DiZhi.DIZHI_MOU){
                strongRootNum++;
            }
            if(dz == DiZhi.DIZHI_WEI || dz == DiZhi.DIZHI_CHEN || dz == DiZhi.DIZHI_HAI || dz == DiZhi.DIZHI_ZI || dz == DiZhi.DIZHI_CHOU){
                weakRootNum++
            }

            //day
            dz = baziInfo.dayDizhi
            if(dz == DiZhi.DIZHI_YIN || dz == DiZhi.DIZHI_MOU){
                strongRootNum++;
            }
            if(dz == DiZhi.DIZHI_WEI || dz == DiZhi.DIZHI_CHEN || dz == DiZhi.DIZHI_HAI || dz == DiZhi.DIZHI_ZI || dz == DiZhi.DIZHI_CHOU){
                weakRootNum++
            }

            //hour
            dz = baziInfo.hourDizhi
            if(dz == DiZhi.DIZHI_YIN || dz == DiZhi.DIZHI_MOU){
                strongRootNum++;
            }
            if(dz == DiZhi.DIZHI_WEI || dz == DiZhi.DIZHI_CHEN || dz == DiZhi.DIZHI_HAI || dz == DiZhi.DIZHI_ZI || dz == DiZhi.DIZHI_CHOU){
                weakRootNum++
            }
        }

        if(tg == TianGan.TIANGAN_BING || tg == TianGan.TIANGAN_DING){
            //yearDz
            if(dz == DiZhi.DIZHI_SI || dz == DiZhi.DIZHI_WU){
                strongRootNum++;
            }
            if(dz == DiZhi.DIZHI_WEI || dz == DiZhi.DIZHI_XU || dz == DiZhi.DIZHI_YIN || dz == DiZhi.DIZHI_MOU || dz == DiZhi.DIZHI_CHEN){
                weakRootNum++
            }
            //month
            dz = baziInfo.monthDizhi
            if(dz == DiZhi.DIZHI_SI || dz == DiZhi.DIZHI_WU){
                strongRootNum++;
            }
            if(dz == DiZhi.DIZHI_WEI || dz == DiZhi.DIZHI_XU || dz == DiZhi.DIZHI_YIN || dz == DiZhi.DIZHI_MOU || dz == DiZhi.DIZHI_CHEN){
                weakRootNum++
            }
            //day
            dz = baziInfo.dayDizhi
            if(dz == DiZhi.DIZHI_SI || dz == DiZhi.DIZHI_WU){
                strongRootNum++;
            }
            if(dz == DiZhi.DIZHI_WEI || dz == DiZhi.DIZHI_XU || dz == DiZhi.DIZHI_YIN || dz == DiZhi.DIZHI_MOU || dz == DiZhi.DIZHI_CHEN){
                weakRootNum++
            }

            //hour
            dz = baziInfo.hourDizhi
            if(dz == DiZhi.DIZHI_SI || dz == DiZhi.DIZHI_WU){
                strongRootNum++;
            }
            if(dz == DiZhi.DIZHI_WEI || dz == DiZhi.DIZHI_XU || dz == DiZhi.DIZHI_YIN || dz == DiZhi.DIZHI_MOU || dz == DiZhi.DIZHI_CHEN){
                weakRootNum++
            }
        }

        if(tg == TianGan.TIANGAN_WU || tg == TianGan.TIANGAN_JI) {
            //yearDz
            if (dz == DiZhi.DIZHI_SI || dz == DiZhi.DIZHI_WU ) {
                strongRootNum++
            }
            if (dz == DiZhi.DIZHI_CHEN || dz == DiZhi.DIZHI_XU || dz == DiZhi.DIZHI_CHOU || dz == DiZhi.DIZHI_WEI) {
                weakRootNum++;
            }

            //month
            dz = baziInfo.monthDizhi
            if (dz == DiZhi.DIZHI_SI || dz == DiZhi.DIZHI_WU ) {
                strongRootNum++
            }
            if (dz == DiZhi.DIZHI_CHEN || dz == DiZhi.DIZHI_XU || dz == DiZhi.DIZHI_CHOU || dz == DiZhi.DIZHI_WEI) {
                weakRootNum++;
            }

            //day
            dz = baziInfo.dayDizhi
            if (dz == DiZhi.DIZHI_SI || dz == DiZhi.DIZHI_WU ) {
                strongRootNum++
            }
            if (dz == DiZhi.DIZHI_CHEN || dz == DiZhi.DIZHI_XU || dz == DiZhi.DIZHI_CHOU || dz == DiZhi.DIZHI_WEI) {
                weakRootNum++;
            }

            //hour
            dz = baziInfo.hourDizhi
            if (dz == DiZhi.DIZHI_SI || dz == DiZhi.DIZHI_WU ) {
                strongRootNum++
            }
            if (dz == DiZhi.DIZHI_CHEN || dz == DiZhi.DIZHI_XU || dz == DiZhi.DIZHI_CHOU || dz == DiZhi.DIZHI_WEI) {
                weakRootNum++;
            }
        }

        if(tg == TianGan.TIANGAN_GENG || tg == TianGan.TIANGAN_XIN) {
            //yearDz
            if (dz == DiZhi.DIZHI_SHEN || dz == DiZhi.DIZHI_YOU) {
                strongRootNum++
            }
            if (dz == DiZhi.DIZHI_CHOU || dz == DiZhi.DIZHI_XU || dz == DiZhi.DIZHI_SI) {
                weakRootNum++;
            }

            //month
            dz = baziInfo.monthDizhi
            if (dz == DiZhi.DIZHI_SHEN || dz == DiZhi.DIZHI_YOU) {
                strongRootNum++
            }
            if (dz == DiZhi.DIZHI_CHOU || dz == DiZhi.DIZHI_XU || dz == DiZhi.DIZHI_SI) {
                weakRootNum++;
            }

            //day
            dz = baziInfo.dayDizhi
            if (dz == DiZhi.DIZHI_SHEN || dz == DiZhi.DIZHI_YOU) {
                strongRootNum++
            }
            if (dz == DiZhi.DIZHI_CHOU || dz == DiZhi.DIZHI_XU || dz == DiZhi.DIZHI_SI) {
                weakRootNum++;
            }

            //hour
            dz = baziInfo.hourDizhi
            if (dz == DiZhi.DIZHI_SHEN || dz == DiZhi.DIZHI_YOU) {
                strongRootNum++
            }
            if (dz == DiZhi.DIZHI_CHOU || dz == DiZhi.DIZHI_XU || dz == DiZhi.DIZHI_SI) {
                weakRootNum++;
            }
        }

        if(tg == TianGan.TIANGAN_REN || tg == TianGan.TIANGAN_GUI) {
            //yearDz
            if (dz == DiZhi.DIZHI_HAI || dz == DiZhi.DIZHI_ZI) {
                strongRootNum++
            }
            if (dz == DiZhi.DIZHI_CHOU || dz == DiZhi.DIZHI_SHEN || dz == DiZhi.DIZHI_CHEN) {
                weakRootNum++;
            }

            //month
            dz = baziInfo.monthDizhi
            if (dz == DiZhi.DIZHI_HAI || dz == DiZhi.DIZHI_ZI) {
                strongRootNum++
            }
            if (dz == DiZhi.DIZHI_CHOU || dz == DiZhi.DIZHI_SHEN || dz == DiZhi.DIZHI_CHEN) {
                weakRootNum++;
            }

            //day
            dz = baziInfo.dayDizhi
            if (dz == DiZhi.DIZHI_HAI || dz == DiZhi.DIZHI_ZI) {
                strongRootNum++
            }
            if (dz == DiZhi.DIZHI_CHOU || dz == DiZhi.DIZHI_SHEN || dz == DiZhi.DIZHI_CHEN) {
                weakRootNum++;
            }

            //hour
            dz = baziInfo.hourDizhi
            if (dz == DiZhi.DIZHI_HAI || dz == DiZhi.DIZHI_ZI) {
                strongRootNum++
            }
            if (dz == DiZhi.DIZHI_CHOU || dz == DiZhi.DIZHI_SHEN || dz == DiZhi.DIZHI_CHEN) {
                weakRootNum++;
            }
        }

        baziModel.setStrongRootNum(strongRootNum)
        baziModel.setWeakRootNum(weakRootNum)
        if(strongRootNum > 0 || weakRootNum > 0)return true

        return false
    }
}