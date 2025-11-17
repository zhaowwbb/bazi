package com.rick.bazi.util

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.BaziGeJu
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.ShiShen
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.WuXing
import com.rick.bazi.ui.BaziViewModel

class GeJuUtil {

    val cangganLookupMap: Map<DiZhi, Array<TianGan>> = mapOf(
        DiZhi.DIZHI_ZI to arrayOf(TianGan.TIANGAN_GUI),
        DiZhi.DIZHI_CHOU to arrayOf(TianGan.TIANGAN_JI, TianGan.TIANGAN_GUI, TianGan.TIANGAN_XIN),
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

//    @Composable
    fun getGJByShiShen(ss : ShiShen) : BaziGeJu {
        var gj = BaziGeJu.GJ_NONE
        if(ss == ShiShen.SHISHEN_ZHENG_GUAN){
            return BaziGeJu.GJ_ZHENG_GUAN
        }
        if(ss == ShiShen.SHISHEN_QI_SHA){
            return BaziGeJu.GJ_QI_SHA
        }
        if(ss == ShiShen.SHISHEN_SHI_SHEN){
            return BaziGeJu.GJ_SHI_SHEN
        }
        if(ss == ShiShen.SHISHEN_SHANG_GUAN){
            return BaziGeJu.GJ_SHANG_GUAN
        }
        if(ss == ShiShen.SHISHEN_ZHENG_YIN){
            return BaziGeJu.GJ_ZHENG_YIN
        }
        if(ss == ShiShen.SHISHEN_PIAN_YIN){
            return BaziGeJu.GJ_PIAN_YIN
        }
        if(ss == ShiShen.SHISHEN_ZHENG_CAI){
            return BaziGeJu.GJ_ZHENG_CAI
        }
        if(ss == ShiShen.SHISHEN_PIAN_CAI){
            return BaziGeJu.GJ_PIAN_CAI
        }
        if(ss == ShiShen.SHISHEN_BI_JIAN || ss == ShiShen.SHISHEN_JIE_CAI){
            return BaziGeJu.GJ_JIAN_LU
        }

        return gj
    }

    @Composable
    fun getGJText(gj : BaziGeJu) : String {
        var str = ""
        if(gj == BaziGeJu.GJ_NONE){
            str = stringResource(R.string.app_bazi_gj_no_label)
        }else if(gj == BaziGeJu.GJ_ZHENG_GUAN){
            str = stringResource(R.string.app_bazi_gj_zhengguan)
        }else if(gj == BaziGeJu.GJ_QI_SHA){
            str = stringResource(R.string.app_bazi_gj_qisha)
        }else if(gj == BaziGeJu.GJ_SHI_SHEN){
            str = stringResource(R.string.app_bazi_gj_shishen)
        }else if(gj == BaziGeJu.GJ_SHANG_GUAN){
            str = stringResource(R.string.app_bazi_gj_shangguan)
        }else if(gj == BaziGeJu.GJ_ZHENG_YIN){
            str = stringResource(R.string.app_bazi_gj_zhengyin)
        }else if(gj == BaziGeJu.GJ_PIAN_YIN){
            str = stringResource(R.string.app_bazi_gj_pianyin)
        }else if(gj == BaziGeJu.GJ_ZHENG_CAI){
            str = stringResource(R.string.app_bazi_gj_zhengcai)
        }else if(gj == BaziGeJu.GJ_PIAN_CAI){
            str = stringResource(R.string.app_bazi_gj_piancai)
        }else if(gj == BaziGeJu.GJ_JIAN_LU){
            str = stringResource(R.string.app_bazi_gj_jianlu)
        }

        return str
    }

    @Composable
    fun getGJSummary(gj : BaziGeJu) : String {
        var str = ""
        if(gj == BaziGeJu.GJ_NONE){
            str = stringResource(R.string.app_bazi_gj_no_label)
        }else if(gj == BaziGeJu.GJ_ZHENG_GUAN){
            str = stringResource(R.string.app_bazi_gj_zhengguan_desc)
        }else if(gj == BaziGeJu.GJ_QI_SHA){
            str = stringResource(R.string.app_bazi_gj_qisha_desc)
        }else if(gj == BaziGeJu.GJ_SHI_SHEN){
            str = stringResource(R.string.app_bazi_gj_shishen_desc)
        }else if(gj == BaziGeJu.GJ_SHANG_GUAN){
            str = stringResource(R.string.app_bazi_gj_shangguan_desc)
        }else if(gj == BaziGeJu.GJ_ZHENG_YIN){
            str = stringResource(R.string.app_bazi_gj_zhengyin_desc)
        }else if(gj == BaziGeJu.GJ_PIAN_YIN){
            str = stringResource(R.string.app_bazi_gj_pianyin_desc)
        }else if(gj == BaziGeJu.GJ_ZHENG_CAI){
            str = stringResource(R.string.app_bazi_gj_zhengcai_desc)
        }else if(gj == BaziGeJu.GJ_PIAN_CAI){
            str = stringResource(R.string.app_bazi_gj_piancai_desc)
        }else if(gj == BaziGeJu.GJ_JIAN_LU){
            str = stringResource(R.string.app_bazi_gj_jianlu_desc)
        }

        return str
    }

    @Composable
    fun isRootTouTianGan(baziInfo: BaziInfo, wx : WuXing) : Boolean{
        var ret = false
        var isTianGanExist = false
        var isDiZhiExist = false
        if(WuXingUtil().getTianGanWuxing(baziInfo.yearTiangan) == wx ||
            WuXingUtil().getTianGanWuxing(baziInfo.monthTiangan) == wx ||
            WuXingUtil().getTianGanWuxing(baziInfo.dayTiangan) == wx ||
            WuXingUtil().getTianGanWuxing(baziInfo.hourTiangan) == wx){
            isTianGanExist = true
        }

        if(WuXingUtil().getDiZhiWuxing(baziInfo.yearDizhi) == wx ||
            WuXingUtil().getDiZhiWuxing(baziInfo.monthDizhi) == wx ||
            WuXingUtil().getDiZhiWuxing(baziInfo.dayDizhi) == wx ||
            WuXingUtil().getDiZhiWuxing(baziInfo.hourDizhi) == wx){
            isDiZhiExist = true
        }

        if(isTianGanExist && isDiZhiExist){
            ret = true
        }

        return ret
    }

    fun isTongGen(tg: TianGan, baziInfo: BaziData): Boolean {
        var ret = false
        var wx = WuXingUtil().getTgWX(tg)
        if (wx == WuXingUtil().getTgWX(baziInfo.yearTiangan)) return true
        if (wx == WuXingUtil().getTgWX(baziInfo.monthTiangan)) return true
        if (wx == WuXingUtil().getTgWX(baziInfo.dayTiangan)) return true
        if (wx == WuXingUtil().getTgWX(baziInfo.hourTiangan)) return true
        return ret
    }

    fun isTouChu(tg: TianGan, baziInfo: BaziData): Boolean {
        var ret = false
        if(tg == baziInfo.yearTiangan)return true
        if(tg == baziInfo.monthTiangan)return true
        if(tg == baziInfo.dayTiangan)return true
        if(tg == baziInfo.hourTiangan)return true
        return ret
    }

    fun getGJ(baziData : BaziData) : BaziGeJu{
        var gj = BaziGeJu.GJ_NONE
        var gjTG = TianGan.TIANGAN_JIA
        var foundGJ = false

        //normal GeJu
        var monthDz = baziData.monthDizhi
        var ss = ShiShen.SHISHEN_SHI_SHEN

//        fun isCongShaGJ(baziInfo: BaziInfo, baziModel: BaziViewModel) : Boolean {

        var tgList = cangganLookupMap.get(monthDz)
        if (tgList != null) {
            if (tgList.size == 1) {
                gjTG = tgList.get(0)
            } else {
                for (tg in tgList) {
                    //check tou chu
                    if (isTouChu(tg, baziData)) {
                        gjTG = tg
                        println("Found tou chu gj TianGan: gjTG=$gjTG")
                        foundGJ = true
                        break
                    }
                }
                if(!foundGJ){
                    for (tg in tgList) {
                        //check tong gen
                        if (isTongGen(tg, baziData)) {
                            gjTG = tg
                            println("Found tong gen gj TianGan: gjTG=$gjTG")
                            foundGJ = true
                            break
                        }
                    }
                }
            }
            ss = ShiShenUtil().getShiShen(gjTG, baziData.dayTiangan)
            gj = GeJuUtil().getGJByShiShen(ss)
        }
        println("Found gj=$gj")
        return gj
    }

    @Composable
    fun isCongShaGJ(baziInfo: BaziInfo, baziModel: BaziViewModel) : Boolean {
        //1970年10月21日 巳时
        //八字: 庚戌 丁亥 己巳 丁巳
        var ret = false
        var gjString = ""
        var gjSummary = ""
        var gj = BaziGeJu.GJ_NONE
        if (baziInfo.isDangLing) return false
        //
        if (baziInfo.strongRootCount > 0 || baziInfo.mediumRootCount > 0 || baziInfo.weakRootCount > 1) return false

        //only Yin TianGan with weak root is CongSha
        if (baziInfo.weakRootCount == 1 && BaziUtil().isYangTianGan(baziInfo.dayTiangan)) return false

        if (baziInfo.guanshaCount >= 2 && (baziInfo.guanshaCount + baziInfo.caiCount) >= 4 && baziInfo.shishangCount == 0) {
            if (isRootTouTianGan(baziInfo, WuXingUtil().getGuanshaWuXing(baziInfo.dayTiangan))) {
                if (!isRootTouTianGan(baziInfo, WuXingUtil().getYinWuXing(baziInfo.dayTiangan))) {
                    ret = true
                    gj = BaziGeJu.GJ_CONG_SHA
                    gjSummary = stringResource(R.string.app_bazi_gj_no_label)
                    gjString = stringResource(R.string.app_bazi_gj_no_label)
                } else {
                    return false
                }
            } else {
                return false
            }

        } else {
            return false
        }

        baziModel.setBaziGJ(gj)
        baziModel.setBaziGJSummary(gjSummary)
        baziModel.setBaziGJString(gjString)
        return ret
    }

    @Composable
    fun getJobDescription(gj : BaziGeJu) : String{
        var str = ""
        if(gj == BaziGeJu.GJ_ZHENG_YIN || gj == BaziGeJu.GJ_PIAN_YIN){
            str = stringResource(R.string.app_bazi_gj_job_yin)
        }else if(gj == BaziGeJu.GJ_SHI_SHEN || gj == BaziGeJu.GJ_SHANG_GUAN){
            str = stringResource(R.string.app_bazi_gj_job_ss)
        } else if(gj == BaziGeJu.GJ_ZHENG_GUAN || gj == BaziGeJu.GJ_QI_SHA){
            str = stringResource(R.string.app_bazi_gj_job_gs)
        } else if(gj == BaziGeJu.GJ_ZHENG_CAI || gj == BaziGeJu.GJ_PIAN_CAI){
            str = stringResource(R.string.app_bazi_gj_job_cai)
        }else{
            str = stringResource(R.string.app_bazi_gj_job_tbd)
        }
        return str
    }

    fun isYinStrong(data : BaziData) : Boolean{
        var ret = false
        var yinCount = 0
        var biJieCount = 0

        yinCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_YIN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_YIN)
        biJieCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_BI_JIAN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_JIE_CAI)

        if(yinCount >= biJieCount){
            ret = true
        }
        println("isYinStrong=$ret")
        return ret
    }

    fun isBiJieStrong(data : BaziData) : Boolean{
        var ret = false
        var yinCount = 0
        var biJieCount = 0

        yinCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_YIN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_YIN)
        biJieCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_BI_JIAN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_JIE_CAI)

        if(biJieCount >= yinCount){
            ret = true
        }
        println("isBiJieStrong=$ret")
        return ret
    }

    fun isGuanShaStrong(data : BaziData) : Boolean{
        var ret = false
        var shiShangCount = 0
        var guanShaCount = 0
        var caiCount = 0

        shiShangCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHI_SHEN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHANG_GUAN)
        guanShaCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_GUAN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_QI_SHA)
        caiCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_CAI) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_CAI)

        println("shiShangCount=$shiShangCount, guanShaCount=$guanShaCount, caiCount=$caiCount")

        if(guanShaCount >= shiShangCount && guanShaCount >= caiCount){
            ret = true
        }
        println("isGuanShaStrong=$ret")
        return ret
    }

    fun isShiShangStrong(data : BaziData) : Boolean{
        var ret = false
        var yinCount = 0
        var shiShangCount = 0
        var biJieCount = 0
        var guanShaCount = 0
        var caiCount = 0

        yinCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_YIN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_YIN)
        shiShangCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHI_SHEN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHANG_GUAN)
        biJieCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_BI_JIAN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_JIE_CAI)
        guanShaCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_GUAN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_QI_SHA)
        caiCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_CAI) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_CAI)

        if(shiShangCount >= guanShaCount && shiShangCount >= caiCount){
            ret = true
        }
        println("isShiShangStrong=$ret")
        return ret
    }

    fun isCaiStrong(data : BaziData) : Boolean{
        var ret = false
        var shiShangCount = 0
        var guanShaCount = 0
        var caiCount = 0

        shiShangCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHI_SHEN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHANG_GUAN)
        guanShaCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_GUAN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_QI_SHA)
        caiCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_CAI) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_CAI)

        if(caiCount >= guanShaCount && caiCount >= shiShangCount){
            ret = true
        }
        println("isCaiStrong=$ret")
        return ret
    }

    fun checkYinGJXiJi(gj : BaziGeJu, data : BaziData){
        var isBaziStrong = WuXingUtil().isBaziStrong(data)
        println("checkYinGJXiJi(), isBaziStrong=$isBaziStrong")
        if(isBaziStrong){
            if(isYinStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI, ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
            }else if(isBiJieStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA, ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA, ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN, ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_JIE_CAI)
            }else if(isCaiStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
            }
        }else{
            if(isGuanShaStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
            }else if(isShiShangStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
            }
        }
    }

    fun checkCaiGJXiJi(gj : BaziGeJu, data : BaziData){
        var isBaziStrong = WuXingUtil().isBaziStrong(data)
        println("checkCaiGJXiJi(), isBaziStrong=$isBaziStrong")
        if(isBaziStrong){
            if(isYinStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI, ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
            }else if(isBiJieStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.xiShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.jiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
            }
        }else{
            if(isShiShangStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
            }else if(isCaiStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN, ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
            }else if(isGuanShaStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
            }
        }
    }

    fun checkShangGuanGJXiJi(gj : BaziGeJu, data : BaziData){
        var isBaziStrong = WuXingUtil().isBaziStrong(data)
        println("[ShangGuanGJ] isBaziStrong=$isBaziStrong")
        if(isBaziStrong){
            if(isYinStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN)
            }else if(isBiJieStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_QI_SHA)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_QI_SHA)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN)
            }
        }else{
            if(isGuanShaStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_JIE_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
            }else if(isCaiStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
            }else if(isShiShangStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
            }
        }
    }

    fun checkZhengGuanGJXiJi(gj : BaziGeJu, data : BaziData){
        //example, 2003,08, 10, 00
        var isBaziStrong = WuXingUtil().isBaziStrong(data)
        println("[ZhengGuanGJ] isBaziStrong=$isBaziStrong")
        if(isBaziStrong){
            if(isBiJieStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA, ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
            }else if(isYinStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
            }else if(isShiShangStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.jiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
            }
        }else{
            if(isCaiStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI, ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
            }else if(isShiShangStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
            }else if(isGuanShaStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
            }
        }
    }

    @Composable
    fun getXiJiString(data : BaziData) : String{
        var str = ""
        str = str + stringResource(R.string.app_bazi_yong_shen) + ":"
        for(ss in data.yongShenList){
            str = str + "[" + ShiShenUtil().getShiShenLabel(ss) + "]"
        }
        str = str + "\n"
        str = str + stringResource(R.string.app_bazi_xi_shen) + ":"
        for(ss in data.xiShenList){
            str = str + "[" + ShiShenUtil().getShiShenLabel(ss) + "]"
        }
        str = str + "\n"
        str = str + stringResource(R.string.app_bazi_ji_shen) + ":"
        for(ss in data.jiShenList){
            str = str + "[" + ShiShenUtil().getShiShenLabel(ss) + "]"
        }
        return str
    }

    fun checkShiShenGJXiJi(gj : BaziGeJu, data : BaziData){
        var isBaziStrong = WuXingUtil().isBaziStrong(data)
        println("checkShiShenGJXiJi(), isBaziStrong=$isBaziStrong")
        if(isBaziStrong){
            if(isYinStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
            }else if(isBiJieStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.xiShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN,ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
            }else if(isCaiStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
            }
        }else{
            if(isShiShangStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
            }else if(isGuanShaStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_QI_SHA)
            }else if(isCaiStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI,ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.jiShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN, ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
            }
        }
    }

    fun checkQiShaGJXiJi(gj : BaziGeJu, data : BaziData){
        var isBaziStrong = WuXingUtil().isBaziStrong(data)
        println("checkQiShaGJXiJi(), isBaziStrong=$isBaziStrong")
        if(isBaziStrong){
            if(isYinStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA, ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
            }else if(isBiJieStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA,ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
            }else if(isGuanShaStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.xiShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
            }
        }else{
            if(isShiShangStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
            }else if(isGuanShaStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
            }else if(isCaiStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI,ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.jiShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN, ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
            }
        }
    }

    @Composable
    fun getGeJuXiJi(gj : BaziGeJu, data : BaziData) : String{
        var str = ""
        println("Bazi gj=$gj")
        if(gj == BaziGeJu.GJ_SHANG_GUAN){
            checkShangGuanGJXiJi(gj, data)
        }else if(gj == BaziGeJu.GJ_ZHENG_YIN || gj == BaziGeJu.GJ_PIAN_YIN){
            checkYinGJXiJi(gj, data)
        }else if(gj == BaziGeJu.GJ_ZHENG_GUAN){
            checkZhengGuanGJXiJi(gj, data)
        }else if(gj == BaziGeJu.GJ_ZHENG_CAI || gj == BaziGeJu.GJ_PIAN_CAI){
            checkCaiGJXiJi(gj, data)
        }else if(gj == BaziGeJu.GJ_SHI_SHEN) {
            checkShiShenGJXiJi(gj, data)
        }else if(gj == BaziGeJu.GJ_QI_SHA) {
            checkQiShaGJXiJi(gj, data)
        }
        else{
            str = "ToDo"
        }
        str = getXiJiString(data)
//            println("=============================")
//            println("data.xiShenList=${data.xiShenList}")
//            println("data.jiShenList=${data.jiShenList}")
//            println("data.yongShenList=${data.yongShenList}")
        return str
    }
}